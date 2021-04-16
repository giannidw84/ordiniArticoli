package eu.winwinit.bcc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ordini_articoli")

public class OrdiniArticoli implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_ordine")
	Integer idOrdine;

	@Column(name = "id_articolo")
	Integer idArticolo;

	double totale;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_articolo", insertable = false, updatable = false)
	private Articoli articoli;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_ordine", insertable = false, updatable = false)
	private Ordini ordini;
    
	public Integer getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Integer idOrdine) {
		this.idOrdine = idOrdine;
	}

	public Integer getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public Articoli getArticoli() {
		return articoli;
	}

	public void setArticoli(Articoli articoli) {
		this.articoli = articoli;
	}

	public Ordini getOrdini() {
		return ordini;
	}

	public void setOrdini(Ordini ordini) {
		this.ordini = ordini;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
