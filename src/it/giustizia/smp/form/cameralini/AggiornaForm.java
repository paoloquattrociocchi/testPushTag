package it.giustizia.smp.form.cameralini;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.validation.date.ModernDate;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class AggiornaForm
{
    private String                          funzioneCameralini;     // parametrizza la funzione richiamata dal menu 

    ProcedimentoForm  procedPadre;
    ProcedimentoForm  procedCamerale;

    private Long      idProcedimento;
    private ComboForm comboStatoProcedimento;
    private String    cognomeSoggetto;
    private String    nomeSoggetto;
    private String    ragioneSociale;
    private String    magistrato;
    @ModernDate
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
    private Date      dataIscrizioneMax;
    @ModernDate
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
    private Date      dataIscrizioneMin;
    @NumberFormat( style = Style.NUMBER, pattern = "####" )
    private Long      numeroMax;
    @NumberFormat( style = Style.NUMBER, pattern = "####" )
    private Long      numeroMin;
    
    
    public ProcedimentoForm getProcedPadre()
    {
        return procedPadre;
    }
    public void setProcedPadre( ProcedimentoForm procedPadre )
    {
        this.procedPadre = procedPadre;
    }
    public ProcedimentoForm getProcedCamerale()
    {
        return procedCamerale;
    }
    public void setProcedCamerale( ProcedimentoForm procedCamerale )
    {
        this.procedCamerale = procedCamerale;
    }
    public ComboForm getComboStatoProcedimento()
    {
        return comboStatoProcedimento;
    }
    public void setComboStatoProcedimento( ComboForm comboStatoProcedimento )
    {
        this.comboStatoProcedimento = comboStatoProcedimento;
    }
    public String getCognomeSoggetto()
    {
        return cognomeSoggetto;
    }
    public void setCognomeSoggetto( String cognomeSoggetto )
    {
        this.cognomeSoggetto = cognomeSoggetto;
    }
    public String getNomeSoggetto()
    {
        return nomeSoggetto;
    }
    public void setNomeSoggetto( String nomeSoggetto )
    {
        this.nomeSoggetto = nomeSoggetto;
    }
    public String getRagioneSociale()
    {
        return ragioneSociale;
    }
    public void setRagioneSociale( String ragioneSociale )
    {
        this.ragioneSociale = ragioneSociale;
    }
    public String getMagistrato()
    {
        return magistrato;
    }
    public void setMagistrato( String magistrato )
    {
        this.magistrato = magistrato;
    }
    public Date getDataIscrizioneMax()
    {
        return dataIscrizioneMax;
    }
    public void setDataIscrizioneMax( Date dataIscrizioneMax )
    {
        this.dataIscrizioneMax = dataIscrizioneMax;
    }
    public Date getDataIscrizioneMin()
    {
        return dataIscrizioneMin;
    }
    public void setDataIscrizioneMin( Date dataIscrizioneMin )
    {
        this.dataIscrizioneMin = dataIscrizioneMin;
    }
    public Long getNumeroMax()
    {
        return numeroMax;
    }
    public void setNumeroMax( Long numeroMax )
    {
        this.numeroMax = numeroMax;
    }
    public Long getNumeroMin()
    {
        return numeroMin;
    }
    public void setNumeroMin( Long numeroMin )
    {
        this.numeroMin = numeroMin;
    }
    public String getFunzioneCameralini()
    {
        return funzioneCameralini;
    }
    public void setFunzioneCameralini( String funzioneCameralini )
    {
        this.funzioneCameralini = funzioneCameralini;
    }
    public Long getIdProcedimento()
    {
        return idProcedimento;
    }
    public void setIdProcedimento( Long idProcedimento )
    {
        this.idProcedimento = idProcedimento;
    }

}
