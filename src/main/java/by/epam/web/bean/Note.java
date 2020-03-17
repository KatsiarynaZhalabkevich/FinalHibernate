package by.epam.web.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tariff_note")
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_id")
    private int userId;
    @Column(name="tariff_id")
    private int tarifId;
    @Column(name="create_time")
    private Date time;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTarifId() {
        return tarifId;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date createNote) {
        this.time = createNote;
    }

    @Override
    public String toString() {
        return "TarifNote [id=" + id + ", tarifId=" + tarifId + ", userId=" + userId + ", createNote=" + time
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + id;
        result = prime * result + tarifId;
        result = prime * result + userId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (id != other.id)
            return false;
        if (tarifId != other.tarifId)
            return false;
        return userId == other.userId;
    }

}
