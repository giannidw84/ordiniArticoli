package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.entities.Ordini;

@Repository("ordiniRepository")
public interface OrdiniRepository extends JpaRepository<Ordini, Integer> {

}
