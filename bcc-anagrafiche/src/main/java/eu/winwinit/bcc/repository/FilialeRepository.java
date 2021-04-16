package eu.winwinit.bcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Filiale;

@Repository("filialeRepository")
public interface FilialeRepository extends JpaRepository<Filiale, Integer> {
	
	Filiale findByCab(String cab);
	
	List<Filiale> findAll();
	
}
