package eu.winwinit.bcc.model;

import java.util.List;

import eu.winwinit.bcc.entities.Articoli;

public class ArticoliResponse extends BaseResponse {

	private List<Articoli> articoli;

	public List<Articoli> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Articoli> articoli) {
		this.articoli = articoli;
	}
}
