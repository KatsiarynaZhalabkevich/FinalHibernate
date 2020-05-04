package by.epam.web.repository;

import by.epam.web.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {

    User findUserByLogin(String login);

    List<User> findUsersByName(String name);

    List<User> findUsersBySurname(String surname);

    List<User> findUsersByEmail(String email);

    List<User> findUsersByPhone(String phone);

    User getUserById(int id);
    User getUserByLogin(String login);

    @Modifying
    @Query("update User set name = :name, " +
            "surname = :surname, " +
            "phone = :phone, " +
            "email = :email " +
            "where id = :id ")
    User updateUserInfo(@Param("name") String name,
                        @Param("surname") String surname,
                        @Param("phone") String phone,
                        @Param("email") String email,
                        @Param("id") int id);

    @Modifying
    @Query("update User set password = :password where id = :id")
    User updatePassword(@Param("password") String password, @Param("id") int id);

    @Modifying
    @Query("update User set active = :active where id = :id")
    User updateIsActive(@Param("active") boolean active, @Param("id") int id);

    @Modifying
    @Query("update User set balance = :balance where id = :id")
    User updateUserBalanceById(int id, double balance);

}
