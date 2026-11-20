/**
 * 
 */
package it.giustizia.smp.service;

import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dao.RiunioneDao;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.RiunioneForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo.demattia
 *
 */
public class RiunioneServiceImpl implements RiunioneService {

	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private RiunioneDao riunioneDao;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private IDocumentaleService	documentaleService;
	@Autowired
	private ProcedimentoService	procedimentoService;
	@Autowired
	private ProcedimentoDao	procedimentoDao;
	
	public KeyMessageDto controlloRiunione(final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo){
		
		return riunioneDao.controlloRiunione(idProcedimentoRiunente, idProcedimentoRiunito, codiceUtente, idUfficio, nomeMetodo);
	}
	
	public KeyMessageDto esecuzioneRiunione(String funzione,long idAtto, final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo, final long magistrato, final Date dataRiunione, RiunioneForm form){
		
		TAtto atto = new TAtto();
		mapperBean.map(form, atto);
	
		if (form.getAllegato() != null && form.getAllegato().getFileData()!= null && form.getAllegato().getFileData().getSize() > 0) {
				form.getAllegato().setFileName(form.getAllegato().getFileData().getOriginalFilename());
				form.getAllegato().setContentType(form.getAllegato().getFileData().getContentType());
		}
		
		Utility.popolaDocumento(form, atto);
		
		return riunioneDao.esecuzioneRiunione(funzione, idAtto, idProcedimentoRiunente, idProcedimentoRiunito, codiceUtente, idUfficio, nomeMetodo,  magistrato, dataRiunione, atto);
	}
	
	public boolean documentaleRiunione(long idProcedimentoOrigine, long idProcedimentoDestinazione, ArrayList<BigInteger> documenti) {
		
		String codiceFascicoloOrigine = procedimentoDao.ricercaFascicoloPDoc(idProcedimentoOrigine);
		
		//creazione fascicolo procedimento destinazione
		TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimentoDestinazione);
		long numeProc = procedimento.getNumeProc();
		int tipoProcedimento = procedimento.getTTpTipoProc().getCodiKIdTipoProc();
		String descUfficioGiudiziario = procedimento.getTAnUfficioByCodiFkIdUffOwner().getDescUffGiudiz();
		
		String codiceFascicoloDestinazione = Utility.creaCodiceFascicolo(Constants.PREFISSO_FASCICOLO, tipoProcedimento,  String.valueOf(numeProc), descUfficioGiudiziario);
		procedimentoService.inserisciECreaFascicoloPDoc(idProcedimentoDestinazione, codiceFascicoloDestinazione);
		
		return documentaleService.riunione(codiceFascicoloOrigine, codiceFascicoloDestinazione, documenti, descUfficioGiudiziario);
	}

	public RiunioneDao getRiunioneDao() {
		return riunioneDao;
	}

	public void setRiunioneDao(RiunioneDao riunioneDao) {
		this.riunioneDao = riunioneDao;
	}
	
}
