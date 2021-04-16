package eu.winwinit.bcc.utility;

public class ClienteGroupedInfo {
	
	private Boolean confermato;
	private Long tot;
	
	public ClienteGroupedInfo(Boolean confermato, Long tot) {
		super();
		this.confermato = confermato;
		this.tot = tot;
	}

	public Boolean getConfermato() {
		return confermato;
	}

	public void setConfermato(Boolean confermato) {
		this.confermato = confermato;
	}

	public Long getTot() {
		return tot;
	}

	public void setTot(Long tot) {
		this.tot = tot;
	}
	
}
