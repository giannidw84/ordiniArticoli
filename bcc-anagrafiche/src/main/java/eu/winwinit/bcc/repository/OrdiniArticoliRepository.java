package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.OrdiniArticoli;

@Repository("ordiniArticoliRepository")
public interface OrdiniArticoliRepository extends JpaRepository<OrdiniArticoli, Integer> {

}