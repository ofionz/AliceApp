/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package myServer.alice.business.services;

import myServer.alice.business.entities.DB.HibernateSessionFactoryUtil;
import myServer.alice.business.entities.DB.TaskDAO;
import myServer.alice.business.entities.DB.TaskDaoImpl;
import myServer.alice.business.entities.Task;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;


public class TaskService {
    private static final Logger log = Logger.getLogger(TaskService.class);

    private TaskDAO taskDao = new TaskDaoImpl();


    public TaskService() {
        setDayTasks();
    }


    public Task findTask(int id) {
        return taskDao.findById(id);
    }


    /**
     * delete task by ID
     * @param id
     */
    public void deleteFromCurrentTasks(Integer id) {
        Task task = findTask(id);
        if (task != null && task.getDate().isEqual(LocalDate.now())) {
            taskDao.delete(task);
        }


    }

    public Map<String, List<Task>> getAllCurrentTasks() {
        List<Task> tasks = findBy(LocalDate.now());
        return splitByTimeOfDay(tasks);
    }

    public void invertTaskStatus(Integer id) {
        Task task = findTask(id);
        if (task != null && task.getDate().isEqual(LocalDate.now())) {
            if (task.isStatus()) task.setStatus(false);
            else task.setStatus(true);
            updateTask(task);
        }
    }


    public void saveTask(Task task) {
        taskDao.save(task);
    }

    public void updateTaskByOtherTask(int id, Task task2) {
        Task task = findTask(id);
        task.setText(task2.getText());
        task.setPoints(task2.getPoints());
        task.setFinepoints(task2.getFinepoints());

        taskDao.update(task);
    }

    private void updateTask(Task task) {
        taskDao.update(task);
    }

    public List<Task> findBy(LocalDate date) {
        return taskDao.findBy(date);

    }

    public List<Task> findBy(String time, LocalDate date) {
        return taskDao.findBy(time, date);
    }

    public List<Task> findBetweenDates(LocalDate date1, LocalDate date2) {
        return taskDao.findBetweenDates(date1, date2);
    }

    /** normalizes and updates data
     * in the database in order to display current tasks
     */
    private void setDayTasks() {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.from(today);
        List<Task> tasks = this.findBy(date);

        //if today tasks !=0 - break this method, else --
        if (tasks.size() == 0) {
            //we get date when tasks was available
            while (tasks.size() == 0) {
                date = date.minusDays(1);
                tasks = this.findBy(date);
            }
            //and copy all tasks to next day, until we get to today
            while (date.isBefore(today)) {
                date = date.plusDays(1);
                for (Task task : tasks) {
                    task.removeId();
                    task.setStatus(false);
                    task.setDate(date);
                    this.saveTask(task);
                }
            }
        }

    }

    /**
     *  Split  all tasks by time of day (morning,afternoon,evening) and sort (by id)
     *
     * @param list
     * @return map of tasks
     */

    private Map<String, List<Task>> splitByTimeOfDay(List<Task> list) {
        if (list==null||list.size() == 0) {
            log.warn("empty base! ");
            list.add(new Task("empty base", LocalDate.now(), false, "morning", 0, 0));
        } else {
            Comparator<Task> comparator = Comparator.comparing(obj -> obj.getId());
            list.sort(comparator);
        }

        Map<String, List<Task>> result = new HashMap<>();
        for (Task task : list) {
            String time = task.getTimeOfDay();
            if (!result.containsKey(time)) {
                List<Task> lst = new ArrayList<>();
                lst.add(task);
                result.put(time, lst);
            } else {
                result.get(time).add(task);
            }
        }

        return result;

    }


}