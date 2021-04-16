package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.RuoloUtente;

@Repository("ruoloRepository")
public interface RuoloRepository extends JpaRepository<RuoloUtente, Integer> {
	
	RuoloUtente findByRuolo(String ruolo);
}
