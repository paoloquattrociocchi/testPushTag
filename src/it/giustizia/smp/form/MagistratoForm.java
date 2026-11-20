package it.giustizia.smp.form;

import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MagistratoForm {

	private String  altroMotivo;
	private String	cognome;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataAssegnazioneFascicolo;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataFineAssegnazione;
	private Long	id;
	private String	idMagString;
	private String	nome;
	private Byte	ordineDiVisualizzazione;
	private String	relatore;
	private String	relatoreAnteModifica;
	private ComboForm	ruolo;
	private String	ruoloAnteModifica;
	private boolean	selected;
	private String	tipoSostituzione;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date		dataNascita;
	private Integer 	numeMatricola;
	private Character	onorario;
	
	//sostituzione magistrati
	private String 		tipoMagistrato;
	private ComboForm	magistratoDaSostituire;
	private ComboForm	magistratoSubentrante;
	private ComboForm	motivazioneSostituzione;
	
	private String checkProcedimenti;
	
	private Long procedimentoDaNum;
	private Short procedimentoDaAnno;
	
	private Long procedimentoANum;
	private Short procedimentoAAnno;
	
    public Long getProcedimentoDaNum() {
		return procedimentoDaNum;
	}

	public void setProcedimentoDaNum(Long procedimentoDaNum) {
		this.procedimentoDaNum = procedimentoDaNum;
	}

	public Short getProcedimentoDaAnno() {
		return procedimentoDaAnno;
	}

	public void setProcedimentoDaAnno(Short procedimentoDaAnno) {
		this.procedimentoDaAnno = procedimentoDaAnno;
	}

	public Long getProcedimentoANum() {
		return procedimentoANum;
	}

	public void setProcedimentoANum(Long procedimentoANum) {
		this.procedimentoANum = procedimentoANum;
	}

	public Short getProcedimentoAAnno() {
		return procedimentoAAnno;
	}

	public void setProcedimentoAAnno(Short procedimentoAAnno) {
		this.procedimentoAAnno = procedimentoAAnno;
	}

	public String getCheckProcedimenti() {
		return checkProcedimenti;
	}

	public void setCheckProcedimenti(String checkProcedimenti) {
		this.checkProcedimenti = checkProcedimenti;
	}

	public ComboForm getMagistratoDaSostituire() {
		return magistratoDaSostituire;
	}

	public void setMagistratoDaSostituire(ComboForm magistratoDaSostituire) {
		this.magistratoDaSostituire = magistratoDaSostituire;
	}

	public ComboForm getMagistratoSubentrante() {
		return magistratoSubentrante;
	}

	public void setMagistratoSubentrante(ComboForm magistratoSubentrante) {
		this.magistratoSubentrante = magistratoSubentrante;
	}

	public ComboForm getMotivazioneSostituzione() {
		return motivazioneSostituzione;
	}

	public void setMotivazioneSostituzione(ComboForm motivazioneSostituzione) {
		this.motivazioneSostituzione = motivazioneSostituzione;
	}

	public MagistratoForm() {

	}

	public MagistratoForm(TAnMagistrati vo) {

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
		MagistratoForm other = (MagistratoForm) obj;
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
	
    public String getAltroMotivo() {
    
        return altroMotivo;
    }

    public String getCognome() {

		return cognome;
	}

	public Date getDataAssegnazioneFascicolo() {

		return dataAssegnazioneFascicolo;
	}

	public Date getDataFineAssegnazione() {

		return dataFineAssegnazione;
	}

	public String getDescrizioneCortaRelatore() {

		if (relatore == null || relatore.trim().isEmpty()) {
			return null;
		}
		if (relatore.trim().equals("S")) {
			return "SI";
		}
		if (relatore.trim().equals("N")) {
			return "NO";
		}
		return null;
	}

	public String getDescrizioneRelatore() {

		if (relatore == null) {
			return null;
		}
		if (relatore.trim().equalsIgnoreCase("S")) {
			return "Relatore";
		}
		if (relatore.trim().equalsIgnoreCase("N")) {
			return "Non relatore";
		}
		return relatore;
	}

	public String getGeneralita() {

		return getCognome() + " " + getNome();
	}

	public Long getId() {

		return id;
	}

	/**
	 * @return the idMagString
	 */
	public String getIdMagString() {

		return idMagString;
	}

	public String getNome() {

		return nome;
	}

	public Byte getOrdineDiVisualizzazione() {

		return ordineDiVisualizzazione;
	}

	public String getRelatore() {

		return relatore;
	}

	/**
	 * @return the relatoreAnteModifica
	 */
	public String getRelatoreAnteModifica() {

		return relatoreAnteModifica;
	}


	/**
	 * @return the ruoloAnteModifica
	 */
	public String getRuoloAnteModifica() {

		return ruoloAnteModifica;
	}

	public String getTipoSostituzione() {

		return tipoSostituzione;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean isSelected() {

		return selected;
	}
	
    public void setAltroMotivo(String altroMotivo) {
    
        this.altroMotivo = altroMotivo;
    }

    public void setCognome(String cognome) {

		this.cognome = cognome;
	}

	public void setDataAssegnazioneFascicolo(Date dataAssegnazioneFascicolo) {

		this.dataAssegnazioneFascicolo = dataAssegnazioneFascicolo;
	}

	public void setDataFineAssegnazione(Date dataFineAssegnazione) {

		this.dataFineAssegnazione = dataFineAssegnazione;
	}

	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * @param idMagString
	 *            the idMagString to set
	 */
	public void setIdMagString(String idMagString) {

		this.idMagString = idMagString;
	}

	public void setNome(String nome) {

		this.nome = nome;
	}

	public void setOrdineDiVisualizzazione(Byte ordineDiVisualizzazione) {

		this.ordineDiVisualizzazione = ordineDiVisualizzazione;
	}

	public void setRelatore(String relatore) {

		this.relatore = relatore;
	}

	/**
	 * @param relatoreAnteModifica
	 *            the relatoreAnteModifica to set
	 */
	public void setRelatoreAnteModifica(String relatoreAnteModifica) {

		this.relatoreAnteModifica = relatoreAnteModifica;
	}


	/**
	 * @param ruoloAnteModifica
	 *            the ruoloAnteModifica to set
	 */
	public void setRuoloAnteModifica(String ruoloAnteModifica) {

		this.ruoloAnteModifica = ruoloAnteModifica;
	}

	public void setSelected(boolean selected) {

		this.selected = selected;
	}

	public void setTipoSostituzione(String tipoSostituzione) {

		this.tipoSostituzione = tipoSostituzione;
	}

	public ComboForm getRuolo() {
		return ruolo;
	}

	public void setRuolo(ComboForm ruolo) {
		this.ruolo = ruolo;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Integer getNumeMatricola() {
		return numeMatricola;
	}

	public void setNumeMatricola(Integer numeMatricola) {
		this.numeMatricola = numeMatricola;
	}

	public Character getOnorario() {
		return onorario;
	}

	public void setOnorario(Character onorario) {
		this.onorario = onorario;
	}

	public String getTipoMagistrato() {
		return tipoMagistrato;
	}

	public void setTipoMagistrato(String tipoMagistrato) {
		this.tipoMagistrato = tipoMagistrato;
	}
	
	
}
