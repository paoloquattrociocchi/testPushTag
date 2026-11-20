package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TCauzioneId;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TIstanzaId;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpPoliziaServizi;
import it.giustizia.smp.model.TTpTipoPrevProc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

public interface PoliziaDao {
	
	public Long insertPoliziaMsgDatabase(int idTipoServizio, long idProcedimento, long idAtto, Blob xml, int statoMsg, long idAutorita, int ufficio, String descUtente);

	public String updateRispostaMsgDatabase(long idMsg, int stato, String descErrore);
	
	public TAttoGenerico getAttoGenerico(long idAtto, int uffOwner);

	public TCauzione getCauzionebyId(TCauzioneId id);
	
	public TIstanza getIstanzabyId(TIstanzaId id);
	
	public TProcedimento getProcedimento(long idProcedimento, int uffOwner);
	
	public List<TRelProcSogint> getTRelProcSogintList(final long idProc, final int uffOwner);
	
	public TRelProcSogint getTRelProcSogint(long idProc, long idSogg, int uffOwner);
	
	public String getTipoSoggetto(long idSoggetto, int uffOwner);
	
	public TPersonaFisica getPersonaFisica(long idSoggetto, int versioneSoggetto, int uffOwner);
	
	public TPersonaFisica getPersonaFisica(long idSoggetto, int uffOwner);
	
	public TPersonaGiur getPersonaGiuridica(long idSoggetto, int versioneSoggetto, int uffOwner);
	
	public TPersonaGiur getPersonaGiuridica(long idSoggetto, int uffOwner);
	
	public TTpTipoPrevProc getTipoPrevenzioneProc(int codiKIdTipoNorm);
	
	public Long getIdUfficioDest(long idUffOwner);
	
	public Long getIdUfficioDestByIdProcedente(int idProcedente);

	public List<TRelAttoMisPatr> listaRelAttoMisPatrSogInt(long idPersona, long idProcedimento, long idAtto, int idUfficioUtente); 
	
	public TDecreto getDecreto(long idDecreto, int uffOwner);
	
	public List<TMisuraPers> listaMisurePersonaliByIdSoggetto(long idSoggettoInteressato, long idProcedimento, long idAtto, int idUffOwn);
	
	public List<TAtto> getListaSentenze();
	
	public List<TPoliziaMsg> getMessaggiDaInviare();
	
	public TPoliziaMsg getTPoliziaMsgById(final long codiKIdMsg);
	
	public String registraFlussiIngressoPolizia(long idMsg, long idUfficio, long idProcedimento, long idAutorita, java.sql.Date dataEmissione, String descrNote, String descrProcedura);

    public String registraFlussiIngressoPoliziaAgg(long idMsg, long idUfficio, long idAutorita, String descrNumAccert, java.sql.Date dataEmissione, String descrNote, String descrProcedura);
	
    public TTpPoliziaServizi getTtpPoliziaServiziById(final long codiKIdServizi);
    
    public void aggiornaFlgMps(final long idAtto, final int uffOwner, final Character flgMps);
    
    public List<BigDecimal> getUffDestinatario(int procedente, int uffOwner, long idProcedimento, String tipoProcedimento);
    
    public int getIdProcedente(int idUfficio);
    
    public void inserisciDocumentoAllegatoAtto(final TAtto datiAtto);

    public TRelProcProc getIdUffOwnerByProcAtto(final long idProc, final long idAtto);
    
    public TTpCatAtto getTtpCatAttoById(final long codiKIdCatAtto);
}
