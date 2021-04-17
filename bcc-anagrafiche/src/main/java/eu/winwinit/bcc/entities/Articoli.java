package eu.winwinit.bcc.entities;

import static javax.persistence.GenerationType.IDENTITY;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Transient
	private Integer quantita;

	@OneToMany(mappedBy = "primaryKey.articoli", cascade = CascadeType.ALL)
	private Set<OrdiniArticoli> ordiniArticoli = new HashSet<>();

	@JsonIgnore
	public Set<OrdiniArticoli> getOrdiniArticoli() {
		return ordiniArticoli;
	}

	public void setOrdiniArticoli(Set<OrdiniArticoli> ordiniArticoli) {
		this.ordiniArticoli = ordiniArticoli;
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

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}


}
