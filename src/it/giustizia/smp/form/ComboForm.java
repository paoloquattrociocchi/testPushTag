package it.giustizia.smp.form;

import java.io.Serializable;

/**
 * ComboForm
 * 
 * @author Almaviva S.p.A.
 *         Classe Form a cui fanno riferimento tutte le combo
 */
public class ComboForm implements Serializable {

    private String descrizione;

    private String id;

    /**
     * costruttore di default
     */
    public ComboForm() {

        super();
    }
    /**
     * @param id
     * @param descrizione
     */
    public ComboForm(String id, String descrizione) {

        super();
        this.id = id;
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComboForm other = (ComboForm) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else
            if (!id.equals(other.id)) {
                return false;
            }
        return true;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {

        return descrizione;
    }

    /**
     * @return the id
     */
    public String getId() {

        return id;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * @param descrizione
     *            the descrizione to set
     */
    public void setDescrizione(String descrizione) {

        this.descrizione = descrizione;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }
}
