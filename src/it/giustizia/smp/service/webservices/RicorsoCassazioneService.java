package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TImpugnazione;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelSoggImp;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;
import it.giustizia.smp.webservice.client.ricorsocassazione.Animali;
import it.giustizia.smp.webservice.client.ricorsocassazione.Atto;
import it.giustizia.smp.webservice.client.ricorsocassazione.Avvocati;
import it.giustizia.smp.webservice.client.ricorsocassazione.Azienda;
import it.giustizia.smp.webservice.client.ricorsocassazione.Bene;
import it.giustizia.smp.webservice.client.ricorsocassazione.BeneFinanziario;
import it.giustizia.smp.webservice.client.ricorsocassazione.BeneImmat;
import it.giustizia.smp.webservice.client.ricorsocassazione.BeneImmobile;
import it.giustizia.smp.webservice.client.ricorsocassazione.Cauzione;
import it.giustizia.smp.webservice.client.ricorsocassazione.Collezioni;
import it.giustizia.smp.webservice.client.ricorsocassazione.Contante;
import it.giustizia.smp.webservice.client.ricorsocassazione.Decreto;
import it.giustizia.smp.webservice.client.ricorsocassazione.Denaro;
import it.giustizia.smp.webservice.client.ricorsocassazione.Impugnazione;
import it.giustizia.smp.webservice.client.ricorsocassazione.MisuraPersonale;
import it.giustizia.smp.webservice.client.ricorsocassazione.PersonaFisica;
import it.giustizia.smp.webservice.client.ricorsocassazione.PersonaGiuridica;
import it.giustizia.smp.webservice.client.ricorsocassazione.Procedimento;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelAttoMisPatr;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelAttoMisuraPersonale;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelAttoProc;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelBeneAtto;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelProcBene;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelProcSoggetto;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelProcedimentoProc;
import it.giustizia.smp.webservice.client.ricorsocassazione.RelSoggettoImp;
import it.giustizia.smp.webservice.client.ricorsocassazione.RicorsoCassazioneServiceClient;
import it.giustizia.smp.webservice.client.ricorsocassazione.RicorsoRequest;
import it.giustizia.smp.webservice.client.ricorsocassazione.RicorsoResponse;
import it.giustizia.smp.webservice.client.ricorsocassazione.Soggetto;
import it.giustizia.smp.webservice.client.ricorsocassazione.Veicolo;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

public class RicorsoCassazioneService  {

	@Autowired
	private RicorsoCassazioneServiceClient ricorsoCassazioneServiceClient;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private AttoDao attoDao;

	@Autowired
	private ProcedimentoDao procedimentoDao;


	private Logger logger = Logger.getLogger(getClass());
	
	
	/***
	 * Inserisce il record che consente l'invio in cassazione in un secondo momento  
	 * @param idUffOwn
	 * @param idAttoImpugnazione
	 * @param idProcedimento
	 * @return
	 */
	public void inserisciRecordInvio(final int idUffOwn, final long idAttoImpugnazione,final long idProcedimento,String descUtente) {
		commonDao.inserisciInTCassMsg(idUffOwn, idAttoImpugnazione, idProcedimento, descUtente);
	}
	
	
	
	/***
	 * Carica tutti i dati necessari all'invio in cassazione e invoca la chiamata la servizio
	 * @param idUffOwn
	 * @param idAttoImpugnazione
	 * @param idProcedimento
	 * @return
	 */
	public RicorsoResponse inserisciRicorso(final int idUffOwn, final long idAttoImpugnazione,final long idProcedimento) {
		
		logger.info("Invocazione chiamata a cassazione per inserimento ricorso.");
		RicorsoRequest request = new RicorsoRequest();
		
		
		TProcedimento tProcedimento = procedimentoDao.getProcedimentoById2(idProcedimento);
		
		// Valorizzo il procedimento
		Procedimento procedimentoWS = new Procedimento();
		logger.info("-->Procedimento");
		procedimentoWS.setCodiFkIdTipoFasc(tProcedimento.getTTpTipoFascicolo().getCodiKIdTipoFasc());
		procedimentoWS.setCodiFkIdTipoProc(tProcedimento.getTTpTipoProc().getCodiKIdTipoProc());
		procedimentoWS.setCodiFkIdUffOwner(tProcedimento.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio());
		procedimentoWS.setCodiKIdProc(tProcedimento.getCodiKIdProc());
		procedimentoWS.setDataIscrizione(Utility.toXMLGregorianCalendar(tProcedimento.getDataIscrizione()));
		procedimentoWS.setFlgAntimafia(tProcedimento.getFlgAntimafia()!=null? tProcedimento.getFlgAntimafia().toString():null);
		procedimentoWS.setFlgAntiterrorismo(tProcedimento.getFlgAntiterrorismo()!=null?tProcedimento.getFlgAntiterrorismo().toString():null);
		procedimentoWS.setFlgOrdinaria(tProcedimento.getFlgOrdinaria()!=null? tProcedimento.getFlgOrdinaria().toString():null);
		procedimentoWS.setNumeAnnoProc(tProcedimento.getNumeAnnoProc());
		procedimentoWS.setNumeProc(tProcedimento.getNumeProc());
		request.setProcedimento(procedimentoWS);
			
		
		// Relazione atto impugnazione procedimento 
		RelAttoProc relAttoProcWS = new RelAttoProc();
		logger.info("-->RelAttoProc");
		relAttoProcWS.setCodiKIdAtto(idAttoImpugnazione);
		relAttoProcWS.setCodiKIdProc(idProcedimento);
		relAttoProcWS.setCodiKIdUffOwner(idUffOwn);
		procedimentoWS.getRelAttoProc().add(relAttoProcWS);
		
		
		// Atto impugnazione
		TAtto atto = attoDao.getAttoById(new TAttoId(idAttoImpugnazione,idUffOwn));
		Atto attoWSImp = new Atto();
		logger.info("-->Impugnazione");
		attoWSImp.setCodiFkIdCatAtto(atto.getTTpCatAtto().getCodiKIdCatAtto());
		attoWSImp.setCodiFkIdTipoAtto(atto.getTTpTipoAtto().getCodiKIdTipoAtto());
		attoWSImp.setCodiKIdAtto(idAttoImpugnazione);
		attoWSImp.setCodiKIdUffOwner(idUffOwn);
		attoWSImp.setDataEmissione(Utility.toXMLGregorianCalendar(atto.getDataEmissione()));
		attoWSImp.setDataRegistrazione(Utility.toXMLGregorianCalendar(atto.getDataRegistrazione()));
		attoWSImp.setDescTipoInvio("T"); 
		attoWSImp.setDescTipoInvRicorso("N"); // TODO QUANDO SARANNO SVILUPPATE LE ALTRE FUNZIONI GESTIRE S e T
		//attoWS.setSicNrg(null);//  non servono 
		//attoWS.setSicNrgreale(null);// non servono 
		relAttoProcWS.getAtto().add(attoWSImp);
		
		
		// Decreto (dell'atto impugnato) deve essere preso da T_REL_ATTI entrando per atto finale
		List<TRelAtti> relAttiList = attoDao.getTRelAtto(idAttoImpugnazione,idUffOwn);
		logger.info("-->Decreto");
		long idAttoDecreto = relAttiList.get(0).getTAttoByTRelAttiFkAttoOr().getId().getCodiKIdAtto();
		int idUffOwnerDecreto = relAttiList.get(0).getTAttoByTRelAttiFkAttoOr().getId().getCodiKIdUffOwner();
		TDecreto decreto = attoDao.getTDecreto(idAttoDecreto,idUffOwnerDecreto);
		Decreto decretoWS = new Decreto();
		decretoWS.setCodiKIdDecreto(idAttoDecreto);
		decretoWS.setCodiKIdUffOwner(idUffOwnerDecreto);
		decretoWS.setNumeAnnoAtto(decreto.getNumeAnnoAtto());
		decretoWS.setNumeAtto(decreto.getNumeAtto());
		
		// Relazione atto procedimento per il decreto
		relAttoProcWS = new RelAttoProc();
		relAttoProcWS.setCodiKIdAtto(idAttoDecreto);
		relAttoProcWS.setCodiKIdProc(idProcedimento);
		relAttoProcWS.setCodiKIdUffOwner(idUffOwn);
		procedimentoWS.getRelAttoProc().add(relAttoProcWS);
		
		// Atto Decreto
		atto = attoDao.getAttoById(new TAttoId(idAttoDecreto,idUffOwn));
		Atto attoWSDec = new Atto();
		logger.info("-->Atto Decreto");
		attoWSDec.setCodiFkIdCatAtto(atto.getTTpCatAtto().getCodiKIdCatAtto());
		attoWSDec.setCodiFkIdTipoAtto(atto.getTTpTipoAtto().getCodiKIdTipoAtto());
		attoWSDec.setCodiKIdAtto(idAttoDecreto);
		attoWSDec.setCodiKIdUffOwner(idUffOwn);
		attoWSDec.setDataEmissione(Utility.toXMLGregorianCalendar(atto.getDataEmissione()));
		attoWSDec.setDataRegistrazione(Utility.toXMLGregorianCalendar(atto.getDataRegistrazione()));
		attoWSDec.setDecreto(decretoWS);
		//attoWSDec.setDescTipoInvio("T"); //  non servono
		//attoWSDec.setDescTipoInvRicorso("N"); //  non servono
		//attoWS.setSicNrg(null);//  non servono 
		//attoWS.setSicNrgreale(null);// non servono 
		relAttoProcWS.getAtto().add(attoWSDec);
		
		// Impugnazione 
		TImpugnazione impugnazione = attoDao.getTImpugnazione(idAttoImpugnazione,idUffOwn);
		Impugnazione impugnazioneWS = new Impugnazione();
		logger.info("-->ImpugnazioneWs ");
		if (impugnazione.getTAnIstitutoDet()!=null) impugnazioneWS.setCodiFkIdIstDet(impugnazione.getTAnIstitutoDet().getCodiKIdIstituto());
		if (impugnazione.getTAnUfficio()!=null)  impugnazioneWS.setCodiFkIdUffDep(impugnazione.getTAnUfficio().getCodiKIdUfficio());
		impugnazioneWS.setCodiKIdImpugnaz(impugnazione.getId().getCodiKIdImpugnaz());
		impugnazioneWS.setCodiKIdUffOwner(impugnazione.getId().getCodiKIdUffOwner());
		impugnazioneWS.setDataDepImp(Utility.toXMLGregorianCalendar(impugnazione.getDataDepImp()));
		if (impugnazione.getFlgUffDep()!=null) impugnazioneWS.setFlgUffDep(impugnazione.getFlgUffDep().toString());
		impugnazioneWS.setNumeAnnoAtto(impugnazione.getNumeAnnoAtto());
		impugnazioneWS.setNumeAtto(impugnazione.getNumeAtto());
		impugnazioneWS.setOraDepImp(impugnazione.getOraDepImp());
		attoWSImp.setImpugnazione(impugnazioneWS);
		
		HashMap<Long,TSoggetto> mappaSoggettiDaInviare = new HashMap<Long,TSoggetto>();
		
		// RelAttoMisPers 
		
		List<TRelAttoMisPers> relAttoMisPersList = attoDao.getTRelAttoMisPers(idProcedimento,idAttoImpugnazione,idUffOwn);
		logger.info("-->RelAttoMisPers inizio"  + " record = " + relAttoMisPersList.size());
		for (TRelAttoMisPers tRelAttoMisPers : relAttoMisPersList) {
			logger.info("-->RelAttoMisPers inizio" + " rec " + tRelAttoMisPers.getId().getCodiKIdMisuraPers());
			RelAttoMisuraPersonale relAttoMisuraPersonaleWS = new RelAttoMisuraPersonale();
			if (tRelAttoMisPers.getTCauzione()!=null) relAttoMisuraPersonaleWS.setCodiFkIdCauzione(tRelAttoMisPers.getTCauzione().getId().getCodiKIdCauzione());
			if (tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito1()!=null) relAttoMisuraPersonaleWS.setCodiFkIdEsito1(tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			if (tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito2()!=null) relAttoMisuraPersonaleWS.setCodiFkIdEsito2(tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			if (tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito3()!=null) relAttoMisuraPersonaleWS.setCodiFkIdEsitoCsz(tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			if (tRelAttoMisPers.getTAnNormative()!=null) relAttoMisuraPersonaleWS.setCodiFkIdNormativa(tRelAttoMisPers.getTAnNormative().getCodiKIdNormativa());
			if (tRelAttoMisPers.getTTpTipoMisura()!=null) relAttoMisuraPersonaleWS.setCodiFkIdTipoMis(tRelAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
			relAttoMisuraPersonaleWS.setCodiKIdAtto(idAttoImpugnazione);
			if (tRelAttoMisPers.getTMisuraPers()!=null) relAttoMisuraPersonaleWS.setCodiKIdMisuraPers(tRelAttoMisPers.getTMisuraPers().getId().getCodiKIdMisura());
			relAttoMisuraPersonaleWS.setCodiKIdProc(idProcedimento);
			relAttoMisuraPersonaleWS.setCodiKIdUffOwner(idUffOwn);
			relAttoMisuraPersonaleWS.setDescLoc(tRelAttoMisPers.getDescLoc());
			relAttoMisuraPersonaleWS.setDescUltPresc(tRelAttoMisPers.getDescUltPresc());
			//relAttoMisuraPersonaleWS.setIdEsitoSent(tRelAttoMisPers.getT().getCodiKIdEsitoFin()); //NON DEVE ESSERE VALORIZZATO (LO PASSANO LORO)
			if (tRelAttoMisPers.getNumeDurataAnni()!=null) relAttoMisuraPersonaleWS.setNumeDurataAnni((short)tRelAttoMisPers.getNumeDurataAnni());
			if (tRelAttoMisPers.getNumeDurataGiorni()!=null) relAttoMisuraPersonaleWS.setNumeDurataGiorni((short)tRelAttoMisPers.getNumeDurataGiorni());
			if (tRelAttoMisPers.getNumeDurataMesi()!=null) relAttoMisuraPersonaleWS.setNumeDurataMesi((short)tRelAttoMisPers.getNumeDurataMesi());
		
			// MisuraPers
			TMisuraPers misuraPers =  tRelAttoMisPers.getTMisuraPers();
			MisuraPersonale misuraPersonaleWS = new MisuraPersonale();
			logger.info("-->MisuraPers inizio" + " rec " + misuraPers.getId().getCodiKIdMisura());
			if (misuraPers!=null) {
				if(misuraPers.getTTpCaratMisura()!=null) misuraPersonaleWS.setCodiFkIdCaratMis(misuraPers.getTTpCaratMisura().getCodiKIdCaratMis() );
				if(misuraPers.getTSoggetto()!=null) misuraPersonaleWS.setCodiFkIdSoggInt(misuraPers.getTSoggetto().getId().getCodiKIdSoggInt() );
				misuraPersonaleWS.setCodiKIdMisura(misuraPers.getId().getCodiKIdMisura() );
				misuraPersonaleWS.setCodiKIdUffOwner(misuraPers.getId().getCodiKIdUffOwner() );
				relAttoMisuraPersonaleWS.setMisuraPersonale(misuraPersonaleWS);
			}

			// Cauzione
			TCauzione cauzione = tRelAttoMisPers.getTCauzione();
			logger.info("-->Cauzione " );
			if (cauzione!=null) {
				Cauzione cauzioneWS = new Cauzione();
				logger.info("-->Cauzione "  + " " + cauzione.getId().getCodiKIdCauzione());
				cauzioneWS.setCodiFkIdProc(idProcedimento);
				if (cauzione.getTRelProcSogint()!=null) cauzioneWS.setCodiFkIdSoggInt(cauzione.getTRelProcSogint().getId().getCodiKIdSoggInt());
				cauzioneWS.setCodiKIdCauzione(cauzione.getId().getCodiKIdCauzione());
				if (cauzione.getTAnUfficio()!=null) cauzioneWS.setCodiKIdUffOwner(cauzione.getTAnUfficio().getCodiKIdUfficio());
				cauzioneWS.setImpoCauzione(cauzione.getImpoCauzione());
				relAttoMisuraPersonaleWS.setCauzione(cauzioneWS);
			}
			
			if (!mappaSoggettiDaInviare.containsKey(misuraPers.getTSoggetto().getId().getCodiKIdSoggInt())) {
				mappaSoggettiDaInviare.put(misuraPers.getTSoggetto().getId().getCodiKIdSoggInt(),misuraPers.getTSoggetto());
			}
			attoWSImp.getRelAttoMisuraPersonale().add(relAttoMisuraPersonaleWS);
					
		}
		
		// T_REL_BENE_ATTO 
		List<TRelBeneAtto> relBeneAttoList = attoDao.getTRelBeneAtto(idProcedimento,idAttoImpugnazione,idUffOwn);
		logger.info("-->TRelBeneAtto " );
		for (TRelBeneAtto relBeneAtto : relBeneAttoList) {
			RelBeneAtto relBeneAttoWS = new RelBeneAtto();
			logger.info("-->TRelBeneAtto " + " rec = " + relBeneAtto.getId().getCodiKIdAtto());
			if (relBeneAtto.getTTpEsitoByCodiFkIdEsito1()!=null) relBeneAttoWS.setCodiFkIdEsito1(relBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			if (relBeneAtto.getTTpEsitoByCodiFkIdEsito2()!=null) relBeneAttoWS.setCodiFkIdEsito2(relBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			if (relBeneAtto.getTTpEsitoByCodiFkIdEsito3()!=null) relBeneAttoWS.setCodiFkIdEsitoCsz(relBeneAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			if (relBeneAtto.getTAnNormative()!=null){  
				relBeneAttoWS.setCodiFkIdNormativa(relBeneAtto.getTAnNormative().getCodiKIdNormativa());
				try {
					if (relBeneAtto.getTAnNormative().getTTpTipoMisura()!=null) relBeneAttoWS.setCodiFkIdTipoMis(relBeneAtto.getTAnNormative().getTTpTipoMisura().getCodiKIdTipoMisura());
				}catch(HibernateException e){
					logger.info("-->TRelBeneAtto " + " erroror = " + relBeneAtto.getTAnNormative().getTTpTipoMisura().getCodiKIdTipoMisura() );
				}
				
			}
			relBeneAttoWS.setCodiKIdAtto(idAttoImpugnazione);
			relBeneAttoWS.setCodiKIdBene(relBeneAtto.getId().getCodiKIdBene());
			relBeneAttoWS.setCodiKIdProc(idProcedimento);
			relBeneAttoWS.setCodiKIdSoggTit(relBeneAtto.getId().getCodiKIdSoggTit());
			if (relBeneAtto.getTTpTipoInteresse()!=null) relBeneAttoWS.setCodiKIdTipoInt(relBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
			relBeneAttoWS.setCodiKIdUffOwner(idUffOwn);
			if (relBeneAtto.getTTpEsitoFinale()!=null) relBeneAttoWS.setIdEsitoSent(relBeneAtto.getTTpEsitoFinale().getCodiKIdEsitoFin()); 
			relBeneAttoWS.setNumeFrazioneDen(relBeneAtto.getNumeFrazioneDen());
			relBeneAttoWS.setNumeFrazioneNum(relBeneAtto.getNumeFrazioneNum());
			relBeneAttoWS.setNumeQuota(relBeneAtto.getNumeQuota());
			attoWSImp.getRelBeneAtto().add(relBeneAttoWS);
			
			for (TRelAttoMisPatr tRelAttoMisPatr : relBeneAtto.getTRelAttoMisPatrs()) {
				logger.info("-->TRelAttoMisPatr " + " rec = " + tRelAttoMisPatr.getId().getCodiKIdSoggInt());
				RelAttoMisPatr relAttoMisPatrWS = new RelAttoMisPatr();
				relAttoMisPatrWS.setCodiKIdAtto(tRelAttoMisPatr.getId().getCodiKIdAtto());
				relAttoMisPatrWS.setCodiKIdBene(tRelAttoMisPatr.getId().getCodiKIdBene());
				relAttoMisPatrWS.setCodiKIdProc(tRelAttoMisPatr.getId().getCodiKIdProc());
				relAttoMisPatrWS.setCodiKIdSoggInt(tRelAttoMisPatr.getId().getCodiKIdSoggInt());
				relAttoMisPatrWS.setCodiKIdSoggTit(tRelAttoMisPatr.getId().getCodiKIdSoggTit());
				relAttoMisPatrWS.setCodiKIdTipoInt(tRelAttoMisPatr.getId().getCodiKIdTipoInt());
				relAttoMisPatrWS.setCodiKIdUffOwner(tRelAttoMisPatr.getId().getCodiKIdUffOwner());
				relBeneAttoWS.getRelAttoMisPatr().add(relAttoMisPatrWS);
				
				if (!mappaSoggettiDaInviare.containsKey(tRelAttoMisPatr.getId().getCodiKIdSoggInt())) {
					TSoggetto soggAppo = attoDao.getTSoggettoById(relAttoMisPatrWS.getCodiKIdSoggInt(),tRelAttoMisPatr.getId().getCodiKIdUffOwner() ) ;
					mappaSoggettiDaInviare.put(soggAppo.getId().getCodiKIdSoggInt(),soggAppo);
				}
				if (!mappaSoggettiDaInviare.containsKey(tRelAttoMisPatr.getId().getCodiKIdSoggTit())) {
					TSoggetto soggAppo = attoDao.getTSoggettoById(relAttoMisPatrWS.getCodiKIdSoggTit(),tRelAttoMisPatr.getId().getCodiKIdUffOwner() ) ;
					mappaSoggettiDaInviare.put(soggAppo.getId().getCodiKIdSoggInt(),soggAppo);
				}
				
			}
		}
		// T_REL_SOGG_IMP
		
		Set<TRelSoggImp> relSogImpList = impugnazione.getTRelSoggImps();
		logger.info("-->TRelSogImp " + " = " + relSogImpList.size());
		for (TRelSoggImp relSoggImp : relSogImpList) {
			RelSoggettoImp relSoggettoImpWS = new  RelSoggettoImp();
			if (relSoggImp.getTAvvocati()!=null) relSoggettoImpWS.setCodiFkIdAvvocato(relSoggImp.getTAvvocati().getCodiKIdAvvocato());
			if (relSoggImp.getTSoggetto()!=null) {
				logger.info("-->relSoggImp " + " rec " + relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt());
				relSoggettoImpWS.setCodiFkIdSoggInt(relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt());
			}
			relSoggettoImpWS.setCodiKIdImpugnaz(relSoggImp.getId().getCodiKIdImpugnaz());
			relSoggettoImpWS.setCodiKIdProgr(relSoggImp.getId().getCodiKIdProgr());
			relSoggettoImpWS.setCodiKIdUffOwner(relSoggImp.getId().getCodiKIdUffOwner());
			relSoggettoImpWS.setDescCognAvv(relSoggImp.getDescCognAvv());
			relSoggettoImpWS.setDescNomeAvv(relSoggImp.getDescNomeAvv());
			relSoggettoImpWS.setDescTipoProp(relSoggImp.getDescTipoProp());
			if (relSoggImp.getFlgTipoParte()!=null) relSoggettoImpWS.setFlgTipoParte(relSoggImp.getFlgTipoParte().toString());
			
			//Avvocati
			if (relSoggImp.getTAvvocati()!=null) {
				List<TAvvocati> avvocatiList = attoDao.getAvvocatiDifensori(idProcedimento, idUffOwn, relSoggImp.getTAvvocati().getCodiKIdAvvocato());
				//for (TAvvocati tAvvocati : avvocatiList) {
					Avvocati avvocatoWS = new Avvocati();
					TAvvocati tAvvocati = avvocatiList.get(0);
					logger.info("-->Avvocati " + " rec " + tAvvocati.getCodiKIdAvvocato());
					if (tAvvocati.getTAnComuni()!=null) avvocatoWS.setCodiFkComForo(tAvvocati.getTAnComuni().getCodiKIdComune());  
					avvocatoWS.setCodiKIdAvvocato(tAvvocati.getCodiKIdAvvocato());
//					avvocatoWS.setCodiKIdUffOwner(tAvvocati.getId().getCodiKIdUffOwner());
					avvocatoWS.setDataNascita(Utility.toXMLGregorianCalendar(tAvvocati.getDataNascita()));
					avvocatoWS.setDescCapDomleg(tAvvocati.getDescCapDomleg());
					avvocatoWS.setDescCapRes(tAvvocati.getDescCapRes());
					avvocatoWS.setDescCodiceFiscale(tAvvocati.getDescCodiceFiscale());
					avvocatoWS.setDescCognomeAvv(tAvvocati.getDescCognomeAvv());
					avvocatoWS.setDescComuneDomleg(tAvvocati.getDescComuneDomleg());
					avvocatoWS.setDescComuneRes(tAvvocati.getDescComuneRes());
					avvocatoWS.setDescEmailDomleg(tAvvocati.getDescEmailDomleg());
					avvocatoWS.setDescEmailRes(tAvvocati.getDescEmailRes());
					avvocatoWS.setDescFaxDomleg(tAvvocati.getDescFaxDomleg());
					avvocatoWS.setDescFaxRes(tAvvocati.getDescFaxRes());
					avvocatoWS.setDescIdReginde(tAvvocati.getDescIdReginde());
					avvocatoWS.setDescIndirDomleg(tAvvocati.getDescIndirDomleg());
					avvocatoWS.setDescIndirRes(tAvvocati.getDescIndirRes());
					avvocatoWS.setDescLuogoNascita(tAvvocati.getDescLuogoNascita());
					avvocatoWS.setDescNomeAvv(tAvvocati.getDescNomeAvv());
					avvocatoWS.setDescPec(tAvvocati.getDescPec());
					avvocatoWS.setDescProvDomleg(tAvvocati.getDescProvDomleg());
					avvocatoWS.setDescProvNascita(tAvvocati.getDescProvNascita());
					avvocatoWS.setDescProvRes(tAvvocati.getDescProvRes());
					avvocatoWS.setDescTelDomleg(tAvvocati.getDescTelDomleg());
					avvocatoWS.setDescTelRes(tAvvocati.getDescTelRes());
					relSoggettoImpWS.getAvvocati().add(avvocatoWS);
				//}
			}
			procedimentoWS.getRelSoggettoImp().add(relSoggettoImpWS);
			
			
			if (tProcedimento.getTTpTipoProc().getCodiKIdTipoProc() == Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
				// Se l'istanza è di tipo ISTANZA_TERZO_INTERVENIENTE aggiungo nella mappa dei soggetti da inviare anche il terzo
				logger.info("-->Terzo interveniente");
				if (relSoggImp.getTSoggetto()!=null) { 
					TSoggetto soggAppo = attoDao.getTSoggettoById(relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt(),relSoggImp.getTSoggetto().getId().getCodiKIdUffOwner() ) ;
					mappaSoggettiDaInviare.put(relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt(),soggAppo);
				}
			}
			
			// verifico se il soggetto imp è presente altrimento lo aggiungo AG SETTEMBRE 2018
				if (relSoggImp.getTSoggetto()!=null) { 
					int presente = 0;
					logger.info("-->Soggetto impugnato");
					for (TSoggetto tSoggettoAppo : mappaSoggettiDaInviare.values()) {
						TRelProcSogint relProcSogint = attoDao.getTRelProcSogint(idProcedimento,tSoggettoAppo.getId().getCodiKIdSoggInt(),tSoggettoAppo.getId().getCodiKIdUffOwner());
						if (relProcSogint.getId().getCodiKIdSoggInt() == relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt() ){
							presente ++;
						}	
					}
						if  (presente == 0){    
							TSoggetto soggAppo = attoDao.getTSoggettoById(relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt(),relSoggImp.getTSoggetto().getId().getCodiKIdUffOwner() ) ;
							mappaSoggettiDaInviare.put(relSoggImp.getTSoggetto().getId().getCodiKIdSoggInt(),soggAppo);
						}
							
					}
			
		}
		
		// Ciclo su titti i soggetti della mappa creata in precedenza
		logger.info("-->Ciclo Soggetti da inviare " + " tot  " + mappaSoggettiDaInviare.size());
		for (TSoggetto tSoggettoAppo : mappaSoggettiDaInviare.values()) {
			logger.info("-->Ciclo Soggetti da inviare " + " rec " + tSoggettoAppo.getId().getCodiKIdSoggInt());
			TRelProcSogint relProcSogint = attoDao.getTRelProcSogint(idProcedimento,tSoggettoAppo.getId().getCodiKIdSoggInt(),tSoggettoAppo.getId().getCodiKIdUffOwner());
			RelProcSoggetto relProcSoggettoWS = new RelProcSoggetto();
			if (relProcSogint.getTTpTipoRuolo()!=null) relProcSoggettoWS.setCodiFkIdRuolo(relProcSogint.getTTpTipoRuolo().getCodiKIdRuolo());
			if (relProcSogint.getTRelProcSogintVer()!=null) relProcSoggettoWS.setCodiFkIdVer(relProcSogint.getTRelProcSogintVer().getId().getCodiKIdVer());
			relProcSoggettoWS.setCodiKIdProc(idProcedimento);
			relProcSoggettoWS.setCodiKIdSoggInt(tSoggettoAppo.getId().getCodiKIdSoggInt());
			relProcSoggettoWS.setCodiKIdUffOwner(idUffOwn);
			procedimentoWS.getRelProcSoggetto().add(relProcSoggettoWS);
			
			// Soggetto
			TSoggetto soggetto = relProcSogint.getTRelProcSogintVer().getTSoggetto();
			Soggetto soggettoWS = new Soggetto();
			soggettoWS.setCodiFkIdTipoSogg(Character.toString(soggetto.getTTpTipoSoggetto().getCodiKIdTipoSogg()));
			soggettoWS.setCodiKIdSoggInt(soggetto.getId().getCodiKIdSoggInt());
			soggettoWS.setCodiKIdUffOwner(soggetto.getId().getCodiKIdUffOwner());
			
			// Persona fisica
			
			if (soggetto.getTTpTipoSoggetto().getCodiKIdTipoSogg()=='F') {
				PersonaFisica personaFisicaWS = new PersonaFisica();
				logger.info("-->Persona Fisica ");
				TPersonaFisica  tPersonaFisica =  attoDao.getTPersonaFisica(relProcSoggettoWS.getCodiKIdSoggInt(),relProcSoggettoWS.getCodiFkIdVer(),relProcSoggettoWS.getCodiKIdUffOwner());
				if (tPersonaFisica.getTAnComuniByCodiFkIdComNasc()!=null) personaFisicaWS.setCodiFkIdComNasc(tPersonaFisica.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
				if (tPersonaFisica.getTAnStatoByCodiFkIdStatoNasc()!=null) personaFisicaWS.setCodiFkIdStatoNasc(tPersonaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
				personaFisicaWS.setCodiKIdPersonaFis(tPersonaFisica.getId().getCodiKIdPersonaFis());
				personaFisicaWS.setCodiKIdUffOwner(tPersonaFisica.getId().getCodiKIdUffOwner());
				personaFisicaWS.setCodiKIdVer(tPersonaFisica.getId().getCodiKIdVer());
				personaFisicaWS.setDataNascita(Utility.toXMLGregorianCalendar(tPersonaFisica.getDataNascita()));
				personaFisicaWS.setDescCodiceFiscale(tPersonaFisica.getDescCodiceFiscale());
				personaFisicaWS.setDescCognome(tPersonaFisica.getDescCognome());
				personaFisicaWS.setDescComEsteroNasc(tPersonaFisica.getDescComEsteroNasc());
				personaFisicaWS.setDescNome(tPersonaFisica.getDescNome());
				
				//ag Modifica del 14 febbraio 2018 per evitARE anomalia in quanto SE IL VALORE è  null non puo essere trasformato in string
				//personaFisicaWS.setFlgDataPresunta(tPersonaFisica.getFlgDataPresunta().toString());
				//personaFisicaWS.setFlgSesso(tPersonaFisica.getFlgSesso().toString());
				if (tPersonaFisica.getFlgDataPresunta()!=null ) personaFisicaWS.setFlgDataPresunta(tPersonaFisica.getFlgDataPresunta().toString());
				if (tPersonaFisica.getFlgSesso()!=null ) personaFisicaWS.setFlgSesso(tPersonaFisica.getFlgSesso().toString());
				// fine modifica 
				soggettoWS.setPersonaFisica(personaFisicaWS);
			} else { // Persona giuridica
				PersonaGiuridica personaGiuridicaWS = new PersonaGiuridica ();
				logger.info("-->Persona Giuridica ");
				TPersonaGiur tPersonaGiur = attoDao.getTPersonaGiur(relProcSoggettoWS.getCodiKIdSoggInt(),relProcSoggettoWS.getCodiFkIdVer(),relProcSoggettoWS.getCodiKIdUffOwner());
				if (tPersonaGiur.getTAnComuni()!=null) personaGiuridicaWS.setCodiFkIdComSede(tPersonaGiur.getTAnComuni().getCodiKIdComune());
				if (tPersonaGiur.getTTpSettoreAtt()!=null) personaGiuridicaWS.setCodiFkIdSettAtt(tPersonaGiur.getTTpSettoreAtt().getCodiKIdSettoreAtt());
				if (tPersonaGiur.getTAnStato()!=null) personaGiuridicaWS.setCodiFkIdStatoSede(tPersonaGiur.getTAnStato().getCodiKIdStato());
				if (tPersonaGiur.getTTpTipoSocieta()!=null) personaGiuridicaWS.setCodiFkIdTipoSoc(tPersonaGiur.getTTpTipoSocieta().getCodiKIdTipoSoc());
				if (tPersonaGiur.getTTpTipoStrada()!=null) personaGiuridicaWS.setCodiFkIdTipoStrada(tPersonaGiur.getTTpTipoStrada().getCodiKIdTipoStrada());
				personaGiuridicaWS.setCodiKIdPersGiur(tPersonaGiur.getId().getCodiKIdPersGiur());
				personaGiuridicaWS.setCodiKIdUffOwner(tPersonaGiur.getId().getCodiKIdUffOwner());
				personaGiuridicaWS.setCodiKIdVer(tPersonaGiur.getId().getCodiKIdVer());
				personaGiuridicaWS.setDescAmmin(tPersonaGiur.getDescAmmin());
				personaGiuridicaWS.setDescCap(tPersonaGiur.getDescCap());
				personaGiuridicaWS.setDescCivico(tPersonaGiur.getDescCivico());
				personaGiuridicaWS.setDescCodFisc(tPersonaGiur.getDescCodFisc());
				personaGiuridicaWS.setDescComEsteroSede(tPersonaGiur.getDescCap());
				personaGiuridicaWS.setDescIndirizzoSede(tPersonaGiur.getDescIndirizzoSede());
				personaGiuridicaWS.setDescNote(tPersonaGiur.getDescNote());
				personaGiuridicaWS.setDescNumeCciaa(tPersonaGiur.getDescNumeCciaa());
				personaGiuridicaWS.setDescPartitaIva(tPersonaGiur.getDescPartitaIva());
				personaGiuridicaWS.setDescRagioneSoc(tPersonaGiur.getDescRagioneSoc());
				personaGiuridicaWS.setDescRapprSoc(tPersonaGiur.getDescRapprSoc());
				personaGiuridicaWS.setImpoCapSoc(tPersonaGiur.getImpoCapSoc());
				soggettoWS.setPersonaGiuridica(personaGiuridicaWS);
			}
			relProcSoggettoWS.setSoggetto(soggettoWS);
			
		}
		
		HashMap<Long,RelBeneAtto> mappaBeni = new HashMap<Long,RelBeneAtto>();
		logger.info("-->Beni  ");
		//T_REL_PROC_BENE  
		for (RelBeneAtto relBeneAtto : attoWSImp.getRelBeneAtto()) {
			if (!mappaBeni.containsKey(relBeneAtto.getCodiKIdBene())) {
				mappaBeni.put(relBeneAtto.getCodiKIdBene(), relBeneAtto);
				TRelProcBene relProcAtto = attoDao.getTRelProcBene(idProcedimento,relBeneAtto.getCodiKIdBene(),idUffOwn);
				RelProcBene relProcBeneWS = new RelProcBene();
				relProcBeneWS.setCodiKIdBene(relBeneAtto.getCodiKIdBene());
				relProcBeneWS.setCodiKIdProc(idProcedimento);
				relProcBeneWS.setCodiKIdUffOwner(idUffOwn);
				
				// T_BENE
				TBene tBene = relProcAtto.getTBene();
				
				Bene beneWS = new Bene();
				logger.info("-->Bene record  " + tBene.getId().getCodiKIdBene());
				if (tBene.getTTpCatBene()!=null) beneWS.setCodiFkIdCatBene(tBene.getTTpCatBene().getCodiKIdCatBene());
				if (tBene.getTAnComuni()!=null) beneWS.setCodiFkIdComune(tBene.getTAnComuni().getCodiKIdComune());
				if (tBene.getTTpSottocatBene()!=null) beneWS.setCodiFkIdSctgBene(tBene.getTTpSottocatBene().getCodiKIdSottocat());
				if (tBene.getTAnStato()!=null) beneWS.setCodiFkIdStato(tBene.getTAnStato().getCodiKIdStato());
				if (tBene.getTTpTipoBene()!=null) beneWS.setCodiFkIdTipoBene(tBene.getTTpTipoBene().getCodiKIdTipoBene());
				if (tBene.getTTpTipoStrada()!=null) beneWS.setCodiFkIdTipoStra(tBene.getTTpTipoStrada().getCodiKIdTipoStrada());
				beneWS.setCodiKIdBene(tBene.getId().getCodiKIdBene());
				beneWS.setCodiKIdUffOwner(tBene.getId().getCodiKIdUffOwner());
				beneWS.setDescCap(tBene.getDescCap());
				beneWS.setDescCivico(tBene.getDescCivico());
				beneWS.setDescComuneEstero(tBene.getDescComuneEstero());
				beneWS.setDescIndirizzo(tBene.getDescIndirizzo());
				beneWS.setDescNote(tBene.getDescNote());
				beneWS.setDescrBene(tBene.getDescrBene());
				//ag Modifica del 14 febbraio 2018 per evitARE anomalia in quanto SE IL VALORE è  null non puo essere trasformato in string
				//beneWS.setFlgCostAzienda(tBene.getFlgCostAzienda().toString());
				if(tBene.getFlgCostAzienda()!=null) beneWS.setFlgCostAzienda(tBene.getFlgCostAzienda().toString());
				// fine Modifica
				beneWS.setFlgGravame(Character.toString(tBene.getFlgGravame()));
				relProcBeneWS.setBene(beneWS);
				
				// ANIMALI			
				Animali animaliWS = new Animali();
				TAnimali animali = attoDao.getTAnimali(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(animali!=null) {
					logger.info("-->Animali  " + tBene.getId().getCodiKIdBene());
					animaliWS.setCodiKIdAnimale(tBene.getId().getCodiKIdBene());
					animaliWS.setCodiKIdUffOwner(idUffOwn);
					animaliWS.setDescCapo(animali.getDescCapo());
					animaliWS.setNumeAnimali(animali.getNumeAnimali());
					beneWS.setAnimali(animaliWS);
				}
				
				// Collezioni
				Collezioni collezioniWS = new Collezioni();
				TCollezioni collezioni = attoDao.getTCollezioni(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(collezioni!=null) {
					logger.info("-->Collezioni  " + tBene.getId().getCodiKIdBene());
					collezioniWS.setCodiKIdColl(tBene.getId().getCodiKIdBene());
					collezioniWS.setCodiKIdUffOwner(idUffOwn);
					collezioniWS.setDescCollezione(collezioni.getDescCollezione());
					collezioniWS.setNumePezzi(collezioni.getNumePezzi());
					beneWS.setCollezioni(collezioniWS);
				}
				// Veicolo
				Veicolo veicoloWS = new Veicolo();
				TVeicolo veicoli = attoDao.getTVeicolo(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(veicoli!=null) {
					logger.info("-->Veicolo  " + tBene.getId().getCodiKIdBene());
					veicoloWS.setCodiKIdVeicolo(tBene.getId().getCodiKIdBene());
					veicoloWS.setCodiKIdUffOwner(idUffOwn);
					veicoloWS.setDescColore(veicoli.getDescColore());
					veicoloWS.setDataReg(Utility.toXMLGregorianCalendar(veicoli.getDataReg()));
					veicoloWS.setDescColore(veicoli.getDescColore());
					veicoloWS.setDescIdentif(veicoli.getDescIdentif());
					veicoloWS.setDescImmatricolaz(veicoli.getDescImmatricolaz());
					veicoloWS.setDescModello(veicoli.getDescModello());
					veicoloWS.setDescReg(veicoli.getDescReg());
					veicoloWS.setDescTarga(veicoli.getDescTarga());
					veicoloWS.setDescTelaio(veicoli.getDescTelaio());
					beneWS.setVeicolo(veicoloWS);			
				}	
				
				// Denaro
				Denaro denaroWS = new Denaro();
				TDenaro denaro = attoDao.getTDenaro(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(denaro!=null) {
					logger.info("-->Denaro  " + tBene.getId().getCodiKIdBene());
					denaroWS.setCodiKIdDenaro(tBene.getId().getCodiKIdBene());
					denaroWS.setCodiKIdUffOwner(idUffOwn);
					if (denaro.getTAnOperFinanz()!=null) denaroWS.setCodiFkIdAbi(denaro.getTAnOperFinanz().getId().getCodiKIdAbi()); 
					if (denaro.getTAnOperFinanz()!=null) denaroWS.setCodiFkIdCab(denaro.getTAnOperFinanz().getId().getCodiKIdCab()); 
					if (denaro.getTTpValuta()!=null) denaroWS.setCodiFkIdValuta(denaro.getTTpValuta().getCodiKIdValuta());
					denaroWS.setDescBicSwift(denaro.getDescBicSwift());
					denaroWS.setDescDirettore(denaro.getDescDirettore());
					denaroWS.setDescEnteDepos(denaro.getDescEnteDepos());
					denaroWS.setDescFiliale(denaro.getDescFiliale());
					denaroWS.setDescIban(denaro.getDescIban());
					denaroWS.setDescIdentEnte(denaro.getDescIdentEnte());
					denaroWS.setImpoEuro(denaro.getImpoEuro());
					denaroWS.setImpoValuta(denaro.getImpoValuta());
					beneWS.setDenaro(denaroWS);
				}
				
				
				// Contante
				Contante contanteWS = new Contante();
				TContante contante = attoDao.getTContante(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(contante!=null) {
					logger.info("-->Contante  " + tBene.getId().getCodiKIdBene());
					contanteWS.setCodiKIdContante(tBene.getId().getCodiKIdBene());
					contanteWS.setCodiKIdUffOwner(idUffOwn);
					if (contante.getTTpValuta()!=null) contanteWS.setCodiFkIdValuta(contante.getTTpValuta().getCodiKIdValuta());
					if (contante.getTAnOperFinanz()!=null)  contanteWS.setCodiFkIdAbi(contante.getTAnOperFinanz().getId().getCodiKIdAbi()); 
					if (contante.getTAnOperFinanz()!=null) contanteWS.setCodiFkIdCab(contante.getTAnOperFinanz().getId().getCodiKIdCab()); 
					contanteWS.setDescBicSwift(contante.getDescBicSwift());
					contanteWS.setDescBollettino(contante.getDescBollettino());
					contanteWS.setDescEnteDepos(contante.getDescEnteDepos());
					contanteWS.setDescIban(contante.getDescIban());
					contanteWS.setDescIdentEnte(contante.getDescIdentEnte());
					contanteWS.setDescIntestatario(contante.getDescIntestatario());
					contanteWS.setFlgVersamento(contante.getFlgVersamento());
					contanteWS.setImpoEuro(contante.getImpoEuro());
					contanteWS.setImpoValuta(contante.getImpoValuta());
					contanteWS.setNumeIdentModTrib(contante.getNumeIdentModTrib());
					beneWS.setContante(contanteWS);
				}
							
				// Azienda
				Azienda aziendaWS = new Azienda();
				TAzienda azienda = attoDao.getTAzienda(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(azienda!=null) {
					logger.info("-->Azienda  " + tBene.getId().getCodiKIdBene());
					if (azienda.getTTpSettoreAtt()!=null) aziendaWS.setCodiFkIdSettAtt(azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt());
					aziendaWS.setCodiKIdAzienda(tBene.getId().getCodiKIdBene());
					aziendaWS.setCodiKIdUffOwner(idUffOwn);
					aziendaWS.setDescRagioneSoc(azienda.getDescRagioneSoc());
					aziendaWS.setDescPIva(azienda.getDescPIva());
					aziendaWS.setDescCodFisc(azienda.getDescCodFisc());
					aziendaWS.setDescCciaa(azienda.getDescCciaa());
					aziendaWS.setDescRappresSoc(azienda.getDescRappresSoc());
					aziendaWS.setDescAmministratore(azienda.getDescAmministratore());
					aziendaWS.setImpoCapitaleSoc(azienda.getImpoCapitaleSoc());
					aziendaWS.setNumeDipendenti(azienda.getNumeDipendenti());
					beneWS.setAzienda(aziendaWS);
				}
				// BeneImmat
				BeneImmat beneImmatWS = new BeneImmat();
				TBeneImmat beneImmat = attoDao.getTBeneImmat(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(beneImmat!=null) {
					logger.info("-->Bene Immat  " + tBene.getId().getCodiKIdBene());
					beneImmatWS.setCodiKIdBeneImm(tBene.getId().getCodiKIdBene());
					beneImmatWS.setCodiKIdUffOwner(idUffOwn);
					beneImmatWS.setDataRegistrazione(Utility.toXMLGregorianCalendar(beneImmat.getDataRegistrazione()));
					beneImmatWS.setDataScadenza(Utility.toXMLGregorianCalendar(beneImmat.getDataScadenza()));
					beneImmatWS.setDescRegistrazione(beneImmat.getDescRegistrazione());
					beneWS.setBeneImmat(beneImmatWS);
				}
				
				// BeneFinanziario
				BeneFinanziario beneFinanziarioWS = new BeneFinanziario();
				TBeneFin beneFin = attoDao.getTBeneFin(tBene.getId().getCodiKIdBene(),idUffOwn);
				if(beneFin!=null) {
					logger.info("-->Bene finanz " + tBene.getId().getCodiKIdBene());
					beneFinanziarioWS.setCodiKIdBeneFin(tBene.getId().getCodiKIdBene());
					beneFinanziarioWS.setCodiKIdUffOwner(idUffOwn);
					if (beneFin.getTAnOperFinanz()!=null) beneFinanziarioWS.setCodiFkIdAbi( beneFin.getTAnOperFinanz().getId().getCodiKIdAbi());  
					if (beneFin.getTAnOperFinanz()!=null) beneFinanziarioWS.setCodiFkIdCab( beneFin.getTAnOperFinanz().getId().getCodiKIdCab());  
					beneFinanziarioWS.setDescIban(beneFin.getDescIban());
					beneFinanziarioWS.setDescBicSwift(beneFin.getDescBicSwift());
					beneFinanziarioWS.setDescEnteCreditizio(beneFin.getDescEnteCreditizio());
					beneFinanziarioWS.setDescFilialeEnte(beneFin.getDescFilialeEnte());
					beneFinanziarioWS.setDescDirettoreEnte(beneFin.getDescDirettoreEnte());
					beneFinanziarioWS.setDescRegistrazione(beneFin.getDescRegistrazione());
					beneFinanziarioWS.setImpoValuta(beneFin.getImpoValuta());
					if(beneFin.getTTpValuta()!=null) beneFinanziarioWS.setCodiFkIdValuta(beneFin.getTTpValuta().getCodiKIdValuta());
					beneFinanziarioWS.setImpoEuro(beneFin.getImpoEuro());
					beneWS.setBeneFinanziario(beneFinanziarioWS);
				}
				
				
				// BeneImmobile
				BeneImmobile beneImmobileWS = new BeneImmobile();
				TBeneImmobile beneImmobile = attoDao.getTBeneImmobile(tBene.getId().getCodiKIdBene(),idUffOwn);
				if (beneImmobile!=null) {
					logger.info("-->Bene immobile  " + tBene.getId().getCodiKIdBene());
					if(beneImmobile.getTTpCatCatasto()!=null)  beneImmobileWS.setCodiFkIdCategoriaCat(beneImmobile.getTTpCatCatasto().getCodiKIdCategCat());
					if(beneImmobile.getTAnComuniByCodiFkIdComuneCat()!=null)  beneImmobileWS.setCodiFkIdComuneCat(beneImmobile.getTAnComuniByCodiFkIdComuneCat().getCodiKIdComune());
					if(beneImmobile.getTAnComuniByCodiFkIdComuneCatP()!=null)  beneImmobileWS.setCodiFkIdComuneCatP(beneImmobile.getTAnComuniByCodiFkIdComuneCatP().getCodiKIdComune());
					if(beneImmobile.getTTpTipoFondo() != null) beneImmobileWS.setCodiFkIdFondo(new Character(beneImmobile.getTTpTipoFondo().getCodiKIdFondo()).toString() );
					if(beneImmobile.getTTpTipoCatastoByCodiFkIdTipoCat() != null) beneImmobileWS.setCodiFkIdTipoCat(new Character(beneImmobile.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat()).toString() );
					if(beneImmobile.getTTpTipoCatastoByCodiFkIdTipoCatP() != null) beneImmobileWS.setCodiFkIdTipoCatP(new Character(beneImmobile.getTTpTipoCatastoByCodiFkIdTipoCatP().getCodiKIdTipoCat()).toString() );
					if(beneImmobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia() != null)  beneImmobileWS.setCodiFkIdTipoDenuncia(new Character(beneImmobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia().getCodiKIdTipoDen()).toString() );
					if(beneImmobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP() != null) beneImmobileWS.setCodiFkIdTipoDenunciaP(new Character(beneImmobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP().getCodiKIdTipoDen()).toString() );
					beneImmobileWS.setCodiKIdBeneImm(tBene.getId().getCodiKIdBene());
					beneImmobileWS.setCodiKIdUffOwner(idUffOwn);
					beneImmobileWS.setDataDomandaAccatast(Utility.toXMLGregorianCalendar(beneImmobile.getDataDomandaAccatast() ));
					beneImmobileWS.setDescCatasto(beneImmobile.getDescCatasto() );
					beneImmobileWS.setDescCatastoP(beneImmobile.getDescCatastoP() );
					beneImmobileWS.setDescEdificio(beneImmobile.getDescEdificio() );
					beneImmobileWS.setDescFoglio(beneImmobile.getDescFoglio() );
					beneImmobileWS.setDescFoglioP(beneImmobile.getDescFoglioP() );
					beneImmobileWS.setDescInterno(beneImmobile.getDescInterno());
					beneImmobileWS.setDescLotto(beneImmobile.getDescLotto() );
					beneImmobileWS.setDescParticella1(beneImmobile.getDescParticella1() );
					beneImmobileWS.setDescParticella1P(beneImmobile.getDescParticella1P() );
					beneImmobileWS.setDescParticella2(beneImmobile.getDescParticella2() );
					beneImmobileWS.setDescParticella2P(beneImmobile.getDescParticella2P() );
					beneImmobileWS.setDescPiano(beneImmobile.getDescPiano());
					beneImmobileWS.setDescScala(beneImmobile.getDescScala());
					beneImmobileWS.setDescSchedaAccatast(beneImmobile.getDescSchedaAccatast() );
					beneImmobileWS.setDescSezione(beneImmobile.getDescSezione() );
					beneImmobileWS.setDescSezioneP(beneImmobile.getDescSezioneP() );
					beneImmobileWS.setDescSubalterno1P(beneImmobile.getDescSubalterno1P() );
					beneImmobileWS.setDescSubalterno2P(beneImmobile.getDescSubalterno2P() );
					beneImmobileWS.setDescSubParticella1(beneImmobile.getDescSubParticella1() );
					beneImmobileWS.setDescSubParticella2(beneImmobile.getDescSubParticella2() );
					beneImmobileWS.setDescZona(beneImmobile.getDescZona() );
					if(beneImmobile.getFlgAppFabConf()!=null) beneImmobileWS.setFlgAppFabConf(beneImmobile.getFlgAppFabConf().toString() );
					if(beneImmobile.getFlgPresConcEdil()!=null)  beneImmobileWS.setFlgPresConcEdil(beneImmobile.getFlgPresConcEdil().toString() );
					beneImmobileWS.setImpoRenditaCat(beneImmobile.getImpoRenditaCat() );
					beneImmobileWS.setImpoRenditaDom(beneImmobile.getImpoRenditaDom() );
					beneImmobileWS.setNumeAnnoDenuncia(beneImmobile.getNumeAnnoDenuncia() );
					beneImmobileWS.setNumeAnnoDenunciaP(beneImmobile.getNumeAnnoDenunciaP() );
					beneImmobileWS.setNumeDenuncia1(beneImmobile.getNumeDenuncia1() );
					beneImmobileWS.setNumeDenuncia1P(beneImmobile.getNumeDenuncia1P() );
					beneImmobileWS.setNumeDenuncia2(beneImmobile.getNumeDenuncia2() );
					beneImmobileWS.setNumeDenuncia2P(beneImmobile.getNumeDenuncia2P() );
					
					beneImmobileWS.setNumeGraffati(beneImmobile.getNumeGraffati() );
					if(beneImmobile.getNumeMcDec() != null) beneImmobileWS.setNumeMcDec((short)beneImmobile.getNumeMcDec() );
					beneImmobileWS.setNumeMcInt(beneImmobile.getNumeMcInt() );
					beneImmobileWS.setNumeSupAre(beneImmobile.getNumeSupAre() );
					beneImmobileWS.setNumeSupCentiare(beneImmobile.getNumeSupCentiare() );
					beneImmobileWS.setNumeSupEttari(beneImmobile.getNumeSupEttari() );
					if(beneImmobile.getNumeSupMqDec() != null) beneImmobileWS.setNumeSupMqDec((short)beneImmobile.getNumeSupMqDec() );
					beneImmobileWS.setNumeSupMqInt(beneImmobile.getNumeSupMqInt() );
					beneImmobileWS.setNumeVaniDec(beneImmobile.getNumeVaniDec() );
					beneImmobileWS.setNumeVaniInt(beneImmobile.getNumeVaniInt() );

					beneWS.setBeneImmobile(beneImmobileWS);
				}
				request.getProcedimento().getRelProcBene().add(relProcBeneWS);
			}
		}
	
	
		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioByIdUfficio(idUffOwn);
		
		List<TRelProcProc> relProcProcList = procedimentoDao.getTRelProcProcByIdProc1Padre(idProcedimento);
		if (tipoUfficio.getCodiKIdTipoUff() == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
			if (tProcedimento.getTTpTipoFascicolo().getCodiKIdTipoFasc()  == Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO) {
				if (relProcProcList !=null && !relProcProcList.isEmpty()) {
					for (TRelProcProc relProcProc : relProcProcList) {
						// Sono in un cameralino nato in cda con riferimento a prevenzione in CDA 
						long idProc2 = relProcProc.getId().getCodiKIdProc2(); // Id del procedimento di prevenzione padre
						if (procedimentoDao.hasInvioInCassazione(idProc2)) {
							RelProcedimentoProc relProcProcWS = valorizzaRelProcedimentoProc(relProcProc);
							request.getRelProcedimentoProc().add(relProcProcWS);
						}
					}
				} else {
					// Sono in un cameralino nato in trib  impugnato in CDA 
					List<TRelProcProc> relProcProcListTrib = procedimentoDao.getTRelProcProcByIdProc1(idProcedimento);
					for (TRelProcProc tRelProcProc : relProcProcListTrib) {
						TTpTipoUfficio tipoUfficio2 = commonDao.getTipoUfficioByIdUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());
						if (tipoUfficio2.getCodiKIdTipoUff() == Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE).longValue()) {
							
							List<TRelProcProc> relProcProcPadreTrib = procedimentoDao.getTRelProcProcByIdProc1Padre(tRelProcProc.getId().getCodiKIdProc2());
							for (TRelProcProc relProcProc : relProcProcPadreTrib) {
								long idProc2 = relProcProc.getId().getCodiKIdProc2(); // Id dei procedimenti collegati al padre
								
								List<TRelProcProc> relProcProcListColTrib = procedimentoDao.getTRelProcProcByIdProc1(idProc2);
								for (TRelProcProc tRelProcProcColTrib : relProcProcListColTrib) {
									tipoUfficio2 = commonDao.getTipoUfficioByIdUfficio(tRelProcProcColTrib.getTAnUfficio().getCodiKIdUfficio());
									if (tipoUfficio2.getCodiKIdTipoUff() == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
										long idProc2Figlio = tRelProcProcColTrib.getId().getCodiKIdProc2(); // Id del procedimento di prevenzione padre
										if (procedimentoDao.hasInvioInCassazione(idProc2Figlio)) {
											RelProcedimentoProc relProcProcWS = valorizzaRelProcedimentoProc(tRelProcProcColTrib);
											request.getRelProcedimentoProc().add(relProcProcWS);
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			if (relProcProcList!=null) {
				for (TRelProcProc relProcProc : relProcProcList) {
					// Sono in un cameralino con riferimento a prevenzione in Tribunale
					long idProc2 = relProcProc.getId().getCodiKIdProc2(); // Id del procedimento di prevenzione padre
					
					List<TRelProcProc> relProcProcListTrib = procedimentoDao.getTRelProcProcByIdProc1(idProc2);
					if (relProcProcListTrib!=null) {
						for (TRelProcProc tRelProcProc : relProcProcListTrib) {
							TTpTipoUfficio tipoUfficio2 = commonDao.getTipoUfficioByIdUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());
							if (tipoUfficio2.getCodiKIdTipoUff() == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
								long idProc2Figlio = tRelProcProc.getId().getCodiKIdProc2(); // Id del procedimento di prevenzione padre
								if (procedimentoDao.hasInvioInCassazione(idProc2Figlio)) {
									RelProcedimentoProc relProcProcWS = valorizzaRelProcedimentoProc(tRelProcProc);
									request.getRelProcedimentoProc().add(relProcProcWS);
								}
							}
						}
					}
				}
			}
		}
		 
		byte[] requestXML = marshaller(request) ;
		RicorsoResponse response = null ;
		if(Configuration.isAbilitaCassazione())
		{
		//	try {
				// scrivo tu tabella prima con valore per avere xml da utilizzare per prova ALfredo
				commonDao.modificaTCassMsg(idUffOwn, idAttoImpugnazione, idProcedimento,'0',"test",requestXML);
				// poi verificare se caso di lasciarlo 
				response = ricorsoCassazioneServiceClient.inviaRicorso(request);
	 			// TODO Nuove modifiche cassazzione
				if (response.getCodice()==0) {
					//Se la chiamata è stata processata correttamente 
					commonDao.modificaTCassMsg(idUffOwn, idAttoImpugnazione, idProcedimento,'1',"",requestXML);
				} else {
					// Se 
					commonDao.modificaTCassMsg(idUffOwn, idAttoImpugnazione, idProcedimento,'0',response.getDescrizione(),requestXML);
				}
		//	} catch (Exception e) {
	 			// TODO Nuove modifiche cassazzione
		//		commonDao.modificaTCassMsg(idUffOwn, idAttoImpugnazione, idProcedimento,'0',e.getMessage(),requestXML);
		//	}
		}
		else
			logger.info("Chiamata al servizio di Cassazione non abilitata");
		return response;
	}
	
	private RelProcedimentoProc valorizzaRelProcedimentoProc(TRelProcProc relProcProc) {
		RelProcedimentoProc relProcProcWS = new RelProcedimentoProc();
		
		if (relProcProc.getTAnUfficio()!=null) relProcProcWS.setCodiFkIdUffOwner2(relProcProc.getTAnUfficio().getCodiKIdUfficio());
		relProcProcWS.setCodiKIdProc1(relProcProc.getTProcedimento().getCodiKIdProc());
		relProcProcWS.setCodiKIdProc2(relProcProc.getId().getCodiKIdProc2());
		relProcProcWS.setDataApeProc2(Utility.toXMLGregorianCalendar(relProcProc.getDataApeProc2()));
		relProcProcWS.setFlgPadre(relProcProc.getFlgPadre().toString());
		if(relProcProc.getFlgTipoRel()!=null) relProcProcWS.setFlgTipoRel(relProcProc.getFlgTipoRel().toString());
		relProcProcWS.setNumeAnnoProc2(relProcProc.getNumeAnnoProc2());
		relProcProcWS.setNumeProcedimento2(relProcProc.getNumeProcedimento2());

		return relProcProcWS;
	}



	/**
	 * Esegue il marshaller per la response e la request del servizio
	 */
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
}

