package it.giustizia.smp.service;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;

import java.util.List;
import java.util.Map;

/**
 * UdienzaService
 * 
 * @author Almaviva S.p.A.
 *         Interfaccia Udienza Service
 */
public interface UdienzaService {

	public void aggiornaDatiConfUdienza(long idProcedimento, long idUfficioUtente, String idUtente, UdienzaForm udienzaForm, String tipologia, long idProcUdi, long idAtto, long tipoUfficio);

    public UdienzaForm dettaglioUdienzaSelezionata(long idProcedimento, long idUfficioUtente, long udienzaSelected);

    public void gestioneMagistrati(long udienzaSelected, long idUfficioUtente, String idUtente, MagistratoForm form, String tipologia);

    public List<SoggettoForm> listaDifensoriUdienza(long idUfficioUtente, long udienzaSelected);

    public List<MagistratoForm> listaMagistratiCollegioUdienza(long idProcedimento, long idUfficioUtente, long udienzaSelected);

    public List<MagistratoForm> listaMagistratiPmUdienza(long idUfficioUtente, long udienzaSelected);

    public List<SoggettoForm> listaSoggettiConvocatiUdienza(long idProcedimento, long idUfficioUtente, long udienzaSelected);

    public List<UdienzaForm> listaUdienzePerProcedimento(long idProcedimento, long idUfficioUtente);
    
    public List<UdienzaForm> listaUdienzePerProcedimentoEStatoUdienza(long idProcedimento, long idUfficioUtente, String idStatoUdienza);
    
    public void copiaDatiProcPerUdienza(long idUdienza, long idUffOwn, String codUtente, long idProcedimento) ;
    
    public long getUdienzaPrecedente(long idUdienza, int idUffOwn);

    public UdienzaForm caricaUdienzaDecretoSeqConv(long codAtto, long idUfficioUtente);

    public String esisteAttoInLavorazioneConDatiUdienza(long idProcedimento, long idUfficioUtente, long codAtto);
    
    public List<MagistratoForm> ricercaMagistratiUff(MagistratoForm magistrato, long idUfficioUtente, String dataUdienza);

    public List<ComboForm> elencoPmProcUdi(String dataUdienzaSelected, long idProcUdi);

    public List<UdienzaForm> getUdienzaByCodAttoEStatoUdi(long codAtto, String valueOf, long idUffOwn);
    
    public Map<String, Long> inserisciVerbaleUdienza(long idUfficio, String descUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, long idUdienza, final String nomeMetodo);
    
    public UdienzaForm getUdienzaByIdAtto(long codAtto, long idUfficioUtente);
    
    public UdienzaForm getUdienzaRicorso(long idAtto,long idUfficio,String descUtente);
}
