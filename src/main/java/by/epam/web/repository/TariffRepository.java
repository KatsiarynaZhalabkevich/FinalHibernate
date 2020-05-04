package by.epam.web.repository;

import by.epam.web.bean.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TariffRepository extends CrudRepository<Tarif, Integer>, JpaRepository<Tarif, Integer> {

    Tarif getTarifById(int id);
}
