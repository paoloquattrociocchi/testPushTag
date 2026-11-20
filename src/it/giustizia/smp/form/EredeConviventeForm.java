package it.giustizia.smp.form;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class EredeConviventeForm {

    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String    cognome;
    private ComboForm comuneNascitaErCon;
    private String    comuneNascitaEstero;
    private String    comuneNascitaItaliano;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date      dataNascita;
    // campo usato per visualizzare in tabella il comune di nascita, che sia
    // estero oppure italiano. Il controllo su cosa restituire è effettuato nel
    // metodo GETTER
    private String    descrizioneComuneNascitaItalianoEstero;
    // id progressivo
    private int       id;
    @Pattern(regexp = Constants.RegExp.NOME)
    private String    nome;
    private String    parentela;
    private String    siglaProvinciaNascitaErCon;
    private ComboForm statoNascitaErCon;

    /**
     * @return the cognome
     */
    public String getCognome() {

        return cognome;
    }

    /**
     * @return the comuneNascitaErCon
     */
    public ComboForm getComuneNascitaErCon() {

        return comuneNascitaErCon;
    }

    /**
     * @return the comuneNascitaEstero
     */
    public String getComuneNascitaEstero() {

        return comuneNascitaEstero;
    }

    /**
     * @return the comuneNascitaItaliano
     */
    public String getComuneNascitaItaliano() {

        return comuneNascitaItaliano;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {

        return dataNascita;
    }

    /**
     * @return the descrizioneComuneNascitaItalianoEstero
     */
    public String getDescrizioneComuneNascitaItalianoEstero() {

        if (comuneNascitaItaliano != null) {
            return comuneNascitaItaliano + " (" + siglaProvinciaNascitaErCon + ")";
        } else
            // se è presente, lo stato estero
            if (comuneNascitaEstero != null) {
                return comuneNascitaEstero;
            }
        return "";
    }

    /**
     * @return the id
     */
    public int getId() {

        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {

        return nome;
    }

    /**
     * @return the parentela
     */
    public String getParentela() {

        return parentela;
    }

    /**
     * @return the siglaProvinciaNascitaErCon
     */
    public String getSiglaProvinciaNascitaErCon() {

        return siglaProvinciaNascitaErCon;
    }

    /**
     * @return the statoNascitaErCon
     */
    public ComboForm getStatoNascitaErCon() {

        return statoNascitaErCon;
    }

    /**
     * @param cognome
     *            the cognome to set
     */
    public void setCognome(String cognome) {

        this.cognome = cognome;
    }

    /**
     * @param comuneNascitaErCon
     *            the comuneNascitaErCon to set
     */
    public void setComuneNascitaErCon(ComboForm comuneNascitaErCon) {

        this.comuneNascitaErCon = comuneNascitaErCon;
    }

    /**
     * @param comuneNascitaEstero
     *            the comuneNascitaEstero to set
     */
    public void setComuneNascitaEstero(String comuneNascitaEstero) {

        this.comuneNascitaEstero = comuneNascitaEstero;
    }

    /**
     * @param comuneNascitaItaliano
     *            the comuneNascitaItaliano to set
     */
    public void setComuneNascitaItaliano(String comuneNascitaItaliano) {

        this.comuneNascitaItaliano = comuneNascitaItaliano;
    }

    /**
     * @param dataNascita
     *            the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {

        this.dataNascita = dataNascita;
    }

    /**
     * @param descrizioneComuneNascitaItalianoEstero
     *            the descrizioneComuneNascitaItalianoEstero to set
     */
    public void setDescrizioneComuneNascitaItalianoEstero(String descrizioneComuneNascitaItalianoEstero) {

        this.descrizioneComuneNascitaItalianoEstero = descrizioneComuneNascitaItalianoEstero;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {

        this.nome = nome;
    }

    /**
     * @param parentela
     *            the parentela to set
     */
    public void setParentela(String parentela) {

        this.parentela = parentela;
    }

    /**
     * @param siglaProvinciaNascitaErCon
     *            the siglaProvinciaNascitaErCon to set
     */
    public void setSiglaProvinciaNascitaErCon(String siglaProvinciaNascitaErCon) {

        this.siglaProvinciaNascitaErCon = siglaProvinciaNascitaErCon;
    }

    /**
     * @param statoNascitaErCon
     *            the statoNascitaErCon to set
     */
    public void setStatoNascitaErCon(ComboForm statoNascitaErCon) {

        this.statoNascitaErCon = statoNascitaErCon;
    }
}
