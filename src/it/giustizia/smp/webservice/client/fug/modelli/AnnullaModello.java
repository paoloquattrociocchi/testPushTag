package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.DatiIdentificativiInvioTipo;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.RichiestaRichiestaAnnullamentoModello;

public class AnnullaModello extends ModelloBase {
	
	public RichiestaAnnullamentoModello creaRichiesta() {
		RichiestaAnnullamentoModello out = new RichiestaAnnullamentoModello();
		DatiIdentificativiInvioTipo datiIdentificativoInvia = new DatiIdentificativiInvioTipo();
		datiIdentificativoInvia.setCancelliere(getCancelliere());
		datiIdentificativoInvia.setCodUffMittente(getCodUffMittente());
		RichiestaRichiestaAnnullamentoModello richiesta = new RichiestaRichiestaAnnullamentoModello();
		richiesta.setDatiIdentificativiInvio(datiIdentificativoInvia);
		richiesta.setDatiRichiestaAnnullamentoModello(getProtocolloRicevutaModello());
		out.setRichiestaRichiestaAnnullamentoModello(richiesta);
		return out;
	}
}
