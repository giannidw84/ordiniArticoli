package eu.winwinit.bcc.model;

import java.util.List;

import eu.winwinit.bcc.entities.Articolo;

public class ArticoliResponse extends BaseResponse {

	private List<Articolo> articoli;

	public List<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}

}
