package by.epam.web.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tariff")
public class Tarif implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int speed;
    private double discount = 0;
    private String description;
    @OneToMany(mappedBy = "tariff")
    private List<Note> notes;
//    @ManyToMany(mappedBy = "User")
//    private List<User> users = new ArrayList<>(0);

    public Tarif() {
    }

    public Tarif(String name, double price, int speed, double discount, String description) {
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.discount = discount;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> userList) {
//        this.users = userList;
//    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());

        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + speed;
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
        Tarif other = (Tarif) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (discount != other.discount)
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (speed != other.speed)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Tarif [id=" + id + ", name=" + name + ", price=" + price + ", speed=" + speed + ", discount=" + discount
                + ", description=" + description + "]";
    }
}
