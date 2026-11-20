package it.giustizia.smp.form;

import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class AmministratoreForm extends ProcedimentoForm {

	private Long	id;
	@Pattern(regexp = Constants.RegExp.COGNOME)
    private String      cognome;
    @Pattern(regexp = Constants.RegExp.NOME)
    private String      nome;
    @ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataInizioIncarico;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataFineIncarico;
	private String      codiceFiscale;
	private String      partitaIva;  
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date		dataNascita;
	private String   	luogoNascita;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataIscrizioneAlbo;
	private String 	descrIscrizioneAlbo;
	private String 	statoIscrizioneAlbo;
	private String  comuneDomicilioLegale;
	private String  statoDomicilioLegale;
	private String  comuneEsteroLegale;
	private String  descStudio;
	private String  sezione;
	private String  tel;
	private String  fax;
	private String  pec;
	private ComboForm	alboProfessionale;
	private ComboForm	ambitoTerritoriale;
	private ComboForm	statoDomProf;
	private ComboForm	provinciaDomProf;
	private ComboForm   comuneDomProf;
	
	private String jspProvenienza;
	private String dettaglio;
	
	private boolean       visualizzaFiltro;
	
	public AmministratoreForm() {

	}

	public AmministratoreForm(TAnMagistrati vo) {

		id = vo.getCodiKIdMagistrato();
		cognome = vo.getDescCognomeMag();
		nome = vo.getDescNomeMag();
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
		AmministratoreForm other = (AmministratoreForm) obj;
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
	
   
    public String getCognome() {

		return cognome;
	}

	public String getGeneralita() {

		return getCognome() + " " + getNome();
	}

	public Long getId() {

		return id;
	}

	
	public String getNome() {

		return nome;
	}

	
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
    public void setCognome(String cognome) {

		this.cognome = cognome;
	}

	
	public void setId(Long id) {

		this.id = id;
	}

		public void setNome(String nome) {

		this.nome = nome;
	}

		public Date getDataInizioIncarico() {
			return dataInizioIncarico;
		}

		public void setDataInizioIncarico(Date dataInizioIncarico) {
			this.dataInizioIncarico = dataInizioIncarico;
		}

		public Date getDataFineIncarico() {
			return dataFineIncarico;
		}

		public void setDataFineIncarico(Date dataFineIncarico) {
			this.dataFineIncarico = dataFineIncarico;
		}

		public String getCodiceFiscale() {
			return codiceFiscale;
		}

		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}

		public String getPartitaIva() {
			return partitaIva;
		}

		public void setPartitaIva(String partitaIva) {
			this.partitaIva = partitaIva;
		}
		
		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getLuogoNascita() {
			return luogoNascita;
		}

		public void setLuogoNascita(String luogoNascita) {
			this.luogoNascita = luogoNascita;
		}

		public Date getDataIscrizioneAlbo() {
			return dataIscrizioneAlbo;
		}

		public void setDataIscrizioneAlbo(Date dataIscrizioneAlbo) {
			this.dataIscrizioneAlbo = dataIscrizioneAlbo;
		}

		public String getDescrIscrizioneAlbo() {
			return descrIscrizioneAlbo;
		}

		public void setDescrIscrizioneAlbo(String descrIscrizioneAlbo) {
			this.descrIscrizioneAlbo = descrIscrizioneAlbo;
		}

		public String getComuneDomicilioLegale() {
			return comuneDomicilioLegale;
		}

		public void setComuneDomicilioLegale(String comuneDomicilioLegale) {
			this.comuneDomicilioLegale = comuneDomicilioLegale;
		}

		public String getStatoDomicilioLegale() {
			return statoDomicilioLegale;
		}

		public void setStatoDomicilioLegale(String statoDomicilioLegale) {
			this.statoDomicilioLegale = statoDomicilioLegale;
		}

		public String getDescStudio() {
			return descStudio;
		}

		public void setDescStudio(String descStudio) {
			this.descStudio = descStudio;
		}

		public ComboForm getAlboProfessionale() {
			return alboProfessionale;
		}

		public void setAlboProfessionale(ComboForm alboProfessionale) {
			this.alboProfessionale = alboProfessionale;
		}

		public ComboForm getAmbitoTerritoriale() {
			return ambitoTerritoriale;
		}

		public void setAmbitoTerritoriale(ComboForm ambitoTerritoriale) {
			this.ambitoTerritoriale = ambitoTerritoriale;
		}

		public ComboForm getProvinciaDomProf() {
			return provinciaDomProf;
		}

		public void setProvinciaDomProf(ComboForm provinciaDomProf) {
			this.provinciaDomProf = provinciaDomProf;
		}

		public ComboForm getComuneDomProf() {
			return comuneDomProf;
		}

		public void setComuneDomProf(ComboForm comuneDomProf) {
			this.comuneDomProf = comuneDomProf;
		}

		public String getSezione() {
			return sezione;
		}

		public void setSezione(String sezione) {
			this.sezione = sezione;
		}

		public ComboForm getStatoDomProf() {
			return statoDomProf;
		}

		public void setStatoDomProf(ComboForm statoDomProf) {
			this.statoDomProf = statoDomProf;
		}

		public String getComuneEsteroLegale() {
			return comuneEsteroLegale;
		}

		public void setComuneEsteroLegale(String comuneEsteroLegale) {
			this.comuneEsteroLegale = comuneEsteroLegale;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getPec() {
			return pec;
		}

		public void setPec(String pec) {
			this.pec = pec;
		}

		public String getstatoIscrizioneAlbo() {
			return statoIscrizioneAlbo;
		}

		public void setstatoIscrizioneAlbo(String statoIscrizioneAlbo) {
			this.statoIscrizioneAlbo = statoIscrizioneAlbo;
		}

		public String getStatoIscrizioneAlbo() {
			return statoIscrizioneAlbo;
		}

		public void setStatoIscrizioneAlbo(String statoIscrizioneAlbo) {
			this.statoIscrizioneAlbo = statoIscrizioneAlbo;
		}

		public String getJspProvenienza() {
			return jspProvenienza;
		}

		public void setJspProvenienza(String jspProvenienza) {
			this.jspProvenienza = jspProvenienza;
		}

		public boolean isVisualizzaFiltro() {
			return visualizzaFiltro;
		}

		public void setVisualizzaFiltro(boolean visualizzaFiltro) {
			this.visualizzaFiltro = visualizzaFiltro;
		}

		public String getDettaglio() {
			return dettaglio;
		}

		public void setDettaglio(String dettaglio) {
			this.dettaglio = dettaglio;
		}
		
		public String getDescrizioneAmministratore() {

            StringBuilder descrizione = new StringBuilder();
            if (cognome != null) {
                descrizione.append(cognome);
            }
            if (nome != null) {
                descrizione.append(" ");
                descrizione.append(nome);
            }
            if (codiceFiscale != null) {
                descrizione.append(" CF ");
                descrizione.append(codiceFiscale);
            }
            if (descrIscrizioneAlbo != null) {
                descrizione.append(" Ordine prof. ");
                descrizione.append(descrIscrizioneAlbo);
            }
            if (comuneDomicilioLegale != null) {
                descrizione.append(" Domicilio legale ");
                descrizione.append(comuneDomicilioLegale);
            }
            return descrizione.toString();
        }

		
}
