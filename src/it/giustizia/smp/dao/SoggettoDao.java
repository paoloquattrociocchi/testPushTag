package it.giustizia.smp.dao;


import java.util.List;
import java.util.Map;

import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.BeniCollegatiTerzoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.model.TIntestatario;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelSoggSpese;



/**
 * SoggettoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Soggetto DAO
 */
public interface SoggettoDao {
 
    public DettaglioPersonaFisicaDto getDettaglioPersonaFisicaVer(TPersonaFisicaId id);

	public Map<String, Object> getDettaglioPersonaGiuVer(final TPersonaGiurId id);
	
	public List<TRelAttoMisPers> misurePersonaliSoggetto(long idSoggetto, int idUffOwn, long idProcedimento);
	
	public List<TRelAttoMisPers> cauzioniSoggetto(long idSoggetto, int idUffOwn, long idProcedimento);
	
	public List<TRelAttoMisPers> art67Soggetto(long idSoggetto, int idUffOwn, long idProcedimento);

	public List<SogIntDettaglioProcedimentoDto> getListaSoggCondannati(long idUfficio, long idProcedimento, long idAtto,String flagSpese, String flagCassa);
	
	public TRelProcSogint dettaglioRelSogInt(long idSoggetto, int idUffOwn, long idProcedimento);
	
	public List<BeniCollegatiTerzoDto> beniCollegatiTerzo(long idUfficio, long idProcedimento, long idSoggTit);
	
	public Long getIdSoggettoAnnullato(long idSoggetto, int idUffOwn, long idProcedimento);
	
	public TIntestatario getIntestatario(long idSoggetto, int idUffOwner);
	
	public List<TRelBeneAtto> flagDecretoSequestro(final long idSoggetto, final int idUffOwn,final long idProcedimento);
	
	public void salvaRelSpeseProc(TRelSoggSpese speseP, String tipoOper);
}
