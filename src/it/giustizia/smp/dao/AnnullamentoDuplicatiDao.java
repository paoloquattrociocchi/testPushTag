package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;

public interface AnnullamentoDuplicatiDao {

    public KeyMessageDto annullamentoSoggettoDuplicato(long idProcedimento, long idSoggettoDuplicato, long idSoggettoValido, int idUfficio, String utente, String nomeMetodo);

}
