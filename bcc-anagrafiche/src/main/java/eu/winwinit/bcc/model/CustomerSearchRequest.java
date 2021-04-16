package eu.winwinit.bcc.model;

import java.util.Date;

public class CustomerSearchRequest {
	
	Integer idFiliale;
	String nag;
	String nome;
	Date dataNascita;
	public Integer getIdFiliale() {
		return idFiliale;
	}
	public void setIdFiliale(Integer idFiliale) {
		this.idFiliale = idFiliale;
	}
	public String getNag() {
		return nag;
	}
	public void setNag(String nag) {
		this.nag = nag;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
}
