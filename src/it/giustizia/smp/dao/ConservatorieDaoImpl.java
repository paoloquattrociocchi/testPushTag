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
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TNota;
import it.giustizia.smp.model.TNotaAnnot;
import it.giustizia.smp.model.TNotaBene;
import it.giustizia.smp.model.TNotaQuadroD;
import it.giustizia.smp.model.TNotaSogg;
import it.giustizia.smp.model.TNotaSoggBene;
import it.giustizia.smp.model.TNotaSoggPf;
import it.giustizia.smp.model.TNotaSoggUn;
import it.giustizia.smp.model.TNotaTrascr;
import it.giustizia.smp.model.TNotaUn;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoComunic;
import it.giustizia.smp.model.TTpTipoDenuncia;
import it.giustizia.smp.model.TTpTipoFondo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional( rollbackFor = Exception.class )
public class ConservatorieDaoImpl extends HibernateDaoSupport implements ConservatorieDao
{
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<ComunicazioneDto> listaComunicazioniConservatorie(
        final String tipo, final String tipoAtto, final Short anno, final Long dalNumero,
        final Long alNumero, final String conservatoria, final String cognome, final String nome,
        final String denominazione, final Integer idUfficioUtente )
    {
        return (List<ComunicazioneDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_lista_comunicazioni" );
                q.setParameter( "tipo", tipo, Hibernate.STRING );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.STRING );
                q.setParameter( "anno", anno, Hibernate.SHORT );
                q.setParameter( "dalNumero", dalNumero, Hibernate.LONG );
                q.setParameter( "alNumero", alNumero, Hibernate.LONG );
                q.setParameter( "conservatoria", conservatoria, Hibernate.STRING );
                q.setParameter( "cognome", cognome, Hibernate.STRING );
                q.setParameter( "nome", nome, Hibernate.STRING );
                q.setParameter( "denominazione", denominazione, Hibernate.STRING );
                q.setParameter( "idUfficioUtente", idUfficioUtente, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.aliasToBean( ComunicazioneDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public DettaglioComunicazioneDto dettaglioNota( final int idUffOwn, final Long idNota )
    {
        return (DettaglioComunicazioneDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Dettaglio_Nota" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( DettaglioComunicazioneDto.class ) );
                List l = q.list();
                if( (l != null) && (l.size() > 0) ) {
                return l.get( 0 );
                }
                return null;
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public NotaAnnotazioneDto getNotaAnnotazione( final int idUffOwn, final Long idNota )
    {
        return (NotaAnnotazioneDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Nota_Annot" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( NotaAnnotazioneDto.class ) );
                List l = q.list();
                if( (l != null) && (l.size() > 0) ) {
                return l.get( 0 );
                }
                return null;
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public NotaTrascrizioneDto getNotaTrascrizione( final int idUffOwn, final Long idNota )
    {
        return (NotaTrascrizioneDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Nota_Trascr" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( NotaTrascrizioneDto.class ) );
                List l = q.list();
                if( (l != null) && (l.size() > 0) ) {
                return l.get( 0 );
                }
                return null;
            }
        } );
    }
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<TipoCategAttiDto> getCategorieAttiNonSequestri( final Integer tipoUff )
    {
        return (List<TipoCategAttiDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_getCategorieAttiNonSequestri" );
                q.setParameter( "tipoUff", tipoUff, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.aliasToBean( TipoCategAttiDto.class ) );
                return q.list();
            }
        } );
    }

//    @SuppressWarnings( "unchecked" )
//    public List<TTpCatAtto> getCategorieAttiRicerca(Integer tipoUff) --
//    {
//        DetachedCriteria criteria = DetachedCriteria.forClass( TTpCatAtto.class );
//        Criterion rest1 = Restrictions.eq( "flgDecisorio", 'S' );
//        Criterion rest2 = Restrictions.eq( "flgDecisorio", 'C' );
//        Criterion rest3 = Restrictions.eq( "flgDecisorio", 'D' );
//        criteria.add( Restrictions.disjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
//        criteria.addOrder( Order.asc( "descCatAtto" ) );
//        List<TTpCatAtto> u = getHibernateTemplate().findByCriteria( criteria );
//        return u;
//    }
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<TipoCategAttiDto> getCategorieAttiRicerca( final Integer tipoUff ) 
    {
        return (List<TipoCategAttiDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_getCategorieAttiRicerca" );
                q.setParameter( "tipoUff", tipoUff, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.aliasToBean( TipoCategAttiDto.class ) );
                return q.list();
            }
        } );
    }

//    @SuppressWarnings( "unchecked" )
//    public List<TTpCatAtto> getCategorieAttiInserimento(Integer tipoUff)
//    {
//        DetachedCriteria criteria = DetachedCriteria.forClass( TTpCatAtto.class );
//        criteria.createAlias( "TTpTipoAtto", "tipoAtto" );
//        Criterion rest1 = Restrictions.eq( "flgDecisorio", 'S' );
//        Criterion rest2 = Restrictions.eq( "flgDecisorio", 'C' );
//        Criterion rest3 = Restrictions.eq( "flgDecisorio", 'D' );
//        criteria.add( Restrictions.disjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
//        Criterion rest4 = Restrictions.isNull( "dataFineValidita" );
//        Criterion rest5 = Restrictions.gt( "dataFineValidita", new Date() );
//        criteria.add( Restrictions.or( rest4, rest5 ) );
//        criteria.addOrder( Order.asc( "descCatAtto" ) );
//        List<TTpCatAtto> u = getHibernateTemplate().findByCriteria( criteria );
//        return u;
//    }
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<TipoCategAttiDto> getCategorieAttiInserimento( final Integer tipoUff )
    {
        return (List<TipoCategAttiDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_getCategorieAttiInserimento" );
                q.setParameter( "tipoUff", tipoUff, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.aliasToBean( TipoCategAttiDto.class ) );
                return q.list();
            }
        } );
    }


    
    @SuppressWarnings( "unchecked" )
    public List<TTpTipoComunic> getTipoComunic()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoComunic.class );
        Criterion rest3 = Restrictions.isNull( "dataFineValidita" );
        Criterion rest4 = Restrictions.gt( "dataFineValidita", new Date() );
        criteria.add( Restrictions.or( rest3, rest4 ) );
        criteria.addOrder( Order.asc( "descTipoComunic" ) );
        List<TTpTipoComunic> u = getHibernateTemplate().findByCriteria( criteria );
        return u;
    }

    @SuppressWarnings( "unchecked" )
    public List<TTpTipoComunic> getTipoComunicAnn()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoComunic.class );
        Criterion rest1 = Restrictions.ilike("codiKTipoComunic", "07%");
        criteria.add(  rest1 );
        Criterion rest3 = Restrictions.isNull( "dataFineValidita" );
        Criterion rest4 = Restrictions.gt( "dataFineValidita", new Date() );
        criteria.add( Restrictions.or( rest3, rest4 ) );
        criteria.addOrder( Order.asc( "codiKTipoComunic" ) );
        List<TTpTipoComunic> u = getHibernateTemplate().findByCriteria( criteria );
        return u;
    }

    @SuppressWarnings( "unchecked" )
    public List<TTpTipoComunic> getTipoComunicTrasc()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoComunic.class );
        Criterion rest1 = Restrictions.ilike("codiKTipoComunic", "09%");
        criteria.add(  rest1 );
        Criterion rest3 = Restrictions.isNull( "dataFineValidita" );
        Criterion rest4 = Restrictions.gt( "dataFineValidita", new Date() );
        criteria.add( Restrictions.or( rest3, rest4 ) );
        criteria.addOrder( Order.asc( "codiKTipoComunic" ) );
        List<TTpTipoComunic> u = getHibernateTemplate().findByCriteria( criteria );
        return u;
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long getIdProcedimento( final Integer idUffOwn, final Integer annoProc, final Integer numeProc,
        final String tipoAtto, final String catAtto )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long idProcedimento = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_ricercaIdProcedimento" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "annoProc", annoProc, Hibernate.INTEGER );
                q.setParameter( "numeProc", numeProc, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.STRING );
                q.setParameter( "catAtto", catAtto, Hibernate.STRING );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                if( (l != null) && (l.size() > 0) )
                {
                    Map<String, Object> ret = (Map<String, Object>)l.get( 0 );
                    idProcedimento = (Long)ret.get( "CODI_K_ID_PROC" );
                }
                return idProcedimento;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public List<Short> cercaProgressivoNota( final Integer idUffOwn, final Integer annoProc, final Integer numeProc )
    {
        return (List<Short>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_cerca_Progressivo" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "annoProc", annoProc, Hibernate.INTEGER );
                q.setParameter( "numeProc", numeProc, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List lista = q.list();
                List<Short> listaProgr = new ArrayList<Short>();
                Iterator it = lista.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    Short p = ((BigDecimal)row.get( "NUME_PROGR_NOTA" )).shortValue();
                    listaProgr.add( p );
                }
                return listaProgr;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Short cercaMaxProgressivoNota( final Integer idUffOwn, final Short annoProc, final Long numeProc )
    {
        return (Short)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Short maxProgr = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_cerca_Max_Progressivo" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "annoProc", annoProc, Hibernate.SHORT );
                q.setParameter( "numeProc", numeProc, Hibernate.LONG );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List listaProgr = q.list();
                if( (listaProgr != null) && (listaProgr.size() > 0) )
                {
                    Map<String, Object> ret = (Map<String, Object>)listaProgr.get( 0 );
                    maxProgr = (Short)ret.get( "MAXPROGR" );
                }
                if( maxProgr == null )
                {
                    maxProgr = (short) 1;
                } else
                {
                	maxProgr = (short) (maxProgr + 1);

                }
                return maxProgr;
            }

                
        } );
    }


    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long inserisciNota( final TNota nota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//            	String descTipoComunic=null;
//            	if (nota.getTTpTipoNota().getCodiKIdTipoNota()=='A') {            	
//                 descTipoComunic( nota.getTTpTipoComunic().getCodiKTipoComunic() );
//            	}else {
//            		descTipoComunic=nota.getDescAtto();
//            	}
            		
                Long idNota = getIdNota( idUffOwn );
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNota" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "descfiscalepu", nota.getDescFiscalePu(), Hibernate.STRING );
                q.setParameter( "numerepert1", nota.getNumeRepert1(), Hibernate.INTEGER );
                q.setParameter( "numerepert2", nota.getNumeRepert2(), Hibernate.INTEGER );
                q.setParameter( "numeprogrnota", nota.getNumeProgrNota(), Hibernate.SHORT );
                q.setParameter( "desctitolo", nota.getDescTitolo(), Hibernate.STRING );
                q.setParameter( "datatitolo", nota.getDataTitolo(), Hibernate.DATE );
                q.setParameter( "desctipopubbluff", nota.getDescTipoPubblUff(), Hibernate.CHARACTER );
                q.setParameter( "descpubbluff", nota.getDescPubblUff(), Hibernate.STRING );
                q.setParameter( "descsedepubbluff", nota.getDescSedePubblUff(), Hibernate.STRING );
                q.setParameter( "descprovpubbluff", nota.getDescProvPubblUff(), Hibernate.STRING );
                q.setParameter( "descspecieatto", nota.getDescSpecieAtto(), Hibernate.STRING );
                q.setParameter( "descatto", nota.getDescAtto(), Hibernate.STRING );
                q.setParameter( "numeunitneg", nota.getNumeUnitNeg(), Hibernate.SHORT );
                q.setParameter( "numeimmobili", nota.getNumeImmobili(), Hibernate.INTEGER );
                q.setParameter( "numesoggfavore", nota.getNumeSoggFavore(), Hibernate.SHORT );
                q.setParameter( "numesoggcontro", nota.getNumeSoggContro(), Hibernate.SHORT );
                q.setParameter( "descconservat", nota.getDescConservat(), Hibernate.STRING );
                q.setParameter( "flgnotacollegata", nota.getFlgNotaCollegata(), Hibernate.CHARACTER );
                q.setParameter( "flgstato", nota.getFlgStato(), Hibernate.CHARACTER );
                q.setParameter( "descutente", nota.getDescUtente(), Hibernate.STRING );
                q.setParameter( "codifkidtipoatto", nota.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER );
                q.setParameter( "codifkidcatatto", nota.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER );
                q.setParameter( "codifkidtiponota", nota.getTTpTipoNota().getCodiKIdTipoNota(), Hibernate.CHARACTER );
                q.setParameter( "codikidconserv", nota.getTAnConservatorie().getCodiKIdConserv(), Hibernate.SHORT );
                q.setParameter( "codifktipocomunic", nota.getTTpTipoComunic().getCodiKTipoComunic(), Hibernate.STRING );
                q.setParameter( "codifkidproc", nota.getTProcedimento().getCodiKIdProc(), Hibernate.LONG );

                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNota = (Long)row.get( "IDNOTA" );
                }
                return idNota;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long getIdNota( final Integer idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long idNota = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_ricercaIdNota" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                if( (l != null) && (l.size() > 0) )
                {
                    Map<String, Object> ret = (Map<String, Object>)l.get( 0 );
                    idNota = (Long)ret.get( "MAXIDNOTA" );
                }
                if( idNota == null )
                {
                    String idNotaString = idUffOwn + "000000001";
                    idNota = Long.valueOf( idNotaString );
                } else
                {
                    idNota = idNota + 1;

                }
                return idNota;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long inserisciNotaAnnot( final TNotaAnnot notaAnnot, final Long idNota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long idNotaAnnotaz = null;
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaAnnot" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "flgvoltura", notaAnnot.getFlgVoltura(), Hibernate.CHARACTER );
                q.setParameter( "dataformalita", notaAnnot.getDataFormalita(), Hibernate.DATE );
                q.setParameter( "numeformalita1", notaAnnot.getNumeFormalita1(), Hibernate.INTEGER );
                q.setParameter( "numeformalita2", notaAnnot.getNumeFormalita2(), Hibernate.SHORT );
                q.setParameter( "numevaloreimmint", notaAnnot.getNumeValoreImmInt(), Hibernate.LONG );
                q.setParameter( "numevaloreimmdec", notaAnnot.getNumeValoreImmDec(), Hibernate.BYTE );
                q.setParameter( "descutente", notaAnnot.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaAnnotaz = (Long)row.get( "IDNOTAANNOT" );
                }
                return idNotaAnnotaz;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long inserisciNotaTrascr( final TNotaTrascr notaTrascr, final Long idNota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {

                Long idNotaTrascr = null;
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaTrascr" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "flgvoltura", notaTrascr.getFlgVoltura(), Hibernate.CHARACTER );
                q.setParameter( "datavolturadiff", notaTrascr.getDataVolturaDiff(), Hibernate.DATE );
                q.setParameter( "flgvolturadiff", notaTrascr.getFlgVolturaDiff(), Hibernate.CHARACTER );
                q.setParameter( "flgfoglioinfo", notaTrascr.getFlgFoglioInfo(), Hibernate.CHARACTER );
                q.setParameter( "flgcondizione", notaTrascr.getFlgCondizione(), Hibernate.CHARACTER );
                q.setParameter( "flgtermini", notaTrascr.getFlgTermini(), Hibernate.CHARACTER );
                q.setParameter( "dataformalita", notaTrascr.getDataFormalita(), Hibernate.DATE );
                q.setParameter( "numeformalita1", notaTrascr.getNumeFormalita1(), Hibernate.INTEGER );
                q.setParameter( "numeformalita2", notaTrascr.getNumeFormalita2(), Hibernate.SHORT );
                q.setParameter( "flgquadroda", notaTrascr.getFlgQuadrodA(), Hibernate.CHARACTER );
                q.setParameter( "flgquadrodb", notaTrascr.getFlgQuadrodB(), Hibernate.CHARACTER );
                q.setParameter( "flgquadrodc", notaTrascr.getFlgQuadrodC(), Hibernate.CHARACTER );

                q.setParameter( "descutente", notaTrascr.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaTrascr = (Long)row.get( "IDNOTATRASCR" );
                }
                return idNotaTrascr;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long modificaNota( final TNota nota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
            	/*
                String descTipoComunic = "";
                If (nota.getDescAtto() != null) {
                	descTipoComunic = nota.getDescAtto();
                }
                /*
                if( descTipoComunic == null )
                {
                    descTipoComunic = descTipoComunic( nota.getTTpTipoComunic().getCodiKTipoComunic() );
                }
                */
                Long idNota = null;
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_modificaNota" );
                q.setParameter( "codikidnota", nota.getId().getCodiKIdNota(), Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "descfiscalepu", nota.getDescFiscalePu(), Hibernate.STRING );
                q.setParameter( "desctitolo", nota.getDescTitolo(), Hibernate.STRING );
                q.setParameter( "datatitolo", nota.getDataTitolo(), Hibernate.DATE );
                q.setParameter( "descpubbluff", nota.getDescPubblUff(), Hibernate.STRING );
                q.setParameter( "descsedepubbluff", nota.getDescSedePubblUff(), Hibernate.STRING );
                q.setParameter( "descprovpubbluff", nota.getDescProvPubblUff(), Hibernate.STRING );
                // q.setParameter( "descatto", descTipoComunic, Hibernate.STRING );
                q.setParameter( "descatto", nota.getDescAtto(), Hibernate.STRING );
                q.setParameter( "numeunitneg", nota.getNumeUnitNeg(), Hibernate.SHORT );
                q.setParameter( "numeimmobili", nota.getNumeImmobili(), Hibernate.INTEGER );
                q.setParameter( "numesoggfavore", nota.getNumeSoggFavore(), Hibernate.SHORT );
                q.setParameter( "numesoggcontro", nota.getNumeSoggContro(), Hibernate.SHORT );
                q.setParameter( "descconservat", nota.getDescConservat(), Hibernate.STRING );
                q.setParameter( "flgnotacollegata", nota.getFlgNotaCollegata(), Hibernate.CHARACTER );
                q.setParameter( "flgstato", nota.getFlgStato(), Hibernate.CHARACTER );
                q.setParameter( "descutente", nota.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", nota.getDataUltAgg(), Hibernate.DATE );
                q.setParameter( "codikidconserv", nota.getTAnConservatorie().getCodiKIdConserv(), Hibernate.SHORT );
                q.setParameter( "codifktipocomunic", nota.getTTpTipoComunic().getCodiKTipoComunic(), Hibernate.STRING );

                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNota = (Long)row.get( "IDNOTA" );
                }
                return idNota;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long modificaNotaAnnot( final TNotaAnnot notaAnnot, final Long idNota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long idNotaAnnotaz = null;
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_modificaNotaAnnot" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "flgvoltura", notaAnnot.getFlgVoltura(), Hibernate.CHARACTER );
                q.setParameter( "dataformalita", notaAnnot.getDataFormalita(), Hibernate.DATE );
                q.setParameter( "numeformalita1", notaAnnot.getNumeFormalita1(), Hibernate.INTEGER );
                q.setParameter( "numeformalita2", notaAnnot.getNumeFormalita2(), Hibernate.SHORT );
                q.setParameter( "numevaloreimmint", notaAnnot.getNumeValoreImmInt(), Hibernate.LONG );
                q.setParameter( "numevaloreimmdec", notaAnnot.getNumeValoreImmDec(), Hibernate.BYTE );
                q.setParameter( "descutente", notaAnnot.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaAnnotaz = (Long)row.get( "IDNOTAANNOT" );
                }
                return idNotaAnnotaz;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long modificaNotaTrascr( final TNotaTrascr notaTrascr, final Long idNota, final int idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long idNotaTrascr = null;
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_modificaNotaTrascr" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "flgvoltura", notaTrascr.getFlgVoltura(), Hibernate.CHARACTER );
                q.setParameter( "datavolturadiff", notaTrascr.getDataVolturaDiff(), Hibernate.DATE );
                q.setParameter( "flgvolturadiff", notaTrascr.getFlgVolturaDiff(), Hibernate.CHARACTER );
                q.setParameter( "flgfoglioinfo", notaTrascr.getFlgFoglioInfo(), Hibernate.CHARACTER );
                q.setParameter( "flgcondizione", notaTrascr.getFlgCondizione(), Hibernate.CHARACTER );
                q.setParameter( "flgtermini", notaTrascr.getFlgTermini(), Hibernate.CHARACTER );
                q.setParameter( "dataformalita", notaTrascr.getDataFormalita(), Hibernate.DATE );
                q.setParameter( "numeformalita1", notaTrascr.getNumeFormalita1(), Hibernate.INTEGER );
                q.setParameter( "numeformalita2", notaTrascr.getNumeFormalita2(), Hibernate.SHORT );
                q.setParameter( "flgquadroda", notaTrascr.getFlgQuadrodA(), Hibernate.CHARACTER );
                q.setParameter( "flgquadrodb", notaTrascr.getFlgQuadrodB(), Hibernate.CHARACTER );
                q.setParameter( "flgquadrodc", notaTrascr.getFlgQuadrodC(), Hibernate.CHARACTER );

                q.setParameter( "descutente", notaTrascr.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = q.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaTrascr = (Long)row.get( "IDNOTATRASCR" );
                }
                return idNotaTrascr;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String eliminaNota( final Long idNota, final int idUffOwn )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_eliminaNota" );
                q.setParameter( "codikidnota", idNota, Hibernate.LONG );
                q.setParameter( "codikiduffowner", idUffOwn );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    @SuppressWarnings( "unchecked" )
    public String descTipoComunic( String tipoComunic )
    {

        String descTipoComunic = null;
        if( tipoComunic != null )
        {
            DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoComunic.class );
            Criterion rest1 = Restrictions.eq( "codiKTipoComunic", tipoComunic );
            criteria.add( rest1 );
            List<TTpTipoComunic> u = getHibernateTemplate().findByCriteria( criteria );
            TTpTipoComunic risultato = DataAccessUtils.requiredSingleResult( u );
            descTipoComunic = risultato.getDescTipoComunic();
        }
        return descTipoComunic;
    }
      //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String getStatoByBelfiore( final String descBelfiore )
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =
//                    " select T_AN_STATO.DESC_STATO as descStato  from T_AN_STATO where T_AN_STATO.DESC_BELFIORE = :descBelfiore ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "descStato", Hibernate.STRING );
//                sqlQuery.setParameter( "descBelfiore", descBelfiore, Hibernate.STRING );
//                List lista = sqlQuery.list();
//                if( !lista.isEmpty() )
//                    return lista.get( 0 );
//                else
//                return null;
//            }
//        } );
//    }
//
    @SuppressWarnings( "unchecked" )
    public String getStatoByBelfiore( String descBelfiore )
    {

        String descStato = null;
        if( descBelfiore != null )
        {
            DetachedCriteria criteria = DetachedCriteria.forClass( TAnStato.class );
            Criterion rest1 = Restrictions.eq( "descBelfiore", descBelfiore );
            criteria.add( rest1 );
            List<TAnStato> lista = getHibernateTemplate().findByCriteria( criteria );
            if (!lista.isEmpty()) {
                TAnStato risultato = DataAccessUtils.requiredSingleResult( lista );
                descStato = risultato.getDescStato();
            }
        }
        return descStato;
    }

    //GP modificato
    @SuppressWarnings( { "unchecked", "rawtypes" } ) 
    public ComuneProvinciaDto getComuneByBelfiore( final String codiceComune )
    {
        return (ComuneProvinciaDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = " select "
//                                     + " T_AN_COMUNI.DESC_COMUNE as comune, "
//                                     + " T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as provincia "
//                                     + " from   T_AN_COMUNI, T_AN_PROVINCIA "
//                                     + " where  T_AN_COMUNI.DESC_BELFIORE = :codiceComune "
//                                     + " and    T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV = T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "comune", Hibernate.STRING );
//                sqlQuery.addScalar( "provincia", Hibernate.STRING );
//                sqlQuery.setParameter( "codiceComune", codiceComune, Hibernate.STRING );
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_comune_by_belfiore" );
                query.setParameter( "codBelfiore", codiceComune, Hibernate.STRING );
                query.setResultTransformer( Transformers.aliasToBean( ComuneProvinciaDto.class ) );
                List lista = query.list();
                if( !lista.isEmpty() )
                    return lista.get( 0 );
                else
                return null;
            }
        } );
    }

    //GP modificato
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public ComuneProvinciaDto getComuneByIstat( final String codiceComune )
    {
        return (ComuneProvinciaDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = " select "
//                                     + " T_AN_COMUNI.DESC_COMUNE as comune, "
//                                     + " T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as provincia "
//                                     + " from   T_AN_COMUNI, T_AN_PROVINCIA "
//                                     + " where  T_AN_COMUNI.DESC_ISTAT_COMUNE = :codiceComune "
//                                     + " and    T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV = T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "comune", Hibernate.STRING );
//                sqlQuery.addScalar( "provincia", Hibernate.STRING );
//                sqlQuery.setParameter( "codiceComune", codiceComune, Hibernate.STRING );
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_comune_by_istat" );
                query.setParameter( "codIstat", codiceComune, Hibernate.STRING );
                query.setResultTransformer( Transformers.aliasToBean( ComuneProvinciaDto.class ) );
                List lista = query.list();
                if( !lista.isEmpty() )
                    return lista.get( 0 );
                else
                return null;
            }
        } );
    }

    //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String getStatoByDescIstat( final String descIstat )
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =
//                    " select T_AN_STATO.DESC_STATO as descStato "
//                        + " from T_AN_STATO where T_AN_STATO.DESC_ISTAT_STATO = :descIstat "; //  '220' = GRECIA
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "descStato", Hibernate.STRING );
//                sqlQuery.setParameter( "descIstat", descIstat, Hibernate.STRING );
//                List lista = sqlQuery.list();
//                if( !lista.isEmpty() )
//                    return lista.get( 0 );
//                else
//                return null;
//            }
//        } );
//    }

    @SuppressWarnings( "unchecked" )
    public String getStatoByDescIstat( String descIstat )
    {

        String descStato = null;
        if( descIstat != null )
        {
            DetachedCriteria criteria = DetachedCriteria.forClass( TAnStato.class );
            Criterion rest1 = Restrictions.eq( "descIstatStato", descIstat );
            criteria.add( rest1 );
            List<TAnStato> lista = getHibernateTemplate().findByCriteria( criteria );
            if (!lista.isEmpty()) {
                TAnStato risultato = DataAccessUtils.requiredSingleResult( lista );
                descStato = risultato.getDescStato();
            }
        }
        return descStato;
    }

    //GP modificato
//   @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer getNumSoggettiContro( final Long idNota, final int idUff )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = // presenza soggetti senza errore
//                    "   Select T_NOTA_SOGG.CODI_K_PROGR_SOGG "
//                        + " FROM T_NOTA_SOGG WHERE 1 = 1 "
//                        + " AND T_NOTA_SOGG.CODI_K_ID_NOTA          = :idNota "
//                        + " AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER     = :idUff "
//                        + " AND T_NOTA_SOGG.CODI_K_TIPO_RECORD      = '5' "
//                        + " AND T_NOTA_SOGG.FLG_ERR = '0' ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return lista.size();
//            }
//        } );
//    }
// PG_CONSERVATORIE_getNumSoggettiContro
  @SuppressWarnings( { "unchecked", "rawtypes" } )
  public Integer getNumSoggettiContro( final Long idNota, final int idUff )
  {
      return (Integer)getHibernateTemplate().execute( new HibernateCallback()
      {
          public Object doInHibernate( final Session session ) throws HibernateException, SQLException
          {
              Query query = session.getNamedQuery( "PG_CONSERVATORIE_getNumSoggettiContro" );
              query.setParameter( "idNota", idNota, Hibernate.LONG );
              query.setParameter( "idUff", idUff, Hibernate.INTEGER );
              List lista = query.list();
              return lista.size();  
          }
      } );
  }
    
//   @SuppressWarnings( "unchecked" )
//   public Integer getNumSoggettiContro(final Long idNota, final int idUff )
//   {
//
//           DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSogg.class );
//           Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
//           Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
//           Criterion rest3 = Restrictions.eq( "id.codiKTipoRecord", '5' );
//           Criterion rest4 = Restrictions.eq( "flgErr", '0' );
//           criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
//           List<TNotaSogg> u = getHibernateTemplate().findByCriteria( criteria );
//
//           return u.size();
//   }

   //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer getNumBeniUnitaNeg( final Long idNota, final int idUff )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =        // presenza beni senza errore
//                    "   Select T_NOTA_BENE.CODI_K_PROGR_BENE "
//                    + " FROM T_NOTA_BENE WHERE 1 = 1 "
//                    + " AND T_NOTA_BENE.CODI_K_ID_NOTA          = :idNota "
//                    + " AND T_NOTA_BENE.CODI_K_ID_UFF_OWNER     = :idUff "
//                    + " AND T_NOTA_BENE.FLG_ERR = '0' ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//
//                List lista = sqlQuery.list();
//                return lista.size();
//            }
//        } );
//    }
    
    @SuppressWarnings( "unchecked" )
    public Integer getNumBeniUnitaNeg(final Long idNota, final int idUff )
    {

            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaBene.class );
            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
            Criterion rest3 = Restrictions.eq( "flgErr", new Character('0') );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
            List<TNotaSogg> u = getHibernateTemplate().findByCriteria( criteria );

            return u.size();
    }

    //FG: fatto  
    private String controllaConiugi( final Session session, final Long idNota, final int idUff )
    {
//        String queryString =       // assenza di soggetti con coniuge non corrispondente ( CODI_K_PROGR_SOGG, CODI_FK_PROGR_SOGG_C) " 
//            " select soggetto from "
//            + "( "             
//            + "  select tpf.CODI_K_PROGR_SOGG as soggetto, tpf.CODI_FK_PROGR_SOGG_C "             
//            + "      FROM T_NOTA_SOGG_PF tpf, T_NOTA_SOGG tsg where 1 = 1 "
//            + "      AND tpf.CODI_K_ID_NOTA         = :idNota "
//            + "      AND tpf.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "      AND tpf.CODI_FK_ID_TIPO_REGIME = 'C' "
//            + "      AND tsg.CODI_K_ID_NOTA         = :idNota "
//            + "      AND tsg.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "      AND tsg.CODI_K_TIPO_RECORD     = '5' "
//            + "      AND tsg.CODI_K_PROGR_SOGG      = tpf.CODI_K_PROGR_SOGG "
//            + "      AND ( CODI_FK_PROGR_SOGG_C, tpf.CODI_K_PROGR_SOGG ) "             // inversione tra soggetto e coniuge
//            + "            NOT IN "   
//            + " ( "
//            + "  select tpf.CODI_K_PROGR_SOGG , tpf.CODI_FK_PROGR_SOGG_C "
//            + "      FROM T_NOTA_SOGG_PF tpf, T_NOTA_SOGG tsg where 1 = 1 "
//            + "      AND tpf.CODI_K_ID_NOTA         = :idNota "
//            + "      AND tpf.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "      AND tpf.CODI_FK_ID_TIPO_REGIME = 'C' "
//            + "      AND tsg.CODI_K_ID_NOTA         = :idNota "
//            + "      AND tsg.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "      AND tsg.CODI_K_TIPO_RECORD     = '5' "
//            + "      AND tsg.CODI_K_PROGR_SOGG      = tpf.CODI_K_PROGR_SOGG "
//            + "      AND tpf.CODI_K_PROGR_SOGG     != CODI_FK_PROGR_SOGG_C "         // controllo anche che nessuno sia sposato con se stesso
//            + " ) "
//            + ") order by soggetto ";
//        SQLQuery sqlQuery = session.createSQLQuery( queryString );
        
        Query query = session.getNamedQuery( "PG_CONSERVATORIE_controllaConiugi" );  // da' i numeri (letteralmente)

        query.setParameter( "idNota", idNota, Hibernate.LONG );
        query.setParameter( "idUff", idUff, Hibernate.INTEGER );
        List<?> lista = query.list();
        if( lista.size() > 0L )
        {
            String listino = " ";
            for( Object elemento : lista )
                listino += elemento + ", ";
            return "I soggetti n.: " + listino + " associati alla nota, risultano in comunione dei beni ma senza coniuge corrispondente.";
        }
        return "";
    }
    
    //FG fatto
    private String controllaBeniConiugi( final Session session, final Long idNota, final int idUff )
    {
//        String queryString =       // assenza di coniugi con beni non corrispondenti 
//            " select distinct soggetto from "
//            + "( select "
//            + "         TSB.CODI_K_PROGR_BENE, TPF.CODI_K_PROGR_SOGG as soggetto, TPF.CODI_FK_PROGR_SOGG_C, "
//            + "         TSB.CODI_K_ID_TIPO_INT, TSB.NUME_QUOTA_NUM_INT, TSB.NUME_QUOTA_NUM_DEC, TSB.NUME_QUOTA_DEN "
//            + "    FROM  T_NOTA_SOGG_PF TPF, T_NOTA_SOGG_BENE TSB, T_NOTA_SOGG tsg "
//            + "    WHERE 1 = 1"
//            + "    and TPF.CODI_K_ID_NOTA         = :idNota "
//            + "    and TPF.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and TPF.CODI_FK_ID_TIPO_REGIME = 'C' "
//            + "    and TSB.CODI_K_ID_NOTA         = :idNota "
//            + "    and TSB.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and TSB.CODI_K_PROGR_SOGG      = TPF.CODI_K_PROGR_SOGG "
//            + "    and tsg.CODI_K_ID_NOTA         = :idNota "
//            + "    and tsg.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and tsg.CODI_K_TIPO_RECORD     = '5' "
//            + "    and tsg.CODI_K_PROGR_SOGG      = TPF.CODI_K_PROGR_SOGG "
//            + "    and ( TSB.CODI_K_PROGR_BENE, TPF.CODI_FK_PROGR_SOGG_C, TPF.CODI_K_PROGR_SOGG, "     // inversione tra soggetto e coniuge
//            + "          TSB.CODI_K_ID_TIPO_INT, TSB.NUME_QUOTA_NUM_INT, "
//            + "          TSB.NUME_QUOTA_NUM_DEC, TSB.NUME_QUOTA_DEN )  NOT IN "
//            + "  ( select "
//            + "         TSB.CODI_K_PROGR_BENE, TPF.CODI_K_PROGR_SOGG, TPF.CODI_FK_PROGR_SOGG_C, "
//            + "         TSB.CODI_K_ID_TIPO_INT, TSB.NUME_QUOTA_NUM_INT, TSB.NUME_QUOTA_NUM_DEC, TSB.NUME_QUOTA_DEN "
//            + "    FROM  T_NOTA_SOGG_PF TPF, T_NOTA_SOGG_BENE TSB, T_NOTA_SOGG tsg "
//            + "    WHERE 1 = 1"
//            + "    and TPF.CODI_K_ID_NOTA         = :idNota "
//            + "    and TPF.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and TPF.CODI_FK_ID_TIPO_REGIME = 'C' "
//            + "    and TSB.CODI_K_ID_NOTA         = :idNota "
//            + "    and TSB.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and TSB.CODI_K_PROGR_SOGG      = TPF.CODI_K_PROGR_SOGG "
//            + "    and tsg.CODI_K_ID_NOTA         = :idNota "
//            + "    and tsg.CODI_K_ID_UFF_OWNER    = :idUff "
//            + "    and tsg.CODI_K_TIPO_RECORD     = '5' "
//            + "    and tsg.CODI_K_PROGR_SOGG      = TPF.CODI_K_PROGR_SOGG "
//            + "    and TPF.CODI_K_PROGR_SOGG     != TPF.CODI_FK_PROGR_SOGG_C "       // controllo anche che nessuno sia sposato con se stesso
//            + " ) "
//            + ") order by soggetto ";
//        SQLQuery sqlQuery = session.createSQLQuery( queryString );
//        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//        List<?> lista = sqlQuery.list();
        
        Query query = session.getNamedQuery( "PG_CONSERVATORIE_controllaBeniConiugi" );  // da' i numeri (letteralmente)
        query.setParameter( "idNota", idNota, Hibernate.LONG );
        query.setParameter( "idUff", idUff, Hibernate.INTEGER );
        List<?> lista = query.list();
        if( lista.size() > 0L )
        {
            String listino = " ";
            for( Object elemento : lista )
                listino += elemento + ", ";
            return "I soggetti n.: " + listino + 
                " associati alla nota, hanno beni in comunione che non sono posseduti dal coniuge allo stesso titolo e per le stesse quote.";
        }
        return "";
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public String controllaSituazProprieta( final Long idNota, final int idUff )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
    {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
        {
//FG fatto 
//              String queryString; SQLQuery query;   
//                queryString =       // presenza soggetti con errore
//                    "   Select CODI_K_PROGR_SOGG "
//                    + " FROM T_NOTA_SOGG WHERE 1 = 1 "
//                    + " AND CODI_K_ID_NOTA          = :idNota "
//                    + " AND CODI_K_ID_UFF_OWNER     = :idUff "
//                    + " AND CODI_K_TIPO_RECORD      = '5' "
//                    + " AND FLG_ERR is not null "
//                    + " AND FLG_ERR != '0' "
//                    + " order by CODI_K_PROGR_SOGG ";
//              SQLQuery sqlQuery = session.createSQLQuery( queryString );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_soggettiErrati" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                List lista = query.list();
                if( lista.size() > 0L )
                {
                    String listino = " ";
                    for( Object elemento : lista )
                        listino += elemento + ", ";
                    return "I soggetti n.: " + listino + " associati alla nota, sono in stato di errore.";
                }
//FG fatto 
//                queryString =       // presenza beni con errore
//                    "   Select CODI_K_PROGR_BENE "
//                    + " FROM T_NOTA_BENE WHERE 1 = 1 "
//                    + " AND CODI_K_ID_NOTA          = :idNota "
//                    + " AND CODI_K_ID_UFF_OWNER     = :idUff "
//                    + " AND FLG_ERR is not null "
//                    + " AND FLG_ERR != '0' "
//                    + " order by CODI_K_PROGR_BENE ";
//                sqlQuery = session.createSQLQuery( queryString );

                query = session.getNamedQuery( "PG_CONSERVATORIE_beniErrati" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                lista = query.list();
                if( lista.size() > 0L )
                {
                    String listino = " ";
                    for( Object elemento : lista )
                        listino += elemento + ", ";
                    return "I beni n.: " + listino + " associati alla nota, sono in stato di errore.";
                }
            
//FG fatto 
//                queryString =       // presenza beni per ogni soggetto
//                    "   Select CODI_K_PROGR_BENE "
//                    + " FROM T_NOTA_BENE WHERE 1 = 1 "
//                    + " AND CODI_K_ID_NOTA       = :idNota "
//                    + " AND CODI_K_ID_UFF_OWNER  = :idUff "
//                    + " AND FLG_ERR              = '0' "
//                    + " AND CODI_K_PROGR_BENE NOT IN "
//                    + " ( "
//                    + "     Select CODI_K_PROGR_BENE FROM T_NOTA_SOGG_BENE WHERE 1 = 1 "
//                    + "     AND CODI_K_ID_NOTA         = :idNota "
//                    + "     AND CODI_K_ID_UFF_OWNER    = :idUff "
//                    + "     AND CODI_K_TIPO_RECORD     = '5'"
//                    + " ) "
//                    + " order by CODI_K_PROGR_BENE ";
//                sqlQuery = session.createSQLQuery( queryString );
                
                query = session.getNamedQuery( "PG_CONSERVATORIE_beniSenzaSoggetto" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                lista = query.list();
                if( lista.size() > 0L )
                {
                    String listino = " ";
                    for( Object elemento : lista )
                        listino += elemento + ", ";
                    return "I beni n.: " + listino + " associati alla nota, non sono collegati a soggetti.";
                }
            
//FG fatto 
//                queryString =       // presenza soggetti per ogni bene  
//                    "   Select CODI_K_PROGR_SOGG "
//                    + " FROM T_NOTA_SOGG WHERE 1 = 1 "
//                    + " AND CODI_K_ID_NOTA       = :idNota "
//                    + " AND CODI_K_ID_UFF_OWNER  = :idUff "
//                    + " AND FLG_ERR              = '0' "
//                    + " AND CODI_K_PROGR_SOGG NOT IN "
//                    + " ( "
//                    + "     Select CODI_K_PROGR_SOGG FROM  T_NOTA_SOGG_BENE WHERE 1 = 1 "
//                    + "     AND CODI_K_ID_NOTA         = :idNota "
//                    + "     AND CODI_K_ID_UFF_OWNER    = :idUff "
//                    + "     AND CODI_K_TIPO_RECORD     = '5'"
//                    + " ) "
//                    + " order by CODI_K_PROGR_SOGG ";
//                sqlQuery = session.createSQLQuery( queryString );

                query = session.getNamedQuery( "PG_CONSERVATORIE_soggettiSenzaBene" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                lista = query.list();
                if( lista.size() > 0L )
                {
                    String listino = " ";
                    for( Object elemento : lista )
                        listino += elemento + ", ";
                    return "I soggetti n.: " + listino + " associati alla nota, non sono collegati a beni.";
                }
/*  
     22/05/2015 CAMERINI HA DETTO NO !
             
                String ret = controllaConiugi( session, idNota, idUff );
                if( ret.length() > 0 )
                    return ret;
            
                ret = controllaBeniConiugi( session, idNota, idUff );
                if( ret.length() > 0 )
                    return ret;
*/
                return "OK";
            }
        } );
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SituazioneProprietaDto> listaSituazProprieta( final Long idNota, final int idUff )
    {
        return (List<SituazioneProprietaDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = 
//                    "   Select distinct "
//                    + "     T_NOTA_SOGG.CODI_K_TIPO_RECORD          as idTipoRecord, "
//                    + "     T_NOTA_SOGG.CODI_K_PROGR_SOGG           as progrSoggetto, "
//                    + "     T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT     as idTipoInteresse, "
//                    + "     T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT     as quotaIntera, "
//                    + "     T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC     as quotaDecimale, "
//                    + "     T_NOTA_SOGG_BENE.NUME_QUOTA_DEN         as quotaDenom, "
//                    + "     T_NOTA_SOGG_BENE.FLG_TIPO_INT           as flagTipoInteresse, "
//                    + "     T_NOTA_SOGG_BENE.DESC_TIPO_INT          as descTipoInteresse, "
//                    + "     T_NOTA_SOGG_PF.CODI_FK_PROGR_SOGG_C     as progrSoggC, "
//                    + "     T_NOTA_SOGG_PF.CODI_FK_ID_TIPO_REGIME   as tipoRegime, "
//                    
//                    + "     TO_CHAR( T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE ) as progrBene, "
//                    + "     TO_CHAR( T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG ) as progrSogg, "
//                    + "     TO_CHAR( T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT ) as tipoInteresse, "
//                    + "     nvl( TRIM ( BOTH '.' FROM TRIM ( TRAILING '0' FROM "
//                    + "          TO_CHAR( nvl( T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT, 0 )  || '.' || nvl( T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC, 0 ) )"
//                    + "        )  ),'1'  ) as quotaNumeratore, "
//                    + "     TO_CHAR( nvl( T_NOTA_SOGG_BENE.NUME_QUOTA_DEN, 1 ) ) as quotaDenominatore, "
//                    + "     TO_CHAR( T_NOTA_SOGG_PF.DESC_CODI_FISCALE ) as codFis, "
//                    + "     TO_CHAR( T_NOTA_SOGG_PNF.DESC_CODI_FISCALE ) as partIva "
//                    + " FROM T_NOTA_SOGG "
//                    + " INNER JOIN T_NOTA_BENE "
//                    + "     ON  T_NOTA_BENE.CODI_K_ID_NOTA           = T_NOTA_SOGG.CODI_K_ID_NOTA "
//                    + "     AND T_NOTA_BENE.CODI_K_ID_UFF_OWNER      = T_NOTA_SOGG.CODI_K_ID_UFF_OWNER "
//                    + " INNER JOIN T_NOTA_SOGG_BENE "
//                    + "     ON  T_NOTA_SOGG_BENE.CODI_K_ID_NOTA      = T_NOTA_SOGG.CODI_K_ID_NOTA "
//                    + "     AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG.CODI_K_ID_UFF_OWNER "
//                    + "     AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = T_NOTA_SOGG.CODI_K_TIPO_RECORD "
//                    + "     AND T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG   = T_NOTA_SOGG.CODI_K_PROGR_SOGG "
//                    + "     AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = T_NOTA_BENE.CODI_K_PROGR_BENE "
//                    + " LEFT JOIN T_NOTA_SOGG_PF "
//                    + "     ON  T_NOTA_SOGG_PF.CODI_K_ID_NOTA        = T_NOTA_SOGG.CODI_K_ID_NOTA "
//                    + "     AND T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER   = T_NOTA_SOGG.CODI_K_ID_UFF_OWNER "
//                    + "     AND T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD    = T_NOTA_SOGG.CODI_K_TIPO_RECORD "
//                    + "     AND T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG     = T_NOTA_SOGG.CODI_K_PROGR_SOGG "
//                    + " LEFT JOIN T_NOTA_SOGG_PNF "
//                    + "     ON  T_NOTA_SOGG_PNF.CODI_K_ID_NOTA       = T_NOTA_SOGG.CODI_K_ID_NOTA "
//                    + "     AND T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER  = T_NOTA_SOGG.CODI_K_ID_UFF_OWNER "
//                    + "     AND T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD   = T_NOTA_SOGG.CODI_K_TIPO_RECORD "
//                    + "     AND T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG    = T_NOTA_SOGG.CODI_K_PROGR_SOGG "
//                    + " WHERE T_NOTA_SOGG.CODI_K_ID_NOTA        = :idNota "
//                    + " AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER     = :idUff "
//                    + " AND T_NOTA_SOGG.CODI_K_TIPO_RECORD      = '5' "
//                    + " AND T_NOTA_SOGG.FLG_ERR                 = '0' "
//                    + " AND T_NOTA_BENE.FLG_ERR                 = '0' ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//
//                sqlQuery.addScalar( "idTipoRecord", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "progrSoggetto", Hibernate.INTEGER );
//                sqlQuery.addScalar( "idTipoInteresse", Hibernate.INTEGER );
//                sqlQuery.addScalar( "quotaIntera", Hibernate.INTEGER );
//                sqlQuery.addScalar( "quotaDecimale", Hibernate.INTEGER );
//                sqlQuery.addScalar( "quotaDenom", Hibernate.INTEGER );
//                sqlQuery.addScalar( "flagTipoInteresse", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "descTipoInteresse", Hibernate.STRING );
//                sqlQuery.addScalar( "progrSoggC", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipoRegime", Hibernate.CHARACTER );
//                
//                sqlQuery.addScalar( "progrBene", Hibernate.STRING );
//                sqlQuery.addScalar( "progrSogg", Hibernate.STRING );
//                sqlQuery.addScalar( "tipoInteresse", Hibernate.STRING );
//                sqlQuery.addScalar( "quotaNumeratore", Hibernate.STRING );
//                sqlQuery.addScalar( "quotaDenominatore", Hibernate.STRING );
//                sqlQuery.addScalar( "codFis", Hibernate.STRING );
//                sqlQuery.addScalar( "partIva", Hibernate.STRING );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SituazioneProprietaDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaSituazProprieta" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUff, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SituazioneProprietaDto.class ) );
                List lista = query.list();
                return lista;
            }
        } );
    }

    //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer pulisciNotaUn(final Long idNota, final Integer idUffOwn )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_UN WHERE CODI_K_ID_NOTA = :idNota and CODI_K_ID_UFF_OWNER= :idUffOwn";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                int righe = sqlQuery.executeUpdate();
//                return righe;
//            }
//        } );
//    }
//    
    @SuppressWarnings("unchecked")
	public void pulisciNotaUn( Long idNota, Integer idUffOwn )
    {
            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaUn.class );
            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ) );
            List< TNotaUn > lista = getHibernateTemplate().findByCriteria(criteria);
            if (lista != null && lista.size() > 0) 
            {
                getHibernateTemplate().deleteAll(lista);  
                getHibernateTemplate().flush();
            }
     }

    //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer cancellaNotaSoggUn(final Long idNota, final Integer idUff, final Long progrSogg )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG_UN  WHERE  "
//                                        + " CODI_K_ID_NOTA          = :idNota "
//                                        + " and CODI_K_ID_UFF_OWNER = :idUff  "
//                                        + " and CODI_K_PROGR_SOGG   = :progrSogg "
//                                        + " and CODI_K_TIPO_RECORD  = '5'  ";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
//                int righe = sqlQuery.executeUpdate(); // cancellaNotaSoggUn
//                return righe;
//            }
//        } );
//    }

    @SuppressWarnings("unchecked")
	public void cancellaNotaSoggUn(final Long idNota, final Integer idUff, final Long progrSogg ) 
    {
            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggUn.class );
            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
            Criterion rest3 = Restrictions.eq( "id.codiKProgrSogg", progrSogg.shortValue() );
            Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
            List< TNotaSoggUn > lista = getHibernateTemplate().findByCriteria(criteria);
            if (lista != null && lista.size() > 0) 
            {
                getHibernateTemplate().deleteAll(lista);
                getHibernateTemplate().flush();
            }
     }

    //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer pulisciNotaSoggUn(final Long idNota, final Integer idUff )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG_UN  WHERE  "
//                                        + " CODI_K_ID_NOTA= :idNota "
//                                        + " and CODI_K_ID_UFF_OWNER= :idUff  "
//                                        + " and CODI_K_TIPO_RECORD= '5'  ";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                int righe = sqlQuery.executeUpdate(); // cancellaNotaSoggUn
//                return righe;
//            }
//        } );
//    }
//    
    @SuppressWarnings("unchecked")
	public void pulisciNotaSoggUn(final Long idNota, final Integer idUff )
    {
            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggUn.class );
            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
            Criterion rest3 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
            List< TNotaSoggUn > lista = getHibernateTemplate().findByCriteria(criteria);
            if (lista != null && lista.size() > 0) 
            {
                getHibernateTemplate().deleteAll(lista);  
                getHibernateTemplate().flush();
            }
     }
	 
    //FG: fatto
//  @SuppressWarnings( { "unchecked", "rawtypes" } )
//  public Integer pulisciNotaBeneUN( final Long idNota, final int idUff )
//  {
//      return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//      {
//          public Integer doInHibernate( final Session session ) throws HibernateException, SQLException
//          {
//              String queryString = 
//                  "UPDATE T_NOTA_BENE  SET "
//                   + " CODI_FK_PROGR_UNEG     = null, "
//                   + " NUME_PROGR_UNEG_BENE   = null "
//                   + " where CODI_K_ID_UFF_OWNER  = :idUff "
//                   + " and  CODI_K_ID_NOTA       = :idNota ";
//              SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//              sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//              sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//              int righe = sqlQuery.executeUpdate(); // pulisciNotaBeneUN
//              return righe;
//          }
//      } );
//  }
  
  @SuppressWarnings( { "unchecked", "rawtypes" } )
  public Integer pulisciNotaBeneUN( final Long idNota, final int idUff )
  {
      return (Integer)getHibernateTemplate().execute( new HibernateCallback()
      {
          public Integer doInHibernate( final Session session ) throws HibernateException, SQLException
          {
              Query query = session.getNamedQuery( "PG_CONSERVATORIE_pulisciNotaBeneUN" );
              query.setParameter( "idNota", idNota, Hibernate.LONG );
              query.setParameter( "idUff", idUff, Hibernate.INTEGER );
              List lista = query.list();
              return lista.size();   
          }
      } );
  }
    
    //GP modificato
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer inserisciNotaUn( final Long idNota, final Integer idUff, final Integer progrUn, final String utente  )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "INSERT INTO T_NOTA_UN   "
//                                     + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_PROGR_UNEG, DESC_UTENTE, DATA_ULT_AGG )"
//                                     + " VALUES (:idNota, :idUff, :progrUn, :utente, SYSDATE )";
//
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrUn", progrUn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                int righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_UN  ---
//                return righe;
//            }
//        } );
//    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String inserisciNotaUn( final Long idNota, final Integer idUff, final Integer progrUn, final String utente  )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Date dataOggi = new Date();
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_inserisci_nota_un" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUff, Hibernate.INTEGER );
                query.setParameter( "progrUn", progrUn, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                query.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = query.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }
    
    //GP modificato
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaUn( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente  )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString = "SELECT CODI_K_PROGR_UNEG FROM T_NOTA_UN   " 
//                    + " WHERE CODI_K_ID_NOTA = :oldIdNota "
//                    + " AND   CODI_K_ID_UFF_OWNER = :idUff ";
//                SQLQuery selectQuery = getSession().createSQLQuery( selectString );
//                selectQuery.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
//                selectQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_lista_progr_uneg" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                List lista = query.list();
                if( lista.isEmpty() )
                    return null;
                else
                {
                    for( Object progr : lista )
                    {
                    	Integer progUn = Integer.valueOf(progr.toString());
                    	inserisciNotaUn(idNota, idUff, progUn,utente  );
//                    	String queryString = "INSERT INTO T_NOTA_UN   "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_PROGR_UNEG, DESC_UTENTE, DATA_ULT_AGG )"
//                            + " VALUES (:idNota, :idUff, :progrUn, :utente, SYSDATE )";
//        
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        sqlQuery.setParameter( "progrUn", progrUn, Hibernate.BIG_DECIMAL );
//                        righe = sqlQuery.executeUpdate(); // riproponi Nota Un T_NOTA_UN  
//                        totRighe += righe;
                    }
                }
                return lista.size();
            }
        } );
    }
    
    //FG: fatto 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer riproponiNotaSogg( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente  )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString =  
//                      " SELECT * FROM  T_NOTA_SOGG soggetti"
//                    + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                    + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//
//                SQLQuery selectQuery = session.createSQLQuery( selectString );
//                selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggettoDto.class ) );
//                selectQuery.addScalar( "CODI_K_TIPO_RECORD", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_K_PROGR_SOGG", Hibernate.INTEGER );
//                selectQuery.addScalar( "FLG_TIPO_SOGG", Hibernate.CHARACTER );
//                selectQuery.addScalar( "FLG_ERR", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_FK_ID_SOGG_INT", Hibernate.LONG );
//
//                List<NotaSoggettoDto> lista = selectQuery.list();
//                if( lista.isEmpty() )
//                    return null;
//                else
//                {
//                    for( NotaSoggettoDto soggetto : lista )
//                    {
//                        soggetto.setDATA_ULT_AGG( new Date() );
//                        soggetto.setDESC_UTENTE( utente );
//                        soggetto.setCODI_K_ID_NOTA( idNota );
//                        String queryString = 
//                              " INSERT INTO T_NOTA_SOGG   "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG,"
//                            + "   FLG_TIPO_SOGG, FLG_ERR, DESC_UTENTE, DATA_ULT_AGG, CODI_FK_ID_SOGG_INT )"
//                            + " VALUES (:idNota, :idUff, :tipoRec, :progrSogg, :tipoSogg, :flgErr, :utente, SYSDATE, :idSogg )";
//        
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoRec", soggetto.getCODI_K_TIPO_RECORD(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "progrSogg", soggetto.getCODI_K_PROGR_SOGG(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoSogg", soggetto.getFLG_TIPO_SOGG(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "flgErr", soggetto.getFLG_ERR(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        sqlQuery.setParameter( "idSogg", soggetto.getCODI_FK_ID_SOGG_INT(), Hibernate.LONG );
//                        righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                        totRighe += righe;
//                    }
//                }
//                return lista.size();
//            }
//        } );
//    }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaSogg( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaSogg" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }

    //FG: fatto 
//  @SuppressWarnings( { "unchecked", "rawtypes" } )
//  public Integer riproponiNotaSoggPF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
//  {
//      return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//      {
//          public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//          {
//              Integer righe = 0;
//              Integer totRighe = 0;
//              String selectString =  
//                    " SELECT * FROM  T_NOTA_SOGG_PF soggetti"
//                  + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                  + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//
//              SQLQuery selectQuery = session.createSQLQuery( selectString );
//              selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggePersUnBeniDto.class ) );
//              selectQuery.addScalar( "CODI_K_TIPO_RECORD", Hibernate.CHARACTER );
//              selectQuery.addScalar( "CODI_K_PROGR_SOGG", Hibernate.INTEGER );
//              selectQuery.addScalar( "DESC_COGNOME", Hibernate.STRING );
//              selectQuery.addScalar( "DESC_NOME", Hibernate.STRING );
//              selectQuery.addScalar( "FLG_SESSO", Hibernate.CHARACTER );
//              selectQuery.addScalar( "DATA_NASC", Hibernate.DATE );
//              selectQuery.addScalar( "DESC_CODI_COMUNE_NASC", Hibernate.STRING );
//              selectQuery.addScalar( "DESC_COMUNE_NASC", Hibernate.STRING );
//              selectQuery.addScalar( "DESC_PROV_NASC", Hibernate.STRING );
//              selectQuery.addScalar( "DESC_CODI_FISCALE", Hibernate.STRING );
//              selectQuery.addScalar( "CODI_FK_ID_TIPO_REGIME", Hibernate.CHARACTER );
//              selectQuery.addScalar( "CODI_FK_PROGR_SOGG_C", Hibernate.INTEGER );
//
//              List<NotaSoggePersUnBeniDto> lista = selectQuery.list();
//              if( lista.isEmpty() )
//                  return null;
//              else
//              {
//                  for( NotaSoggePersUnBeniDto soggetto : lista )
//                  {
//                      soggetto.setDATA_ULT_AGG( new Date() );
//                      soggetto.setDESC_UTENTE( utente );
//                      soggetto.setCODI_K_ID_NOTA( idNota );
//                      String queryString = 
//                            " INSERT INTO T_NOTA_SOGG_PF   "
//                          + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG,"
//                          + "   DESC_COGNOME, DESC_NOME, FLG_SESSO, DATA_NASC, DESC_CODI_COMUNE_NASC, DESC_COMUNE_NASC, "
//                          + "   DESC_PROV_NASC, DESC_CODI_FISCALE, CODI_FK_ID_TIPO_REGIME, CODI_FK_PROGR_SOGG_C, "
//                          + "   DESC_UTENTE, DATA_ULT_AGG )"
//                          + " VALUES (:idNota, :idUff, :tipoRec, :progrSogg, :cognome, :nome, :sesso, :dataNasc, :codComuneNasc, "
//                          + "         :comuneNasc, :provNasc, :codFiscale, :regime, :coniuge, :utente, SYSDATE )";
//      
//                      SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                      sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                      sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                      sqlQuery.setParameter( "tipoRec", soggetto.getCODI_K_TIPO_RECORD(), Hibernate.CHARACTER );
//                      sqlQuery.setParameter( "progrSogg", soggetto.getCODI_K_PROGR_SOGG(), Hibernate.INTEGER );
//                      sqlQuery.setParameter( "cognome", soggetto.getDESC_COGNOME(), Hibernate.STRING );
//                      sqlQuery.setParameter( "nome", soggetto.getDESC_NOME(), Hibernate.STRING );
//                      sqlQuery.setParameter( "sesso", soggetto.getFLG_SESSO(), Hibernate.CHARACTER );
//                      sqlQuery.setParameter( "dataNasc", soggetto.getDATA_NASC(), Hibernate.DATE );
//                      sqlQuery.setParameter( "codComuneNasc", soggetto.getDESC_CODI_COMUNE_NASC(), Hibernate.STRING );
//                      sqlQuery.setParameter( "comuneNasc", soggetto.getDESC_COMUNE_NASC(), Hibernate.STRING );
//                      sqlQuery.setParameter( "provNasc", soggetto.getDESC_PROV_NASC(), Hibernate.STRING );
//                      sqlQuery.setParameter( "codFiscale", soggetto.getDESC_CODI_FISCALE(), Hibernate.STRING );
//                      sqlQuery.setParameter( "regime", soggetto.getCODI_FK_ID_TIPO_REGIME(), Hibernate.CHARACTER );
//                      sqlQuery.setParameter( "coniuge", soggetto.getCODI_FK_PROGR_SOGG_C(), Hibernate.INTEGER );
//                      sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                      righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                      totRighe += righe;
//                  }
//              }
//              return lista.size();
//          }
//      } );
//  }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaSoggPF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaSoggPF" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG fatto
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer riproponiNotaSoggPNF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString =  
//                      " SELECT * FROM  T_NOTA_SOGG_PNF soggetti"
//                    + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                    + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//
//                SQLQuery selectQuery = session.createSQLQuery( selectString );
//                selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggePersUnBeniDto.class ) );
//                selectQuery.addScalar( "CODI_K_TIPO_RECORD", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_K_PROGR_SOGG", Hibernate.INTEGER );
//                selectQuery.addScalar( "DESC_DENOMINAZIONE", Hibernate.STRING );
//                selectQuery.addScalar( "DESC_CODI_COMUNE_SEDE", Hibernate.STRING );
//                selectQuery.addScalar( "DESC_COMUNE_SEDE", Hibernate.STRING );
//                selectQuery.addScalar( "DESC_PROV_SEDE", Hibernate.STRING );
//                selectQuery.addScalar( "DESC_CODI_FISCALE", Hibernate.STRING );
//
//                List<NotaSoggePersUnBeniDto> lista = selectQuery.list();
//                if( lista.isEmpty() )
//                    return null;
//                else
//                {
//                    for( NotaSoggePersUnBeniDto soggetto : lista )
//                    {
//                        soggetto.setDATA_ULT_AGG( new Date() );
//                        soggetto.setDESC_UTENTE( utente );
//                        soggetto.setCODI_K_ID_NOTA( idNota );
//                        String queryString = 
//                              " INSERT INTO T_NOTA_SOGG_PNF "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG,"
//                            + "   DESC_DENOMINAZIONE, DESC_CODI_COMUNE_SEDE, DESC_COMUNE_SEDE, DESC_PROV_SEDE, "
//                            + "   DESC_CODI_FISCALE, DESC_UTENTE, DATA_ULT_AGG )"
//                            + " VALUES (:idNota, :idUff, :tipoRec, :progrSogg, :denominazione, :codComuneSede, "
//                            + "         :comuneSede, :provSede, :codFiscale, :utente, SYSDATE )";
//        
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoRec", soggetto.getCODI_K_TIPO_RECORD(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "progrSogg", soggetto.getCODI_K_PROGR_SOGG(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "denominazione", soggetto.getDESC_DENOMINAZIONE(), Hibernate.STRING );
//                        sqlQuery.setParameter( "codComuneSede", soggetto.getDESC_NOME(), Hibernate.STRING );
//                        sqlQuery.setParameter( "comuneSede", soggetto.getFLG_SESSO(), Hibernate.STRING );
//                        sqlQuery.setParameter( "provSede", soggetto.getDATA_NASC(), Hibernate.STRING );
//                        sqlQuery.setParameter( "codFiscale", soggetto.getDESC_CODI_FISCALE(), Hibernate.STRING );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                        totRighe += righe;
//                    }
//                }
//                return lista.size();
//            }
//        } );
//    }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaSoggPNF( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaSoggPNF" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer riproponiNotaSoggUn1( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString =  
//                      " SELECT * FROM  T_NOTA_SOGG_UN soggetti"
//                    + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                    + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//
//                SQLQuery selectQuery = session.createSQLQuery( selectString );
//                selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggePersUnBeniDto.class ) );
//                selectQuery.addScalar( "CODI_K_TIPO_RECORD", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_K_PROGR_SOGG", Hibernate.INTEGER );
//
//                selectQuery.addScalar( "CODI_K_PROGR_UNEG", Hibernate.INTEGER );
//                selectQuery.addScalar( "CODI_K_ID_TIPO_INT", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_PROGR_RIGA", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_NUM_INT", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_NUM_DEC", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_DEN", Hibernate.INTEGER );
//                selectQuery.addScalar( "FLG_TIPO_INT", Hibernate.CHARACTER );
//                selectQuery.addScalar( "DESC_TIPO_INT", Hibernate.STRING );
//                selectQuery.addScalar( "CODI_FK_PROGR_SOGG_C", Hibernate.INTEGER );
//                selectQuery.addScalar( "CODI_FK_ID_TIPO_REGIME", Hibernate.CHARACTER );
//                List<NotaSoggePersUnBeniDto> lista = selectQuery.list();
//                if( lista.isEmpty() )
//                    return null;
//                else
//                {
//                    for( NotaSoggePersUnBeniDto soggetto : lista )
//                    {
//                        soggetto.setDATA_ULT_AGG( new Date() );
//                        soggetto.setDESC_UTENTE( utente );
//                        soggetto.setCODI_K_ID_NOTA( idNota );
//                        String queryString = 
//                              " INSERT INTO T_NOTA_SOGG_UN "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG,"
//                            + "   CODI_K_PROGR_UNEG, CODI_K_ID_TIPO_INT, NUME_PROGR_RIGA, NUME_QUOTA_NUM_INT, NUME_QUOTA_NUM_DEC, "
//                            + "   NUME_QUOTA_DEN, FLG_TIPO_INT, DESC_TIPO_INT, CODI_FK_PROGR_SOGG_C, CODI_FK_ID_TIPO_REGIME, "
//                            + "   DESC_UTENTE, DATA_ULT_AGG )"
//                            + " VALUES (:idNota, :idUff, :tipoRec, :progrSogg, "
//                            + "         :uniNegoz, :idTipoInt, :progrRiga, :quotaInt, :quotaDec, "
//                            + "         :quotaDen, :flgTipoInt, :descTipoInt, :progrSoggC, :regime, "
//                            + "         :utente, SYSDATE )";
//        
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoRec", soggetto.getCODI_K_TIPO_RECORD(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "progrSogg", soggetto.getCODI_K_PROGR_SOGG(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "uniNegoz", soggetto.getCODI_K_PROGR_UNEG(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "idTipoInt", soggetto.getCODI_K_ID_TIPO_INT(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "progrRiga", soggetto.getNUME_PROGR_RIGA(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaInt", soggetto.getNUME_QUOTA_NUM_INT(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaDec", soggetto.getNUME_QUOTA_NUM_DEC(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaDen", soggetto.getNUME_QUOTA_DEN(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "flgTipoInt", soggetto.getFLG_TIPO_INT(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "descTipoInt", soggetto.getDESC_TIPO_INT(), Hibernate.STRING );
//                        sqlQuery.setParameter( "progrSoggC", soggetto.getCODI_FK_PROGR_SOGG_C(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "regime", soggetto.getCODI_FK_ID_TIPO_REGIME(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                        totRighe += righe;
//                    }
//                }
//                return lista.size();
//            }
//        } );
//    }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaSoggUn( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaSoggUn" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer riproponiNotaSoggBene1( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString =  
//                      " SELECT * FROM  T_NOTA_SOGG_BENE soggetti"
//                    + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                    + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//
//                SQLQuery selectQuery = session.createSQLQuery( selectString );
//                selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggePersUnBeniDto.class ) );
//                selectQuery.addScalar( "CODI_K_TIPO_RECORD", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_K_PROGR_SOGG", Hibernate.INTEGER );
//
//                selectQuery.addScalar( "CODI_K_PROGR_BENE", Hibernate.INTEGER );
//                selectQuery.addScalar( "CODI_K_ID_TIPO_INT", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_PROGR_RIGA", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_NUM_INT", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_NUM_DEC", Hibernate.INTEGER );
//                selectQuery.addScalar( "NUME_QUOTA_DEN", Hibernate.INTEGER );
//                selectQuery.addScalar( "FLG_TIPO_INT", Hibernate.CHARACTER );
//                selectQuery.addScalar( "DESC_TIPO_INT", Hibernate.STRING );
//                List<NotaSoggePersUnBeniDto> lista = selectQuery.list();
//                if( lista.isEmpty() )
//                    return null;
//                else
//                {
//                    for( NotaSoggePersUnBeniDto soggetto : lista )
//                    {
//                        soggetto.setDATA_ULT_AGG( new Date() );
//                        soggetto.setDESC_UTENTE( utente );
//                        soggetto.setCODI_K_ID_NOTA( idNota );
//                        String queryString = 
//                              " INSERT INTO T_NOTA_SOGG_BENE "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG,"
//                            + "   CODI_K_PROGR_BENE, CODI_K_ID_TIPO_INT, NUME_PROGR_RIGA, NUME_QUOTA_NUM_INT, NUME_QUOTA_NUM_DEC, "
//                            + "   NUME_QUOTA_DEN, FLG_TIPO_INT, DESC_TIPO_INT, DESC_UTENTE, DATA_ULT_AGG )"
//                            + " VALUES (:idNota, :idUff, :tipoRec, :progrSogg, "
//                            + "         :progrBene, :idTipoInt, :progrRiga, :quotaInt, :quotaDec, "
//                            + "         :quotaDen, :flgTipoInt, :descTipoInt, :utente, SYSDATE )";
//        
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoRec", soggetto.getCODI_K_TIPO_RECORD(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "progrSogg", soggetto.getCODI_K_PROGR_SOGG(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "progrBene", soggetto.getCODI_K_PROGR_BENE(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "idTipoInt", soggetto.getCODI_K_ID_TIPO_INT(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "progrRiga", soggetto.getNUME_PROGR_RIGA(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaInt", soggetto.getNUME_QUOTA_NUM_INT(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaDec", soggetto.getNUME_QUOTA_NUM_DEC(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "quotaDen", soggetto.getNUME_QUOTA_DEN(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "flgTipoInt", soggetto.getFLG_TIPO_INT(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "descTipoInt", soggetto.getDESC_TIPO_INT(), Hibernate.STRING );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                        totRighe += righe;
//                    }
//                }
//                return lista.size();
//            }
//        } );
//    }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaSoggBene( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaSoggBene" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer riproponiNotaQuadroD( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Integer righe = 0;
//                Integer totRighe = 0;
//                String selectString =  
//                      " SELECT * FROM  T_NOTA_QUADRO_D soggetti"
//                    + " WHERE soggetti.CODI_K_ID_NOTA = " + oldIdNota 
//                    + " AND   soggetti.CODI_K_ID_UFF_OWNER = " + idUff ;
//                SQLQuery selectQuery = session.createSQLQuery( selectString );
//                selectQuery.setResultTransformer( Transformers.aliasToBean( NotaSoggePersUnBeniDto.class ) );
//                selectQuery.addScalar( "CODI_K_TIPO_INF", Hibernate.CHARACTER );
//                selectQuery.addScalar( "CODI_K_PROGR_D", Hibernate.INTEGER );
//                selectQuery.addScalar( "DESC_NOTA", Hibernate.STRING );
//                List<NotaSoggePersUnBeniDto> lista = selectQuery.list();
//                if( lista.isEmpty() )
//                    return null;
//                else
//                {
//                    for( NotaSoggePersUnBeniDto soggetto : lista )
//                    {
//                        soggetto.setDATA_ULT_AGG( new Date() );
//                        soggetto.setDESC_UTENTE( utente );
//                        soggetto.setCODI_K_ID_NOTA( idNota );
//                        String queryString = 
//                              " INSERT INTO T_NOTA_QUADRO_D "
//                            + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_INF, CODI_K_PROGR_D, DESC_NOTA, DESC_UTENTE, DATA_ULT_AGG )"
//                            + " VALUES (:idNota, :idUff, :tipoInf, :progrD, :descNota, :utente, SYSDATE )";
//                        SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                        sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                        sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                        sqlQuery.setParameter( "tipoInf", soggetto.getCODI_K_TIPO_INF(), Hibernate.CHARACTER );
//                        sqlQuery.setParameter( "progrD", soggetto.getCODI_K_PROGR_D(), Hibernate.INTEGER );
//                        sqlQuery.setParameter( "descNota", soggetto.getDESC_NOTA(), Hibernate.STRING );
//                        sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                        righe = sqlQuery.executeUpdate(); // inserisci T_NOTA_SOGG 
//                        totRighe += righe;
//                    }
//                }
//                return lista.size();
//            }
//        } );
//    }
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer riproponiNotaQuadroD( final Long oldIdNota, final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_riproponiNotaQuadroD" );
                query.setParameter( "oldIdNota", oldIdNota, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer aggiornaNotaBeneUN( 
        final Long idNota, final int idUff, final int progrUn, final int progrRiga, final int progrBene, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Integer doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = 
//                    "UPDATE T_NOTA_BENE  SET "
//                     + " CODI_FK_PROGR_UNEG     = :progrUn, "
//                     + " NUME_PROGR_UNEG_BENE   = :progrRiga, "
//                     + " DESC_UTENTE            = :utente, "
//                     + " DATA_ULT_AGG           = SYSDATE "
//                     + " where 1 = 1 "
//                     + " and  CODI_K_ID_UFF_OWNER  = :idUff "
//                     + " and  CODI_K_ID_NOTA       = :idNota "
//                     + " and  CODI_K_PROGR_BENE    = :progrBene ";
//                int righe = sqlQuery.executeUpdate(); // aggiornaNotaBeneUN
//                return righe;
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_aggiornaNotaBeneUN" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "progrUn", progrUn, Hibernate.INTEGER );
                query.setParameter( "progrRiga", progrRiga, Hibernate.INTEGER );
                query.setParameter( "progrBene", progrBene, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer aggiornaUnNota( 
        final Long idNota, final int idUff, final int soggContro, final int beniInteressati, final int unitaNeg, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Integer doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = 
//                    " UPDATE T_NOTA SET "
//                    + " NUME_IMMOBILI       = :beniInteressati, "
//                    + " NUME_SOGG_CONTRO    = :soggContro, "
//                    + " NUME_SOGG_FAVORE    = 1, "
//                    + " NUME_UNIT_NEG       = :unitaNeg, "
//                    + " FLG_STATO           = '1', "
//                    + " DESC_UTENTE         = :utente, "
//                    + " DATA_ULT_AGG        = SYSDATE "
//                    + " where 1 = 1 "
//                    + " AND CODI_K_ID_NOTA          = :idNota "
//                    + " AND CODI_K_ID_UFF_OWNER     = :idUff ";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                int righe = sqlQuery.executeUpdate(); // pulisciNotaBeneUN
//                return righe;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_aggiornaUnNota" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "soggContro", soggContro, Hibernate.INTEGER );
                query.setParameter( "beniInteressati", beniInteressati, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "unitaNeg", unitaNeg, Hibernate.INTEGER );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer inserisciNotaSoggettoUn( 
        final Long idNota, final int idUff, final String utente, final int progrUn, final int progrRiga, final SituazioneProprietaDto situazione )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Integer doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "INSERT INTO T_NOTA_SOGG_UN   "
//                                     + " (  CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, "
//                                     + "    CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG, "
//                                     + "    CODI_K_PROGR_UNEG, CODI_K_ID_TIPO_INT, NUME_PROGR_RIGA, "
//                                     + "    NUME_QUOTA_NUM_INT, NUME_QUOTA_NUM_DEC, NUME_QUOTA_DEN, "
//                                     + "    FLG_TIPO_INT, DESC_TIPO_INT, CODI_FK_PROGR_SOGG_C, CODI_FK_ID_TIPO_REGIME, "
//                                     + "    DESC_UTENTE, DATA_ULT_AGG )"
//                                     + " VALUES ("
//                                     + "    :idNota, :idUff, '5', :progrSogg, :progrUn, :idTipoInt, :progrRiga, :interoQuota, "
//                                     + "    :decimaleQuota, :denomQuota, :flagTipoInt, :descTipoInt, "
//                                     + "    :idProgrSoggC, :idTipoRegime, :utente, SYSDATE )";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//              int righe = sqlQuery.executeUpdate(); // inserisci SoggettoTNotaSogg  ---
//              return righe;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaSoggettoUn" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "progrSogg", situazione.getPROGRSOGGETTO(), Hibernate.BIG_DECIMAL );
                query.setParameter( "progrUn", progrUn, Hibernate.INTEGER );
                query.setParameter( "idTipoInt", situazione.getIDTIPOINTERESSE(), Hibernate.BIG_DECIMAL );
                query.setParameter( "progrRiga", progrRiga, Hibernate.INTEGER );
                query.setParameter( "interoQuota", situazione.getQUOTAINTERA(), Hibernate.BIG_DECIMAL );
                query.setParameter( "decimaleQuota", situazione.getQUOTADECIMALE(), Hibernate.BIG_DECIMAL );
                query.setParameter( "denomQuota", situazione.getQUOTADENOM(), Hibernate.BIG_DECIMAL );
                query.setParameter( "flagTipoInt", situazione.getFLAGTIPOINTERESSE(), Hibernate.CHARACTER );
                query.setParameter( "descTipoInt", situazione.getDESCTIPOINTERESSE(), Hibernate.STRING );
                query.setParameter( "idProgrSoggC", situazione.getPROGRSOGGC(), Hibernate.BIG_DECIMAL );
                query.setParameter( "idTipoRegime", situazione.getTIPOREGIME(), Hibernate.CHARACTER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPersoneFisiche( final Long idNota, final int idUffOwn )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    "Select T_NOTA_SOGG.CODI_K_PROGR_SOGG as progressivo, "
//                        + "T_NOTA_SOGG.FLG_TIPO_SOGG as tipologia, "
//                        + "CASE "
//                        + "   WHEN T_NOTA_SOGG_PF.FLG_SESSO = 'F' THEN "
//                        + "      T_NOTA_SOGG_PF.DESC_COGNOME     || ' '    || T_NOTA_SOGG_PF.DESC_NOME || ' nata a ' || "
//                        + "      T_NOTA_SOGG_PF.DESC_COMUNE_NASC || ' il ' || T_NOTA_SOGG_PF.DATA_NASC "
//                        + "   ELSE "
//                        + "      T_NOTA_SOGG_PF.DESC_COGNOME     || ' '    || T_NOTA_SOGG_PF.DESC_NOME || ' nato a ' || "
//                        + "      T_NOTA_SOGG_PF.DESC_COMUNE_NASC || ' il ' || T_NOTA_SOGG_PF.DATA_NASC "
//                        + "   END  as soggetto, "
//                        + "T_NOTA_SOGG.CODI_FK_ID_SOGG_INT as soggettoSitMp, "
//                        + "T_NOTA_SOGG.FLG_ERR as flagErrore "
//                        + "from   T_NOTA_SOGG_PF, T_NOTA_SOGG "
//                        + "where  1 = 1 "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG.FLG_TIPO_SOGG = 'F' "
//                        + "and    T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PF.CODI_K_ID_NOTA "
//                        + "order  by T_NOTA_SOGG_PF.DESC_COGNOME ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "progressivo", Hibernate.LONG );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.addScalar( "flagErrore", Hibernate.CHARACTER );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return lista;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Lista_Persone_Fisiche" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list();
                return lista;

            }
        } );
    }
    
    //GP modificato-eliminato in quanto incorporato nella listaPersoneFisiche 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public List<SoggettoInteressatoDto> listaPersoneFisicheCompleta( final Long idNota, final int idUff )
//    {
//        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =
//                    "Select T_NOTA_SOGG.CODI_K_PROGR_SOGG as progressivo, "
//                        + "T_NOTA_SOGG.FLG_TIPO_SOGG as tipologia, "
//                        + "T_NOTA_SOGG.CODI_FK_ID_SOGG_INT as soggettoSitMp, "
//                        + "T_NOTA_SOGG.FLG_ERR as flagErrore, "
//						+ "T_NOTA_SOGG_PF.DESC_COGNOME     AS cognome, "
//						+ "T_NOTA_SOGG_PF.DESC_NOME        AS nome, "
//                        + "T_NOTA_SOGG_PF.FLG_SESSO        AS flgSesso, "
//                        + "T_NOTA_SOGG_PF.DATA_NASC        AS dataNascita, "
//                        + "T_NOTA_SOGG_PF.DESC_COMUNE_NASC AS comuneNascita, "
//                        + "T_NOTA_SOGG_PF.DESC_PROV_NASC   AS provNascita, "
//                        + "T_NOTA_SOGG_PF.DESC_CODI_FISCALE AS codiceFiscalePF, "
//                        + "T_NOTA_SOGG_PF.CODI_FK_ID_TIPO_REGIME AS regime "
//                        + "from   T_NOTA_SOGG_PF, T_NOTA_SOGG "
//                        + "where  1 = 1 "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG.FLG_TIPO_SOGG = 'F' "
//                        + "and    T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PF.CODI_K_ID_NOTA "
//                        + "order  by T_NOTA_SOGG_PF.DESC_COGNOME ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "progressivo", Hibernate.LONG );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "cognome", Hibernate.STRING );
//                sqlQuery.addScalar( "nome", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.addScalar( "flagErrore", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "flgSesso", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "dataNascita", Hibernate.DATE );
//                sqlQuery.addScalar( "comuneNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "provNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "codiceFiscalePF", Hibernate.STRING );
//                sqlQuery.addScalar( "regime", Hibernate.CHARACTER );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = query.list();
//                return lista;
//            }
//        } );
//    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPersoneGiurid( final Long idNota, final Integer idUffOwn )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    "Select T_NOTA_SOGG.CODI_K_PROGR_SOGG as progressivo, "
//                        + "T_NOTA_SOGG.FLG_TIPO_SOGG as tipologia, "
//                        + "( T_NOTA_SOGG_PNF.DESC_DENOMINAZIONE || ' a ' || T_NOTA_SOGG_PNF.DESC_COMUNE_SEDE || ' ' ) as soggetto, "
//                        + "T_NOTA_SOGG.CODI_FK_ID_SOGG_INT as soggettoSitMp, "
//                        + "T_NOTA_SOGG.FLG_ERR as flagErrore "
//                        + "from   T_NOTA_SOGG_PNF, T_NOTA_SOGG "
//                        + "where  T_NOTA_SOGG.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG_PNF.CODI_K_ID_NOTA = :idNota "
//                        + "and    T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER = :idUff "
//                        + "and    T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD = 5 "
//                        + "and    T_NOTA_SOGG.FLG_TIPO_SOGG = 'N' "
//                        + "and    T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG "
//                        + "and    T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PNF.CODI_K_ID_NOTA "
//                        + "order by T_NOTA_SOGG_PNF.DESC_DENOMINAZIONE  ";
//                SQLQuery quesqlQuerysession.createSQLQuery( queryString );
//                sqlQuery.addScalar( "progressivo", Hibernate.LONG );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.addScalar( "flagErrore", Hibernate.CHARACTER );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return lista;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Lista_Persone_Giuridiche" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list();
                return lista;

            }
        } );
    }


    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPFAssocAttoDecisCassazAnnot( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
            final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    "SELECT DISTINCT(T_SOGGETTO.CODI_K_ID_SOGG_INT) AS idSoggetto,   "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + " 	    T_SOGGETTO.CODI_FK_ID_TIPO_SOGG  AS tipologia,  "
//                        + " 	    CASE  "
//                        + " 	    WHEN T_PERSONA_FISICA.FLG_SESSO = 'F'  "
//                        + " 	    THEN T_PERSONA_FISICA.DESC_COGNOME  "
//                        + " 	      || ' '  "
//                        + " 	      || T_PERSONA_FISICA.DESC_NOME  "
//                        + " 	      || ' nata il '  "
//                        + " 	      || T_PERSONA_FISICA.DATA_NASCITA  "
//                        + " 	    ELSE T_PERSONA_FISICA.DESC_COGNOME  "
//                        + " 	      || ' '  "
//                        + " 	      || T_PERSONA_FISICA.DESC_NOME  "
//                        + " 	      || ' nato il '  "
//                        + " 	      || T_PERSONA_FISICA.DATA_NASCITA  "
//                        + " 	      || ' - Versione '  "
//                        + " 	      || T_REL_PROC_SOGINT.CODI_FK_ID_VER  "
//                        + " 	  END                            AS soggetto,  "
//                        + " T_REL_BENE_ATTO.CODI_K_ID_PROC as soggettoSitMP  "
//                        + " 	FROM   "
//                        + " 	T_SOGGETTO,  "
//                        + " 	T_PERSONA_FISICA,  "
//                        + " 	T_REL_BENE_ATTO,  "
//                        + " 	T_REL_PROC_SOGINT,   "
//                        + " 	t_atto,  "
//                        + " 	t_decreto,  "
//                        + " 	t_an_comuni_conserv,  "
//                        + " 	T_BENE,  "
//                        + " 	t_an_conservatorie,  "
//                        + " 	T_AN_COMUNI,  "
//                        + " 	T_TP_CAT_ATTO,  "
//                        + " 	T_TP_TIPO_INTERESSE,  "
//                        + " 	T_DEF_PATR  "
//                        + " 	WHERE   "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + " 	AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO  "
//                        + " 	AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER  "
//                        + " 	AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO  "
//                        + " 	AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER  "
//                        + " 	AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE  "
//                        + " 	AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER  "
//                        + " 	And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT  "
//                        + " 	And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER=T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER  "
//                        + " 	And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC  "
//                        + " 	And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT = T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + " 	And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER = T_SOGGETTO.CODI_K_ID_UFF_OWNER  "
//                        + " 	And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_FISICA.CODI_K_ID_PERSONA_FIS   "
//                        + " 	And T_SOGGETTO.CODI_K_ID_UFF_OWNER =T_PERSONA_FISICA.CODI_K_ID_UFF_OWNER   "
//                        + " 	And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_FISICA.CODI_K_ID_VER  "
//                        + " 	AND T_BENE.CODI_FK_ID_TIPO_BENE = 1   "
//                        + " 	AND T_BENE.CODI_FK_ID_COMUNE=T_AN_COMUNI_CONSERV.CODI_K_ID_COMUNE(+)  "
//                        + " AND T_AN_COMUNI_CONSERV.CODI_K_ID_CONSERV= :idConserv    "
//                        + " And (T_TP_CAT_ATTO.FLG_DECISORIO = 'D' or T_TP_CAT_ATTO.FLG_DECISORIO = 'C')  "
//                        + " And T_REL_BENE_ATTO.CODI_FK_ID_TIPO_ESITO_FIN in (1,2)  "
//                        + " And T_DEF_PATR.DATA_DEFINITIVITA IS NOT NULL  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_DEF_PATR.CODI_K_ID_ATTO  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_DEF_PATR.CODI_K_ID_UFF_OWNER  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_DEF_PATR.CODI_K_ID_BENE  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_DEF_PATR.CODI_K_ID_SOGG_TIT  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_DEF_PATR.CODI_K_ID_PROC  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT=T_DEF_PATR.CODI_K_ID_TIPO_INT  "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO   "
//                        + " AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO  "
//                        + " And  T_REL_BENE_ATTO.FLG_VALIDO = 'S'  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT=T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT  "
//                        + " AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL  "
//                        + " AND  "
//                        + " NOT EXISTS (SELECT 1   "
//                        + " FROM T_NOTA_SOGG    "
//                        + " WHERE   "
//                        + " T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT   "
//                        + "  And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "  AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + " ORDER BY soggetto ASC ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPFAssociabiliAnnot" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list();
                return lista;
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPFAssocAttoSequesTrascr( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
        final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria)
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    " SELECT DISTINCT (T_SOGGETTO.CODI_K_ID_SOGG_INT) as idSoggetto,  "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + " T_SOGGETTO.CODI_FK_ID_TIPO_SOGG as tipologia,  "
//                        + " CASE   "
//                        + "  WHEN T_PERSONA_FISICA.FLG_SESSO = 'F' THEN   "
//                        + "  T_PERSONA_FISICA.DESC_COGNOME     || ' '    || T_PERSONA_FISICA.DESC_NOME ||   "
//                        + "  ' nata il ' || T_PERSONA_FISICA.DATA_NASCITA  || ' - Versione ' || T_REL_PROC_SOGINT.CODI_FK_ID_VER "
//                        + " ELSE  "
//                        + "   T_PERSONA_FISICA.DESC_COGNOME     || ' '    || T_PERSONA_FISICA.DESC_NOME ||  "
//                        + "    ' nato il ' || T_PERSONA_FISICA.DATA_NASCITA  || ' - Versione ' || T_REL_PROC_SOGINT.CODI_FK_ID_VER "
//                        + "  END  as soggetto,   "
//                        + "	T_REL_BENE_ATTO.CODI_K_ID_PROC  as soggettoSitMp "
//                        + "	FROM  "
//                        + " T_REL_BENE_ATTO, "
//                        + " T_ATTO, "
//                        + " T_DECRETO, "
//                        + " T_AN_COMUNI_CONSERV, "
//                        + " T_BENE, "
//                        + " T_TP_CAT_ATTO, "
//                        + " T_TP_TIPO_INTERESSE, "
//                        + " T_SOGGETTO, "
//                        + " T_PERSONA_FISICA, "
//                        + " T_REL_PROC_SOGINT "
//                        + " WHERE  "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + " AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO  "
//                        + " AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER  "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC  "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT=T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER=T_SOGGETTO.CODI_K_ID_UFF_OWNER  "
//                        + " And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_FISICA.CODI_K_ID_PERSONA_FIS "
//                        + " And T_SOGGETTO.CODI_K_ID_UFF_OWNER =T_PERSONA_FISICA.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_FISICA.CODI_K_ID_VER "
//                        + " AND T_BENE.CODI_FK_ID_TIPO_BENE = 1  "
//                        + " AND T_BENE.CODI_FK_ID_COMUNE=T_AN_COMUNI_CONSERV.CODI_K_ID_COMUNE(+) "
//                        + " AND T_AN_COMUNI_CONSERV.CODI_K_ID_CONSERV= :idConservatoria  "
//                        + " AND T_TP_CAT_ATTO.FLG_DECISORIO = 'S' "
//                        + " AND T_REL_BENE_ATTO.CODI_FK_ID_TIPO_MIS in (5,6,16)  "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO "
//                        + " AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO "
//                        + " And  T_REL_BENE_ATTO.FLG_VALIDO = 'S' "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT= T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT "
//                        + " AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL "
//                        + " AND  "
//                        + " NOT EXISTS (SELECT 1  "
//                        + " FROM T_NOTA_SOGG   "
//                        + " WHERE  "
//                        + " T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + "  And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "  AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + " ORDER BY soggetto  ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//                sqlQuery.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
//                sqlQuery.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "repert2", repert2, Hibernate.INTEGER );
//                sqlQuery.setParameter( "repert", repert, Hibernate.INTEGER );
//                sqlQuery.setParameter( "idProc", idProc, Hibernate.LONG );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPFAssocAttoSequesTrascr" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list();
                return lista;


            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPFAssocAttoDecisTrascr( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
            final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//
//                    "SELECT DISTINCT(T_SOGGETTO.CODI_K_ID_SOGG_INT) AS idSoggetto,  "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + "      T_SOGGETTO.CODI_FK_ID_TIPO_SOGG  AS tipologia,  "
//                        + " CASE "
//                        + "  WHEN T_PERSONA_FISICA.FLG_SESSO = 'F' "
//                        + "   THEN T_PERSONA_FISICA.DESC_COGNOME "
//                        + "    || ' ' "
//                        + "    || T_PERSONA_FISICA.DESC_NOME "
//                        + "    || ' nata il ' "
//                        + "    || T_PERSONA_FISICA.DATA_NASCITA "
//                        + "   ELSE T_PERSONA_FISICA.DESC_COGNOME "
//                        + "    || ' ' "
//                        + "    || T_PERSONA_FISICA.DESC_NOME "
//                        + "    || ' nato il ' "
//                        + "    || T_PERSONA_FISICA.DATA_NASCITA "
//                        + "    || ' - Versione ' "
//                        + "    || T_REL_PROC_SOGINT.CODI_FK_ID_VER "
//                        + "  END    "
//                        + "	AS soggetto, "
//                        + " T_REL_BENE_ATTO.CODI_K_ID_PROC as soggettoSitMP  "
//                        + " FROM  "
//                        + " T_SOGGETTO, "
//                        + " T_PERSONA_FISICA, "
//                        + " T_REL_BENE_ATTO, "
//                        + " T_REL_PROC_SOGINT,  "
//                        + " t_atto, "
//                        + " t_decreto, "
//                        + " t_an_comuni_conserv, "
//                        + " T_BENE, "
//                        + " t_an_conservatorie, "
//                        + " T_AN_COMUNI, "
//                        + " T_TP_CAT_ATTO, "
//                        + " T_TP_TIPO_INTERESSE "
//                        + " WHERE  "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + " AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO "
//                        + " AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT=T_SOGGETTO.CODI_K_ID_SOGG_INT "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER=T_SOGGETTO.CODI_K_ID_UFF_OWNER "
//                        + " And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_FISICA.CODI_K_ID_PERSONA_FIS  "
//                        + " And T_SOGGETTO.CODI_K_ID_UFF_OWNER =T_PERSONA_FISICA.CODI_K_ID_UFF_OWNER  "
//                        + " And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_FISICA.CODI_K_ID_VER "
//                        + " AND T_BENE.CODI_FK_ID_TIPO_BENE = 1  "
//                        + " AND T_BENE.CODI_FK_ID_COMUNE= t_an_comuni_conserv.codi_k_id_comune(+) "
//                        + " AND t_an_comuni_conserv.codi_k_id_conserv= :idConservatoria "
//                        + " AND T_TP_CAT_ATTO.FLG_DECISORIO = 'D' "
//                        + " AND T_REL_BENE_ATTO.CODI_FK_ID_TIPO_ESITO_FIN = 1  "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO  "
//                        + " AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO "
//                        + " And  T_REL_BENE_ATTO.FLG_VALIDO = 'S' "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT= T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT "
//                        + " AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL "
//                        + " AND "
//                        + " NOT EXISTS (SELECT 1 FROM T_NOTA_SOGG   "
//                        + " WHERE  "
//                        + " T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + "  And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "  AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + " ORDER BY soggetto ASC ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPFAssocAttoDecisTrascr" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                List lista = query.list();
                return lista;
            }
        } );
    }


    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPGAssocAttoDecisCassazAnnot( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
            final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    " SELECT DISTINCT (T_SOGGETTO.CODI_K_ID_SOGG_INT) AS idSoggetto,  "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + " T_SOGGETTO.CODI_FK_ID_TIPO_SOGG  AS tipologia, "
//                        + "      T_PERSONA_GIUR.DESC_RAGIONE_SOC  || ' Versione ' || T_REL_PROC_SOGINT.CODI_FK_ID_VER  "
//                        + "        AS soggetto,  "
//                        + " T_REL_BENE_ATTO.CODI_K_ID_PROC as soggettoSitMP  "
//                        + " FROM  "
//                        + " T_SOGGETTO, "
//                        + " T_PERSONA_GIUR, "
//                        + " T_REL_BENE_ATTO, "
//                        + " T_REL_PROC_SOGINT,  "
//                        + " t_atto, "
//                        + " t_decreto, "
//                        + " t_an_comuni_conserv, "
//                        + " T_BENE, "
//                        + " t_an_conservatorie, "
//                        + " T_AN_COMUNI, "
//                        + " T_TP_CAT_ATTO, "
//                        + "  T_TP_TIPO_INTERESSE, "
//                        + "  T_DEF_PATR "
//                        + "  WHERE  "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + " AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO "
//                        + " AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO "
//                        + "  AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + "  AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE "
//                        + "  AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER "
//                        + "  And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT "
//                        + "  And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER=T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER "
//                        + "  And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC "
//                        + "  And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT = T_SOGGETTO.CODI_K_ID_SOGG_INT "
//                        + "  And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER = T_SOGGETTO.CODI_K_ID_UFF_OWNER "
//                        + " And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_GIUR.CODI_K_ID_PERS_GIUR "
//                        + "  And T_SOGGETTO.CODI_K_ID_UFF_OWNER=T_PERSONA_GIUR.CODI_K_ID_UFF_OWNER "
//                        + "  And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_GIUR.CODI_K_ID_VER "
//                        + "  AND T_BENE.CODI_FK_ID_TIPO_BENE = 1  "
//                        + "  AND T_BENE.CODI_FK_ID_COMUNE=T_AN_COMUNI_CONSERV.CODI_K_ID_COMUNE(+) "
//                        + "  AND T_AN_COMUNI_CONSERV.CODI_K_ID_CONSERV= :idConserv  "
//                        + "  And (T_TP_CAT_ATTO.FLG_DECISORIO = 'D' or T_TP_CAT_ATTO.FLG_DECISORIO = 'C') "
//                        + "And T_REL_BENE_ATTO.CODI_FK_ID_TIPO_ESITO_FIN in (1,2) "
//                        + " And T_DEF_PATR.DATA_DEFINITIVITA IS NOT NULL "
//                        + "AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_DEF_PATR.CODI_K_ID_ATTO "
//                        + "AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_DEF_PATR.CODI_K_ID_UFF_OWNER "
//                        + "AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_DEF_PATR.CODI_K_ID_BENE "
//                        + "And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_DEF_PATR.CODI_K_ID_SOGG_TIT "
//                        + "And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_DEF_PATR.CODI_K_ID_PROC "
//                        + "And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT=T_DEF_PATR.CODI_K_ID_TIPO_INT "
//                        + "AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO  "
//                        + "AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO "
//                        + "And  T_REL_BENE_ATTO.FLG_VALIDO = 'S' "
//                        + "And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT=T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT "
//                        + "AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL "
//                        + "AND "
//                        + "NOT EXISTS (SELECT 1  "
//                        + "  FROM T_NOTA_SOGG   "
//                        + "WHERE  "
//                        + " T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + "  And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "  AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + " ORDER BY soggetto ASC ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPGAssociabiliAnnot" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                List lista = query.list();
                return lista;
            }
        } );
    }
   
    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPGAssocAttoSequesTrascr( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
            final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//
//                    "   SELECT DISTINCT(T_SOGGETTO.CODI_K_ID_SOGG_INT) as idSoggetto,  "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + " T_SOGGETTO.CODI_FK_ID_TIPO_SOGG as tipologia,  "
//                        + "   T_PERSONA_GIUR.DESC_RAGIONE_SOC     || '  - Versione '  || T_REL_PROC_SOGINT.CODI_FK_ID_VER as soggetto,  "
//                        + "   T_REL_BENE_ATTO.CODI_K_ID_PROC as soggettoSitMP  "
//                        + "   FROM   "
//                        + "   T_REL_BENE_ATTO,  "
//                        + "  T_ATTO,  "
//                        + "  T_DECRETO, "
//                        + "   T_AN_COMUNI_CONSERV, "
//                        + "   T_BENE, "
//                        + "   T_TP_CAT_ATTO, "
//                        + "   T_TP_TIPO_INTERESSE, "
//                        + "   T_SOGGETTO, "
//                        + "   T_PERSONA_GIUR,  "
//                        + "   T_REL_PROC_SOGINT "
//                        + "   WHERE  "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + "   AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO "
//                        + "   AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + "   AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO "
//                        + "   AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + "   AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE "
//                        + "   AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER "
//                        + "   And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT "
//                        + "   And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER "
//                        + "  And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC "
//                        + "   And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT=T_SOGGETTO.CODI_K_ID_SOGG_INT "
//                        + "   And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER=T_SOGGETTO.CODI_K_ID_UFF_OWNER "
//                        + "   And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_GIUR.CODI_K_ID_PERS_GIUR  "
//                        + "   And T_SOGGETTO.CODI_K_ID_UFF_OWNER =T_PERSONA_GIUR.CODI_K_ID_UFF_OWNER "
//                        + "   And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_GIUR.CODI_K_ID_VER "
//                        + "  AND T_BENE.CODI_FK_ID_TIPO_BENE = 1 "
//                        + "  AND T_BENE.CODI_FK_ID_COMUNE=T_AN_COMUNI_CONSERV.CODI_K_ID_COMUNE(+) "
//                        + "   AND T_AN_COMUNI_CONSERV.CODI_K_ID_CONSERV= :idConservatoria  "
//                        + "   AND T_TP_CAT_ATTO.FLG_DECISORIO = 'S' "
//                        + "   AND T_REL_BENE_ATTO.CODI_FK_ID_TIPO_MIS in (5,6,16)  "
//                        + "   AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO  "
//                        + "   AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO "
//                        + "   And  T_REL_BENE_ATTO.FLG_VALIDO = 'S' "
//                        + "   And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT= T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT "
//                        + "   AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL"
//                        + "   AND "
//                        + "   NOT EXISTS (SELECT 1 "
//                        + "   FROM T_NOTA_SOGG  "
//                        + "   WHERE "
//                        + "   T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + "        And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "        AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + "   ORDER BY soggetto ASC ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPGAssocAttoSequesTrascr" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                List lista = query.list();
                return lista;
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaPGAssocAttoDecisTrascr( final Long idTipoAtto, final Long idCatAtto, final Integer repert2,
            final Integer repert, final Long idProc, final Long idNota, final Integer idUff, final Integer idConservatoria)
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//
//                    " SELECT DISTINCT "
//                        + "(T_SOGGETTO.CODI_K_ID_SOGG_INT) AS idSoggetto,  "
//                        + " T_REL_PROC_SOGINT.CODI_FK_ID_VER as versione,  "
//                        + "T_SOGGETTO.CODI_FK_ID_TIPO_SOGG  AS tipologia, "
//                        + "     T_PERSONA_GIUR.DESC_RAGIONE_SOC "
//                        + "     || ' Versione ' "
//                        + "      || T_REL_PROC_SOGINT.CODI_FK_ID_VER "
//                        + "        AS soggetto, "
//                        + " T_REL_BENE_ATTO.CODI_K_ID_PROC as soggettoSitMP "
//                        + " FROM  "
//                        + " T_SOGGETTO, "
//                        + " T_PERSONA_GIUR, "
//                        + " T_REL_BENE_ATTO, "
//                        + " T_REL_PROC_SOGINT,  "
//                        + " t_atto, "
//                        + " t_decreto, "
//                        + " t_an_comuni_conserv, "
//                        + " T_BENE, "
//                        + " t_an_conservatorie, "
//                        + " T_AN_COMUNI, "
//                        + " T_TP_CAT_ATTO, "
//                        + " T_TP_TIPO_INTERESSE "
//                        + " WHERE  "
//                        + " T_ATTO.CODI_FK_ID_TIPO_ATTO =  :idTipoAtto "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  :idCatAtto "
//                        + " AND T_ATTO.CODI_K_ID_UFF_OWNER= :idUff  "
//                        + " AND T_DECRETO.NUME_ANNO_ATTO = :repert2  "
//                        + " AND T_DECRETO.NUME_ATTO = :repert  "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_PROC= :idProc  "
//                        + " AND T_DECRETO.CODI_K_ID_DECRETO= T_ATTO.CODI_K_ID_ATTO "
//                        + " AND T_DECRETO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_ATTO= T_ATTO.CODI_K_ID_ATTO "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER = T_ATTO.CODI_K_ID_UFF_OWNER "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_BENE=T_BENE.CODI_K_ID_BENE "
//                        + " AND T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_BENE.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_SOGG_TIT = T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_UFF_OWNER= T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_PROC= T_REL_PROC_SOGINT.CODI_K_ID_PROC "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT=T_SOGGETTO.CODI_K_ID_SOGG_INT "
//                        + " And T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER=T_SOGGETTO.CODI_K_ID_UFF_OWNER "
//                        + " And T_SOGGETTO.CODI_K_ID_SOGG_INT =T_PERSONA_GIUR.CODI_K_ID_PERS_GIUR "
//                        + " And T_SOGGETTO.CODI_K_ID_UFF_OWNER=T_PERSONA_GIUR.CODI_K_ID_UFF_OWNER "
//                        + " And T_REL_PROC_SOGINT.CODI_FK_ID_VER=T_PERSONA_GIUR.CODI_K_ID_VER "
//                        + " AND T_BENE.CODI_FK_ID_TIPO_BENE = 1   "
//                        + " AND T_BENE.CODI_FK_ID_COMUNE= t_an_comuni_conserv.codi_k_id_comune(+) "
//                        + " AND t_an_comuni_conserv.codi_k_id_conserv= :idConservatoria "
//                        + " AND T_TP_CAT_ATTO.FLG_DECISORIO = 'D' "
//                        + " AND T_REL_BENE_ATTO.CODI_FK_ID_TIPO_ESITO_FIN = 1  "
//                        + " AND T_ATTO.CODI_FK_ID_CAT_ATTO =  T_TP_CAT_ATTO.CODI_K_ID_CAT_ATTO  "
//                        + " AND T_ATTO.CODI_FK_ID_TIPO_ATTO=T_TP_CAT_ATTO.CODI_FK_ID_TIPO_ATTO "
//                        + " And  T_REL_BENE_ATTO.FLG_VALIDO = 'S' "
//                        + " And T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT= T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT "
//                        + " AND T_TP_TIPO_INTERESSE.NUME_CONS_1 IS NOT NULL "
//                        + "  AND "
//                        + "  NOT EXISTS (SELECT 1 FROM T_NOTA_SOGG   "
//                        + "  WHERE  "
//                        + " T_NOTA_SOGG.CODI_FK_ID_SOGG_INT =T_SOGGETTO.CODI_K_ID_SOGG_INT  "
//                        + "  And T_NOTA_SOGG.CODI_K_ID_NOTA= :idNota "
//                        + "  AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = :idUff) "
//                        + " ORDER BY soggetto ASC ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "idSoggetto", Hibernate.LONG );
//                sqlQuery.addScalar( "versione", Hibernate.INTEGER );
//                sqlQuery.addScalar( "tipologia", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "soggetto", Hibernate.STRING );
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaPGAssocAttoDecisTrascr" );
                query.setParameter( "idTipoAtto", idTipoAtto, Hibernate.LONG );
                query.setParameter( "idCatAtto", idCatAtto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "repert2", repert2, Hibernate.INTEGER );
                query.setParameter( "repert", repert, Hibernate.INTEGER );
                query.setParameter( "idProc", idProc, Hibernate.LONG );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idConservatoria", idConservatoria, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );

                List lista = query.list();
                return lista;
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public SoggettoInteressatoDto dettaglioSoggettoFisico( final Long idNota, final Integer idUffOwn, final Long progressivo )
    {
        return (SoggettoInteressatoDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = // dettaglioPersonaFisica
//                    "    select T_NOTA_SOGG.CODI_FK_ID_SOGG_INT as soggettoSitMp, "
//                        + " T_NOTA_SOGG_PF.DESC_COGNOME as cognome, "
//                        + " T_NOTA_SOGG_PF.DESC_NOME as nome, "
//                        + " T_NOTA_SOGG_PF.FLG_SESSO as flgSesso, "
//                        + " T_NOTA_SOGG_PF.DATA_NASC as dataNascita, "
//                        + " T_NOTA_SOGG_PF.DESC_CODI_COMUNE_NASC as codiceComuneNasc, "
//                        + " T_NOTA_SOGG_PF.DESC_COMUNE_NASC as comuneNascita, "
//                        + " T_NOTA_SOGG_PF.DESC_PROV_NASC as provNascita, "
//                        + " T_NOTA_SOGG_PF.DESC_CODI_FISCALE as codiceFiscalePF, "
//                        + " T_NOTA_SOGG_PF.CODI_FK_ID_TIPO_REGIME as regime, "
//                        + " nvl ( T_TP_TIPO_REGIME.DESC_TIPO_REGIME, null ) as descRegime, "
//                        + " T_NOTA_SOGG_PF.CODI_FK_PROGR_SOGG_C as codProgrSoggC "
//                        + " from  T_NOTA_SOGG, T_NOTA_SOGG_PF,T_TP_TIPO_REGIME "
//                        + " where 1= 1 "
//                        + " and  T_NOTA_SOGG.CODI_K_PROGR_SOGG         = T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG "
//                        + " and  T_NOTA_SOGG_PF.CODI_FK_ID_TIPO_REGIME = T_TP_TIPO_REGIME.CODI_K_ID_TIPO_REGIME (+) "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD     = 5 "
//                        + " and  T_NOTA_SOGG.CODI_K_TIPO_RECORD        = 5 "
//                        + " and  T_NOTA_SOGG.FLG_TIPO_SOGG             = 'F' "
//                        + " and  T_NOTA_SOGG.CODI_K_ID_NOTA            = :idNota "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_NOTA         = :idNota "
//                        + " and  T_NOTA_SOGG.CODI_K_ID_UFF_OWNER       = :idUff "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER    = :idUff "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG      = :progressivo ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//
//                sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                sqlQuery.addScalar( "cognome", Hibernate.STRING );
//                sqlQuery.addScalar( "nome", Hibernate.STRING );
//                sqlQuery.addScalar( "flgSesso", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "dataNascita", Hibernate.DATE );
//                sqlQuery.addScalar( "codiceComuneNasc", Hibernate.STRING );
//                sqlQuery.addScalar( "comuneNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "provNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "codiceFiscalePF", Hibernate.STRING );
//                sqlQuery.addScalar( "regime", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "descRegime", Hibernate.STRING );
//                sqlQuery.addScalar( "codProgrSoggC", Hibernate.INTEGER );
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Dettaglio_Pf" );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setParameter( "progressivo", progressivo, Hibernate.LONG );
                List l = query.list();
                if( (l != null) && (l.size() > 0) ) 
                {
                	return l.get( 0 );
                }
                	return null;
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public SoggettoInteressatoDto dettaglioSoggettoGiurid( final Long idNota, final Integer idUffOwn, final Long progressivo )
    {
        return (SoggettoInteressatoDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                        " select  T_NOTA_SOGG.CODI_FK_ID_SOGG_INT as soggettoSitMp, "
//                            + " T_NOTA_SOGG_PNF.DESC_DENOMINAZIONE as denominazione, "
//                            + " T_NOTA_SOGG_PNF.DESC_CODI_COMUNE_SEDE as codiceComuneSede, " // -- serve per trovare statoSede 
//                            + " T_NOTA_SOGG_PNF.DESC_COMUNE_SEDE as comuneSede, "
//                            + " T_NOTA_SOGG_PNF.DESC_PROV_SEDE as provSede, "
//                            + " T_NOTA_SOGG_PNF.DESC_CODI_FISCALE as partitaIva "
//                            + " from  T_NOTA_SOGG, T_NOTA_SOGG_PNF where 1= 1 "
//                            + " and  T_NOTA_SOGG.CODI_K_PROGR_SOGG        = T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG "
//                            + " and  T_NOTA_SOGG.FLG_TIPO_SOGG            = 'N' "
//                            + " and  T_NOTA_SOGG.CODI_K_TIPO_RECORD       = 5 "
//                            + " and  T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD   = 5 "
//                            + " and  T_NOTA_SOGG.CODI_K_ID_NOTA           = :idNota "
//                            + " and  T_NOTA_SOGG_PNF.CODI_K_ID_NOTA       = :idNota "
//                            + " and  T_NOTA_SOGG.CODI_K_ID_UFF_OWNER      = :idUff "
//                            + " and  T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER  = :idUff "
//                            + " and  T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG    = :progressivo "
//                    ;
//                    SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                    sqlQuery.addScalar( "soggettoSitMp", Hibernate.LONG );
//                    sqlQuery.addScalar( "denominazione", Hibernate.STRING );
//                    sqlQuery.addScalar( "codiceComuneSede", Hibernate.STRING );
//                    sqlQuery.addScalar( "comuneSede", Hibernate.STRING );
//                    sqlQuery.addScalar( "provSede", Hibernate.STRING );
//                    sqlQuery.addScalar( "partitaIva", Hibernate.STRING );
//                    sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//    			      sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//    			      sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//    			      sqlQuery.setParameter( "progressivo", progressivo, Hibernate.LONG );
//    			      List lista = sqlQuery.list();
//    			      return (SoggettoInteressatoDto)lista.get( 0 ); // dettaglioPersonaGiurid
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Dettaglio_Pnf" );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setParameter( "progressivo", progressivo, Hibernate.LONG );
                List l = query.list();
                if( (l != null) && (l.size() > 0) ) 
                {
                	return l.get( 0 );
                }
                	return null;
            }
        }
      );
    }

    //FG fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Character sessoSoggettoFisico( final Long idNota, final Integer idUff, final Integer progrSoggCon )
    {
        return (Character)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                    " select T_NOTA_SOGG_PF.FLG_SESSO as sesso "
//                        + " from T_NOTA_SOGG_PF "
//                        + " where 1= 1 "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD   = 5 "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_NOTA       = :idNota "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER  = :idUff "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG    = :progrSoggCon ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "sesso", Hibernate.CHARACTER );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrSoggCon", progrSoggCon, Hibernate.INTEGER );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_sessoSoggettoFisico" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "progrSoggCon", progrSoggCon, Hibernate.INTEGER );
                List lista = query.list();
                if( !lista.isEmpty() )
                    return lista.get( 0 );
                else
                return null;
            }
        } );
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer modificaSoggettoFisico( final Long idNota, final Integer idUff, final String utente, final String cognome,
        final String nome, final Character sesso, final Date dataNascita, final String statoNascita, final String comuneNascita,
        final String provNascita, final String codFiscale, final Character regime, final Integer progrSoggCon, final Long progressivo )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "update T_NOTA_SOGG_PF "
//                                     + " set "
//                                     + " T_NOTA_SOGG_PF.DESC_COGNOME = :cognome, "
//                                     + " T_NOTA_SOGG_PF.DESC_NOME = :nome, "
//                                     + " T_NOTA_SOGG_PF.FLG_SESSO = :sesso, "
//                                     + " T_NOTA_SOGG_PF.DATA_NASC = :dataNascita, "
//                                     + " T_NOTA_SOGG_PF.DESC_CODI_COMUNE_NASC = :statoNascita, "
//                                     + " T_NOTA_SOGG_PF.DESC_PROV_NASC = :provNascita, "
//                                     + " T_NOTA_SOGG_PF.DESC_COMUNE_NASC = :comuneNascita, "
//                                     + " T_NOTA_SOGG_PF.DESC_CODI_FISCALE = :codFiscale, "
//                                     + " T_NOTA_SOGG_PF.CODI_FK_ID_TIPO_REGIME = :regime, "
//                                     + " T_NOTA_SOGG_PF.CODI_FK_PROGR_SOGG_C = :progrSoggCon, "
//                                     + " T_NOTA_SOGG_PF.DESC_UTENTE = :utente, "
//                                     + " T_NOTA_SOGG_PF.DATA_ULT_AGG = sysdate "
//                                     + " where 1= 1 "
//                                     + " and  T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD   = 5 "
//                                     + " and  T_NOTA_SOGG_PF.CODI_K_ID_NOTA       = :idNota "
//                                     + " and  T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER  = :idUff "
//                                     + " and  T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG    = :progressivo ";
//              SQLQuery sqlQuery = session.createSQLQuery( queryString );
//              int righe = sqlQuery.executeUpdate(); // modificaSoggettoFisico  ---
//              return righe;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_modificaSoggettoFisico" );
                query.setParameter( "cognome", cognome, Hibernate.STRING );
                query.setParameter( "nome", nome, Hibernate.STRING );
                query.setParameter( "sesso", sesso, Hibernate.CHARACTER );
                query.setParameter( "dataNascita", dataNascita, Hibernate.DATE );
                query.setParameter( "statoNascita", statoNascita, Hibernate.STRING );
                query.setParameter( "comuneNascita", comuneNascita, Hibernate.STRING );
                query.setParameter( "provNascita", provNascita, Hibernate.STRING );
                query.setParameter( "codFiscale", codFiscale, Hibernate.STRING );
                query.setParameter( "regime", regime, Hibernate.CHARACTER );
                query.setParameter( "progrSoggCon", progrSoggCon, Hibernate.INTEGER );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "progressivo", progressivo, Hibernate.LONG );
                List lista = query.list();
                return lista.size();
            }
        } );
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer resetErroreSoggetto( final Long idNota, final Integer idUff, final String utente, final Long progressivo )
    {
        {
            return (Integer)getHibernateTemplate().execute( new HibernateCallback()
            {
                public Object doInHibernate( final Session session ) throws HibernateException, SQLException
                {
//                    String queryString = "update T_NOTA_SOGG "
//                                         + " set "
//                                         + " T_NOTA_SOGG.FLG_ERR = 0, "
//                                         + " T_NOTA_SOGG.DESC_UTENTE = :utente, "
//                                         + " T_NOTA_SOGG.DATA_ULT_AGG = sysdate "
//                                         + " where 1= 1 "
//                                         + " and  T_NOTA_SOGG.CODI_K_TIPO_RECORD   = 5 "
//                                         + " and  T_NOTA_SOGG.CODI_K_ID_NOTA       = :idNota "
//                                         + " and  T_NOTA_SOGG.CODI_K_ID_UFF_OWNER  = :idUff "
//                                         + " and  T_NOTA_SOGG.CODI_K_PROGR_SOGG    = :progressivo ";
//                    SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                    sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                    sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                    sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                    sqlQuery.setParameter( "progressivo", progressivo, Hibernate.LONG );
//                    int righe = sqlQuery.executeUpdate();
//                    return righe;
//                  int righe = sqlQuery.executeUpdate(); // modificaSoggettoFisico  ---
//                  return righe;
                    
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_resetErroreSoggetto" );
                    query.setParameter( "idNota", idNota, Hibernate.LONG );
                    query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                    query.setParameter( "utente", utente, Hibernate.STRING );
                    query.setParameter( "progressivo", progressivo, Hibernate.LONG );
                    List lista = query.list();
                    return lista.size();
                }
            } );
        }
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer modificaSoggettoGiurid( final Long idNota, final Integer idUff, final String utente, final String denom, final String comune,
        final String provincia, final String pIva, final String codComune, final Long progressivo )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "update T_NOTA_SOGG_PNF "
//                                     + " set "
//                                     + " DESC_DENOMINAZIONE = :denom, "
//                                     + " DESC_COMUNE_SEDE = :comune, "
//                                     + " DESC_PROV_SEDE = :provincia, "
//                                     + " DESC_CODI_FISCALE = :pIva, "
//                                     + " DESC_CODI_COMUNE_SEDE = :codComune, "
//                                     + " DESC_UTENTE = :utente, "
//                                     + " DATA_ULT_AGG = sysdate "
//                                     + " where 1= 1 "
//                                     + " and  T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD   = 5 "
//                                     + " and  T_NOTA_SOGG_PNF.CODI_K_ID_NOTA       = :idNota "
//                                     + " and  T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER  = :idUff "
//                                     + " and  T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG    = :progressivo ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                int righe = sqlQuery.executeUpdate(); // modificaSoggettoGiurid 
//                return righe;
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_modificaSoggettoGiurid" );
                query.setParameter( "denom", denom, Hibernate.STRING );
                query.setParameter( "comune", comune, Hibernate.STRING );
                query.setParameter( "provincia", provincia, Hibernate.STRING );
                query.setParameter( "pIva", pIva, Hibernate.STRING );
                query.setParameter( "codComune", codComune, Hibernate.STRING );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "progressivo", progressivo, Hibernate.LONG );
                List lista = query.list();
                return lista.size();
                
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociatoDto> listaBeniAssociati( final Long idNota, final int idUffOwn )
    {
        return (List<BeneAssociatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Short progrNotaBene = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Associati" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "progrNotaBene", progrNotaBene, Hibernate.SHORT );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociatoDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniAssociabiliA(
        final Long idNota,
        final int idUffOwn,
        final Integer tipoAtto,
        final Integer catAtto,
        final Integer repAnno,
        final Integer repNumero,
        final Long idProced,
        final Short idConserv )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Associabili_A" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.INTEGER );
                q.setParameter( "repNumero", repNumero, Hibernate.INTEGER );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setParameter( "idConserv", idConserv, Hibernate.SHORT );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniAssociabiliTD(
        final Long idNota,
        final int idUffOwn,
        final Integer tipoAtto,
        final Integer catAtto,
        final Integer repAnno,
        final Integer repNumero,
        final Long idProced,
        final Short idConserv )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Associabili_TD" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.INTEGER );
                q.setParameter( "repNumero", repNumero, Hibernate.INTEGER );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setParameter( "idConserv", idConserv, Hibernate.SHORT );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniAssociabiliTS(
        final Long idNota,
        final int idUffOwn,
        final Integer tipoAtto,
        final Integer catAtto,
        final Integer repAnno,
        final Integer repNumero,
        final Long idProced,
        final Short idConserv )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Associabili_TS" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.INTEGER );
                q.setParameter( "repNumero", repNumero, Hibernate.INTEGER );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setParameter( "idConserv", idConserv, Hibernate.SHORT );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public List<String> cercaOsservazioniNota( final Integer idUffOwn, final Long idNota, final Character tipoInfo )
    {
        return (List<String>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_cerca_Osservazioni" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "tipoInfo", tipoInfo, Hibernate.CHARACTER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List lista = q.list();
                List<String> listaOsservazioni = new ArrayList<String>();
                Iterator it = lista.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    String p = ((String)row.get( "DESC_NOTA" ));
                    listaOsservazioni.add( p );
                }
                return listaOsservazioni;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long inserisciQuadroD(
        final Integer idUffOwn, final String descUtente, final Long idNota,
        final Character tipoInf, final String blocco, final int progr )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Long doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisciQuadroD" );
                q.setParameter( "idUffOwn", idUffOwn );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "blocco", blocco, Hibernate.STRING );
                q.setParameter( "descUtente", descUtente, Hibernate.STRING );
                q.setParameter( "tipoInf", tipoInf, Hibernate.CHARACTER );
                q.setParameter( "progr", progr, Hibernate.INTEGER );
                q.setParameter( "dataultagg", new Date(), Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (Long)(first.get( "IDN" ));
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String aggiornaStatoNota( final Integer idUffOwn, final String descUtente, final Long idNota, final Character flgstato)
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_aggiornaStatoNota" );
                q.setParameter( "idUffOwn", idUffOwn );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "descUtente", descUtente, Hibernate.STRING );
                q.setParameter( "dataultagg", new Date(), Hibernate.TIMESTAMP );
                q.setParameter( "flgstato", flgstato, Hibernate.CHARACTER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String cancellaQuadroD( final Integer idUffOwn, final Long idNota, final Character tipoInf )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_cancellaQuadroD" );
                q.setParameter( "idUffOwn", idUffOwn );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "tipoInfo", tipoInf, Hibernate.CHARACTER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public Long getNotaBene( final Long idNotaOld, final Integer idUffOwn )
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long progr = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_ricerca_progr_nota_bene" );
                q.setParameter( "idNota", idNotaOld, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                if( (l != null) && (l.size() > 0) )
                {
                    Map<String, Object> ret = (Map<String, Object>)l.get( 0 );
                    progr = (Long)ret.get( "MAXPROGR" );
                }
                return progr;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
	public Long getMaxProgrNotaBene(final Long idNota, final int idUffOwn, final long idBene)
    {
        return (Long)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Long progr = null;
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_get_max_progr_nota_bene" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idBene", idBene, Hibernate.LONG );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                if( (l != null) && (l.size() > 0) )
                {
                    Map<String, Object> ret = (Map<String, Object>)l.get( 0 );
                    progr = (Long)ret.get( "MAXPROGR" );
                }
                return progr;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String inserisciNotaBene( final Long idNotaOld, final Long idNotaNew, final int idUffOwn, final String descUtente )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisci_nota_bene" );
                q.setParameter( "idNotaOld", idNotaOld, Hibernate.LONG );
                q.setParameter( "idNotaNew", idNotaNew, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "descutente", descUtente, Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public BeneAssociatoDto dettaglioNotaBene( final Short progrNotaBene, final Long idNota, final Integer idUffOwn )
    {
        return (BeneAssociatoDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Associati" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "progrNotaBene", progrNotaBene, Hibernate.SHORT );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociatoDto.class ) );
                List l = q.list();
                if( (l != null) && (l.size() > 0) ) {
                return l.get( 0 );
                }
                return null;
            }
        } );
    }

    @SuppressWarnings( "unchecked" )
    public String descTipoCatasto( Character idTipoCat )
    {

        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoCatasto.class );
        Criterion rest1 = Restrictions.eq( "codiKIdTipoCat", idTipoCat );
        criteria.add( rest1 );
        List<TTpTipoCatasto> u = getHibernateTemplate().findByCriteria( criteria );
        TTpTipoCatasto risultato = DataAccessUtils.requiredSingleResult( u );
        String descTipoCatasto = risultato.getDescCatasto();
        return descTipoCatasto;
    }

    @SuppressWarnings( "unchecked" )
    public String descTipoFondo( Character idTipoFondo )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoFondo.class );
        Criterion rest1 = Restrictions.eq( "codiKIdFondo", idTipoFondo );
        criteria.add( rest1 );
        List<TTpTipoFondo> u = getHibernateTemplate().findByCriteria( criteria );
        TTpTipoFondo risultato = DataAccessUtils.requiredSingleResult( u );
        String descTipoFondo = risultato.getDescFondo();
        return descTipoFondo;
    }

    @SuppressWarnings( "unchecked" )
    public String descTipoDenuncia( Character idTipoDenuncia )
    {

        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoDenuncia.class );
        Criterion rest1 = Restrictions.eq( "codiKIdTipoDen", idTipoDenuncia );
        criteria.add( rest1 );
        List<TTpTipoDenuncia> u = getHibernateTemplate().findByCriteria( criteria );
        TTpTipoDenuncia risultato = DataAccessUtils.requiredSingleResult( u );
        String descTipoDenuncia = risultato.getDescTipoDenuncia();
        return descTipoDenuncia;
    }

    @SuppressWarnings( "unchecked" )
    public String descCatCatasto( Integer idCatCatasto )
    {

        DetachedCriteria criteria = DetachedCriteria.forClass( TTpCatCatasto.class );
        Criterion rest1 = Restrictions.eq( "codiKIdCategCat", idCatCatasto );
        criteria.add( rest1 );
        List<TTpCatCatasto> u = getHibernateTemplate().findByCriteria( criteria );
        TTpCatCatasto risultato = DataAccessUtils.requiredSingleResult( u );
        String descCatCatasto = risultato.getDescCategoriaCat();
        return descCatCatasto;
    }
    
    @SuppressWarnings( "unchecked" )
    public String descBreveCatCatasto( Integer idCatCatasto )
    {

        DetachedCriteria criteria = DetachedCriteria.forClass( TTpCatCatasto.class );
        Criterion rest1 = Restrictions.eq( "codiKIdCategCat", idCatCatasto );
        criteria.add( rest1 );
        List<TTpCatCatasto> u = getHibernateTemplate().findByCriteria( criteria );
        TTpCatCatasto risultato = DataAccessUtils.requiredSingleResult( u );
        String descBreveCatCatasto = risultato.getDescBreveCategoriaCat();
        return descBreveCatCatasto;
    }

    @SuppressWarnings( "unchecked" )
    public Character getFlagDecisorio( Integer tipoAtto, Integer catAtto )
    {

        DetachedCriteria criteria = DetachedCriteria.forClass( TTpCatAtto.class );
        Criterion rest1 = Restrictions.eq( "codiKIdCatAtto", catAtto );
        Criterion rest2 = Restrictions.eq( "TTpTipoAtto.codiKIdTipoAtto", tipoAtto );
        criteria.add( Restrictions.and( rest1, rest2 ) );
        List<TTpCatAtto> u = getHibernateTemplate().findByCriteria( criteria );
        TTpCatAtto risultato = DataAccessUtils.requiredSingleResult( u );
        Character flagDecisorio = risultato.getFlgDecisorio();
        return flagDecisorio;
    }

    @SuppressWarnings("unchecked")
	public String cancellaNotaBene( Long idNota, Integer idUffOwn, Long progrNotaBene )
    {
        String esito = "OK";
        DetachedCriteria criteria = DetachedCriteria.forClass( TNotaBene.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
        Criterion rest3 = Restrictions.eq( "id.codiKProgrBene", progrNotaBene.shortValue() );
        criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
        List< TNotaBene > lista = getHibernateTemplate().findByCriteria(criteria);
        if (lista != null && lista.size() > 0) {
            TNotaBene itemSelezionato = (TNotaBene)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
            if( itemSelezionato != null )
            {
                getHibernateTemplate().delete( itemSelezionato );
                getHibernateTemplate().flush();
            }
        }
        return esito;
    }

    @SuppressWarnings("unchecked")
    public String cancellaNotaSoggBene( Long idNota, Integer idUffOwn, Long progrNotaBene )
    {
        String esito = "OK";
        DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggBene.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
        Criterion rest3 = Restrictions.eq( "id.codiKProgrBene", progrNotaBene.shortValue() );
        criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
		List< TNotaSoggBene > lista = getHibernateTemplate().findByCriteria(criteria);
        if (lista != null && lista.size() > 0) 
        {
          	getHibernateTemplate().deleteAll(lista);
            getHibernateTemplate().flush();
        	esito = "OK";
        } 
        return esito;
    }

    public String updateStatoNota( Long idNota, Integer idUffOwn, String descUtente )
    {
        String esito = "OK";
        Date dataOggi = new Date();
        DetachedCriteria criteria = DetachedCriteria.forClass( TNota.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
        criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ) );
        TNota itemSelezionato = (TNota)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
        itemSelezionato.setFlgStato( '0' );
        itemSelezionato.setDescUtente( descUtente );
        itemSelezionato.setDataUltAgg( dataOggi );
        getHibernateTemplate().saveOrUpdate( itemSelezionato );
        getHibernateTemplate().flush();
        return esito;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public BeneDaAssociareDto beneDaAssociare( final Long idBene, final Integer idUffOwn )
    {

        return (BeneDaAssociareDto)getHibernateTemplate().execute( new HibernateCallback()
        {

            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {

                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Bene_Da_Associare" );
                q.setParameter( "idBene", idBene, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setResultTransformer( Transformers.aliasToBean( BeneDaAssociareDto.class ) );
                List l = q.list();
                if( (l != null) && (l.size() > 0) ) {
                return l.get( 0 );
                }
                return null;
            }
        } );
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String associaNotaBene( final TNotaBene notaBene )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Short idProgUneg = null;
                if( notaBene.getTNotaUn() != null )
                {
                    if( notaBene.getTNotaUn().getId() != null )
                    {
                        if( notaBene.getTNotaUn().getId().getCodiKProgrUneg() != 0 )
                        {
                            idProgUneg = notaBene.getTNotaUn().getId().getCodiKProgrUneg();
                        }
                    }
                }

                Character tipoFondo = null;
                if( notaBene.getTTpTipoFondo() != null )
                {
                    tipoFondo = notaBene.getTTpTipoFondo().getCodiKIdFondo();
                }

                Character tipoCat = null;
                if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCat() != null )
                {
                    tipoCat = notaBene.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat();
                }

                Character tipoCatP = null;
                if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCatP() != null )
                {
                    tipoCatP = notaBene.getTTpTipoCatastoByCodiFkIdTipoCatP().getCodiKIdTipoCat();
                }

                Integer catCatasto = null;
                if( notaBene.getTTpCatCatasto() != null )
                {
                    catCatasto = notaBene.getTTpCatCatasto().getCodiKIdCategCat();
                }

                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_associa_nota_bene" );
                q.setParameter( "codikidnota", notaBene.getId().getCodiKIdNota(), Hibernate.LONG );
                q.setParameter( "codikiduffowner", notaBene.getId().getCodiKIdUffOwner(), Hibernate.INTEGER );
                q.setParameter( "codikprogrbene", notaBene.getId().getCodiKProgrBene(), Hibernate.SHORT );
                q.setParameter( "numeprogrunegbene", notaBene.getNumeProgrUnegBene(), Hibernate.SHORT );
                q.setParameter( "descsedeimmobile", notaBene.getDescSedeImmobile(), Hibernate.STRING );
                q.setParameter( "desccatasto", notaBene.getDescCatasto(), Hibernate.STRING );
                q.setParameter( "desccatastop", notaBene.getDescCatastoP(), Hibernate.STRING );
                q.setParameter( "codifkidtipocat", tipoCat, Hibernate.CHARACTER );
                q.setParameter( "codifkidtipocatp", tipoCatP, Hibernate.CHARACTER );
                q.setParameter( "descsezione", notaBene.getDescSezione(), Hibernate.STRING );
                q.setParameter( "descsezionep", notaBene.getDescSezioneP(), Hibernate.STRING );
                q.setParameter( "descfoglio", notaBene.getDescFoglio(), Hibernate.STRING );
                q.setParameter( "descfogliop", notaBene.getDescFoglioP(), Hibernate.STRING );
                q.setParameter( "descparticella1", notaBene.getDescParticella1(), Hibernate.STRING );
                q.setParameter( "descparticella1p", notaBene.getDescParticella1P(), Hibernate.STRING );
                q.setParameter( "descparticella2", notaBene.getDescParticella2(), Hibernate.STRING );
                q.setParameter( "descparticella2p", notaBene.getDescParticella2P(), Hibernate.STRING );
                q.setParameter( "descsubalterno1", notaBene.getDescSubalterno1(), Hibernate.STRING );
                q.setParameter( "descsubalterno1p", notaBene.getDescSubalterno1P(), Hibernate.STRING );
                q.setParameter( "descsubalterno2", notaBene.getDescSubalterno2(), Hibernate.STRING );
                q.setParameter( "descsubalterno2p", notaBene.getDescSubalterno2P(), Hibernate.STRING );
                q.setParameter( "desctipodenuncia", notaBene.getDescTipoDenuncia(), Hibernate.CHARACTER );
                q.setParameter( "desctipodenunciap", notaBene.getDescTipoDenunciaP(), Hibernate.CHARACTER );
                q.setParameter( "numedenuncia1", notaBene.getNumeDenuncia1(), Hibernate.INTEGER );
                q.setParameter( "numedenuncia1p", notaBene.getNumeDenuncia1P(), Hibernate.INTEGER );
                q.setParameter( "numedenuncia2", notaBene.getNumeDenuncia2(), Hibernate.SHORT );
                q.setParameter( "numedenuncia2p", notaBene.getNumeDenuncia2P(), Hibernate.SHORT );
                q.setParameter( "numeannodenuncia", notaBene.getNumeAnnoDenuncia(), Hibernate.SHORT );
                q.setParameter( "numeannodenunciap", notaBene.getNumeAnnoDenunciaP(), Hibernate.SHORT );
                q.setParameter( "numegraffati", notaBene.getNumeGraffati(), Hibernate.LONG );
                q.setParameter( "codifkidfondo", tipoFondo, Hibernate.CHARACTER );
                q.setParameter( "numesupettari", notaBene.getNumeSupEttari(), Hibernate.INTEGER );
                q.setParameter( "numesupare", notaBene.getNumeSupAre(), Hibernate.INTEGER );
                q.setParameter( "numesupcentiare", notaBene.getNumeSupCentiare(), Hibernate.INTEGER );
                q.setParameter( "numesupmqint", notaBene.getNumeSupMqInt(), Hibernate.SHORT );
                q.setParameter( "numesupmqdec", notaBene.getNumeSupMqDec(), Hibernate.SHORT );
                q.setParameter( "numevaniint", notaBene.getNumeVaniInt(), Hibernate.SHORT );
                q.setParameter( "numevanidec", notaBene.getNumeVaniDec(), Hibernate.SHORT );
                q.setParameter( "numemcint", notaBene.getNumeMcInt(), Hibernate.SHORT );
                q.setParameter( "numemcdec", notaBene.getNumeMcDec(), Hibernate.SHORT );
                q.setParameter( "codifkidcategcat", catCatasto, Hibernate.INTEGER );
                q.setParameter( "descindirizzo", notaBene.getDescIndirizzo(), Hibernate.STRING );
                q.setParameter( "numecapitaleint", notaBene.getNumeCapitaleInt(), Hibernate.LONG );
                q.setParameter( "numecapitaledec", notaBene.getNumeCapitaleDec(), Hibernate.SHORT );
                q.setParameter( "desccivico", notaBene.getDescCivico(), Hibernate.STRING );
                q.setParameter( "desclotto", notaBene.getDescLotto(), Hibernate.STRING );
                q.setParameter( "descedificio", notaBene.getDescEdificio(), Hibernate.STRING );
                q.setParameter( "descscala", notaBene.getDescScala(), Hibernate.STRING );
                q.setParameter( "descinterno", notaBene.getDescInterno(), Hibernate.STRING );
                q.setParameter( "descpiano", notaBene.getDescPiano(), Hibernate.STRING );
                q.setParameter( "flgerr", notaBene.getFlgErr(), Hibernate.CHARACTER );
                q.setParameter( "descutente", notaBene.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setParameter( "codifkidbene", notaBene.getTBene().getId().getCodiKIdBene(), Hibernate.LONG );
                q.setParameter( "codifkprogruneg", idProgUneg, Hibernate.SHORT );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }


    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String updateNotaBene( final TNotaBene notaBene, final String descUtente, final String codCatasto  )
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Short idProgUneg = null;
                if( notaBene.getTNotaUn() != null )
                {
                    if( notaBene.getTNotaUn().getId() != null )
                    {
                        if( notaBene.getTNotaUn().getId().getCodiKProgrUneg() != 0 )
                        {
                            idProgUneg = notaBene.getTNotaUn().getId().getCodiKProgrUneg();
                        }
                    }
                }

                Character tipoFondo = null;
                if( notaBene.getTTpTipoFondo() != null )
                {
                    tipoFondo = notaBene.getTTpTipoFondo().getCodiKIdFondo();
                }

                Character tipoCat = null;
                if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCat() != null )
                {
                    tipoCat = notaBene.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat();
                }

                Character tipoCatP = null;
                if( notaBene.getTTpTipoCatastoByCodiFkIdTipoCatP() != null )
                {
                    tipoCatP = notaBene.getTTpTipoCatastoByCodiFkIdTipoCatP().getCodiKIdTipoCat();
                }

                Integer catCatasto = null;
                if( notaBene.getTTpCatCatasto() != null )
                {
                    catCatasto = notaBene.getTTpCatCatasto().getCodiKIdCategCat();
                }

                Date dataOggi = new Date();
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_aggiorna_nota_bene" );
                q.setParameter( "codikidnota", notaBene.getId().getCodiKIdNota(), Hibernate.LONG );
                q.setParameter( "codikiduffowner", notaBene.getId().getCodiKIdUffOwner(), Hibernate.INTEGER );
                q.setParameter( "codikprogrbene", notaBene.getId().getCodiKProgrBene(), Hibernate.SHORT );
                q.setParameter( "numeprogrunegbene", notaBene.getNumeProgrUnegBene(), Hibernate.SHORT );
                q.setParameter( "descsedeimmobile", notaBene.getDescSedeImmobile(), Hibernate.STRING );
                q.setParameter( "desccatasto", notaBene.getDescCatasto(), Hibernate.STRING );
                q.setParameter( "desccatastop", notaBene.getDescCatastoP(), Hibernate.STRING );
                q.setParameter( "codifkidtipocat", tipoCat, Hibernate.CHARACTER );
                q.setParameter( "codifkidtipocatp", tipoCatP, Hibernate.CHARACTER );
                q.setParameter( "descsezione", notaBene.getDescSezione(), Hibernate.STRING );
                q.setParameter( "descsezionep", notaBene.getDescSezioneP(), Hibernate.STRING );
                q.setParameter( "descfoglio", notaBene.getDescFoglio(), Hibernate.STRING );
                q.setParameter( "descfogliop", notaBene.getDescFoglioP(), Hibernate.STRING );
                q.setParameter( "descparticella1", notaBene.getDescParticella1(), Hibernate.STRING );
                q.setParameter( "descparticella1p", notaBene.getDescParticella1P(), Hibernate.STRING );
                q.setParameter( "descparticella2", notaBene.getDescParticella2(), Hibernate.STRING );
                q.setParameter( "descparticella2p", notaBene.getDescParticella2P(), Hibernate.STRING );
                q.setParameter( "descsubalterno1", notaBene.getDescSubalterno1(), Hibernate.STRING );
                q.setParameter( "descsubalterno1p", notaBene.getDescSubalterno1P(), Hibernate.STRING );
                q.setParameter( "descsubalterno2", notaBene.getDescSubalterno2(), Hibernate.STRING );
                q.setParameter( "descsubalterno2p", notaBene.getDescSubalterno2P(), Hibernate.STRING );
                q.setParameter( "desctipodenuncia", notaBene.getDescTipoDenuncia(), Hibernate.CHARACTER );
                q.setParameter( "desctipodenunciap", notaBene.getDescTipoDenunciaP(), Hibernate.CHARACTER );
                q.setParameter( "numedenuncia1", notaBene.getNumeDenuncia1(), Hibernate.INTEGER );
                q.setParameter( "numedenuncia1p", notaBene.getNumeDenuncia1P(), Hibernate.INTEGER );
                q.setParameter( "numedenuncia2", notaBene.getNumeDenuncia2(), Hibernate.SHORT );
                q.setParameter( "numedenuncia2p", notaBene.getNumeDenuncia2P(), Hibernate.SHORT );
                q.setParameter( "numeannodenuncia", notaBene.getNumeAnnoDenuncia(), Hibernate.SHORT );
                q.setParameter( "numeannodenunciap", notaBene.getNumeAnnoDenunciaP(), Hibernate.SHORT );
                q.setParameter( "numegraffati", notaBene.getNumeGraffati(), Hibernate.LONG );
                q.setParameter( "codifkidfondo", tipoFondo, Hibernate.CHARACTER );
                q.setParameter( "numesupettari", notaBene.getNumeSupEttari(), Hibernate.INTEGER );
                q.setParameter( "numesupare", notaBene.getNumeSupAre(), Hibernate.INTEGER );
                q.setParameter( "numesupcentiare", notaBene.getNumeSupCentiare(), Hibernate.INTEGER );
                q.setParameter( "numesupmqint", notaBene.getNumeSupMqInt(), Hibernate.SHORT );
                q.setParameter( "numesupmqdec", notaBene.getNumeSupMqDec(), Hibernate.SHORT );
                q.setParameter( "numevaniint", notaBene.getNumeVaniInt(), Hibernate.SHORT );
                q.setParameter( "numevanidec", notaBene.getNumeVaniDec(), Hibernate.SHORT );
                q.setParameter( "numemcint", notaBene.getNumeMcInt(), Hibernate.SHORT );
                q.setParameter( "numemcdec", notaBene.getNumeMcDec(), Hibernate.SHORT );
                q.setParameter( "codifkidcategcat", catCatasto, Hibernate.INTEGER );
                q.setParameter( "descindirizzo", notaBene.getDescIndirizzo(), Hibernate.STRING );
                q.setParameter( "numecapitaleint", notaBene.getNumeCapitaleInt(), Hibernate.LONG );
                q.setParameter( "numecapitaledec", notaBene.getNumeCapitaleDec(), Hibernate.SHORT );
                q.setParameter( "desccivico", notaBene.getDescCivico(), Hibernate.STRING );
                q.setParameter( "desclotto", notaBene.getDescLotto(), Hibernate.STRING );
                q.setParameter( "descedificio", notaBene.getDescEdificio(), Hibernate.STRING );
                q.setParameter( "descscala", notaBene.getDescScala(), Hibernate.STRING );
                q.setParameter( "descinterno", notaBene.getDescInterno(), Hibernate.STRING );
                q.setParameter( "descpiano", notaBene.getDescPiano(), Hibernate.STRING );
                q.setParameter( "flgerr", notaBene.getFlgErr(), Hibernate.CHARACTER );
                q.setParameter( "descutente", notaBene.getDescUtente(), Hibernate.STRING );
                q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                q.setParameter( "codifkidbene", notaBene.getTBene().getId().getCodiKIdBene(), Hibernate.LONG );
                q.setParameter( "codifkprogruneg", idProgUneg, Hibernate.SHORT );
                q.setParameter( "codCatasto", codCatasto, Hibernate.STRING );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<ComuneConservatoriaDto> getComuniByConservatoria( final Long idConserv )
    {
        return (List<ComuneConservatoriaDto>)getHibernateTemplate().execute( new HibernateCallback()
        {

            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {

                Query q = session.getNamedQuery( "PG_CONSERVATORIE_lista_comuni_conservatoria" );
                q.setParameter( "idConserv", idConserv, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( ComuneConservatoriaDto.class ) );
                return q.list();
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public SoggettoInteressatoDto dettaglioPersonaFisica( final Long idSoggetto, final Integer idUff, final Integer versione )
    {
        return (SoggettoInteressatoDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = // dettaglioSoggettoFisico
//                    " select "
//                    + " T_PERSONA_FISICA.CODI_FK_ID_STATO_NASC as statoNasc, "
//                    + " T_PERSONA_FISICA.DESC_COGNOME as cognome, "
//                    + " T_PERSONA_FISICA.DESC_NOME as nome, "
//                    + " T_PERSONA_FISICA.FLG_SESSO as flgSesso, "
//                    + " T_PERSONA_FISICA.DATA_NASCITA as dataNascita, "
//                    + " T_PERSONA_FISICA.DESC_CODICE_FISCALE as codiceFiscalePF, "
//                    + " CASE "
//                    + "    WHEN T_PERSONA_FISICA.CODI_FK_ID_STATO_NASC = '1'  THEN T_AN_COMUNI.DESC_BELFIORE "
//                    + "    ELSE T_AN_STATO.DESC_BELFIORE"
//                    + " END AS codiceComuneNasc, "
//                    + " CASE "
//                    + "    WHEN T_PERSONA_FISICA.CODI_FK_ID_STATO_NASC = '1'  THEN T_AN_COMUNI.DESC_COMUNE "
//                    + "    ELSE T_PERSONA_FISICA. DESC_COM_ESTERO_NASC "
//                    + " END AS comuneNascita,"
//                    + " CASE "
//                    + "    WHEN T_PERSONA_FISICA.CODI_FK_ID_STATO_NASC = '1'  THEN  T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA "
//                    + "    ELSE 'EE' "
//                    + " END AS provNascita "
//                    + " from  T_PERSONA_FISICA, T_AN_COMUNI, T_AN_STATO, T_AN_PROVINCIA  where 1= 1 "
//                    + " and  T_PERSONA_FISICA.CODI_K_ID_PERSONA_FIS    = :idSoggetto "
//                    + " and  T_PERSONA_FISICA.CODI_K_ID_UFF_OWNER      = :idUff "
//                    + " and  T_PERSONA_FISICA.CODI_K_ID_VER            = :versione "
//                    + " and  T_AN_COMUNI.CODI_K_ID_COMUNE         (+)  = T_PERSONA_FISICA.CODI_FK_ID_COM_NASC "
//                    + " and  T_AN_STATO.CODI_K_ID_STATO           (+)  = T_PERSONA_FISICA.CODI_FK_ID_STATO_NASC "
//                    + " and  T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV  (+)  = T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV "
//                    + " order by cognome ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "cognome", Hibernate.STRING );
//                sqlQuery.addScalar( "nome", Hibernate.STRING );
//                sqlQuery.addScalar( "flgSesso", Hibernate.CHARACTER );
//                sqlQuery.addScalar( "dataNascita", Hibernate.DATE );
//                sqlQuery.addScalar( "codiceComuneNasc", Hibernate.STRING );
//                sqlQuery.addScalar( "comuneNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "provNascita", Hibernate.STRING );
//                sqlQuery.addScalar( "codiceFiscalePF", Hibernate.STRING );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Dettaglio_Persona_Fisica" );
                query.setParameter( "idSoggetto", idSoggetto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "versione", versione, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                
            	List lista = query.list(); // dettaglioSoggettoFisico
                if( lista != null && !lista.isEmpty() )
                    return lista.get( 0 );
                else
                    return null;
            }
        } );
    }
    
    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public SoggettoInteressatoDto dettaglioPersonaGiurid( final Long idSoggetto, final Integer idUff, final Integer versione )
    {
        return (SoggettoInteressatoDto)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = // dettaglioSoggettoFisico
//                " select "
//                + " T_PERSONA_GIUR.CODI_FK_ID_STATO_SEDE as statoSede, "
//                + " T_PERSONA_GIUR.DESC_RAGIONE_SOC as denominazione, "
//                + " T_PERSONA_GIUR.DESC_COD_FISC as partitaIva, "
//                + " CASE "
//                + "     WHEN T_PERSONA_GIUR.CODI_FK_ID_STATO_SEDE = '1' "
//                + "     THEN T_AN_COMUNI.DESC_ISTAT_COMUNE "
//                + "     ELSE T_AN_STATO.DESC_ISTAT_STATO "
//                + " END AS codiceComuneSede, "
//                + " CASE "
//                + "     WHEN T_PERSONA_GIUR.CODI_FK_ID_STATO_SEDE = '1' "
//                + "     THEN T_AN_COMUNI.DESC_COMUNE "
//                + "     ELSE T_PERSONA_GIUR.DESC_COM_ESTERO_SEDE "
//                + " END AS comuneSede, "
//                + " CASE "
//                + "     WHEN T_PERSONA_GIUR.CODI_FK_ID_STATO_SEDE = '1' "
//                + "     THEN  T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA "
//                + "     ELSE 'EE' "
//                + " END AS provSede "
//                + " from  T_PERSONA_GIUR, T_AN_COMUNI, T_AN_STATO, T_AN_PROVINCIA  where 1= 1 "
//                + " and  T_PERSONA_GIUR.CODI_K_ID_PERS_GIUR   = :idSoggetto "
//                + " and  T_PERSONA_GIUR.CODI_K_ID_UFF_OWNER   = :idUff "
//                + " and  T_PERSONA_GIUR.CODI_K_ID_VER         = :versione "
//                + " and  T_AN_COMUNI.CODI_K_ID_COMUNE         (+)  = T_PERSONA_GIUR.CODI_FK_ID_COM_SEDE "
//                + " and  T_AN_STATO.CODI_K_ID_STATO           (+)  = T_PERSONA_GIUR.CODI_FK_ID_STATO_SEDE "
//                + " and  T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV  (+)  = T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV "
//                + "order by denominazione ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "statoSede", Hibernate.STRING );
//                sqlQuery.addScalar( "denominazione", Hibernate.STRING );
//                sqlQuery.addScalar( "partitaIva", Hibernate.STRING );
//                sqlQuery.addScalar( "codiceComuneSede", Hibernate.STRING );
//                sqlQuery.addScalar( "comuneSede", Hibernate.STRING );
//                sqlQuery.addScalar( "provSede", Hibernate.STRING );
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_Dettaglio_Persona_Giuridica" );
                query.setParameter( "idSoggetto", idSoggetto, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "versione", versione, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list(); 
                if( lista != null && !lista.isEmpty() )
                    return lista.get( 0 );
                else
                    return null;
            }
        } );
    }
    
    //GP modificato 
   @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer inserisciSoggettoTNotaSogg( 
        final SoggettoInteressatoDto dettaglioPers, final Integer idUffOwn, final String utente, final Long idNota  )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Integer progressivo = cercaMaxProgressivoNotaSogg(idNota, idUffOwn);
                @SuppressWarnings( "unused" )
                Long idNotaSogg = inserisciNotaSogg(dettaglioPers, idUffOwn, utente, idNota, progressivo);
//                String queryStringProgr = 
//                      " select nvl( ( max(CODI_K_PROGR_SOGG) + 1), 1) as progress "
//                    + " from  T_NOTA_SOGG "
//                    + " where CODI_K_ID_NOTA = :idNota and CODI_K_ID_UFF_OWNER = :idUff ";
//
//                SQLQuery queryProgr = getSession().createSQLQuery( queryStringProgr );
//                queryProgr.addScalar( "progress", Hibernate.INTEGER );
//                queryProgr.setParameter( "idNota", idNota, Hibernate.LONG );
//                queryProgr.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = queryProgr.list();
//                if( lista == null || lista.isEmpty() )
//                    return null;
//                else
//                    progressivo = (Integer)lista.get( 0 );
                
//                String queryString = "INSERT INTO T_NOTA_SOGG   "
//                                     + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER, CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG, FLG_TIPO_SOGG, "
//                                     + "   CODI_FK_ID_SOGG_INT, FLG_ERR, DESC_UTENTE, DATA_ULT_AGG )"
//                                     + " VALUES (:idNota, :idUff, '5', :progressivo, :tipologia, :idSoggetto, :flgErrore, :utente, SYSDATE )";
//
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progressivo", progressivo, Hibernate.INTEGER );
//                sqlQuery.setParameter( "tipologia", dettaglioPers.getTipologia(), Hibernate.CHARACTER );
//                sqlQuery.setParameter( "idSoggetto", dettaglioPers.getIdSoggetto(), Hibernate.LONG );
//                sqlQuery.setParameter( "flgErrore", dettaglioPers.getFlagErrore(), Hibernate.CHARACTER );
//                sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                int righe = sqlQuery.executeUpdate(); // inserisci SoggettoTNotaSogg  ---
//                if( righe == 1 )
//                    return progressivo;
//                else
//                    return null;
              return progressivo;
            }
        } );
    }

   
   @SuppressWarnings( { "rawtypes", "unchecked" } )
   public Integer cercaMaxProgressivoNotaSogg( final Long idNota, final Integer idUffOwn)
   {
       return (Integer)getHibernateTemplate().execute( new HibernateCallback()
       {
           public Object doInHibernate( final Session session ) throws HibernateException, SQLException
           {
               Long maxProgr = null;
               Query q = session.getNamedQuery( "PG_CONSERVATORIE_cerca_Max_Progr_NotaSogg" );
               q.setParameter( "idNota", idNota, Hibernate.LONG );
               q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
               q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
               List listaProgr = q.list();
               if( (listaProgr != null) && (listaProgr.size() > 0) )
               {
                   Map<String, Object> ret = (Map<String, Object>)listaProgr.get( 0 );
                   maxProgr = (Long)ret.get( "MAXPROGR" );
               }
               if( maxProgr == null )
               {
                   maxProgr = 1L;
               } else
               {
               	maxProgr = (Long) (maxProgr + 1);

               }
               return maxProgr.intValue();
           }

               
       } );
   }

    
   @SuppressWarnings( { "rawtypes", "unchecked" } )
   public Long inserisciNotaSogg( final SoggettoInteressatoDto dettaglioPers, final Integer idUffOwn, final String utente, final Long idNota, final Integer progressivo )
   {
       return (Long)getHibernateTemplate().execute( new HibernateCallback()
       {
           public Object doInHibernate( final Session session ) throws HibernateException, SQLException
           {
               Long idNotaSogg = null;
               Date dataOggi = new Date();
               Query q = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaSogg" );
               q.setParameter( "idNota", idNota, Hibernate.LONG );
               q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
               q.setParameter( "progressivo", progressivo, Hibernate.INTEGER );
               q.setParameter( "tipologia", dettaglioPers.getTIPOLOGIA(), Hibernate.CHARACTER );
               q.setParameter( "idSoggetto", dettaglioPers.getIDSOGGETTO(), Hibernate.BIG_DECIMAL );
               q.setParameter( "flgErrore", dettaglioPers.getFLAGERRORE(), Hibernate.CHARACTER );
               q.setParameter( "utente", utente, Hibernate.STRING );
               q.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
               q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
               List list = q.list();
               Iterator it = list.iterator();
               while( it.hasNext() )
               {
                   Map<String, Object> row = (Map<String, Object>)it.next();
                   idNotaSogg = (Long)row.get( "IDNOTASOGG" );
               }
               return idNotaSogg; 
           }
       } );
   }
   

   //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes","unused" } )
    public Integer inserisciSoggettoTNotaSoggPG( 
        final SoggettoInteressatoDto dettaglioPers, final Long idNota, final Integer idUffOwn, final Integer progressivo , final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
			public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "INSERT INTO T_NOTA_SOGG_PNF"
//                                     + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER,  CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG, "
//                                     + "   DESC_DENOMINAZIONE, DESC_CODI_COMUNE_SEDE, "
//                                     + "   DESC_COMUNE_SEDE, DESC_PROV_SEDE, DESC_CODI_FISCALE, DESC_UTENTE, DATA_ULT_AGG ) "
//                                     + " VALUES "
//                                     + " ( :idNota, :idUff, '5', :progressivo, :denominazione, :codComuneSede, :descComuneSede, "
//                                     + "   :descProvSede, :codFiscale, :utente, SYSDATE )   ";
//
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
                int righe = 0;
                Long idNotaSoggPnf = null;
                Date dataOggi = new Date();
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaSoggPnf" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setParameter( "progressivo", progressivo, Hibernate.INTEGER );
                query.setParameter( "denominazione", dettaglioPers.getDENOMINAZIONE(), Hibernate.STRING );
                query.setParameter( "codComuneSede", dettaglioPers.getCODICECOMUNESEDE(), Hibernate.STRING );
                query.setParameter( "descComuneSede", dettaglioPers.getCOMUNESEDE(), Hibernate.STRING );
                query.setParameter( "descProvSede", dettaglioPers.getPROVSEDE(), Hibernate.STRING );
                query.setParameter( "codFiscale", dettaglioPers.getPARTITAIVA(), Hibernate.STRING );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                query.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = query.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaSoggPnf = (Long)row.get( "IDNOTASOGGPNF" );
                }
//              int righe = sqlQuery.executeUpdate(); // inserisci SoggettoTNotaSoggPNF  ---
                return righe;
            }
        } );
    }

    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes","unused" } )
    public Integer inserisciSoggettoTNotaSoggPF( 
        final SoggettoInteressatoDto dettaglioPers, final Long idNota, final Integer idUffOwn, final Integer progressivo , final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "INSERT INTO T_NOTA_SOGG_PF"
//                                     + " ( CODI_K_ID_NOTA, CODI_K_ID_UFF_OWNER,  CODI_K_TIPO_RECORD, CODI_K_PROGR_SOGG, "
//                                     + "   DESC_COGNOME,   DESC_NOME, FLG_SESSO, DATA_NASC, DESC_CODI_COMUNE_NASC, "
//                                     + "   DESC_COMUNE_NASC, DESC_PROV_NASC, DESC_CODI_FISCALE, DESC_UTENTE, DATA_ULT_AGG ) "
//                                     + " VALUES "
//                                     + " ( :idNota, :idUff, '5', :progressivo, :cognome, :nome, "
//                                     + "   :flagSesso, TO_DATE(:dataNascita, 'DD-MON-RR'), "
//                                     + "   :codComuneNascita, :descComuneNascita, :descProvNascita, :codFiscale, :utente, SYSDATE )   ";
//
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
                int righe = 0;
                Long idNotaSoggPf = null;
                Date dataOggi = new Date();
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_inserisciNotaSoggPf" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                query.setParameter( "progressivo", progressivo, Hibernate.INTEGER );
                query.setParameter( "cognome", dettaglioPers.getCOGNOME(), Hibernate.STRING );
                query.setParameter( "nome", dettaglioPers.getNOME(), Hibernate.STRING );
                query.setParameter( "flagSesso", dettaglioPers.getFLGSESSO(), Hibernate.CHARACTER );
                query.setParameter( "dataNascita", dettaglioPers.getDATANASCITA(), Hibernate.DATE );
                query.setParameter( "codComuneNascita", dettaglioPers.getCODICECOMUNENASC(), Hibernate.STRING );
                query.setParameter( "descComuneNascita", dettaglioPers.getCOMUNENASCITA(), Hibernate.STRING );
                query.setParameter( "descProvNascita", dettaglioPers.getPROVNASCITA(), Hibernate.STRING );
                query.setParameter( "codFiscale", dettaglioPers.getCODICEFISCALEPF(), Hibernate.STRING );
                query.setParameter( "utente", utente, Hibernate.STRING );
                query.setParameter( "dataultagg", dataOggi, Hibernate.TIMESTAMP );
                query.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List list = query.list();
                Iterator it = list.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    idNotaSoggPf = (Long)row.get( "IDNOTASOGGPF" );
                }
//                int righe = sqlQuery.executeUpdate(); // inserisci SoggettoTNotaSoggPF  ---
                return righe;
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes","unused" } )
    public Integer statoNotaToZero( final Long idNota, final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Character stato;
                int righe = 0;
                //GP modificato 
//                String queryStringStato = "select FLG_STATO from T_NOTA where CODI_K_ID_NOTA = :idNota and CODI_K_ID_UFF_OWNER = :idUff";
//
//                SQLQuery queryStato = getSession().createSQLQuery( queryStringStato );
////                queryStato.addScalar( "stato", Hibernate.CHARACTER );
//                queryStato.setParameter( "idNota", idNota, Hibernate.LONG );
//                queryStato.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = queryStato.list();
//                if( lista == null || lista.isEmpty() )
//                    return null;
//                else
//                    stato = (Character)lista.get( 0 );
                stato = getStatoNota(idNota, idUff);
                if(stato == null){
                	return null;
                }
                if( stato.equals( '1' ) ) 
                {
                    //GP modificato  
                	String statoNota = aggiornaStatoNota(idUff, utente, idNota, new Character('0'));
//                    String queryString = " update T_NOTA  "
//                                         + " set    FLG_STATO = '0', DESC_UTENTE = :utente, DATA_ULT_AGG = sysdate "
//                                         + " where  CODI_K_ID_NOTA = :idNota  and  CODI_K_ID_UFF_OWNER  = :idUff ";
//                    SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                    sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                    sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                    sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                    righe = sqlQuery.executeUpdate(); // aggiornaStatoNota
                }
                return righe;
            }
        } );
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<TitoloDirittoDto> listaTitoliDiritto( final Short progressivoNotaBene, final Long idNota, final Integer idUff)
    {
		return (List<TitoloDirittoDto>) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(final Session session) throws HibernateException, SQLException
			{
//				String queryString = "SELECT "
//						+ "T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG as prog,  "
//						+ "(T_NOTA_SOGG_PF.DESC_COGNOME || ' ' || T_NOTA_SOGG_PF.DESC_NOME || ' nato a ' || T_NOTA_SOGG_PF.DESC_COMUNE_NASC || ' il ' || to_char(T_NOTA_SOGG_PF.DATA_NASC,'DD/MM/YYYY')) AS soggetto,  "
//						+ "T_NOTA_SOGG_BENE.NUME_PROGR_RIGA as progRiga,  "
//						+ "T_TP_TIPO_INTERESSE.DESC_TIPO_INT as descTipoInt, "
//						+ "T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT as idTipoInt, "
// 						+ " CASE "
// 						+ "    WHEN T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC = '0'  THEN (T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) "
// 						+ "    ELSE (T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || ','|| T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) "
// 						+ " END AS numeQuota "	
//						//+ "(T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || ','|| T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) as numeQuota "
//						+ "FROM T_NOTA_SOGG_BENE INNER JOIN T_NOTA_SOGG "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_BENE.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD  = T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG   = T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG "
//						+ "INNER JOIN T_TP_TIPO_INTERESSE "
//						+ "ON T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT = T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT "
//						+ "INNER JOIN T_NOTA_SOGG_PF "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PF.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD = T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG "
//						+ "WHERE T_NOTA_SOGG_BENE.CODI_K_ID_NOTA  = :idNota "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUffOwn "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//						+ "UNION  "
//						+ "SELECT T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG as prog,  "
//						+ "(T_NOTA_SOGG_PNF.DESC_DENOMINAZIONE ||  ' a ' || T_NOTA_SOGG_PNF.DESC_COMUNE_SEDE)  AS soggetto,  "
//						+ "T_NOTA_SOGG_BENE.NUME_PROGR_RIGA as progRiga,  "
//						+ "T_TP_TIPO_INTERESSE.DESC_TIPO_INT as descTipoInt, "
//						+ "T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT as idTipoInt, "
// 						+ " CASE "
// 						+ "    WHEN T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC = '0'  THEN (T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) "
// 						+ "    ELSE (T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || ','|| T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) "
// 						+ " END AS numeQuota "	
//						//+ "(T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || ','|| T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC || '/' || T_NOTA_SOGG_BENE.NUME_QUOTA_DEN) as numeQuota "
//						+ "FROM T_NOTA_SOGG_BENE INNER JOIN T_NOTA_SOGG "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_BENE.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD  = T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG   = T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG "
//						+ "INNER JOIN T_TP_TIPO_INTERESSE "
//						+ "ON T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT = T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT "
//						+ "INNER JOIN T_NOTA_SOGG_PNF "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PNF.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD = T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG "
//						+ "WHERE T_NOTA_SOGG_BENE.CODI_K_ID_NOTA  = :idNota "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUffOwn "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//						+ "ORDER BY prog";
//                SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
//                sqlQuery.addScalar("prog", Hibernate.LONG);
//                sqlQuery.addScalar("soggetto", Hibernate.STRING );
//                sqlQuery.addScalar("progRiga", Hibernate.LONG );
//                sqlQuery.addScalar("descTipoInt", Hibernate.STRING );   
//                sqlQuery.addScalar("numeQuota", Hibernate.STRING );
//                sqlQuery.addScalar("idTipoInt", Hibernate.INTEGER );
//				  List lista = sqlQuery.list();
//                return lista;  
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaTitoliDiritto" );
                query.setParameter("idNota", idNota, Hibernate.LONG);
                query.setParameter("idUff", idUff, Hibernate.INTEGER); 
                query.setParameter("progressivoNotaBene", progressivoNotaBene, Hibernate.SHORT);
                query.setResultTransformer(Transformers.aliasToBean(TitoloDirittoDto.class));
                List lista = query.list();
                return lista;
			}
		});
        //return null;
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public TitoloDirittoDto titoloDirittoPG( final Short progressivoNotaBene, final Long idNota, final Integer idUff, final Short titoloSel, final Integer idTipoInteresse)
    {
		return (TitoloDirittoDto) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(final Session session) throws HibernateException, SQLException
			{
//				String queryString = "SELECT "
//						+ "T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG as PROG,  "
//						+ "T_NOTA_SOGG.FLG_TIPO_SOGG as tipoSogg, "
//						+ "T_NOTA_SOGG_PNF.DESC_DENOMINAZIONE as denominazione,  "
//						+ "T_NOTA_SOGG_BENE.NUME_PROGR_RIGA as PROGRIGA,  "
//						+ "T_TP_TIPO_INTERESSE.DESC_TIPO_INT as DESCTIPOINT, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT as numeQuotaInt, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC as numeQuotaDec, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_DEN as numeQuotaDen, "
//						+ "T_NOTA_SOGG.CODI_FK_ID_SOGG_INT  as soggettoSitMp, "
//						+ "T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT as IDTIPOINT "
//						+ "FROM T_NOTA_SOGG_BENE INNER JOIN T_NOTA_SOGG "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_BENE.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD  = T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG   = T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG "
//						+ "INNER JOIN T_TP_TIPO_INTERESSE "
//						+ "ON T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT = T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT "
//						+ "INNER JOIN T_NOTA_SOGG_PNF "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PNF.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_PNF.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD = T_NOTA_SOGG_PNF.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PNF.CODI_K_PROGR_SOGG "
//						+ "WHERE T_NOTA_SOGG_BENE.CODI_K_ID_NOTA  = :idNota "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUffOwn "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG = :titoloSel "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT = :idTipoInteresse "
//						+ "ORDER BY PROG";
//				SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
//				sqlQuery.addScalar("PROG", Hibernate.BIG_DECIMAL);
//				sqlQuery.addScalar("denominazione", Hibernate.STRING );
//				sqlQuery.addScalar("PROGRIGA", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("DESCTIPOINT", Hibernate.STRING );	
//				sqlQuery.addScalar("soggettoSitMp", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("numeQuotaInt", Hibernate.BIG_DECIMAL );	
//				sqlQuery.addScalar("numeQuotaDec", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("numeQuotaDen", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("tipoSogg", Hibernate.STRING );
//				sqlQuery.addScalar("IDTIPOINT", Hibernate.BIG_DECIMAL );

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_titoloDirittoPG" );
                query.setParameter("idNota", idNota, Hibernate.LONG);
                query.setParameter("idUff", idUff, Hibernate.INTEGER);
                query.setParameter("progressivoNotaBene", progressivoNotaBene, Hibernate.SHORT);
                query.setParameter("titoloSel", titoloSel, Hibernate.SHORT);
                query.setParameter("idTipoInteresse", idTipoInteresse, Hibernate.INTEGER);
                query.setResultTransformer(Transformers.aliasToBean(TitoloDirittoDto.class));
                List lista = query.list();
                if( lista != null && !lista.isEmpty() )
                    return lista.get( 0 );
                else
                    return null;
			}
		});
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public TitoloDirittoDto titoloDirittoPF( final Short progressivoNotaBene, final Long idNota, final Integer idUff, final Short titoloSel, final Integer idTipoInteresse)
    {
		return (TitoloDirittoDto) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(final Session session) throws HibernateException, SQLException
			{
//				String queryString = "SELECT "
//						+ "T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG as PROG,  "
//						+ "T_NOTA_SOGG.FLG_TIPO_SOGG as tipoSogg, "
//						+ "T_NOTA_SOGG_PF.DESC_COGNOME as cognome, "
//						+ "T_NOTA_SOGG_PF.DESC_NOME as nome, "
//						+ "T_NOTA_SOGG_BENE.NUME_PROGR_RIGA as PROGRIGA,  "
//						+ "T_TP_TIPO_INTERESSE.DESC_TIPO_INT as DESCTIPOINT, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT as numeQuotaInt, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC as numeQuotaDec, "
//						+ "T_NOTA_SOGG_BENE.NUME_QUOTA_DEN as numeQuotaDen, "
//						+ "T_NOTA_SOGG.CODI_FK_ID_SOGG_INT  as soggettoSitMp, "
//						+ "T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT as IDTIPOINT "
//						+ "FROM T_NOTA_SOGG_BENE INNER JOIN T_NOTA_SOGG "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_BENE.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD  = T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG   = T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG "
//						+ "INNER JOIN T_TP_TIPO_INTERESSE "
//						+ "ON T_TP_TIPO_INTERESSE.CODI_K_ID_TIPO_INT = T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT "
//						+ "INNER JOIN T_NOTA_SOGG_PF "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA = T_NOTA_SOGG_PF.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD = T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG = T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG "
//						+ "WHERE T_NOTA_SOGG_BENE.CODI_K_ID_NOTA  = :idNota "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG = :titoloSel "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT = :idTipoInteresse "
//						+ "ORDER BY PROG";
//				SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
//				sqlQuery.addScalar("PROG", Hibernate.BIG_DECIMAL);
//				sqlQuery.addScalar("cognome", Hibernate.STRING );
//				sqlQuery.addScalar("nome", Hibernate.STRING );
//				sqlQuery.addScalar("PROGRIGA", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("DESCTIPOINT", Hibernate.STRING );	
//				sqlQuery.addScalar("soggettoSitMp", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("numeQuotaInt", Hibernate.BIG_DECIMAL );	
//				sqlQuery.addScalar("numeQuotaDec", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("numeQuotaDen", Hibernate.BIG_DECIMAL );
//				sqlQuery.addScalar("tipoSogg", Hibernate.STRING );
//				sqlQuery.addScalar("IDTIPOINT", Hibernate.BIG_DECIMAL );
                  
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_titoloDirittoPF" );
                query.setParameter("idNota", idNota, Hibernate.LONG);
                query.setParameter("idUff", idUff, Hibernate.INTEGER);
                query.setParameter("progressivoNotaBene", progressivoNotaBene, Hibernate.SHORT);
                query.setParameter("titoloSel", titoloSel, Hibernate.SHORT);
                query.setParameter("idTipoInteresse", idTipoInteresse, Hibernate.INTEGER);
                query.setResultTransformer(Transformers.aliasToBean(TitoloDirittoDto.class));
                List lista = query.list();
                if( lista != null && !lista.isEmpty() )
                    return lista.get( 0 );
                else
                    return null;
			}
		});
        //return null;
    }
    
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Character getTipoSogg( final Short progressivoNotaBene, final Long idNota, final Integer idUff, final Short titoloSel)
    {
		return (Character) getHibernateTemplate().execute(new HibernateCallback()
		{
			public Object doInHibernate(final Session session) throws HibernateException, SQLException
			{
//				String queryString = "SELECT "
//						+ "T_NOTA_SOGG.FLG_TIPO_SOGG as tipoSogg "
//						+ "FROM T_NOTA_SOGG_BENE INNER JOIN T_NOTA_SOGG "
//						+ "ON T_NOTA_SOGG.CODI_K_ID_NOTA       = T_NOTA_SOGG_BENE.CODI_K_ID_NOTA "
//						+ "AND T_NOTA_SOGG.CODI_K_ID_UFF_OWNER = T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER "
//						+ "AND T_NOTA_SOGG.CODI_K_TIPO_RECORD  = T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD "
//						+ "AND T_NOTA_SOGG.CODI_K_PROGR_SOGG   = T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG "					
//						+ " where 1= 1 AND "
//						+ "T_NOTA_SOGG_BENE.CODI_K_ID_NOTA          = :idNota "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//						+ "AND T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG   = :titoloSel ";
//				SQLQuery sqlQuery = getSession().createSQLQuery(queryString);

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_getTipoSogg" );
                query.setParameter("idNota", idNota, Hibernate.LONG);
                query.setParameter("idUff", idUff, Hibernate.INTEGER);
                query.setParameter("progressivoNotaBene", progressivoNotaBene, Hibernate.SHORT);
                query.setParameter("titoloSel", titoloSel, Hibernate.SHORT);
                List lista = query.list();
                if( lista != null && !lista.isEmpty() )
                    return lista.get( 0 );
                else
                    return null;
            }
        } );
    }
    
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer modificaTitoloDiritto(
        final Short progressivoNotaBene, final Long idNota, final Integer idUff, final Short progrSogg, final String utente, 
        final Long quotaNum, final Long quotaDec, final Long quotaDen, final Integer idTipoInt)
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString = "update T_NOTA_SOGG_BENE "
//                                     + " set "
//                                     + " DESC_UTENTE = :utente, "
//                                     + " DATA_ULT_AGG = sysdate, "
//                                     + " NUME_QUOTA_NUM_INT = :quotaNum, "
//                                     + " NUME_QUOTA_NUM_DEC = :quotaDec, "
//                                     + " NUME_QUOTA_DEN = :quotaDen "
//                                     + " where 1= 1 "
//                                     + " AND T_NOTA_SOGG_BENE.CODI_K_ID_NOTA  = :idNota "
//                                     + " AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//             						 + " AND T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :progressivoNotaBene  "
//             						 + " AND T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = '5' "
//             						 + " AND T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG = :progrSogg "
//             						 + " AND T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT = :idTipoInt";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progressivoNotaBene", progressivoNotaBene, Hibernate.SHORT );
//                sqlQuery.setParameter( "progrSogg", progrSogg, Hibernate.SHORT );
//                sqlQuery.setParameter( "idTipoInt", idTipoInt, Hibernate.INTEGER );
//                sqlQuery.setParameter( "utente", utente, Hibernate.STRING );
//                sqlQuery.setParameter( "quotaNum", quotaNum, Hibernate.LONG );
//                sqlQuery.setParameter( "quotaDec", quotaDec, Hibernate.LONG );
//                sqlQuery.setParameter( "quotaDen", quotaDen, Hibernate.LONG );
//                int righe = sqlQuery.executeUpdate(); 
//                return righe;

                DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggBene.class );
                Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
                Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
                Criterion rest3 = Restrictions.eq( "id.codiKProgrBene", progressivoNotaBene );
                Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", '5' );
                Criterion rest5 = Restrictions.eq( "id.codiKProgrSogg", progrSogg );
                Criterion rest6 = Restrictions.eq( "id.codiKIdTipoInt", idTipoInt );
                criteria.add( Restrictions.and( rest1, rest2 ) );
                criteria.add( Restrictions.and( rest3, rest4 ) );
                criteria.add( Restrictions.and( rest5, rest6 ) );

                TNotaSoggBene item = (TNotaSoggBene)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
                item.setNumeQuotaNumInt( quotaNum.intValue() );
                item.setNumeQuotaNumDec( quotaDec.shortValue() );
                item.setNumeQuotaDen( quotaDen.intValue() );
                item.setDescUtente( utente );
                item.setDataUltAgg( new Date() );

                getHibernateTemplate().saveOrUpdate( item );
                getHibernateTemplate().flush();
                return 1;
            }
        } );
    }

    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String cancellaNotaSoggettoPF(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG_PF "
//                						+ "WHERE  "
//										+ "CODI_K_ID_NOTA= :idNota "
//										+ "and CODI_K_ID_UFF_OWNER= :idUffOwn  "
//										+ "and CODI_K_TIPO_RECORD= 5  "
//										+ "and CODI_K_PROGR_SOGG= :progrNotaSoggetto " ;
//           
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrNotaSoggetto", progrNotaSoggetto, Hibernate.LONG );
//   
//
//                int righe = sqlQuery.executeUpdate(); // delete SoggettoTNotaSoggPF  ---
//                String esito="";
//                
//				if (righe > 0)
//				{
//					esito = "OK";
//				} else
//					esito = "KO";
//				return esito;
//            }
//        } );
//
//    }
    
    //GP modificato questo metodo non funziona a causa della presenza deell'annotazione @OneToOne nel model TNotaSoggPf (come indicato da Catalani, va fatta una stored procedure)
//    @SuppressWarnings("unchecked")
//	public String cancellaNotaSoggettoPF(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto) 
//    {
//    		String esito="KO";
//            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggPf.class );
//            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
//            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
//            Criterion rest3 = Restrictions.eq( "id.codiKProgrSogg", progrNotaSoggetto.shortValue() );
//            Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
//            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
//            List< TNotaSoggPf > lista = getHibernateTemplate().findByCriteria(criteria);
//            if (lista != null) {
//            	TNotaSoggPf itemSelezionato = (TNotaSoggPf)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
//                if( itemSelezionato != null )
//                {
//                    getHibernateTemplate().delete( itemSelezionato );
//                    getHibernateTemplate().flush();
//					esito = "OK";
//                }
//            }
//			return esito;
//     }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String cancellaNotaSoggettoPF(final Long idNota, final Integer idUffOwn, final Long progrNotaSogg)
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_elimina_NotaSoggPf" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "progrNotaSogg", progrNotaSogg, Hibernate.LONG );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }


    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String cancellaNotaSoggettoPG(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG_PNF "
//                						+ "WHERE  "
//										+ "CODI_K_ID_NOTA= :idNota "
//										+ "and CODI_K_ID_UFF_OWNER= :idUffOwn  "
//										+ "and CODI_K_TIPO_RECORD= 5  "
//										+ "and CODI_K_PROGR_SOGG= :progrNotaSoggetto " ;
//           
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrNotaSoggetto", progrNotaSoggetto, Hibernate.LONG );
//   
//
//                int righe = sqlQuery.executeUpdate(); // delete SoggettoTNotaSoggPFN  ---
//                String esito="";
//                
//				if (righe > 0)
//				{
//					esito = "OK";
//				} else
//					esito = "KO";
//				return esito;
//            }
//        } );
//    }
    
    //GP modificato questo metodo non funziona a causa della presenza deell'annotazione @OneToOne nel model TNotaSoggPnf (come indicato da Catalani, va fatta una stored procedure)
//    @SuppressWarnings("unchecked")
//	public String cancellaNotaSoggettoPG(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//    		String esito="KO";
//            DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggPnf.class );
//            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
//            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
//            Criterion rest3 = Restrictions.eq( "id.codiKProgrSogg", progrNotaSoggetto.shortValue() );
//            Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
//            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
//            List< TNotaSoggPnf > lista = getHibernateTemplate().findByCriteria(criteria);
//            if (lista != null) {
//            	TNotaSoggPnf itemSelezionato = (TNotaSoggPnf)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
//                if( itemSelezionato != null )
//                {
//                    getHibernateTemplate().delete( itemSelezionato );
//                    getHibernateTemplate().flush();
//					esito = "OK";
//                }
//            }
//			return esito;
//     }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String cancellaNotaSoggettoPG(final Long idNota, final Integer idUffOwn, final Long progrNotaSogg)
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_elimina_NotaSoggPnf" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "progrNotaSogg", progrNotaSogg, Hibernate.LONG );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }

    
    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String cancellaNotaSoggetto(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG "
//                						+ "WHERE  "
//										+ "CODI_K_ID_NOTA= :idNota "
//										+ "and CODI_K_ID_UFF_OWNER= :idUffOwn  "
//										+ "and CODI_K_TIPO_RECORD= 5  "
//										+ "and CODI_K_PROGR_SOGG= :progrNotaSoggetto " ;
//           
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrNotaSoggetto", progrNotaSoggetto, Hibernate.LONG );
//   
//
//                int righe = sqlQuery.executeUpdate(); // delete SoggettoTNotaSoggPFN  ---
//                String esito="";
//                
//				if (righe > 0)
//				{
//					esito = "OK";
//				} else
//					esito = "KO";
//				return esito;
//            }
//        } );
//    } 

    //GP modificato questo metodo non funziona a causa della presenza deell'annotazione @OneToOne nel model TNotaSoggPnf (come indicato da Catalani, va fatta una stored procedure)
//    @SuppressWarnings("unchecked")
//	public String cancellaNotaSoggetto(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//    		String esito="KO";
//    		
//    		DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSogg.class );
//            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
//            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
//            Criterion rest3 = Restrictions.eq( "id.codiKProgrSogg", progrNotaSoggetto.shortValue() );
//            Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
//            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
//            List< TNotaSogg > lista = getHibernateTemplate().findByCriteria(criteria);
//            if (lista != null && lista.size() > 0) {
//            	TNotaSogg itemSelezionato = (TNotaSogg)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
//                if( itemSelezionato != null )
//                {
//                    getHibernateTemplate().delete( itemSelezionato );
//                    getHibernateTemplate().flush();
//					esito = "OK";
//                }
//            }
//			return esito;
//     }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String cancellaNotaSoggetto(final Long idNota, final Integer idUffOwn, final Long progrNotaSogg)
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_elimina_NotaSogg" );
                q.setParameter( "idNota", idNota, Hibernate.LONG );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "progrNotaSogg", progrNotaSogg, Hibernate.LONG );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List l = q.list();
                HashMap first = (HashMap)l.get( 0 );
                return (String)first.get( "STATO" );
            }
        } );
    }


    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String cancellaNotaBeneSogg(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "DELETE FROM T_NOTA_SOGG_BENE "
//                						+ "WHERE  "
//										+ "CODI_K_ID_NOTA= :idNota "
//										+ "and CODI_K_ID_UFF_OWNER= :idUffOwn  "
//										+ "and CODI_K_TIPO_RECORD= 5  "
//										+ "and CODI_K_PROGR_SOGG= :progrNotaSoggetto " ;
//           
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrNotaSoggetto", progrNotaSoggetto, Hibernate.LONG );
//   
//
//                int righe = sqlQuery.executeUpdate(); // delete SoggettoTNotaSoggPFN  ---
//                String esito="";
//                
//				if (righe > 0)
//				{
//					esito = "OK";
//				} else
//					esito = "KO";
//				return esito;
//            }
//        } );
//
//    } 

    @SuppressWarnings("unchecked")
    public String cancellaNotaBeneSogg(final Long idNota, final Integer idUffOwn, final Long progrNotaSoggetto)
    {
    		String esito="KO";
    		
    		DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggBene.class );
            Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUffOwn );
            Criterion rest3 = Restrictions.eq( "id.codiKProgrSogg", progrNotaSoggetto.shortValue() );
            Criterion rest4 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ) );
            List< TNotaSoggBene > lista = getHibernateTemplate().findByCriteria(criteria);
            if (lista != null && lista.size() > 0) 
            {
            	getHibernateTemplate().deleteAll(lista);
                getHibernateTemplate().flush();
            	esito = "OK";
            }
			return esito;
     }

    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer inserisciTitoliDiritto( final Long idNota,final Long beneSitMp,final Long progrSogg, final Integer tipoInteresse, 
        final Long quotaIntero,final Long quotaDecimale, final Long denominQuota,final Integer idUff, final String utente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//            	String queryString = "INSERT INTO T_NOTA_SOGG_BENE   "
//                        + " ( CODI_K_PROGR_BENE   ,    "
//                        + "   CODI_K_ID_UFF_OWNER,    "
//                        + "   CODI_K_TIPO_RECORD,    "
//                        + "   CODI_K_PROGR_SOGG,    "
//                        + "   CODI_K_ID_NOTA ,    "
//                        + "   CODI_K_ID_TIPO_INT  , "
//						+ "   NUME_PROGR_RIGA   , "
//						+ "   NUME_QUOTA_NUM_INT   , "
//						+ "   NUME_QUOTA_NUM_DEC   , "
//						+ "   NUME_QUOTA_DEN   , "
//						+ "   FLG_TIPO_INT   , "
//						+ "   DESC_TIPO_INT  , "
//                        + "   DESC_UTENTE,"
//                        + "   DATA_ULT_AGG "
//                        + "    ) "
//                        + " VALUES (:beneSitMp, :idUff, '5', :progrSogg, :idNota, :tipoInteresse,"
//                        + "   ( select nvl(( max(NUME_PROGR_RIGA) + 1),1) "
//                        + "     from T_NOTA_SOGG_BENE where 1 = 1 "
//                        + "     and  CODI_K_PROGR_SOGG = :progrSogg "
//                        + "     and  CODI_K_ID_UFF_OWNER = :idUff "
//                        + "     and  CODI_K_ID_NOTA = :idNota "
//                        + "     and  CODI_K_TIPO_RECORD = '5' "
//                        + "   ), "
//                        + " :quotaIntero, :quotaDecimale,:denominQuota,  "
//                        
//						+ "   (select ( DESC_CONS_2) from T_TP_TIPO_INTERESSE where"
//						+ "             CODI_K_ID_TIPO_INT = :tipoInteresse), "
//						+ "   (select ( DESC_CONS) from T_TP_TIPO_INTERESSE where"
//						+ "             CODI_K_ID_TIPO_INT = :tipoInteresse), "
//            			+ " :utente, SYSDATE)  ";
//                SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//                int righe = sqlQuery.executeUpdate(); // inserisci inserisciTitoliDiritto
//                return righe;
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_inserisciTitoliDiritto" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
                query.setParameter( "beneSitMp", beneSitMp, Hibernate.LONG );
                query.setParameter( "tipoInteresse", tipoInteresse, Hibernate.INTEGER );
                query.setParameter( "quotaIntero", quotaIntero, Hibernate.LONG );
                query.setParameter( "quotaDecimale", quotaDecimale, Hibernate.LONG );
                query.setParameter( "denominQuota", denominQuota, Hibernate.LONG );
                query.setParameter( "utente", utente, Hibernate.STRING );
                List lista = query.list();
                return lista.size();   
            }
        });
    }
    //rm
    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Double controllaCapienza( final Long idNota,final Long beneSitMp,final Long progrSogg,
    		final Integer tipoInteresse,final Integer idUff, final String descUtente )
    {
        return (Double)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//        		" SELECT "
//        		+ "   to_number "
//        		+ "   ( to_char "
//        		+ "     ( SUM "
//        		+ "       ( ( T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT || ',' || T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC ) "
//        		+ "         / T_NOTA_SOGG_BENE.NUME_QUOTA_DEN "
//        		+ "       ),'999999.99999' "
//        		+ "     ),  '999999.99999' "
//        		+ "   ) AS capienza "
//        		+ " FROM    T_NOTA_SOGG_BENE "
//        		+ " where 1 = 1 "
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_ID_NOTA      = :idNota "
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD  = 5 "	
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT  = :tipoInteresse "	
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :beneSitMp "	
//        		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG  <> :progrSogg "	;
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "capienza", Hibernate.DOUBLE );
                
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_controllaCapienza" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setParameter( "beneSitMp", beneSitMp, Hibernate.LONG );
                query.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
                query.setParameter( "tipoInteresse", tipoInteresse, Hibernate.INTEGER );
                List lista = query.list();
                
                if( !lista.isEmpty() && lista.size() > 0 && lista.get( 0 ) != null )
                    return  ((BigDecimal)lista.get( 0 )).doubleValue();
                else
                    return new Double( "0" );
            }
        } );
    }
    
    //nrm
    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Integer controllaDuplicato( final Long idNota,final Long beneSitMp,final Long progrSogg,
    		final Integer tipoInteresse,final Integer idUff, final String descUtente )
    {
        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {	
//                String queryString =
//                		"SELECT COUNT(*) "
//                		+ "	 AS capienza "
//                		+ " FROM    T_NOTA_SOGG_BENE "
//                		+ " where 1= 1 "
//                		+ " AND T_NOTA_SOGG_BENE.CODI_K_ID_NOTA = :idNota "
//                		+ " AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD    = 5 "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT   = :tipoInteresse "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :beneSitMp "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG   = :progrSogg "	;
//  
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "capienza", Hibernate.INTEGER );
//                sqlQuery.setParameter( "beneSitMp", beneSitMp, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "tipoInteresse", tipoInteresse, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                if( !lista.isEmpty() && (lista.size() > 0) )
//                {
//                    if( lista.get( 0 ) == null )
//                        return new Integer( "0" );
//                    else
//                        return lista.get( 0 );
//                }else
//                    return lista.get( 0 );
                              
                Criteria criteria = session.createCriteria( TNotaSoggBene.class );
                Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
                Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
                Criterion rest3 = Restrictions.eq( "id.codiKTipoRecord", 5 );
                Criterion rest4 = Restrictions.eq( "id.codiKIdTipoInt", tipoInteresse );
                Criterion rest5 = Restrictions.eq( "id.codiKProgrBene", beneSitMp.shortValue() );
                Criterion rest6 = Restrictions.eq( "id.codiKProgrSogg", progrSogg.shortValue() );
                criteria.add( Restrictions.and( rest1, rest2 ) );
                criteria.add( Restrictions.and( rest3, rest4 ) );
                criteria.add( Restrictions.and( rest5, rest6 ) );
                criteria.setProjection( Projections.rowCount() );
                Integer ret = (Integer)criteria.uniqueResult();
                return ret ;
            }
        } );
    }

    //nrm
    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Character statoNota( final Long idNota, final Integer idUff, final String utente )
//    {
//        return (Character)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                Character stato='X';
//                int righe = 0;
//                String queryStringStato = "select FLG_STATO from T_NOTA where CODI_K_ID_NOTA = :idNota and CODI_K_ID_UFF_OWNER = :idUff";
//
//                SQLQuery queryStato = getSession().createSQLQuery( queryStringStato );
////                queryStato.addScalar( "stato", Hibernate.CHARACTER );
//                queryStato.setParameter( "idNota", idNota, Hibernate.LONG );
//                queryStato.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = queryStato.list();
//                if( lista == null || lista.isEmpty() )
//                    return stato;
//                else
//                    stato = (Character)lista.get( 0 );
//               
//                return stato;
//            }
//        } );
//    }

    @SuppressWarnings( "unchecked" )
    public Character getStatoNota(  final Long idNota, final Integer idUff )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TNota.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
        criteria.add( Restrictions.and( rest1, rest2 ) );
        List<TNota> u = getHibernateTemplate().findByCriteria( criteria );
        TNota nota = DataAccessUtils.requiredSingleResult( u );
        Character flagStato = nota.getFlgStato();
        return flagStato;
    }
    
    //GP modificato - FG: corretto
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public Integer cancellaTitoliDiritto( final Long idNota,final Long beneSitMp,final Long progrSogg,
//    		final Integer tipoInteresse,final Integer idUff )
//    {
//        return (Integer)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//            	 String queryString = "DELETE FROM T_NOTA_SOGG_BENE "
// 						+ " WHERE 1=1 "
// 						+ " AND T_NOTA_SOGG_BENE.CODI_K_ID_NOTA = :idNota "
//                		+ " AND T_NOTA_SOGG_BENE.CODI_K_ID_UFF_OWNER = :idUff "
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_TIPO_RECORD    = 5 "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT   = :tipoInteresse "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE   = :beneSitMp "	
//                		+ " AND  T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG   = :progrSogg "	; 
//            	 SQLQuery sqlQuery = getSession().createSQLQuery( queryString );
//            	 
//            	 sqlQuery.setParameter( "beneSitMp", beneSitMp, Hibernate.LONG );
//                 sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//              
//                 sqlQuery.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
//                 sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                 sqlQuery.setParameter( "tipoInteresse", tipoInteresse, Hibernate.INTEGER );
//              
//                 int righe = sqlQuery.executeUpdate(); 
//                 return righe;
//            }
//        });
//    }
    
    public String cancellaTitoliDiritto( final Long idNota,final Long beneSitMp,final Long progrSogg,
    		final Integer tipoInteresse,final Integer idUff )
    {
    		String esito="KO";
    		
    		DetachedCriteria criteria = DetachedCriteria.forClass( TNotaSoggBene.class );
            Criterion rest1 = Restrictions.eq( "id.codiKProgrBene", beneSitMp.shortValue() );
            Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
            Criterion rest3 = Restrictions.eq( "id.codiKTipoRecord", new Character('5') );
            Criterion rest4 = Restrictions.eq( "id.codiKProgrSogg", progrSogg.shortValue() );
            Criterion rest5 = Restrictions.eq( "id.codiKIdNota", idNota );
            Criterion rest6 = Restrictions.eq( "id.codiKIdTipoInt", tipoInteresse );
            criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ).add( rest4 ).add( rest5 ).add( rest6 ) );
            List<?> lista = getHibernateTemplate().findByCriteria(criteria);
            if (lista != null && lista.size() > 0) {
            	TNotaSoggBene itemSelezionato = (TNotaSoggBene)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
                if( itemSelezionato != null )
                {
                    getHibernateTemplate().delete( itemSelezionato );
                    getHibernateTemplate().flush();
					esito = "OK";
                }
            }
			return esito;
     }

   
    //GP modificato 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoInteressatoDto> listaSoggettiUnitaNegoziali(final Long idNota, final Integer idUff )
    {
        return (List<SoggettoInteressatoDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
            	
//                String queryString =
//                	"SELECT DISTINCT CODI_K_PROGR_SOGG as progSoggetto, CODI_K_PROGR_UNEG as progUnitaNegoziale, "+
//					" CODI_K_TIPO_RECORD as qualificaUnitaNegoziale, "+
//					" NUME_QUOTA_NUM_INT as numeQuotaIntUnitaNegoziale, NUME_QUOTA_NUM_DEC as numeQuotaDecUnitaNegoziale, "+
//					" NUME_QUOTA_DEN as numeQuotaDenomUnitaNegoziale, CODI_K_ID_TIPO_INT as titoloInteresseUnitaNegoziale, "+
//					" DESC_TIPO_INT as descrInteresseUnitaNegoziale, CODI_FK_PROGR_SOGG_C as regimeConiugiUnitaNegoziale, CODI_FK_ID_TIPO_REGIME as idregimeUnitaNegoziale "+
//                	" FROM T_NOTA_SOGG_UN "+ 
//                	" WHERE CODI_K_ID_NOTA = :idNota "+
//                	" AND CODI_K_ID_UFF_OWNER = :idUff "+
//                	" ORDER BY CODI_K_PROGR_UNEG ";
//                
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//				sqlQuery.addScalar("progSoggetto", Hibernate.LONG );	
//				sqlQuery.addScalar("progUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("qualificaUnitaNegoziale", Hibernate.CHARACTER );
//				sqlQuery.addScalar("numeQuotaIntUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("numeQuotaDecUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("numeQuotaDenomUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("titoloInteresseUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("descrInteresseUnitaNegoziale", Hibernate.STRING );
//				sqlQuery.addScalar("regimeConiugiUnitaNegoziale", Hibernate.LONG );
//				sqlQuery.addScalar("idregimeUnitaNegoziale", Hibernate.CHARACTER );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return lista;
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaSoggettiUnitaNegoziali" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( SoggettoInteressatoDto.class ) );
                List lista = query.list();
                return lista;
           }
        } );
    }
    
    
    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String getTipoTerreno(final Long idBene, final Integer idUff)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =
//                    " select CODI_FK_ID_SCTG_BENE as tipoTerreno from T_BENE "
//                        + " where CODI_K_ID_BENE = :idBene "
//                        + " and CODI_K_ID_UFF_OWNER = :idUff "; 
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "tipoTerreno", Hibernate.STRING );
//                sqlQuery.setParameter( "idBene", idBene, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                if( !lista.isEmpty() )
//                    return lista.get( 0 );
//                else
//                return null;
//            }
//        } );
//    }

    @SuppressWarnings( "unchecked" )
    public String getTipoTerreno(  final Long idBene, final Integer idUff )
    {
    	String tipoTerreno = null;
    	DetachedCriteria criteria = DetachedCriteria.forClass( TBene.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdBene", idBene );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
        criteria.add( Restrictions.and( rest1, rest2 ) );
        List<TBene> lista = getHibernateTemplate().findByCriteria( criteria );
        if( !lista.isEmpty() ) {
            TBene bene = DataAccessUtils.requiredSingleResult( lista );
            if (bene.getTTpSottocatBene() != null) {
                tipoTerreno = ""+ bene.getTTpSottocatBene().getCodiKIdSottocat();
            }
        }
        
        return tipoTerreno;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniAnnot(final int idUffOwn,final Integer tipoAtto,final Integer catAtto,
			   final Short repAnno,final Long repNumero,final Long idProced, final String tenoreMisura )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Annot" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setParameter( "tenoreMisura", tenoreMisura, Hibernate.STRING );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniTrascrD(final int idUffOwn,final Integer tipoAtto,final Integer catAtto,
			   final Short repAnno,final Long repNumero,final Long idProced )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Trascr_D" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociabileDto> listaBeniTrascrS(final int idUffOwn,final Integer tipoAtto,final Integer catAtto,
			   final Short repAnno,final Long repNumero,final Long idProced )
    {
        return (List<BeneAssociabileDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Beni_Trascr_S" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( BeneAssociabileDto.class ) );
                return q.list();
            }
        } );
    }

    //FG: fatto 
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Boolean esisteSoggettoFisico( final Long idNota, final Integer idUff, final Integer progrSoggCon )
    {
        return (Boolean)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Criteria criteria = session.createCriteria( TNotaSoggPf.class );
                Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
                Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
                Criterion rest3 = Restrictions.eq( "id.codiKTipoRecord", 5 );
                Criterion rest4 = Restrictions.eq( "id.codiKProgrSogg", progrSoggCon.shortValue() );
                criteria.add( Restrictions.and( rest1, rest2 ) );
                criteria.add( Restrictions.and( rest3, rest4 ) );
                criteria.setProjection( Projections.rowCount() );
                Integer ret = (Integer)criteria.uniqueResult();
                return ( ret > 0) ;
//                String queryString = "SELECT COUNT(*) as occorrenza"
//                        + " from T_NOTA_SOGG_PF "
//                        + " where 1= 1 "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_TIPO_RECORD   = 5 "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_NOTA       = :idNota "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_ID_UFF_OWNER  = :idUff "
//                        + " and  T_NOTA_SOGG_PF.CODI_K_PROGR_SOGG    = :progrSoggCon ";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "occorrenza", Hibernate.INTEGER );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "progrSoggCon", progrSoggCon, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return ( !lista.isEmpty() );
            }
        } );
    }
    
/*
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<SoggettoBeneDto> listaSoggettiBene(final int idUffOwn, final Integer tipoAtto, final Integer catAtto,
            final Short repAnno, final Long repNumero, final Long idBene, final Long idProced)
    {
        return (List<SoggettoBeneDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Soggetti_Contro" );
                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
                q.setParameter( "idBene", idBene, Hibernate.LONG );
                q.setParameter( "idProced", idProced, Hibernate.LONG );
                q.setResultTransformer( Transformers.aliasToBean( SoggettoBeneDto.class ) );
                List lista = q.list();
                if( lista == null || lista.isEmpty() )
                    throw new HibernateException( "Soggetti non trovati" );
                return lista;
            }
        } );
    }
*/
    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String getDescrizioneQuadroD(final Integer idUff, final Long idNota)
//    {
//        return (String) getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString =
//                		"Select DESC_NOTA from T_NOTA_QUADRO_D "
//                		+"where CODI_K_ID_NOTA = :idNota "
//                		+"and CODI_K_ID_UFF_OWNER = :idUff " 
//                		+"order by CODI_K_TIPO_INF, CODI_K_PROGR_D";		
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//                List lista = sqlQuery.list();
//                String descrizione = "";
//                Iterator it = lista.iterator();
//                while( it.hasNext() )
//                {
//                    Map<String, Object> row = (Map<String, Object>)it.next();
//                    descrizione = descrizione+" "+((String)row.get( "DESC_NOTA" ));
//                }
//                return descrizione;
//            }
//        } );
//    }
    
    @SuppressWarnings( "unchecked" )
    public String getDescrizioneQuadroD( final Integer idUff, final Long idNota )
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TNotaQuadroD.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdNota", idNota );
        Criterion rest2 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
        criteria.add( Restrictions.and( rest1, rest2 ) );
        List<TNotaQuadroD> lista = getHibernateTemplate().findByCriteria( criteria );
        String descrizione = "";
        Iterator<TNotaQuadroD> it = lista.iterator();
        while( it.hasNext() )
        {
        	TNotaQuadroD quadroD = new TNotaQuadroD();
        	quadroD =  it.next();
        	descrizione = descrizione+" "+quadroD.getDescNota();
        }
        return descrizione;
    }

    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public String getSiglaProvincia(final String comune)
//    {
//        return (String)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = "select T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProvincia " 
//                		+ " FROM T_AN_PROVINCIA, T_AN_COMUNI  " 
//                		+ " WHERE T_AN_COMUNI.DESC_COMUNE= :comune "
//                		+ " AND T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV ";
//
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.addScalar( "siglaProvincia", Hibernate.STRING );
//                sqlQuery.setParameter( "comune", comune, Hibernate.STRING );
//                List lista = sqlQuery.list();
//                if( !lista.isEmpty() )
//                    return lista.get( 0 );
//                else
//                return null;
//            }
//        } );
//    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public String getSiglaProvincia(final String comune)
    {
        return (String)getHibernateTemplate().execute( new HibernateCallback()
        {
            public String doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query query = session.getNamedQuery( "PG_CONSERVATORIE_ricerca_sigla_provincia" );
                query.setParameter( "comune", comune, Hibernate.STRING );
                query.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List lista = query.list();
	            if( !lista.isEmpty() ) {
	                HashMap first = (HashMap)lista.get( 0 );
	                return (String)first.get( "SIGLAPROV" );
	            }  else {
	  	          return null;
	            }
            }
        } );
    }


    //GP modificato 
//    @SuppressWarnings( { "unchecked", "rawtypes" } )
//    public TipoDomicilioDto getTipoDomicilio(final Integer idUff, final Long numeroProcedimento, final Long idSoggetto)
//    {
//        return (TipoDomicilioDto)getHibernateTemplate().execute( new HibernateCallback()
//        {
//            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
//            {
//                String queryString = 
//                "SELECT DESC_INDIR_RES as descIndirRes, DESC_INDIR_DOM as descIndirDom "
//                + "FROM T_REL_PROC_SOGINT "
//                + " where 1= 1 "
//                + " and  T_REL_PROC_SOGINT.CODI_K_ID_UFF_OWNER  = :idUff "
//                + " and  T_REL_PROC_SOGINT.CODI_K_ID_PROC  = :numeroProcedimento "
//                + " and  T_REL_PROC_SOGINT.CODI_K_ID_SOGG_INT  = :idSoggetto ";
//                
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( TipoDomicilioDto.class ) );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                sqlQuery.setParameter( "numeroProcedimento", numeroProcedimento, Hibernate.LONG );
//                sqlQuery.setParameter( "idSoggetto", idSoggetto, Hibernate.LONG );
//                sqlQuery.addScalar( "descIndirRes", Hibernate.STRING );
//                sqlQuery.addScalar( "descIndirDom", Hibernate.STRING );
//                List lista = sqlQuery.list(); 
//                if( lista != null && !lista.isEmpty() )
//                    return lista.get( 0 );
//                else
//                    return null;
//            }
//        } );
//    }
    
    @SuppressWarnings( "unchecked" )
    public TipoDomicilioDto getTipoDomicilio(final Integer idUff, final Long numeroProcedimento, final Long idSoggetto)
    {
        TipoDomicilioDto domicilio = new TipoDomicilioDto();
        DetachedCriteria criteria = DetachedCriteria.forClass( TRelProcSogint.class );
        Criterion rest1 = Restrictions.eq( "id.codiKIdProc", numeroProcedimento );
        Criterion rest2 = Restrictions.eq( "id.codiKIdSoggInt", idSoggetto );
        Criterion rest3 = Restrictions.eq( "id.codiKIdUffOwner", idUff );
        criteria.add( Restrictions.conjunction().add( rest1 ).add( rest2 ).add( rest3 ) );
        List<TRelProcSogint> lista = getHibernateTemplate().findByCriteria( criteria );
        if( !lista.isEmpty() ) {
            TRelProcSogint risultato = DataAccessUtils.requiredSingleResult( lista );
            domicilio = new TipoDomicilioDto();
            domicilio.setDescIndirDom(risultato.getDescIndirDom());
            domicilio.setDescIndirRes(risultato.getDescIndirRes());
        }
        return domicilio;
    }

    //FG: fatto
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public List<BeneAssociatoDescDto> listaUnitaNegoziali(final Long idNota, final Integer idUff )
    {
        return (List<BeneAssociatoDescDto>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
//                String queryString =
//                	"Select CODI_FK_PROGR_UNEG as progUneg, CODI_K_PROGR_BENE as progressivo "+  
//						"from   T_BENE bene,T_NOTA_BENE notaBene, T_NOTA nota "+
//						"where notaBene.CODI_K_ID_NOTA    = :idNota "+
//						"and notaBene.CODI_K_ID_UFF_OWNER = :idUff "+
//						"and bene.CODI_K_ID_BENE          = notaBene.CODI_FK_ID_BENE "+
//						"and bene.CODI_K_ID_UFF_OWNER     = notaBene.CODI_K_ID_UFF_OWNER "+
//						"and nota.CODI_K_ID_NOTA          = notaBene.CODI_K_ID_NOTA "+
//						"and nota.CODI_K_ID_UFF_OWNER     = notaBene.CODI_K_ID_UFF_OWNER "+
//						"order by CODI_FK_PROGR_UNEG";
//                SQLQuery sqlQuery = session.createSQLQuery( queryString );
//				  sqlQuery.addScalar("progUneg", Hibernate.LONG );	
//				  sqlQuery.addScalar("progressivo", Hibernate.LONG );
//                sqlQuery.setResultTransformer( Transformers.aliasToBean( BeneAssociatoDescDto.class ) );
//                sqlQuery.setParameter( "idNota", idNota, Hibernate.LONG );
//                sqlQuery.setParameter( "idUff", idUff, Hibernate.INTEGER );
//                List lista = sqlQuery.list();
//                return lista;

                Query query = session.getNamedQuery( "PG_CONSERVATORIE_listaUnitaNegoziali" );
                query.setParameter( "idNota", idNota, Hibernate.LONG );
                query.setParameter( "idUff", idUff, Hibernate.INTEGER );
                query.setResultTransformer( Transformers.aliasToBean( BeneAssociatoDescDto.class ) );
                List lista = query.list();
                return lista;
            }
        } );
    }
   
 //Ricerca comunicazioni per generaz note  
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<ComunicazionePredispDto> listaComunicazioniConservatoriePredisp(
            final Integer ufficio, final String tipoNota, final Integer tipoAtto,final Integer catAtto, final Short repAnno, final Long repNumero, final String attoSpecie, final String descSpecie)
        {
            return (List<ComunicazionePredispDto>)getHibernateTemplate().execute( new HibernateCallback()
            {
                public Object doInHibernate( final Session session ) throws HibernateException, SQLException
                {
                    Query q = session.getNamedQuery( "PG_CONSERVATORIE_lista_comunicazioni_predisp" );
                    q.setParameter( "ufficio", ufficio, Hibernate.INTEGER );
                    q.setParameter( "tipoNota", tipoNota, Hibernate.STRING );
                    q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
                    q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
                    q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
                    q.setParameter( "repNumero", repNumero, Hibernate.LONG );
                    q.setParameter( "attoSpecie", attoSpecie, Hibernate.STRING );
                    q.setParameter( "descSpecie", descSpecie, Hibernate.STRING);
                    
                    q.setResultTransformer( Transformers.aliasToBean( ComunicazionePredispDto.class ) );
                    return q.list();
                }
            } );
        }

        //GP modificato
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<Object[]> getComuniSediTribun( final String pattern )
        {
            return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//                    SQLQuery query = getSession().createSQLQuery(
//                        "select DISTINCT "
//                        + "T_AN_COMUNI.CODI_K_ID_COMUNE as idComune, "
//                        + "T_AN_COMUNI.DESC_COMUNE as desComune, "
//                        + "T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProv "
//                        + "from T_AN_COMUNI, T_AN_UFFICIO, T_AN_PROVINCIA "
//                        + "where T_AN_COMUNI.DESC_COMUNE like :pattern "
//                        + "and T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV "
//                        + "and T_AN_COMUNI.CODI_K_ID_COMUNE = T_AN_UFFICIO.CODI_FK_ID_COM_SEDE "
//                        + "and T_AN_UFFICIO.CODI_FK_ID_TIPO_UFF in (6,19) "
//                        + "AND ( T_AN_COMUNI.DATA_FINE_VALIDITA IS NULL or T_AN_COMUNI.DATA_FINE_VALIDITA > sysdate ) "
//                        + "order by  T_AN_COMUNI.DESC_COMUNE " );
//                    
//                    query.addScalar("idComune", Hibernate.LONG);
//                    query.addScalar("desComune", Hibernate.STRING);
//                    query.addScalar("siglaProv", Hibernate.STRING);
//                    query.setParameter("pattern", pattern, Hibernate.STRING);
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_getComuniSediTribun" );
                    query.setParameter( "pattern", pattern, Hibernate.STRING );
                    List lista = query.list();
                    return lista;
                }
            });
        }

        //GP modificato
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<Object[]> getAllComuniProv( final String pattern )
        {
            return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//                    SQLQuery query = getSession().createSQLQuery(
//                        "select DISTINCT "
//                        + "T_AN_COMUNI.DESC_CATASTO as descCatasto, "
//                        + "T_AN_COMUNI.DESC_COMUNE as desComune, "
//                        + "T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProv "
//                        + "from T_AN_COMUNI, T_AN_PROVINCIA "
//                        + "where T_AN_COMUNI.DESC_COMUNE like :pattern "
//                        + "and T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV "
//                        + "AND ( T_AN_COMUNI.DATA_FINE_VALIDITA IS NULL or T_AN_COMUNI.DATA_FINE_VALIDITA > sysdate ) "
//                        + "order by  T_AN_COMUNI.DESC_COMUNE " );
//                    
//                    query.addScalar("descCatasto", Hibernate.STRING);
//                    query.addScalar("desComune", Hibernate.STRING);
//                    query.addScalar("siglaProv", Hibernate.STRING);
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_getAllComuniProv" );
                    query.setParameter("pattern", pattern, Hibernate.STRING);
                    List lista = query.list();
                    return lista;
                }
            });
        }
      
        //GP modificato
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<Object[]> getBelfComuniStartingWith( final String pattern )
        {
            return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//                    SQLQuery query = getSession().createSQLQuery(
//                        "select DISTINCT T_AN_COMUNI.DESC_BELFIORE as idComune, "
//                        + "T_AN_COMUNI.DESC_COMUNE as desComune, "
//                        + "T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProv "
//                        + "from T_AN_COMUNI, T_AN_PROVINCIA "
//                        + "where T_AN_COMUNI.DESC_COMUNE like :pattern "
//                        + "and T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV "
//                        + "AND ( T_AN_COMUNI.DATA_FINE_VALIDITA IS NULL or T_AN_COMUNI.DATA_FINE_VALIDITA > sysdate ) "
//                        + "order by  T_AN_COMUNI.DESC_COMUNE " );
//                    query.addScalar("idComune", Hibernate.STRING);
//                    query.addScalar("desComune", Hibernate.STRING);
//                    query.addScalar("siglaProv", Hibernate.STRING);
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_getBelfComuniStartingWith" );
                    query.setParameter("pattern", pattern, Hibernate.STRING);
                    List lista = query.list();
                    return lista;
                }
            });
        }
      
        //GP modificato
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<Object[]> getIstatComuniStartingWith( final String pattern )
        {
            return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//                    SQLQuery query = getSession().createSQLQuery(
//                        "select DISTINCT T_AN_COMUNI.DESC_ISTAT_COMUNE as idComune, "
//                        + "T_AN_COMUNI.DESC_COMUNE as desComune, "
//                        + "T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProv "
//                        + "from T_AN_COMUNI, T_AN_PROVINCIA "
//                        + "where T_AN_COMUNI.DESC_COMUNE like :pattern "
//                        + "and T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV "
//                        + "AND ( T_AN_COMUNI.DATA_FINE_VALIDITA IS NULL or T_AN_COMUNI.DATA_FINE_VALIDITA > sysdate ) "
//                        + "order by  T_AN_COMUNI.DESC_COMUNE " );
//                    query.addScalar("idComune", Hibernate.STRING);
//                    query.addScalar("desComune", Hibernate.STRING);
//                    query.addScalar("siglaProv", Hibernate.STRING);
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_getIstatComuniStartingWith" );
                    query.setParameter("pattern", pattern, Hibernate.STRING);
                    List lista = query.list();
                    return lista;
                }
            });
        }
        
        //GP modificato
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public Object[] getComuneByUfficio( final Integer codUfficio )
        {
            return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//                    SQLQuery query = getSession().createSQLQuery(
//                          "SELECT "
//                        + "T_AN_COMUNI.CODI_K_ID_COMUNE as idComune, "
//                        + "T_AN_COMUNI.DESC_COMUNE as desComune, "
//                        + "T_AN_PROVINCIA.DESC_SIGLA_PROVINCIA as siglaProv "
//                        + "FROM T_AN_COMUNI, T_AN_UFFICIO, T_AN_PROVINCIA "
//                        + "WHERE T_AN_UFFICIO.CODI_K_ID_UFFICIO = :codUfficio "
//                        + "AND T_AN_UFFICIO.CODI_FK_ID_COM_SEDE = T_AN_COMUNI.CODI_K_ID_COMUNE "
//                        + "and T_AN_COMUNI.CODI_FK_ID_ISTAT_PROV = T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV " );
//                    
//                    query.addScalar("idComune", Hibernate.LONG);
//                    query.addScalar("desComune", Hibernate.STRING);
//                    query.addScalar("siglaProv", Hibernate.STRING);
                    Query query = session.getNamedQuery( "PG_CONSERVATORIE_getComuneByUfficio" );
                    query.setParameter("codUfficio", codUfficio, Hibernate.INTEGER);
                    List lista = query.list();
                    return lista.get( 0 );
                }
            });
        }


	    @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<SoggettoBeneDto> listaSoggettiBeneAnnot(final int idUffOwn,
				final Integer tipoAtto, final Integer catAtto, final Short repAnno,
				final Long repNumero, final long idBene, final long idProced,
				final String tenoreMisura) 	    
		{
	        return (List<SoggettoBeneDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Soggetti_Contro_A" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
	                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
	                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
	                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
	                q.setParameter( "idBene", idBene, Hibernate.LONG );
	                q.setParameter( "idProced", idProced, Hibernate.LONG );
	                q.setParameter( "tenoreMisura", tenoreMisura, Hibernate.STRING );
	                q.setResultTransformer( Transformers.aliasToBean( SoggettoBeneDto.class ) );
	                List lista = q.list();
//	                if( lista == null || lista.isEmpty() )
//	                    throw new HibernateException( "Soggetti non trovati" );
	                return lista;
	            }
	        } );
	    }

	    @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<SoggettoBeneDto> listaSoggettiBeneTrascrD(final int idUffOwn,
				final Integer tipoAtto, final Integer catAtto, final Short repAnno,
				final Long repNumero, final long idBene, final long idProced) 	    {
	        return (List<SoggettoBeneDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Soggetti_Contro_TD" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
	                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
	                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
	                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
	                q.setParameter( "idBene", idBene, Hibernate.LONG );
	                q.setParameter( "idProced", idProced, Hibernate.LONG );
	                q.setResultTransformer( Transformers.aliasToBean( SoggettoBeneDto.class ) );
	                List lista = q.list();
//	                if( lista == null || lista.isEmpty() )
//	                    throw new HibernateException( "Soggetti non trovati" );
	                return lista;
	            }
	        } );
	    }

	    @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<SoggettoBeneDto> listaSoggettiBeneTrascrS(final int idUffOwn,
				final Integer tipoAtto, final Integer catAtto, final Short repAnno,
				final Long repNumero, final long idBene, final long idProced) 
	    {
	        return (List<SoggettoBeneDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CONSERVATORIE_Ricerca_Soggetti_Contro_TS" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "tipoAtto", tipoAtto, Hibernate.INTEGER );
	                q.setParameter( "catAtto", catAtto, Hibernate.INTEGER );
	                q.setParameter( "repAnno", repAnno, Hibernate.SHORT );
	                q.setParameter( "repNumero", repNumero, Hibernate.LONG );
	                q.setParameter( "idBene", idBene, Hibernate.LONG );
	                q.setParameter( "idProced", idProced, Hibernate.LONG );
	                q.setResultTransformer( Transformers.aliasToBean( SoggettoBeneDto.class ) );
	                List lista = q.list();
//	                if( lista == null || lista.isEmpty() )
//	                    throw new HibernateException( "Soggetti non trovati" );
	                return lista;
	            }
	        } );
	    }
	    
        @SuppressWarnings( { "unchecked", "rawtypes" } )
        public List<NotaPersFisRegimeDto> listaPFinRegime(final Long idNota, final Integer idUffOwn, final Long progrSogg)
        {
            return (List<NotaPersFisRegimeDto>) getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                	Query q = session.getNamedQuery( "PG_CONSERVATORIE_lista_PF_Regime" );
 	                q.setParameter( "idNota", idNota, Hibernate.LONG );
 	                q.setParameter( "idUff", idUffOwn, Hibernate.INTEGER );
 	                q.setParameter( "progrSogg", progrSogg, Hibernate.LONG );
 	                q.setResultTransformer( Transformers.aliasToBean( NotaPersFisRegimeDto.class ) );
 	                return q.list();
                }
            });
        }
}
