/**
 * 
 */
package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class FlussiToolAmministratoriForm {

	private Long						idDeposito;
	private int						    idUffOwner;

	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataRicezione;
	private String						numeroRiferimento;
	private Long						idProcFG;
	private String						descProcFG;
	private Long						idProc;
	private String						descProc;
	private Long						idAmm;
	private String						descCodiceFiscale;
	private String						descCognome;
	private String						descNome;
	private String						amministratore;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataInizioPeriodo;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataFinePeriodo;
	private int                         idUffMitt;
	private String                      descUffMitt;
	private String 			            nomeAllegato;
	private String 			            tipoAllegato;
	private ProcedimentoForm 			formProcedimentoFG;
	private ProcedimentoForm 			formProcedimentoFM;
	private AmministratoreForm 			formAmministratore;
	private List<BeneForm> 			    listaFormBeni = new ArrayList<BeneForm>();
	private char						flag;
	
	public Long getIdDeposito() {
		return idDeposito;
	}
	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}
	public int getIdUffOwner() {
		return idUffOwner;
	}
	public void setIdUffOwner(int idUfficio) {
		this.idUffOwner = idUfficio;
	}
	
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Long getIdProcFG() {
		return idProcFG;
	}
	public void setIdProcFG(Long idProcFG) {
		this.idProcFG = idProcFG;
	}
	public String getDescProcFG() {
		return descProcFG;
	}
	public void setDescProcFG(String descProcFG) {
		this.descProcFG = descProcFG;
	}
	public Long getIdProc() {
		return idProc;
	}
	public void setIdProc(Long idProc) {
		this.idProc = idProc;
	}
	public String getDescProc() {
		return descProc;
	}
	public Long getIdAmm() {
		return idAmm;
	}
	public void setidAmm(Long idAmm) {
		this.idAmm = idAmm;
	}	
	public void setDescProc(String descProc) {
		this.descProc = descProc;
	}
	public String getDescCodiceFiscale() {
		return descCodiceFiscale;
	}
	public void setDescCodiceFiscale(String descCodiceFiscale) {
		this.descCodiceFiscale = descCodiceFiscale;
	}
	public String getDescCognome() {
		return descCognome;
	}
	public void setDescCognome(String descCognome) {
		this.descCognome = descCognome;
	}
	public String getDescNome() {
		return descNome;
	}
	public void setDescNome(String descNome) {
		this.descNome = descNome;
	}
	public String getAmministratore() {
		return amministratore;
	}
	public void setAmministratore(String amministratore) {
		this.amministratore=amministratore;
	}
	public Date getDataInizioPeriodo() {
		return dataInizioPeriodo;
	}
	public void setDataInizioPeriodo(Date dataInizio) {
		this.dataInizioPeriodo = dataInizio;
	}
	public Date getDataFinePeriodo() {
		return dataFinePeriodo;
	}
	public void setDataFinePeriodo(Date dataFine) {
		this.dataFinePeriodo = dataFine;
	}
	public int getIdUffMitt() {
		return idUffMitt;
	}
	public void setIdUffMitt(int idUffMitt) {
		this.idUffMitt = idUffMitt;
	}
	public String getDescUffMitt() {
		return descUffMitt;
	}
	public void setDescUffMitt(String descUffMitt) {
		this.descUffMitt = descUffMitt;
	}

	
	public String getNomeAllegato() {
		return nomeAllegato;
	}
	public void setNomeAllegato(String nomeDoc) {
		this.nomeAllegato=nomeDoc;
	}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoDoc) {
		this.tipoAllegato=tipoDoc;
	}
	
	public ProcedimentoForm getFormProcedimentoFG() {
		return formProcedimentoFG;
	}
	public void setFormProcedimentoFG(ProcedimentoForm procedimento) {
		this.formProcedimentoFG = procedimento;
	}
	public ProcedimentoForm getFormProcedimentoFM() {
		return formProcedimentoFM;
	}
	public void setFormProcedimentoFM(ProcedimentoForm procedimento) {
		this.formProcedimentoFM = procedimento;
	}
	public AmministratoreForm getFormAmministratore() {
		return formAmministratore;
	}
	public void setFormAmministratore( AmministratoreForm formAmministratore) {
		this.formAmministratore = formAmministratore;
	}	
	
	public String getNumeroRiferimento() {
		return numeroRiferimento;
	}
	public void setNumeroRiferimento(String numRif) {
		this.numeroRiferimento=numRif;
	}
	
	public List<BeneForm> getListaFormBeni() {
		return listaFormBeni;
	}
	public void setListaFormBeni(List<BeneForm> listaBeni) {
		this.listaFormBeni = listaBeni;
	}
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
}
