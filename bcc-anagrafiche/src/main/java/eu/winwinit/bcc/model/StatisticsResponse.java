package eu.winwinit.bcc.model;

public class StatisticsResponse {
	long totCustomers=0;
	long totConfirmedRecords=0;
	long totChangedRecords=0;
	long totNotConfirmedRecords=0;
	long totEditedPhone=0;
	long totEditedEmail=0;
	long totEditedPrivacy1=0;
	long totEditedPrivacy2=0;
	long totEditedPrivacy3=0;
	long totEditedPrivacy4=0;
	long totEditedPrivacy5=0;
	long totEditedPrivacy6=0;
	long totEditedFirma=0;
	
	
	public StatisticsResponse() {
		super();
	}
	
	public StatisticsResponse(long totCustomers, long totConfirmedRecords, long totChangedRecords,
			long totNotConfirmedRecords, long totEditedPhone, long totEditedEmail, long totEditedPrivacy1,
			long totEditedPrivacy2, long totEditedPrivacy3, long totEditedPrivacy4, long totEditedPrivacy5,
			long totEditedPrivacy6, long totEditedFirma) {
		super();
		this.totCustomers = totCustomers;
		this.totConfirmedRecords = totConfirmedRecords;
		this.totChangedRecords = totChangedRecords;
		this.totNotConfirmedRecords = totNotConfirmedRecords;
		this.totEditedPhone = totEditedPhone;
		this.totEditedEmail = totEditedEmail;
		this.totEditedPrivacy1 = totEditedPrivacy1;
		this.totEditedPrivacy2 = totEditedPrivacy2;
		this.totEditedPrivacy3 = totEditedPrivacy3;
		this.totEditedPrivacy4 = totEditedPrivacy4;
		this.totEditedPrivacy5 = totEditedPrivacy5;
		this.totEditedPrivacy6 = totEditedPrivacy6;
		this.totEditedFirma = totEditedFirma;
	}

	public long getTotCustomers() {
		return totCustomers;
	}
	public void setTotCustomers(long totCustomers) {
		this.totCustomers = totCustomers;
	}
	public long getTotConfirmedRecords() {
		return totConfirmedRecords;
	}
	public void setTotConfirmedRecords(long totConfirmedRecords) {
		this.totConfirmedRecords = totConfirmedRecords;
	}
	public long getTotChangedRecords() {
		return totChangedRecords;
	}
	public void setTotChangedRecords(long totChangedRecords) {
		this.totChangedRecords = totChangedRecords;
	}
	public long getTotNotConfirmedRecords() {
		return totNotConfirmedRecords;
	}
	public void setTotNotConfirmedRecords(long totNotConfirmedRecords) {
		this.totNotConfirmedRecords = totNotConfirmedRecords;
	}
	public long getTotEditedPhone() {
		return totEditedPhone;
	}
	public void setTotEditedPhone(long totEditedPhone) {
		this.totEditedPhone = totEditedPhone;
	}
	public long getTotEditedEmail() {
		return totEditedEmail;
	}
	public void setTotEditedEmail(long totEditedEmail) {
		this.totEditedEmail = totEditedEmail;
	}
	public long getTotEditedPrivacy1() {
		return totEditedPrivacy1;
	}
	public void setTotEditedPrivacy1(long totEditedPrivacy1) {
		this.totEditedPrivacy1 = totEditedPrivacy1;
	}
	public long getTotEditedPrivacy2() {
		return totEditedPrivacy2;
	}
	public void setTotEditedPrivacy2(long totEditedPrivacy2) {
		this.totEditedPrivacy2 = totEditedPrivacy2;
	}
	public long getTotEditedPrivacy3() {
		return totEditedPrivacy3;
	}
	public void setTotEditedPrivacy3(long totEditedPrivacy3) {
		this.totEditedPrivacy3 = totEditedPrivacy3;
	}
	public long getTotEditedPrivacy4() {
		return totEditedPrivacy4;
	}
	public void setTotEditedPrivacy4(long totEditedPrivacy4) {
		this.totEditedPrivacy4 = totEditedPrivacy4;
	}
	public long getTotEditedPrivacy5() {
		return totEditedPrivacy5;
	}
	public void setTotEditedPrivacy5(long totEditedPrivacy5) {
		this.totEditedPrivacy5 = totEditedPrivacy5;
	}
	public long getTotEditedPrivacy6() {
		return totEditedPrivacy6;
	}
	public void setTotEditedPrivacy6(long totEditedPrivacy6) {
		this.totEditedPrivacy6 = totEditedPrivacy6;
	}
	public long getTotEditedFirma() {
		return totEditedFirma;
	}
	public void setTotEditedFirma(long totEditedFirma) {
		this.totEditedFirma = totEditedFirma;
	}
}
