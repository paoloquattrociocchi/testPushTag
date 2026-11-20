package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_conservatorie.BeneAssociabileDto;
import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDto;
import it.giustizia.smp.dto.pg_conservatorie.BeneDaAssociareDto;
import it.giustizia.smp.dto.pg_conservatorie.ComuneConservatoriaDto;
import it.giustizia.smp.dto.pg_conservatorie.ComuneProvinciaDto;
import it.giustizia.smp.dto.pg_conservatorie.ComunicazioneDto;
import it.giustizia.smp.dto.pg_conservatorie.ComunicazionePredispDto;
import it.giustizia.smp.dto.pg_conservatorie.DettaglioComunicazioneDto;
import it.giustizia.smp.dto.pg_conservatorie.NotaAnnotazioneDto;
import it.giustizia.smp.dto.pg_conservatorie.NotaPersFisRegimeDto;
import it.giustizia.smp.dto.pg_conservatorie.NotaTrascrizioneDto;
import it.giustizia.smp.dto.pg_conservatorie.SituazioneProprietaDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoBeneDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.dto.pg_conservatorie.TipoCategAttiDto;
import it.giustizia.smp.dto.pg_conservatorie.TipoDomicilioDto;
import it.giustizia.smp.dto.pg_conservatorie.TitoloDirittoDto;
import it.giustizia.smp.model.TNota;
import it.giustizia.smp.model.TNotaAnnot;
import it.giustizia.smp.model.TNotaBene;
import it.giustizia.smp.model.TNotaTrascr;
import it.giustizia.smp.model.TTpTipoComunic;
import java.util.Date;
import java.util.List;

public interface ConservatorieDao
{
    public List<TipoCategAttiDto> getCategorieAttiInserimento(Integer tipoUff);

    public List<TipoCategAttiDto> getCategorieAttiRicerca(Integer tipoUff);

    public List<TipoCategAttiDto> getCategorieAttiNonSequestri( Integer tipoUff );

    public Long getIdProcedimento( Integer idUffOwn, Integer annoProc, Integer numeProc, String tipoAtto, String catAtto );

    public List<ComunicazioneDto> listaComunicazioniConservatorie(
        String tipo, String categorieAtti, Short anno, Long dalNumero,
        Long alNumero, String conservatoria, String cognome, String nome,
        String denominazione, Integer idUfficioUtente );

    public DettaglioComunicazioneDto dettaglioNota( final int idUffOwn, final Long idNota );

    public NotaAnnotazioneDto getNotaAnnotazione( final int idUffOwn, final Long idNota );

    public NotaTrascrizioneDto getNotaTrascrizione( final int idUffOwn, final Long idNota );

    public List<TTpTipoComunic> getTipoComunic();

    public List<TTpTipoComunic> getTipoComunicAnn();

    public List<TTpTipoComunic> getTipoComunicTrasc();

    public List<Short> cercaProgressivoNota( final Integer idUffOwn, final Integer annoProc, final Integer numeProc );

    public Short cercaMaxProgressivoNota( final Integer idUffOwn, final Short annoProc, final Long numeProc );

    public Long getIdNota( final Integer idUffOwn );

    public Long inserisciNota( TNota nota, int idUffOwn );

    public Long inserisciNotaAnnot( TNotaAnnot notaAnnot, Long idNotaAnnot, int idUffOwn );

    public Long inserisciNotaTrascr( TNotaTrascr notaTrascr, Long idNotaTrascr, int idUffOwn );

    public Long modificaNota( TNota nota, int idUffOwn );

    public Long modificaNotaAnnot( TNotaAnnot notaAnnot, Long idNota, int idUffOwn );

    public Long modificaNotaTrascr( TNotaTrascr notaAnnot, Long idNota, int idUffOwn );

    public String eliminaNota( final Long idNota, final int idUffOwn );

    public List<BeneAssociatoDto> listaBeniAssociati( final Long idNota, final int idUffOwn );

    public List<BeneAssociabileDto> listaBeniAssociabiliA( final Long idNota, final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
        final Integer repAnno, final Integer repNumero, final Long idProced, final Short idConserv );

    public List<BeneAssociabileDto> listaBeniAssociabiliTD( final Long idNota, final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
        final Integer repAnno, final Integer repNumero, final Long idProced, final Short idConserv );

    public List<BeneAssociabileDto> listaBeniAssociabiliTS( final Long idNota, final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
        final Integer repAnno, final Integer repNumero, final Long idProced, final Short idConserv );

    public List<String> cercaOsservazioniNota( final Integer idUffOwn, final Long idNota, final Character tipoInfo );

    public Long inserisciQuadroD(
        Integer idUfficioUtente, String descUtente, Long idNota, Character tipoInf, String blocco, int progr );

    public String aggiornaStatoNota( Integer idUfficioUtente, String descUtente, Long idNota, Character flgstato );

    public String cancellaQuadroD( Integer idUffOwn, Long idNota, Character tipoInf );

    public String inserisciNotaBene( Long oldIdNota, Long idNota, int codiKIdUffOwner, String descUtente );

    public BeneDaAssociareDto beneDaAssociare( Long idBene, Integer idUffOwn );

    public String associaNotaBene( TNotaBene notabene );

    public String descTipoCatasto( Character idTipoCat );

    public String descTipoFondo( Character idTipoFondo );

    public String descTipoDenuncia( Character idTipoDenuncia );

    public String descCatCatasto( Integer idCatCatasto );
    
    public String descBreveCatCatasto( Integer idCatCatasto );
    
    public String getSiglaProvincia( String comune );

    public Character getFlagDecisorio( Integer tipoAtto, Integer catAtto );

    public List<SoggettoInteressatoDto> listaPersoneFisiche( final Long idNota, final int idUff );
    
//    public List<SoggettoInteressatoDto> listaPersoneFisicheCompleta( final Long idNota, final int idUff );

    public Long getNotaBene( final Long idNota, final Integer idUffOwn );

    public BeneAssociatoDto dettaglioNotaBene( Short progressivoNotaBene, Long idNota, Integer idUffOwn );

    public List<TitoloDirittoDto> listaTitoliDiritto( Short progressivoNotaBene, Long idNota, Integer idUffOwn );
    
    public TitoloDirittoDto titoloDirittoPF(Short progressivoNotaBene, Long idNota, Integer idUffOwn, Short titoloSel, Integer idTipoInteresse);
    
    public TitoloDirittoDto titoloDirittoPG(Short progressivoNotaBene, Long idNota, Integer idUffOwn, Short titoloSel, Integer idTipoInteresse);
    
    public Character getTipoSogg(Short progressivoNotaBene, Long idNota, Integer idUffOwn, Short titoloSel);
    
    public Integer modificaTitoloDiritto(
        Short progressivoNotaBene, Long idNota,  Integer idUffOwn, Short titoloSel,  String utente, Long quotaNum, 
        Long quotaDec, Long quotaDen, Integer idTipoInt);
    
    public String cancellaNotaBene( Long idNota, Integer idUffOwn, Long progrNotaBene );

    public String updateStatoNota( Long idNota, Integer idUffOwn, String descUtente );

    public String updateNotaBene( TNotaBene notabene, String descUtente, String codCatasto );

    public SoggettoInteressatoDto dettaglioSoggettoGiurid( final Long idNota, final Integer idUff, final Long progressivo );

    public SoggettoInteressatoDto dettaglioSoggettoFisico( final Long idNota, final Integer idUff, final Long progressivo );

    public SoggettoInteressatoDto dettaglioPersonaFisica( final Long idSoggetto, final Integer idUff, final Integer versione );

    public SoggettoInteressatoDto dettaglioPersonaGiurid( final Long idSoggetto, final Integer idUff, final Integer versione );
    
    public TipoDomicilioDto getTipoDomicilio(Integer idUff, Long numeroProcedimento, Long idSoggetto);
    
    public String getDescrizioneQuadroD(Integer idUff, Long idNota);

    public String getStatoByBelfiore( String codiceComuneNasc );

    public List<SoggettoInteressatoDto> listaPersoneGiurid( final Long idNota, final Integer idUff );

    public String getStatoByDescIstat( final String descIstat );

    public Integer modificaSoggettoGiurid( final Long idNota, final Integer idUff, final String utente,
        final String denom, final String comune, final String provincia, final String pIva, final String codComune, final Long progressivo );

    public Integer resetErroreSoggetto( final Long idNota, final Integer idUff, final String utente, final Long progressivo );

    public String cancellaNotaSoggBene( Long idNota, Integer idUffOwn, Long progrNotaBene );

    public Character sessoSoggettoFisico( final Long idNota, final Integer idUffOwn, final Integer progrSoggCon );

    public Integer modificaSoggettoFisico( final Long idNota, final Integer idUffOwn, final String utente, final String cognome,
        final String nome, final Character sesso, final Date dataNascita, final String statoNascita, final String comuneNascita,
        final String provNascita, final String codFiscale, final Character regime, final Integer progrSoggCon, final Long progressivo );

    public List<SoggettoInteressatoDto> listaPFAssocAttoSequesTrascr
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv);

    public List<SoggettoInteressatoDto> listaPGAssocAttoSequesTrascr
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv);

    public List<SoggettoInteressatoDto> listaPFAssocAttoDecisTrascr
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv );

    public List<SoggettoInteressatoDto> listaPGAssocAttoDecisTrascr
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv );

    public List<SoggettoInteressatoDto> listaPFAssocAttoDecisCassazAnnot
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv );

    public List<SoggettoInteressatoDto> listaPGAssocAttoDecisCassazAnnot
        ( final Long idTipoAtto, final Long idCatAtto, final Integer repert2, final Integer repert, final Long idProc, final Long idNota,
            final Integer idUff, final Integer idConserv );

    public ComuneProvinciaDto getComuneByBelfiore( final String codiceComuneNasc );

    public ComuneProvinciaDto getComuneByIstat( final String codiceComuneSede );

    public List<ComuneConservatoriaDto> getComuniByConservatoria( Long conservatoriaId );
    
    List<SoggettoInteressatoDto> listaSoggettiUnitaNegoziali( Long idNota, Integer idUffOwn );
    
    List<BeneAssociatoDescDto> listaUnitaNegoziali( Long idNota, Integer idUffOwn );
    
    public String getTipoTerreno (Long idNota, Integer idUffOwn);

    //    public SoggettoInteressatoDto soggettoDaAssociarePF(Long idSoggetto, Integer idUffOwn, Integer versione );
    //    public SoggettoInteressatoDto soggettoDaAssociarePG(final Long idSoggetto, final Integer idUffOwn, final Integer versione );

    public Integer inserisciSoggettoTNotaSogg(
        final SoggettoInteressatoDto dettaglioPers, final Integer idUffOwn, final String utente, final Long idNota );

    public Integer inserisciSoggettoTNotaSoggPF(
        final SoggettoInteressatoDto dettaglioPers, final Long idNota, final Integer idUff, final Integer progressivo, final String utente );

    public String cancellaNotaSoggettoPF( Long idNota, Integer idUffOwn, Long progrNotaSoggetto );

    public String cancellaNotaSoggettoPG( final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto );

    public String cancellaNotaSoggetto( final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto );

    public String cancellaNotaBeneSogg( final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto );

    public Integer inserisciSoggettoTNotaSoggPG(
        SoggettoInteressatoDto dettaglioPers, Long idNota, Integer idUffOwn, Integer progressivo, String utente );

    public Integer statoNotaToZero( final Long idNota, final Integer idUff, final String utente );
    
    public List<SituazioneProprietaDto> listaSituazProprieta( final Long idNota, final int idUff );
    
//    public Integer pulisciNotaUn_old(final Long idNota, final Integer idUffOwn );
    
    public void pulisciNotaUn(final Long idNota, final Integer idUffOwn );

//    public Integer pulisciNotaSoggUn_old(final Long idNota, final Integer idUffOwn );
    
    public void pulisciNotaSoggUn(final Long idNota, final Integer idUffOwn );
    
//    public Integer inserisciNotaUn( final Long idNota, final Integer idUff, final Integer idUnitaNeg, final String utente  );

    public String inserisciNotaUn( final Long idNota, final Integer idUff, final Integer idUnitaNeg, final String utente  );

    public Integer riproponiNotaUn( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );

    public Integer riproponiNotaSogg( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );

    public Integer riproponiNotaSoggPF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );
    
    public Integer riproponiNotaSoggPNF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );
    
    public Integer riproponiNotaSoggUn( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );
    
    public Integer riproponiNotaSoggBene( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );
    
    public Integer riproponiNotaQuadroD( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente );
    
    public Integer inserisciNotaSoggettoUn( Long idNota, int idUff, String utente, int progrUn, int progrRiga, SituazioneProprietaDto situazione );
     
    //rm
    public Integer inserisciTitoliDiritto( final Long numeroComunicazione, final Long beneSitMp, final Long progrSogg,
            final Integer tipoInteresse, final Long numeratoreQuotaIntero, final Long numeratoreQuotaDecimale,
            final Long denominatoreQuota, final Integer idUffOwner, final String descUtente );

    public Double controllaCapienza(final Long numeroComunicazione, final Long beneSitMp,final Long progrSogg,
    		final Integer tipoInteresse,final Integer idUffOwner, final String descUtente );
    
    public Integer controllaDuplicato( final Long numeroComunicazione,final Long beneSitMp,final Long progrSogg,
    		final Integer tipoInteresse,final Integer idUffOwner, final String descUtente );
    
//    public Character statoNota( final Long idNota, final Integer idUff, final String utente );
    
//    public Integer cancellaTitoliDiritto( final Long numeroComunicazione, final Long beneSitMp, final Long progrSogg,
//            final Long tipoInteresse,  final Integer idUffOwner );

    public String cancellaTitoliDiritto( final Long numeroComunicazione, final Long beneSitMp, final Long progrSogg,
            final Integer tipoInteresse,  final Integer idUffOwner );

    public Integer aggiornaNotaBeneUN( 
        final Long idNota, final int idUff, final int progrUn, final int progrRiga, final int progrBene, final String utente );

    public Integer pulisciNotaBeneUN( final Long idNota, final int idUff );
    
//    public Integer cancellaNotaSoggUn_old(final Long idNota, final Integer idUff, final Long progrSogg );

    public void cancellaNotaSoggUn(final Long idNota, final Integer idUff, final Long progrSogg );

    public String controllaSituazProprieta( final Long idNota, final int idUff );
   
    public List<BeneAssociabileDto> listaBeniAnnot( final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
            final Short repAnno, final Long repNumero, final Long idProced, String tenoreMisura);

    public List<BeneAssociabileDto> listaBeniTrascrD(final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
            final Short repAnno, final Long repNumero, final Long idProced);

    public List<BeneAssociabileDto> listaBeniTrascrS(final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
            final Short repAnno, final Long repNumero, final Long idProced);

    public Integer aggiornaUnNota( final Long idNota, final int idUff, final int soggContro, final int beniInteressati, final int unitaNeg, final String utente );
    
    public Integer getNumSoggettiContro( final Long idNota, final int idUff );
    
    public Integer getNumBeniUnitaNeg( final Long idNota, final int idUff );    
    //rm
    public Boolean esisteSoggettoFisico( final Long idNota, final Integer idUffOwn, final Integer progrSoggCon );
    
    public List<ComunicazionePredispDto> listaComunicazioniConservatoriePredisp(
            final Integer ufficio, final String tipoNota, final Integer tipoAtto,final Integer catAtto, final Short repAnno, final Long repNumero, final String attoSpecie, final String descSpecie);

    public Character getStatoNota(  final Long idNota, final Integer idUff );

    public List<Object[]> getComuniSediTribun( String pattern );

    public Object[] getComuneByUfficio( Integer idUffOwn );

    public List<Object[]> getIstatComuniStartingWith( String upperCase );

    public List<Object[]> getBelfComuniStartingWith( String upperCase );

    public List<Object[]> getAllComuniProv( String upperCase );

	public Long getMaxProgrNotaBene(Long idNota, int idUffOwn, long longValue);

	public List<SoggettoBeneDto> listaSoggettiBeneAnnot(int idUffOwn,
			Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero,
			long idBene, long idProced, String tenoreMisura);

	public List<SoggettoBeneDto> listaSoggettiBeneTrascrD(int idUffOwn,
			Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero,
			long idBene, long idProced);

	public List<SoggettoBeneDto> listaSoggettiBeneTrascrS(int idUffOwn,
			Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero,
			long idBene, long idProced);
	
	public List<NotaPersFisRegimeDto> listaPFinRegime(Long idNota, Integer idUffOwn, Long progrSogg);

}
