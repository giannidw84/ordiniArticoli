package eu.winwinit.bcc.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.entities.Ordini;
import eu.winwinit.bcc.entities.OrdiniArticoli;
import eu.winwinit.bcc.repository.ArticoliRepository;
import eu.winwinit.bcc.repository.OrdiniArticoliRepository;
import eu.winwinit.bcc.repository.OrdiniRepository;

@Service("ordiniService")
public class OrdiniServiceImpl implements OrdiniService {

	@Autowired
	private OrdiniRepository ordiniRepository;

	@Autowired
	private ArticoliRepository articoliRepository;

	@Autowired
	private OrdiniArticoliRepository ordiniArticoliRepository;

	public List<Ordini> findAll() throws Exception {

		List<Ordini> ordini = ordiniRepository.findAll();

// popolo la response con i dettagli dell'ordine

		for (Ordini ordineRead : ordini) {
// per ogni ordine estraggo dalla tabella ordini_articoli il campo id_articoli			
			List<OrdiniArticoli> ordineArticoliRead = ordiniArticoliRepository.findOrdini(ordineRead.getIdOrdine());

			List<Articoli> dettaglioArticolo = new ArrayList<Articoli>();

			for (OrdiniArticoli elaboraIdArticolo : ordineArticoliRead) {
// per ogni id articolo ricava la descrizione, prezzo ... dell'articolo	
				Articoli articolo = articoliRepository
						.findByIdArticolo(elaboraIdArticolo.getArticoli().getIdArticolo());
				articolo.setQuantita(elaboraIdArticolo.getQuantita());
				dettaglioArticolo.add(articolo);
			}
			ordineRead.setArticoli(dettaglioArticolo);
		}
		return ordini;
	}

	public Ordini findById(int id) throws Exception {
		Ordini ordini = ordiniRepository.findById(id).get();
		List<OrdiniArticoli> ordineArticoliRead = ordiniArticoliRepository.findOrdini(ordini.getIdOrdine());

		List<Articoli> dettaglioArticolo = new ArrayList<Articoli>();

		for (OrdiniArticoli elaboraIdArticolo : ordineArticoliRead) {
//per ogni id articolo ricava la descrizione, prezzo ... dell'articolo	
			Articoli articolo = articoliRepository.findByIdArticolo(elaboraIdArticolo.getArticoli().getIdArticolo());
			articolo.setQuantita(elaboraIdArticolo.getQuantita());
			dettaglioArticolo.add(articolo);
		}
		ordini.setArticoli(dettaglioArticolo);
		return ordini;
	}

	public Ordini saveAndFlush(Ordini ordine) throws Exception {

		double totPrezzoOrdine = 0;
		int numTotaleArticoli = 0;

		List<Articoli> articoli = ordine.getArticoli();
		List<Articoli> dettaglioArticolo = new ArrayList<Articoli>();

		for (Articoli articoloRead : articoli) {
			Articoli articolo = articoliRepository.findById(articoloRead.getIdArticolo()).get();
			numTotaleArticoli = numTotaleArticoli + articoloRead.getQuantita();
			totPrezzoOrdine = totPrezzoOrdine + (articolo.getPrezzo() * articoloRead.getQuantita());
			OrdiniArticoli ordArt = new OrdiniArticoli();
			ordArt.setQuantita(articoloRead.getQuantita());
			ordArt.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
			ordArt.setOrdini(ordine);
			ordArt.setArticoli(articolo);
			ordine.getOrdiniArticoli().add(ordArt);

// valorizzo il dettaglio dell'articolo per la response	
			articolo.setQuantita(articoloRead.getQuantita());
			dettaglioArticolo.add(articolo);
		}

		if (numTotaleArticoli > 0) {
			ordine.setArticoli(dettaglioArticolo);
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			ordine.setDataOrdine(ts);
			ordine.setTotPrezzo(totPrezzoOrdine);
			ordine.setTotaleArticoli(numTotaleArticoli);
			ordiniRepository.saveAndFlush(ordine);
			return ordine;
		} else {
			return null;
		}
	}

	public Ordini addArticoli(int id, Ordini ordini) throws Exception {
		Ordini ordine = ordiniRepository.findById(id).get();

		if (ordine != null) {
			Double importoTotOrdine = ordine.getTotPrezzo();
			int quantitaTotOrdine = ordine.getTotaleArticoli();

			double prezzoTotArticoli = 0;
			int numTotaleArticoli = 0;

			List<Articoli> articoli = ordini.getArticoli();
			List<Articoli> dettaglioArticolo = new ArrayList<Articoli>();

			for (Articoli articoloRead : articoli) {
				Articoli articolo = articoliRepository.findById(articoloRead.getIdArticolo()).get();
				numTotaleArticoli = numTotaleArticoli + articoloRead.getQuantita();
				prezzoTotArticoli = prezzoTotArticoli + (articolo.getPrezzo() * articoloRead.getQuantita());
				OrdiniArticoli ordArt = new OrdiniArticoli();
				ordArt.setQuantita(articoloRead.getQuantita());
				ordArt.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
				ordArt.setOrdini(ordine);
				ordArt.setArticoli(articolo);
				ordine.getOrdiniArticoli().add(ordArt);

// valorizzo il dettaglio dell'articolo per la response	
				articolo.setQuantita(articoloRead.getQuantita());
				dettaglioArticolo.add(articolo);
			}

			if (numTotaleArticoli > 0) {
				ordine.setArticoli(dettaglioArticolo);
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				ordine.setDataOrdine(ts);
				ordine.setTotPrezzo(prezzoTotArticoli + importoTotOrdine);
				ordine.setTotaleArticoli(numTotaleArticoli + quantitaTotOrdine);
				ordiniRepository.saveAndFlush(ordine);
				return ordine;
			}
		}
		return ordini;

	}

	public Ordini deleteArticoli(int id, Ordini ordini) throws Exception {
		Ordini ordine = ordiniRepository.findById(id).get();

		if (ordine != null) {

			double totPrezzoOrdine = ordine.getTotPrezzo();
			int numTotaleArticoli = ordine.getTotaleArticoli();

			List<Articoli> articoli = ordini.getArticoli();

			for (Articoli articoloRead : articoli) {
				OrdiniArticoli ordArt = ordiniArticoliRepository.findOrdineArticolo(id, articoloRead.getIdArticolo());
				numTotaleArticoli = numTotaleArticoli - ordArt.getQuantita();
				totPrezzoOrdine = totPrezzoOrdine - ordArt.getTotale();
				ordiniArticoliRepository.delete(ordArt);
			}

			if (numTotaleArticoli > 0) {
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				ordine.setDataOrdine(ts);
				ordine.setTotPrezzo(totPrezzoOrdine);
				ordine.setTotaleArticoli(numTotaleArticoli);
				ordiniRepository.saveAndFlush(ordine);
				return ordine;
			} else {
				ordiniRepository.deleteById(id);
			}
		}
		return ordini;

	}

	public Ordini deleteOrdineAll(int id) throws Exception {
		Ordini deleted = ordiniRepository.findById(id).get();
		ordiniRepository.deleteById(id);
		return deleted;
	}

}
