package eu.winwinit.bcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Articoli;

@Repository("articoliRepository")
public interface ArticoliRepository extends JpaRepository<Articoli, Integer> {

}
