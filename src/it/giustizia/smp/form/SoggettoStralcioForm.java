package it.giustizia.smp.form;

import java.util.ArrayList;
import java.util.List;

public class SoggettoStralcioForm extends SoggettoForm {
	
//	per le patr
	List<BeneStralcioForm> listaBeneStralcioForm = new ArrayList<BeneStralcioForm>();
	
	public Character getFlgValidoMisPatr() {
		Character flgV = null;
		
		for (int i = 0; i < getListaBeneStralcioForm().size(); i++) {
			BeneStralcioForm element = getListaBeneStralcioForm().get(i);
			if(element.getFlgValidoBene() != null && element.getFlgValidoBene() == 'S' ){
				return 'S';
			}
			flgV = 'O';
		}
		return flgV;
	}
	
	public Character getFlgValidoMisPers() {
		
		if(getMisura() != null && getMisura().getFlagMisuraValido() != null ){
			return getMisura().getFlagMisuraValido();
		}else{
			return null;
		}
	}

	
	public Character getFlgSoggettoValido() {
		if(getFlgValido() != null && getFlgValido().equalsIgnoreCase("S")){
			return 'S';
		}
		if(getFlgValidoMisPatr() != null && getFlgValidoMisPatr() == 'S'){
			return 'S';
		}
		
		if(getFlgValidoMisPers() != null && getFlgValidoMisPers() == 'S'){
			return 'S';
		}
		
		return 'O';
	}
	
	
	
	
	//metodo inserito per il ricorso
	
	public Character getFlgValidoMisPersRicorso() {
		Character flgV = null;
		
		for (int i = 0; i < getListaBeneStralcioForm().size(); i++) {
			MisuraForm element = getListaBeneStralcioForm().get(i).getMisura();
			if(element!= null && element.getFlagMisuraValido()!= null && element.getFlagMisuraValido() == 'S' ){
				return 'S';
			}
			flgV = 'R';
		}
		return flgV;
	}
	
	public Character getFlgSoggettoValidoRicorso() {
		if(getFlgValido() != null && getFlgValido().equalsIgnoreCase("S")){
			return 'S';
		}
		if(getFlgValidoMisPatr() != null && getFlgValidoMisPatr() == 'S'){
			return 'S';
		}
		
		if(getFlgValidoMisPersRicorso() != null && getFlgValidoMisPersRicorso() == 'S'){
			return 'S';
		}
		
		return 'R';
	}
	
	
	//fine metodo inserito per il ricorso
	

	
	public List<BeneStralcioForm> getListaBeneStralcioForm() {
		return listaBeneStralcioForm;
	}

	public void setListaBeneStralcioForm(
			List<BeneStralcioForm> listaBeneStralcioForm) {
		this.listaBeneStralcioForm = listaBeneStralcioForm;
	}

	
}
