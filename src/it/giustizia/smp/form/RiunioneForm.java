package it.giustizia.smp.form;



/**
 * RiunioneForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione della riunione
 */
public class RiunioneForm extends AttoForm{

	//per l'id ProcedimentoRiunente
	protected String            	riunioneAnno;
	protected String            	riunioneNumero;
	
	protected String           		dataRiunione;
    
    protected ComboForm            	magistrato;
//    protected Long         			idProcedimento;
//    protected Long         			idUffOwner;
    
    protected Long 					idProcedimentoRiunente;
    
    //campi x il dettaglio del soggetto 
    protected Long         			idSoggetto;
    protected Long         			idVersioneSoggetto;
    protected String         		tipoSoggetto;
    
	
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public ComboForm getMagistrato() {
		return magistrato;
	}
	public void setMagistrato(ComboForm magistrato) {
		this.magistrato = magistrato;
	}
//	public Long getIdProcedimento() {
//		return idProcedimento;
//	}
//	public Long getIdUffOwner() {
//		return idUffOwner;
//	}
//	public void setIdProcedimento(Long idProcedimento) {
//		this.idProcedimento = idProcedimento;
//	}
//	public void setIdUffOwner(Long idUffOwner) {
//		this.idUffOwner = idUffOwner;
//	}
	public String getRiunioneAnno() {
		return riunioneAnno;
	}
	public String getRiunioneNumero() {
		return riunioneNumero;
	}
	public void setRiunioneAnno(String riunioneAnno) {
		this.riunioneAnno = riunioneAnno;
	}
	public void setRiunioneNumero(String riunioneNumero) {
		this.riunioneNumero = riunioneNumero;
	}
	public Long getIdProcedimentoRiunente() {
		return idProcedimentoRiunente;
	}
	public void setIdProcedimentoRiunente(Long idProcedimentoRiunente) {
		this.idProcedimentoRiunente = idProcedimentoRiunente;
	}
	public String getDataRiunione() {
		return dataRiunione;
	}
	public void setDataRiunione(String dataRiunione) {
		this.dataRiunione = dataRiunione;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public Long getIdVersioneSoggetto() {
		return idVersioneSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public void setIdVersioneSoggetto(Long idVersioneSoggetto) {
		this.idVersioneSoggetto = idVersioneSoggetto;
	}
    
   
}
