package it.giustizia.smp.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.NumeroAttoDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

/**
 * NumeroAttoServiceImpl: classe del Numero Atto
 * 
 * @author Gioggi
 */

@Transactional(rollbackFor = Exception.class)
public class NumeroAttoServiceImpl implements NumeroAttoService {

	private NumeroAttoDao numeroAttoDao;

	@Autowired
	private Mapper mapperBean;

	// private Logger logger = Logger.getLogger(getClass());

	/*
	 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
	 * Numero MEV : 006
	 * Autore : Gioggi
	 * Data : 16 apr 2020
	 * Branch : MEV_006
	 */
	private TAtto popolaAttoEDocumento(AttoForm attoForm) {
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		return atto;
	}

	public Long generaNumeroAtto(long idUffOwn, long idProcUdi, long idProcGen, long idProcProp,
			long idProcedimento, AttoForm attoForm, String codUtente, int tipoUfficio, long idMagistrato,
			String giudiceDelegato) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return numeroAttoDao.generaNumeroAtto(idUffOwn, idProcUdi, idProcGen, idProcProp, idProcedimento,
				atto, codUtente, tipoUfficio, idMagistrato, giudiceDelegato);
	}

	public void generaNumeroAttoDI(long idProcedimento, long idAtto, long idMittente, long idDestinatario1,
			long idDestinatario2, long idDestinatario3, String idCodiceUtente, long idUfficio,
			long categoriaAtto, String nomeMetodo) {

		numeroAttoDao.generaNumeroAttoDI(idProcedimento, idAtto, idMittente, idDestinatario1, idDestinatario2,
				idDestinatario3, idCodiceUtente, idUfficio, categoriaAtto, nomeMetodo);
	}

	public void generaNumeroAttoDIP(long idProcedimento, long idAtto, long idMittente,
			long idDestinatarioProposta, long idDestinatario2, long idDestinatario3, String idCodiceUtente,
			long idUfficio, long categoriaAtto, String nomeMetodo) {

		numeroAttoDao.generaNumeroAttoDIP(idProcedimento, idAtto, idMittente, idDestinatarioProposta,
				idDestinatario2, idDestinatario3, idCodiceUtente, idUfficio, categoriaAtto, nomeMetodo);
	}

	public Long generaNumeroAttoDS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, long idMagistrato, String giudiceDelegato) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return numeroAttoDao.generaNumeroAttoDS(idUfficio, idProcedimento, atto, codUtente, tipoAtto,
				categoriaAtto, idStatoAtto, idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss,
				codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec, idMagistrato,
				giudiceDelegato);
	}

	public Long generaNumeroAttoDRTS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return numeroAttoDao.generaNumeroAttoDRTS(idUfficio, idProcedimento, atto, codUtente, tipoAtto,
				categoriaAtto, idStatoAtto, idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss,
				codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec, nomeMetodo);
	}

	public void setNumeroAttoDao(NumeroAttoDao numeroAttoDao) {

		this.numeroAttoDao = numeroAttoDao;
	}

	public void generaNumeroAttoDAP(long idProcedimento, long vidUffOwn, long idSoggInt, String vcodUtente,
			SoggettoForm soggettoForm, AttoForm attoForm) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String articolo9c1 = "N";
		String idMisureCaricate = null;
		TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
		mapperBean.map(soggettoForm, relAttoMisPers);
		if (soggettoForm.getMisura() != null) {
			if (soggettoForm.getIdAtto() != null)
				vidAtto = soggettoForm.getIdAtto();
			if (soggettoForm.getMisura().getArt9c1() != null)
				articolo9c1 = soggettoForm.getMisura().getArt9c1();
			if (soggettoForm.getMisura().getIdMisureCaricate() != null)
				idMisureCaricate = soggettoForm.getMisura().getIdMisureCaricate();
			if ((soggettoForm.getMisura().getIdSelezionati() != null)
					&& !soggettoForm.getMisura().getIdSelezionati().isEmpty()) {
				idPrescrizioni = soggettoForm.getMisura().getIdSelezionati().get(0) + "";
				if (soggettoForm.getMisura().getIdSelezionati().size() > 1) {
					for (int i = 1; i < soggettoForm.getMisura().getIdSelezionati().size(); i++) {
						idPrescrizioni += ";" + soggettoForm.getMisura().getIdSelezionati().get(i);
					}
				}
			} else {
				idPrescrizioni = null;
			}
		} else {
			idPrescrizioni = null;
		}
		numeroAttoDao.generaNumeroAttoDAP(atto, idMisureCaricate, idProcedimento, vidAtto, vidUffOwn,
				relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, null,
				articolo9c1);
	}

	public void generaNumeroAttoDRAP(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente,
			SoggettoForm soggettoForm, AttoForm attoForm) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String articolo9c1 = "N";
		String idMisureCaricate = null;
		TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
		mapperBean.map(soggettoForm, relAttoMisPers);
		if (soggettoForm.getMisura() != null) {
			if (soggettoForm.getIdAtto() != null) {
				vidAtto = soggettoForm.getIdAtto();
			}
			if (soggettoForm.getMisura().getArt9c1() != null) {
				articolo9c1 = soggettoForm.getMisura().getArt9c1();
			}
			if (soggettoForm.getMisura().getIdMisureCaricate() != null) {
				idMisureCaricate = soggettoForm.getMisura().getIdMisureCaricate();
			}
			if ((soggettoForm.getMisura().getIdSelezionati() != null)
					&& !soggettoForm.getMisura().getIdSelezionati().isEmpty()) {
				idPrescrizioni = soggettoForm.getMisura().getIdSelezionati().get(0) + "";
				if (soggettoForm.getMisura().getIdSelezionati().size() > 1) {
					for (int i = 1; i < soggettoForm.getMisura().getIdSelezionati().size(); i++) {
						idPrescrizioni += ";" + soggettoForm.getMisura().getIdSelezionati().get(i);
					}
				}
			} else {
				idPrescrizioni = null;
			}
		} else {
			idPrescrizioni = null;
		}
		numeroAttoDao.generaNumeroAttoDRAP(atto, idMisureCaricate, vidProc, vidAtto, vidUffOwn,
				relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, null,
				articolo9c1);
	}

	public Long generaNumeroAttoDCRS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario,
			long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim,
			long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDCRS(idUfficio, idProcedimento, atto, codUtente, tipoAtto,
				categoriaAtto, idStatoAtto, idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss,
				codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec, Constants.CONVALIDA,
				nomeMetodo);
	}

	public Long generaNumeroAttoDPES(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto,long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDPES(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec,
				Constants.CONVALIDA, nomeMetodo);
	}

	public Long generaNumeroAttoDPS(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDPS(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, nomeMetodo);
	}

	public Long generaNumeroAttoDST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDST(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, nomeMetodo);
	}

	public Long generaNumeroAttoDSST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDSST(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, nomeMetodo);
	}

	public Long generaNumeroAttoDPST(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, String nomeMetodo) {
		
		TAtto atto = popolaAttoEDocumento(attoForm);

//		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return numeroAttoDao.generaNumeroAttoDPST(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, nomeMetodo);
	}

	public Long generaNumeroAttoSRC(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
			AttoForm attoForm, String codUtente) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return numeroAttoDao.generaNumeroAttoSRC(idUffOwn, idUff1, idUff2, idUff3, idProcedimento, atto, codUtente);
	}

	public Long generaNumeroAttoSOD(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento,
			AttoForm attoForm, String codUtente) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return numeroAttoDao.generaNumeroAttoSOD(idUffOwn, idUff1, idUff2, idUff3, idProcedimento, atto, codUtente);
	}

	public Long generaNumeroAttoDO(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcedimento,
			AttoForm attoForm, String codUtente) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		int idEsito = Integer.parseInt(attoForm.getEsitoOpposizione().getId());
		return numeroAttoDao.generaNumeroAttoDO(idUffOwn, idProcInUdienza, idProcGenerale, idProcedimento,
				atto, codUtente, idEsito);
	}
	// ***** FINE INTERVENTO MEV_006 *****//

}