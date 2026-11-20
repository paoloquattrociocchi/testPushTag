package it.giustizia.smp.form.cameralini;

import java.util.Date;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.utility.Constants;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author Almaviva S.p.A. classe per inserimento dei soggetti Terzi
 */
public class TerzoIntervenienteForm extends BaseForm {

    @Pattern(regexp = Constants.RegExp.NOME)
    private String                    nome;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    cognome;
    
    // PERSONA FISICA
    private String                    nomeLabel;
    private String                    cognomeLabel;
    private String                    codiceFiscale;
    private String                    codiceFiscaleLabel;
    private String                    sesso;
    private String                    descrSesso;
    private String                    descrSessoLabel;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataNascita;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataNascitaLabel;
    
    private ComboForm                 statoNascita;
    private ComboForm                 comuneNascita;
    private String                    descrizioneComuneEsteroNascita;
    
    // PERSONA GIURIDICA
    private String                    ragioneSociale;
    private String                    ragioneSocialeLabel;
    private String                    partitaIva;
    private String					  partitaIvaSiglaStato;
    private String					  partitaIvaCompleta;
    private String					  partitaIvaCompletaLabel;
    private ComboForm                 statoSedeLegale;
    private ComboForm                 comuneSedeLegale;

    private String                    tipoSocieta;
    private long                      idTipoSocieta;
    private String                    numeroCCIAA;
    private IndirizzoForm             indirizzoSedeLegale;
   
    private SoggettoForm 			  soggettoForm;
    private String					  tipoSoggetto;
    private String					  soggettoSel;
    
    private String                    descTipoStrada;
    private String                    descIndirizzo;
    private String                    descCivico;
    private String                    descStatoSedeLegale;
    private String                    descComuneSedeLegale;
    
   
	public String getSoggettoSel() {
		return soggettoSel;
	}
	public void setSoggettoSel(String soggettoSel) {
		this.soggettoSel = soggettoSel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public void setDescrSesso(String descrSesso) {
		this.descrSesso = descrSesso;
	}
	public String getDescrSesso() {
        if ((sesso != null) && sesso.equalsIgnoreCase("m")) {
            descrSesso = "MASCHIO";
        } else
            if ((sesso != null) && sesso.equalsIgnoreCase("f")) {
                descrSesso = "FEMMINA";
            }
        return descrSesso;
    }
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public ComboForm getStatoNascita() {
		return statoNascita;
	}
	public void setStatoNascita(ComboForm statoNascita) {
		this.statoNascita = statoNascita;
	}
	public ComboForm getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(ComboForm comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getPartitaIvaCompleta() {
		partitaIvaSiglaStato=partitaIvaSiglaStato==null?"":partitaIvaSiglaStato;
		partitaIva=partitaIva==null?"":partitaIva;
		partitaIvaCompleta = partitaIvaSiglaStato+partitaIva;
		if(partitaIva!=null && partitaIva.trim().length()<3){
			partitaIvaCompleta="";
		}
		return partitaIvaCompleta;
	}

	public void setPartitaIvaCompleta(String partitaIvaCompleta) {
		this.partitaIvaCompleta = partitaIvaCompleta;
		if(partitaIvaCompleta!=null && partitaIvaCompleta.trim().length()>2){
			this.partitaIvaSiglaStato =partitaIvaCompleta.substring(0, 2);
			this.partitaIva = partitaIvaCompleta.substring(2);
		}	
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getPartitaIvaSiglaStato() {
		return partitaIvaSiglaStato;
	}
	public void setPartitaIvaSiglaStato(String partitaIvaSiglaStato) {
		this.partitaIvaSiglaStato = partitaIvaSiglaStato;
	}
	public ComboForm getStatoSedeLegale() {
		return statoSedeLegale;
	}
	public void setStatoSedeLegale(ComboForm statoSedeLegale) {
		this.statoSedeLegale = statoSedeLegale;
	}
	public ComboForm getComuneSedeLegale() {
		return comuneSedeLegale;
	}
	public void setComuneSedeLegale(ComboForm comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}
	public String getDescrizioneComuneEsteroNascita() {
		return descrizioneComuneEsteroNascita;
	}
	public void setDescrizioneComuneEsteroNascita(
			String descrizioneComuneEsteroNascita) {
		this.descrizioneComuneEsteroNascita = descrizioneComuneEsteroNascita;
	}
	public String getTipoSocieta() {
		return tipoSocieta;
	}
	public void setTipoSocieta(String tipoSocieta) {
		this.tipoSocieta = tipoSocieta;
	}
	public long getIdTipoSocieta() {
		return idTipoSocieta;
	}
	public void setIdTipoSocieta(long idTipoSocieta) {
		this.idTipoSocieta = idTipoSocieta;
	}
	public String getNumeroCCIAA() {
		return numeroCCIAA;
	}
	public void setNumeroCCIAA(String numeroCCIAA) {
		this.numeroCCIAA = numeroCCIAA;
	}
	
	public IndirizzoForm getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}
	public void setIndirizzoSedeLegale(IndirizzoForm indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}
	
	public String getDescTipoStrada() {
		return descTipoStrada;
	}
	public void setDescTipoStrada(String descTipoStrada) {
		this.descTipoStrada = descTipoStrada;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getDescCivico() {
		return descCivico;
	}
	public void setDescCivico(String descCivico) {
		this.descCivico = descCivico;
	}
	
	public String getDescStatoSedeLegale() {
		return descStatoSedeLegale;
	}
	public void setDescStatoSedeLegale(String descStatoSedeLegale) {
		this.descStatoSedeLegale = descStatoSedeLegale;
	}
	public String getDescComuneSedeLegale() {
		return descComuneSedeLegale;
	}
	public void setDescComuneSedeLegale(String descComuneSedeLegale) {
		this.descComuneSedeLegale = descComuneSedeLegale;
	}
	public String getNomeLabel() {
		return nomeLabel;
	}
	public void setNomeLabel(String nomeLabel) {
		this.nomeLabel = nomeLabel;
	}
	public String getCognomeLabel() {
		return cognomeLabel;
	}
	public void setCognomeLabel(String cognomeLabel) {
		this.cognomeLabel = cognomeLabel;
	}
	public String getCodiceFiscaleLabel() {
		return codiceFiscaleLabel;
	}
	public void setCodiceFiscaleLabel(String codiceFiscaleLabel) {
		this.codiceFiscaleLabel = codiceFiscaleLabel;
	}
	
	public String getDescrSessoLabel() {
		return descrSessoLabel;
	}
	public void setDescrSessoLabel(String descrSessoLabel) {
		this.descrSessoLabel = descrSessoLabel;
	}
	public Date getDataNascitaLabel() {
		return dataNascitaLabel;
	}
	public void setDataNascitaLabel(Date dataNascitaLabel) {
		this.dataNascitaLabel = dataNascitaLabel;
	}
	public String getRagioneSocialeLabel() {
		return ragioneSocialeLabel;
	}
	public void setRagioneSocialeLabel(String ragioneSocialeLabel) {
		this.ragioneSocialeLabel = ragioneSocialeLabel;
	}
	public String getPartitaIvaCompletaLabel() {
		return partitaIvaCompletaLabel;
	}
	public void setPartitaIvaCompletaLabel(String partitaIvaCompletaLabel) {
		this.partitaIvaCompletaLabel = partitaIvaCompletaLabel;
	}
	@SuppressWarnings("unused")
	public void resetForm (){
	    this.nome = null;
	    this.cognome = null;
	    this.codiceFiscale = null;
	    this.sesso = null;
	    this.descrSesso = null;
	    this.dataNascita = null;
	    this.statoNascita = null;
	    this.comuneNascita = null;
	    this.descrizioneComuneEsteroNascita = null;
	    this.ragioneSociale = null;
	    this.partitaIva = null;
	    this.partitaIvaSiglaStato = null;
	    this.partitaIvaCompleta = null;
	    this.statoSedeLegale = null;
	    this.comuneSedeLegale = null;
	}
	public SoggettoForm getSoggettoForm() {
		return soggettoForm;
	}
	public void setSoggettoForm(SoggettoForm soggettoForm) {
		this.soggettoForm = soggettoForm;
	}
}
