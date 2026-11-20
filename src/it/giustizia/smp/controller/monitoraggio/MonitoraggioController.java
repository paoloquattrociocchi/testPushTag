package it.giustizia.smp.controller.monitoraggio;


//import it.giustizia.smp.service.webservices.FugWsService;
//import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
//import it.giustizia.smp.webservice.client.fug.DatiIdentificativiInvioTipo;
//import it.giustizia.smp.webservice.client.fug.DatiRegistroFUGTipo;
//import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloA;
//import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloA.InfoProvvedimento;
//import it.giustizia.smp.webservice.client.fug.DatiRisorsaTipo;
//import it.giustizia.smp.webservice.client.fug.InfoProcessoTipo;
//import it.giustizia.smp.webservice.client.fug.RichiestaModelloA;
//import it.giustizia.smp.webservice.client.fug.TipoRegistroFUGTipo;
//import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;
//
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;

@Controller
public class MonitoraggioController {

//	@Autowired
//	private FugWsService	fugService;
	
	/**
	 * Questo metodo è solo di test per simulare un invio di Modello A al FUG
	 * Il link è definito per la Procura (utente SMP) e si ragginge dal Monitoraggio
	 * @return
	 */
	/*@RequestMapping(value = "/monitoraggio/test.htm", method = RequestMethod.GET)
	public final ModelAndView test() {
		AcquisisciModelloA modelloA = new AcquisisciModelloA();
		RichiestaModelloA richiestaModelloA = new RichiestaModelloA();
		
		DatiIdentificativiInvioTipo datiIdentificativiInvio = new DatiIdentificativiInvioTipo();
		datiIdentificativiInvio.setCancelliere("cancelliere12345");
		datiIdentificativiInvio.setCodUffMittente("12345678910");
		richiestaModelloA.setDatiIdentificativiInvio(datiIdentificativiInvio);
		
		
		DatiRichiestaInvioModelloA datiRichiestaInvioModelloA = new DatiRichiestaInvioModelloA();
		
		DatiRisorsaTipo datiRisorsaTipo = new DatiRisorsaTipo();
		datiRisorsaTipo.setCodiceOperatore("1234567893214569");
		datiRisorsaTipo.setTipoRisorsa("12");
		datiRisorsaTipo.setNumeroRisorsa("123456987452145");
		datiRisorsaTipo.setABI("12345");
		datiRisorsaTipo.setCAB("12345");
		
		DatiRegistroFUGTipo datiRegistro = new DatiRegistroFUGTipo();
		datiRegistro.setTipoRegistroFUG(TipoRegistroFUGTipo.RFUG_1);
		datiRegistro.setAnnoRegistroFUG("2014");
		datiRegistro.setNumeroRegistroFUG("1234");
		
		InfoProcessoTipo infoProcesso = new InfoProcessoTipo();
		infoProcesso.setCodUffEmittente("12345678912");
		infoProcesso.setTipoRegistro(TipoRegistroTipo.RPRO_1);
		infoProcesso.setNumeroRegistro("1234567");
		infoProcesso.setAnnoRegistro("2014");
		
		InfoProvvedimento infoProvvedimento = new InfoProvvedimento();
		infoProvvedimento.setDataProvvedimentoSequestro("25022014");
		infoProvvedimento.setFlagSequestro("P");
		infoProvvedimento.setImporto("100,56");
		infoProvvedimento.setDivisa("EUR");
		
		datiRichiestaInvioModelloA.setDatiRisorsa(datiRisorsaTipo);
		datiRichiestaInvioModelloA.setDatiRegistroFUG(datiRegistro);
		datiRichiestaInvioModelloA.setInfoProcesso(infoProcesso);
		datiRichiestaInvioModelloA.setInfoProvvedimento(infoProvvedimento);
		richiestaModelloA.setDatiRichiestaInvioModelloA(datiRichiestaInvioModelloA);
		
		modelloA.setRichiestaModelloA(richiestaModelloA);
		fugService.invioModelloA(modelloA);
		ModelAndView mnv = new ModelAndView("/login.htm");
		return mnv;
	}*/
}
