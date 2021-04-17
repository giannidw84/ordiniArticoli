package eu.winwinit.bcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.OrdiniArticoli;

@Repository("ordiniArticoliRepository")
public interface OrdiniArticoliRepository extends JpaRepository<OrdiniArticoli, Integer> {

	@Query(value = "select b "
			 + "FROM OrdiniArticoli b "
			 + "WHERE id_ordine = :id_ordine ")
	public List<OrdiniArticoli> findOrdini(@Param("id_ordine") Integer idOrdine);

	@Query(value = "select b "
			 + "FROM OrdiniArticoli b "
			 + "WHERE id_ordine = :id_ordine "
			 + "AND id_articolo = :id_articolo")
	public OrdiniArticoli findOrdineArticolo(@Param("id_ordine") Integer idOrdine,@Param("id_articolo") Integer idArticolo);
}