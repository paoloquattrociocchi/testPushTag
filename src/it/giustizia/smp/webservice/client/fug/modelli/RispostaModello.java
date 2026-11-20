package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.ArrayList;
import java.util.List;

public class RispostaModello {

	public final static String NO_ID_PROTOCOLLO = "---";
	private String idProtocollo;
	private Long idFugInvio;
	private List<EsitoFUG> esito;

	// private String esitoAnnullamento;
	// private String codiceSezione;
	// private String campoTipo;
	// private String descErroreTipo;
	// private String descErrore;

	public static EsitoFUG getEsito(RispostaModello in){
		return ((in != null) && (in.getEsito() != null) && (in.getEsito().size() > 0)) ? in.getEsito().get(0) : new EsitoFUG(); 
	}

	public String getIdProtocollo() {
		return (idProtocollo != null) ? idProtocollo : NO_ID_PROTOCOLLO;
	}

	public void setIdProtocollo(String idProtocollo) {
		this.idProtocollo = idProtocollo;
	}

	public Long getIdFugInvio() {
		return idFugInvio;
	}

	public void setIdFugInvio(Long idFugInvio) {
		this.idFugInvio = idFugInvio;
	}
	
	public List<EsitoFUG> getEsito() {
		return esito;
	}

	public void setEsito(List<EsitoFUG> esito) {
		this.esito = esito;
	}

	public void addEsito(EsitoFUG in) {
		if (in != null) {
			if (esito == null) esito = new ArrayList<EsitoFUG>();
			esito.add(in);
		}
	}
}
