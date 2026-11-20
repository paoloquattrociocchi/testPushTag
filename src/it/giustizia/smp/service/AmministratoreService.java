package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.dto.pg_atto.RedazioneSchedaDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.CertificatoPenaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.DifensoreForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TCertificatoPenale;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefIstGen;
import it.giustizia.smp.model.TDefIstGenId;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

@SuppressWarnings("rawtypes")
public interface AmministratoreService {
	
	public void copiaAmministratori(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo);
	public void copiaAmministratoriIIGrado(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo);
	public List<TRelProcAttoAmm> listaAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia);
	public List<TRelProcBeneAttoAmm> listaAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, boolean escludiAtto);
	public void sistemaDate(int idUfficio, long idProcedimento, long idAtto, Date dataDeposito, int grado);
	public boolean visualizzaMessaggioAmministratoriPresenti(int idUfficio, long idProcedimento, long idAtto, int grado);
	void sistemaDateIIGrado(int idUfficio, long idProcedimento, long idAttoDecisorio, Date dataDeposito, int grado);
}