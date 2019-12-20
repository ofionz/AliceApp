package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Task;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDaoImpl implements TaskDAO {




    public Task findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Task.class, id);
    }

    /**
     * Find ALL Tasks on  date what you want
     * @param date LOcalDate
     * @return List<Task></>
     */
        @Override
    public List<Task> findBy(LocalDate date) {
        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("date", date);
        String hqlQuery = "FROM Task WHERE date =:date";

        List<Task> tasks = sendRequest(hqlQuery, parameterNameAndValues);

        return tasks;
    }

    /** This method just open connection with Data Base
     * and send all parameters what you need *
     * @param hql String of hql request
     * @param parameterNameAndValues Map where String it is name of parameter, Object it s parameter (LocalDate, String)
     * @return List of tasks with necessary parameters (date or time of day)
     */
    private List<Task> sendRequest(String hql, Map<String, Object> parameterNameAndValues) {
        Query query = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession().createQuery(hql);
        for (Map.Entry<String, Object> e : parameterNameAndValues.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        return query.list();
    }

    /** Find by tome of day and date
     *
     * @param time of day
     * @param date
     * @return list tasks
     */
    public List<Task> findBy(String time, LocalDate date) {

        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("date", date);
        parameterNameAndValues.put("time", time);
        String hqlQuery = "FROM Task WHERE date =:date AND timeofday =:time";
        return sendRequest(hqlQuery, parameterNameAndValues);


    }

    /** Find all tasks between two dates it need when we want calculate all points between dates
     *
     * @param startDate
     * @param endDate
     * @return list tasks
     */
    public List<Task> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("startDate", startDate);
        parameterNameAndValues.put("endDate", endDate);
        String hqlQuery = "FROM Task WHERE date BETWEEN :startDate AND :endDate";
        return sendRequest(hqlQuery, parameterNameAndValues);
    }





}
