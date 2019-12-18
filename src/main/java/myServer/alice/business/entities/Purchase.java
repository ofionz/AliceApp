package myServer.alice.business.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "purchases")

public class Purchase implements Comparable<Purchase> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "text")
    private String text = null;

    @Column(name = "date")
    private LocalDate date = null;

    @Column(name = "price")
    private Integer price = null;

    public Purchase() {

    }


    public Purchase(String text, Integer price) {
        this.text = text;
        this.date = LocalDate.now();
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int compareTo(Purchase o) {
        if (this.id > o.id) return -1;
        else if (this.id < o.id) return 1;
        else return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
