package eu.winwinit.bcc.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ordini")
public class Ordini implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_ordine", unique = true, nullable = false)
	private Integer idOrdine;

	@Column(name = "data_ordine", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dataOrdine;

	private String cliente;

	@Column(name = "totale_articoli")
	private Integer totaleArticoli;

	@Column(name = "totale_prezzo")
	private Double totPrezzo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "ordini_articoli", joinColumns = {
			@JoinColumn(name = "id_ordine", referencedColumnName = "id_ordine") }, inverseJoinColumns = {
					@JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo") })

	private Set<Articoli> articoliAssociati;

	public Ordini() {
		articoliAssociati = new HashSet<Articoli>();
	}

	@Transient
	private List<Articoli> articoli;

	public List<Articoli> getArticoli() {
		return articoli;
	}

	public Set<Articoli> getArticoliAssociati() {
		return articoliAssociati;
	}

	public void setArticoliAssociati(Set<Articoli> articoliAssociati) {
		this.articoliAssociati = articoliAssociati;
	}

	public void setArticoli(List<Articoli> articoli) {
		this.articoli = articoli;
	}

	public Integer getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Integer idOrdine) {
		this.idOrdine = idOrdine;
	}

	public Timestamp getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Timestamp dataOrdine) {
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