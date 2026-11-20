package it.giustizia.smp.form;

import it.giustizia.smp.utility.report.ReportDataSource;

import java.util.List;

import net.sf.jasperreports.engine.JasperReport;


/**
 * ReportForm
 * 
 * Classe container dei dati da passare ai Report 
 * 
 * @author Almaviva S.p.A.
 */

public class ReportForm {

	
    private ProcedimentoForm	   			procedimento;
    private AttoForm			   			atto;
    private MisuraForm			   			misura;
	private SoggettoForm           			soggetto;
	private SoggettoForm					soggettoTitolare;
    private BeneForm               			bene;  
    private List<MagistratoForm>   			magistrati;
    
    
    private ReportDataSource				dataSourceMagistrati;
    private JasperReport					subreportMagistrati;
    private ReportDataSource				dataSourceMispatrBeneSog;
    private JasperReport					subreportMispatrBeneSog;
    
   /* private List<BeneSoggettoForm> listaBeniSoggetti ;
    private List<BeneForm>         listaBeni;
    private List<SoggettoForm>     soggetti;
   ;*/
    
  
    
	public BeneForm getBene() {
		return bene;
	}
	public void setBene(BeneForm bene) {
		this.bene = bene;
	}
	public SoggettoForm getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SoggettoForm soggetto) {
		this.soggetto = soggetto;
	}
	/**
	 * @return the soggettoTitolare
	 */
	public SoggettoForm getSoggettoTitolare() {
		return soggettoTitolare;
	}
	/**
	 * @param soggettoTitolare the soggettoTitolare to set
	 */
	public void setSoggettoTitolare(SoggettoForm soggettoTitolare) {
		this.soggettoTitolare = soggettoTitolare;
	}
	public ProcedimentoForm getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(ProcedimentoForm procedimento) {
		this.procedimento = procedimento;
	}
	public AttoForm getAtto() {
		return atto;
	}
	public void setAtto(AttoForm atto) {
		this.atto = atto;
	}
	public MisuraForm getMisura() {
		return misura;
	}
	public void setMisura(MisuraForm misura) {
		this.misura = misura;
	}	
	
	public List<MagistratoForm> getMagistrati() {
		return magistrati;
	}
	public void setMagistrati(List<MagistratoForm> magistrati) {
		this.magistrati = magistrati;
	}
/*	public List<BeneSoggettoForm> getListaBeniSoggetti() {
		return listaBeniSoggetti;
	}
	public void setListaBeniSoggetti(List<BeneSoggettoForm> listaBeniSoggetti) {
		this.listaBeniSoggetti = listaBeniSoggetti;
	}
	public List<BeneForm> getListaBeni() {
		return listaBeni;
	}
	public void setListaBeni(List<BeneForm> listaBeni) {
		this.listaBeni = listaBeni;
	}
	public List<SoggettoForm> getSoggetti() {
		return soggetti;
	}
	public void setSoggetti(List<SoggettoForm> soggetti) {
		this.soggetti = soggetti;
	}*/
	/**
	 * @return the dataSourceMagistrati
	 */
	public ReportDataSource getDataSourceMagistrati() {
		return dataSourceMagistrati;
	}
	/**
	 * @param dataSourceMagistrati the dataSourceMagistrati to set
	 */
	public void setDataSourceMagistrati(ReportDataSource dataSourceMagistrati) {
		this.dataSourceMagistrati = dataSourceMagistrati;
	}
	/**
	 * @return the subreportMagistrati
	 */
	public JasperReport getSubreportMagistrati() {
		return subreportMagistrati;
	}
	/**
	 * @param subreportMagistrati the subreportMagistrati to set
	 */
	public void setSubreportMagistrati(JasperReport subreportMagistrati) {
		this.subreportMagistrati = subreportMagistrati;
	}
	/**
	 * @return the dataSourceMispatrBeneSog
	 */
	public ReportDataSource getDataSourceMispatrBeneSog() {
		return dataSourceMispatrBeneSog;
	}
	/**
	 * @param dataSourceMispatrBeneSog the dataSourceMispatrBeneSog to set
	 */
	public void setDataSourceMispatrBeneSog(
			ReportDataSource dataSourceMispatrBeneSog) {
		this.dataSourceMispatrBeneSog = dataSourceMispatrBeneSog;
	}
	/**
	 * @return the subreportMispatrBeneSog
	 */
	public JasperReport getSubreportMispatrBeneSog() {
		return subreportMispatrBeneSog;
	}
	/**
	 * @param subreportMispatrBeneSog the subreportMispatrBeneSog to set
	 */
	public void setSubreportMispatrBeneSog(JasperReport subreportMispatrBeneSog) {
		this.subreportMispatrBeneSog = subreportMispatrBeneSog;
	}
	
	

    
}