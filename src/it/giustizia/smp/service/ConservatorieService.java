package it.giustizia.smp.service;

import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.ComuneProvinciaDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.dto.pg_conservatorie.TipoDomicilioDto;
import it.giustizia.smp.dto.pg_conservatorie.TitoloDirittoDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneBeniForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneInsertForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneListaForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneModificaForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneSoggettiForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.model.TAnUfficio;
import java.util.Date;
import java.util.List;

public interface ConservatorieService
{
	public NotaDettaglioForm dettaglioNota(final int idUffOwn, final Long idNota);

	public List<ComboForm> getCategorieAtti( String ricIns, Integer tipoUff );

	public Long getIdProcedimento(Integer idUffOwn, Integer annoProc, Integer numeProc, String tipoAtto, String catAtto );

	public List<ComunicazioneListaForm> listaComunicazioniConservatorie(
			String tipo, String categorieAtti, Short anno, Long dalNumero,
			Long alNumero, String conservatoria, String cognome, String nome,
			String denominazione, Integer idUfficioUtente);

	public List<ComboForm> getTipoComunic();
	
	public List<ComboForm> getTipoComunicAnn();

	public List<ComboForm> getTipoComunicTrasc();
	
	public List<Short> cercaProgressivoNota(Integer idUffOwn, Integer annoProc, Integer numeProc );
	
	public Long inserisciNota( ComunicazioneInsertForm form,int idUffOwn);

	public Long inserisciNotaAnnot(ComunicazioneInsertForm form,Long idNota,int idUffOwn);

	public Long inserisciNotaTrascr (ComunicazioneInsertForm form,Long idNota,int idUffOwn);

	public String associaNotaBene(Long idNota,Integer idUffOwn, Long idBene, String descUtente);

	public TAnUfficio getDettaglioUfficio(Integer idUffOwn);

	public Long modificaNota( ComunicazioneModificaForm form,int idUffOwn);

	public Long modificaNotaAnnot(ComunicazioneModificaForm form,Long idNota,int idUffOwn);

	public Long modificaNotaTrascr (ComunicazioneModificaForm form,Long idNota,int idUffOwn);

	public String eliminaNota(Long idNota, Integer idUffOwn);

	public Long generaAnnotazione(NotaDettaglioForm form, int idUffOwn, String descUtente);

	public Long riproposizione(NotaDettaglioForm form,int idUffOwn, String descUtente);

	public List<BeneAssociatoDescDto> listaBeniAssociati(final Long idNota,final int idUffOwn);
	
	public List<String> cercaOsservazioniNota(Integer idUffOwn, Long idNota, Character tipoInfo );

	public Long inserisciQuadroD( 
		Integer idUfficioUtente, String descUtente, Long idNota, Character tipoInf, String quadroD, Character stato );

	public String cancellaQuadroD( Integer idUfficioUtente, String descUtente, Long idNota, Character tipoInf, Character stato );
	
	public ComunicazioneBeniForm dettaglioNotaBene (ComunicazioneBeniForm form, Long idNota,Integer idUffOwn);
	
	public List<TitoloDirittoDto> listaTitoliDiritto (ComunicazioneBeniForm form, Long idNota,Integer idUffOwn);

	public ComunicazioneBeniForm titoloDiritto(ComunicazioneBeniForm form, Long idNota, Integer idUffOwn);
	
    public Integer modificaTitoloDiritto(ComunicazioneBeniForm form, Long idNota,  Integer idUffOwn, String utente);
	
	public String cancellaNotaBene(Long idNota,Integer idUffOwn, Long progrNotaBene, String descUtente);

	public String updateNotaBene(ComunicazioneBeniForm form, Long idNota,Integer idUffOwn, String descUtente);

	public void dettaglioSoggetto( ComunicazioneSoggettiForm soggettiForm , Long idNota, Integer idUffOwn, Long progressivo );
	
	public SoggettoInteressatoDto dettaglioPersonaFisica( Long idSoggetto, Integer idUff, Integer versione );

    public SoggettoInteressatoDto dettaglioPersonaGiurid( Long idSoggetto, Integer idUff, Integer versione );
    
    public TipoDomicilioDto getTipoDomicilio(Integer idUff, Long numeroProcedimento, Long idSoggetto);
    
    public String getDescrizioneQuadroD(Integer idUff, Long idNota);

    public boolean modificaSoggettoGiurid( Long idNota, Integer idUffOwn, String descUtente,
        String denom, String comune, String provincia, String pIva, String codComune, Long progressivo );

    public boolean controllaRegimeSoggettoFisico( Long idNota, Integer idUffOwn, Character sesso, Integer progrSoggCon );

    public boolean modificaSoggettoFisico( Long idNota, Integer idUffOwn, String descUtente, String cognome,
        String nome, Character sesso, Date dataNascita, String statoNascita, String comuneNascita, String provNascita, 
        String codFiscale, Character regime, Integer progrSoggCon, Long progressivo );
    
    public List<ComuneProvinciaDto> getComuniByConservatoria(Long conservatoriaId);

	public boolean associaSoggetto( SoggettoInteressatoDto dettaglioPers, Integer idUffOwn, String utente, Long idNota );

    public List<SoggettoInteressatoDto> listaSoggettiAssociabili( Long idNota, Integer idUfficioUtente, NotaDettaglioForm dettaglio );
    
    public List<SoggettoInteressatoDto> listaSoggettiUnitaNegoziali( Long idNota, Integer idUffOwn);
    
    public List<BeneAssociatoDescDto> listaUnitaNegoziali( Long idNota, Integer idUffOwn);

    public String cancellaNotaSoggetto( 
        Long idNota, Integer idUffOwn, Long progrNotaSoggetto, String descUtente, Character tipoUtente, Character stato );
    
    public String inserisciTitoliDiritto( Long numeroComunicazione, Long beneSitMp, Long progrSogg, Integer tipoInteresse,
        Long numeratoreQuotaIntero, Long numeratoreQuotaDecimale, Long denominatoreQuota, Integer idUffOwner,
        String descUtente );
    
    public Double controllaCapienza( Long numeroComunicazione,Long beneSitMp,Long progrSogg,
        Integer tipoInteresse,Integer idUffOwner, String descUtente );
    
    public Integer controllaDuplicato( Long numeroComunicazione,Long beneSitMp,Long progrSogg,
    		Integer tipoInteresse,Integer idUffOwner, String descUtente );
    
    public String cancellaTitoliDiritto( Long numeroComunicazione, Long beneSitMp, Long progrSogg,
        Integer tipoInteresse,Integer idUffOwner, String descUtente );

    public String calcolaUnitaNegoziali( Long idNota, int idUff, String descUtente  );
    
    public boolean esisteSoggettoFisico( Long idNota, Integer idUffOwn,  Integer progrSoggCon );

	public String nuovaComunicazione(int idUffOwn, Integer tipoAtto,	Integer catAtto, Short repAnno, Long repNumero, long idProced, ComunicazioneInsertForm form, Short idCons);

	public List<ComunicazioneListaForm> listaComunicazioniPredisp(Integer ufficio, String tipoNota, Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero, String attoSpecie, String descSpecie);

	public String rigeneraComunicazione(int idUffOwn, Integer tipoAtto,	Integer catAtto, Short repAnno, Long repNumero, long idProced, ComunicazioneInsertForm form, String[] notaSel);
	
	public String aggiornaStatoNota( Integer idUfficioUtente, String descUtente, Long idNota, Character flgstato );
	
	public List<SoggettoInteressatoDescDto> setListaSoggettiDesc(	List<SoggettoInteressatoDto> lista) ;

    public ComboForm getComuneByUfficio( Integer idUffOwn );
    
    public List<ComboForm> listaPFinRegime(Long idNota, Integer idUffOwn, Long progrSogg) ;

    public List<ComboForm> getCategorieAttiNonSequestri( Integer tipoUfficio );

    public Character getFlagDecisorio( Integer idTipoAtto, Integer idCatAtto );
}
