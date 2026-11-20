package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TTpTipoFilePdoc;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.IDocumentaleClient;
import it.giustizia.smp.webservice.client.documentale.model.general.Risposta;
import it.giustizia.smp.webservice.client.documentale.model.response.InserimentoDocumentoResponse;

import java.math.BigInteger;
import java.rmi.ConnectException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.axis2.databinding.types.soapencoding.Decimal;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentaleService implements IDocumentaleService {

	@Autowired
	private IDocumentaleClient	documentaleClient;
	@Autowired
	private AttoService			attoService;
	@Autowired
	private ProcedimentoDao 	procedimentoDao;
	@Autowired
	private AttoDao 			attoDao;
	@Autowired
	private CommonDao 			commonDao;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public final void setDocumentaleClient(IDocumentaleClient documentaleClient) {
		this.documentaleClient = documentaleClient;
	}
	
	public void setProcedimentoDao(ProcedimentoDao procedimentoDao) {
		this.procedimentoDao = procedimentoDao;
	}   

	public String testConnessione(TAnUtenzePdoc utenzaPdoc){
		Risposta response = null;
		try{
			response = documentaleClient.testConnessione(utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if (response == null) {
			return null;
		}
		return response.getMessaggio().getMessagebody();
	}
	
	public boolean esitoTestConnessione(int idUffOwner){
		Risposta response = null;
		try{
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.testConnessione(utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if (response == null) {
			return false;
		}
		if(logger.isDebugEnabled()){
			logger.debug("    1.1.1) ESITO TEST CONNESSIONE - Ufficio: " + Integer.toString(idUffOwner) + "\n" + 
									", Esito: " + Boolean.toString(response.getMessaggio().isEsito()) + "\n" + 
									", Codice Messaggio: " + response.getMessaggio().getCodicemessaggio() + "\n" + 
									", Messaggio: " + response.getMessaggio().getMessagebody() + ";" );
		}
		return response.getMessaggio().isEsito();
	}
	
	public boolean creaFascicolo(String codiceFascicolo, String codiceFascicoloPadre, String oggettoFascicolo, int idUffOwner){
		Risposta response = null;
		int codiceGL;
		TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
		List<BigInteger> codugnaList = new ArrayList<BigInteger>();
		codiceGL = commonDao.getIdDocumentale("GL");
		if(codiceGL != 0)
			codugnaList.add(BigInteger.valueOf(codiceGL));
		try{
			response = documentaleClient.creaFascicolo(codiceFascicolo, codiceFascicoloPadre, oggettoFascicolo, codugnaList, utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if (response == null) {
			return false;
		}
		if(logger.isDebugEnabled()){
			logger.debug("   0.2 ) DocumentaleService ==> codiceFascicolo: "+codiceFascicolo+ " Creazione fascicolo nel documentale: " + response.getMessaggio().isEsito() );
		}
		return response.getMessaggio().isEsito();
	}
	
	public BigInteger inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, String codugna){
		Integer idUffOwner = new Integer(codugna);
		return inserimentoDocumento(oggetto, tipoDocumento, fileName, note, blobFile, codugna, idUffOwner.intValue());
	}
	
	public BigInteger inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, String listCodugna, int idUffOwner){
		InserimentoDocumentoResponse response = null;
		List<BigInteger> codugnaList = new ArrayList<BigInteger>();
		if(listCodugna!=null && listCodugna.length()>0)
		{
			StringTokenizer codugnaTokenizer = new StringTokenizer(listCodugna, SEPARATORE_CAMPI_IN_STRINGA_CODUGNA);
			while (codugnaTokenizer.hasMoreElements())
			{
				String codUgnaStr = codugnaTokenizer.nextElement().toString();
				int codugnaInt = commonDao.getIdDocumentale(codUgnaStr);
				if(codugnaInt != 0)
					codugnaList.add(BigInteger.valueOf(codugnaInt));
			}
		}
		try {
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.inserimentoDocumento(oggetto, tipoDocumento, fileName, note, blobFile, codugnaList, utenzaPdoc);
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		if (response == null) {
			return null;
		}
		return response.getIddocumento();
	}
	
	public byte[] recuperoDocumento(BigInteger idDocumento){
		byte[] response;
		try {
			int idUffOwner = attoService.getUffOwnerFromIdDocumento(idDocumento);
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.recuperoDocumento(idDocumento, utenzaPdoc);
			if(logger.isDebugEnabled()){
				logger.debug("**********DocumentaleService ==> recuperoDocumento - idDocumento: "+idDocumento);
			}
			return response;
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] recuperoDocumento(BigInteger idDocumento, int idUffOwner){
		byte[] response;
		try {
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.recuperoDocumento(idDocumento, utenzaPdoc);
			if(logger.isDebugEnabled()){
				logger.debug("**********DocumentaleService ==> recuperoDocumento - idDocumento: "+idDocumento);
			}
			return response;
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean eliminaDocumento(BigInteger idDocumento, int idUffOwner){
		boolean response = false;
		try{
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.eliminaDocumento(idDocumento, utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("**********DocumentaleService ==> eliminaDocumento - idDocumento : "+idDocumento +" response: "+response);
		}
		return response;
	}
	
	public boolean stralcio(String codiceFascicoloOrigine, String codiceFascicoloDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti, String codUgna){
		boolean response = false;
		try{
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(codUgna);
			response = documentaleClient.stralcio(codiceFascicoloOrigine, codiceFascicoloDestinazione, documenti, utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("**********DocumentaleService ==> stralcio - fascicoloOrigine : "+codiceFascicoloOrigine +" response: "+response);
		}
		return response;
	}
	
	public boolean riunione(String codiceFascicoloOrigine, String codiceFascicoloRiunione, ArrayList<BigInteger> documenti, String codUgna){
		boolean response = false;
		try{
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(codUgna);
			response = documentaleClient.riunione(codiceFascicoloOrigine, codiceFascicoloRiunione, documenti, utenzaPdoc);
		}
		catch (ConnectException e) {
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("**********DocumentaleService ==> riunione - fascicoloOrigine : "+codiceFascicoloOrigine +" response: "+response);
		}
		return response;
	}
	
	
	public boolean firmaRemota(BigInteger idDocumento, String username, String password, String pin, int idUffOwner){
		boolean result = false;
		try {
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			Risposta response = documentaleClient.firmaRemota(idDocumento, username, password, pin, utenzaPdoc);
			if(response.getMessaggio().isEsito() == true)
				result = true;
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public BigInteger renderPdf(String tipoDocumento, String nomeDocumento, long idAtto, BigInteger idDocumento, long uffOwner, TAnUtenzePdoc utenzaPdoc){
		BigInteger idTransazione = null;
		
		// Modifica documentale... si convertono sutte le tipologie diverse da pdf!! 6 aprile 2018
		//if(tipoDocumento.equalsIgnoreCase("application/msword") || tipoDocumento.equalsIgnoreCase("image/pjpeg")
		//		|| tipoDocumento.equalsIgnoreCase("text/plain") || tipoDocumento.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
		//		|| tipoDocumento.equalsIgnoreCase("image/bmp") || tipoDocumento.equalsIgnoreCase("image/gif")
		//		|| tipoDocumento.equalsIgnoreCase("image/tiff"))
		if (nomeDocumento == null){
			nomeDocumento = "_.";
		}
		if(!nomeDocumento.equalsIgnoreCase("RapportoTrasmissione.pdf") &&  !nomeDocumento.equalsIgnoreCase("ricevuta.pdf") )  //Esclusi dalla conversione le occorrenze derivanti dalle notifiche
		{
		List<TTpTipoFilePdoc> listatipofiledaconv =  attoDao.listaTipoFilePdoc();
		
		Iterator<TTpTipoFilePdoc> tf = listatipofiledaconv.iterator();
		while(tf.hasNext())
			 {
				TTpTipoFilePdoc tipoF =  tf.next();
				String tipologiaConv =  tipoF.getMimeType().toLowerCase();
				if (tipoDocumento.toLowerCase().toLowerCase().equalsIgnoreCase(tipologiaConv)){
					
						if(logger.isDebugEnabled()){
							logger.debug("    5.1) - DocumentaleService ==> Invio Conversione PDF: " +  idAtto +" - " + uffOwner);
						}
						try {
							String nomeDocumentoOk="_.";
							if (nomeDocumento != null){
								nomeDocumentoOk = nomeDocumento;
							}
							//int dotPos = nomeDocumento.lastIndexOf('.');
							int dotPos = nomeDocumentoOk.lastIndexOf('.');
							if(dotPos == -1)
								return null;
							idTransazione = documentaleClient.renderPdf(idDocumento, utenzaPdoc);
							if(idTransazione != null){
								attoDao.insertIdTransazioneDocumentale(idAtto, idTransazione, (int)uffOwner);
	//							attoDao.aggiornaTipoDocumento(nomeDocumentoPdf,idAtto,idDocumento,(int)uffOwner);
	//							attoDao.updateIdTransazioneDocumentale(idAtto, idTransazione);
								if(logger.isDebugEnabled()){
									logger.debug("    5.1) - DocumentaleService ==> Ritorno Conversione PDF: "+ idTransazione +" - " +  idAtto +" - " + uffOwner);
								}
							}else{
								System.out.println("    5.1) - ERRORE ==> Errore Ritorno Conversione PDF: "+ idTransazione );
							}
						} catch (ConnectException e) {
							e.printStackTrace();
						}
						break;
				}
			 }
		}
		return idTransazione;
	}
	
	public boolean monitorRenderPdf(TAtto atto, TAnUtenzePdoc utenzaPdoc){
		boolean response = false;
		try {
			if(response = documentaleClient.monitorRenderPdf(BigInteger.valueOf(atto.getNumeConvPdoc()), utenzaPdoc, atto) == true)
			{
				String nomeDocumentoOrig = "_."; // se non vi è il nome documento 
				if (atto.getDescNomeDocumento() != null){
					nomeDocumentoOrig = atto.getDescNomeDocumento();
				}
			
				int dotPos = nomeDocumentoOrig.lastIndexOf('.');
				nomeDocumentoOrig = nomeDocumentoOrig.substring(0, dotPos);
				String nomeDocumentoPdf = nomeDocumentoOrig.concat(".pdf");
				if(logger.isDebugEnabled()){
					logger.debug("    6.1) Nome del documento origine--> " + atto.getDescNomeDocumento() + "--> Nome dopo conversione " + nomeDocumentoPdf);
				}
				//System.out.println("    6.1) Nome del documento origine--> " + atto.getDescNomeDocumento() + "--> Nome dopo conversione " + nomeDocumentoOrig);
				
				attoDao.aggiornaTipoDocumento(nomeDocumentoPdf.toLowerCase(),atto.getId().getCodiKIdAtto(),BigInteger.valueOf(atto.getNumeDocumentoPdoc()),(int)atto.getId().getCodiKIdUffOwner());	
				
				
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		return response;
	}

	public BigInteger invioDocumentoPDoc(AttoForm attoForm , int idUfficio, long idProcedimento) {	
		
		Blob documentoBlob = null;
		byte[] b = null;
		BigInteger idDocumentoInseritoPDoc = new BigInteger("-1");
		// modifica documentale per scrivere sul campo Note l'id_atto e uff_owner
		//String note = "NOTE"; // note (valore fisso) 
		String note =  (String.valueOf(attoForm.getCodAtto()) + "|" + String.valueOf(idUfficio));
		//if(logger.isDebugEnabled()){
		//	logger.debug("    3.1) - Contenuto Campo Note ==> " + note );
		//}
		// fine modifica documentale 
		String oggetto = "OGGETTO";// descrizione del tipo di documento
		String tipoDocumento ="";
		String fileName = "";
		long idAtto = attoForm.getCodAtto();
		
		
		if(attoForm.getAllegato().getContentType() != null){
			tipoDocumento =  attoForm.getAllegato().getContentType();
		}else if(attoForm.getAllegato().getFileData() != null){
			tipoDocumento =  attoForm.getAllegato().getFileData().getContentType();
		}
		
		if (attoForm.getAllegato().getFileName() != null) {
			fileName = attoForm.getAllegato().getFileName();
		} else if(attoForm.getAllegato().getFileData() != null){
			fileName = attoForm.getAllegato().getFileData().getOriginalFilename();
		}
		
		//controllo se è già stato inviato
		TAtto tAtto = attoService.getTAttoById(attoForm.getCodAtto(), idUfficio);

		if(tAtto.getNumeDocumentoPdoc() != null){
			if(logger.isDebugEnabled()){
				logger.debug("    3.1) - DocumentaleService ==> ID Atto Documentale GIA' INSERITO: "+ tAtto.getNumeDocumentoPdoc() +" - " +  idAtto +" - " + idUfficio);
			}
			String codugnaList = null;
			if(tAtto.getDescVisibilita()!=null && tAtto.getDescVisibilita().length()>0)
				codugnaList = tAtto.getDescVisibilita();
			
			attoDao.inserisciDocumentoPDoc(tAtto.getNumeDocumentoPdoc(), idAtto, idUfficio, tipoDocumento, fileName, codugnaList); 
			return Utility.longToBigInteger(tAtto.getNumeDocumentoPdoc());
		}
		
		try {
			if ((attoForm.getAllegato() != null && attoForm.getAllegato().getFileData() != null && attoForm.getAllegato().getFileData().getSize() > 0)
					// 20180625: aggiunto controllo preventivo in and condition
					|| (attoForm.getAllegato() != null && attoForm.getAllegato().getDocumento() != null && attoForm.getAllegato().getDocumento().length() > 0)) {
				if (attoForm.getAllegato().getFileData() != null) {
					b = attoForm.getAllegato().getFileData().getBytes();
					documentoBlob = Hibernate.createBlob(b);
				}
				else if(attoForm.getAllegato().getDocumento() != null)
					documentoBlob = attoForm.getAllegato().getDocumento();
				
				TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUfficio);
				idDocumentoInseritoPDoc = inserimentoDocumento(oggetto, tipoDocumento, fileName, note , documentoBlob, attoForm.getDescVisibilita(), idUfficio);
				//modifica documentale
				boolean result = false;
				//fine modifica documentale 5 aprile 2018
				if (idDocumentoInseritoPDoc != null) {
					Long idDocumentoPDoc = idDocumentoInseritoPDoc.longValue();
					String codugnaList = null;
					if (attoForm.getDescVisibilita()!=null && attoForm.getDescVisibilita().length() > 0)
						codugnaList = attoForm.getDescVisibilita();
					result = attoDao.inserisciDocumentoPDoc(idDocumentoPDoc, idAtto, idUfficio, tipoDocumento, fileName, codugnaList); 
				}
				if (result != false) {
					if(logger.isDebugEnabled()){
						logger.debug("    3.1.1) - DocumentaleService ==> Ricerca ed eventualmente associa fascicolo ");
					}
				  String idFascicoloPDoc = procedimentoDao.ricercaFascicoloPDoc(idProcedimento);
				  if(idFascicoloPDoc!=null && idDocumentoInseritoPDoc!=null){
					  if(associaDocumentoFascicolo(idDocumentoInseritoPDoc, idFascicoloPDoc, utenzaPdoc))
						  procedimentoDao.associaDocFascicoloPDoc(idProcedimento,idAtto, idUfficio);
				  }
				  if(idDocumentoInseritoPDoc != null){
					  renderPdf(tipoDocumento, fileName, attoForm.getCodAtto(), idDocumentoInseritoPDoc, idUfficio, utenzaPdoc);
				  }
				}
			}
		} catch (SQLException e) {
				logger.debug("DocumentaleService ==> ERRORE!! attoForm.getAllegato().getDocumento().length()");
				e.printStackTrace();
		}
		
		if (logger.isDebugEnabled()) {
			if (idDocumentoInseritoPDoc != null){
				logger.debug("    3.1.2) - DocumentaleService ==> Identificativo Documentale: "+ idDocumentoInseritoPDoc +" - atto n. " +  idAtto +" - ufficio  " + idUfficio);
			}else{
				logger.debug("    3.1.2) - DocumentaleService ==> ERRORE!!  Identificativo Documentale non rilasciato: " + " - atto n. " +  idAtto +" - ufficio  " + idUfficio);
			}
			
		}
		return idDocumentoInseritoPDoc;
	}

	public boolean associaDocumentoFascicolo(BigInteger idDocumento, String idFascicolo, TAnUtenzePdoc utenzaPdoc){
		boolean response = false;
		try {
			response = documentaleClient.associaDocumentoFascicolo(idDocumento, idFascicolo, utenzaPdoc);
			if ( response){
				System.out.println("    4.1.4) Esito Positivo associazione Documento Fascicolo : " + idFascicolo);
			}
			
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public boolean estendiSicurezza(BigInteger idDocumento, String listCodugna, TAnUtenzePdoc utenzaPdoc){
		boolean response = false;
		if(listCodugna==null | listCodugna.length()==0)
			return false;
		StringTokenizer codugnaTokenizer = new StringTokenizer(listCodugna, SEPARATORE_CAMPI_IN_STRINGA_CODUGNA);
		while (codugnaTokenizer.hasMoreElements())
		{
			try {
				String codUgnaStr = codugnaTokenizer.nextElement().toString();
				int codugnaInt = commonDao.getIdDocumentale(codUgnaStr);
				if(codugnaInt != 0) //F.C. Ticket#202202240113 SIT-MP - errore download pdf e registrazione scambio 
					response = documentaleClient.estendiSicurezza(idDocumento, BigInteger.valueOf(codugnaInt), utenzaPdoc);
				else
					return false;
			} catch (ConnectException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public BigInteger copiaDocumento(BigInteger idDocumento,String oggetto, String tipoDocumento, String fileName, String note, String listCodugna, int idUffOwner)throws ConnectException {
		BigInteger response;
		List<BigInteger> codugnaList = new ArrayList<BigInteger>();
		if(listCodugna!=null && listCodugna.length()>0)
		{
			StringTokenizer codugnaTokenizer = new StringTokenizer(listCodugna, SEPARATORE_CAMPI_IN_STRINGA_CODUGNA);
			while (codugnaTokenizer.hasMoreElements())
			{
				String codUgnaStr = codugnaTokenizer.nextElement().toString();
				int codugnaInt = commonDao.getIdDocumentale(codUgnaStr);
				if(codugnaInt != 0)
					codugnaList.add(BigInteger.valueOf(codugnaInt));
			}
		}
		try {
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
			response = documentaleClient.copiaDocumento(idDocumento, oggetto, tipoDocumento, fileName, note, codugnaList, utenzaPdoc);
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}


}
