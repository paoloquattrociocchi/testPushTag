package it.giustizia.smp.form;

import java.util.ArrayList;
import java.util.List;

public class BeneStralcioForm extends BeneForm {
	
	 private List<SoggettoTitolareForm> 	listaSoggettoTitolareForm = new ArrayList<SoggettoTitolareForm>();
	 private String				 			tipoMisura;
	 private MisuraForm                     misura;
	 
	 
	

	public Character getFlgValidoBene() {
		 Character flgV = null;
			for (int i = 0; i < getListaSoggettoTitolareForm().size(); i++) {
				SoggettoTitolareForm element = getListaSoggettoTitolareForm().get(i);
				if(element.getMisura() != null && element.getMisura().getFlagMisuraValido() == 'S'){
					return 'S';
				}
				flgV = 'O';
			}
			
		return flgV;
	}
	 
	 //inserito per il ricorso
	 public Character getFlgValidoBeneRicorso() {
		 Character flgV = null;
			for (int i = 0; i < getListaSoggettoTitolareForm().size(); i++) {
				SoggettoTitolareForm element = getListaSoggettoTitolareForm().get(i);
				if(element.getMisura() != null && element.getMisura().getFlagMisuraValido() == 'S'){
					return 'S';
				}
				flgV = 'R';
			}
			
		return flgV;
	}
	 
	 //fine ricorso

	public List<SoggettoTitolareForm> getListaSoggettoTitolareForm() {
		return listaSoggettoTitolareForm;
	}

	public void setListaSoggettoTitolareForm(
			List<SoggettoTitolareForm> listaSoggettoTitolareForm) {
		this.listaSoggettoTitolareForm = listaSoggettoTitolareForm;
	}

	public String getTipoMisura() {
		return tipoMisura;
	}

	public void setTipoMisura(String tipoMisura) {
		this.tipoMisura = tipoMisura;
	}

	 public MisuraForm getMisura() {
			return misura;
		}

		public void setMisura(MisuraForm misura) {
			this.misura = misura;
		}
	
	
}
