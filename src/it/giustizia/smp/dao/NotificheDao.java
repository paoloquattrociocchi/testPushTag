package it.giustizia.smp.dao;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificheEComunicazioniForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TNotifAmm;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifiche;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TTpRuoliDest;
import it.giustizia.smp.model.TTpStatiTrasm;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.model.VRappTrasm;
import it.giustizia.smp.model.VRicevuta;
import it.giustizia.smp.utility.notifiche.ServiceResult;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RisultatoComunicazione;


/**
 * @author massimo.demattia
 *
 */

public interface NotificheDao {

	/**
	 * Ritorna il valore 'S' se l'atto è notificabile. 'D' Se è notificabile ma manca il documento
	 * 'N' se non è notificabile
	 * @param idAtto
	 * @param idProcedimento
	 * @return
	 */
	public String isAttoInviabile(final long idAtto, final long idProcedimento);
	public List<DestinatarioDto> caricaListaDestinatari(final long idProc, final long idAtto,final int idUfficio);
	public Boolean hasNotifiche(Long idAtto, Integer idUfficio);
	public List<TTpTipoUfficio> getTipoUfficiPerDesinatario();
	public DestinatarioDto getDetinatarioDTObyIdUfficio(int idUfficio);
	public TAnUfficio getUfficioByCodugne(String codugne);
	public TAmministratori getAmminByCodiceFiscale(String codfisc);
	public void modificaCopiaPDOC(final Long numeCopiaPdoc, final long idAtto,final int idUfficio);
	public ServiceResult insertNotifica(
			NotificaAttoForm attoForm, 
			TAtto tAtto, 
			ProcedimentoForm procedimento, 
			int idUfficioMit,
			RisultatoComunicazione responseSenzaContenuto, 
			RisultatoComunicazione responseConContenutoSenzaXML,
			RisultatoComunicazione responseConContenutoConXML,
			InviaComunicazione requestSenzaContenuto,
			InviaComunicazione requestConContenutoSenzaXML,
			InviaComunicazione requestConContenutoConXML,
			final String descUtente,
			List<DestinatarioDto> listaDestinatariSelezionatiSenzaContenuto, 
			List<DestinatarioDto> listaDestinatariSelezionatiConContenuto,
			byte[] xmlDati,
			BigInteger idPDOCXml
			) throws Exception;
	
    
	public List<TNotifiche>  getListaNotifiche(Long idAtto, int idUfficio);
	public String decodificaStatoTrasmissione(String stato);
	public List<TNotifDest> getNotifDestAttesaRicevuta();
	public List<TNotifDest> getNotifDestAttesaRappTrasm();
	public TTpStatiTrasm getTTpStatiTrasmById(String statoTrasmissione);
	public long inserisciAttoRicevuta(int idUfficioOwn, int codiKIdCatAttoRicevuta, String descUtente, long idProcedimento, long codiKIdNotifDest,
			String codiKStatoTrasm, byte[] file,String tipoDoc);
	
	public long inserisciAttoRappTrasm(int idUfficioOwn, int codiKIdCatAttoRicevuta, String descUtente, long idProcedimento, long codiKIdNotifDest,
			String idPDocTrasmissione);
	
	
	public boolean isStatoTrasmissioneGiaGestito(String statoTrasmissione, long codiKIdNotifDest);
	
	public List<VRicevuta>  getListaAttiRicevutoDaAttoNotificato(Long idAttoNotificato, int idUfficioNotificato) ;
	
	public List<VRappTrasm>  getRappTrasmDaAttoNotificato(Long idAttoNotificato, int idUfficioNotificato) ;
	
	
	
	public List<VNotifiche> getListaNotifichePerFiltro(int idUfficioMitt, int idTipoUff, NotificheEComunicazioniForm notificheEComunicazioniForm);
	public List<TTpRuoliDest> getListaTipoRuoliDesinatario();
	
	public void inserisciDepositoSeNonEsiste(String idDepositoGl, String idMittenteString, String idDestinatariString, Integer idUfficioMittente,
			Integer idUfficioDestinatario, Date dataRicezione, String idDocumentoString);

	public TNotifAmm getTNotifAmmByid(long codiKIdNotifDest);
	public TDepositi getDepositoByGl(String idDepositoGl);

	public void inserisciDepositoAmm(long idDeposito, TProcedimento tProcedimentoFg, TProcedimento tProcedimento, TDepositi tDeposito, Blob blobXml,
									 Blob blobPdf, String descTipoDoc, String descNomeDoc);

	
}
