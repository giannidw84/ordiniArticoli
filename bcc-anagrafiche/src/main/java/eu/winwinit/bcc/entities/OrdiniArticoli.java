package eu.winwinit.bcc.entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ordini_articoli")
@AssociationOverrides({ @AssociationOverride(name = "primaryKey.ordini", joinColumns = @JoinColumn(name = "id_ordine")),
		@AssociationOverride(name = "primaryKey.articoli", joinColumns = @JoinColumn(name = "id_articolo")) })

public class OrdiniArticoli implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private OrdiniArticoliId primaryKey = new OrdiniArticoliId();

	Integer quantita;
	
	double totale;

	@EmbeddedId
	public OrdiniArticoliId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(OrdiniArticoliId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Transient
	public Ordine getOrdini() {
		return getPrimaryKey().getOrdini();
	}

	public void setOrdini(Ordine ordini) {
		getPrimaryKey().setOrdini(ordini);
	}

	@Transient
	public Articolo getArticolo() {
		return getPrimaryKey().getArticoli();
	}

	public void setArticolo(Articolo articoli) {
		getPrimaryKey().setArticoli(articoli);
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	
	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
