package it.giustizia.smp.utility;

import java.util.HashMap;

public final class MappaStatiComunicazioneCasellario {

	private static HashMap<String,String> mappaStati = new HashMap<String, String>();
	static {
		mappaStati.put("0","Da Inviare");
		mappaStati.put("1","Presenza Omonimi");
		mappaStati.put("2","Inviata");
		mappaStati.put("3","Morte del Soggetto");
		mappaStati.put("4","Annullata");
	}

	/**
	 * Restituisce la descrizione dello stato
	 * @param codStato
	 * @return
	 */
	public static String getDescrizioneStatoScheda(String codStato) {
		return mappaStati.get(codStato);
	}
	
	
	/*
	<c:if test="${attoForm.comunicazioneForm!=null}">
	<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='0'}">Presente / Da Inviare</c:if>
	<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='1'}">Presenza Omonimi</c:if>
	<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='2'}">Inviata il <fmt:formatDate value="${attoForm.comunicazioneForm.dataInvio}" type="date" pattern="dd/MM/yyyy" /> </c:if>
	<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='3'}">Morte del Soggetto</c:if>
	<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='4'}">Annullata il <fmt:formatDate value="${attoForm.comunicazioneForm.dataInvio}" type="date" pattern="dd/MM/yyyy" /></c:if>
	</c:if>
	*/
}
