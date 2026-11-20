package it.giustizia.smp.dto.fug;

import java.sql.Blob;

public class DettaglioInvioFugDto {

	private String protocollo;
	private Blob RICHIESTAXML;
	private Blob ESITOXML;

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Blob getRICHIESTAXML() {
		return RICHIESTAXML;
	}

	public void setRICHIESTAXML(Blob rICHIESTAXML) {
		RICHIESTAXML = rICHIESTAXML;
	}

	public Blob getESITOXML() {
		return ESITOXML;
	}

	public void setESITOXML(Blob eSITOXML) {
		ESITOXML = eSITOXML;
	}

}
