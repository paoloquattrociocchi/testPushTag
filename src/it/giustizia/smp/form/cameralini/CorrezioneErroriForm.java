package it.giustizia.smp.form.cameralini;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.utility.Constants;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Almaviva S.p.A.
 *         classe per la gestione 
 */
public class CorrezioneErroriForm extends BaseForm {

	
	private List<SoggettoForm>  			listaSoggetti;
	private String 						 	soggettoSelected;  		// concatena i valori idSoggetto^tipoPersona^idVersione di SoggettoForm
	private String 							tipoSoggetto; 
	private int  							tipoUfficio;
	
	//DATI PERSONA FISICA
    	@Pattern(regexp = Constants.RegExp.NOME)
    	private String                    nome;
    	@Pattern(regexp = Constants.RegExp.COGNOME)
    	private String                    cognome;
    
		private String                    	codiceFiscale;
		private String                    	sesso;
		@DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date                      	dataNascita;
	    private ComboForm                   statoNascita;
	    private ComboForm                   comuneNascita;
		private String                    	descrizioneComuneEsteroNascita;

	//DATI PERSONA GIURIDICA
		private String                    	partitaIva;
		private String                    	ragioneSociale;
	    private ComboForm                   statoSedeLegale;
	    private ComboForm                   comuneSedeLegale;    
	    private String                    	statoSede;
	    private String 				  	    comuneSede;
		private String					    partitaIvaSiglaStato;
	    private String					    partitaIvaCompleta;
	    private String						descrizioneComuneEsteroSedeLegale;
	    
	    private IndirizzoForm             indirizzoSedeLegale;
	    
	    
		public List<SoggettoForm> getListaSoggetti()
		{
			return listaSoggetti;
		}
		public void setListaSoggetti(List<SoggettoForm> listaSoggetti)
		{
			this.listaSoggetti = listaSoggetti;
		}
		public String getSoggettoSelected()
		{
			return soggettoSelected;
		}
		public void setSoggettoSelected(String soggettoSelected)
		{
			this.soggettoSelected = soggettoSelected;
		}
		public String getTipoSoggetto()
		{
			return tipoSoggetto;
		}
		public void setTipoSoggetto(String tipoSoggetto)
		{
			this.tipoSoggetto = tipoSoggetto;
		}
		
		public String getCodiceFiscale()
		{
			return codiceFiscale;
		}
		public void setCodiceFiscale(String codiceFiscale)
		{
			this.codiceFiscale = codiceFiscale;
		}
		public String getSesso()
		{
			return sesso;
		}
		public void setSesso(String sesso)
		{
			this.sesso = sesso;
		}
		
		public Date getDataNascita()
		{
			return dataNascita;
		}
		public void setDataNascita(Date dataNascita)
		{
			this.dataNascita = dataNascita;
		}
		public ComboForm getStatoNascita()
		{
			return statoNascita;
		}
		public void setStatoNascita(ComboForm statoNascita)
		{
			this.statoNascita = statoNascita;
		}
		public ComboForm getComuneNascita()
		{
			return comuneNascita;
		}
		public void setComuneNascita(ComboForm comuneNascita)
		{
			this.comuneNascita = comuneNascita;
		}
		public String getDescrizioneComuneEsteroNascita()
		{
			return descrizioneComuneEsteroNascita;
		}
		public void setDescrizioneComuneEsteroNascita(String descrizioneComuneEsteroNascita)
		{
			this.descrizioneComuneEsteroNascita = descrizioneComuneEsteroNascita;
		}
		public String getPartitaIva()
		{
			return partitaIva;
		}
		public void setPartitaIva(String partitaIva)
		{
			this.partitaIva = partitaIva;
		}
		public String getRagioneSociale()
		{
			return ragioneSociale;
		}
		public void setRagioneSociale(String ragioneSociale)
		{
			this.ragioneSociale = ragioneSociale;
		}
		public ComboForm getStatoSedeLegale()
		{
			return statoSedeLegale;
		}
		public void setStatoSedeLegale(ComboForm statoSedeLegale)
		{
			this.statoSedeLegale = statoSedeLegale;
		}
		public ComboForm getComuneSedeLegale()
		{
			return comuneSedeLegale;
		}
		public void setComuneSedeLegale(ComboForm comuneSedeLegale)
		{
			this.comuneSedeLegale = comuneSedeLegale;
		}
		public String getStatoSede()
		{
			return statoSede;
		}
		public void setStatoSede(String statoSede)
		{
			this.statoSede = statoSede;
		}
		public String getComuneSede()
		{
			return comuneSede;
		}
		public void setComuneSede(String comuneSede)
		{
			this.comuneSede = comuneSede;
		}
		public String getPartitaIvaSiglaStato()
		{
			return partitaIvaSiglaStato;
		}
		public void setPartitaIvaSiglaStato(String partitaIvaSiglaStato)
		{
			this.partitaIvaSiglaStato = partitaIvaSiglaStato;
		}
		public String getPartitaIvaCompleta()
		{
			return partitaIvaCompleta;
		}
		public void setPartitaIvaCompleta(String partitaIvaCompleta)
		{
			this.partitaIvaCompleta = partitaIvaCompleta;
		}
		public int getTipoUfficio()
		{
			return tipoUfficio;
		}
		public void setTipoUfficio(int tipoUfficio)
		{
			this.tipoUfficio = tipoUfficio;
		}
		public String getNome()
		{
			return nome;
		}
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		public String getCognome()
		{
			return cognome;
		}
		public void setCognome(String cognome)
		{
			this.cognome = cognome;
		}
		public IndirizzoForm getIndirizzoSedeLegale() {
			return indirizzoSedeLegale;
		}
		public void setIndirizzoSedeLegale(IndirizzoForm indirizzoSedeLegale) {
			this.indirizzoSedeLegale = indirizzoSedeLegale;
		}
		public String getDescrizioneComuneEsteroSedeLegale() {
			return descrizioneComuneEsteroSedeLegale;
		}
		public void setDescrizioneComuneEsteroSedeLegale(
				String descrizioneComuneEsteroSedeLegale) {
			this.descrizioneComuneEsteroSedeLegale = descrizioneComuneEsteroSedeLegale;
		}
					
	    
	    
}
	