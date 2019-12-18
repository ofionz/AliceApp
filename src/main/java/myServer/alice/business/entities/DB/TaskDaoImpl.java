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

    @Override
    public List<Task> findBy(LocalDate date) {
        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("date", date);
        String hqlQuery = "FROM Task WHERE date =:date";

        List<Task> tasks = sendRequest(hqlQuery, parameterNameAndValues);

        return tasks;
    }

    private List<Task> sendRequest(String hql, Map<String, Object> parameterNameAndValues) {
        Query query = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession().createQuery(hql);
        for (Map.Entry<String, Object> e : parameterNameAndValues.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        return query.list();
    }

    public List<Task> findBy(String time, LocalDate date) {

        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("date", date);
        parameterNameAndValues.put("time", time);
        String hqlQuery = "FROM Task WHERE date =:date AND timeofday =:time";
        return sendRequest(hqlQuery, parameterNameAndValues);


    }

    public List<Task> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> parameterNameAndValues = new HashMap<>();
        parameterNameAndValues.put("startDate", startDate);
        parameterNameAndValues.put("endDate", endDate);
        String hqlQuery = "FROM Task WHERE date BETWEEN :startDate AND :endDate";
        return sendRequest(hqlQuery, parameterNameAndValues);
    }


    public List<Task> findAll() {

        List<Task> tasks = (List<Task>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Task")
                .list();

        return tasks;
    }


}
