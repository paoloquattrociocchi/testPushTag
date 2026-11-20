package it.giustizia.smp.dao;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnbscMsg;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
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
import it.giustizia.smp.model.TRelBeneProcDisp;
import it.giustizia.smp.model.TRelImmProcVinc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelUdienzaStatoUd;
import it.giustizia.smp.model.TTpAnbscMsg;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TVeicolo;


public interface AnbscDao {

	public boolean verificaInvioSequestroAnbsc(long idProcedimento, long idAtto);
	
	public TProcedimento getProcedimento(long idProcedimento, int uffOwner);
	
	public TProcedimento getProcedimentoEmittente(long idProcedimento);
	
	public Date getDataPropostaSequestro(long idProcedimento);
	
	public TRelMagProc getGiudiceDelegato(long idProcedimento);
	
	public String getUfficio(Integer idUfficio);
	
	public TDecreto getDecreto(long idDecreto);
	
	public Date getDataDepositoProvv(long idAtto);
	
	public List<Long> getListaIdBeni(long idProcedimento, long idAtto);
	
	public TBene getBene(long idBene);
	
	public BigInteger getTipologiaBene(long idBene);
	
	public BigInteger getCategoriaBene(long idBene);
	
	public BigInteger getSottocategoriaBene(long idBene);
	
	public TAnimali getAnimali(long idBene);
	
	public TCollezioni getCollezioni(long idBene);
	
	public TBeneImmat getImmateriali(long idBene);
	
	public TVeicolo getVeicoli(long idBene);
	
	public TAzienda getAzienda(long idBene);
	
	public TDenaro getDenaro(long idBene);
	
	public TContante getContante(long idBene, Integer uffOwner);
	
	public TBeneFin getFinanziario(long idBene, Integer uffOwner);
	
	public TBeneImmobile getImmobile(long idBene, Integer uffOwner);
	
	public TRelBeneProcDisp getDisponibilita(long idProc, long idBene);
	
	public TRelProcBene getProcBene(long idProc, long idBene);
	
	public TRelImmProcVinc getVincolo(long idProc, long idBene);
	
	public TAnOperFinanz getOperatoreFinanziario(Class clazz, long idBene);
	
	public TAnOperFinanz getOperatoreFinanziario(String abi, String cab);
	
	public List<Long> getRelAttoMisPatr(long idProc, long idAtto, long idBene);
	
	public TRelProcSogint getTRelProcSogint(long idProc, long idSogg, Integer uffOwner);
	
	public List<TRelProcSogint> getTRelProcSogintList(long idProc, long idSogg, Integer uffOwner);
	
	public String getTipoSoggetto(long idSoggetto);
	
	public TPersonaFisica getPersonaFisica(long idSoggetto, short versioneSoggetto, int uffOwner);
	
	public TPersonaGiur getPersonaGiuridica(long idSoggetto, short versioneSoggetto, int uffOwner);
	
	public List<TRelAttoMisPatr> getSoggettoTitolare(long idProc, long idAtto, long idBene, int uffOwner);
	
	public TRelBeneAtto getTRelBeneAtto(long idProc, long idAtto, long idBene, long idSoggTit, int tipoInteresse, int uffOwner);
	
	public List<TRelBeneAtto> getListaTRelBeneAtto(long idProcedimentoOrigine,long idProcedimentoDestinazione,int idUfficio);
	
	public long getIdUdienza(long idAtto, Integer idUfficio);
	
	public TUdienza getUdienza(long idProcedimento, long idAtto, Integer idUfficio);
	
	public List<TRelUdienzaStatoUd> getStatoUdienza(long idAtto, Integer idUfficio);
	
	public List<Long> getIdAmministratore(long idProcedimento, long idAtto, Integer idUfficio);
	
	public TAmministratori getAmministratore(long idAmministratore);
	
	public List<TRelProcBeneAttoAmm> getAmministrazioni(long idProcedimento, long idAtto, Integer uffOwner, boolean escludiAgenzia);
	
	public List<Long> getSoggettiProposti(long idProc, long idAtto, long idBene, long idSoggTit, Integer uffOwner);
	
	public Long insertMsg(long idProc, long idAtto, long idTipoAnbscMsg, Blob xml, String descUtente);
	
	public Object updateRispostaMsgDatabase(final long idMsg, final char esito, final String risposta, final Blob xml);
	
	public List<TAnbscMsg> getDaInviare();
	
	public TRelProcProc getProcedimentoConfiscaIIGrado(long idProc);

	public Date getDataDefinitivita(long idProcedimento, long idAtto, Integer idUfficio);
	
	public boolean verificaInvioDecretoConvalidaAnbsc(long idProcedimento, long idAtto);
	
	public List<Long> getListaIdBeniDecretoConvalida(long idProcedimento, long idAtto);
	
	public long recuperaAttoOrig(long idAtto, Integer idUfficioUtente);
	
	public List<TAtto> listSentenze();
	
	public long getIdProcedimento(long idAtto, Integer idUfficioUtente);
	
	public void updateSentenzaInviata(long idAtto, Integer idUfficioUtente);
	
	public int getTipoUfficio(Integer idUfficioUtente);
	
	public boolean verificaInvioStralcioRiunione(long idProcedimento);
	
	public TTpAnbscMsg getByCodiKIdTipoAnbscMsg(long codiKIdTipoAnbscMsg);
	
}
