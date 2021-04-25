package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Ordine;

@Repository("ordiniRepository")
public interface OrdiniRepository extends JpaRepository<Ordine, Integer> {

}
