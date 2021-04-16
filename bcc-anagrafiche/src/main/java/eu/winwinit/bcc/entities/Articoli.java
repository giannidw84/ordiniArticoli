package eu.winwinit.bcc.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "articoli")
public class Articoli implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_articolo", unique = true, nullable = false)
	private Integer idArticolo;

	@Column(name = "cod_articolo", unique = true)
	private String codArticolo;

	@Column(name = "descrizione_articolo")
	private String descArticolo;

	private String categoria;

	@Column(name = "prezzo", nullable = false)
	private Double prezzo;
    
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "articoliAssociati")
	private Set<Ordini> ordiniAssociati;

	public Articoli() {
		ordiniAssociati = new HashSet<Ordini>();
	}

	public Set<Ordini> getOrdiniAssociati() {
		return ordiniAssociati;
	}

	public void setOrdiniAssociati(Set<Ordini> ordiniAssociati) {
		this.ordiniAssociati = ordiniAssociati;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
	}

	public String getCodArticolo() {
		return codArticolo;
	}

	public void setCodArticolo(String codArticolo) {
		this.codArticolo = codArticolo;
	}

	public String getDescArticolo() {
		return descArticolo;
	}

	public void setDescArticolo(String descArticolo) {
		this.descArticolo = descArticolo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

}
