package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NumeroRegistroForm {

    private String annoProcedimento;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date   dataIscrizione;
    private String iter;
    private String numeroProcedimento;
    private String procedimento;
    private String sedeUfficio;
    private String tipoUfficio;
    private String ufficio;
    private long idProc2;
    private int idUfficio2;
    private int idTipoUfficio2;

    /**
     * @return the annoProcedimento
     */
    public String getAnnoProcedimento() {

        return annoProcedimento;
    }

    /**
     * @return the dataIscrizione
     */
    public Date getDataIscrizione() {

        return dataIscrizione;
    }

    /**
     * @return the iter
     */
    public String getIter() {

        return iter;
    }

    /**
     * @return the numeroProcedimento
     */
    public String getNumeroProcedimento() {

        return numeroProcedimento;
    }

    /**
     * @return the procedimento
     */
    public String getProcedimento() {

        return annoProcedimento + "/" + numeroProcedimento;
    }

    /**
     * @return the sedeUfficio
     */
    public String getSedeUfficio() {

        return sedeUfficio;
    }

    /**
     * @return the tipoUfficio
     */
    public String getTipoUfficio() {

        return tipoUfficio;
    }

    /**
     * @return the ufficio
     */
    public String getUfficio() {

        return tipoUfficio + " di " + sedeUfficio;
    }

    /**
     * @param annoProcedimento
     *            the annoProcedimento to set
     */
    public void setAnnoProcedimento(String annoProcedimento) {

        this.annoProcedimento = annoProcedimento;
    }

    /**
     * @param dataIscrizione
     *            the dataIscrizione to set
     */
    public void setDataIscrizione(Date dataIscrizione) {

        this.dataIscrizione = dataIscrizione;
    }

    /**
     * @param iter
     *            the iter to set
     */
    public void setIter(String iter) {

        this.iter = iter;
    }

    /**
     * @param numeroProcedimento
     *            the numeroProcedimento to set
     */
    public void setNumeroProcedimento(String numeroProcedimento) {

        this.numeroProcedimento = numeroProcedimento;
    }

    /**
     * @param procedimento
     *            the procedimento to set
     */
    public void setProcedimento(String procedimento) {

        this.procedimento = procedimento;
    }

    /**
     * @param sedeUfficio
     *            the sedeUfficio to set
     */
    public void setSedeUfficio(String sedeUfficio) {

        this.sedeUfficio = sedeUfficio;
    }

    /**
     * @param tipoUfficio
     *            the tipoUfficio to set
     */
    public void setTipoUfficio(String tipoUfficio) {

        this.tipoUfficio = tipoUfficio;
    }

    /**
     * @param ufficio
     *            the ufficio to set
     */
    public void setUfficio(String ufficio) {

        this.ufficio = ufficio;
    }
    
    /**
     * @return the annoNumeroProcedimento
     */
    public String getAnnoNumeroProcedimento() {

        return annoProcedimento + "/" + numeroProcedimento;
    }

	public long getIdProc2() {
		return idProc2;
	}

	public void setIdProc2(long idProc2) {
		this.idProc2 = idProc2;
	}

	public int getIdUfficio2() {
		return idUfficio2;
	}

	public void setIdUfficio2(int idUfficio2) {
		this.idUfficio2 = idUfficio2;
	}

	public int getIdTipoUfficio2() {
		return idTipoUfficio2;
	}

	public void setIdTipoUfficio2(int idTipoUfficio2) {
		this.idTipoUfficio2 = idTipoUfficio2;
	}

}
