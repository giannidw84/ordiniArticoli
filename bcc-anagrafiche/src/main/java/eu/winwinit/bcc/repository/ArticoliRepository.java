package eu.winwinit.bcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Articolo;

@Repository("articoliRepository")
public interface ArticoliRepository extends JpaRepository<Articolo, Integer> {

	List<Articolo> findByDescArticoloIgnoreCaseContainingAndCategoriaIgnoreCase(String descArticolo, String Categoria);

	List<Articolo> findByDescArticoloIgnoreCaseContaining(String descArticolo);

	List<Articolo> findByCategoriaIgnoreCase(String Categoria);

	Articolo findByIdArticolo(int IdArticolo);

}
