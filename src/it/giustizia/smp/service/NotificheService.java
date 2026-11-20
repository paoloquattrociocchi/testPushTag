package it.giustizia.smp.service;

import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificaDettaglioForm;
import it.giustizia.smp.form.NotificheEComunicazioniForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TNotifAmm;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.utility.notifiche.ServiceResult;

import java.util.ArrayList;
import java.util.List;


public interface NotificheService {
	
	public String isAttoInviabile(long idAtto, long  idProcedimento);

	public List<DestinatarioDto> caricaListaDestinatari(long idProc,long idAtto, int idUfficio);

	public List<ComboForm> getTipoUfficiPerDesinatario();

	public DestinatarioDto getDetinatarioDTObyIdUfficio(int idUfficio);

	public ServiceResult inviaNotifiche(NotificaAttoForm attoForm,ProcedimentoForm procedimento, int idUfficioMitt,String nomeECongomeUtente, String descUtente);

	public ArrayList<NotificaDettaglioForm> caricaListaNotifiche(Long idAtto,int idUfficio);

	public List<VNotifiche> getListaNotifichePerData(int idUfficioMitt,  int idTipoUff, NotificheEComunicazioniForm notificheEComunicazioniForm);
	
	public List<ComboForm> getListaTipoRuoloDesinatario();

	public TNotifAmm getTNotifAmmById(Long idNotifAmm);
	
	public Boolean controllaPresenzaAmministratori(List<DestinatarioDto> listaDestinatari);
}
