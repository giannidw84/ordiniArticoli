package eu.winwinit.bcc.service;

import java.util.List;

import eu.winwinit.bcc.entities.Ordine;

public interface OrdiniService {

	public List<Ordine> findAll() throws Exception;

	public List<Ordine> findById(int idOrdine) throws Exception;

	public List<Ordine> saveAndFlush(Ordine ordineInsert) throws Exception;

	public List<Ordine> variaOrdine(int idOrdine, Ordine ordineDaVariare) throws Exception;

	public List<Ordine> deleteOrdine(int idOrdine) throws Exception;
}
