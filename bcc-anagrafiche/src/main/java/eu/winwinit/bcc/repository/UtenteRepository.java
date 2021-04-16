package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Utente;

@Repository("utenteRepository")
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	
	Utente findByUsername(String username);
	Utente findByUsernameAndPassword(String username, String password);
}
