package eu.winwinit.bcc.model;

import java.util.List;

import eu.winwinit.bcc.entities.Ordine;

public class OrdiniResponse extends BaseResponse {

	private List<Ordine> ordini;

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

}
