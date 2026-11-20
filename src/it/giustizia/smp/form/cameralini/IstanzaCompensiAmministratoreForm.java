package it.giustizia.smp.form.cameralini;

import it.giustizia.smp.form.AmministratoreForm;

import java.util.List;

/**
 * @author Almaviva S.p.A.
 *         classe per la gestione delle misure di prevenzione: personali e patrimoniali
 */
public class IstanzaCompensiAmministratoreForm extends BaseForm {

	private List<AmministratoreForm>  		listaAmministratori;
	private String 						 	amministratoreSelected;  	
	private List<AmministratoreForm>		listaOmonimi;
	private String 						 	amministratoreOmonimoSel;
	private String 						 	pulsanteRegistra;
	
	
	
	public String getPulsanteRegistra() {
		return pulsanteRegistra;
	}
	public void setPulsanteRegistra(String pulsanteRegistra) {
		this.pulsanteRegistra = pulsanteRegistra;
	}
	public List<AmministratoreForm> getListaAmministratori() {
		return listaAmministratori;
	}
	public void setListaAmministratori(List<AmministratoreForm> listaAmministratori) {
		this.listaAmministratori = listaAmministratori;
	}
	public String getAmministratoreSelected() {
		return amministratoreSelected;
	}
	public List<AmministratoreForm> getListaOmonimi() {
		return listaOmonimi;
	}
	public void setListaOmonimi(List<AmministratoreForm> listaOmonimi) {
		this.listaOmonimi = listaOmonimi;
	}
	public void setAmministratoreSelected(String amministratoreSelected) {
		this.amministratoreSelected = amministratoreSelected;
	}
	public String getAmministratoreOmonimoSel() {
		return amministratoreOmonimoSel;
	}
	public void setAmministratoreOmonimoSel(String amministratoreOmonimoSel) {
		this.amministratoreOmonimoSel = amministratoreOmonimoSel;
	}	
	
}
