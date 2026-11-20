package it.giustizia.smp.service;

import static it.giustizia.smp.utility.NumbersUtility.getBigInteger;
import it.giustizia.smp.dao.AnbscDao;
import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TTpCatBene;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpTipoBene;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.NumbersUtility;
import it.giustizia.smp.xml.Amministrazione;
import it.giustizia.smp.xml.Animali;
import it.giustizia.smp.xml.Azienda;
import it.giustizia.smp.xml.BeneT;
import it.giustizia.smp.xml.Collezioni;
import it.giustizia.smp.xml.Finanziario;
import it.giustizia.smp.xml.Immateriale;
import it.giustizia.smp.xml.Immobile;
import it.giustizia.smp.xml.Misura;
import it.giustizia.smp.xml.Persona;
import it.giustizia.smp.xml.PersonaFisica;
import it.giustizia.smp.xml.PersonaGiuridica;
import it.giustizia.smp.xml.Procedimento;
import it.giustizia.smp.xml.ProvvedimentoT;
import it.giustizia.smp.xml.SedeLegale;
import it.giustizia.smp.xml.Ubicazione;
import it.giustizia.smp.xml.Veicolo;
import it.giustizia.smp.xml.amministratori.TipoStradaType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class TrasmissioneAmministratoriServiceImpl implements TrasmissioneAmministratoriService {

	@Autowired
	AttoDao attoDao;

	@Autowired
	AnbscDao anbscDao;

	@Autowired
	BeneDao beneDao;
	
	@Autowired
	ProcedimentoDao procedimentoDao;

	@Autowired
	private BeneService beneService;

	@Autowired
	private CommonDao         commonDao;	

	public TAnimali getTAnimali(Long idBene, int idUffOwn) {
		return attoDao.getTAnimali(idBene, idUffOwn);
	}

	public Animali getAnimali(Long idBene, int idUffOwn) {
		TAnimali tan = attoDao.getTAnimali(idBene, idUffOwn);
		if (tan!=null) {
			Animali out = new Animali();
			out.setDescrizione(tan.getDescCapo());
			out.setNumero(getBigInteger(tan.getNumeAnimali()));	
			return out;
		}
		return null;
	}
	
	public TAzienda getTAzienda(Long idBene) {
		return anbscDao.getAzienda(idBene);
	}

	public Azienda getAzienda(Long idBene) {
		TAzienda ta = anbscDao.getAzienda(idBene);
		if (ta!=null) {
			Azienda out = new Azienda();
			out.setAmministratore(ta.getDescAmministratore());
			out.setCapitaleSociale(getBigInteger(ta.getImpoCapitaleSoc()));
			out.setCodiceFiscale(ta.getDescCodFisc());
			out.setNumeroCCIA(ta.getDescCciaa());
			out.setNumeroDipendenti(getBigInteger(ta.getNumeDipendenti()));
			out.setPartitaIVA(ta.getDescPIva());
			out.setRagioneSociale(ta.getDescRagioneSoc());
			out.setRappresentanteSocieta(ta.getDescRappresSoc());
			if (ta.getTTpSettoreAtt() != null){
				out.setSettore(ta.getTTpSettoreAtt().getDescSettoreAtt());				
			}
	
			return out;
		} else {
			return null;
		}
	}
	
	public Procedimento getProcedimento(Long idProcedimento) {
		TProcedimento tProcedimento = procedimentoDao.getProcedimentoById(idProcedimento);
		Procedimento procedimento = new Procedimento();
		procedimento.setAnnoProcedimento(tProcedimento.getNumeAnnoProc());
		procedimento.setId(getBigInteger(idProcedimento));
		procedimento.setNumeroProcedimento(getBigInteger(tProcedimento.getNumeProc()));
		procedimento.setUfficioCompetente(tProcedimento.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio());
		return procedimento;
	}

	public ProvvedimentoT getProvvedimento(Long idAtto, int idUffOwner) {
		TAtto atto = attoDao.getAttoById(new TAttoId(idAtto, idUffOwner));
		TDecreto decreto = attoDao.getTDecreto(idAtto, idUffOwner);
		ProvvedimentoT provvedimentoT = new ProvvedimentoT();
		provvedimentoT.setAnnoProvvedimento(getBigInteger(decreto.getNumeAnnoAtto()));
		provvedimentoT.setDataDeposito(atto.getDataDeposito());
		provvedimentoT.setId(getBigInteger(atto.getId().getCodiKIdAtto()));
		provvedimentoT.setNumero(getBigInteger(decreto.getNumeAtto()));
		provvedimentoT.setTipo(atto.getTTpTipoAtto().getDescTipoAtto());
		
		// TODO TOOL AMMINISTRATORI
		return provvedimentoT;
	}

	
	
	public List<BeneT> getTBeni(long idProcedimento, long idAtto, int idUffOwner){

		List<BeneT> beniTList = new ArrayList<BeneT>();	
		List<Long> idBeni = beneService.getListaIdBeniAssociatiAdAttoEProcedimento(idProcedimento, idAtto);
		
		Iterator<Long> iter = idBeni.iterator();
		while( iter.hasNext() ) {
			Long currentIdBene = ((Long)iter.next());
			
			TBene tBene = beneDao.getBene(currentIdBene); 
			
			BeneT beneT = getTBene(currentIdBene, idUffOwner, tBene);			
			beniTList.add(beneT);
		}				
		return beniTList;
	}
	
	public BeneT getTBene(Long idBene, int idUffOwn, TBene model) {
			
		BeneT beneT = new BeneT();

		BigInteger categoriaBene = beneDao.getCategoriaBene(idBene);
		BigInteger sottocategoriaBene = beneDao.getSottocategoriaBene(idBene);
		
		beneT.setId(BigInteger.valueOf(idBene));		
		
		if (model.getTTpTipoBene() != null)
			beneT.setTipo(getTipologiaBene(model.getTTpTipoBene().getCodiKIdTipoBene()) != null ? getTipologiaBene(model.getTTpTipoBene().getCodiKIdTipoBene()) : "");
		
		if (model.getTTpCatBene() != null)		
			beneT.setCategoria(getCategoriaBene(model.getTTpCatBene().getCodiKIdCatBene()) != null ? getCategoriaBene(model.getTTpCatBene().getCodiKIdCatBene()) : "");

		if (model.getTTpSottocatBene() != null)
			beneT.setSottocategoria(getSottoCategoriaBene(model.getTTpSottocatBene().getCodiKIdSottocat()) != null ? getSottoCategoriaBene(model.getTTpSottocatBene().getCodiKIdSottocat()) : "");

		beneT.setUbicazione(getUbicazione(model));
		if(model.getFlgGravame() != null)
		{
			if(model.getFlgGravame().equals("S"))
				beneT.setGravatoDaPegnoIpoteca(true);
			if(model.getFlgGravame().equals("N"))
				beneT.setGravatoDaPegnoIpoteca(false);
		}
		
		
		if(categoriaBene != null) {
			if (categoriaBene.longValue() == Constants.CategoriaBeni.ID_ALTRI_OGGETTI) {
				String splitOnString = " - ";
				String descBene = model.getDescrBene();
				String descSottoCategoria = getSottoCategoriaBene(sottocategoriaBene.intValue());
				String[] parts = descBene.split(splitOnString);
				int i = 0;
				while (!parts[i].equals(descSottoCategoria)) {i++;}
				String descrizioneBene = ((++i) <= parts.length) ? parts[i] : "";
				while (++i < parts.length) {
					descrizioneBene += splitOnString + parts[i];
				}
				beneT.setAltriOggettiDescr(descrizioneBene);
			} else if (categoriaBene.intValue() >= 1 && categoriaBene.intValue() <= 5) {
				// IMMOBILI
				beneT.setImmobile(getImmobile(model));
			} else if (categoriaBene.intValue() == 6 && (sottocategoriaBene == null || (sottocategoriaBene.intValue() >= 36 && sottocategoriaBene.intValue() <= 39))) {
				// DENARO
				beneT.setFinanziario(getDenaro(model));
			} else if (categoriaBene.intValue() == 6 && sottocategoriaBene != null && sottocategoriaBene.intValue() == 35) {
				// DENARO CONTANTE
				beneT.setFinanziario(getContante(model));
			} else if (categoriaBene.intValue() == 7) {
				// COLLEZIONI
				beneT.setCollezioni(getCollezioni(model));
			} else if (categoriaBene.intValue() == 8) {
				// ANIMALI
				beneT.setAnimali(getAnimali(idBene, idUffOwn));
			} else if (categoriaBene.intValue() == 10) {
				// VEICOLO
				beneT.setVeicolo(getVeicoli(model));
			} else if (categoriaBene.intValue() == 11) {
				// IMMATERIALI
				beneT.setImmateriale(getImmateriali(model));
			} else if ((categoriaBene.intValue() >= 12 && categoriaBene.intValue() <= 16) || (categoriaBene.intValue() >= 31 && categoriaBene.intValue() <= 32)) {
				// FINANZIARI
				beneT.setFinanziario(getFinanziario(model));
			} else if (categoriaBene.intValue() >= 17 && categoriaBene.intValue() <= 30) {
				// AZIENDA
				beneT.setAzienda(getAzienda(idBene));
			}
		}
		
		beneT.setDescBene(model.getDescrBene());
		
		return beneT;
	}
	
	private Finanziario getDenaro(TBene model){

		Finanziario finanziario = new Finanziario();
		
		finanziario.setTipo("Denaro");
		
		TDenaro denaroModel = anbscDao.getDenaro(model.getId().getCodiKIdBene());
		if(denaroModel != null)
		{
			if(denaroModel.getDescEnteDepos() != null)
				finanziario.setDescEnte(denaroModel.getDescEnteDepos());
			else
				finanziario.setDescEnte(Constants.NON_DISPONIBILE);
									
			finanziario.setFilialeModulo(denaroModel.getDescFiliale());
			finanziario.setDirettore(denaroModel.getDescDirettore());
			finanziario.setSwift(denaroModel.getDescBicSwift());
			finanziario.setIban(denaroModel.getDescIban());

			TAnOperFinanz operatoreFinanziario = denaroModel.getTAnOperFinanz();
			if(denaroModel.getDescFiliale() == null)
			{
				if(operatoreFinanziario!=null && operatoreFinanziario.getId().getCodiKIdAbi()!=null && operatoreFinanziario.getId().getCodiKIdCab()!=null)
				{
					TAnOperFinanz operatoreFinanziarioModel = anbscDao.getOperatoreFinanziario(operatoreFinanziario.getId().getCodiKIdAbi(), operatoreFinanziario.getId().getCodiKIdCab());
					finanziario.setFilialeModulo(getDescFiliale(operatoreFinanziarioModel));
					finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
					finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
				}
			}
			else {
				finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
				finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
			}
			
//abi...
//cab..
//cc..					
//	    	if(denaroModel.getDescIban()!=null && denaroModel.getDescIban().length() > 10){
//	    		finanziario.setAbi(denaroModel.getDescIban().substring(5, 10));
//	    	}
//	    	if(denaroModel.getDescIban()!=null && denaroModel.getDescIban().length() > 15){
//	    		finanziario.setCab(denaroModel.getDescIban().substring(10, 15));
//		    }	
			
	    	if(denaroModel.getDescIban()!=null && denaroModel.getDescIban().length() > 27){
	    		finanziario.setNnumerocc(denaroModel.getDescIban().substring(15, denaroModel.getDescIban().length()-1));
		    }	    	
	    		    	
		    if(denaroModel.getTTpValuta() != null){
		    	finanziario.setTipoValuta(denaroModel.getTTpValuta().getCodiKIdValuta());		    	
		    }
									
			finanziario.setImportoValuta(denaroModel.getImpoValuta()); 
			finanziario.setImportoEuro(denaroModel.getImpoEuro()); 

			if(denaroModel.getDescIdentEnte() != null)
				finanziario.setIdent(denaroModel.getDescIdentEnte());
			else
				finanziario.setIdent(Constants.NON_DISPONIBILE);
			
		}
		return finanziario;
	}
	
	private Finanziario getContante(TBene model){
		Finanziario finanziario = new Finanziario();
		
		finanziario.setTipo("DenaroContante");
		
		TContante contanteModel = anbscDao.getContante(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(contanteModel != null)
		{
			if(contanteModel.getDescEnteDepos() != null)
				finanziario.setDescEnte(contanteModel.getDescEnteDepos());
			else
				finanziario.setDescEnte(Constants.NON_DISPONIBILE);
			
			if(contanteModel.getNumeIdentModTrib() != null)
				finanziario.setFilialeModulo(contanteModel.getNumeIdentModTrib().toString());
			
			finanziario.setDirettore(contanteModel.getDescIntestatario());									
			finanziario.setSwift(contanteModel.getDescBicSwift());
			finanziario.setIban(contanteModel.getDescIban());
			
			TAnOperFinanz operatoreFinanziario = contanteModel.getTAnOperFinanz();
			if(contanteModel.getNumeIdentModTrib() == null)
			{
				if(operatoreFinanziario!=null && operatoreFinanziario.getId().getCodiKIdAbi()!=null && operatoreFinanziario.getId().getCodiKIdCab()!=null)
				{
					TAnOperFinanz operatoreFinanziarioModel = anbscDao.getOperatoreFinanziario(operatoreFinanziario.getId().getCodiKIdAbi(), operatoreFinanziario.getId().getCodiKIdCab());
					finanziario.setFilialeModulo(getDescFiliale(operatoreFinanziarioModel));
					finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
					finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
				}
			}
			else {
				finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
				finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
			}
			
//abi...
//cab..
//cc..
//	    	if(contanteModel.getDescIban()!=null && contanteModel.getDescIban().length() > 10){
//	    		finanziario.setAbi(contanteModel.getDescIban().substring(5, 10));
//	    	}
//	    	if(contanteModel.getDescIban()!=null && contanteModel.getDescIban().length() > 15){
//	    		finanziario.setCab(contanteModel.getDescIban().substring(10, 15));
//		    }	
			
	    	if(contanteModel.getDescIban()!=null && contanteModel.getDescIban().length() > 27){
	    		finanziario.setNnumerocc(contanteModel.getDescIban().substring(15, contanteModel.getDescIban().length()-1));
		    }	    				
			
		    if(contanteModel.getTTpValuta() != null){
		    	finanziario.setTipoValuta(contanteModel.getTTpValuta().getCodiKIdValuta());		    	
		    }
				
			finanziario.setImportoValuta(contanteModel.getImpoValuta());
			finanziario.setImportoEuro(contanteModel.getImpoEuro());
			
			if(contanteModel.getDescIdentEnte() != null)
				finanziario.setIdent(contanteModel.getDescIdentEnte());
			else
				finanziario.setIdent(Constants.NON_DISPONIBILE);
			
		}
		return finanziario;
	}	

	
	private Finanziario getFinanziario(TBene model){
		Finanziario finanziario = new Finanziario();
		
		finanziario.setTipo("Finanziario");
		
		TBeneFin finanziarioModel = anbscDao.getFinanziario(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(finanziarioModel != null)
		{
			if(finanziarioModel.getTAnOperFinanz() != null)  finanziario.setDescEnte(finanziarioModel.getTAnOperFinanz().getDescBanca());
			else finanziario.setDescEnte(Constants.NON_DISPONIBILE);
		    finanziario.setFilialeModulo(finanziarioModel.getDescFilialeEnte());
		    finanziario.setDirettore(finanziarioModel.getDescDirettoreEnte());
		    if(finanziarioModel.getTTpValuta() != null){
		    	finanziario.setTipoValuta(finanziarioModel.getTTpValuta().getCodiKIdValuta());		    	
		    }
		    finanziario.setImportoValuta(finanziarioModel.getImpoValuta());
		    finanziario.setImportoEuro(finanziarioModel.getImpoEuro());
		    
		    
		    if (model.getTTpCatBene().getCodiKIdCatBene() == 32 ) { // POLIZZA ASSICURATIVA
		    	finanziario.setCodiceFiscale(finanziarioModel.getDescIban());
		    	finanziario.setNumeroPolizza(finanziarioModel.getDescRegistrazione());
		    	finanziario.setDescEnte(finanziarioModel.getDescEnteCreditizio());
		    }
		    else {
		    	finanziario.setSwift(finanziarioModel.getDescBicSwift());
			    finanziario.setIban(finanziarioModel.getDescIban());
				
				TAnOperFinanz operatoreFinanziario = finanziarioModel.getTAnOperFinanz();
				if(finanziarioModel.getDescFilialeEnte() == null)
				{
					if(operatoreFinanziario!=null && operatoreFinanziario.getId().getCodiKIdAbi()!=null && operatoreFinanziario.getId().getCodiKIdCab()!=null)
					{
						TAnOperFinanz operatoreFinanziarioModel = anbscDao.getOperatoreFinanziario(operatoreFinanziario.getId().getCodiKIdAbi(), operatoreFinanziario.getId().getCodiKIdCab());
						finanziario.setFilialeModulo(getDescFiliale(operatoreFinanziarioModel));
						finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
						finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
					}
				}
				else {
					finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
					finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());	
				}
				
			    finanziario.setNnumerocc(finanziarioModel.getDescRegistrazione());
			    		    		    			    
			   
			    if (Constants.NON_DISPONIBILE.equals(finanziario.getDescEnte())){
			    	finanziario.setDescEnte(finanziarioModel.getDescEnteCreditizio());
			    }
		    } 
		}

		return finanziario;
	}

	private String getDescFiliale(TAnOperFinanz model){
		String result = "";
		if(model.getDescCap() != null)
			result += model.getDescCap() + "- ";
		if(model.getDescComune() != null)
			result += model.getDescComune() + "-";
		if(model.getDescProv() != null)
			result += model.getDescProv() + "-";
		if(model.getDescIndirizzo() != null)
			result += model.getDescIndirizzo();
		return result;
	}
	
	private Immateriale getImmateriali(TBene model){
		Immateriale immateriale = new Immateriale();
		TBeneImmat immaterialeModel = anbscDao.getImmateriali(model.getId().getCodiKIdBene());
		if(immaterialeModel != null)
		{
			immateriale.setNumeroRegistrazione(immaterialeModel.getDescRegistrazione());
			if(immaterialeModel.getDataRegistrazione() != null)
				immateriale.setDataRegistrazione(immaterialeModel.getDataRegistrazione());	
			if(immaterialeModel.getDataScadenza() != null)
				immateriale.setDataScadenza(immaterialeModel.getDataScadenza());
			
		}
		return immateriale;
	}
	
	private Immobile getImmobile(TBene model){
		Immobile immobile = new Immobile();
		TBeneImmobile immobileModel = anbscDao.getImmobile(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(immobileModel != null)
		{
		    immobile.setEdificio(immobileModel.getDescEdificio());
		    immobile.setScala(immobileModel.getDescScala());
		    immobile.setPiano(immobileModel.getDescPiano());
		    immobile.setInterno(immobileModel.getDescInterno());
		    
		    if(immobileModel.getNumeMcInt()!=null)
		    	immobile.setVolumeInt(getBigInteger(immobileModel.getNumeMcInt()));		    
		    if(immobileModel.getNumeMcDec() != null)
		    	immobile.setVolumeDec(getBigInteger(immobileModel.getNumeMcDec().toString()));

		    if(immobileModel.getNumeVaniInt() != null)
		    	immobile.setVaniInt(getBigInteger(immobileModel.getNumeVaniInt()));
		    if(immobileModel.getNumeVaniDec() != null)
		    	immobile.setVaniDec(getBigInteger(immobileModel.getNumeVaniDec()));
		    
		    immobile.setLotto(immobileModel.getDescLotto());
		    		    
		    if(immobileModel.getNumeSupMqInt()!=null)
		    	immobile.setSuperficieInt(getBigInteger(immobileModel.getNumeSupMqInt()));
		    if(immobileModel.getNumeSupMqDec() != null)
		    	immobile.setSuperficieDec(getBigInteger(immobileModel.getNumeSupMqDec().toString()));		    
		    	
		    if(immobileModel.getNumeSupEttari() != null)
		    	immobile.setEttari(BigInteger.valueOf(immobileModel.getNumeSupEttari()));
		    if(immobileModel.getNumeSupAre() != null)
		    	immobile.setAre(BigInteger.valueOf(immobileModel.getNumeSupAre()));
		    if(immobileModel.getNumeSupCentiare() != null)
		    	immobile.setCentiare(BigInteger.valueOf(immobileModel.getNumeSupCentiare()));
		    
		    if(immobileModel.getTTpTipoCatastoByCodiFkIdTipoCat()!= null)
		    	immobile.setTipoCatasto(Character.toString(immobileModel.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat()));
		    if(immobileModel.getTAnComuniByCodiFkIdComuneCat()!=null && immobileModel.getTAnComuniByCodiFkIdComuneCat().getDescBelfiore()!=null)
		    	immobile.setIdentificativoComuneCatasto(immobileModel.getTAnComuniByCodiFkIdComuneCat().getDescBelfiore());
		    
		    immobile.setSezioneCatasto(immobileModel.getDescSezione());
		    immobile.setFoglioCatasto(immobileModel.getDescFoglio());
		    
		    immobile.setParticellaCatasto1(immobileModel.getDescParticella1());
		    immobile.setSubparticellaCatasto1(immobileModel.getDescSubParticella1());
		    
		    immobile.setParticellaCatasto2(immobileModel.getDescParticella2());
		    immobile.setSubparticellaCatasto2(immobileModel.getDescSubParticella2());
		    
			if(immobileModel.getTTpCatCatasto()!=null)
				immobile.setIdentificativoCategoriaCatastale(Integer.toString(immobileModel.getTTpCatCatasto().getCodiKIdCategCat()));
			
			immobile.setImportoRenditaCatastale(immobileModel.getImpoRenditaCat());
			immobile.setImportoRenditaDominicale(immobileModel.getImpoRenditaDom());	 
			
		    immobile.setNumeroDomandaAccatastamento(immobileModel.getDescSchedaAccatast());
		    
		}
		
		return immobile;
	}
	
	private Collezioni getCollezioni(TBene model){
		Collezioni collezioni = new Collezioni();
		TCollezioni collezioniModel = anbscDao.getCollezioni(model.getId().getCodiKIdBene());
		if(collezioniModel != null)
		{
			if(collezioniModel.getDescCollezione() != null)
				collezioni.setDescrizioneCollezione(collezioniModel.getDescCollezione());
			collezioni.setNumeroPezzi(BigInteger.valueOf(collezioniModel.getNumePezzi()));
		}
		return collezioni;
	}
	
	private Veicolo getVeicoli(TBene model){
		Veicolo veicolo = new Veicolo();
		TVeicolo veicoloModel = anbscDao.getVeicoli(model.getId().getCodiKIdBene());
		if(veicoloModel != null)
		{
			veicolo.setNumeroRegistro(veicoloModel.getDescReg());
			if(veicoloModel.getDataReg() != null)
				veicolo.setDataRegistrazione(veicoloModel.getDataReg());
			if(veicoloModel.getDescIdentif() != null && veicoloModel.getDescIdentif().length() > 0)
				veicolo.setNumeroIdentificativo(veicoloModel.getDescIdentif());
			veicolo.setCodiceImmatricolazione(veicoloModel.getDescImmatricolaz());
			veicolo.setDescrizioneModello(veicoloModel.getDescModello());
	
			veicolo.setNumeroTarga(veicoloModel.getDescTarga());
			veicolo.setNumeroTelaio(veicoloModel.getDescTelaio());
						
		}
		return veicolo;
	}
	
	public List<Misura> getMisure(long idProc, long idAtto, Integer uffOwner){
		List<Misura> misureList = new ArrayList<Misura>();
		
		List<Long> idBeni = beneService.getListaIdBeniAssociatiAdAttoEProcedimento(idProc, idAtto);
		
		Iterator<Long> iter = idBeni.iterator();
		while(iter.hasNext() ) {
			Long currentIdBene = ((Long)iter.next());
			
			List<TRelAttoMisPatr> relAttoMisPatrList = getSoggettoTitolare(idProc, idAtto, currentIdBene, uffOwner);
//			List<Long> soggettiTitPerBene = getIdSoggettoTitolare(relAttoMisPatrList);
//			Iterator<Long> iterSoggTit = soggettiTitPerBene.iterator();
			Iterator<TRelAttoMisPatr> iterRelAttoMisPatr = relAttoMisPatrList.iterator();
			while(iterRelAttoMisPatr.hasNext() ) {
				TRelAttoMisPatr relAttoMisPatr = (TRelAttoMisPatr)iterRelAttoMisPatr.next();
				Misura misura = new Misura();
				misura.setIdentificativoBene(BigInteger.valueOf(currentIdBene));
				misura.setIdentificativoSoggettoTitolare(BigInteger.valueOf(relAttoMisPatr.getId().getCodiKIdSoggTit()));
				
				misura.setTipoInteresse(Integer.toString((relAttoMisPatr.getId().getCodiKIdTipoInt())));
				TRelBeneAtto relBeneAtto = getTRelBeneAtto(idProc, idAtto, currentIdBene, relAttoMisPatr.getId().getCodiKIdSoggTit(), relAttoMisPatr.getId().getCodiKIdTipoInt(), uffOwner);
				if(relBeneAtto != null)
				{
					//QUOTA					
					if(relBeneAtto.getNumeQuota() != null)
						misura.setQuota(relBeneAtto.getNumeQuota());
					
					//SOGGETTI PROPOSTI
					List<Long> soggettiProposti = getSoggettiProposti(idProc, idAtto, currentIdBene,relAttoMisPatr.getId().getCodiKIdSoggTit(),uffOwner);
					if(soggettiProposti != null)
					{
						List<BigInteger> listSoggProposti = new ArrayList<BigInteger>();
						for (Long idSoggInt : soggettiProposti)
						{
							listSoggProposti.add(BigInteger.valueOf(idSoggInt));
						}
						misura.setIdentificativoSoggettoProposto(listSoggProposti);
					}
					if(relBeneAtto.getTAnNormative() != null)
						misura.setIdentificativoNormativa(BigInteger.valueOf(relBeneAtto.getTAnNormative().getCodiKIdNormativa()));
					else
						misura.setIdentificativoNormativa(BigInteger.valueOf(0));
					if(relBeneAtto.getTTpTipoMisura() != null)
					{
						if(relBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 5)
							misura.setDecisione("S");//Sequestro
						else if(relBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 20)
							misura.setDecisione("R");//Revoca
					}
					if(relBeneAtto.getTTpEsitoFinale() != null)
					{
						if(relBeneAtto.getTTpEsitoFinale().getCodiKIdEsitoFin() == 1)
							misura.setDecisione("C");//Sequestro
						else if(relBeneAtto.getTTpEsitoFinale().getCodiKIdEsitoFin() == 2)
							misura.setDecisione("R");
					}
				}
				
				misureList.add(misura);
			}
		}
		
		if(misureList.size()>1)
		{
			Collections.sort(misureList);
	    	Misura temp1 = misureList.get(0);
	    	for(int i=1; i<misureList.size(); i++)
	    	{
	    		Misura temp2 = misureList.get(i);
	    		if(temp1.getIdentificativoBene().equals(temp2.getIdentificativoBene())
	    				&& ((temp1.getIdentificativoNormativa()!=null && temp2.getIdentificativoNormativa()!=null)
	    					&&
	    					(temp1.getIdentificativoNormativa().equals(temp2.getIdentificativoNormativa()))
	    					)
	        			&& temp1.getIdentificativoSoggettoTitolare().equals(temp2.getIdentificativoSoggettoTitolare())
	        			&& temp1.getTipoInteresse().equals(temp2.getTipoInteresse()))
	    		{
	    			misureList.remove(i);
	    			i--;
	    		}
	    		temp1 = temp2;
	    	}
		}
    	
		return misureList;
	}

	public List<Persona> getSoggetti(int uffOwner, long idProc, long idAtto){
		List<Persona> persone = new ArrayList<Persona>();
		
		List<Long> idBeni = beneService.getListaIdBeniAssociatiAdAttoEProcedimento(idProc, idAtto);
		
		Iterator<Long> iter = idBeni.iterator();
		while(iter.hasNext() ) {
			Long currentIdBene = ((Long)iter.next());
			
			List<Long> listMisPatr = anbscDao.getRelAttoMisPatr(idProc,idAtto,currentIdBene);
			if(listMisPatr != null)
			{
				Iterator<Long> iterTRelAttoMisPatr = listMisPatr.iterator();
				while(iterTRelAttoMisPatr.hasNext())
				{
					Long idSoggetto = (Long)iterTRelAttoMisPatr.next();
					
					TRelProcSogint procSogint = getTRelProcSogint(idProc,idSoggetto,uffOwner);
					short versioneSoggetto = 0;
					if(procSogint != null)
					{
						if(procSogint.getTRelProcSogintVer() != null)
						{
							versioneSoggetto = procSogint.getTRelProcSogintVer().getId().getCodiKIdVer();
						}
					}
					String tipoPersona = getTipoSoggetto(idSoggetto);
					
					if(tipoPersona.equalsIgnoreCase("F"))
					{
						TPersonaFisica personaFisica = getPersonaFisica(idSoggetto,versioneSoggetto,uffOwner);
						if(personaFisica != null)
						{
							PersonaFisica pf = new PersonaFisica();
							pf.setIdentificativo(BigInteger.valueOf(idSoggetto));
							pf.setCognome(personaFisica.getDescCognome());
							pf.setNome(personaFisica.getDescNome());
							pf.setSesso(String.valueOf(personaFisica.getFlgSesso()));
							pf.setDataNascita(personaFisica.getDataNascita());
							if(personaFisica.getTAnStatoByCodiFkIdStatoNasc()!=null)
							{
								  Ubicazione ubicazione = new Ubicazione();
								
								if(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() == 1)//ITALIA
								{
									if(personaFisica.getTAnComuniByCodiFkIdComNasc() != null){
										  ubicazione.setIdComuneItaliano(personaFisica.getTAnComuniByCodiFkIdComNasc().getDescBelfiore());
										  ubicazione.setIdStato(getBigInteger(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato()));
									}
								}
								else //ESTERO
								{
									  ubicazione.setDescrizioneComuneEstero(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getDescBelfiore());
									  ubicazione.setIdStato(getBigInteger(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato()));
								}
																
								pf.setLuogoNascita(ubicazione);
								
							}
							
							pf.setCodiceFiscale(personaFisica.getDescCodiceFiscale());
							pf.setCui(personaFisica.getDescCui());
							
							persone.add(pf);
						}
					}
					else if(tipoPersona.equalsIgnoreCase("G"))
					{
						TPersonaGiur personaGiuridica = getPersonaGiuridica(idSoggetto,versioneSoggetto,uffOwner);
						PersonaGiuridica pg = new PersonaGiuridica();
						pg.setIdentificativo(BigInteger.valueOf(idSoggetto));
						pg.setDenominazione(personaGiuridica.getDescRagioneSoc());
						//Roberto aggiunto controllo partita IVA assente
						if(personaGiuridica.getDescPartitaIva()!=null && personaGiuridica.getDescPartitaIva().length() > 11)
							pg.setPartitaIVA(riduciPartitaIva(personaGiuridica.getDescPartitaIva()));
						else
							pg.setPartitaIVA(personaGiuridica.getDescPartitaIva());
						
						if(personaGiuridica.getTAnStato()!=null)
						{
							SedeLegale sedeLegale = new SedeLegale();
							Ubicazione ubicazione = new Ubicazione();
							
							if(personaGiuridica.getTAnStato().getCodiKIdStato() == 1)//ITALIA
							{
								if(personaGiuridica.getTAnComuni() != null){
									ubicazione.setIdComuneItaliano(personaGiuridica.getTAnComuni().getDescBelfiore());
									ubicazione.setIdStato(getBigInteger(personaGiuridica.getTAnStato().getCodiKIdStato()));
								}									
							}
							else //ESTERO
							{
								ubicazione.setDescrizioneComuneEstero(personaGiuridica.getTAnStato().getDescBelfiore());
								ubicazione.setIdStato(getBigInteger(personaGiuridica.getTAnStato().getCodiKIdStato()));
							}
							
							sedeLegale.setUbicazione(ubicazione);
							sedeLegale.setIndirizzo(personaGiuridica.getDescIndirizzoSede());
							
							pg.setSedeLegale(sedeLegale);
						}
						if (personaGiuridica.getTTpTipoSocieta()!=null) {
							pg.setTipologia(allineaTipoSocieta(personaGiuridica.getTTpTipoSocieta().getCodiKIdTipoSoc()).toString());
						}
						
						persone.add(pg);
					}

				}
			}
		}
		
		return persone;	
	}
	
	public List<Amministrazione> getAmministrazioni(long idProcedimento, long idAtto, Integer uffOwner, boolean escludiAgenzia) {
		List<TRelProcBeneAttoAmm> amministrazioniModel = anbscDao.getAmministrazioni(idProcedimento, idAtto, uffOwner, escludiAgenzia);
		if(amministrazioniModel != null)
		{
			List<Amministrazione> amministrazioniResult = new ArrayList<Amministrazione>();
			Iterator<TRelProcBeneAttoAmm> iter = amministrazioniModel.iterator();
			Amministrazione amministrazione = null;
			long amministratoreTmp = 0L;
			List<BigInteger> idBeniList = new ArrayList<BigInteger>();
			while(iter.hasNext() ) {
				TRelProcBeneAttoAmm amm = iter.next();
				if(amministratoreTmp != amm.getId().getCodiKIdAmmin())
				{
					if(amministratoreTmp!=0L && amministratoreTmp!=amm.getId().getCodiKIdAmmin())
					{
						amministrazione.setIdentificativoBene(idBeniList);
						amministrazioniResult.add(amministrazione);
					}
					amministrazione = new Amministrazione();
				
					if(amm.getDataInizioIncarico() != null)
						amministrazione.setDataInizioIncarico(amm.getDataInizioIncarico());
					if(amm.getDataFineIncarico() != null)
						amministrazione.setDataFineIncarico(amm.getDataFineIncarico());
					idBeniList = new ArrayList<BigInteger>();
					idBeniList.add(BigInteger.valueOf(amm.getId().getCodiKIdBene()));
					amministratoreTmp = amm.getId().getCodiKIdAmmin();
				}
				else
				{
					idBeniList.add(BigInteger.valueOf(amm.getId().getCodiKIdBene()));
					amministratoreTmp = amm.getId().getCodiKIdAmmin();
				}
				
				amministrazione.setCodiceFiscale(amm.getTAmministratori().getDescCodiceFiscale());
			}
			amministrazione.setIdentificativoBene(idBeniList);
			amministrazioniResult.add(amministrazione);
			return amministrazioniResult;
		}
		else
			return null;
	}
	
	
	private List<TRelAttoMisPatr> getSoggettoTitolare(long idProc, long idAtto, long idBene, int uffOwner){
		return anbscDao.getSoggettoTitolare(idProc, idAtto, idBene, uffOwner);
	}
		
	private TRelProcSogint getTRelProcSogint(long idProc, long idSogg, Integer uffOwner){
		TRelProcSogint soggetto = anbscDao.getTRelProcSogint(idProc, idSogg, uffOwner);
		return soggetto;
	}
	
	private TRelBeneAtto getTRelBeneAtto(long idProc, long idAtto, long idBene, long idSoggTit, int tipoInteresse, int uffOwner){
		return anbscDao.getTRelBeneAtto(idProc, idAtto, idBene, idSoggTit, tipoInteresse, uffOwner);
	}
	
	private List<Long> getSoggettiProposti(long idProc, long idAtto, long idBene, long idSoggTit, Integer uffOwner){
		return anbscDao.getSoggettiProposti(idProc, idAtto, idBene, idSoggTit, uffOwner);
	}
	
	private String getTipoSoggetto(long idSoggetto){
		return anbscDao.getTipoSoggetto(idSoggetto);
	}	
	
	private TPersonaFisica getPersonaFisica(long idSoggetto, short versioneSoggetto, int uffOwner){
		return anbscDao.getPersonaFisica(idSoggetto,versioneSoggetto,uffOwner);
	}
		
	private TPersonaGiur getPersonaGiuridica(long idSoggetto, short versioneSoggetto, int uffOwner){
		return anbscDao.getPersonaGiuridica(idSoggetto,versioneSoggetto,uffOwner);
	}
	
	private String riduciPartitaIva(String partitaIvaOrig)
	{
		int len = partitaIvaOrig.length();
		int beginIndex = len - 11;
		String partitaIva = partitaIvaOrig.substring(beginIndex);
		return partitaIva;
	}

	private BigInteger allineaTipoSocieta(int tipoSocietaSitmp){
		BigInteger result;
		switch (tipoSocietaSitmp) {
	    case 1:  result = new BigInteger("17");
	        break;
	    case 2:  result = new BigInteger("21");
	    	break;
	    case 3:  result = new BigInteger("22");
			break;
	    case 4:  result = new BigInteger("23");
			break;
	    case 5:  result = new BigInteger("25");
			break;
	    case 6:  result = new BigInteger("24");
			break;
	    case 7:  result = new BigInteger("18");
			break;
	    case 8:  result = new BigInteger("19");
			break;
	    case 9:  result = new BigInteger("20");
			break;
	    case 10:  result = new BigInteger("26");
			break;
	    case 11:  result = new BigInteger("19");
			break;
		default:
			result = null;
		}
		return result;
	}
	
	private Ubicazione getUbicazione(TBene beneModel){
		Ubicazione ubicazione = new Ubicazione();
		if(beneModel.getTAnComuni() != null)
		{
			ubicazione.setIdComuneItaliano(beneModel.getTAnComuni().getDescIstatComune());
			ubicazione.setIdStato(NumbersUtility.getBigInteger(1l));
		}
		else if(beneModel.getTAnStato() != null && !beneModel.getTAnStato().getDescIstatStato().equalsIgnoreCase("1"))
		{		
			ubicazione.setIdStato(new BigInteger(beneModel.getTAnStato().getDescIstatStato()));
			ubicazione.setDescrizioneComuneEstero(beneModel.getDescComuneEstero());			
		}

		ubicazione.setIndirizzo(beneModel.getDescIndirizzo());
		ubicazione.setCap(beneModel.getDescCap());
		ubicazione.setCivico(beneModel.getDescCivico());
		if (beneModel.getTTpTipoStrada()!=null ) {
			ubicazione.setTipoStrada(
					TipoStradaType.valueOf(
							getTipologiaStrada(beneModel.getTTpTipoStrada().getCodiKIdTipoStrada())
					)
			);
		}
			
		
		return ubicazione;
	}
	
	
	public String getTipologiaStrada(int idTipologia) {
		TTpTipoStrada result = commonDao.getTTpTipoStrada(idTipologia);
		return result != null ? result.getDescTipoStrada() : "";
	}
	
	public String getTipologiaBene(int idTipologia) {
		TTpTipoBene result = commonDao.getTipologiaBene(idTipologia);
		return result != null ? result.getDescTipoBene() : "";
	}
	
	public String getCategoriaBene(int idTipologia) {
		TTpCatBene result = commonDao.getCategoriaBene(idTipologia);
		return result != null ? result.getDescCatBene() : "";
	}
	
	public String getSottoCategoriaBene(int idTipologia) {
		TTpSottocatBene result = commonDao.getSottoCategoriaBene(idTipologia);
		return result != null ? result.getDescSottocatBene() : "";
	}
	
}
