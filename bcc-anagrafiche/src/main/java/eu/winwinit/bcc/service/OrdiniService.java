package eu.winwinit.bcc.service;

import java.util.List;

import eu.winwinit.bcc.entities.Ordini;

public interface OrdiniService {

	public List<Ordini> findAll() throws Exception;
	public Ordini findById(int id) throws Exception;
	public Ordini saveAndFlush(Ordini ordini) throws Exception;
	public Ordini variaOrdine(int id, Ordini ordini) throws Exception;
	public Ordini deleteOrdine(int id) throws Exception;
}

