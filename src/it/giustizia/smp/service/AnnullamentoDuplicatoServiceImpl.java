package it.giustizia.smp.service;

import it.giustizia.smp.dao.AnnullamentoDuplicatiDao;
import it.giustizia.smp.dto.KeyMessageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * BeneServiceImpl
 * 
 * @author Almaviva S.p.A. classe del Bene
 */
@Transactional(rollbackFor = Exception.class)
public class AnnullamentoDuplicatoServiceImpl implements AnnullamentoDuplicatoService {
	
	@Autowired
	private AnnullamentoDuplicatiDao annullamentoDuplicatiDao;

	public KeyMessageDto annullamentoSoggettoDuplicato(long idProcedimento,
			long idSoggettoDuplicato, long idSoggettoValido, int idUfficio,
			String utente, String nomeMetodo) {
		
		return annullamentoDuplicatiDao.annullamentoSoggettoDuplicato(idProcedimento, idSoggettoDuplicato, idSoggettoValido, idUfficio, utente, nomeMetodo);
	}

	public AnnullamentoDuplicatiDao getAnnullamentoDuplicatiDao() {
		return annullamentoDuplicatiDao;
	}

	public void setAnnullamentoDuplicatiDao(
			AnnullamentoDuplicatiDao annullamentoDuplicatiDao) {
		this.annullamentoDuplicatiDao = annullamentoDuplicatiDao;
	}
	
	
}
