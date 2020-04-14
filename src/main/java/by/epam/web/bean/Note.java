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
//    @Column(name = "user_id")
//    private int userId;

//    @Column(name = "tariff_id")
//    private int tarifId;
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

    public void setId(int id) {
        this.id = id;
    }

//    public int getTarifId() {
//        return tarifId;
//    }
//
//    public void setTarifId(int tarifId) {
//        this.tarifId = tarifId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date createNote) {
        this.time = createNote;
    }

    @Override
    public String toString() {
        return "TarifNote [id=" + id + "" +
        //        "/*, tarifId=" + tarifId */
       // ", userId=" + userId +
                ", createNote=" + time
                + "]";
    }



}
