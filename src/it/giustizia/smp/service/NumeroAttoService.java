package it.giustizia.smp.service;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.SoggettoForm;

/**
 * NumeroAttoService: Interfaccia Numero Atto Service
 * 
 * @author Gioggi
 */
public interface NumeroAttoService {

	/*
	 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
	 * Numero MEV : 006
	 * Autore : Gioggi
	 * Data : 16 apr 2020
	 * Branch : MEV_006
	 */
	public Long generaNumeroAtto(long idUffOwn, long idProcUdi, long idProcGen, long idProcProp,
			long idProcedimento, AttoForm attoForm, String codUtente, int tipoUfficio, long idMagistrato,
			String giudiceDelegato);

	public void generaNumeroAttoDI(long idProcedimento, long idAtto, long idUfficio, long idDestinatario1,
			long idDestinatario2, long idDestinatario3, String codiceUtente, long idUfficio2,
			long categoriaAtto, String printMethodName);

	public void generaNumeroAttoDIP(long idProcedimento, long idAtto, long idUfficio,
			long idDestinatarioProposta, long idDestinatario2, long idDestinatario3, String codiceUtente,
			long idUfficio2, long categoriaAtto, String printMethodName);

	public Long generaNumeroAttoDS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, long idMagistrato, String giudiceDelegato);

	public Long generaNumeroAttoDRTS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String nomeMetodo);

	public void generaNumeroAttoDAP(long idProcedimento, long vidUffOwn, long idSoggInt, String vcodUtente,
			SoggettoForm soggettoForm, AttoForm attoForm);

	public void generaNumeroAttoDRAP(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente,
			SoggettoForm soggettoForm, AttoForm attoForm);

	public Long generaNumeroAttoDCRS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String nomeMetodo);

	public Long generaNumeroAttoDPES(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip,	long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo);

	public Long generaNumeroAttoDPS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);

	public Long generaNumeroAttoDST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);

	public Long generaNumeroAttoDSST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);

	public Long generaNumeroAttoDPST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);

	public Long generaNumeroAttoSRC(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
			AttoForm attoForm, String codUtente);

	public Long generaNumeroAttoSOD(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
			AttoForm attoForm, String codUtente);

	public Long generaNumeroAttoDO(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcedimento,
			AttoForm attoForm, String codUtente);
	// ***** FINE INTERVENTO MEV_006 *****//

}