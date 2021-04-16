package eu.winwinit.bcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.repository.ArticoliRepository;

@Service("articoliService")
public class ArticoliServiceImpl implements ArticoliService {

	@Autowired
	private ArticoliRepository articoliRepository;

	public List<Articoli> findAll() throws Exception {
		List<Articoli> arts = articoliRepository.findAll();
		return arts;
	}

	public Articoli saveAndFlush(Articoli art) throws Exception {
		articoliRepository.saveAndFlush(art);
		return art;
	}

	public Articoli patch(int id, Double prezzo) throws Exception {
		Articoli art = articoliRepository.findById(id).get();
		if (art != null) {
			art.setPrezzo(prezzo);
			return articoliRepository.saveAndFlush(art);
		}
		return null;
	}

	public Articoli delete(int id) throws Exception {
		Articoli deleted = articoliRepository.findById(id).get();
		articoliRepository.deleteById(id);
		return deleted;
	}
}
