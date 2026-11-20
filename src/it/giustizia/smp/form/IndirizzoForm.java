package it.giustizia.smp.form;

/**
 * IndirizzoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione gli indirizzi
 */
public class IndirizzoForm {

    private String    cap;
    private String    civico;
    private String    denominazione;
    private ComboForm toponimo;

    public String getCap() {

        return cap;
    }

    /**
     * @return the civico
     */
    public String getCivico() {

        return civico;
    }

    /**
     * @return the denominazione
     */
    public String getDenominazione() {

        return denominazione;
    }

    /**
     * @return the toponimo
     */
    public ComboForm getToponimo() {

        return toponimo;
    }

    public void setCap(String cap) {

        this.cap = cap;
    }

    /**
     * @param civico
     *            the civico to set
     */
    public void setCivico(String civico) {

        this.civico = civico;
    }

    /**
     * @param denominazione
     *            the denominazione to set
     */
    public void setDenominazione(String denominazione) {

        this.denominazione = denominazione;
    }

    /**
     * @param toponimo
     *            the toponimo to set
     */
    public void setToponimo(ComboForm toponimo) {

        this.toponimo = toponimo;
    }
}
