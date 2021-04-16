package eu.winwinit.bcc.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.entities.Ordini;
import eu.winwinit.bcc.entities.OrdiniArticoli;
import eu.winwinit.bcc.repository.ArticoliRepository;
import eu.winwinit.bcc.repository.OrdiniRepository;

@Service("ordiniService")
public class OrdiniServiceImpl implements OrdiniService {

	@Autowired
	private OrdiniRepository ordiniRepository;
	
	@Autowired
	private ArticoliRepository articoliRepository;

	public Ordini saveAndFlush(Ordini art) throws Exception {

		double totPrezzoOrdine = 0;
		int numTotaleArticoli = 0;

		List<Articoli> articoli = art.getArticoli();

		for (Articoli articoloRead : articoli) {
			Articoli articolo = articoliRepository.findById(articoloRead.getIdArticolo()).get();
			numTotaleArticoli++;
			totPrezzoOrdine = totPrezzoOrdine + articolo.getPrezzo();
			art.getArticoliAssociati().add(articolo);
//			OrdiniArticoli ordArt = new OrdiniArticoli();
//			ordArt.setTotale(numTotaleArticoli);
//			ordArt.setOrdini(art);
//			ordArt.setArticoli(articolo);
//			art.getOrdiniArticoli().add(ordArt);
		}

		if (numTotaleArticoli > 0) {
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			art.setDataOrdine(ts);
			art.setTotPrezzo(totPrezzoOrdine);
			art.setTotaleArticoli(numTotaleArticoli);
			ordiniRepository.saveAndFlush(art);
			return art;
		} else {
			return null;
		}
	}

}
