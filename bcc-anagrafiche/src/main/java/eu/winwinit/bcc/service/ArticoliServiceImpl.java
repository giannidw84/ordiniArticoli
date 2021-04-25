package eu.winwinit.bcc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.repository.ArticoliRepository;

@Service("articoliService")
public class ArticoliServiceImpl implements ArticoliService {

	@Autowired
	private ArticoliRepository articoliRepository;

	public List<Articolo> findAll() throws Exception {
		List<Articolo> articoliList = articoliRepository.findAll();
		return articoliList;
	}

	public List<Articolo> findByFilter(String descArticolo, String categoria) throws Exception {

		List<Articolo> articolo;

		if (descArticolo.isEmpty()) {
			articolo = articoliRepository.findByCategoriaIgnoreCase(categoria);
		} else if (categoria.isEmpty()) {
			articolo = articoliRepository.findByDescArticoloIgnoreCaseContaining(descArticolo);
		} else {
			articolo = articoliRepository.findByDescArticoloIgnoreCaseContainingAndCategoriaIgnoreCase(descArticolo,
					categoria);
		}
		return articolo;
	}

	public List<Articolo> saveAndFlush(Articolo articoloInsert) throws Exception {
		List<Articolo> articolo = new ArrayList<Articolo>();
		articolo.add(articoliRepository.saveAndFlush(articoloInsert));
		return articolo;
	}

	public List<Articolo> patch(int idArticolo, Double prezzo) throws Exception {
		List<Articolo> articolo = new ArrayList<Articolo>();
		Articolo articoloRead = articoliRepository.findById(idArticolo).get();
		if (articoloRead != null) {
			articoloRead.setPrezzo(prezzo);
			articolo.add(articoliRepository.saveAndFlush(articoloRead));
		}
		return articolo;
	}

	public List<Articolo> delete(int idArticolo) throws Exception {
		List<Articolo> articolo = new ArrayList<Articolo>();
		Articolo articoloRead = articoliRepository.findById(idArticolo).get();
		if (articoloRead != null) {
			articoliRepository.deleteById(idArticolo);
			articolo.add(articoloRead);
		}
		return articolo;
	}
}
