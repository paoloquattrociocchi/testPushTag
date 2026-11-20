package it.giustizia.smp.dao;

import java.math.BigDecimal;

import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TRelAttoMisPers;

/**
 * NumeroAttoDao: Interfaccia Numero Atto DAO
 * 
 * @author Gioggi
 */
public interface NumeroAttoDao {

	/*
	 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
	 * Numero MEV : 006
	 * Autore : Gioggi
	 * Data : 16 apr 2020
	 * Branch : MEV_006
	 */
	public Long generaNumeroAtto(long idUffOwn, long idProcUdi, long idProcGen, long idProcProp,
			long idProcedimento, TAtto atto, String codUtente, int tipoUfficio, long idMagistrato,
			String giudiceDelegato);

	public void generaNumeroAttoDI(long idProcedimento, long idAtto, long idMittente, long idDestinatario1,
			long idDestinatario2, long idDestinatario3, String idCodiceUtente, long idUfficio,
			long categoriaAtto, String nomeMetodo);

	public void generaNumeroAttoDIP(long idProcedimento, long idAtto, long idMittente,
			long idDestinatarioProposta, long idDestinatario2, long idDestinatario3, String idCodiceUtente,
			long idUfficio, long categoriaAtto, String nomeMetodo);

	public Long generaNumeroAttoDS(long idUfficio, long idProcedimento, TAtto atto, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, long idMagistrato, String giudiceDelegato);

	public Long generaNumeroAttoDRTS(long idUfficio, long idProcedimento, TAtto atto, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String nomeMetodo);

	public void generaNumeroAttoDAP(TAtto atto, String idMisureCaricate, long idProcedimento, long vidAtto,
			long vidUffOwn, TRelAttoMisPers relAttoMisPers, long idSoggInt, String cauzione,
			String articolo67, String vcodUtente, String idPrescrizioni, BigDecimal importoCauzione,
			String articolo9c1);

	public void generaNumeroAttoDRAP(TAtto atto, String idMisureCaricate, long vidProc, long vidAtto,
			long vidUffOwn, TRelAttoMisPers relAttoMisPers, long idSoggInt, String cauzione,
			String articolo67, String vcodUtente, String idPrescrizioni, BigDecimal importoCauzione,
			String articolo9c1);

	public Long generaNumeroAttoDCRS(long idUfficio, long idProcedimento, TAtto atto, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String tipoConvalidaRevoca, String nomeMetodo);

	public Long generaNumeroAttoDPES(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip,
			long codUdienzaInFaseDiRim, long statoUdienzaPrec, String tipoConvalidaRevoca, String nomeMetodo);

    public Long generaNumeroAttoDPS(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto,
    		long categoriaAtto, long idStatoAtto, String nomeMetodo);

    public Long generaNumeroAttoDST(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto,
    		long categoriaAtto, long idStatoAtto, String nomeMetodo);

    public Long generaNumeroAttoDSST(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto,
    		long categoriaAtto, long idStatoAtto, String nomeMetodo);

    public Long generaNumeroAttoDPST(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto,
    		long categoriaAtto, long idStatoAtto, String nomeMetodo);

    public Long generaNumeroAttoSRC(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
    		TAtto attoForm, String codUtente);

    public Long generaNumeroAttoSOD(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
    		TAtto attoForm, String codUtente);

    public Long generaNumeroAttoDO(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcedimento,
    		TAtto atto, String codUtente, int idEsito);
	// ***** FINE INTERVENTO MEV_006 *****//

}