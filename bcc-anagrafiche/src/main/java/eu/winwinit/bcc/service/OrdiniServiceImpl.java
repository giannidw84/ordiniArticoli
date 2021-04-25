package eu.winwinit.bcc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.entities.OrdiniArticoli;
import eu.winwinit.bcc.model.ArticoloDettaglioOrdine;
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

	public List<Ordine> findAll() throws Exception {

		List<Ordine> ordiniList = ordiniRepository.findAll();

		for (Ordine ordineRead : ordiniList) {
			ordineRead.setArticoloDettaglioOrdine(createListArticoliForResponse(ordineRead));
		}
		return ordiniList;
	}

	public List<Ordine> findById(int idOrdine) throws Exception {
		List<Ordine> ordine = new ArrayList<Ordine>();
		Ordine ordini = ordiniRepository.findById(idOrdine).get();
		ordini.setArticoloDettaglioOrdine(createListArticoliForResponse(ordini));
		ordine.add(ordini);
		return ordine;
	}

	public List<Ordine> saveAndFlush(Ordine ordineInsert) throws Exception {

		List<Ordine> ordine = new ArrayList<Ordine>();
		double totPrezzoOrdine = 0;
		int numTotaleArticoli = 0;

		List<ArticoloDettaglioOrdine> articoliNew = ordineInsert.getArticoloDettaglioOrdine();

		for (ArticoloDettaglioOrdine articoloRead : articoliNew) {
			Articolo articolo = articoliRepository.findById(articoloRead.getArticolo().getIdArticolo()).get();
			numTotaleArticoli = numTotaleArticoli + articoloRead.getQuantita();
			totPrezzoOrdine = totPrezzoOrdine + (articolo.getPrezzo() * articoloRead.getQuantita());
			OrdiniArticoli ordArt = new OrdiniArticoli();
			ordArt.setQuantita(articoloRead.getQuantita());
			ordArt.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
			ordArt.setOrdini(ordineInsert);
			ordArt.setArticolo(articolo);
			ordineInsert.getOrdiniArticoli().add(ordArt);
			articoloRead.setArticolo(articolo);
		}
		ordineInsert.setTotPrezzo(totPrezzoOrdine);
		ordineInsert.setTotaleArticoli(numTotaleArticoli);
		Ordine saved = ordiniRepository.saveAndFlush(ordineInsert);
		ordine.add(saved);

		return ordine;
	}

	public List<Ordine> variaOrdine(int idOrdine, Ordine ordiniVariati) throws Exception {
		List<Ordine> ordine = new ArrayList<Ordine>();

		Ordine ordineRead = ordiniRepository.findById(idOrdine).get();

		if (ordineRead != null) {

			double totPrezzoOrdine = ordineRead.getTotPrezzo();
			int numTotaleArticoli = ordineRead.getTotaleArticoli();

			List<ArticoloDettaglioOrdine> articoli = ordiniVariati.getArticoloDettaglioOrdine();

			for (ArticoloDettaglioOrdine articoloRead : articoli) {
				Articolo articolo = articoliRepository.findById(articoloRead.getArticolo().getIdArticolo()).get();

				switch (articoloRead.getAzione()) {

				case "nuovo":
					OrdiniArticoli ordArtNew = new OrdiniArticoli();
					numTotaleArticoli = numTotaleArticoli + articoloRead.getQuantita();
					totPrezzoOrdine = totPrezzoOrdine + (articolo.getPrezzo() * articoloRead.getQuantita());
					ordArtNew.setOrdini(ordineRead);
					ordArtNew.setArticolo(articolo);
					ordArtNew.setQuantita(articoloRead.getQuantita());
					ordArtNew.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
					ordineRead.getOrdiniArticoli().add(ordArtNew);
					break;

				case "varia":
					for (OrdiniArticoli elaboraArticoliOrdine : ordineRead.getOrdiniArticoli()) {
						if (articolo.getIdArticolo() == elaboraArticoliOrdine.getArticolo().getIdArticolo()) {
// nuova quantita (totale quantita ordine - quantita ordine da modificare) + nuova quantita

							numTotaleArticoli = (numTotaleArticoli - elaboraArticoliOrdine.getQuantita())
									+ articoloRead.getQuantita();
// nuovo importo totale ordine (totale ordine - importo totale articolo da modificare) + (prezzo articolo * quantita)

							totPrezzoOrdine = (totPrezzoOrdine - elaboraArticoliOrdine.getTotale())
									+ (articolo.getPrezzo() * articoloRead.getQuantita());
							elaboraArticoliOrdine.setQuantita(articoloRead.getQuantita());
							elaboraArticoliOrdine.setTotale(articolo.getPrezzo() * articoloRead.getQuantita());
						}
					}
					break;

				case "cancella": {
//					Set<OrdiniArticoli> articoli2save = new HashSet<OrdiniArticoli>();
//					for (OrdiniArticoli elaboraArticoliOrdine : ordineRead.getOrdiniArticoli()) {
//						if (articolo.getIdArticolo() != elaboraArticoliOrdine.getArticolo().getIdArticolo()) {
//							articoli2save.add(elaboraArticoliOrdine);
//						}
//					}
//					ordineRead.setOrdiniArticoli(articoli2save);
//					
//				}

					for (OrdiniArticoli elaboraArticoliOrdine : ordineRead.getOrdiniArticoli()) {
						if (articolo.getIdArticolo() == elaboraArticoliOrdine.getArticolo().getIdArticolo()) {
							numTotaleArticoli = numTotaleArticoli - elaboraArticoliOrdine.getQuantita();
							totPrezzoOrdine = totPrezzoOrdine - elaboraArticoliOrdine.getTotale();
							ordineRead.getOrdiniArticoli().remove(elaboraArticoliOrdine);
							ordiniArticoliRepository.delete(elaboraArticoliOrdine);
							break;
						}
					}
					break;
				}
				}
			}

			if (numTotaleArticoli > 0) {
				ordineRead.setTotPrezzo(totPrezzoOrdine);
				ordineRead.setTotaleArticoli(numTotaleArticoli);
				ordineRead = ordiniRepository.saveAndFlush(ordineRead);
				ordineRead.setArticoloDettaglioOrdine(createListArticoliForResponse(ordineRead));
				ordine.add(ordineRead);
				return ordine;
			} else {
				ordine = deleteOrdine(idOrdine);
			}
		}
		return ordine;
	}

	public List<Ordine> deleteOrdine(int idOrdine) throws Exception {
		List<Ordine> ordine = new ArrayList<Ordine>();
		Ordine ordineRead = ordiniRepository.findById(idOrdine).get();

		if (ordineRead != null) {
			ordiniRepository.deleteById(idOrdine);
		}

		ordineRead.setArticoloDettaglioOrdine(createListArticoliForResponse(ordineRead));
		ordine.add(ordineRead);
		return ordine;
	}

	private List<ArticoloDettaglioOrdine> createListArticoliForResponse(Ordine ordineForResponse) {
		List<ArticoloDettaglioOrdine> listaArticoli = new ArrayList<ArticoloDettaglioOrdine>();
		for (OrdiniArticoli elaboraArticolo : ordineForResponse.getOrdiniArticoli()) {
			ArticoloDettaglioOrdine articolo = new ArticoloDettaglioOrdine();
			articolo.setArticolo(elaboraArticolo.getArticolo());
			articolo.setQuantita(elaboraArticolo.getQuantita());
			listaArticoli.add(articolo);
		}
		return listaArticoli;
	}
}
