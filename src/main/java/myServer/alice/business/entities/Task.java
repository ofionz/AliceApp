package myServer.alice.business.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "text")
    private String text = null;

    @Column(name = "date")
    private LocalDate date = null;

    @Column(name = "status")
    private boolean status = false;

    @Column(name = "timeofday")
    private String timeOfDay = null;

    @Column(name = "points")
    private Integer points = null;

    @Column(name = "finepoints")
    private Integer finepoints = null;

    @Column(name = "dayofweeks")
    private String dayOfWeeks = null;

    public Task() {

    }

    public Task(String text, LocalDate date, boolean status, String timeOfDay, Integer points, Integer finepoints, String dayOfWeeks) {
        this.text = text;
        this.date = date;
        this.status = status;
        this.timeOfDay = timeOfDay;
        this.points = points;
        this.finepoints = finepoints;
        this.dayOfWeeks=dayOfWeeks;
    }


    public Integer getId() {
        return this.id;
    }

    public void removeId() {
        this.id = null;
    }

    public Integer getPoints() {
        return points;
    }

    public Task setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public String getDayOfWeeks() {
        return dayOfWeeks;
    }
    public boolean isDayOfWeek(char ch) {
       if (dayOfWeeks.toCharArray()[Character.getNumericValue(ch)]=='1')return true;
       return false;
    }

    public void setDayOfWeeks(String dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public Integer getFinepoints() {
        return finepoints;
    }

    public Task setFinepoints(Integer finepoints) {
        this.finepoints = finepoints;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public Task setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
