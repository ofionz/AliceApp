package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskDAO extends DAO {
    public Task findById(int id);

    public List<Task> findBy(LocalDate date);

    public List<Task> findBy(String time, LocalDate date);

    public List<Task> findBetweenDates(LocalDate date, LocalDate date2);


}
