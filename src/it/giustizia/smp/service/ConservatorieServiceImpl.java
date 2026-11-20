package it.giustizia.smp.service;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ConservatorieDao;
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
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.dto.pg_conservatorie.TipoCategAttiDto;
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
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneId;
import it.giustizia.smp.model.TNota;
import it.giustizia.smp.model.TNotaAnnot;
import it.giustizia.smp.model.TNotaBene;
import it.giustizia.smp.model.TNotaBeneId;
import it.giustizia.smp.model.TNotaTrascr;
import it.giustizia.smp.model.TNotaUn;
import it.giustizia.smp.model.TNotaUnId;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoComunic;
import it.giustizia.smp.model.TTpTipoFondo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional( rollbackFor = Exception.class )
public class ConservatorieServiceImpl implements ConservatorieService
{

    @Autowired
    private ConservatorieDao conservatorieDao;

    @Autowired
    private CommonDao        commonDao;

    @Autowired
    private Mapper           mapperBean;

    public ConservatorieDao getConservatorieDao()
    {
        return conservatorieDao;
    }

    public void setConservatorieDao( ConservatorieDao conservatorieDao )
    {
        this.conservatorieDao = conservatorieDao;
    }

    public List<ComboForm> getCategorieAttiNonSequestri( Integer tipoUff )
    {
        List<TipoCategAttiDto> lista = conservatorieDao.getCategorieAttiNonSequestri( tipoUff );
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TipoCategAttiDto> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
            TipoCategAttiDto categoria = iterator.next();
            risultati.add( new ComboForm( "" + categoria.getCODI_K_ID_CAT_ATTO() + ";" + categoria.getCODI_FK_ID_TIPO_ATTO() + ";" + categoria.getFLG_DECISORIO(),
                categoria.getDESC_CAT_ATTO() ) );
        }
        return risultati;
    }

    public List<ComboForm> getCategorieAtti( String ricIns, Integer tipoUff )
    {
        List<TipoCategAttiDto> lista;
        if( ricIns.equals( "ricerca" ) )
            lista = conservatorieDao.getCategorieAttiRicerca( tipoUff );
        else
            lista = conservatorieDao.getCategorieAttiInserimento( tipoUff );
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TipoCategAttiDto> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
            TipoCategAttiDto categoria = iterator.next();
            risultati.add( new ComboForm( "" + categoria.getCODI_K_ID_CAT_ATTO() + ";" + categoria.getCODI_FK_ID_TIPO_ATTO() + ";" + categoria.getFLG_DECISORIO(),
                categoria.getDESC_CAT_ATTO() ) );
        }
        return risultati;
    }

    public List<ComboForm> getTipoComunic()
    {
        List<TTpTipoComunic> lista = conservatorieDao.getTipoComunic();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoComunic> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
            TTpTipoComunic tipoComunic = iterator.next();
            risultati.add( new ComboForm( String.valueOf( tipoComunic.getCodiKTipoComunic() ), tipoComunic.getDescTipoComunic() ) );
        }
        return risultati;
    }

    public List<ComboForm> getTipoComunicAnn()
    {
        List<TTpTipoComunic> lista = conservatorieDao.getTipoComunicAnn();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoComunic> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
            TTpTipoComunic tipoComunic = iterator.next();
            risultati.add( new ComboForm( String.valueOf( tipoComunic.getCodiKTipoComunic() ), tipoComunic.getDescTipoComunic() ) );
        }
        return risultati;
    }

    public List<ComboForm> getTipoComunicTrasc()
    {
        List<TTpTipoComunic> lista = conservatorieDao.getTipoComunicTrasc();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoComunic> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
            TTpTipoComunic tipoComunic = iterator.next();
            risultati.add( new ComboForm( String.valueOf( tipoComunic.getCodiKTipoComunic() ), tipoComunic.getDescTipoComunic() ) );
        }
        return risultati;
    }

    public Long getIdProcedimento( Integer idUffOwn, Integer annoProc, Integer numeProc, String tipoAtto, String catAtto )
    {
        Long idProcedimento = conservatorieDao.getIdProcedimento( idUffOwn, annoProc, numeProc, tipoAtto, catAtto );
        return idProcedimento;
    }

    public List<Short> cercaProgressivoNota( Integer idUffOwn, Integer annoProc, Integer numeProc )
    {
        List<Short> lista = conservatorieDao.cercaProgressivoNota( idUffOwn, annoProc, numeProc );
        return lista;
    }

    public List<ComunicazioneListaForm> listaComunicazioniConservatorie(
        String tipo, String categorieAtti, Short anno, Long dalNumero,
        Long alNumero, String conservatoria, String cognome, String nome,
        String denominazione, Integer idUfficioUtente )
    {
        List<ComunicazioneListaForm> listaComunicazioni = new ArrayList<ComunicazioneListaForm>();
        List<ComunicazioneDto> comunicazioni = conservatorieDao.listaComunicazioniConservatorie(
            tipo, categorieAtti, anno, dalNumero, alNumero, conservatoria, cognome, nome, denominazione, idUfficioUtente );
        //      List<ListaComunicazioniDto> comunicazioni1 = conservatorieDao.listaComunicazioniConservatorie1(
        //              tipo,categorieAtti,anno,dalNumero,alNumero,conservatoria,cognome,nome,denominazione,idUfficioUtente);
        if( comunicazioni.isEmpty() )
        {
            return null;
        }
        //      mapperBean.map(comunicazioni, listaComunicazioni);

        Iterator<ComunicazioneDto> iter = comunicazioni.iterator();
        while( iter.hasNext() )
        {
            ComunicazioneListaForm currentForm = new ComunicazioneListaForm();
            ComunicazioneDto lista = iter.next();
            currentForm.setIdNota( lista.getIDNOTA().longValue() );
            currentForm.setRepertorio( "" + lista.getANNOREPERTORIO() + "/" + lista.getNUMEROREPERTORIO() + "-"
                                       + lista.getPROGRESSIVOREPERTORIO() );
            currentForm.setDescTipoCatAtto( lista.getDESCTIPOCATATTO() );
            switch( lista.getSTATOCOMUNICAZIONE() )
            {
            case '0': currentForm.setStatoComunicazione( "in lavorazione" ); break;
            case '1': currentForm.setStatoComunicazione( "validata" ); break;
            case '2': currentForm.setStatoComunicazione( "inviata" ); break;
            case '3': currentForm.setStatoComunicazione( "sostituita" ); break;
            }
            currentForm.setDataComunicazione( lista.getDATACOMUNICAZIONE() );
            switch( lista.getTIPOCOMUNICAZIONE() )
            {
            case 'A': currentForm.setTipoComunicazione( "Annotazione" ); break;
            case 'T': currentForm.setTipoComunicazione( "Trascrizione" ); break;
            }
            currentForm.setIdUffOwner( idUfficioUtente );
            listaComunicazioni.add( currentForm );
        }

        return listaComunicazioni;
    }

    public NotaDettaglioForm dettaglioNota( final int idUffOwn, final Long idNota )
    {
        DettaglioComunicazioneDto dettaglioNotaDto = conservatorieDao.dettaglioNota( idUffOwn, idNota );
        //      TNota dettaglioNota1 = conservatorieDao.dettaglioNota1( idUffOwn, idNota );
        NotaDettaglioForm dettaglioNota = new NotaDettaglioForm();
        dettaglioNota.setNumero( dettaglioNotaDto.getCODI_K_ID_NOTA().longValue() );
        dettaglioNota.setStato( dettaglioNotaDto.getFLG_STATO() );
        switch( dettaglioNota.getStato() )
        {
        case '0':
            dettaglioNota.setDescStato( "In lavorazione" );
            break;
        case '1':
            dettaglioNota.setDescStato( "Validata" );
            break;
        case '2':
            dettaglioNota.setDescStato( "Inviata" );
            break;
        case '3':
            dettaglioNota.setDescStato( "Sostituita" );
            break;
        }
        dettaglioNota.setTipoAttoId( dettaglioNotaDto.getCODI_FK_ID_TIPO_ATTO() == null ? null : dettaglioNotaDto.getCODI_FK_ID_TIPO_ATTO()
            .longValue() );
        dettaglioNota.setTipoNotaId( dettaglioNotaDto.getCODI_FK_ID_TIPO_NOTA() == null ? null : dettaglioNotaDto.getCODI_FK_ID_TIPO_NOTA() );
        dettaglioNota.setDescTipoNota( dettaglioNotaDto.getDESC_TIPO_NOTA() );
        dettaglioNota.setCatAttoId( dettaglioNotaDto.getCODI_FK_ID_CAT_ATTO() == null ? null : dettaglioNotaDto.getCODI_FK_ID_CAT_ATTO()
            .longValue() );
        dettaglioNota.setDescCatAtto( dettaglioNotaDto.getDESC_CAT_ATTO() );
        if( dettaglioNotaDto.getDESC_ATTO() != null )
        {
            dettaglioNota.setDescAtto( dettaglioNotaDto.getDESC_ATTO() );
        } else
        {
            dettaglioNota.setDescAtto( dettaglioNotaDto.getDESC_TIPO_COMUNIC() );
        }
        if( dettaglioNotaDto.getNUME_REPERT_1() != null )
        {
            dettaglioNota.setNumeroRepertorio1( dettaglioNotaDto.getNUME_REPERT_1().longValue() );
        }
        if( dettaglioNotaDto.getNUME_REPERT_2() != null )
        {
            dettaglioNota.setRepertorioAnno( dettaglioNotaDto.getNUME_REPERT_2().longValue() );
        }
        dettaglioNota.setNumeroProcedimento( dettaglioNotaDto.getCODI_FK_ID_PROC().longValue() );
        dettaglioNota.setProgressivo( dettaglioNotaDto.getNUME_PROGR_NOTA().longValue() );

        dettaglioNota.setConservatoria( dettaglioNotaDto.getDESC_CONSERV() );
        if( dettaglioNotaDto.getCODI_K_ID_CONSERV() != null )
        {
            dettaglioNota.setConservatoriaId( dettaglioNotaDto.getCODI_K_ID_CONSERV().longValue() );
        }
        dettaglioNota.setCodiceFiscale( dettaglioNotaDto.getDESC_FISCALE_PU() );
        dettaglioNota.setTitoloDescrizione( dettaglioNotaDto.getDESC_TITOLO() );
        dettaglioNota.setData( dettaglioNotaDto.getDATA_TITOLO() );

        dettaglioNota.setPubblicoUfficiale( dettaglioNotaDto.getDESC_PUBBL_UFF() );
        dettaglioNota.setCategoria( dettaglioNotaDto.getDESC_TIPO_PUBBL_UFF() );
        switch( dettaglioNota.getCategoria() )
        {
        case '1':
            dettaglioNota.setDescTipoPubblUff( "Notaio" );
            break;
        case '2':
            dettaglioNota.setDescTipoPubblUff( "Altro pubblico ufficiale" );
            break;
        }

        dettaglioNota.setSede( dettaglioNotaDto.getDESC_SEDE_PUBBL_UFF() );
        if( dettaglioNotaDto.getDESC_PROV_PUBBL_UFF() != null )
        {
            dettaglioNota.setProvincia( dettaglioNotaDto.getDESC_PROV_PUBBL_UFF().toString() );
        }
        dettaglioNota.setAttoSpecie( dettaglioNotaDto.getDESC_SPECIE_ATTO() );
        if( dettaglioNotaDto.getCODI_FK_TIPO_COMUNIC() != null )
            dettaglioNota.setTipoComunic( dettaglioNotaDto.getCODI_FK_TIPO_COMUNIC().toString() );
        if( dettaglioNotaDto.getNUME_UNIT_NEG() != null )
        {
            dettaglioNota.setUnitaNegoziali( dettaglioNotaDto.getNUME_UNIT_NEG().longValue() );
        }
        if( dettaglioNotaDto.getNUME_IMMOBILI() != null )
        {
            dettaglioNota.setImmobili( dettaglioNotaDto.getNUME_IMMOBILI().longValue() );
        }
        if( dettaglioNotaDto.getNUME_SOGG_FAVORE() != null )
        {
            dettaglioNota.setSoggettiFavore( dettaglioNotaDto.getNUME_SOGG_FAVORE().longValue() );
        }
        if( dettaglioNotaDto.getNUME_SOGG_CONTRO() != null )
        {
            dettaglioNota.setSoggettiContro( dettaglioNotaDto.getNUME_SOGG_CONTRO().longValue() );
        }

        dettaglioNota.setListaBeni( listaBeniAssociati( idNota, idUffOwn ) );

        Character tipoNota = dettaglioNotaDto.getCODI_FK_ID_TIPO_NOTA();
        Integer tipoAtto = dettaglioNotaDto.getCODI_FK_ID_TIPO_ATTO().intValue();
        Integer catAtto = dettaglioNotaDto.getCODI_FK_ID_CAT_ATTO().intValue();
        Integer repAnno = dettaglioNotaDto.getNUME_REPERT_2().intValue();
        Integer repNumero = dettaglioNotaDto.getNUME_REPERT_1().intValue();
        Long idProced = dettaglioNotaDto.getCODI_FK_ID_PROC().longValue();
        Short idConserv = dettaglioNotaDto.getCODI_K_ID_CONSERV().shortValue();

        dettaglioNota.setListaBeniAssociabili( listaBeniAssociabili( idNota, idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced,
            idConserv, tipoNota ) );

        List<SoggettoInteressatoDto> lista = new ArrayList<SoggettoInteressatoDto>();
        List<SoggettoInteressatoDescDto> listaDesc = new ArrayList<SoggettoInteressatoDescDto>();
        listaSoggetti( lista, idNota, idUffOwn );
        listaDesc = setListaSoggettiDesc( lista );
        dettaglioNota.setListaSoggetti( listaDesc );

        //        dettaglioNota.setListaSoggetti(setListaSoggettiDesc(idNota, idUffOwn));

        //        List<SoggettoInteressatoDto> listacompleta = new ArrayList<SoggettoInteressatoDto>();
        //        listaSoggettiCompleta( listacompleta, idNota, idUffOwn );
        //        dettaglioNota.setListaSoggettiCompleta( listacompleta );

        if( dettaglioNotaDto.getCODI_FK_ID_TIPO_NOTA().equals( 'A' ) )
        {
            NotaAnnotazioneDto notaAnnotDto = conservatorieDao.getNotaAnnotazione( idUffOwn, idNota );
            if( notaAnnotDto != null )
            {
                if( notaAnnotDto.getDATA_FORMALITA() != null )
                {
                    dettaglioNota.setTrascrizioneDel( notaAnnotDto.getDATA_FORMALITA() );
                }
                if( notaAnnotDto.getNUME_FORMALITA_1() != null )
                {
                    dettaglioNota.setNumeroTrascrizione1( notaAnnotDto.getNUME_FORMALITA_1().intValue() );
                }
                if( notaAnnotDto.getNUME_FORMALITA_2() != null )
                {
                    dettaglioNota.setNumeroTrascrizione2( notaAnnotDto.getNUME_FORMALITA_2().shortValue() );
                }
                if( notaAnnotDto.getNUME_VALORE_IMM_INT() != null )
                {
                    dettaglioNota.setValoreImmobLib( notaAnnotDto.getNUME_VALORE_IMM_INT().longValue() );
                }
                if( notaAnnotDto.getNUME_VALORE_IMM_DEC() != null )
                {
                    dettaglioNota.setValoreImmobLibParteDecimale( notaAnnotDto.getNUME_VALORE_IMM_DEC().byteValue() );
                }
                // verificare valorizzazione valore immobili
                dettaglioNota.setVolturaCatastale( false );
                if( notaAnnotDto.getFLG_VOLTURA().equals( '1' ) )
                {
                    dettaglioNota.setVolturaCatastale( true );
                }
            }
        }

        if( dettaglioNotaDto.getCODI_FK_ID_TIPO_NOTA().equals( 'T' ) )
        {
            NotaTrascrizioneDto notaTrascrDto = conservatorieDao.getNotaTrascrizione( idUffOwn, idNota );
            if( notaTrascrDto != null )
            {
                dettaglioNota.setAttoSoggVoltura( false );
                if( notaTrascrDto.getFLG_VOLTURA().equals( '1' ) )
                {
                    dettaglioNota.setAttoSoggVoltura( true );
                }
                dettaglioNota.setDifferita( false );
                if( notaTrascrDto.getFLG_VOLTURA_DIFF().equals( '1' ) )
                {
                    dettaglioNota.setDifferita( true );
                }
                dettaglioNota.setDataDal( notaTrascrDto.getDATA_VOLTURA_DIFF() );
                dettaglioNota.setPresenzaCondizione( false );
                if( notaTrascrDto.getFLG_CONDIZIONE().equals( '1' ) )
                {
                    dettaglioNota.setPresenzaCondizione( true );
                }
                dettaglioNota.setPresenzaTermini( false );
                if( notaTrascrDto.getFLG_TERMINI().equals( '1' ) )
                {
                    dettaglioNota.setPresenzaTermini( true );
                }
                dettaglioNota.setDataFormalita( notaTrascrDto.getDATA_FORMALITA() );
                if( notaTrascrDto.getNUME_FORMALITA_1() != null )
                {
                    dettaglioNota.setNumeroFormalita1( notaTrascrDto.getNUME_FORMALITA_1().intValue() );
                }
                if( notaTrascrDto.getNUME_FORMALITA_2() != null )
                {
                    dettaglioNota.setNumeroFormalita2( notaTrascrDto.getNUME_FORMALITA_2().shortValue() );
                }
                dettaglioNota.setQuadroA( false );
                if( notaTrascrDto.getFLG_QUADROD_A().equals( '1' ) )
                {
                    dettaglioNota.setQuadroA( true );
                }
                dettaglioNota.setQuadroB( false );
                if( notaTrascrDto.getFLG_QUADROD_B().equals( '1' ) )
                {
                    dettaglioNota.setQuadroB( true );
                }
                dettaglioNota.setQuadroC( false );
                if( notaTrascrDto.getFLG_QUADROD_C().equals( '1' ) )
                {
                    dettaglioNota.setQuadroC( true );
                }
            }
        }

        return dettaglioNota;

    }

    public List<SoggettoInteressatoDescDto> setListaSoggettiDesc( List<SoggettoInteressatoDto> lista )
    {
        List<SoggettoInteressatoDescDto> listaSoggettidesc = new ArrayList<SoggettoInteressatoDescDto>();
        Iterator<SoggettoInteressatoDto> iter = lista.iterator();
        while( iter.hasNext() )
        {
            SoggettoInteressatoDescDto soggettoDesc = new SoggettoInteressatoDescDto();
            SoggettoInteressatoDto soggetto = iter.next();

            if( soggetto.getIDSOGGETTO() != null )
                soggettoDesc.setIdSoggetto( soggetto.getIDSOGGETTO().longValue() );
            if( soggetto.getVERSIONE() != null )
                soggettoDesc.setVersione( soggetto.getVERSIONE().intValue() );
            if( soggetto.getPROGRESSIVO() != null )
                soggettoDesc.setProgressivo( soggetto.getPROGRESSIVO().longValue() );
            if( soggetto.getTIPOLOGIA() != null )
                soggettoDesc.setTipologia( soggetto.getTIPOLOGIA() );
            if( soggetto.getSOGGETTO() != null )
                soggettoDesc.setSoggetto( soggetto.getSOGGETTO() );
            if( soggetto.getSOGGETTOSITMP() != null )
                soggettoDesc.setSoggettoSitMp( soggetto.getSOGGETTOSITMP().longValue() );
            if( soggetto.getFLAGERRORE() != null )
                soggettoDesc.setFlagErrore( soggetto.getFLAGERRORE() );
            if( soggetto.getCOGNOME() != null )
                soggettoDesc.setCognome( soggetto.getCOGNOME() );
            if( soggetto.getNOME() != null )
                soggettoDesc.setNome( soggetto.getNOME() );
            if( soggetto.getFLGSESSO() != null )
                soggettoDesc.setFlgSesso( soggetto.getFLGSESSO() );
            if( soggetto.getDATANASCITA() != null )
                soggettoDesc.setDataNascita( soggetto.getDATANASCITA() );
            if( soggetto.getDATAMORTE() != null )
                soggettoDesc.setDataMorte( soggetto.getDATAMORTE() );
            if( soggetto.getCOMUNENASCITA() != null )
                soggettoDesc.setComuneNascita( soggetto.getCOMUNENASCITA() );
            if( soggetto.getCODICECOMUNENASC() != null )
                soggettoDesc.setCodiceComuneNasc( soggetto.getCODICECOMUNENASC() );
            if( soggetto.getPROVNASCITA() != null )
                soggettoDesc.setProvNascita( soggetto.getPROVNASCITA() );
            if( soggetto.getSTATONASCITA() != null )
                soggettoDesc.setStatoNascita( soggetto.getSTATONASCITA() );
            if( soggetto.getCODICEFISCALEPF() != null )
                soggettoDesc.setCodiceFiscalePF( soggetto.getCODICEFISCALEPF() );
            if( soggetto.getREGIME() != null )
                soggettoDesc.setRegime( soggetto.getREGIME() );
            if( soggetto.getDESCREGIME() != null )
                soggettoDesc.setDescRegime( soggetto.getDESCREGIME() );
            if( soggetto.getCODPROGRSOGGC() != null )
                soggettoDesc.setCodProgrSoggC( soggetto.getCODPROGRSOGGC().intValue() );
            if( soggetto.getDENOMINAZIONE() != null )
                soggettoDesc.setDenominazione( soggetto.getDENOMINAZIONE() );
            if( soggetto.getSTATOSEDE() != null )
                soggettoDesc.setStatoSede( soggetto.getSTATOSEDE() );
            if( soggetto.getCOMUNESEDE() != null )
                soggettoDesc.setComuneSede( soggetto.getCOMUNESEDE() );
            if( soggetto.getPROVSEDE() != null )
                soggettoDesc.setProvSede( soggetto.getPROVSEDE() );
            if( soggetto.getPARTITAIVA() != null )
                soggettoDesc.setPartitaIva( soggetto.getPARTITAIVA() );
            if( soggetto.getCODICECOMUNESEDE() != null )
                soggettoDesc.setCodiceComuneSede( soggetto.getCODICECOMUNESEDE() );
            if( soggetto.getPROGSOGGETTO() != null )
                soggettoDesc.setProgSoggetto( soggetto.getPROGSOGGETTO().longValue() );
            if( soggetto.getPROGUNITANEGOZIALE() != null )
                soggettoDesc.setProgUnitaNegoziale( soggetto.getPROGUNITANEGOZIALE().longValue() );
            if( soggetto.getQUALIFICAUNITANEGOZIALE() != null )
                soggettoDesc.setQualificaUnitaNegoziale( soggetto.getQUALIFICAUNITANEGOZIALE() );
            if( soggetto.getNUMEQUOTAINTUNITANEGOZIALE() != null )
                soggettoDesc.setNumeQuotaIntUnitaNegoziale( soggetto.getNUMEQUOTAINTUNITANEGOZIALE().longValue() );
            if( soggetto.getNUMEQUOTADECUNITANEGOZIALE() != null )
                soggettoDesc.setNumeQuotaDecUnitaNegoziale( soggetto.getNUMEQUOTADECUNITANEGOZIALE().longValue() );
            if( soggetto.getNUMEQUOTADENOMUNITANEGOZIALE() != null )
                soggettoDesc.setNumeQuotaDenomUnitaNegoziale( soggetto.getNUMEQUOTADENOMUNITANEGOZIALE().longValue() );
            if( soggetto.getTITOLOINTERESSEUNITANEGOZIALE() != null )
                soggettoDesc.setTitoloInteresseUnitaNegoziale( soggetto.getTITOLOINTERESSEUNITANEGOZIALE().longValue() );
            if( soggetto.getDESCRINTERESSEUNITANEGOZIALE() != null )
                soggettoDesc.setDescrInteresseUnitaNegoziale( soggetto.getDESCRINTERESSEUNITANEGOZIALE() );
            if( soggetto.getIDREGIMEUNITANEGOZIALE() != null )
                soggettoDesc.setIdregimeUnitaNegoziale( soggetto.getIDREGIMEUNITANEGOZIALE() );
            if( soggetto.getREGIMECONIUGIUNITANEGOZIALE() != null )
                soggettoDesc.setRegimeConiugiUnitaNegoziale( soggetto.getREGIMECONIUGIUNITANEGOZIALE().longValue() );

            listaSoggettidesc.add( soggettoDesc );

        }

        return listaSoggettidesc;
    }

    //	private List<SoggettoInteressatoDescDto> setListaSoggettiDesc(Long idNota, int idUffOwn)
    //    {
    //        List<SoggettoInteressatoDescDto> listaSoggettidesc = new ArrayList<SoggettoInteressatoDescDto>();
    //        List<SoggettoInteressatoDto> listaSoggInt = new ArrayList<SoggettoInteressatoDto>();
    //        listaSoggetti( listaSoggInt, idNota, idUffOwn );
    //        if( listaSoggInt.isEmpty() ) 
    //        { 
    //        	return null; 
    //        }
    //
    //        Iterator<SoggettoInteressatoDto> iter = listaSoggInt.iterator();
    //        while( iter.hasNext() )
    //        {
    //        	SoggettoInteressatoDescDto soggettoDesc = new SoggettoInteressatoDescDto();
    //        	SoggettoInteressatoDto soggetto = iter.next();
    //
    //            if(soggetto.getIDSOGGETTO() != null)
    //				soggettoDesc.setIdSoggetto(soggetto.getIDSOGGETTO().longValue()); 
    //			if (soggetto.getVERSIONE() != null)
    //				soggettoDesc.setVersione(soggetto.getVERSIONE().intValue());           
    //			if (soggetto.getPROGRESSIVO() != null)
    //				soggettoDesc.setProgressivo(soggetto.getPROGRESSIVO().longValue());     
    //			if (soggetto.getTIPOLOGIA() != null)
    //				soggettoDesc.setTipologia(soggetto.getTIPOLOGIA());         
    //			if (soggetto.getSOGGETTO() != null)
    //				soggettoDesc.setSoggetto(soggetto.getSOGGETTO());          
    //			if (soggetto.getSOGGETTOSITMP() != null)
    //				soggettoDesc.setSoggettoSitMp(soggetto.getSOGGETTOSITMP().longValue());   
    //			if (soggetto.getFLAGERRORE() != null)
    //				soggettoDesc.setFlagErrore(soggetto.getFLAGERRORE());           
    //			if (soggetto.getCOGNOME() != null)
    //				soggettoDesc.setCognome(soggetto.getCOGNOME()); 
    //			if (soggetto.getNOME() != null)
    //				soggettoDesc.setNome(soggetto.getNOME()); 
    //			if (soggetto.getFLGSESSO() != null)
    //				soggettoDesc.setFlgSesso(soggetto.getFLGSESSO());         
    //			if (soggetto.getDATANASCITA() != null)
    //				soggettoDesc.setDataNascita(soggetto.getDATANASCITA());       
    //			if (soggetto.getDATAMORTE() != null)
    //				soggettoDesc.setDataMorte(soggetto.getDATAMORTE());        
    //			if (soggetto.getCOMUNENASCITA() != null)
    //				soggettoDesc.setComuneNascita(soggetto.getCOMUNENASCITA()); 
    //			if (soggetto.getCODICECOMUNENASC() != null)
    //				soggettoDesc.setCodiceComuneNasc(soggetto.getCODICECOMUNENASC()); 
    //			if (soggetto.getPROVNASCITA() != null)
    //				soggettoDesc.setProvNascita(soggetto.getPROVNASCITA()); 
    //			if (soggetto.getSTATONASCITA() != null)
    //				soggettoDesc.setStatoNascita(soggetto.getSTATONASCITA()); 
    //			if (soggetto.getCODICEFISCALEPF() != null)
    //				soggettoDesc.setCodiceFiscalePF(soggetto.getCODICEFISCALEPF());
    //			if (soggetto.getREGIME() != null)
    //				soggettoDesc.setRegime(soggetto.getREGIME()); 
    //			if (soggetto.getDESCREGIME() != null)
    //				soggettoDesc.setDescRegime(soggetto.getDESCREGIME()); 
    //			if (soggetto.getCODPROGRSOGGC() != null)
    //				soggettoDesc.setCodProgrSoggC(soggetto.getCODPROGRSOGGC()); 
    //			if (soggetto.getDENOMINAZIONE() != null)
    //				soggettoDesc.setDenominazione(soggetto.getDENOMINAZIONE());
    //			if (soggetto.getSTATOSEDE() != null)
    //				soggettoDesc.setStatoSede(soggetto.getSTATOSEDE());
    //			if (soggetto.getCOMUNESEDE() != null)
    //				soggettoDesc.setComuneSede(soggetto.getCOMUNESEDE());
    //			if (soggetto.getPROVSEDE() != null)
    //				soggettoDesc.setProvSede(soggetto.getPROVSEDE());
    //			if (soggetto.getPARTITAIVA() != null)
    //				soggettoDesc.setPartitaIva(soggetto.getPARTITAIVA());
    //			if (soggetto.getCODICECOMUNESEDE() != null)
    //				soggettoDesc.setCodiceComuneSede(soggetto.getCODICECOMUNESEDE());
    //			if (soggetto.getPROGSOGGETTO() != null)
    //				soggettoDesc.setProgSoggetto(soggetto.getPROGSOGGETTO().longValue());       		       			
    //			if (soggetto.getPROGUNITANEGOZIALE() != null)
    //				soggettoDesc.setProgUnitaNegoziale(soggetto.getPROGUNITANEGOZIALE().longValue()); 		 			
    //			if (soggetto.getQUALIFICAUNITANEGOZIALE() != null)
    //				soggettoDesc.setQualificaUnitaNegoziale(soggetto.getQUALIFICAUNITANEGOZIALE());			
    //			if (soggetto.getNUMEQUOTAINTUNITANEGOZIALE() != null)
    //				soggettoDesc.setNumeQuotaIntUnitaNegoziale(soggetto.getNUMEQUOTAINTUNITANEGOZIALE().longValue());	 	
    //			if (soggetto.getNUMEQUOTADECUNITANEGOZIALE() != null)
    //				soggettoDesc.setNumeQuotaDecUnitaNegoziale(soggetto.getNUMEQUOTADECUNITANEGOZIALE().longValue());	 	
    //			if (soggetto.getNUMEQUOTADENOMUNITANEGOZIALE() != null)
    //				soggettoDesc.setNumeQuotaDenomUnitaNegoziale(soggetto.getNUMEQUOTADENOMUNITANEGOZIALE().longValue());	
    //			if (soggetto.getTITOLOINTERESSEUNITANEGOZIALE() != null)
    //				soggettoDesc.setTitoloInteresseUnitaNegoziale(soggetto.getTITOLOINTERESSEUNITANEGOZIALE().longValue());
    //			if (soggetto.getDESCRINTERESSEUNITANEGOZIALE() != null)
    //				soggettoDesc.setDescrInteresseUnitaNegoziale(soggetto.getDESCRINTERESSEUNITANEGOZIALE());	
    // 			if (soggetto.getIDREGIMEUNITANEGOZIALE() != null)
    //				soggettoDesc.setIdregimeUnitaNegoziale(soggetto.getIDREGIMEUNITANEGOZIALE());    		
    //			if (soggetto.getREGIMECONIUGIUNITANEGOZIALE() != null)
    //				soggettoDesc.setRegimeConiugiUnitaNegoziale(soggetto.getREGIMECONIUGIUNITANEGOZIALE().longValue()); 	            
    //
    //            listaSoggettidesc.add( soggettoDesc );
    //
    //        }
    //
    //		return listaSoggettidesc;
    //	}

    private void listaSoggetti( List<SoggettoInteressatoDto> lista, Long idNota, int idUff )
    {
        List<SoggettoInteressatoDto> listaPF = conservatorieDao.listaPersoneFisiche( idNota, idUff );
        lista.addAll( listaPF );
        List<SoggettoInteressatoDto> listaPNF = conservatorieDao.listaPersoneGiurid( idNota, idUff ); // nome colonna non valido !!
        lista.addAll( listaPNF );
    }

    //GP modificato/eliminato in quanto compreso nel metodo listaSoggetti
    //    private void listaSoggettiCompleta( List<SoggettoInteressatoDto> lista, Long idNota, int idUff )
    //    {
    //        List<SoggettoInteressatoDto> listaPF = conservatorieDao.listaPersoneFisicheCompleta( idNota, idUff );
    //        lista.addAll( listaPF );
    //        List<SoggettoInteressatoDto> listaPNF = conservatorieDao.listaPersoneGiurid( idNota, idUff ); // nome colonna non valido !!
    //        lista.addAll( listaPNF );
    //    }

    public String calcolaUnitaNegoziali( Long idNota, int idUff, String utente )
    {
        Integer soggettiContro = conservatorieDao.getNumSoggettiContro( idNota, idUff );
        if( soggettiContro <= 0L )
            return "Non sono presenti soggetti, privi di errore, associati alla nota";

        Integer beniInteressati = conservatorieDao.getNumBeniUnitaNeg( idNota, idUff );
        if( beniInteressati <= 0L )
            return "Non sono presenti beni, privi di errore, associati alla nota";

        String esito = conservatorieDao.controllaSituazProprieta( idNota, idUff );
        if( !esito.equals( "OK" ) )
            return esito;

        List<SituazioneProprietaDto> listaSituazioni = conservatorieDao.listaSituazProprieta( idNota, idUff );
        for( SituazioneProprietaDto situazione : listaSituazioni )
        {
            situazione.validate(); // trovo gli errori e costruisco la stringa parziale 'Situazione di proprietà'
        }
        for( int k = listaSituazioni.size() - 1; k >= 0; k-- ) // elimino le situazioni errate
        {
            if( !listaSituazioni.get( k ).isGOOD() )
                listaSituazioni.remove( k );
        }
        Collections.sort( listaSituazioni ); // 

        Hashtable<String, String> htSitProprieta = new Hashtable<String, String>( 100 ); // key = beni , val = situazioni 
        for( SituazioneProprietaDto situazione : listaSituazioni )
        {
            String situazioni = situazione.getSTRINGAPARZUN();
            String bene = situazione.getPROGRBENE();
            String stringaTrovata = htSitProprieta.get( bene );
            if( stringaTrovata == null ) // esiste già la chiave corrispondente al bene in questione ?
            {
                htSitProprieta.put( bene, situazioni ); // No, la creo ora
            } else
            {
                htSitProprieta.put( bene, stringaTrovata + " | " + situazioni ); // Si, vi aggiungo la stringa corrente
            }
        }

        Hashtable<String, String> htUnitaNeg = new Hashtable<String, String>( 100 ); // key = situazioni , val = beni
        Enumeration<String> enumSituazioni = htSitProprieta.keys();
        while( enumSituazioni.hasMoreElements() )
        {
            String bene = enumSituazioni.nextElement();
            String situazioni = htSitProprieta.get( bene );
            String beneTrovato = htUnitaNeg.get( situazioni );
            if( beneTrovato == null ) // esiste già la chiave corrispondente alle situazioni in questione ?
            {
                htUnitaNeg.put( situazioni, bene ); // No, la creo ora
            } else
            {
                htUnitaNeg.put( situazioni, beneTrovato + " | " + bene ); // Si, vi aggiungo il bene corrente
            }
        }
        if( htUnitaNeg.size() == 0 )
            return "Nessuna unità negoziale da generare";
        conservatorieDao.pulisciNotaBeneUN( idNota, idUff );
        conservatorieDao.pulisciNotaSoggUn( idNota, idUff );
        conservatorieDao.pulisciNotaUn( idNota, idUff );

        Enumeration<String> enumUnNeg = htUnitaNeg.keys();

        int progrUn = 0;
        while( enumUnNeg.hasMoreElements() )
        {
            progrUn++;
            String sitProp = enumUnNeg.nextElement();
            String beni = htUnitaNeg.get( sitProp );
            String[] arrayBeni = StringUtils.split( beni, " | " );
            conservatorieDao.inserisciNotaUn( idNota, idUff, progrUn, utente );
            int progrBeneUn = 0;
            for( String bene : arrayBeni )
            {
                conservatorieDao.aggiornaNotaBeneUN( idNota, idUff, progrUn, ++progrBeneUn, Integer.valueOf( bene ), utente );
            }
            int progrRiga = 0;
            String catenaStrParz = "";
            for( SituazioneProprietaDto situazione : listaSituazioni )
            {
                String stringaParz = situazione.getSTRINGAPARZUN();
                String bene = situazione.getPROGRBENE();
                if( sitProp.contains( stringaParz ) && beni.contains( bene ) && !catenaStrParz.contains( stringaParz ) )
                {
                    catenaStrParz += stringaParz;
                    conservatorieDao.inserisciNotaSoggettoUn( idNota, idUff, utente, progrUn, ++progrRiga, situazione );
                }
            }
        }
        conservatorieDao.aggiornaUnNota( idNota, idUff, soggettiContro, beniInteressati, progrUn, utente );
        return "OK";
    }

    public List<SoggettoInteressatoDto> listaSoggettiAssociabili( Long idNota, Integer idUffOwn, NotaDettaglioForm dettaglio )
    {
        Long tipoAtto = dettaglio.getTipoAttoId();
        Long catAtto = dettaglio.getCatAttoId();
        Integer repAnno = dettaglio.getRepertorioAnno().intValue();
        Integer repNumero = dettaglio.getNumeroRepertorio1().intValue();
        Long numProc = dettaglio.getNumeroProcedimento();
        Long idConserv = dettaglio.getConservatoriaId();
        Character tipoNota = dettaglio.getTipoNotaId();

        List<SoggettoInteressatoDto> lista = new ArrayList<SoggettoInteressatoDto>();
        List<SoggettoInteressatoDto> listaSogg = null;
        if( tipoNota.equals( 'T' ) )
        {
            listaSogg = conservatorieDao.listaPFAssocAttoSequesTrascr( tipoAtto, catAtto, repAnno, repNumero, numProc, idNota, idUffOwn,
                idConserv.intValue() );
            lista.addAll( listaSogg );
            listaSogg = conservatorieDao.listaPFAssocAttoDecisTrascr( tipoAtto, catAtto, repAnno, repNumero, numProc, idNota, idUffOwn, idConserv
                .intValue() );
            lista.addAll( listaSogg );
            listaSogg = conservatorieDao.listaPGAssocAttoSequesTrascr( tipoAtto, catAtto, repAnno, repNumero, numProc, idNota, idUffOwn,
                idConserv.intValue() );
            lista.addAll( listaSogg );
            listaSogg = conservatorieDao.listaPGAssocAttoDecisTrascr( tipoAtto, catAtto, repAnno, repNumero, numProc, idNota, idUffOwn, idConserv
                .intValue() );
            lista.addAll( listaSogg );
        } else
            if( tipoNota.equals( 'A' ) )
            { // richiamo la query per nota annotazione
                listaSogg = conservatorieDao.listaPFAssocAttoDecisCassazAnnot( tipoAtto, catAtto, repAnno, repNumero, numProc,
                    idNota, idUffOwn, idConserv.intValue() );
                lista.addAll( listaSogg );
                listaSogg = conservatorieDao.listaPGAssocAttoDecisCassazAnnot( tipoAtto, catAtto, repAnno, repNumero, numProc,
                    idNota, idUffOwn, idConserv.intValue() );
                lista.addAll( listaSogg );
            }
        return lista;
    }

    public List<SoggettoInteressatoDto> listaSoggettiUnitaNegoziali( Long idNota, Integer idUffOwn )
    {
        List<SoggettoInteressatoDto> lista = new ArrayList<SoggettoInteressatoDto>();
        lista = conservatorieDao.listaSoggettiUnitaNegoziali( idNota, idUffOwn );
        return lista;
    }

    public List<BeneAssociatoDescDto> listaUnitaNegoziali( Long idNota, Integer idUffOwn )
    {
        List<BeneAssociatoDescDto> lista = new ArrayList<BeneAssociatoDescDto>();
        lista = conservatorieDao.listaUnitaNegoziali( idNota, idUffOwn );
        return lista;
    }

    @SuppressWarnings( "unused" )
    public Long inserisciNota( ComunicazioneInsertForm form, int idUffOwn )
    {
        Long idNota = null;
        Long idNotaAnnot = null;
        Long idNotaTrascr = null;
        TNota nota = new TNota();
        mapperBean.map( form, nota );
        nota.setDescAtto( form.getDescAtto() );
        idNota = conservatorieDao.inserisciNota( nota, idUffOwn );
        if( "A".equals( form.getTipoNota().getId() ) )
        {
            idNotaAnnot = inserisciNotaAnnot( form, idNota, idUffOwn );
        } else
            if( "T".equals( form.getTipoNota().getId() ) )
            {
                idNotaTrascr = inserisciNotaTrascr( form, idNota, idUffOwn );
            }
        return idNota;
    }

    public Long inserisciNotaAnnot( ComunicazioneInsertForm form, Long idNota, int idUffOwn )
    {
        TNotaAnnot notaAnnot = new TNotaAnnot();

        //      mapperBean.map( form, notaAnnot );  // non converte i booleani e non so come fargli separare la parte intera da quella decimale
        notaAnnot.setDataFormalita( form.getTrascrizioneDel() );
        notaAnnot.setDescUtente( form.getDescUtente() );
        notaAnnot.setFlgVoltura( form.isVolturaCatastale() ? '1' : '0' );
        notaAnnot.setNumeFormalita1( form.getNumeroTrascrizione1() );
        notaAnnot.setNumeFormalita2( form.getNumeroTrascrizione2() );

        notaAnnot.setNumeValoreImmInt( null );
        notaAnnot.setNumeValoreImmDec( null );
        if( form.getValoreImmobLib() != null )
        {
            String valoreImmInt = "";
            String valoreImmDec = "0";
            String valore = form.getValoreImmobLib();
            int vInd = valore.indexOf( '.' );
            if( vInd < 0 )
                valoreImmInt = valore;
            else
            {
                valoreImmInt = valore.substring( 0, vInd );
                valoreImmDec = valore.substring( vInd + 1, valore.length() );
            }
            if( valoreImmInt.length() < 1 )
                valoreImmInt = "0";
            if( valoreImmDec.length() < 1 )
                valoreImmDec = "0";
            notaAnnot.setNumeValoreImmInt( new Long( valoreImmInt ) );
//          notaAnnot.setNumeValoreImmDec( new Byte( valoreImmDec ) );
            Byte decByte = calcolaDecimali( valoreImmDec );
            notaAnnot.setNumeValoreImmDec( decByte );
        }
        
        Long idNotaAnnot = conservatorieDao.inserisciNotaAnnot( notaAnnot, idNota, idUffOwn );
        return idNotaAnnot;
    }

    private Byte calcolaDecimali( String valoreDec )
    {
        Byte parteDecimale = 0;
        try
        {
            parteDecimale = Byte.parseByte( (valoreDec + "00").substring( 0, 2 ) );
        } catch( NumberFormatException e )
        {
            // e.printStackTrace();
            return null;
        }
        if( parteDecimale < 0 )
            return null;
        return parteDecimale;
    }

    public Long inserisciNotaTrascr( ComunicazioneInsertForm form, Long idNota, int idUffOwn )
    {
        TNotaTrascr notaTrascr = new TNotaTrascr();

        //      mapperBean.map( form, notaTrascr );
        notaTrascr.setDescUtente( form.getDescUtente() );
        notaTrascr.setFlgFoglioInfo( '0' );
        notaTrascr.setDataFormalita( form.getDataFormalita() );

        notaTrascr.setFlgVoltura( '0' );
        if( form.isAttoSoggVoltura() )
        {
            notaTrascr.setFlgVoltura( '1' );
        }
        notaTrascr.setFlgVolturaDiff( '0' );
        if( form.isDifferita() )
        {
            notaTrascr.setFlgVolturaDiff( '1' );
        }
        notaTrascr.setDataVolturaDiff( form.getDataDal() );

        notaTrascr.setFlgCondizione( '0' );
        if( form.isPresenzaCondizione() )
        {
            notaTrascr.setFlgCondizione( '1' );
        }

        notaTrascr.setFlgTermini( '0' );
        if( form.isPresenzaTermini() )
        {
            notaTrascr.setFlgTermini( '1' );
        }

        notaTrascr.setNumeFormalita1( form.getNumeFormalita1() );
        notaTrascr.setNumeFormalita2( form.getNumeFormalita2() );

        notaTrascr.setFlgQuadrodA( '0' );
        if( form.isQuadroA() )
        {
            notaTrascr.setFlgQuadrodA( '1' );
        }
        notaTrascr.setFlgQuadrodB( '0' );
        if( form.isQuadroB() )
        {
            notaTrascr.setFlgQuadrodB( '1' );
        }
        notaTrascr.setFlgQuadrodC( '0' );
        if( form.isQuadroC() )
        {
            notaTrascr.setFlgQuadrodC( '1' );
        }
        Long idNotaTrascr = conservatorieDao.inserisciNotaTrascr( notaTrascr, idNota, idUffOwn );
        return idNotaTrascr;
    }

    public TAnUfficio getDettaglioUfficio( Integer idUffOwn )
    {
        TAnUfficio ufficio = commonDao.getDettaglioUfficio( idUffOwn );
        return ufficio;
    }

    @SuppressWarnings( "unused" )
    public Long modificaNota( ComunicazioneModificaForm form, int idUffOwn )
    {
        Long idNota = null;
        Long idNotaAnnot = null;
        Long idNotaTrascr = null;
        TNota nota = new TNota();
        mapperBean.map( form, nota );
        nota.setFlgStato( '0' );

        if( form.getTipoNotaId().equals( 'T' ) )
        {
            nota.setDescAtto( form.getTipoComunicSelez() );
            nota.getTTpTipoComunic().setCodiKTipoComunic( form.getTipoComunic().getId() );
        }
        if( form.getTipoNotaId().equals( 'A' ) )
        {
            nota.setDescAtto( form.getDescAtto() );
            nota.getTTpTipoComunic().setCodiKTipoComunic( form.getTipoComunicSelez() );
        }

        if( form.getSede() != null )
        {
            nota.setDescSedePubblUff( form.getSede().trim() );
        }

        if( form.getConservatoriaSelez() != null )
        {
            nota.setDescConservat( form.getConservatoriaSelez() );
        }

        idNota = conservatorieDao.modificaNota( nota, idUffOwn );

        if( form.getTipoNotaId().equals( 'A' ) )
        {
            idNotaAnnot = modificaNotaAnnot( form, idNota, idUffOwn );
        } else
            if( form.getTipoNotaId().equals( 'T' ) )
            {
                idNotaTrascr = modificaNotaTrascr( form, idNota, idUffOwn );
            }
        return idNota;
    }

    public Long modificaNotaAnnot( ComunicazioneModificaForm form, Long idNota, int idUffOwn )
    {
        TNotaAnnot notaAnnot = new TNotaAnnot();
        notaAnnot.setDataFormalita( form.getTrascrizioneDel() );
        notaAnnot.setDescUtente( form.getDescUtente() );
        notaAnnot.setFlgVoltura( form.isVolturaCatastale() ? '1' : '0' );
        notaAnnot.setNumeFormalita1( form.getNumeroTrascrizione1() );
        notaAnnot.setNumeFormalita2( form.getNumeroTrascrizione2() );
        notaAnnot.setNumeValoreImmInt( 0L );
        notaAnnot.setNumeValoreImmDec( (byte)(0) );
        if( form.getValoreImmobLib() != null )
        {
            String valoreImmInt = "";
            String valoreImmDec = "0";
            String valore = form.getValoreImmobLib();
            int vInd = valore.indexOf( '.' );
            if( vInd < 0 )
                valoreImmInt = valore;
            else
            {
                valoreImmInt = valore.substring( 0, vInd );
                valoreImmDec = valore.substring( vInd + 1, valore.length() );
            }
            if( valoreImmInt.length() < 1 )
                valoreImmInt = "0";
            if( valoreImmDec.length() < 1 )
                valoreImmDec = "0";
            notaAnnot.setNumeValoreImmInt( new Long( valoreImmInt ) );
//          notaAnnot.setNumeValoreImmDec( new Byte( valoreImmDec ) );
            Byte decByte = calcolaDecimali( valoreImmDec );
            notaAnnot.setNumeValoreImmDec( decByte );
        }
        Long idNotaAnnot = conservatorieDao.modificaNotaAnnot( notaAnnot, idNota, idUffOwn );
        return idNotaAnnot;
    }

    public Long modificaNotaTrascr( ComunicazioneModificaForm form, Long idNota, int idUffOwn )
    {
        TNotaTrascr notaTrascr = new TNotaTrascr();

        notaTrascr.setDescUtente( form.getDescUtente() );
        notaTrascr.setFlgFoglioInfo( '0' );
        notaTrascr.setDataFormalita( form.getDataFormalita() );

        notaTrascr.setFlgVoltura( '0' );
        if( form.isAttoSoggVoltura() )
        {
            notaTrascr.setFlgVoltura( '1' );
        }
        notaTrascr.setFlgVolturaDiff( '0' );
        if( form.isDifferita() )
        {
            notaTrascr.setFlgVolturaDiff( '1' );
        }
        notaTrascr.setDataVolturaDiff( form.getDataDal() );

        notaTrascr.setFlgCondizione( '0' );
        if( form.isPresenzaCondizione() )
        {
            notaTrascr.setFlgCondizione( '1' );
        }

        notaTrascr.setFlgTermini( '0' );
        if( form.isPresenzaTermini() )
        {
            notaTrascr.setFlgTermini( '1' );
        }

        notaTrascr.setNumeFormalita1( form.getNumeroFormalita1() );
        notaTrascr.setNumeFormalita2( form.getNumeroFormalita2() );

        notaTrascr.setFlgQuadrodA( '0' );
        if( form.isQuadroA() )
        {
            notaTrascr.setFlgQuadrodA( '1' );
        }
        notaTrascr.setFlgQuadrodB( '0' );
        if( form.isQuadroB() )
        {
            notaTrascr.setFlgQuadrodB( '1' );
        }
        notaTrascr.setFlgQuadrodC( '0' );
        if( form.isQuadroC() )
        {
            notaTrascr.setFlgQuadrodC( '1' );
        }

        Long idNotaTrascr = conservatorieDao.modificaNotaTrascr( notaTrascr, idNota, idUffOwn );

        return idNotaTrascr;
    }

    public String eliminaNota( Long idNota, Integer idUffOwn )
    {

        String esito = null;
        esito = conservatorieDao.eliminaNota( idNota, idUffOwn );
        return esito;
    }

    @SuppressWarnings( "unused" )
    public Long generaAnnotazione( NotaDettaglioForm form, int idUffOwn, String descUtente )
    {
        TNota nota = new TNota();
        mapperBean.map( form, nota );
        Long oldIdNota = nota.getId().getCodiKIdNota();
        char oldTipoNota = nota.getTTpTipoNota().getCodiKIdTipoNota();
        if( oldTipoNota == 'T' )
        {
            nota.setDescSpecieAtto( "ANNOTAZIONE A TRASCRIZIONE" );
            nota.getTTpTipoComunic().setCodiKTipoComunic( "0700" );
            nota.setDescAtto( "ANNOTAZIONE A TRASCRIZIONE" );
        }
        if( oldTipoNota == 'A' )
        {
            nota.setDescSpecieAtto( "ANNOTAZIONE AD ANNOTAZIONE" );
            nota.getTTpTipoComunic().setCodiKTipoComunic( "0900" );
            nota.setDescAtto( "ANNOTAZIONE AD ANNOTAZIONE" );
        }
        nota.setFlgStato( '0' );
        nota.getTTpTipoNota().setCodiKIdTipoNota( 'A' );
        Long idNotaNew = conservatorieDao.inserisciNota( nota, idUffOwn );

        TNotaAnnot notaAnnot = new TNotaAnnot();
        if( oldTipoNota == 'A' )
        {
            notaAnnot.setDataFormalita( form.getTrascrizioneDel() );
            notaAnnot.setFlgVoltura( form.isVolturaCatastale() ? '1' : '0' );
            notaAnnot.setNumeFormalita1( form.getNumeroTrascrizione1() );
            notaAnnot.setNumeFormalita2( form.getNumeroTrascrizione2() );
            notaAnnot.setNumeValoreImmInt( form.getValoreImmobLib() );
            notaAnnot.setNumeValoreImmDec( form.getValoreImmobLibParteDecimale() );
        } else
        {
            if( oldTipoNota == 'T' )
            {
                notaAnnot.setFlgVoltura( '0' );
                notaAnnot.setDataFormalita( null );
                notaAnnot.setNumeFormalita1( null );
                notaAnnot.setNumeFormalita2( null );
                notaAnnot.setNumeValoreImmInt( null );
                notaAnnot.setNumeValoreImmDec( null );
            }
        }
        notaAnnot.setDescUtente( descUtente );
        Long idNotaAnnot = conservatorieDao.inserisciNotaAnnot( notaAnnot, idNotaNew, idUffOwn );

        /* T_NOTA, T_NOTA_ANNOT, T_NOTA_TRASCR, T_NOTA_UN,  T_NOTA_SOGG, T_NOTA_SOGG_UN, T_NOTA_SOGG_PF, T_NOTA_SOGG_PNF, 
            T_NOTA_BENE,  T_NOTA_SOGG_BENE, T_NOTA_QUADRO_D 
        */
        Integer numRighe = 0;
        numRighe = conservatorieDao.riproponiNotaUn( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSogg( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggPF( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggPNF( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggUn( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaQuadroD( oldIdNota, idNotaNew, idUffOwn, descUtente );

        // controllo se la nota originale ha beni ed in caso positivo li copio per la nuova nota
        Long progr = conservatorieDao.getNotaBene( oldIdNota, idUffOwn );
        if( progr != null )
        {
            String stato = conservatorieDao.inserisciNotaBene( oldIdNota, idNotaNew, idUffOwn, descUtente );
        }
        numRighe = conservatorieDao.riproponiNotaSoggBene( oldIdNota, idNotaNew, idUffOwn, descUtente );
        return idNotaNew;
    }

    @SuppressWarnings( "unused" )
    public Long riproposizione( NotaDettaglioForm form, int idUffOwn, String descUtente )
    {
        TNota nota = new TNota();
        mapperBean.map( form, nota );
        char oldTipoNota = nota.getTTpTipoNota().getCodiKIdTipoNota();
        Long oldIdNota = nota.getId().getCodiKIdNota();
        nota.setFlgStato( '0' );
        nota.getTTpTipoNota().setCodiKIdTipoNota( oldTipoNota );
        Long idNotaNew = conservatorieDao.inserisciNota( nota, idUffOwn ); // immagino che come prima operazione calcola l'ID della nuova Nota
        if( oldTipoNota == 'A' )
        {
            TNotaAnnot notaAnnot = new TNotaAnnot();
            notaAnnot.setDataFormalita( form.getTrascrizioneDel() );
            notaAnnot.setDescUtente( descUtente );
            notaAnnot.setFlgVoltura( form.isVolturaCatastale() ? '1' : '0' );
            notaAnnot.setNumeFormalita1( form.getNumeroTrascrizione1() );
            notaAnnot.setNumeFormalita2( form.getNumeroTrascrizione2() );
            notaAnnot.setNumeValoreImmInt( form.getValoreImmobLib() );
            notaAnnot.setNumeValoreImmDec( form.getValoreImmobLibParteDecimale() );
            notaAnnot.setDescUtente( descUtente );
            Long idNotaAnnot = conservatorieDao.inserisciNotaAnnot( notaAnnot, idNotaNew, idUffOwn );
        } else
        {
            if( oldTipoNota == 'T' )
            {
                TNotaTrascr notaTrascr = new TNotaTrascr();
                notaTrascr.setDescUtente( descUtente );
                notaTrascr.setFlgFoglioInfo( '0' );
                notaTrascr.setDataFormalita( form.getDataFormalita() );
                notaTrascr.setDataVolturaDiff( form.getDataDal() );
                notaTrascr.setNumeFormalita1( form.getNumeroFormalita1() );
                notaTrascr.setNumeFormalita2( form.getNumeroFormalita2() );
                notaTrascr.setFlgVoltura( form.isAttoSoggVoltura() ? '1' : '0' );
                notaTrascr.setFlgVolturaDiff( form.isDifferita() ? '1' : '0' );
                notaTrascr.setFlgCondizione( form.isPresenzaCondizione() ? '1' : '0' );
                notaTrascr.setFlgTermini( form.isPresenzaTermini() ? '1' : '0' );
                notaTrascr.setFlgQuadrodA( form.isQuadroA() ? '1' : '0' );
                notaTrascr.setFlgQuadrodB( form.isQuadroB() ? '1' : '0' );
                notaTrascr.setFlgQuadrodC( form.isQuadroC() ? '1' : '0' );
                Long idNotaTrascr = conservatorieDao.inserisciNotaTrascr( notaTrascr, idNotaNew, idUffOwn );
            }
        }
        /* T_NOTA, T_NOTA_ANNOT, T_NOTA_TRASCR, T_NOTA_UN, T_NOTA_SOGG, T_NOTA_SOGG_PF, T_NOTA_SOGG_PNF, T_NOTA_SOGG_UN, 
        T_NOTA_BENE, T_NOTA_SOGG_BENE, T_NOTA_QUADRO_D   */
        Integer numRighe = 0;
        numRighe = conservatorieDao.riproponiNotaUn( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSogg( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggPF( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggPNF( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaSoggUn( oldIdNota, idNotaNew, idUffOwn, descUtente );
        numRighe = conservatorieDao.riproponiNotaQuadroD( oldIdNota, idNotaNew, idUffOwn, descUtente );
        Long progr = conservatorieDao.getNotaBene( oldIdNota, idUffOwn );
        // controllo se la tabella notaBene ha almeno una riga relativa alla nota da riproporre
        if( progr != null )
        {
            String stato = conservatorieDao.inserisciNotaBene( oldIdNota, idNotaNew, idUffOwn, nota.getDescUtente() );
        }
        numRighe = conservatorieDao.riproponiNotaSoggBene( oldIdNota, idNotaNew, idUffOwn, descUtente );
        return idNotaNew;
    }

    public List<BeneAssociatoDescDto> listaBeniAssociati( Long idNota, int idUffOwn )
    {

        List<BeneAssociatoDescDto> listaBeniAssociati = new ArrayList<BeneAssociatoDescDto>();
        List<BeneAssociatoDto> listaBeniAssociatiDto = conservatorieDao.listaBeniAssociati( idNota, idUffOwn );
        if( listaBeniAssociatiDto.isEmpty() )
        {
            return null;
        }

        Iterator<BeneAssociatoDto> iter = listaBeniAssociatiDto.iterator();
        while( iter.hasNext() )
        {
            BeneAssociatoDescDto currentForm = new BeneAssociatoDescDto();
            BeneAssociatoDto lista = iter.next();
            currentForm.setPROGRESSIVO( lista.getCODI_K_PROGR_BENE() );
            currentForm.setPROGUNEG( null );
            if( lista.getCODI_FK_PROGR_UNEG() != null )
            {
                currentForm.setPROGUNEG( lista.getCODI_FK_PROGR_UNEG() );
            }
            currentForm.setProgUnegBene( null );
            if( lista.getNUME_PROGR_UNEG_BENE() != null )
            {
                currentForm.setProgUnegBene( lista.getNUME_PROGR_UNEG_BENE().longValue() );
            }
            currentForm.setDescrizione( null );
            if( lista.getDESCR_BENE() != null )
            {
                currentForm.setDescrizione( lista.getDESCR_BENE() );
            }
            currentForm.setCatasto( null );
            if( lista.getDESC_CATASTO() != null )
            {
                currentForm.setCatasto( lista.getDESC_CATASTO() );
            }
            currentForm.setComune( null );
            if( lista.getDESC_SEDE_IMMOBILE() != null )
            {
                currentForm.setComune( lista.getDESC_SEDE_IMMOBILE() );
                currentForm.setProvincia( conservatorieDao.getSiglaProvincia( lista.getDESC_SEDE_IMMOBILE() ) );
            }
            currentForm.setBeneSMP( lista.getCODI_FK_ID_BENE().longValue() );
            currentForm.setFlagErrore( lista.getFLG_ERR() );
            if( lista.getCODI_FK_ID_TIPO_CAT() != null )
            {
                currentForm.setCodTipoCatasto( lista.getCODI_FK_ID_TIPO_CAT() );
            }
            if( lista.getCODI_FK_ID_TIPO_CAT_P() != null )
            {
                currentForm.setCodTipoCatastoP( lista.getCODI_FK_ID_TIPO_CAT_P() );
            }
            if( lista.getDESC_SEZIONE() != null )
            {
                currentForm.setDescSezione( lista.getDESC_SEZIONE() );
            }
            if( lista.getDESC_FOGLIO() != null )
            {
                currentForm.setDescFoglio( lista.getDESC_FOGLIO() );
            }
            if( lista.getDESC_PARTICELLA_1() != null )
            {
                currentForm.setDescParticella1( lista.getDESC_PARTICELLA_1() );
            }
            if( lista.getDESC_PARTICELLA_2() != null )
            {
                currentForm.setDescParticella2( lista.getDESC_PARTICELLA_2() );
            }
            if( lista.getDESC_SUBALTERNO_1() != null )
            {
                currentForm.setDescSubalterno1( lista.getDESC_SUBALTERNO_1() );
            }
            if( lista.getDESC_SUBALTERNO_2() != null )
            {
                currentForm.setDescSubalterno2( lista.getDESC_SUBALTERNO_2() );
            }
            if( lista.getDESC_FOGLIO_P() != null )
            {
                currentForm.setDescFoglioP( lista.getDESC_FOGLIO_P() );
            }
            if( lista.getDESC_PARTICELLA_1_P() != null )
            {
                currentForm.setDescParticella1P( lista.getDESC_PARTICELLA_1_P() );
            }
            if( lista.getDESC_TIPO_DENUNCIA() != null )
            {
                currentForm.setDescTipoDenuncia( lista.getDESC_TIPO_DENUNCIA() );
            }
            if( lista.getNUME_DENUNCIA_1() != null )
            {
                currentForm.setNumeDenuncia1( lista.getNUME_DENUNCIA_1() );
            }
            if( lista.getNUME_DENUNCIA_2() != null )
            {
                currentForm.setNumeDenuncia2( lista.getNUME_DENUNCIA_2() );
            }
            if( lista.getNUME_ANNO_DENUNCIA() != null )
            {
                currentForm.setNumeAnnoDenuncia( lista.getNUME_ANNO_DENUNCIA() );
            }
            if( lista.getNUME_GRAFFATI() != null )
            {
                currentForm.setNumeGraffati( lista.getNUME_GRAFFATI() );
            }

            if( lista.getNUME_VANI_INT() != null )
            {
                currentForm.setNumeVaniInt( lista.getNUME_VANI_INT() );
            }
            if( lista.getNUME_VANI_DEC() != null )
            {
                currentForm.setNumeVaniDec( lista.getNUME_VANI_DEC() );
            }
            if( lista.getNUME_MC_INT() != null )
            {
                currentForm.setNumeMcInt( lista.getNUME_MC_INT() );
            }
            if( lista.getNUME_MC_DEC() != null )
            {
                currentForm.setNumeMcDec( lista.getNUME_MC_DEC() );
            }
            if( lista.getNUME_SUP_MQ_INT() != null )
            {
                currentForm.setNumeSupMqInt( lista.getNUME_SUP_MQ_INT() );
            }
            if( lista.getNUME_SUP_MQ_DEC() != null )
            {
                currentForm.setNumeSupMqDec( lista.getNUME_SUP_MQ_DEC() );
            }
            if( lista.getNUME_SUP_ARE() != null )
            {
                currentForm.setNumeSupAre( lista.getNUME_SUP_ARE() );
            }
            if( lista.getNUME_SUP_CENTIARE() != null )
            {
                currentForm.setNumeSupCentiare( lista.getNUME_SUP_CENTIARE() );
            }
            if( lista.getNUME_SUP_ETTARI() != null )
            {
                currentForm.setNumeSupEttari( lista.getNUME_SUP_ETTARI() );
            }
            if( lista.getCODI_FK_ID_CATEG_CAT() != null )
            {
                currentForm.setDescrBreveCat( conservatorieDao.descBreveCatCatasto( lista.getCODI_FK_ID_CATEG_CAT().intValue() ) );
            }

            if( lista.getNUME_CAPITALE_INT() != null )
            {
                currentForm.setNumeCapitaleInt( lista.getNUME_CAPITALE_INT() );
            }
            if( lista.getNUME_CAPITALE_DEC() != null )
            {
                currentForm.setNumeCapitaleDec( lista.getNUME_CAPITALE_DEC() );
            }

            if( lista.getDESC_INDIRIZZO() != null )
            {
                currentForm.setDescIndirizzo( lista.getDESC_INDIRIZZO() );
            }
            if( lista.getDESC_CIVICO() != null )
            {
                currentForm.setDescCivico( lista.getDESC_CIVICO() );
            }
            if( lista.getDESC_LOTTO() != null )
            {
                currentForm.setDescLotto( lista.getDESC_LOTTO() );
            }
            if( lista.getDESC_EDIFICIO() != null )
            {
                currentForm.setDescEdificio( lista.getDESC_EDIFICIO() );
            }
            if( lista.getDESC_SCALA() != null )
            {
                currentForm.setDescScala( lista.getDESC_SCALA() );
            }
            if( lista.getDESC_INTERNO() != null )
            {
                currentForm.setDescInterno( lista.getDESC_INTERNO() );
            }
            if( lista.getDESC_PIANO() != null )
            {
                currentForm.setDescPiano( lista.getDESC_PIANO() );
            }
            if( lista.getDESC_CATASTO_P() != null )
            {
                currentForm.setDescCatastoP( lista.getDESC_CATASTO_P() );
            }
            if( lista.getCODI_FK_ID_FONDO() != null )
            {
                currentForm.setCodiFkIdFondo( lista.getCODI_FK_ID_FONDO() );
            }
            currentForm.setTipoTerreno( conservatorieDao.getTipoTerreno( lista.getCODI_FK_ID_BENE().longValue(), idUffOwn ) );
            listaBeniAssociati.add( currentForm );
        }

        return listaBeniAssociati;
    }

    public List<BeneAssociabileDto> listaBeniAssociabili(
        Long idNota,
        int idUffOwn,
        Integer tipoAtto,
        Integer catAtto,
        Integer repAnno,
        Integer repNumero,
        Long idProced,
        Short idConserv,
        Character tipoNota )
    {

        List<BeneAssociabileDto> listaBeniAssociabiliDto = null;
        if( tipoNota.equals( 'A' ) )
        {
            // richiamo la query per nota annotazione
            listaBeniAssociabiliDto = conservatorieDao.listaBeniAssociabiliA( idNota, idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced,
                idConserv );
        }
        if( tipoNota.equals( 'T' ) )
        {
            Character decisorio = conservatorieDao.getFlagDecisorio( tipoAtto, catAtto );
            if( decisorio.equals( 'D' ) )
            {
                // richiamo la query per nota trascrizione atti decisori 1 e 2 grado
                listaBeniAssociabiliDto = conservatorieDao.listaBeniAssociabiliTD( idNota, idUffOwn, tipoAtto, catAtto, repAnno, repNumero,
                    idProced, idConserv );
            } else
                if( decisorio.equals( 'S' ) )
                {
                    // richiamo la query per nota trascrizione sequestro
                    listaBeniAssociabiliDto = conservatorieDao.listaBeniAssociabiliTS( idNota, idUffOwn, tipoAtto, catAtto, repAnno, repNumero,
                        idProced, idConserv );
                }
        }

        return listaBeniAssociabiliDto;
    }

    public List<String> cercaOsservazioniNota( Integer idUffOwn, Long idNota, Character tipoInfo )
    {
        List<String> lista = conservatorieDao.cercaOsservazioniNota( idUffOwn, idNota, tipoInfo );
        return lista;
    }

    public Long inserisciQuadroD(
        Integer idUffOwn, String descUtente, Long idNota, Character tipoInf, String quadroD, Character stato )
    {
        if( quadroD.length() == 0 )
            return null;
        @SuppressWarnings( "unused" )
        String esito = null;
        List<String> lista = conservatorieDao.cercaOsservazioniNota( idUffOwn, idNota, tipoInf );
        if( lista != null && !lista.isEmpty() )
        {
            esito = conservatorieDao.cancellaQuadroD( idUffOwn, idNota, tipoInf );
            //            if( !esito.equals( "OK" ) )
            //                return null;
        }
        Long idNotaRet = null;
        String blocco;
        int i = 0;
        int progr = 1;
        while( i < quadroD.length() )
        {
            if( quadroD.length() - i > 490 )
            {
                blocco = quadroD.substring( i, i + 489 );
                i += 490;
            } else
            {
                blocco = quadroD.substring( i );
                i = quadroD.length();
            }
            idNotaRet = conservatorieDao.inserisciQuadroD( idUffOwn, descUtente, idNota, tipoInf, blocco, progr );
            progr++;
        }
        if( stato == '1' )
        {
            esito = conservatorieDao.aggiornaStatoNota( idUffOwn, descUtente, idNota, new Character( '0' ) ); // stato passa da 1 a 0
            //            if( !esito.equals( "OK" ) )
            //                return null;
        }
        return idNotaRet;
    }

    public String cancellaQuadroD( Integer idUffOwn, String descUtente, Long idNota, Character tipoInf, Character stato )
    {
        String esito = null;
        esito = conservatorieDao.cancellaQuadroD( idUffOwn, idNota, tipoInf );
        //        if( !esito.equals( "OK" ) )
        //            return esito;
        if( stato == '1' )
        {
            esito = conservatorieDao.aggiornaStatoNota( idUffOwn, descUtente, idNota, new Character( '0' ) ); // stato passa da 1 a 0
        }
        return esito;
    }

    public ComunicazioneBeniForm dettaglioNotaBene( ComunicazioneBeniForm form, Long idNota, Integer idUffOwn )
    {
        Short progrNotaBene = Short.valueOf( form.getBeneSelected() );
        BeneAssociatoDto beneDto = conservatorieDao.dettaglioNotaBene( progrNotaBene, idNota, idUffOwn );
        completaForm( form, beneDto );
        return form;
    }

    public List<TitoloDirittoDto> listaTitoliDiritto( ComunicazioneBeniForm form, Long idNota, Integer idUffOwn )
    {
        Short progrNotaBene = Short.valueOf( form.getBeneSelected() );
        List<TitoloDirittoDto> listaDto = conservatorieDao.listaTitoliDiritto( progrNotaBene, idNota, idUffOwn );
        //completaForm( form, titoloDto );
        for( TitoloDirittoDto t : listaDto )
        {
            if( t.getNUMEQUOTADEC().longValue() > 0 )
            {
                String millesimi = ( "000" + t.getNUMEQUOTADEC() ); 
                millesimi = millesimi.substring( millesimi.length() - 3 );
                t.setNUMEQUOTA( "" + t.getNUMEQUOTAINT() + "," + millesimi + "/" + t.getNUMEQUOTADEN() );
            }else
                t.setNUMEQUOTA( "" + t.getNUMEQUOTAINT() + "/" + t.getNUMEQUOTADEN() );        }
        return listaDto;
    }

    public ComunicazioneBeniForm titoloDiritto( ComunicazioneBeniForm form, Long idNota, Integer idUffOwn )
    {
        Short progrNotaBene = Short.valueOf( form.getBeneSelected() );
        Short titoloSel = Short.valueOf( form.getTitoloDirittoSelected() );
        Integer idTipoInteresse = Integer.valueOf( form.getIdTipoInt() );
        TitoloDirittoDto titoloDto = new TitoloDirittoDto();

        if( conservatorieDao.getTipoSogg( Short.valueOf( form.getBeneSelected() ), idNota, idUffOwn, titoloSel ).equals( 'F' ) )
            titoloDto = conservatorieDao.titoloDirittoPF( progrNotaBene, idNota, idUffOwn, titoloSel, idTipoInteresse );
        else
            titoloDto = conservatorieDao.titoloDirittoPG( progrNotaBene, idNota, idUffOwn, titoloSel, idTipoInteresse );
        completaFormModifica( form, titoloDto );
        return form;
    }

    private void completaFormModifica( ComunicazioneBeniForm form, TitoloDirittoDto titoloDto )
    {
        if( titoloDto.getSOGGETTOSITMP() != null )
            form.setSoggettoSitMp( titoloDto.getSOGGETTOSITMP().longValue() );
        if( titoloDto.getCOGNOME() != null )
            form.setCognome( titoloDto.getCOGNOME() );
        if( titoloDto.getNOME() != null )
            form.setNome( titoloDto.getNOME() );
        if( titoloDto.getDENOMINAZIONE() != null )
            form.setDenominazione( titoloDto.getDENOMINAZIONE() );
        if( titoloDto.getDESCTIPOINT() != null )
            form.setDescTipoInt( titoloDto.getDESCTIPOINT() );
        if( titoloDto.getNUMEQUOTAINT() != null )
            form.setNumeQuotaInt( titoloDto.getNUMEQUOTAINT().longValue() );
        if( titoloDto.getNUMEQUOTADEN() != null )
            form.setNumeQuotaDen( titoloDto.getNUMEQUOTADEN().longValue() );
        if( titoloDto.getNUMEQUOTADEC() != null )
            form.setNumeQuotaDec( titoloDto.getNUMEQUOTADEC().longValue() );
        form.setTipoSogg( titoloDto.getTIPOSOGG().toString() );
        form.setIdTipoInt( titoloDto.getIDTIPOINT().intValue() );

    }

    @SuppressWarnings( "unused" )
    public Integer modificaTitoloDiritto( ComunicazioneBeniForm form, Long idNota, Integer idUffOwn, String utente )
    {
        Integer righe = null;
        Short progrNotaBene = Short.valueOf( form.getBeneSelected() );
        Short titoloSel = Short.valueOf( form.getTitoloDirittoSelected() );
        Long quotaNum = Long.valueOf( form.getNumeQuotaInt() );
        Long quotaDec = Long.valueOf( form.getNumeQuotaDec() );
        Long quotaDen = Long.valueOf( form.getNumeQuotaDen() );
        Integer idTipoInt = form.getIdTipoInt();
        righe = conservatorieDao.modificaTitoloDiritto(
            progrNotaBene, idNota, idUffOwn, titoloSel, utente, quotaNum, quotaDec, quotaDen, idTipoInt );
        if( form.getStato().equals( "1" ) )
        {
            String rit = conservatorieDao.updateStatoNota( idNota, idUffOwn, utente );
        }
        return righe;
    }

    private void completaForm( ComunicazioneBeniForm form, BeneAssociatoDto beneDto )
    {

        form.setStato( beneDto.getFLG_STATO() );
        if( beneDto.getCODI_FK_ID_BENE() != null )
            form.setBeneSitMp( beneDto.getCODI_FK_ID_BENE().longValue() );

        if( beneDto.getDESCR_BENE() != null )
            form.setDescBene( beneDto.getDESCR_BENE() );

        if( beneDto.getDESC_SEDE_IMMOBILE() != null )
            form.setDescComuneBene( beneDto.getDESC_SEDE_IMMOBILE() );

        if( beneDto.getDESC_CATASTO() != null )
        {
            if( beneDto.getDESC_CATASTO().length() > 4 )
            {
                form.setCodiceCatastale1( beneDto.getDESC_CATASTO().substring( 0, 4 ) );
                form.setCodiceCatastale2( beneDto.getDESC_CATASTO().substring( 4, 5 ) );
            } else
            {
                form.setCodiceCatastale1( beneDto.getDESC_CATASTO() );
                form.setCodiceCatastale2( null );
            }
        }

        if( beneDto.getCODI_FK_ID_TIPO_CAT() != null )
        {
            form.setTipoCatasto( beneDto.getCODI_FK_ID_TIPO_CAT() );
            form.setDescTipoCatasto( conservatorieDao.descTipoCatasto( beneDto.getCODI_FK_ID_TIPO_CAT() ) );
        }

        if( beneDto.getCODI_FK_ID_FONDO() != null )
        {
            form.setDescrFondo( conservatorieDao.descTipoFondo( beneDto.getCODI_FK_ID_FONDO() ) );
            form.setFondo( beneDto.getCODI_FK_ID_FONDO() );
        }

        if( beneDto.getNUME_GRAFFATI() != null )
            form.setNumeGraffati( beneDto.getNUME_GRAFFATI().longValue() );

        if( beneDto.getCODI_FK_ID_CATEG_CAT() != null )
        {
            form.setDescrNaturaImmobile( conservatorieDao.descCatCatasto( beneDto.getCODI_FK_ID_CATEG_CAT().intValue() ) );
            form.setIdNaturaImmobile( beneDto.getCODI_FK_ID_CATEG_CAT().intValue() );
        }

        if( beneDto.getNUME_SUP_ETTARI() != null )
            form.setNumeSupEttari( beneDto.getNUME_SUP_ETTARI().intValue() );

        if( beneDto.getNUME_SUP_ARE() != null )
            form.setNumeSupAre( beneDto.getNUME_SUP_ARE().intValue() );

        if( beneDto.getNUME_SUP_CENTIARE() != null )
            form.setNumeSupCentiare( beneDto.getNUME_SUP_CENTIARE().intValue() );

        if( beneDto.getNUME_VANI_INT() != null )
            form.setNumeVaniInt( beneDto.getNUME_VANI_INT().shortValue() );

        if( beneDto.getNUME_VANI_DEC() != null )
            form.setNumeVaniDec( beneDto.getNUME_VANI_DEC().shortValue() );

        if( beneDto.getNUME_MC_INT() != null )
            form.setNumeMcInt( beneDto.getNUME_MC_INT().shortValue() );

        if( beneDto.getNUME_MC_DEC() != null )
            form.setNumeMcDec( beneDto.getNUME_MC_DEC().shortValue() );

        if( beneDto.getNUME_SUP_MQ_INT() != null )
            form.setNumeSupMqInt( beneDto.getNUME_SUP_MQ_INT().shortValue() );

        if( beneDto.getNUME_SUP_MQ_DEC() != null )
            form.setNumeSupMqDec( beneDto.getNUME_SUP_MQ_DEC().shortValue() );

        if( beneDto.getDESC_INDIRIZZO() != null )
            form.setDescIndirizzo( beneDto.getDESC_INDIRIZZO() );

        if( beneDto.getDESC_CIVICO() != null )
            form.setDescCivico( beneDto.getDESC_CIVICO() );

        if( beneDto.getDESC_SCALA() != null )
            form.setDescScala( beneDto.getDESC_SCALA() );

        if( beneDto.getDESC_INTERNO() != null )
            form.setDescInterno( beneDto.getDESC_INTERNO() );

        if( beneDto.getDESC_PIANO() != null )
            form.setDescPiano( beneDto.getDESC_PIANO() );

        if( beneDto.getDESC_LOTTO() != null )
            form.setDescLotto( beneDto.getDESC_LOTTO() );

        if( beneDto.getDESC_SEZIONE() != null )
            form.setDescSezione( beneDto.getDESC_SEZIONE() );

        if( beneDto.getDESC_FOGLIO() != null )
            form.setDescFoglio( beneDto.getDESC_FOGLIO() );

        if( beneDto.getDESC_PARTICELLA_1() != null )
            form.setDescParticella1( beneDto.getDESC_PARTICELLA_1() );

        if( beneDto.getDESC_PARTICELLA_2() != null )
            form.setDescParticella2( beneDto.getDESC_PARTICELLA_2() );

        if( beneDto.getDESC_SUBALTERNO_1() != null )
            form.setDescSubalterno1( beneDto.getDESC_SUBALTERNO_1() );

        if( beneDto.getDESC_SUBALTERNO_2() != null )
            form.setDescSubalterno2( beneDto.getDESC_SUBALTERNO_2() );

        String descTipoDenuncia = null;
        if( beneDto.getDESC_TIPO_DENUNCIA() != null )
        {
            form.setTipoDenuncia( beneDto.getDESC_TIPO_DENUNCIA().charValue() );
            descTipoDenuncia = conservatorieDao.descTipoDenuncia( beneDto.getDESC_TIPO_DENUNCIA() );
            form.setDescTipoDenuncia( descTipoDenuncia );
        }

        if( beneDto.getNUME_DENUNCIA_1() != null )
            form.setNumeDenuncia1( beneDto.getNUME_DENUNCIA_1().intValue() );

        if( beneDto.getNUME_DENUNCIA_2() != null )
            form.setNumeDenuncia2( beneDto.getNUME_DENUNCIA_2().shortValue() );

        if( beneDto.getNUME_ANNO_DENUNCIA() != null )
            form.setNumeAnnoDenuncia( beneDto.getNUME_ANNO_DENUNCIA().shortValue() );

        /* Dati di origine */
        if( beneDto.getDESC_CATASTO_P() != null )
        {
            if( beneDto.getDESC_CATASTO_P().length() > 4 )
            {
                form.setCodiceCatastale1P( beneDto.getDESC_CATASTO_P().substring( 0, 4 ) );
                form.setCodiceCatastale2P( beneDto.getDESC_CATASTO_P().substring( 4, 5 ) );
            } else
            {
                form.setCodiceCatastale1P( beneDto.getDESC_CATASTO_P() );
                form.setCodiceCatastale2P( null );
            }
        }
        if( beneDto.getDESC_COMUNE_P() != null )
        {
            form.setDescComuneBeneP( beneDto.getDESC_COMUNE_P() );
        }
        if( beneDto.getCODI_FK_ID_TIPO_CAT_P() != null )
        {
            form.setTipoCatastoP( beneDto.getCODI_FK_ID_TIPO_CAT_P() );
        }

        if( beneDto.getDESC_SEZIONE_P() != null )
            form.setDescSezioneP( beneDto.getDESC_SEZIONE_P() );

        if( beneDto.getDESC_FOGLIO_P() != null )
            form.setDescFoglioP( beneDto.getDESC_FOGLIO_P() );

        if( beneDto.getDESC_PARTICELLA_1_P() != null )
            form.setDescParticella1P( beneDto.getDESC_PARTICELLA_1_P() );

        if( beneDto.getDESC_PARTICELLA_2_P() != null )
            form.setDescParticella2P( beneDto.getDESC_PARTICELLA_2_P() );

        if( beneDto.getDESC_SUBALTERNO_1_P() != null )
            form.setDescSubalterno1P( beneDto.getDESC_SUBALTERNO_1_P() );

        if( beneDto.getDESC_SUBALTERNO_2_P() != null )
            form.setDescSubalterno2P( beneDto.getDESC_SUBALTERNO_2_P() );

        String descTipoDenunciaP = null;
        if( beneDto.getDESC_TIPO_DENUNCIA_P() != null )
        {
            form.setTipoDenunciaP( beneDto.getDESC_TIPO_DENUNCIA_P().charValue() );
            descTipoDenunciaP = conservatorieDao.descTipoDenuncia( beneDto.getDESC_TIPO_DENUNCIA_P() );
            form.setDescTipoDenunciaP( descTipoDenunciaP );
        }

        if( beneDto.getNUME_DENUNCIA_1_P() != null )
            form.setNumeDenuncia1P( beneDto.getNUME_DENUNCIA_1_P().intValue() );

        if( beneDto.getNUME_DENUNCIA_2_P() != null )
            form.setNumeDenuncia2P( beneDto.getNUME_DENUNCIA_2_P().shortValue() );

        if( beneDto.getNUME_ANNO_DENUNCIA_P() != null )
            form.setNumeAnnoDenunciaP( beneDto.getNUME_ANNO_DENUNCIA_P().shortValue() );
    }

    public String cancellaNotaBene( Long idNota, Integer idUffOwn, Long progrNotaBene, String descUtente )
    {

        String esito = null;

        esito = conservatorieDao.cancellaNotaSoggBene( idNota, idUffOwn, progrNotaBene );
        esito = conservatorieDao.cancellaNotaBene( idNota, idUffOwn, progrNotaBene );
        esito = conservatorieDao.updateStatoNota( idNota, idUffOwn, descUtente );

        return esito;
    }

    public String updateNotaBene( ComunicazioneBeniForm form, Long idNota, Integer idUffOwn, String descUtente )
    {

        String esito = null;
        TNotaBene notaBene = new TNotaBene();
        //      mapperBean.map( form, notaBene );
        completaNotaBeneUpdate( form, notaBene, idNota, idUffOwn, descUtente );
        String codCatasto = form.getCodiceCatastale1();
        esito = conservatorieDao.updateNotaBene( notaBene, descUtente, codCatasto );

        if( form.getStato() == '1' )
        {
            esito = conservatorieDao.aggiornaStatoNota( idUffOwn, descUtente, idNota, new Character( '0' ) ); // stato passa da 1 a 0
            //            if( !esito.equals( "OK" ) )
            //                return null;
        }

        return esito;
    }

    private void completaNotaBeneUpdate( ComunicazioneBeniForm form, TNotaBene notaBene, Long idNota, Integer idUffOwn, String descUtente )
    {
        TNotaBeneId notaBeneId = new TNotaBeneId();
        notaBeneId.setCodiKIdNota( idNota );
        notaBeneId.setCodiKIdUffOwner( idUffOwn );
        notaBeneId.setCodiKProgrBene( Short.valueOf( form.getBeneSelected() ) );
        notaBene.setId( notaBeneId );

        TBene bene = new TBene();
        TBeneId beneId = new TBeneId();
        beneId.setCodiKIdBene( form.getBeneSitMp() );
        beneId.setCodiKIdUffOwner( idUffOwn );
        bene.setId( beneId );
        notaBene.setTBene( bene );

        notaBene.setNumeProgrUnegBene( (short)0 );

        TNotaUn notaUn = new TNotaUn();
        TNotaUnId notaUnId = new TNotaUnId();
        notaUnId.setCodiKIdNota( idNota );
        notaUnId.setCodiKIdUffOwner( idUffOwn );
        notaUnId.setCodiKProgrUneg( (short)0 );
        notaUn.setId( notaUnId );
        notaBene.setTNotaUn( notaUn );

        notaBene.setDescSedeImmobile( form.getDescSedeImmobile() );
        notaBene.setDescCatasto( form.getDescCatasto() );
        notaBene.setDescCatastoP( form.getDescCatastoP() );

        TTpTipoCatasto tipoCat = new TTpTipoCatasto();
        tipoCat.setCodiKIdTipoCat( form.getTipoCatasto() );
        notaBene.setTTpTipoCatastoByCodiFkIdTipoCat( tipoCat );

        //        tipoCat = null;
        if( form.getTipoCatastoP() != null )
        {
            tipoCat.setCodiKIdTipoCat( form.getTipoCatastoP() );
        } else
        {
            tipoCat = null;
        }
        notaBene.setTTpTipoCatastoByCodiFkIdTipoCatP( tipoCat );
        notaBene.setDescSezione( form.getDescSezione() );
        notaBene.setDescSezioneP( form.getDescSezioneP() );
        notaBene.setDescFoglio( form.getDescFoglio() );
        notaBene.setDescFoglioP( form.getDescFoglioP() );
        notaBene.setDescParticella1( form.getDescParticella1() );
        notaBene.setDescParticella1P( form.getDescParticella1P() );
        notaBene.setDescParticella2( form.getDescParticella2() );
        notaBene.setDescParticella2P( form.getDescParticella2P() );
        notaBene.setDescSubalterno1( form.getDescSubalterno1() );
        notaBene.setDescSubalterno1P( form.getDescSubalterno1P() );
        notaBene.setDescSubalterno2( form.getDescSubalterno2() );
        notaBene.setDescSubalterno2P( form.getDescSubalterno2P() );
        notaBene.setDescTipoDenuncia( form.getTipoDenuncia() );

        if( form.getTipoDenunciaP() != null )
        {
            notaBene.setDescTipoDenunciaP( form.getTipoDenunciaP() );
        }

        notaBene.setNumeDenuncia1( form.getNumeDenuncia1() == null ? null : form.getNumeDenuncia1().intValue() );
        notaBene.setNumeDenuncia1P( form.getNumeDenuncia1P() == null ? null : form.getNumeDenuncia1P().intValue() );
        notaBene.setNumeDenuncia2( form.getNumeDenuncia2() == null ? null : form.getNumeDenuncia2().shortValue() );
        notaBene.setNumeDenuncia2P( form.getNumeDenuncia2P() == null ? null : form.getNumeDenuncia2P().shortValue() );
        notaBene.setNumeAnnoDenuncia( form.getNumeAnnoDenuncia() == null ? null : form.getNumeAnnoDenuncia().shortValue() );
        notaBene.setNumeAnnoDenunciaP( form.getNumeAnnoDenunciaP() == null ? null : form.getNumeAnnoDenunciaP().shortValue() );
        notaBene.setNumeGraffati( form.getNumeGraffati() == null ? null : form.getNumeGraffati().longValue() );

        if( form.getFondo() != null )
        {
            TTpTipoFondo tipoFondo = new TTpTipoFondo();
            tipoFondo.setCodiKIdFondo( form.getFondo() );
            notaBene.setTTpTipoFondo( tipoFondo );
        }

        notaBene.setNumeSupEttari( form.getNumeSupEttari() == null ? null : form.getNumeSupEttari().intValue() );
        notaBene.setNumeSupAre( form.getNumeSupAre() == null ? null : form.getNumeSupAre().intValue() );
        notaBene.setNumeSupCentiare( form.getNumeSupCentiare() == null ? null : form.getNumeSupCentiare().intValue() );
        notaBene.setNumeSupMqInt( form.getNumeSupMqInt() == null ? null : form.getNumeSupMqInt().shortValue() );
        notaBene.setNumeSupMqDec( form.getNumeSupMqDec() == null ? null : form.getNumeSupMqDec().shortValue() );
        notaBene.setNumeVaniInt( form.getNumeVaniInt() == null ? null : form.getNumeVaniInt().shortValue() );
        notaBene.setNumeVaniDec( form.getNumeVaniDec() == null ? null : form.getNumeVaniDec().shortValue() );
        notaBene.setNumeMcInt( form.getNumeMcInt() == null ? null : form.getNumeMcInt().shortValue() );
        notaBene.setNumeMcDec( form.getNumeMcDec() == null ? null : form.getNumeMcDec().shortValue() );

        TTpCatCatasto catCatasto = new TTpCatCatasto();
        //      catCatasto.setCodiKIdCategCat(form.getCategoriaCatastale().getId() == null ? null : Integer.valueOf(form.getCategoriaCatastale().getId()));
        catCatasto.setCodiKIdCategCat( form.getIdNaturaImmobile() == null ? null : form.getIdNaturaImmobile() );
        notaBene.setTTpCatCatasto( catCatasto );

        notaBene.setDescIndirizzo( form.getDescIndirizzo() );
        notaBene.setDescCivico( form.getDescCivico() );
        notaBene.setDescLotto( form.getDescLotto() );
        notaBene.setDescEdificio( form.getDescEdificio() );
        notaBene.setDescScala( form.getDescScala() );
        notaBene.setDescInterno( form.getDescInterno() );
        notaBene.setDescPiano( form.getDescPiano() );

        String esito = controllaCampi( notaBene );
        if( esito.equals( "OK" ) )
        {
            notaBene.setFlgErr( '0' );
        } else
        {
            notaBene.setFlgErr( '1' );
        }

        notaBene.setDescUtente( descUtente );
    }

    public String associaNotaBene( Long idNota, Integer idUffOwn, Long idBene, String descUtente )
    {

        String esito = null;
        TNotaBene notaBene = new TNotaBene();
        BeneDaAssociareDto beneDaAssociare = new BeneDaAssociareDto();
        beneDaAssociare = conservatorieDao.beneDaAssociare( idBene, idUffOwn );
        completaNotaBeneInsert( beneDaAssociare, notaBene, idNota, idUffOwn, descUtente );

        esito = conservatorieDao.associaNotaBene( notaBene );

        return esito;
    }

    private void
        completaNotaBeneInsert( BeneDaAssociareDto beneDaAssociare, TNotaBene notaBene, Long idNota, Integer idUffOwn, String descUtente )
    {

        TNotaBeneId notaBeneId = new TNotaBeneId();
        notaBeneId.setCodiKIdNota( idNota );
        notaBeneId.setCodiKIdUffOwner( idUffOwn );
        Long progr = conservatorieDao.getNotaBene( idNota, idUffOwn );
        if( progr != null )
        {
            progr = progr + 1;
        } else
        {
            progr = (long)1;
        }
        notaBeneId.setCodiKProgrBene( progr.shortValue() );
        notaBene.setId( notaBeneId );

        TBene bene = new TBene();
        TBeneId beneId = new TBeneId();
        beneId.setCodiKIdBene( beneDaAssociare.getCODI_K_ID_BENE_IMM() == null ? null : beneDaAssociare.getCODI_K_ID_BENE_IMM().longValue() );
        beneId.setCodiKIdUffOwner( idUffOwn );
        bene.setId( beneId );
        notaBene.setTBene( bene );

        notaBene.setNumeProgrUnegBene( (short)0 );

        TNotaUn notaUn = new TNotaUn();
        TNotaUnId notaUnId = new TNotaUnId();
        notaUnId.setCodiKIdNota( idNota );
        notaUnId.setCodiKIdUffOwner( idUffOwn );
        //        notaUnId.setCodiKProgrUneg( (short)0 );
        notaUn.setId( notaUnId );
        notaBene.setTNotaUn( notaUn );

        notaBene.setDescSedeImmobile( beneDaAssociare.getDESC_COMUNE() );
        notaBene.setDescCatasto( beneDaAssociare.getDESC_CATASTO() );
        //GP inserita in data 13/04/2015 per valorizzare il catasto quando è presente solo la sede
        if( beneDaAssociare.getDESC_COMUNE() != null && beneDaAssociare.getDESC_CATASTO() == null )
        {
            notaBene.setDescCatasto( beneDaAssociare.getDESC_CATASTO_COMUNE() );
        }
        notaBene.setDescCatastoP( beneDaAssociare.getDESC_CATASTO_P() );

        if( beneDaAssociare.getCODI_FK_ID_TIPO_CAT() != null )
        {
            TTpTipoCatasto tipoCat = new TTpTipoCatasto();
            tipoCat.setCodiKIdTipoCat( beneDaAssociare.getCODI_FK_ID_TIPO_CAT() );
            notaBene.setTTpTipoCatastoByCodiFkIdTipoCat( tipoCat );
        }
        if( beneDaAssociare.getCODI_FK_ID_TIPO_CAT_P() != null )
        {
            TTpTipoCatasto tipoCatP = new TTpTipoCatasto();
            tipoCatP.setCodiKIdTipoCat( beneDaAssociare.getCODI_FK_ID_TIPO_CAT_P() );
            notaBene.setTTpTipoCatastoByCodiFkIdTipoCatP( tipoCatP );
        }

        if( ( beneDaAssociare.getDESC_SEZIONE() != null && beneDaAssociare.getDESC_SEZIONE().length() > 3 )
            || beneDaAssociare.getCODI_FK_ID_TIPO_CAT() == 'T' )
        {
            notaBene.setDescSezione( null );
        } else
        {
            notaBene.setDescSezione( beneDaAssociare.getDESC_SEZIONE() );
        }
        notaBene.setDescSezioneP( beneDaAssociare.getDESC_SEZIONE_P() );
        notaBene.setDescFoglio( beneDaAssociare.getDESC_FOGLIO() );
        notaBene.setDescFoglioP( beneDaAssociare.getDESC_FOGLIO_P() );
        notaBene.setDescParticella1( beneDaAssociare.getDESC_PARTICELLA_1() );
        notaBene.setDescParticella1P( beneDaAssociare.getDESC_PARTICELLA_1_P() );
        notaBene.setDescParticella2( beneDaAssociare.getDESC_PARTICELLA_2() );
        notaBene.setDescParticella2P( beneDaAssociare.getDESC_PARTICELLA_2_P() );
        notaBene.setDescSubalterno1( beneDaAssociare.getDESC_SUB_PARTICELLA_1() );
        notaBene.setDescSubalterno1P( beneDaAssociare.getDESC_SUBALTERNO_1_P() );
        notaBene.setDescSubalterno2( beneDaAssociare.getDESC_SUB_PARTICELLA_2() );
        notaBene.setDescSubalterno2P( beneDaAssociare.getDESC_SUBALTERNO_2_P() );
        notaBene.setDescTipoDenuncia( beneDaAssociare.getCODI_FK_ID_TIPO_DENUNCIA() );
        notaBene.setDescTipoDenunciaP( beneDaAssociare.getCODI_FK_ID_TIPO_DENUNCIA_P() );

        notaBene.setNumeDenuncia1( beneDaAssociare.getNUME_DENUNCIA_1() == null ? null : beneDaAssociare.getNUME_DENUNCIA_1().intValue() );
        notaBene.setNumeDenuncia1P( beneDaAssociare.getNUME_DENUNCIA_1_P() == null ? null : beneDaAssociare.getNUME_DENUNCIA_1_P().intValue() );
        notaBene.setNumeDenuncia2( beneDaAssociare.getNUME_DENUNCIA_2() == null ? null : beneDaAssociare.getNUME_DENUNCIA_2().shortValue() );
        notaBene.setNumeDenuncia2P( beneDaAssociare.getNUME_DENUNCIA_2_P() == null ? null : beneDaAssociare.getNUME_DENUNCIA_2_P().shortValue() );
        notaBene.setNumeAnnoDenuncia( beneDaAssociare.getNUME_ANNO_DENUNCIA() == null ? null : beneDaAssociare.getNUME_ANNO_DENUNCIA()
            .shortValue() );
        notaBene.setNumeAnnoDenunciaP( beneDaAssociare.getNUME_ANNO_DENUNCIA_P() == null ? null : beneDaAssociare.getNUME_ANNO_DENUNCIA_P()
            .shortValue() );
        notaBene.setNumeGraffati( beneDaAssociare.getNUME_GRAFFATI() == null ? null : beneDaAssociare.getNUME_GRAFFATI().longValue() );

        TTpTipoFondo tipoFondo = new TTpTipoFondo();
        if( beneDaAssociare.getCODI_FK_ID_FONDO() != null )
        {
            tipoFondo.setCodiKIdFondo( beneDaAssociare.getCODI_FK_ID_FONDO() );
            notaBene.setTTpTipoFondo( tipoFondo );
        }
        //		else {
        //			Character fondo = '\u0000';
        //			tipoFondo.setCodiKIdFondo(fondo);
        //			notaBene.setTTpTipoFondo(tipoFondo);
        //			
        //		}

        notaBene.setNumeSupEttari( beneDaAssociare.getNUME_SUP_ETTARI() == null ? null : beneDaAssociare.getNUME_SUP_ETTARI().intValue() );
        notaBene.setNumeSupAre( beneDaAssociare.getNUME_SUP_ARE() == null ? null : beneDaAssociare.getNUME_SUP_ARE().intValue() );
        notaBene.setNumeSupCentiare( beneDaAssociare.getNUME_SUP_CENTIARE() == null ? null : beneDaAssociare.getNUME_SUP_CENTIARE().intValue() );
        notaBene.setNumeSupMqInt( beneDaAssociare.getNUME_SUP_MQ_INT() == null ? null : beneDaAssociare.getNUME_SUP_MQ_INT().shortValue() );
        notaBene.setNumeSupMqDec( beneDaAssociare.getNUME_SUP_MQ_DEC() == null ? null : beneDaAssociare.getNUME_SUP_MQ_DEC().shortValue() );
        notaBene.setNumeVaniInt( beneDaAssociare.getNUME_VANI_INT() == null ? null : beneDaAssociare.getNUME_VANI_INT().shortValue() );
        notaBene.setNumeVaniDec( beneDaAssociare.getNUME_VANI_DEC() == null ? null : beneDaAssociare.getNUME_VANI_DEC().shortValue() );
        notaBene.setNumeMcInt( beneDaAssociare.getNUME_MC_INT() == null ? null : beneDaAssociare.getNUME_MC_INT().shortValue() );
        notaBene.setNumeMcDec( beneDaAssociare.getNUME_MC_DEC() == null ? null : beneDaAssociare.getNUME_MC_DEC().shortValue() );

        if( beneDaAssociare.getCODI_FK_ID_CATEGORIA_CAT() != null )
        {
            TTpCatCatasto catCatasto = new TTpCatCatasto();
            catCatasto.setCodiKIdCategCat( beneDaAssociare.getCODI_FK_ID_CATEGORIA_CAT().intValue() );
            notaBene.setTTpCatCatasto( catCatasto );
        }

        notaBene.setDescIndirizzo( beneDaAssociare.getDESC_INDIRIZZO() );
        notaBene.setDescCivico( beneDaAssociare.getDESC_CIVICO() );
        notaBene.setDescLotto( beneDaAssociare.getDESC_LOTTO() );
        notaBene.setDescEdificio( beneDaAssociare.getDESC_EDIFICIO() );
        notaBene.setDescScala( beneDaAssociare.getDESC_SCALA() );
        notaBene.setDescInterno( beneDaAssociare.getDESC_INTERNO() );
        notaBene.setDescPiano( beneDaAssociare.getDESC_PIANO() );

        String esito = controllaCampi( notaBene );
        if( esito.equals( "OK" ) )
        {
            notaBene.setFlgErr( '0' );
        } else
        {
            notaBene.setFlgErr( '1' );
        }

    }

    private String controllaCampi( TNotaBene notaBene )
    {

        String esito = "OK";

        if( notaBene.getDescCatasto() == null )
        {
            esito = "KO";
        }
        if( notaBene.getTTpCatCatasto() != null )
        {
            if( notaBene.getTTpCatCatasto().getCodiKIdCategCat() == 0 )
            {
                esito = "KO";
            }
        } else
        {
            esito = "KO";
        }
        if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCat() != null )
        {
            if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat() == 0 )
            {
                esito = "KO";
            }
        } else
        {
            esito = "KO";
        }
        if( notaBene.getDescTipoDenuncia() != null )
        {
            if( !notaBene.getDescTipoDenuncia().equals( 'P' ) )
                if( !notaBene.getDescTipoDenuncia().equals( 'S' ) )
                    if( !notaBene.getDescTipoDenuncia().equals( 'V' ) )
                        esito = "KO";
        }
        if( (notaBene.getDescSezione() != null || notaBene.getDescFoglio() != null || notaBene.getDescParticella1() != null ||
             notaBene.getDescParticella2() != null || notaBene.getDescSubalterno1() != null ||
            notaBene.getDescSubalterno2() != null) &&
            (notaBene.getDescTipoDenuncia() != null || notaBene.getNumeAnnoDenuncia() != null || notaBene.getNumeDenuncia1() != null) )
        {
            esito = "KO";
        }

        if( notaBene.getDescSezione() == null && notaBene.getDescFoglio() == null && notaBene.getDescParticella1() == null &&
            notaBene.getDescParticella2() == null && notaBene.getDescSubalterno1() == null &&
            notaBene.getDescSubalterno2() == null &&
            notaBene.getDescTipoDenuncia() == null && notaBene.getNumeAnnoDenuncia() == null && notaBene.getNumeDenuncia1() == null )
        {
            esito = "KO";
        }
        return esito;
    }

    public void dettaglioSoggetto( ComunicazioneSoggettiForm form, Long idNota, Integer idUffOwn, Long progressivo )
    {
        SoggettoInteressatoDto soggetto = null;
        if( form.getFlgTipoSoggetto() == 'F' )
        {
            soggetto = conservatorieDao.dettaglioSoggettoFisico( idNota, idUffOwn, progressivo );
            if (soggetto.getCODPROGRSOGGC() != null) 
            {
                SoggettoInteressatoDto soggettoCon = conservatorieDao.dettaglioSoggettoFisico( 
                    idNota, idUffOwn, soggetto.getCODPROGRSOGGC().longValue() ); 
                    
                    soggetto.setDescSoggCon( soggettoCon.getCOGNOME() + " " + soggettoCon.getNOME() );
                   
            }
            String statoNasc = conservatorieDao.getStatoByBelfiore( soggetto.getCODICECOMUNENASC() );
            if( statoNasc != null && !statoNasc.equals( "1" ) ) // nato all'estero
            {
                soggetto.setSTATONASCITA( statoNasc ); // comune e provincia nascita scritti dul DB sono presi così come sono 
                form.setComboStatiBelfiore( new ComboForm( soggetto.getCODICECOMUNENASC(), statoNasc ) );
                form.setComboComuniBelfiore( new ComboForm( null, soggetto.getCOMUNENASCITA() ) );
            } else
            {
                soggetto.setSTATONASCITA( "ITALIA" ); // comune e provincia nascita sono ricavati da CodiceComuneNasc
                form.setComboStatiBelfiore( new ComboForm( null, soggetto.getSTATOSEDE() ) );

                String codComuneNascita = soggetto.getCODICECOMUNENASC();
                ComuneProvinciaDto comuneProvNasc = null;
                if( codComuneNascita != null )
                    comuneProvNasc = conservatorieDao.getComuneByBelfiore( codComuneNascita );
                if( comuneProvNasc != null )
                {
                    soggetto.setCOMUNENASCITA( comuneProvNasc.getCOMUNE() );
                    soggetto.setPROVNASCITA( comuneProvNasc.getPROVINCIA() );
                    form.setComboComuniBelfiore( new ComboForm( soggetto.getCODICECOMUNENASC(),
                        soggetto.getCOMUNENASCITA() + " (" + soggetto.getPROVNASCITA() + ")" ) );
                }
            }
        } else
            if( form.getFlgTipoSoggetto() == 'N' )
            {
                soggetto = conservatorieDao.dettaglioSoggettoGiurid( idNota, idUffOwn, progressivo );
                String statoSede = conservatorieDao.getStatoByDescIstat( soggetto.getCODICECOMUNESEDE() );
                if( statoSede != null && !statoSede.equals( "1" ) ) // residente all'estero
                {
                    soggetto.setSTATOSEDE( statoSede ); // comune e provincia sede scritti dul DB sono presi così come sono 
                    form.setComboStatiIstat( new ComboForm( soggetto.getCODICECOMUNESEDE(), statoSede ) );
                    form.setComboComuniIstat( new ComboForm( null, soggetto.getCOMUNESEDE() ) );
                } else
                {
                    soggetto.setSTATOSEDE( "ITALIA" ); // comune e provincia sede sono ricavati da CodiceComuneSede
                    form.setComboStatiIstat( new ComboForm( null, soggetto.getSTATOSEDE() ) );

                    String codComuneSede = soggetto.getCODICECOMUNESEDE();
                    ComuneProvinciaDto comuneProvSede = null;
                    if( codComuneSede != null )
                        comuneProvSede = conservatorieDao.getComuneByIstat( codComuneSede );
                    if( comuneProvSede != null )
                    {
                        soggetto.setCOMUNESEDE( comuneProvSede.getCOMUNE() );
                        soggetto.setPROVSEDE( comuneProvSede.getPROVINCIA() );
                        form.setComboComuniIstat( new ComboForm( soggetto.getCODICECOMUNESEDE(),
                            soggetto.getCOMUNESEDE() + " (" + soggetto.getPROVSEDE() + ")" ) );
                    }
                }
            }
        soggetto.setPROGRESSIVO( BigDecimal.valueOf( progressivo ) );
        soggetto.setTIPOLOGIA( form.getFlgTipoSoggetto() );
        form.setSoggettoDto( setSoggettoDesc( soggetto ) );
    }

    private SoggettoInteressatoDescDto setSoggettoDesc( SoggettoInteressatoDto soggetto )
    {
        SoggettoInteressatoDescDto soggettoDesc = new SoggettoInteressatoDescDto();

        if( soggetto.getDescSoggCon() != null )
            soggettoDesc.setDescSoggCon( soggetto.getDescSoggCon() );
        
        if( soggetto.getIDSOGGETTO() != null )
            soggettoDesc.setIdSoggetto( soggetto.getIDSOGGETTO().longValue() );
        if( soggetto.getVERSIONE() != null )
            soggettoDesc.setVersione( soggetto.getVERSIONE().intValue() );
        if( soggetto.getPROGRESSIVO() != null )
            soggettoDesc.setProgressivo( soggetto.getPROGRESSIVO().longValue() );
        if( soggetto.getTIPOLOGIA() != null )
            soggettoDesc.setTipologia( soggetto.getTIPOLOGIA() );
        if( soggetto.getSOGGETTO() != null )
            soggettoDesc.setSoggetto( soggetto.getSOGGETTO() );
        if( soggetto.getSOGGETTOSITMP() != null )
            soggettoDesc.setSoggettoSitMp( soggetto.getSOGGETTOSITMP().longValue() );
        if( soggetto.getFLAGERRORE() != null )
            soggettoDesc.setFlagErrore( soggetto.getFLAGERRORE() );
        if( soggetto.getCOGNOME() != null )
            soggettoDesc.setCognome( soggetto.getCOGNOME() );
        if( soggetto.getNOME() != null )
            soggettoDesc.setNome( soggetto.getNOME() );
        if( soggetto.getFLGSESSO() != null )
            soggettoDesc.setFlgSesso( soggetto.getFLGSESSO() );
        if( soggetto.getDATANASCITA() != null )
            soggettoDesc.setDataNascita( soggetto.getDATANASCITA() );
        if( soggetto.getDATAMORTE() != null )
            soggettoDesc.setDataMorte( soggetto.getDATAMORTE() );
        if( soggetto.getCOMUNENASCITA() != null )
            soggettoDesc.setComuneNascita( soggetto.getCOMUNENASCITA() );
        if( soggetto.getCODICECOMUNENASC() != null )
            soggettoDesc.setCodiceComuneNasc( soggetto.getCODICECOMUNENASC() );
        if( soggetto.getPROVNASCITA() != null )
            soggettoDesc.setProvNascita( soggetto.getPROVNASCITA() );
        if( soggetto.getSTATONASCITA() != null )
            soggettoDesc.setStatoNascita( soggetto.getSTATONASCITA() );
        if( soggetto.getCODICEFISCALEPF() != null )
            soggettoDesc.setCodiceFiscalePF( soggetto.getCODICEFISCALEPF() );
        if( soggetto.getREGIME() != null )
            soggettoDesc.setRegime( soggetto.getREGIME() );
        if( soggetto.getDESCREGIME() != null )
            soggettoDesc.setDescRegime( soggetto.getDESCREGIME() );
        if( soggetto.getCODPROGRSOGGC() != null )
            soggettoDesc.setCodProgrSoggC( soggetto.getCODPROGRSOGGC().intValue() );
        if( soggetto.getDENOMINAZIONE() != null )
            soggettoDesc.setDenominazione( soggetto.getDENOMINAZIONE() );
        if( soggetto.getSTATOSEDE() != null )
            soggettoDesc.setStatoSede( soggetto.getSTATOSEDE() );
        if( soggetto.getCOMUNESEDE() != null )
            soggettoDesc.setComuneSede( soggetto.getCOMUNESEDE() );
        if( soggetto.getPROVSEDE() != null )
            soggettoDesc.setProvSede( soggetto.getPROVSEDE() );
        if( soggetto.getPARTITAIVA() != null )
            soggettoDesc.setPartitaIva( soggetto.getPARTITAIVA() );
        if( soggetto.getCODICECOMUNESEDE() != null )
            soggettoDesc.setCodiceComuneSede( soggetto.getCODICECOMUNESEDE() );
        if( soggetto.getPROGSOGGETTO() != null )
            soggettoDesc.setProgSoggetto( soggetto.getPROGSOGGETTO().longValue() );
        if( soggetto.getPROGUNITANEGOZIALE() != null )
            soggettoDesc.setProgUnitaNegoziale( soggetto.getPROGUNITANEGOZIALE().longValue() );
        if( soggetto.getQUALIFICAUNITANEGOZIALE() != null )
            soggettoDesc.setQualificaUnitaNegoziale( soggetto.getQUALIFICAUNITANEGOZIALE() );
        if( soggetto.getNUMEQUOTAINTUNITANEGOZIALE() != null )
            soggettoDesc.setNumeQuotaIntUnitaNegoziale( soggetto.getNUMEQUOTAINTUNITANEGOZIALE().longValue() );
        if( soggetto.getNUMEQUOTADECUNITANEGOZIALE() != null )
            soggettoDesc.setNumeQuotaDecUnitaNegoziale( soggetto.getNUMEQUOTADECUNITANEGOZIALE().longValue() );
        if( soggetto.getNUMEQUOTADENOMUNITANEGOZIALE() != null )
            soggettoDesc.setNumeQuotaDenomUnitaNegoziale( soggetto.getNUMEQUOTADENOMUNITANEGOZIALE().longValue() );
        if( soggetto.getTITOLOINTERESSEUNITANEGOZIALE() != null )
            soggettoDesc.setTitoloInteresseUnitaNegoziale( soggetto.getTITOLOINTERESSEUNITANEGOZIALE().longValue() );
        if( soggetto.getDESCRINTERESSEUNITANEGOZIALE() != null )
            soggettoDesc.setDescrInteresseUnitaNegoziale( soggetto.getDESCRINTERESSEUNITANEGOZIALE() );
        if( soggetto.getIDREGIMEUNITANEGOZIALE() != null )
            soggettoDesc.setIdregimeUnitaNegoziale( soggetto.getIDREGIMEUNITANEGOZIALE() );
        if( soggetto.getREGIMECONIUGIUNITANEGOZIALE() != null )
            soggettoDesc.setRegimeConiugiUnitaNegoziale( soggetto.getREGIMECONIUGIUNITANEGOZIALE().longValue() );

        return soggettoDesc;
    }

    @SuppressWarnings( "unused" )
    public boolean modificaSoggettoGiurid( Long idNota, Integer idUffOwn, String utente,
        String denom, String comune, String provincia, String pIva, String codComune, Long progressivo )
    {
        Integer righe = null;
        righe = conservatorieDao.modificaSoggettoGiurid( idNota, idUffOwn, utente, denom, comune, provincia, pIva, codComune, progressivo );
        righe = conservatorieDao.resetErroreSoggetto( idNota, idUffOwn, utente, progressivo );

        NotaDettaglioForm dettaglioNota = dettaglioNota( idUffOwn, idNota );
        if( dettaglioNota.getStato().equals( '1' ) )
        {
            String esito = conservatorieDao.aggiornaStatoNota( idUffOwn, utente, idNota, new Character( '0' ) ); // stato passa da 1 a 0
            //            if( !esito.equals( "OK" ) )
            //                return false;
        }
        return true;
    }

    public boolean modificaSoggettoFisico( Long idNota, Integer idUffOwn, String utente, String cognome, String nome, Character sesso,
        Date dataNascita, String statoNascita, String comuneNascita, String provNascita, String codFiscale, Character regime,
        Integer progrSoggCon,
        Long progressivo )
    {
        @SuppressWarnings( "unused" )
        Integer righe = null;
        righe = conservatorieDao.modificaSoggettoFisico( idNota, idUffOwn, utente, cognome, nome, sesso, dataNascita,
            statoNascita, comuneNascita, provNascita, codFiscale, regime, progrSoggCon, progressivo );

        righe = conservatorieDao.resetErroreSoggetto( idNota, idUffOwn, utente, progressivo );

        NotaDettaglioForm dettaglioNota = dettaglioNota( idUffOwn, idNota );
        if( dettaglioNota.getStato().equals( '1' ) )
        {
            @SuppressWarnings( "unused" )
            String esito = conservatorieDao.aggiornaStatoNota( idUffOwn, utente, idNota, new Character( '0' ) ); // stato passa da 1 a 0
            //            if( !esito.equals( "OK" ) )
            //                return false;
        }
        return true;
    }

    public boolean controllaRegimeSoggettoFisico( Long idNota, Integer idUffOwn, Character sesso, Integer progrSoggCon )
    {
        Character sessoCon = conservatorieDao.sessoSoggettoFisico( idNota, idUffOwn, progrSoggCon );
        if( sessoCon == null || sessoCon.equals( sesso ) )
            return false;
        return true;
    }

    public List<ComuneProvinciaDto> getComuniByConservatoria( Long conservatoriaId )
    {
        List<ComuneConservatoriaDto> comuniByConservat = conservatorieDao.getComuniByConservatoria( conservatoriaId );
        List<ComuneProvinciaDto> listaComuni = new ArrayList<ComuneProvinciaDto>();

        if( comuniByConservat.isEmpty() )
        {
            return null;
        }
        //      mapperBean.map(comunicazioni, listaComunicazioni);

        Iterator<ComuneConservatoriaDto> iter = comuniByConservat.iterator();
        while( iter.hasNext() )
        {
            ComuneProvinciaDto comune = new ComuneProvinciaDto();
            ComuneConservatoriaDto lista = iter.next();
            comune.setChiaveCombo( lista.getCHIAVECOMBO() );
            comune.setIdComune( lista.getIDCOMUNE().longValue() );
            comune.setCOMUNE( lista.getDESCCOMUNE() );
            comune.setPROVINCIA( lista.getSIGLAPROVINCIA() );
            comune.setCodiceCatasto( lista.getCODICECATASTO() );
            listaComuni.add( comune );
        }
        return listaComuni;
    }

    public SoggettoInteressatoDto dettaglioPersonaFisica( Long idSoggetto, Integer idUff, Integer versione )
    {
        return conservatorieDao.dettaglioPersonaFisica( idSoggetto, idUff, versione );
    }

    public SoggettoInteressatoDto dettaglioPersonaGiurid( Long idSoggetto, Integer idUff, Integer versione )
    {
        return conservatorieDao.dettaglioPersonaGiurid( idSoggetto, idUff, versione );
    }

    public TipoDomicilioDto getTipoDomicilio( Integer idUff, Long numeroProcedimento, Long idSoggetto )
    {
        return conservatorieDao.getTipoDomicilio( idUff, numeroProcedimento, idSoggetto );
    }

    public String getDescrizioneQuadroD( Integer idUff, Long idNota )
    {
        return conservatorieDao.getDescrizioneQuadroD( idUff, idNota );
    }

    public Long associaSoggettoProgr( SoggettoInteressatoDto dettaglioPers, Integer idUffOwn, String utente, Long idNota )
    {
        Integer progressivo = conservatorieDao.inserisciSoggettoTNotaSogg( dettaglioPers, idUffOwn, utente, idNota );
        //        if( progressivo == null )
        //            return false;

        if( dettaglioPers.getTIPOLOGIA().equals( 'F' ) )
            conservatorieDao.inserisciSoggettoTNotaSoggPF( dettaglioPers, idNota, idUffOwn, progressivo, utente );

        if( dettaglioPers.getTIPOLOGIA().equals( 'N' ) )
            conservatorieDao.inserisciSoggettoTNotaSoggPG( dettaglioPers, idNota, idUffOwn, progressivo, utente );

        conservatorieDao.statoNotaToZero( idNota, idUffOwn, utente );

        return progressivo.longValue();
    }

    public boolean associaSoggetto( SoggettoInteressatoDto dettaglioPers, Integer idUffOwn, String utente, Long idNota )
    {
        Integer progressivo = conservatorieDao.inserisciSoggettoTNotaSogg( dettaglioPers, idUffOwn, utente, idNota );
        //        if( progressivo == null )
        //            return false;

        if( dettaglioPers.getTIPOLOGIA().equals( 'F' ) )
            conservatorieDao.inserisciSoggettoTNotaSoggPF( dettaglioPers, idNota, idUffOwn, progressivo, utente );

        if( dettaglioPers.getTIPOLOGIA().equals( 'N' ) )
            conservatorieDao.inserisciSoggettoTNotaSoggPG( dettaglioPers, idNota, idUffOwn, progressivo, utente );

        conservatorieDao.statoNotaToZero( idNota, idUffOwn, utente );

        return true;
    }

    @SuppressWarnings( "unused" )
    public String cancellaNotaSoggetto( Long idNota, Integer idUff, Long progrSoggetto, String utente, Character tipoSoggetto, Character stato )
    {
        String esito = null;
        if( tipoSoggetto == 'F' )
        {
            esito = conservatorieDao.cancellaNotaSoggettoPF( idNota, idUff, progrSoggetto );
        } else
            if( tipoSoggetto == 'N' )
            {
                esito = conservatorieDao.cancellaNotaSoggettoPG( idNota, idUff, progrSoggetto );
            }
        esito = conservatorieDao.cancellaNotaBeneSogg( idNota, idUff, progrSoggetto );
        conservatorieDao.cancellaNotaSoggUn( idNota, idUff, progrSoggetto );
        esito = conservatorieDao.cancellaNotaSoggetto( idNota, idUff, progrSoggetto );
        Integer righeModificate = conservatorieDao.statoNotaToZero( idNota, idUff, utente );
        return esito;
    }

    //rm
    public String inserisciTitoliDiritto( Long numeroComunicazione, Long beneSitMp, Long progrSogg,
        Integer tipoInteresse, Long numeratoreQuotaIntero, Long numeratoreQuotaDecimale, Long denominatoreQuota,
        Integer idUffOwner, String descUtente )
    {
        String esito = null;
        @SuppressWarnings( "unused" )
        Integer progressivo = conservatorieDao.inserisciTitoliDiritto( numeroComunicazione, beneSitMp, progrSogg,
            tipoInteresse, numeratoreQuotaIntero, numeratoreQuotaDecimale, denominatoreQuota, idUffOwner, descUtente );
        conservatorieDao.statoNotaToZero( numeroComunicazione, idUffOwner, descUtente );
        esito = "OK";
        return esito;
    }

    //rm
    public Double controllaCapienza( Long numeroComunicazione, Long beneSitMp, Long progrSogg,
        Integer tipoInteresse, Integer idUffOwner, String descUtente )
    {
        Double somma = conservatorieDao.controllaCapienza( numeroComunicazione, beneSitMp, progrSogg, tipoInteresse, idUffOwner, descUtente );
        return somma;
    }

    public Integer controllaDuplicato( Long numeroComunicazione, Long beneSitMp, Long progrSogg,
        Integer tipoInteresse, Integer idUffOwner, String descUtente )
    {
        Integer numero = conservatorieDao.controllaDuplicato( numeroComunicazione, beneSitMp, progrSogg, tipoInteresse, idUffOwner, descUtente );
        return numero;
    }

    public String cancellaTitoliDiritto( Long numeroComunicazione, Long beneSitMp, Long progrSogg,
        Integer tipoInteresse, Integer idUffOwner, String descUtente )

    {
        String esito = null;
        char stato;
        //        stato = conservatorieDao.statoNota( numeroComunicazione, idUffOwner, descUtente );
        stato = conservatorieDao.getStatoNota( numeroComunicazione, idUffOwner );
        if( stato == '1' || stato == '0' )
        {
            //            Integer righe = conservatorieDao.cancellaTitoliDiritto( numeroComunicazione, beneSitMp, progrSogg,
            //                    tipoInteresse, idUffOwner );
            esito = conservatorieDao.cancellaTitoliDiritto( numeroComunicazione, beneSitMp, progrSogg,
                tipoInteresse, idUffOwner );
            conservatorieDao.statoNotaToZero( numeroComunicazione, idUffOwner, descUtente );
            esito = "OK!!!";

        }
        return esito;
    }

    public boolean esisteSoggettoFisico( Long idNota, Integer idUffOwn, Integer progrSoggCon )
    {
        return conservatorieDao.esisteSoggettoFisico( idNota, idUffOwn, progrSoggCon ); //fg
    }
    
    public String nuovaComunicazione( int idUffOwn, Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero, long idProced,
        ComunicazioneInsertForm form, Short idCons )
    {
        Hashtable<Long, Long> soggettiInseriti = null; // key = id , val = progressivo 
        
        String esito = "OK";
        Date dataOggi = new Date();

        // ricerco i beni che devono essere sottoposti a comunicazione (in funzione del tipo nota)
        String tipoNota = form.getTipoNota().getId();
        List<BeneAssociabileDto> listaBeniAssociabiliDto = null;
        List<SoggettoBeneDto> soggettoBene = null;
        Character decisorio = conservatorieDao.getFlagDecisorio( tipoAtto, catAtto );
        if( tipoNota.equals( "A" ) )
        {
            if( decisorio.equals( 'S' ) )
            {
                return "KO_SEQUESTRO";
            }
            // richiamo la query per nota annotazione
            listaBeniAssociabiliDto = conservatorieDao.listaBeniAnnot( idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced, form
                .getTenoreMisura() );
        }
        if( tipoNota.equals( "T" ) )
        {
            if( decisorio.equals( 'D' ) )
            {
                // richiamo la query per nota trascrizione atti decisori 1 e 2 grado
                listaBeniAssociabiliDto = conservatorieDao.listaBeniTrascrD( idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced );
            } else
                if( decisorio.equals( 'S' ) )
                {
                    // richiamo la query per nota trascrizione sequestro
                    listaBeniAssociabiliDto = conservatorieDao.listaBeniTrascrS( idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced );
                }
        }
        if( listaBeniAssociabiliDto == null || listaBeniAssociabiliDto.size() == 0 )
        {
            return "KO_BENI";
        }
        Long idNota = null;
        String idConservElab = "";
        Iterator<BeneAssociabileDto> iter = listaBeniAssociabiliDto.iterator();
        while( iter.hasNext() ) // ciclo sui beni
        {
            // per ogni bene inserisco una nota nelle tabelle T_NOTA e, in funzione del tipo nota, una riga su T_NOTA_ANNOT o T_NOTA_TRASCR 
            // prima devo valorizzare i campi della form che servono al metodo inserisciNota
            BeneAssociabileDto bene = iter.next();
            form.setDescrConservatoria( bene.getDESC_CONSERV() );
            ComboForm idConserv = new ComboForm();
            if( idCons != 0 )
            {
                if( idCons != bene.getCODI_K_ID_CONSERV().shortValue() )
                {
                	esito = "KO_BENI";
                    continue;
                }
            }
            esito = "OK";
            idConserv.setId( bene.getCODI_K_ID_CONSERV().toString() );
            idConserv.setDescrizione( bene.getDESC_CONSERV() );
            form.setConservatoria( idConserv );
            form.setIdProcedimento( idProced );
            form.setCatAtto( catAtto.toString() );
            form.setTipoAtto( tipoAtto.toString() );
            valorizzaCampiForm( form, idUffOwn, repAnno, repNumero, dataOggi );

            // controllo commentato in data 21/05/2015 su richiesta di cameralini-dileo
//            if( form.getPubblicoUfficiale() == null || form.getCodiceFiscale() == null )
//            {
//                return "KO_PUBBL_UFF";
//            }
            if( !idConservElab.equals( idConserv.getId() ) )
            {
                idConservElab = idConserv.getId().toString();
                idNota = inserisciNota( form, idUffOwn );
                soggettiInseriti = new Hashtable<Long, Long>( 100 );
            }

            // dopo aver inserito la nota inserisco la relativa riga sulla tabella T_NOTA_BENE
            esito = associaNotaBene( idNota, idUffOwn, bene.getCODI_K_ID_BENE().longValue(), form.getDescUtente() );
            Long maxProgrBene = conservatorieDao.getMaxProgrNotaBene( idNota, idUffOwn, bene.getCODI_K_ID_BENE().longValue() );

            //ricerco i soggetti (contro) da inserire 

            if( tipoNota.equals( "A" ) )
            {
                // richiamo la query per nota annotazione
                soggettoBene = conservatorieDao.listaSoggettiBeneAnnot( idUffOwn, tipoAtto, catAtto, repAnno, repNumero,
                        bene.getCODI_K_ID_BENE().longValue(), idProced,form.getTenoreMisura() );

            }
            if( tipoNota.equals( "T" ) )
            {
                if( decisorio.equals( 'D' ) )
                {
                    // richiamo la query per nota trascrizione atti decisori 1 e 2 grado
                    soggettoBene = conservatorieDao.listaSoggettiBeneTrascrD( idUffOwn, tipoAtto, catAtto, repAnno, repNumero,
                            bene.getCODI_K_ID_BENE().longValue(), idProced );
                } else
                    if( decisorio.equals( 'S' ) )
                    {
                        // richiamo la query per nota trascrizione sequestro
                        soggettoBene = conservatorieDao.listaSoggettiBeneTrascrS( idUffOwn, tipoAtto, catAtto, repAnno, repNumero,
                                bene.getCODI_K_ID_BENE().longValue(), idProced );
                    }
            }

            Iterator<SoggettoBeneDto> iterSb = soggettoBene.iterator();
            while( iterSb.hasNext() )
            {
                SoggettoBeneDto soggBene = iterSb.next();

                esito = ricercaInserisciNotaSoggetto( soggBene, idUffOwn, form.getDescUtente(), idNota, maxProgrBene, soggettiInseriti );
            }
        } // FINE ciclo sui beni
        return esito;
    }

    private void valorizzaCampiForm( ComunicazioneInsertForm form, int idUffOwn, Short repAnno, Long repNumero, Date dataOggi )
    {
        // ricerca del max progressivo nota
        form.setProgressivo( conservatorieDao.cercaMaxProgressivoNota( idUffOwn, repAnno, repNumero ) );
        form.setRepertorioAnno( repAnno.intValue() );
        form.setNumero( repNumero.intValue() );
        form.setDataTitolo( dataOggi );
        // ricerco sulla tabella T_AN_UFFICIO dei campi relativi
        TAnUfficio ufficio = getDettaglioUfficio( idUffOwn );
        if( ufficio.getDescCfPu() != null )
        {
            form.setCodiceFiscale( ufficio.getDescCfPu().toUpperCase() );
        }
        if( ufficio.getDescPubblUff() != null )
        {
            form.setPubblicoUfficiale( ufficio.getDescPubblUff().toUpperCase() );
        }
        if( ufficio.getDescSedeUfficio() != null )
        {
            form.setSede( ufficio.getDescSedeUfficio() );
        }
        form.setCategoria( '2' ); // DESC_TIPO_PUBBL_UFF !!!

        ComuneProvinciaDto comuneProv = null;

        comuneProv = conservatorieDao.getComuneByBelfiore( String.valueOf( ufficio.getTAnComuni().getDescBelfiore() ) );
        if( comuneProv != null )
        {
            form.setProvincia( comuneProv.getPROVINCIA() );
        }

        form.setTitoloDescrizione( "ATTO GIUDIZIARIO" );

        form.setUnitaNegoziali( (short)0 );
        form.setImmobili( 0 );
        form.setSoggettiFavore( (short)0 );
        form.setSoggettiContro( (short)0 );

        form.setflgStato( '0' );
        form.setflgNotaCollegata( null );

    }

    private String ricercaInserisciNotaSoggetto( SoggettoBeneDto soggBene, int idUffOwn, String descUtente, Long idNota,
        Long maxProgrBene, Hashtable<Long, Long> soggettiInseriti  )
    {

        String esito = "OK";
        Long progrSogg = 0l;

        // per ogni soggetto inserisco una nota nelle tabelle T_NOTA_SOGG e, in funzione del tipo soggetto, una riga su T_NOTA_SOGG_PF o T_NOTA_SOGG_PNF 

        SoggettoInteressatoDto dettaglioPers = null;
        Long idSoggetto = soggBene.getCODI_K_ID_SOGG_INT().longValue();
        Integer versione = soggBene.getCODI_FK_ID_VER().intValue();
        Character tipoSoggetto = soggBene.getCODI_FK_ID_TIPO_SOGG();

        if( tipoSoggetto.equals( 'F' ) )
        {
            dettaglioPers = dettaglioPersonaFisica( idSoggetto, idUffOwn, versione );
        } else
        {
            tipoSoggetto = 'N';
            dettaglioPers = dettaglioPersonaGiurid( idSoggetto, idUffOwn, versione );
        }
        boolean controlliOk = false;
        if( tipoSoggetto.equals( 'F' ) )
        {
            String cognome = dettaglioPers.getCOGNOME();
            String nome = dettaglioPers.getNOME();
            Character sesso = dettaglioPers.getFLGSESSO();
            String codFiscale = dettaglioPers.getCODICEFISCALEPF();
            Date dataNascita = dettaglioPers.getDATANASCITA();
            controlliOk = controlliAssociaPF( cognome, nome, sesso, dataNascita, codFiscale );
        } else
            if( tipoSoggetto.equals( 'N' ) )
            {
                String denominazione = dettaglioPers.getDENOMINAZIONE();
                String comuneSede = dettaglioPers.getCODICECOMUNESEDE();
                String provSede = dettaglioPers.getPROVSEDE();
                String codFiscale = dettaglioPers.getPARTITAIVA();
                controlliOk = controlliAssociaPNF( denominazione, comuneSede, provSede, codFiscale );
            }
        Character flgErrore = controlliOk ? '0' : '1';
        dettaglioPers.setVERSIONE( BigDecimal.valueOf( versione ) );
        dettaglioPers.setIDSOGGETTO( BigDecimal.valueOf( idSoggetto ) );
        dettaglioPers.setTIPOLOGIA( tipoSoggetto );
        dettaglioPers.setFLAGERRORE( flgErrore );

        if( !soggettiInseriti.containsKey( idSoggetto ) )
        {
            progrSogg = associaSoggettoProgr( dettaglioPers, idUffOwn, descUtente, idNota );
            soggettiInseriti.put( idSoggetto, progrSogg );
        }

        //valorizzo i campi per l'inserimento della tabella T_NOTA_SOGG_BENE
//        progrSogg = progrSogg + 1;
        Integer interesse = soggBene.getCODI_K_ID_TIPO_INT().intValue();
        Long numeratoreQuotaIntero = 0L;
        Long numeratoreQuotaDecimale = 0L;
        Long denominatoreQuota = 0L;
        if( soggBene.getNUME_QUOTA() != null )
        {
            numeratoreQuotaIntero = soggBene.getNUME_QUOTA().longValue();
            denominatoreQuota = 100L;
        } else
            if( soggBene.getNUME_FRAZIONE_NUM() != null && soggBene.getNUME_FRAZIONE_DEN() != null )
            {
                numeratoreQuotaIntero = soggBene.getNUME_FRAZIONE_NUM().longValue();
                denominatoreQuota = soggBene.getNUME_FRAZIONE_DEN().longValue();
            }
        esito = inserisciTitoliDiritto( idNota, maxProgrBene, soggettiInseriti.get( idSoggetto ),
            interesse, numeratoreQuotaIntero, numeratoreQuotaDecimale, denominatoreQuota, idUffOwn, descUtente );
        //            if( !"OK".equals( esito ) )
        //            {
        //                return "KO_NOTA_SOGG_BENE"; 
        //            }

        return esito;
    }

    private boolean controlliAssociaPNF( String denominazione, String comuneSede, String provSede, String partitaIva )
    {
        if( denominazione == null || comuneSede == null || provSede == null || partitaIva == null )
        {
            return false;
        }
        return true;
    }

    private boolean controlliAssociaPF( String cognome, String nome, Character sesso, Date dataNascita, String codFiscale )
    {
        if( cognome == null || nome == null || sesso == null || dataNascita == null || dataNascita.after( new Date() ) || codFiscale == null )
        {
            return false;
        }
        return true;
    }

    public List<ComunicazioneListaForm> listaComunicazioniPredisp( Integer ufficio, String tipoNota, Integer tipoAtto, Integer catAtto,
        Short repAnno, Long repNumero, String attoSpecie, String descSpecie )

    {
        List<ComunicazioneListaForm> listaComunicazioni = new ArrayList<ComunicazioneListaForm>();
        List<ComunicazionePredispDto> comunicazioni = conservatorieDao.listaComunicazioniConservatoriePredisp(
            ufficio, tipoNota, tipoAtto, catAtto, repAnno, repNumero, attoSpecie, descSpecie );
        //      List<ListaComunicazioniDto> comunicazioni1 = conservatorieDao.listaComunicazioniConservatorie1(
        //              tipo,categorieAtti,anno,dalNumero,alNumero,conservatoria,cognome,nome,denominazione,idUfficioUtente);
        if( comunicazioni.isEmpty() )
        {
            return null;
        }
        //      mapperBean.map(comunicazioni, listaComunicazioni);

        Iterator<ComunicazionePredispDto> iter = comunicazioni.iterator();
        while( iter.hasNext() )
        {
            ComunicazioneListaForm currentForm = new ComunicazioneListaForm();
            ComunicazionePredispDto lista = iter.next();
            currentForm.setIdNota( lista.getIDNOTA().longValue() );
            switch( lista.getSTATO() )
            {
            case '0': currentForm.setStatoComunicazione( "in lavorazione" ); break;
            case '1': currentForm.setStatoComunicazione( "validata" ); break;
            case '2': currentForm.setStatoComunicazione( "inviata" ); break;
            case '3': currentForm.setStatoComunicazione( "sostituita" ); break;
            }
            currentForm.setDataComunicazione( lista.getDATATITOLO() );
            currentForm.setDescConservatoria( lista.getDESCCONSERV() );
            currentForm.setCodConservatoria( lista.getIDCONSERV().intValue() );
            currentForm.setProgrNota( lista.getPROGRNOTA().longValue() );
            currentForm.setAnnoNumero( "" + repAnno + "/" + repNumero + "-" + currentForm.getProgrNota() );
            listaComunicazioni.add( currentForm );
        }
        return listaComunicazioni;
    }

    public String rigeneraComunicazione( int idUffOwn, Integer tipoAtto, Integer catAtto, Short repAnno, Long repNumero, long idProced,
        ComunicazioneInsertForm form, String[] notaSel )
    {
        String esito = "OK";
        String idConsElab = null;
        // String[] note = StringUtils.split( notaSel, "," );
        for( String nota : notaSel )
        {
            if( nota != null )
            {
                String[] campi = StringUtils.split( nota, "^" );
                String idNotaS = campi[0];
                String idConsS = campi[1];
                Long idNota = new Long( idNotaS );
                Short idCons = new Short( idConsS );
                if( !idConsS.equals( idConsElab ) )
                {
                    idConsElab = idConsS;
                    esito = nuovaComunicazione( idUffOwn, tipoAtto, catAtto, repAnno, repNumero, idProced, form, idCons );
                    if ("OK".equals(esito)) {
                        esito = cancellaNota( idUffOwn, idNota );
                    }
                } else
                {
                    esito = cancellaNota( idUffOwn, idNota );
                }
            }
        }
        return esito;
    }

    private String cancellaNota( int idUffOwn, Long idNota )
    {
        String esitoNota = "OK";
        // carico il dettaglio della nota selezionata
        NotaDettaglioForm dettaglioNota = dettaglioNota( idUffOwn, idNota );
        if( dettaglioNota.getStato().equals( '0' ) || dettaglioNota.getStato().equals( '1' ) )
        {
            //devo cancellare quanto presente nel DataBase afferente alla nota seezionata
            esitoNota = eliminaNota( idNota, idUffOwn );
        }
        return esitoNota;
    }

    public String aggiornaStatoNota( Integer idUfficioUtente, String descUtente, Long idNota, Character flgstato )
    {
        String esito = conservatorieDao.aggiornaStatoNota( idUfficioUtente, descUtente, idNota, flgstato ); // stato passa da 1 a 0
        return esito;
    }

    public ComboForm getComuneByUfficio( Integer idUffOwn )
    {
        Object[] datiComune = conservatorieDao.getComuneByUfficio( idUffOwn );
        ComboForm retCombo = new ComboForm( "" + ((BigDecimal)datiComune[0]).longValue(), "" + datiComune[1] + " (" + datiComune[2] + ")" );
        return retCombo;
    }
    
    
    public List<ComboForm> listaPFinRegime(Long idNota, Integer idUffOwn, Long progrSogg)
    {
        List<NotaPersFisRegimeDto> lista;
        lista = conservatorieDao.listaPFinRegime(idNota, idUffOwn, progrSogg);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<NotaPersFisRegimeDto> iterator = lista.iterator();
        while( iterator.hasNext() )
        {
        	NotaPersFisRegimeDto pf = iterator.next();
        	if (pf.getCODI_K_PROGR_SOGG() != null)
        		risultati.add( new ComboForm( pf.getCODI_K_PROGR_SOGG().toString(), "" + pf.getDESC_COGNOME() + " " + pf.getDESC_NOME() ) );
        	else
        		risultati.add( new ComboForm( "", "" + pf.getDESC_COGNOME() + " " + pf.getDESC_NOME() ) );
        }
        return risultati;
    }

    public Character getFlagDecisorio( Integer idTipoAtto, Integer idCatAtto )
    {
        return conservatorieDao.getFlagDecisorio( idTipoAtto, idCatAtto );
    }

}
