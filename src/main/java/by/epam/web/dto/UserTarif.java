package by.epam.web.dto;

import by.epam.web.bean.Tarif;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserTarif implements Serializable {
    /**
     * Класс dto для обработки сложных запросов
     */

//    private int tarifId;
//    private String name;
//    private String description;
//    private double price;
//    private int speed;
//    private double discount = 0;

    private int userId;
    private Tarif tarif;
    private int noteId;
    private Date date;
    public UserTarif() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    //    public int getTarifId() {
//        return tarifId;
//    }
//
//    public void setTarifId(int tarifId) {
//        this.tarifId = tarifId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }
//
//    public double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(double discount) {
//        this.discount = discount;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTarif userTarif = (UserTarif) o;
        return getUserId() == userTarif.getUserId() &&
                getNoteId() == userTarif.getNoteId() &&
                Objects.equals(tarif, userTarif.tarif) &&
                Objects.equals(getDate(), userTarif.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), tarif, getNoteId(), getDate());
    }

    @Override
    public String toString() {
        return "UserTarif{" +
                "userId=" + userId +
                ", tarif=" + tarif +
                ", noteId=" + noteId +
                ", date=" + date +
                '}';
    }
}
