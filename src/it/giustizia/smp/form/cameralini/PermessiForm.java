package it.giustizia.smp.form.cameralini;

import it.giustizia.smp.form.SoggettoForm;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

/**
 * @author Almaviva S.p.A.
 *         classe per la gestione delle misure di prevenzione: personali e patrimoniali
 */
public class PermessiForm extends BaseForm {

	private List<SoggettoForm>  			listaSoggetti;
	private String 						 	soggettoSelected;  		// concatena i valori idSoggetto^tipoPersona^idVersione di SoggettoForm
	
	private List<SoggettoForm>  soggettiMisPers = new AutoPopulatingList<SoggettoForm>(SoggettoForm.class); // attributo per popolamento lista soggetti sel
	
	public List<SoggettoForm> getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List<SoggettoForm> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}
	public String getSoggettoSelected() {
		return soggettoSelected;
	}
	public void setSoggettoSelected(String soggettoSelected) {
		this.soggettoSelected = soggettoSelected;
	}
	
    public List<SoggettoForm> getSoggettiMisPers() {
		return soggettiMisPers;
	}
	public void setSoggettiMisPers(List<SoggettoForm> soggettiMisPers) {
		this.soggettiMisPers = soggettiMisPers;
	}
	
}
