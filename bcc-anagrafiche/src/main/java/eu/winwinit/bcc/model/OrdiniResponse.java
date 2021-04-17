package eu.winwinit.bcc.model;

import java.util.List;

import eu.winwinit.bcc.entities.Ordini;

public class OrdiniResponse extends BaseResponse {

	private List<Ordini> ordini;

	public List<Ordini> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordini> ordini) {
		this.ordini = ordini;
	}

}
