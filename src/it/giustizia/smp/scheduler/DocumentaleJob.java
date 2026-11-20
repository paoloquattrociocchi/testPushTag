package it.giustizia.smp.scheduler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.ConnectException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dto.pg_atto.AttoDocumentaleDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TTpTipoFilePdoc;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import javassist.expr.Cast;


public class DocumentaleJob{
	
	@Autowired
	private UserRoutingDataSource	myDataSource;
	@Autowired
	private AttoService				attoService;
	@Autowired
   	private IDocumentaleService		documentaleService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private ProcedimentoDao			procedimentoDao;
	@Autowired
	private AttoDao					attoDao;
	@Autowired
	private CommonService			commonService;
	@Autowired
    private Mapper 					mapperBean;
	
	// modifica DOCUMENTALE per gestire il logger su questa classe
	private Logger logger = Logger.getLogger(getClass());
	// fine modifica documentale
	    
//	 @Scheduled(cron="0 0/2 9-19 * * ?")//scheduler ogni giorno dalle 9 alle 17 ogni 3 minuti
//	 @Scheduled(cron="0 0/45 1-2 * * ?")//scheduler ogni giorno dalle 1 alle 2 ogni 45 minuti
	    public void scheduledMethod()
	    {
	    	//GC 15/07/2020 - INIZIO MODIFICA PDOC: CREAZIONE DELLA VARIABILE SCHEMA
	    	String schema = "";
	    	schema = "SMP";
		 	System.out.println("1) - Inizio Documentale Job ->" + schema + "<-");		 	
		 	elaboraJob(schema);
		 	System.out.println("8) - Fine Documentale Job ->" + schema + "<-");
		 	
		 	schema = "SMP_GIU";
		 	System.out.println("1) - Inizio Documentale Job ->" + schema + "<-");		 	
		 	elaboraJob(schema);
		 	myDataSource.setSchemaDocumentale("");
		 	
		 	System.out.println("8) - Fine Documentale Job ->" + schema + "<-");
		 	
	    }
	 
	 private void elaboraJob(String schema){
		 myDataSource.selectConnection(schema);
		 if(testConnessione())
		 {
			 creazioneFascicoli(schema);
			 invioAtti();
			 associaAttoFascicolo();
			 conversionePdf();
			 monitorRenderPdf();
			 cleanBlob();
		 }else{
			 System.out.println("    1.2) - ERRORE CONNESSIONE !! Verificare Servizio Documentale se raggiungibile !!"); 
		 }
	 }
	 
	 private boolean testConnessione(){
		 int idUffOwner = attoService.getUffOwnerTestDocumentale();
		 if(logger.isDebugEnabled()){
				logger.debug("    1.1) TEST CONNESSIONE - Ufficio pilota per test connessione: " + Integer.toString(idUffOwner) + ";" );
		 }
		 return documentaleService.esitoTestConnessione(idUffOwner);
	 }
	
	 private void creazioneFascicoli(String schema){
		 
		// test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018
		if(logger.isDebugEnabled()){
				logger.debug("T01 - TEST) - Inizio Atti da inviare" );
				
			Map<Long,String> mapTestFascicoli = procedimentoDao.getTestProcedimentiJobDocumentale();
			if(mapTestFascicoli != null){
				System.out.println("    T01 - Test ) --> Lista Fascicoli da creare/inviare: " + mapTestFascicoli.size());
				for (Map.Entry<Long, String> entry : mapTestFascicoli.entrySet())
				{
					System.out.println("  T01 - Test ) --> Valori : " + entry.getKey() + " / " + entry.getValue());
		 	   
				}
			}else{
	 		System.out.println("  T01 - Test ) --> " + " Non ci sono Fascicoli da Creare/inviare!!"  );
			}	
		}
		
		// fine test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018
	 	Map<Long,String> mapFascicoli = procedimentoDao.getProcedimentiJobDocumentale();
	 	
	 	System.out.println("2) - Creazione Fascicoli" );
	 	if(mapFascicoli != null){
	 		System.out.println("    2.1) - Lista Fascicoli da creare:" + mapFascicoli.size());
		 	for (Map.Entry<Long, String> entry : mapFascicoli.entrySet())
		 	{
		 	   System.out.println("  2.1)" + entry.getKey() + " / " + entry.getValue());
		 	   procedimentoService.inserisciECreaFascicoloPDoc(entry.getKey(),entry.getValue());
		 	}
	 	}else{
	 		System.out.println("  2.1)" + " Non ci sono Fascicoli da Creare!!"  );
	 	}
	 }
	 
	 private void invioAtti(){
		 List<AttoDocumentaleDto> lista = attoDao.getDocumentiJobDocumentale();
		 
		 if(logger.isDebugEnabled()){
				logger.debug("3) - Inizio Atti da inviare" );
		 }
		 if(lista != null){
			 if(logger.isDebugEnabled()){
					logger.debug("    3.1) - Lista Atti da inviare:" + lista.size());
			 }
			 //System.out.println("3) - Lista Atti da inviare:" + lista.size());
			 Iterator<AttoDocumentaleDto> it = lista.iterator();
			 while(it.hasNext())
			 {
				 AttoForm attoForm = new AttoForm();
				 AttoDocumentaleDto attoDto = it.next();
				 if (attoDto.getDESC_TIPO_DOCUMENTO() == null){
					 if(logger.isDebugEnabled()){
							logger.debug("    3.1) - Anomalia ! Tipo Documento non valorizzato :" + attoDto.getCODI_K_ID_ATTO() + "|" + attoDto.getCODI_K_ID_UFF_OWNER());
					 }
					 attoDto.setDESC_TIPO_DOCUMENTO("APPLICATION/MSWORD");
				 }
				 mapperBean.map(attoDto, attoForm);
				 if(attoDto!=null && attoDto.getFILE_DOCUMENTO()!=null)
				 {
					 attoForm.getAllegato().setDocumento(attoDto.getFILE_DOCUMENTO());
				 }
				 BigDecimal tmpIdProc = (BigDecimal)attoDto.getCODI_K_ID_PROC();
				 Long idProc = tmpIdProc.longValue();
				 BigDecimal tmpUfficio = (BigDecimal)attoDto.getCODI_K_ID_UFF_OWNER();
				 int idUfficio = tmpUfficio.intValue();
				 documentaleService.invioDocumentoPDoc(attoForm, idUfficio, idProc);
			 }
		 }
		 if(logger.isDebugEnabled()){
				logger.debug("3) - Fine  Atti da inviare " ); 
		 }
	 }
	 
	 private void associaAttoFascicolo(){
		 List<TRelAttoProc> relAttoProc = procedimentoDao.getAttoProcDaAssociare();
		 if(logger.isDebugEnabled()){
				logger.debug("4) - Associa Atto Fascicolo : " + relAttoProc.size());
			}
		 //System.out.println("4) - Associa Atto Fascicolo : " + relAttoProc.size());
		 for (TRelAttoProc tRelAttoProc : relAttoProc) {
			 String descFascicoloPdoc = procedimentoDao.ricercaFascicoloPDoc(tRelAttoProc.getTProcedimento().getCodiKIdProc());
			 if(descFascicoloPdoc!=null && descFascicoloPdoc.length()>1)
			 {
				 Long idDocumentoPdoc = attoDao.getIdDocumentale(tRelAttoProc.getTAtto().getId().getCodiKIdAtto(), tRelAttoProc.getId().getCodiKIdUffOwner());
				 if(idDocumentoPdoc != null)
				 {
					 TAnUtenzePdoc utenzaPdoc = commonService.getUtenzaPdoc(tRelAttoProc.getId().getCodiKIdUffOwner());
					 if(documentaleService.associaDocumentoFascicolo(new BigInteger(idDocumentoPdoc.toString()), descFascicoloPdoc, utenzaPdoc))
							procedimentoDao.associaDocFascicoloPDoc(tRelAttoProc.getTProcedimento().getCodiKIdProc(),
									tRelAttoProc.getTAtto().getId().getCodiKIdAtto(), tRelAttoProc.getId().getCodiKIdUffOwner());
				 }
			 }
			//GC 10/07/2020: Modifica per miglioramento log pdoc
			 else
			 {
				 logger.debug("4) Associazione Atto/Fascicolo non possibile; Al fascicolo di SITMP non è associato nessun fascicolo PDOC." );
			 }
			 //GC 10/07/2020: Fine Modifica per miglioramento log pdoc
		 }
		 if(logger.isDebugEnabled()){
				logger.debug("4) - Fine Associa Atto Fascicolo  " );
			}
	 }
	 
	 private void conversionePdf(){
		 List<TAtto> lista = attoDao.listAttiConversionePdfJob();
		 
		 if(logger.isDebugEnabled()){
				logger.debug("5) - Totale Atti da processare : " + lista.size());
			}
		 
		 if(lista.size() > 0 ){
			
			 Iterator<TAtto> it = lista.iterator();
			 while(it.hasNext())
			 {
				TAtto atto = it.next();
				
				TAnUtenzePdoc utenzaPdoc = commonService.getUtenzaPdoc(atto.getId().getCodiKIdUffOwner());
				documentaleService.renderPdf(atto.getDescTipoDocumento(), atto.getDescNomeDocumento(), atto.getId().getCodiKIdAtto(),BigInteger.valueOf(atto.getNumeDocumentoPdoc()), atto.getId().getCodiKIdUffOwner(), utenzaPdoc);
							
			 }
		 }

		 if(logger.isDebugEnabled()){
				logger.debug("5) - Fine Conversione Pdf");
		}
		 
	 }
	 
	 private void monitorRenderPdf(){
		 List<TAtto> lista = attoDao.listMonitorRenderPdfJob();
		 if(logger.isDebugEnabled()){
				logger.debug("6) - Totale Atti per render pdf : " + lista.size());
			}
		 //System.out.println("6) - Inizio Monitoraggio conversione pdf : " + lista.size());
		 if(lista != null){
			 Iterator<TAtto> it = lista.iterator();
			 while(it.hasNext())
			 {
				TAtto atto = it.next();
				try {
					TAnUtenzePdoc utenzaPdoc = commonService.getUtenzaPdoc(atto.getId().getCodiKIdUffOwner());
					documentaleService.monitorRenderPdf(atto, utenzaPdoc);
				} 
				catch (ConnectException e) {
					e.printStackTrace();
				}
			 }
		 }
		 if(logger.isDebugEnabled()){
				logger.debug("6) - Fine render Pdf");
			}
		 //System.out.println("6) - Fine Monotoraggio Pdf");
	 }
	 
	 private void cleanBlob(){
		 if(logger.isDebugEnabled()){
				logger.debug("7) - Pulizia Blob");
			}
		 //System.out.println("7) - Cancellazione Blob");
		 attoDao.cleanBlobJobDocumentale();
	 }


}
