package by.epam.web.repository;

import by.epam.web.bean.Tarif;
import by.epam.web.dto.UserTarif;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TariffRepository extends CrudRepository<Tarif, Integer>, JpaRepository<Tarif, Integer>, PagingAndSortingRepository<Tarif, Integer> {

    Tarif getTarifById(int id);

}
