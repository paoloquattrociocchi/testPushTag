package it.giustizia.smp.service.webservices;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.OmonimoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIRISPOSTATRASFERIMENTO;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import javax.xml.bind.JAXBException;

public interface ICasellarioServiceFC {

	/**
	 * Invia della scheda al casellario
	 * @param utente
	 * @param idUffOwn
	 * @param codUtente
	 * @param attoForm
	 * @param comunicazioneForm
	 * @return
	 */
	public abstract DATIRISPOSTATRASFERIMENTO inviaProvvedimento(final String descUtente, final TUtente utente,final long idUffOwn,AttoForm attoForm,ComunicazioneForm comunicazioneForm,ProcedimentoForm procedimentoForm,String idNSC) throws Exception ;
	public abstract DATIRISPOSTATRASFERIMENTO cancellaProvvedimento(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm, ComunicazioneForm comunicazioneForm, ProcedimentoForm procedimentoForm) throws Exception ;
	public abstract DATIRISPOSTATRASFERIMENTO unmarhallerRispostaTrasferimento(Blob blobFile) throws JAXBException,IOException;
	public abstract List<OmonimoForm> getListaOmonimi(ComunicazioneForm comunicazioneForm) throws JAXBException, IOException;
}