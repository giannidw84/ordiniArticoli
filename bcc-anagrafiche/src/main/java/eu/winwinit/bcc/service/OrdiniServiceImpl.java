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
		}

		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		ordine.setDataOrdine(ts);
		ordine.setTotPrezzo(totPrezzoOrdine);
		ordine.setTotaleArticoli(numTotaleArticoli);
		ordiniRepository.saveAndFlush(ordine);
		return ordine;
	}

	public Ordini variaOrdine(int id, Ordini ordini) throws Exception {
		Ordini ordine = ordiniRepository.findById(id).get();

		if (ordine != null) {

			double totPrezzoOrdine = ordine.getTotPrezzo();
			int numTotaleArticoli = ordine.getTotaleArticoli();

			List<Articoli> articoli = ordini.getArticoli();

			for (Articoli articoloRead : articoli) {
				OrdiniArticoli ordArt = ordiniArticoliRepository.findOrdineArticolo(id, articoloRead.getIdArticolo());
				Articoli articolo = articoliRepository.findById(articoloRead.getIdArticolo()).get();
				OrdiniArticoli ordArtNew = new OrdiniArticoli();

				switch (articoloRead.getAzione()) {

				case "nuovo":
					numTotaleArticoli = numTotaleArticoli + articoloRead.getQuantita();
					totPrezzoOrdine = totPrezzoOrdine + (articolo.getPrezzo() * articoloRead.getQuantita());
					ordArtNew.setOrdini(ordine);
					ordArtNew.setArticoli(articolo);
					ordArtNew.setQuantita(articoloRead.getQuantita());
					ordArtNew.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
					ordine.getOrdiniArticoli().add(ordArtNew);
					break;

				case "varia":
// nuova quantita (totale quantita ordine - quantita ordine da modificare) + nuova quantita

					numTotaleArticoli = (numTotaleArticoli - ordArt.getQuantita()) + articoloRead.getQuantita();
// nuovo importo totale ordine (totale ordine - importo totale articolo da modificare) + (prezzo articolo * quantita)

					totPrezzoOrdine = (totPrezzoOrdine - ordArt.getTotale())
							+ (articolo.getPrezzo() * articoloRead.getQuantita());
					ordArt.setQuantita(articoloRead.getQuantita());
					ordArt.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
					ordiniArticoliRepository.saveAndFlush(ordArt);
					break;

				case "cancella":
					numTotaleArticoli = numTotaleArticoli - ordArt.getQuantita();
					totPrezzoOrdine = totPrezzoOrdine - ordArt.getTotale();
					ordine.getOrdiniArticoli().remove(ordArt);
					ordiniArticoliRepository.delete(ordArt);
					break;

				}
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
		return ordine;
	}

	public Ordini deleteOrdine(int id) throws Exception {
		Ordini deleted = ordiniRepository.findById(id).get();
		ordiniRepository.deleteById(id);
		return deleted;
	}

}
