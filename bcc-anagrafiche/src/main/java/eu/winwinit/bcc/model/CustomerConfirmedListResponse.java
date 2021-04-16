package eu.winwinit.bcc.model;

import java.util.Date;

public class CustomerConfirmedListResponse {

	private Integer id;
	private String nag;
	private String codice;
	private Date data;
	private String filiale;
	private Boolean telefonoEdited = false;
	private Boolean emailEdited = false;
	private Boolean p1Edited = false;
	private Boolean p2Edited = false;
	private Boolean p3Edited = false;
	private Boolean p4Edited = false;
	private Boolean p5Edited = false;
	private Boolean p6Edited = false;
	private Boolean firmaEdited = false;
	private String editedFieldsSummary;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNag() {
		return nag;
	}
	
	public void setNag(String nag) {
		this.nag = nag;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getFiliale() {
		return filiale;
	}
	
	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}
	
	public Boolean getTelefonoEdited() {
		return telefonoEdited;
	}
	
	public void setTelefonoEdited(Boolean telefonoEdited) {
		this.telefonoEdited = telefonoEdited;
	}
	
	public Boolean getEmailEdited() {
		return emailEdited;
	}
	
	public void setEmailEdited(Boolean emailEdited) {
		this.emailEdited = emailEdited;
	}
	
	public Boolean getP1Edited() {
		return p1Edited;
	}
	
	public void setP1Edited(Boolean p1Edited) {
		this.p1Edited = p1Edited;
	}
	
	public Boolean getP2Edited() {
		return p2Edited;
	}
	
	public void setP2Edited(Boolean p2Edited) {
		this.p2Edited = p2Edited;
	}
	
	public Boolean getP3Edited() {
		return p3Edited;
	}
	
	public void setP3Edited(Boolean p3Edited) {
		this.p3Edited = p3Edited;
	}
	
	public Boolean getP4Edited() {
		return p4Edited;
	}
	
	public void setP4Edited(Boolean p4Edited) {
		this.p4Edited = p4Edited;
	}
	
	public Boolean getP5Edited() {
		return p5Edited;
	}
	
	public void setP5Edited(Boolean p5Edited) {
		this.p5Edited = p5Edited;
	}
	
	public Boolean getP6Edited() {
		return p6Edited;
	}
	
	public void setP6Edited(Boolean p6Edited) {
		this.p6Edited = p6Edited;
	}
	
	public Boolean getFirmaEdited() {
		return firmaEdited;
	}

	public void setFirmaEdited(Boolean firmaEdited) {
		this.firmaEdited = firmaEdited;
	}

	public String getEditedFieldsSummary() {
		return editedFieldsSummary;
	}

	public void setEditedFieldsSummary(String editedFieldsSummary) {
		this.editedFieldsSummary = editedFieldsSummary;
	}
	
}
