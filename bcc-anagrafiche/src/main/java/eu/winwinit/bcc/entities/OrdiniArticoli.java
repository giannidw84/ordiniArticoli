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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrdiniArticoliId primaryKey = new OrdiniArticoliId();

	double totale;

	@EmbeddedId
	public OrdiniArticoliId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(OrdiniArticoliId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Transient
	public Ordini getOrdini() {
		return getPrimaryKey().getOrdini();
	}

	public void setOrdini(Ordini ordini) {
		getPrimaryKey().setOrdini(ordini);
	}

	@Transient
	public Articoli getArticoli() {
		return getPrimaryKey().getArticoli();
	}

	public void setArticoli(Articoli articoli) {
		getPrimaryKey().setArticoli(articoli);
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
