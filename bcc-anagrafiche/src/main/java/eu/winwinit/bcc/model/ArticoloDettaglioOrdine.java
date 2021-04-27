package eu.winwinit.bcc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.winwinit.bcc.entities.Articolo;

public class ArticoloDettaglioOrdine {

	private Articolo articolo;

	private Integer quantita;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String azione;

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

}
