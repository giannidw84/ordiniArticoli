package eu.winwinit.bcc.service;

import java.util.List;

import eu.winwinit.bcc.entities.Articoli;

public interface ArticoliService {

	public List<Articoli> findAll() throws Exception;
	public Articoli saveAndFlush(Articoli art) throws Exception;
	public Articoli patch(int id, Double prezzo) throws Exception;
	public Articoli delete(int id) throws Exception;
}
