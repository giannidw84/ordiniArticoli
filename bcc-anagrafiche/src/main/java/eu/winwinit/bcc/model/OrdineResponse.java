package eu.winwinit.bcc.model;

import eu.winwinit.bcc.entities.Ordini;

public class OrdineResponse extends BaseResponse {

	private Ordini ordine;

	public Ordini getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordini ordine) {
		this.ordine = ordine;
	}

}
