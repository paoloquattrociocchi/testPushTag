package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;

public interface AnnullamentoDuplicatoService {
	
	public KeyMessageDto annullamentoSoggettoDuplicato(long idProcedimento, long idSoggettoDuplicato, long idSoggettoValido, int idUfficio, String utente, String nomeMetodo);
	
}
