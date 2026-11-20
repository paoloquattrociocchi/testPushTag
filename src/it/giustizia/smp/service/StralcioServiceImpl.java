/**
 * 
 */
package it.giustizia.smp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dao.StralcioDao;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.form.BeneStralcioForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.SoggettoTitolareForm;
import it.giustizia.smp.form.StralcioForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

/**
 * @author massimo.demattia
 *
 */
@Transactional(rollbackFor = Exception.class)
public class StralcioServiceImpl implements StralcioService {

	@Autowired
	private StralcioDao stralcioDao;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private IDocumentaleService	documentaleService;
	@Autowired
	private ProcedimentoService	procedimentoService;
	@Autowired
	private ProcedimentoDao	procedimentoDao;
	
	
	public List<SoggettoStralcioForm> ricercaStralcio(long idProcedimento, long idUffOwner, StralcioForm stralcioForm, String flagValido) {

		String idsMisure = null;
		String idsSoggInt = null;
		String  idsSoggTit = null;
		Integer idTipoBene = null;
		Integer idCatBene = null;
		Integer idSottoCatBene = null;
		List<RicercaStralcioDto> queryResults = null;
		MisuraForm misuraForm = null;
		RicercaStralcioDto dto = null;
		BeneStralcioForm beneStralcioForm = null;
		SoggettoStralcioForm soggettoStralcioForm = null;
		SoggettoTitolareForm soggettoTitolareForm = null;
		List<BeneStralcioForm> listaBeneStralcioForm = null;
		
		if(Utility.isComboFormSelected(stralcioForm.getTipoMisure())){
			idsMisure = stralcioForm.getTipoMisure().getId();
		}
		if(Utility.isComboFormSelected(stralcioForm.getSoggettiDiretti())){
			idsSoggInt = stralcioForm.getSoggettiDiretti().getId();
		}
		if(Utility.isComboFormSelected(stralcioForm.getSoggettiTitolari())){
			idsSoggTit = stralcioForm.getSoggettiTitolari().getId();
		}
		if(Utility.isComboFormSelected(stralcioForm.getTipologiaBene())){
			idTipoBene = Integer.valueOf(stralcioForm.getTipologiaBene().getId());
		}
		if(Utility.isComboFormSelected(stralcioForm.getCategoriaBene())){
			idCatBene = Integer.valueOf(stralcioForm.getCategoriaBene().getId());
		}
		if(Utility.isComboFormSelected(stralcioForm.getSottoCategoriaBene())){
			idSottoCatBene = Integer.valueOf(stralcioForm.getSottoCategoriaBene().getId());
		}
		
		queryResults = stralcioDao.ricercaStralcio(idProcedimento, idUffOwner, idsMisure, idsSoggInt, idsSoggTit, idTipoBene, idCatBene, idSottoCatBene, flagValido);
		
		List<SoggettoStralcioForm> risultati = new ArrayList<SoggettoStralcioForm>();
		if (queryResults == null || queryResults.isEmpty()) {
			return risultati;
		}
		
		
		for (int i = 0; i < queryResults.size(); i++) {
			
			dto = (RicercaStralcioDto) queryResults.get(i);
			soggettoStralcioForm = new SoggettoStralcioForm();
			misuraForm = new MisuraForm();
			BigDecimal prevSoggInt = null;
			BigDecimal prevIdBene = null;
			BigDecimal prevIdSoggInt = null;
			boolean isFirst= true;
			mapperBean.map(dto, soggettoStralcioForm);
			
			listaBeneStralcioForm = new ArrayList<BeneStralcioForm>();
			soggettoStralcioForm.setListaBeneStralcioForm(listaBeneStralcioForm);
			
			prevSoggInt = dto.getCODI_K_ID_SOGG_INT();
			
			
			//inizio gestione NO_MIS_ID
			if(Constants.TipoMisura.NO_MIS_ID.equalsIgnoreCase(dto.getTIPO_MISURA())){
				isFirst = false;
				mapperBean.map(dto, misuraForm);
				soggettoStralcioForm.setFlgValido(String.valueOf(dto.getFLG_VALIDO()));
				soggettoStralcioForm.setMisura(misuraForm);
				//continue;
			}else{
			
			
				//ciclo sui beni
				for (; i < queryResults.size(); i++) {
					if(!isFirst){
						dto = (RicercaStralcioDto)queryResults.get(i);
					}
					
					
					
					if(prevSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())){
					
						//inizio gestione MIS_PERS
						if(Constants.TipoMisura.PERSONALE_ID.equalsIgnoreCase(dto.getTIPO_MISURA())){
							isFirst = false;
							mapperBean.map(dto, misuraForm);
							soggettoStralcioForm.setMisura(misuraForm);
							beneStralcioForm = new BeneStralcioForm();
		//					mapping bene
							mapperBean.map(dto, beneStralcioForm);
							listaBeneStralcioForm.add(beneStralcioForm);
							continue;
						}
						else{
							isFirst = true;
						}
						//fine gestione MIS_PERS
						
						beneStralcioForm = new BeneStralcioForm();
						mapperBean.map(dto, beneStralcioForm);
						listaBeneStralcioForm.add(beneStralcioForm);
						
						List<SoggettoTitolareForm> listSoggettoTitolareForm = new ArrayList<SoggettoTitolareForm>();
						beneStralcioForm.setListaSoggettoTitolareForm(listSoggettoTitolareForm);
						prevIdBene = dto.getCODI_K_ID_BENE();
						prevIdSoggInt = dto.getCODI_K_ID_SOGG_INT();
						
						//ciclo sui soggetti titolare
						for (; i < queryResults.size(); i++) {
							if(!isFirst){
								dto = (RicercaStralcioDto)queryResults.get(i);
							}
							isFirst = false;
							if(prevIdBene.equals(dto.getCODI_K_ID_BENE()) && prevIdSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())){
								soggettoTitolareForm = new SoggettoTitolareForm();
								mapperBean.map(dto, soggettoTitolareForm);
								listSoggettoTitolareForm.add(soggettoTitolareForm);
							}else{
								isFirst = true;
								i--;
								break;
							}
						}
					}else{
						isFirst = true;
						i--;
						break;
					}
				}
			}
			risultati.add(soggettoStralcioForm);
		}
		return risultati;
	}


	public void setStralcioDao(StralcioDao stralcioDao) {
		this.stralcioDao = stralcioDao;
	}
	
	public void impostaStralcioMisPatr(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, long idBene, String idSoggTit, int tipoBene, int catBene, int sottoCatBene, String codiceUtente){
		
			stralcioDao.impostaStralcioMisPatr(idProcedimento, idUffOwner, checked, idSoggInt, idBene, idSoggTit, tipoBene, catBene, sottoCatBene,codiceUtente);
	}
	
	public void impostaStralcioMisPers(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, String codiceUtente){
		
			stralcioDao.impostaStralcioMisPers(idProcedimento, idUffOwner, checked, idSoggInt, codiceUtente);
	}
	
	public void impostaStralcioSoggetto(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, final String idsSoggTit, final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, String codiceUtente){
		
			stralcioDao.impostaStralcioSoggetto(idProcedimento, idUffOwner, checked, idSoggInt, idsSoggTit, idsMisure, tipoBene, catBene, sottoCatBene, codiceUtente);
	}
	
	public KeyMessageDto controlloStralcio(long idProcedimento){
		
		return stralcioDao.controlloStralcio(idProcedimento);
	}
	
	public KeyMessageDto esecuzioneStralcio(String funzione,long idAtto,long idProcedimento, String codiceUtente, Date dataStralcio, long magistrato, StralcioForm form){
		
		TAtto atto = new TAtto();
		mapperBean.map(form, atto);
		
		if (form.getAllegato() != null && form.getAllegato().getFileData()!= null && form.getAllegato().getFileData().getSize() > 0) {
			form.getAllegato().setFileName(form.getAllegato().getFileData().getOriginalFilename());
			form.getAllegato().setContentType(form.getAllegato().getFileData().getContentType());
		}
		
		Utility.popolaDocumento(form, atto);
		
		return stralcioDao.esecuzioneStralcio(funzione,idAtto,idProcedimento, codiceUtente, dataStralcio, magistrato, atto);
	}
	
	public boolean documentaleStralcio(long idProcedimentoOrigine, long idProcedimentoDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti) {
		
		String codiceFascicoloOrigine = procedimentoDao.ricercaFascicoloPDoc(idProcedimentoOrigine);
		
		//creazione fascicolo procedimento destinazione
		TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimentoDestinazione);
		long numeProc = procedimento.getNumeProc();
		int tipoProcedimento = procedimento.getTTpTipoProc().getCodiKIdTipoProc();
		String descUfficioGiudiziario = procedimento.getTAnUfficioByCodiFkIdUffOwner().getDescUffGiudiz();
		
		String codiceFascicoloDestinazione = Utility.creaCodiceFascicolo(Constants.PREFISSO_FASCICOLO, tipoProcedimento, String.valueOf(numeProc), descUfficioGiudiziario);
		procedimentoService.inserisciECreaFascicoloPDoc(idProcedimentoDestinazione, codiceFascicoloDestinazione);
		
		return documentaleService.stralcio(codiceFascicoloOrigine, codiceFascicoloDestinazione, documenti, descUfficioGiudiziario);
	}
	
	public KeyMessageDto copiaSpostaAttoGenerico(final long idProcedimento, final long idProcedimentoDestinatario, final long idAtto, final String operazione, final String codiceUtente, final long idUffOwn, final String nomeMetodo){
		
		return stralcioDao.copiaSpostaAttoGenerico(idProcedimento, idProcedimentoDestinatario, idAtto, operazione, codiceUtente, idUffOwn, nomeMetodo);
	}
	
}
