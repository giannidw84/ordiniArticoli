package eu.winwinit.bcc.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.winwinit.bcc.model.ArticoloDettaglioOrdine;

@Entity
@Table(name = "ordini")
public class Ordine implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_ordine", unique = true, nullable = false)
	private Integer idOrdine;

	@Column(name = "data_ordine", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Generated(GenerationTime.INSERT)
	private Date dataOrdine;

	private String cliente;

	@Column(name = "totale_articoli")
	private Integer totaleArticoli;

	@Column(name = "totale_prezzo")
	private Double totPrezzo;

	@OneToMany(mappedBy = "primaryKey.ordini", cascade = CascadeType.ALL)
	private Set<OrdiniArticoli> ordiniArticoli;

	@JsonIgnore
	public Set<OrdiniArticoli> getOrdiniArticoli() {
		if (ordiniArticoli == null) {
			ordiniArticoli = new HashSet<OrdiniArticoli>();
		}
		return ordiniArticoli;
	}

	@Transient
	private List<ArticoloDettaglioOrdine> articoloDettaglioOrdine;	

	public void setOrdiniArticoli(Set<OrdiniArticoli> ordiniArticoli) {
		this.ordiniArticoli = ordiniArticoli;
	}

	public List<ArticoloDettaglioOrdine> getArticoloDettaglioOrdine() {
		return articoloDettaglioOrdine;
	}

	public void setArticoloDettaglioOrdine(List<ArticoloDettaglioOrdine> articoloDettaglioOrdine) {
		this.articoloDettaglioOrdine = articoloDettaglioOrdine;
	}

	public Integer getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Integer idOrdine) {
		this.idOrdine = idOrdine;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getTotaleArticoli() {
		return totaleArticoli;
	}

	public void setTotaleArticoli(Integer totaleArticoli) {
		this.totaleArticoli = totaleArticoli;
	}

	public Double getTotPrezzo() {
		return totPrezzo;
	}

	public void setTotPrezzo(Double totPrezzo) {
		this.totPrezzo = totPrezzo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}