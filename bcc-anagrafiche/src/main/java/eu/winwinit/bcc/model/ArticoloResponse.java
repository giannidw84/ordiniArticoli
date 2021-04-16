package eu.winwinit.bcc.model;

import eu.winwinit.bcc.entities.Articoli;

public class ArticoloResponse extends BaseResponse {

	private Articoli articolo;

	public Articoli getArticolo() {
		return articolo;
	}

	public void setArticolo(Articoli articolo) {
		this.articolo = articolo;
	}

}
