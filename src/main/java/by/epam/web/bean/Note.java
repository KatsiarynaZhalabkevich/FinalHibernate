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

    private int id;
    private Date time;
    private Tarif tariff;
    private User user;

    public Note() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "create_time")
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    public Tarif getTariff() {
        return tariff;
    }

    public void setTariff(Tarif tariff) {
        this.tariff = tariff;
    }
    @ManyToOne
    @JoinColumn(name="user_id")
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
