package by.epam.web.bean;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Entity
@Table(name = "tariff_note")
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "create_time")
    private Date time;
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tarif tariff;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Note() {
    }

    public int getId() {
        return id;
    }
    public Date getTime() {
        return time;
    }

    public Tarif getTariff() {
        return tariff;
    }

    public void setTariff(Tarif tariff) {
        this.tariff = tariff;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", time=" + time +
                ", tariff=" + tariff +
                ", user=" + user +
                '}';
    }
}
