package eu.winwinit.bcc.service;

import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.entities.Ordini;

public interface OrdiniService {

	public Ordini saveAndFlush(Ordini art) throws Exception;

}

