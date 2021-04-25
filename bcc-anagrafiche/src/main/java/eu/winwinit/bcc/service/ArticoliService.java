package eu.winwinit.bcc.service;

import java.util.List;

import eu.winwinit.bcc.entities.Articolo;

public interface ArticoliService {

	public List<Articolo> findAll() throws Exception;

	public List<Articolo> findByFilter(String descArticolo, String categoria) throws Exception;

	public List<Articolo> saveAndFlush(Articolo articoloInsert) throws Exception;

	public List<Articolo> patch(int idArticolo, Double prezzo) throws Exception;

	public List<Articolo> delete(int idArticolo) throws Exception;
}
