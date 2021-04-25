package eu.winwinit.bcc.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrdiniArticoliId  implements Serializable {

	    private Articolo articoli;
	    private Ordine ordini;
	 
	    @ManyToOne(cascade = CascadeType.ALL)
	    public Articolo getArticoli() {
	        return articoli;
	    }
	 
	    public void setArticoli(Articolo articoli) {
	        this.articoli = articoli;
	    }
	 
	    @ManyToOne(cascade = CascadeType.ALL)
	    public Ordine getOrdini() {
	        return ordini;
	    }
	 
	    public void setOrdini(Ordine ordini) {
	        this.ordini = ordini;
	    }

}
