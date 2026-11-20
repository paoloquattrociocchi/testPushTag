package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RisultatoComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.ServiziTrasmissioneServiceClient;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiziTrasmissioneService  {

	@Autowired
	private ServiziTrasmissioneServiceClient serviceClient;

	@Autowired
	private AttoDao attoDao;

	@Autowired
	private CommonDao commonDao;

	/***
	 * Invio di una comunicazione attraverso il ws ServiziTrasmissione di glmp
	 * @param descUtente
	 * @param tUtente
	 * @param idUffOwn
	 * @param attoForm
	 * @param procedimentoForm
	 * @return
	 
	public RisultatoComunicazione inviaComunicazione(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm,
			 ProcedimentoForm procedimentoForm) {

		
		InviaComunicazione request = new InviaComunicazione();
		RisultatoComunicazione response = serviceClient.inviaComunicazione(request);
		return response;
	}
	 */
	
	/*
	SoggettoForm soggetto = attoForm.getSoggetti().get(0);
	
	// Estraggo il tipo atto per leggere i valori del cassellario
	String idCatAtto = attoForm.getCategoriaAtto().getId();
	TTpCatAtto catAtto = commonDao.getCatAttoById(Integer.parseInt(idCatAtto)); 
	String nscTipoProvvedimento = "" + catAtto.getDescCodiNSC(); 
	
	// Estraggo l'ufficio per leggere i valori del casellario
	TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwn); 
	
	// Estraggo il tipo ufficio corrente
	TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioById(ufficioCorrente.getTTpTipoUfficio().getCodiKIdTipoUff());
	String nscUfficioCorrenteCodiAutorita = tipoUfficio.getDescCodiAutoritaNSC(); 
	String nscUfficioCorrenteCodiUfficio = tipoUfficio.getDescCodiUfficioNSC(); 

	// Estraggo l'ufficio per leggere i valori del casellario
	TAnComuni comuneUfficioCorrente = commonDao.getComuneById(ufficioCorrente.getTAnComuni().getCodiKIdComune());
	String nscComuneUfficioCorrente = comuneUfficioCorrente.getDescCodiNSC(); 

	String nscComuneNascita = null;
	// Estraggo l'ufficio per leggere i valori del casellario
	if (soggetto.getComuneNascita().getId()!=null) {
		TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneNascita().getId()));
		nscComuneNascita = comuneNascita.getDescCodiNSC(); 
	}
	
	String nscComuneMorte = null;
	// Estraggo l'ufficio per leggere i valori del casellario
	if (soggetto.getComuneMorte().getId()!=null) {
		TAnComuni comuneMorte = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneMorte().getId()));
		nscComuneMorte = comuneMorte.getDescCodiNSC(); 
	}
	
	String nscStatoNascita = null;
	if (soggetto.getStatoNascita().getId()!=null) {
		TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoNascita().getId()));
		nscStatoNascita = statoNascita.getDescCodiNSC(); 
	}
	
	String nscCodiceStatoEsteroMorte = null;
	if (soggetto.getStatoMorte().getId()!=null) {
		TAnStato statoMorte = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoMorte().getId()));
		nscCodiceStatoEsteroMorte = statoMorte.getDescCodiNSC();
	}
	String nscCodiceMisuraPrevenzione = null;
	TTpTipoMisura tipoMisura = commonDao.getTipoMisuraById(Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()));
	nscCodiceMisuraPrevenzione = tipoMisura.getDescCodiNSC();

	DATIUFFICIO datiUfficio = creaDatiUfficio(nscComuneUfficioCorrente,nscUfficioCorrenteCodiUfficio);
	DATIUTENTE datiUtente = creaDatiUtente(tUtente,datiUfficio);
	CHIAVI chiavi = creaChiavi(soggetto,comunicazioneForm);
			
	
	ANAGRAFICA anagrafica = new ANAGRAFICA();
	DATIANAGRAFICI datiAnagrafici = creaDatiAnagrafici(soggetto, nscComuneNascita, nscStatoNascita, nscComuneMorte, nscCodiceStatoEsteroMorte);
			
	
	anagrafica.setDATIANAGRAFICI(datiAnagrafici);

	// Impostazione delle informazioni relative al procedeimento/atto definitivo
	PROCEDIMENTO procedimento = creaDatiProcedimento(soggetto, attoForm, procedimentoForm, comunicazioneForm, nscTipoProvvedimento, nscCodiceMisuraPrevenzione, nscUfficioCorrenteCodiAutorita, nscComuneUfficioCorrente);
	
	//Inserimento del procedimanto
	anagrafica.setPROCEDIMENTO(procedimento);

	// Impostazione dei dati relativi all'operazione da effettuare
	DATIOPERAZIONE operazione = new DATIOPERAZIONE();

	operazione.setENTITA(ENTITA.P);// Nel caso di primo invio è P
	if (idNSC!=null && !idNSC.trim().equals("")) {
		// Se l'id del soggetto casellario è valorizzato (caso di risposta con omonimia) 
		// lo l'operazione deve essere impostata a SKIP_INSERT_S e  valorizzato l'idNSC
		operazione.setOPERAZIONE(OPERAZIONE.SKIP_INSERT_S);
		chiavi.setKANSC(BigInteger.valueOf(Long.parseLong(idNSC)));
	} else {
		operazione.setOPERAZIONE(OPERAZIONE.INSERT);
	}

	// Impostazione della request
	DATICHIAMATATRASFERIMENTO request = new DATICHIAMATATRASFERIMENTO();
	request.setANAGRAFICA(anagrafica);
	request.setCHIAVI(chiavi);
	request.setDATIOPERAZIONE(operazione);
	request.setDATIUTENTE(datiUtente);
	request.setPROGANAGRAFICASIPPI(BigInteger.valueOf(soggetto.getIdSoggetto())); 
	 */
	
	
	/*
	private PROCEDIMENTO creaDatiProcedimento(SoggettoForm soggetto,
			AttoForm attoForm,
			ProcedimentoForm procedimentoForm, 
			ComunicazioneForm comunicazioneForm, 
			String nscTipoProvvedimento,
			String nscCodiceMisuraPrevenzione,
			String nscUfficioCorrenteCodiAutorita,
			String nscComuneUfficioCorrente) {
		// Impostazione delle informazioni relative al procedeimento/atto definitivo
		PROCEDIMENTO procedimento = new PROCEDIMENTO();
		
		// Impostazione della info della misura di prevenzione
		ArrayMisurePrevenzione arrayMisurePrevenzione = new ArrayMisurePrevenzione();
		MISURADIPREVENZIONE misuradiprevenzione = new MISURADIPREVENZIONE();
		DURATA durataMisuta = new DURATA();
		durataMisuta.setANNIDURATA(BigInteger.valueOf((soggetto.getMisura().getAnni()!=null?soggetto.getMisura().getAnni():0)));
		durataMisuta.setMESIDURATA(BigInteger.valueOf((soggetto.getMisura().getMesi()!=null?soggetto.getMisura().getMesi():0)));
		durataMisuta.setGIORNIDURATA(BigInteger.valueOf((soggetto.getMisura().getGiorni()!=null?soggetto.getMisura().getGiorni():0)));
		misuradiprevenzione.setDURATAMISURA(durataMisuta);
		misuradiprevenzione.setIMPORTO(soggetto.getMisura().getImporto()); 
		misuradiprevenzione.setPROGMISPREVENZIONE(BigInteger.valueOf(soggetto.getMisura().getIdMisuraPersonale())); // ID Della misura di prevenzione 
		misuradiprevenzione.setCODITIPOMP(nscCodiceMisuraPrevenzione);
		arrayMisurePrevenzione.getMISURADIPREVENZIONE().add(misuradiprevenzione);
		procedimento.setArrayMisurePrevenzione(arrayMisurePrevenzione); 
		
		// Impugnazioni primo grado
		if (attoForm.getImpugnazioniPrimoGrado()!=null && attoForm.getImpugnazioniPrimoGrado().size()>0) {
			 ImpugnazioneCassellarioDTO impugnazioneCassellarioDTO = attoForm.getImpugnazioniPrimoGrado().get(0);
			 
			 ArrayPrimoGrado arrayPrimoGrado = new ArrayPrimoGrado();
			PRIMOGRADO primoGrado = new PRIMOGRADO();
			IMPUGNAZIONE impugnazione = new IMPUGNAZIONE();
			primoGrado.setIMPUGNAZIONE(impugnazione); 
			impugnazione.setCODIAUTORITA(impugnazioneCassellarioDTO.getCODI_AUTORITA_NSC());
			impugnazione.setCODISEDEAUTORITAPRINDIST(impugnazioneCassellarioDTO.getCODI_SEDE_AUTORITA_NSC());
			impugnazione.setCODITIPORIFERIMENTO(impugnazioneCassellarioDTO.getDESC_ESITO_DEC_DECISORIO_NSC_1());
			impugnazione.setDATAPROVVEDIMENTO(convertiData(impugnazioneCassellarioDTO.getDATA_EMISSIONE_DECISORIO()));
			impugnazione.setIDENTIFICATIVO(BigInteger.valueOf(impugnazioneCassellarioDTO.getID_ATTO_DECISORIO().longValue()));
			
			arrayPrimoGrado.getPRIMOGRADO().add(primoGrado);
			procedimento.setArrayPrimoGrado(arrayPrimoGrado);
		}
			
		
		DATIPROVVEDIMENTO datiProvvedimento = new DATIPROVVEDIMENTO(); // Provvedimento = ATTO
		datiProvvedimento.setNUMEROPROVVEDIMENTO(attoForm.getNumeAtto()); 
		datiProvvedimento.setANNOPROVVEDIMENTO(BigInteger.valueOf(Long.parseLong(attoForm.getNumeAnnoAtto()))); 
		datiProvvedimento.setNUMEROREGISTROGENERALE("" + procedimentoForm.getNumero());
		datiProvvedimento.setANNOREGISTROGENERALE(BigInteger.valueOf(procedimentoForm.getAnno()));
		datiProvvedimento.setDATAPROVVEDIMENTO(convertiData(attoForm.getDataEmissione())); 
		datiProvvedimento.setDESCNOTE(attoForm.getNote());
		datiProvvedimento.setDATAPASSAGGIOGIUDICATO(convertiData(comunicazioneForm.getDataDefinitivita()));
		datiProvvedimento.setPROGPROVV(BigInteger.valueOf(comunicazioneForm.getIdAttoEsecutivo()));
		 
		datiProvvedimento.setCODITIPOPROVV(nscTipoProvvedimento); 
		datiProvvedimento.setCODIAUTORITA(nscUfficioCorrenteCodiAutorita); 
		datiProvvedimento.setCODISEDEAUTORITAPRINDIST(nscComuneUfficioCorrente); 

		procedimento.setDATIPROVVEDIMENTO(datiProvvedimento);
		return procedimento;
	}
	*/
	/***
	 * Crea l'oggetto con i dati anagrafici da passare al servizio
	 * @param soggetto
	 * @param nscComuneNascita
	 * @param nscStatoNascita
	 * @param nscComuneMorte
	 * @param nscCodiceStatoEsteroMorte
	 * @return
	
	private DATIANAGRAFICI creaDatiAnagrafici(SoggettoForm soggetto,String nscComuneNascita,String nscStatoNascita,String nscComuneMorte,String nscCodiceStatoEsteroMorte) {
		// Impostazione delle informazioni anagrafiche del soggetto
		DATIANAGRAFICI datiAnagrafici = new DATIANAGRAFICI();
		datiAnagrafici.setPROGANAGRAFICA(BigInteger.valueOf(soggetto.getIdSoggetto()));
		datiAnagrafici.setPERSCOGNOME(soggetto.getCognome());
		datiAnagrafici.setPERSNOME(soggetto.getNome());
		datiAnagrafici.setCODILUOGONASCITA(nscComuneNascita); 
		datiAnagrafici.setCODISTATOESTERONAS(nscStatoNascita ); 
		datiAnagrafici.setDESCCOMUNEESTERO(soggetto.getDescrizioneComuneEsteroNascita() ); 
		datiAnagrafici.setDATANASCITA(convertiData(soggetto.getDataNascita()));
		datiAnagrafici.setCODIFISCALE(soggetto.getCodiceFiscale());
		datiAnagrafici.setCODIIMPRONTADIGITALE(null); // null da specifica
		datiAnagrafici.setPERSPATERNITA(soggetto.getNomePaternita());
		datiAnagrafici.setPERSCOGNOMEMADRE(soggetto.getCognomeMaternita());
		datiAnagrafici.setPERSNOMEMADRE(soggetto.getNomeMaternita());
		// datiAnagrafici.setDESCANNOTAZIONE( ); //Francesco dice di lasciare a null
		datiAnagrafici.setFLAGDECEDUTOSN((soggetto.getDataMorte() != null ? FLAGDECESSO.S : FLAGDECESSO.N));
		datiAnagrafici.setCODILUOGOMORTE(nscComuneMorte ); 
		datiAnagrafici.setCODISTATOESTEROMORTE(nscCodiceStatoEsteroMorte ); 
		datiAnagrafici.setNUMEATTOMORTE(null); // null da specifica
		datiAnagrafici.setDATAMORTE(convertiData(soggetto.getDataMorte()));
		datiAnagrafici.setSESSO(convertiSesso(soggetto.getSesso()));
		return datiAnagrafici;
	}
	 */
	/***
	 * Cancellazione del Provvedimento inviato al casellario
	 * 
	 * @param idComunicazione
	
	public DATIRISPOSTATRASFERIMENTO cancellaProvvedimento(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm,
			ComunicazioneForm comunicazioneForm, ProcedimentoForm procedimentoForm) {
	
		// Estraggo il tipo atto per leggere i valori del cassellario
		String idCatAtto = attoForm.getCategoriaAtto().getId();
		TTpCatAtto catAtto = commonDao.getCatAttoById(Integer.parseInt(idCatAtto)); 
		String nscTipoProvvedimento = "" + catAtto.getDescCodiNSC(); 
	
		
		SoggettoForm soggetto = attoForm.getSoggetti().get(0);
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnUfficio ufficioCorrente = commonDao.getUfficioById((int) idUffOwn);
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnComuni comuneUfficioCorrente = commonDao.getComuneById(ufficioCorrente.getTAnComuni().getCodiKIdComune());
		String nscComuneUfficioCorrente = comuneUfficioCorrente.getDescCodiNSC();
		// Estraggo il tipo ufficio corrente
		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioById(ufficioCorrente.getTTpTipoUfficio().getCodiKIdTipoUff());
		String nscUfficioCorrenteCodiAutorita = tipoUfficio.getDescCodiAutoritaNSC();
		String nscUfficioCorrenteCodiUfficio = tipoUfficio.getDescCodiUfficioNSC();
		
		
		String nscComuneNascita = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneNascita().getId()!=null) {
			TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneNascita().getId()));
			nscComuneNascita = comuneNascita.getDescCodiNSC(); 
		}
		
		String nscComuneMorte = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneMorte().getId()!=null) {
			TAnComuni comuneMorte = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneMorte().getId()));
			nscComuneMorte = comuneMorte.getDescCodiNSC(); 
		}
		
		String nscStatoNascita = null;
		if (soggetto.getStatoNascita().getId()!=null) {
			TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoNascita().getId()));
			nscStatoNascita = statoNascita.getDescCodiNSC(); 
		}
		
		String nscCodiceStatoEsteroMorte = null;
		if (soggetto.getStatoMorte().getId()!=null) {
			TAnStato statoMorte = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoMorte().getId()));
			nscCodiceStatoEsteroMorte = statoMorte.getDescCodiNSC();
		}		
		String nscCodiceMisuraPrevenzione = null;
		TTpTipoMisura tipoMisura = commonDao.getTipoMisuraById(Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()));
		nscCodiceMisuraPrevenzione = tipoMisura.getDescCodiNSC();
	
		DATIUFFICIO datiUfficio = creaDatiUfficio(nscComuneUfficioCorrente, nscUfficioCorrenteCodiUfficio);
		DATIUTENTE datiUtente = creaDatiUtente(tUtente, datiUfficio);
		CHIAVI chiavi = creaChiavi(soggetto, comunicazioneForm);
		DATICHIAMATATRASFERIMENTO request = new DATICHIAMATATRASFERIMENTO();
	
		// Imposto i dati anagrafici
		ANAGRAFICA anagrafica = new ANAGRAFICA();
		// Impostazione delle informazioni relative al procedeimento/atto definitivo
		PROCEDIMENTO procedimento = creaDatiProcedimento(soggetto, attoForm, procedimentoForm, comunicazioneForm, nscTipoProvvedimento, nscCodiceMisuraPrevenzione, nscUfficioCorrenteCodiAutorita, nscComuneUfficioCorrente);
				
		DATIANAGRAFICI datiAnagrafici = creaDatiAnagrafici(soggetto, nscComuneNascita, nscStatoNascita, nscComuneMorte, nscCodiceStatoEsteroMorte);
		anagrafica.setDATIANAGRAFICI(datiAnagrafici);
		anagrafica.setPROCEDIMENTO(procedimento);
		request.setANAGRAFICA(anagrafica);		
		
		// Estraggo la chiave del soggetto NSC per rimandarla sulla chiavi della cancellazione 
		TComunicazione tComunicazione = attoDao.getTComunicazioneById(comunicazioneForm.getIdComunicazione());
		try {
			chiavi.setKANSC(unmarhallerRispostaTrasferimento(tComunicazione.getFileEstrattoCertif()).getCHIAVI().getKANSC());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setPROGANAGRAFICASIPPI(BigInteger.valueOf(soggetto.getIdSoggetto()));
		
		// Impostazione dei dati relativi all'operazione da effettuare
		DATIOPERAZIONE operazione = new DATIOPERAZIONE();
		operazione.setENTITA(ENTITA.P);// Nel caso di primo invio è P
		operazione.setOPERAZIONE(OPERAZIONE.DELETE);
		request.setCHIAVI(chiavi);
		request.setDATIOPERAZIONE(operazione);
		request.setDATIUTENTE(datiUtente);
		
		// Invocazione del servizio del casellario
		return casellarioServiceClient.inviaProvvedimento(request);

	}
	
	private CHIAVI creaChiavi(SoggettoForm soggetto, ComunicazioneForm comunicazioneForm) {
		CHIAVI chiavi = new CHIAVI();
		chiavi.setKANSC(null); // Imposta a nulla su invio nuovo scheda
		chiavi.setKASIPPI(BigInteger.valueOf(soggetto.getIdSoggetto()));

		// Impostato con l'id dell'atto definitivo
		chiavi.setKPSIPPI(BigInteger.valueOf(comunicazioneForm.getIdAttoEsecutivo()));
		return chiavi;
	}

	private DATIUTENTE creaDatiUtente(TUtente tUtente, DATIUFFICIO datiUfficio) {
		DATIUTENTE datiUtente = new DATIUTENTE();
		datiUtente.setCOGNOMEUTENTE(tUtente.getCognome());
		datiUtente.setNOMEUTENTE(tUtente.getNome());
		datiUtente.setUSERNAMESIPPI(tUtente.getCodiceFiscale());
		System.out.println("Username usata:" + tUtente.getCodiceFiscale());
		datiUtente.setDATIUFFICIO(datiUfficio);
		return datiUtente;
	}

	private DATIUFFICIO creaDatiUfficio(String nscComuneUfficioCorrente, String nscUfficioCorrenteCodiUfficio) {
		DATIUFFICIO datiUfficio = new DATIUFFICIO();
		datiUfficio.setCODICESEDEUFFICIO(nscComuneUfficioCorrente);
		datiUfficio.setCODICETIPOUFFICIO(nscUfficioCorrenteCodiUfficio);

		return datiUfficio;
	}
 */
	/**
	 * Crea l'oggetto di invocazione per ogni operation
	
	private Blob serializeInBlob(byte[] byteArray) {
		return Hibernate.createBlob(byteArray);
	}
 */
	/**
	 * Esegue il marshaller per la response e la request del servizio
	 
	private byte[] marshaller(Object request) {
		byte[] firmaBytes = new byte[1000];
		// ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			JAXBContext contextObj = JAXBContext.newInstance(request.getClass());
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerObj.marshal(request, bos);
			firmaBytes = bos.toByteArray();
			// bis = new ByteArrayInputStream(bos.toByteArray());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return firmaBytes;
	}

	public DATIRISPOSTATRASFERIMENTO unmarhallerRispostaTrasferimento(Blob blobFile) throws JAXBException, IOException {
		DataHandler dh = serializzaFile(blobFile);
		DATIRISPOSTATRASFERIMENTO risposta = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		dh.writeTo(bout);
		JAXBContext jc = JAXBContext.newInstance(DATIRISPOSTATRASFERIMENTO.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
		Unmarshaller u = jc.createUnmarshaller();
		risposta = (DATIRISPOSTATRASFERIMENTO) u.unmarshal(bis);
		return risposta;
	}

	public DATICHIAMATATRASFERIMENTO unmarhallerChiamataTrasferimento(Blob blobFile) throws JAXBException, IOException {
		DataHandler dh = serializzaFile(blobFile);
		DATICHIAMATATRASFERIMENTO chiamata = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		dh.writeTo(bout);
		JAXBContext jc = JAXBContext.newInstance(DATICHIAMATATRASFERIMENTO.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
		Unmarshaller u = jc.createUnmarshaller();
		chiamata = (DATICHIAMATATRASFERIMENTO) u.unmarshal(bis);
		return chiamata;
	}

	private DataHandler serializzaFile(Blob blobFile) {
		DataHandler dh = null;
		try {
			InputStream is = blobFile.getBinaryStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int next = is.read();
			while (next > -1) {
				bos.write(next);
				next = is.read();
			}
			bos.flush();
			byte[] fileContent = bos.toByteArray();
			ByteArrayDataSource rawData = new ByteArrayDataSource(fileContent);
			dh = new DataHandler(rawData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return dh;
		} catch (IOException e) {
			e.printStackTrace();
			return dh;
		} catch (SQLException e) {
			e.printStackTrace();
			return dh;
		}
		return dh;
	}
*/
	/**
	 * Converte un oggetto java date nella data accettata dal servizio del casellario
	 * 
	 * @param date
	 * @return
	
	private static SESSO convertiSesso(String sessoString) {
		if (sessoString.toUpperCase().equals("M")) {
			return SESSO.M;
		} else if (sessoString.toUpperCase().equals("F")) {
			return SESSO.M;
		}
		return null;
	}
 */
	/**
	 * Converte un oggetto java date nella data accettata dal servizio del casellario
	 * 
	 * @param date
	 * @return
	
	private static DATA convertiData(Date date) {
		if (date == null)
			return null;
		DATA data = new DATA();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		data.setANNO("" + year);
		data.setMESE("" + month);
		data.setGIORNO("" + day);
		return data;
	}
 */
	/**
	 * Converte un oggetto DATA del servizio in una data date
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	
	public Date convertiData(DATA data) throws ParseException {
		if (data == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(data.getGIORNO() + "/" + data.getMESE() + "/" + data.getANNO());
	}

	public List<OmonimoForm> getListaOmonimi(ComunicazioneForm comunicazioneForm) throws JAXBException, IOException {
		ArrayList<OmonimoForm> listOmonimoForms = new ArrayList<OmonimoForm>();
		TComunicazione tComunicazione = attoDao.getTComunicazioneById(comunicazioneForm.getIdComunicazione());
		DATIRISPOSTATRASFERIMENTO risposta = unmarhallerRispostaTrasferimento(tComunicazione.getFileEstrattoCertif());

		for (OMONIMO omonimo : risposta.getArrayOmonimi().getOMONIMO()) {
			TAnStato statoEstero = commonDao.getStatoByIdCasellario(omonimo.getDATIANAGRAFICI().getCODISTATOESTERONAS());
			String descStato = "";
			if (statoEstero != null) {
				descStato = statoEstero.getDescStato();
			}
			String nome = omonimo.getDATIANAGRAFICI().getPERSNOME() == null ? "" : omonimo.getDATIANAGRAFICI().getPERSNOME().toUpperCase();
			String cognome = omonimo.getDATIANAGRAFICI().getPERSCOGNOME() == null ? "" : omonimo.getDATIANAGRAFICI().getPERSCOGNOME().toUpperCase();
			String comuneNascita = "";
			TAnComuni anComuni = commonDao.getComuneByIdCasellario(omonimo.getDATIANAGRAFICI().getCODILUOGONASCITA());
			if (anComuni != null) {
				comuneNascita = anComuni.getDescComune();
			}
			if (omonimo.getDATIANAGRAFICI().getDESCCOMUNEESTERO() != null) {
				comuneNascita += omonimo.getDATIANAGRAFICI().getDESCCOMUNEESTERO();
			}
			String natoNata = "nato";
			if (omonimo.getDATIANAGRAFICI().getSESSO() != null && omonimo.getDATIANAGRAFICI().getSESSO().equals(SESSO.F)) {
				natoNata = "nata";
			}
			String soggetto = cognome + " " + nome + " " + natoNata + " in " + descStato + " ( " + comuneNascita + " ) " + " il "
					+ omonimo.getDATIANAGRAFICI().getDATANASCITA().getGIORNO() + "/" + omonimo.getDATIANAGRAFICI().getDATANASCITA().getMESE() + "/"
					+ omonimo.getDATIANAGRAFICI().getDATANASCITA().getANNO();

			String deceduto = "No";
			if (omonimo.getDATIANAGRAFICI().getFLAGDECEDUTOSN() != null) {
				if (omonimo.getDATIANAGRAFICI().getFLAGDECEDUTOSN().equals(FLAGDECESSO.S))
					deceduto = "Si";
			}
			listOmonimoForms.add(new OmonimoForm(omonimo.getDATIANAGRAFICI().getPROGANAGRAFICA().toString(), soggetto, omonimo.getDATIANAGRAFICI()
					.getCODIFISCALE(), deceduto));
		}
		return listOmonimoForms;
	}
 */
}
