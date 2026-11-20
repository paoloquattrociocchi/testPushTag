package it.giustizia.smp.dto;

import java.util.HashMap;
import java.util.List;

import it.giustizia.smp.utility.Constants;

public class KeyMessageDto {
	
	
	//Campo di controllo per capire se è stato generato un errore con 'KO' o l'operazione
	//è andata a buon fine con 'OK'
	private String status;

	//contiene i messaggi di ritorno per l'utente. 
	//Può contenere sia messaggi di errore in caso di KO
	//che messaggi di controllo in caso di OK
	private String message;
	
	//contiene l'eventuale id di ritorno nelle procedure di inserimento/lettura/modifica avvenute su DB
	private long id;

	public KeyMessageDto() {
	}
	
	public KeyMessageDto(List l) {
		if(l != null && l.size()==1){
			HashMap ritorno = (HashMap) l.get(0);
			this.message = ((String) ritorno.get(Constants.TipoMessaggiDb.MESSAGE));
			this.status = ((String) ritorno.get(Constants.TipoMessaggiDb.STATUS));
			if(ritorno.get(Constants.TipoMessaggiDb.KEY) != null){
				this.id = (((Long)ritorno.get(Constants.TipoMessaggiDb.KEY)).longValue());
			}
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		if(status!=null && status.trim().length()>0 && status.equalsIgnoreCase(Constants.TipoMessaggiDb.KO)){
			return true;
		}else{
			return false;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}

