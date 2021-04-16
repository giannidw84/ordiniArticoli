package eu.winwinit.bcc.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrdiniArticoliId  implements Serializable {

	    private Articoli articoli;
	    private Ordini ordini;
	 
	    @ManyToOne(cascade = CascadeType.ALL)
	    public Articoli getArticoli() {
	        return articoli;
	    }
	 
	    public void setArticoli(Articoli articoli) {
	        this.articoli = articoli;
	    }
	 
	    @ManyToOne(cascade = CascadeType.ALL)
	    public Ordini getOrdini() {
	        return ordini;
	    }
	 
	    public void setOrdini(Ordini ordini) {
	        this.ordini = ordini;
	    }

}
