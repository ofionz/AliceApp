package myServer.alice.business.entities;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;
    @Column(name = "date")
    private LocalDate date = null;
    @Column(name = "amount")
    private Integer amount = null;
    @Column(name = "promocode")
    private boolean promocode = false;


    public Balance() {

    }

    public LocalDate getDate() {
        return date;
    }

    public Balance setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public boolean isPromocode() {
        return promocode;
    }

    public void setPromocode(boolean promocode) {
        this.promocode = promocode;
    }

    public Balance setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
