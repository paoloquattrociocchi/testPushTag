package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

public class DifensoreSoggettoForm {

	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataNomina;
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataRevoca;
    private String        descTipoNomina;
    @Valid
    private DifensoreForm difensoreForm;
    private Boolean       gratuitoPatrocinioCaricato;
    @Valid
    private SoggettoForm  soggettoForm;
    private ComboForm     tipoNomina;

    /**
     * @return the dataNomina
     */
    public Date getDataNomina() {

        return dataNomina;
    }

    /**
     * @return the dataRevoca
     */
    public Date getDataRevoca() {

        return dataRevoca;
    }

    /**
     * @return the descTipoNomina
     */
    public String getDescTipoNomina() {

        if ((tipoNomina != null) && (tipoNomina.getId() != null)) {
            if (tipoNomina.getId().equals("U")) {
                descTipoNomina = "D' ufficio";
            } else {
                descTipoNomina = "Di fiducia";
            }
        }
        return descTipoNomina;
    }

    /**
     * @return the difensoreForm
     */
    public DifensoreForm getDifensoreForm() {

        return difensoreForm;
    }

    /**
     * @return the gratuitoPatrocinioCaricato
     */
    public Boolean getGratuitoPatrocinioCaricato() {

        return gratuitoPatrocinioCaricato;
    }

    /**
     * @return the soggettoForm
     */
    public SoggettoForm getSoggettoForm() {

        return soggettoForm;
    }

    /**
     * @return the tipoNomina
     */
    public ComboForm getTipoNomina() {

        return tipoNomina;
    }

    /**
     * @param dataNomina
     *            the dataNomina to set
     */
    public void setDataNomina(Date dataNomina) {

        this.dataNomina = dataNomina;
    }

    /**
     * @param dataRevoca
     *            the dataRevoca to set
     */
    public void setDataRevoca(Date dataRevoca) {

        this.dataRevoca = dataRevoca;
    }

    /**
     * @param descTipoNomina
     *            the descTipoNomina to set
     */
    public void setDescTipoNomina(String descTipoNomina) {

        this.descTipoNomina = descTipoNomina;
    }

    /**
     * @param difensoreForm
     *            the difensoreForm to set
     */
    public void setDifensoreForm(DifensoreForm difensoreForm) {

        this.difensoreForm = difensoreForm;
    }

    /**
     * @param gratuitoPatrocinioCaricato
     *            the gratuitoPatrocinioCaricato to set
     */
    public void setGratuitoPatrocinioCaricato(Boolean gratuitoPatrocinioCaricato) {

        this.gratuitoPatrocinioCaricato = gratuitoPatrocinioCaricato;
    }

    /**
     * @param soggettoForm
     *            the soggettoForm to set
     */
    public void setSoggettoForm(SoggettoForm soggettoForm) {

        this.soggettoForm = soggettoForm;
    }

    /**
     * @param tipoNomina
     *            the tipoNomina to set
     */
    public void setTipoNomina(ComboForm tipoNomina) {

        this.tipoNomina = tipoNomina;
    }
}
