package eu.winwinit.bcc.utility;

public class VariazioneGroupedInfo {
	
	private String campo;
	private Long tot;
	
	public VariazioneGroupedInfo(String campo, Long tot) {
		super();
		this.campo = campo;
		this.tot = tot;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Long getTot() {
		return tot;
	}

	public void setTot(Long tot) {
		this.tot = tot;
	}
	
	
}
