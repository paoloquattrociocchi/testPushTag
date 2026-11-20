package it.giustizia.smp.dao.cameralini;

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
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.ProcedimentoDaoImpl;
import it.giustizia.smp.dto.pg_cameralini.AmmDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelProcIntestatari;

@Transactional( rollbackFor = Exception.class )
public class ProcedimentoDaoCamImpl extends ProcedimentoDaoImpl implements ProcedimentoDaoCam
{
	
	
	 @SuppressWarnings( { "unchecked", "rawtypes" } )
	    public ProcedimentoDto getProcedimentoByKeys( final int idUffOwn, final Long numProc, final Long annoProc, final Long tipoProc)
	    {
	        return (ProcedimentoDto)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CAMERALINI_GetProcedimentoByKeys" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "numProc", numProc, Hibernate.LONG );
	                q.setParameter( "annoProc", annoProc, Hibernate.LONG );
	                q.setParameter( "tipoProc", tipoProc, Hibernate.LONG );
	                q.setResultTransformer( Transformers.aliasToBean( ProcedimentoDto.class ) );
	                List l = q.list();
	                if( (l != null) && (l.size() > 0) ) {
	                return l.get( 0 );
	                }
	                return null;
	            }
	        } );
	    }
	 

	 @SuppressWarnings( { "unchecked", "rawtypes" } )
	public List<SogIntDettaglioProcedimentoDto> getSoggettiMisurePatrProcedimento(final int idUffOwn, final Long idProc)
	{
		 return (List<SogIntDettaglioProcedimentoDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CAMERALINI_GetMisurePatrProcedimento" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "idProc", idProc, Hibernate.LONG );
	               
	                
	                q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
					return q.list();
	            }
	        } );
	}
	 @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<SogIntDettaglioProcedimentoDto> getSoggettiMisurePersProcedimento(final int idUffOwn, final Long idProc,final String tipoMisura)
		{
			 return (List<SogIntDettaglioProcedimentoDto>)getHibernateTemplate().execute( new HibernateCallback()
		        {
		            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
		            {
		                Query q = session.getNamedQuery( "PG_CAMERALINI_GetMisurePersProcedimento" );
		                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
		                q.setParameter( "idProc", idProc, Hibernate.LONG );
		                q.setParameter( "tipoMisura", tipoMisura, Hibernate.STRING );
		                
		                q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
						return q.list();
		            }
		        } );
		} 
	 
	 @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<AmmDettaglioProcedimentoDto> getAmmMisurePatrPersProcedimento(final int idUffOwn, final Long idProc)
		{
			 return (List<AmmDettaglioProcedimentoDto>)getHibernateTemplate().execute( new HibernateCallback()
		        {
		            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
		            {
		                Query q = session.getNamedQuery( "PG_CAMERALINI_getAmmLiquidazione" );
		                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
		                q.setParameter( "idProc", idProc, Hibernate.LONG );
		                
		                q.setResultTransformer(Transformers.aliasToBean(AmmDettaglioProcedimentoDto.class));
						return q.list();
		            }
		        } );
		} 
	 
	 @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<IntestatarioDto> getAmministratoriOmonimi(final String cognome, final String nome, final String ragSoc)
		{
			 return (List<IntestatarioDto>)getHibernateTemplate().execute( new HibernateCallback()
		        {
		            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
		            {
		                Query sqlQuery = session.getNamedQuery( "PG_CAMERALINI_getAmmOmonimi" ); 
		               
 						sqlQuery.setParameter( "cognome", cognome, Hibernate.STRING );
 						sqlQuery.setParameter( "nome", nome, Hibernate.STRING );
 						sqlQuery.setParameter( "ragSoc", ragSoc, Hibernate.STRING );
 						sqlQuery.setResultTransformer(Transformers.aliasToBean(IntestatarioDto.class));
						return sqlQuery.list();
		            }
		        } );
		} 

	 @SuppressWarnings( { "unchecked", "rawtypes" } )
		public List<IntestatarioDto> getIntestatariOmonimi(final Character tipoSoggetto,final String cognome, final String nome, final Date dataNascita, 
														   final Long idStatoNasc, final Long idComNasc, final String comuneNascEst,
												           final String codiceFiscale, final String ragioneSoc, final String pIvaCompl, 
              											   final Long idStatoSede,final Long idComuneSede, final String comuneSedeEst)
		{
			 return (List<IntestatarioDto>)getHibernateTemplate().execute( new HibernateCallback()
		        {
		            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
		            {
		                Query sqlQuery = session.getNamedQuery( "PG_CAMERALINI_getIntestOmonimi" ); 
		               
 						sqlQuery.setParameter( "tipoSoggetto", tipoSoggetto, Hibernate.CHARACTER );
 						sqlQuery.setParameter( "cognome", cognome, Hibernate.STRING );
 						sqlQuery.setParameter( "nome", nome, Hibernate.STRING );
 						sqlQuery.setParameter( "dataNascita", dataNascita, Hibernate.DATE );
 						sqlQuery.setParameter( "idStatoNasc", idStatoNasc, Hibernate.LONG );
 						sqlQuery.setParameter( "idComNasc", idComNasc, Hibernate.LONG );
 						sqlQuery.setParameter( "comuneNascEst", comuneNascEst, Hibernate.STRING );
 						sqlQuery.setParameter( "codiceFiscale", codiceFiscale, Hibernate.STRING );
 						sqlQuery.setParameter( "ragioneSoc", ragioneSoc, Hibernate.STRING );
 						sqlQuery.setParameter( "pIvaCompl", pIvaCompl, Hibernate.STRING );
 						sqlQuery.setParameter( "idStatoSede", idStatoSede, Hibernate.LONG );
 						sqlQuery.setParameter( "idComuneSede", idComuneSede, Hibernate.LONG );
 						sqlQuery.setParameter( "comuneSedeEst", comuneSedeEst, Hibernate.STRING );
 						sqlQuery.setResultTransformer(Transformers.aliasToBean(IntestatarioDto.class));
						return sqlQuery.list();
		            }
		        } );
		} 
	 
	 @SuppressWarnings( { "rawtypes", "unchecked" } )
	 public Long inserisciTRelProcProc(final long idProc1, final long idProc2, final int idUffOwn, final String codUtente, final long idTipoAtto,
				final long idCatAtto, final String codiTipoRel, final Character flagPadre) {

			return (Long) getHibernateTemplate().execute(new HibernateCallback() {

				public Long doInHibernate(final Session session) throws HibernateException, SQLException {

					Query q = session.getNamedQuery("PG_CAMERALINI_insTRelProcProc");
					q.setParameter("idProc1", idProc1, Hibernate.LONG);
					q.setParameter("idProc2", idProc2, Hibernate.LONG);
					q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
					q.setParameter("descUtente", String.valueOf(codUtente), Hibernate.STRING);
					q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
					q.setParameter("idCatAtto", idCatAtto, Hibernate.LONG);
					q.setParameter("codiTipoRel", codiTipoRel, Hibernate.STRING);
					q.setParameter("flagPadre", flagPadre, Hibernate.CHARACTER);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long id = (Long) first.get("IDPROC");
					return id;
				}
			});
		}

	 @SuppressWarnings( { "rawtypes", "unchecked" } )
	public Long inserisciProcedimentoCameralini(final int idUffOwn,final  String codUtente,
			final TProcedimento proc,final  Date dataUltAggiornamento,final  int idTipoFascicolo,
			final Long idTipoAtto,final  Long idCatAtto,final  String cognomeRichiedente,
			final String nomeRichiedente,final  Date dataEmissione,final  Date dataDeposito,
			final Long idModalitaTrasmissione, final Long codicePU) {
		

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idProc = null;
				Query q = session.getNamedQuery("PG_CAMERALINI_inserisciProcedimento");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				// dataUltAggiornamento è null --> Viene impostata dal Trigger
				q.setParameter("dataAperFasc", dataUltAggiornamento, Hibernate.TIMESTAMP);
				if ((proc.getTTpStatoProc() != null) && (proc.getTTpStatoProc().getCodiKIdStatoProc() > 0)) {
					q.setParameter("idStatoProc", proc.getTTpStatoProc().getCodiKIdStatoProc(), Hibernate.INTEGER);
				} else {
					q.setParameter("idStatoProc", null, Hibernate.INTEGER);
				}
				if ((proc.getTTpTipoProc() != null) && (proc.getTTpTipoProc().getCodiKIdTipoProc() > 0)) {
					q.setParameter("idTipoProc", proc.getTTpTipoProc().getCodiKIdTipoProc(), Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoProc", null, Hibernate.INTEGER);
				}
				if(idTipoAtto != null){
					q.setParameter("idTipoAtto",idTipoAtto, Hibernate.LONG);
				}else {
					q.setParameter("idTipoAtto",null, Hibernate.LONG);
				}
				if(idCatAtto != null){
					q.setParameter("idCatAtto",idCatAtto, Hibernate.LONG);
				}else {
					q.setParameter("idCatAtto",null, Hibernate.LONG);
				}
				
				if ((proc.getTAnProcedente() != null) && (proc.getTAnProcedente().getCodiKIdProced() > 0)) {
					q.setParameter("idProcedente", proc.getTAnProcedente().getCodiKIdProced(), Hibernate.INTEGER);
				} else {
					q.setParameter("idProcedente", null, Hibernate.INTEGER);
				}
				
				q.setParameter("flgOrdinaria", proc.getFlgOrdinaria(), Hibernate.CHARACTER);
				q.setParameter("flgAntimafia", proc.getFlgAntimafia(), Hibernate.CHARACTER);
				q.setParameter("flgAntiterrorismo", proc.getFlgAntiterrorismo(), Hibernate.CHARACTER);
				// dataUltAggiornamento è null --> Viene impostata dal Trigger
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				
				if (idTipoFascicolo > 0) {
					q.setParameter("idTipoFascicolo", idTipoFascicolo, Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoFascicolo", null, Hibernate.INTEGER);
				}
				
				
				
				q.setParameter("cognomeRichiedente",cognomeRichiedente, Hibernate.STRING);
				q.setParameter("nomeRichiedente",nomeRichiedente, Hibernate.STRING);
				q.setParameter("dataEmissione",dataEmissione, Hibernate.TIMESTAMP);
				q.setParameter("dataDeposito",dataDeposito, Hibernate.TIMESTAMP);
				q.setParameter("idModalitaTrasmissione",idModalitaTrasmissione, Hibernate.LONG);
				q.setParameter("codicePU",codicePU, Hibernate.LONG);
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					idProc = (Long) row.get("IDPROC");
				}
				return idProc;
			}
		});
	}
	 
	 
	 
	 @SuppressWarnings( { "rawtypes", "unchecked" } )
		public Long inserisciProcedimentoCameraliniNew(final int idUffOwn,final  String codUtente,
				final TProcedimento proc,final  Date dataUltAggiornamento,final  int idTipoFascicolo,
				final String listaSoggetti,final int idTipoProc, final String tipoSoggetto, final Long idVersione) {
			

			return (Long) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					Long idProc = null;
					Query q = session.getNamedQuery("PG_CAMERALINI_registraCamereConsiglio");
					q.setParameter("ID_PROC_MERITO",proc.getCodiKIdProc());
					q.setParameter("ID_UFFICIO",idUffOwn);
					//13 patrimoniale
					q.setParameter("ID_TIPO_PROC",idTipoProc);
					q.setParameter("STR_LISTAIDSOGGETTI",listaSoggetti);
					
					if(tipoSoggetto!=null) {
						q.setParameter("TIPOSOGGETTO", tipoSoggetto, Hibernate.STRING);
					}  else {
						q.setParameter("TIPOSOGGETTO", null, Hibernate.STRING);
					}
					
					if(idVersione!=null) {
						q.setParameter("IDVERSIONE", idVersione, Hibernate.LONG);
					} else {
						q.setParameter("IDVERSIONE", null, Hibernate.LONG);
					}
					
					q.setParameter("DESC_UTENTE",codUtente);
					
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List list = q.list();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) it.next();
						idProc = (Long) row.get("IDPROC");
					}
					return idProc;
				}
			});
		}	 
	 


	 @SuppressWarnings( { "rawtypes", "unchecked" } )
	    public Long inserisciIntestatarioPersFis(final  TPersonaFisica persona , final String utente)
	    {
	        return (Long)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	            		
	                BigDecimal idSogg = null;
	                Query q = session.getNamedQuery( "PG_CAMERALINI_inserisciIntestario" );
	                
	                q.setParameter( "codSoggetto", persona.getId().getCodiKIdPersonaFis(), Hibernate.LONG );
	                q.setParameter( "idTipoSogg", 'F', Hibernate.CHARACTER );
	                q.setParameter( "nome", persona.getDescNome(), Hibernate.STRING );
	                q.setParameter( "cognome", persona.getDescCognome(), Hibernate.STRING );
	                q.setParameter( "sesso", persona.getFlgSesso(), Hibernate.CHARACTER );
	                q.setParameter( "dataNascita", persona.getDataNascita(), Hibernate.DATE );
	                q.setParameter( "comEsteroNasc", persona.getDescComEsteroNasc(), Hibernate.STRING );
	                q.setParameter( "codiceFiscale", persona.getDescCodiceFiscale(), Hibernate.STRING );
	                q.setParameter( "ragioneSociale", null, Hibernate.STRING );
	                q.setParameter( "comEsteroSede", null, Hibernate.STRING );
	                q.setParameter( "indirizzoSede", null, Hibernate.STRING );
	                q.setParameter( "descPartitaIva", null, Hibernate.STRING );
	                q.setParameter( "descUtente",utente, Hibernate.STRING );
	                q.setParameter( "idUffOwn", persona.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER );
	                q.setParameter( "descNumeCcia", null, Hibernate.STRING );
	                q.setParameter( "descCodFiscale", null, Hibernate.STRING );
	                q.setParameter( "descCivico", null, Hibernate.STRING );
	                q.setParameter( "descCap", null, Hibernate.STRING );
	                q.setParameter( "idTipoStrada", null, Hibernate.INTEGER );
	                q.setParameter( "idComSede", null, Hibernate.INTEGER );
	                q.setParameter( "idComNascita", persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER );
	                q.setParameter( "idStatoSede", null, Hibernate.INTEGER );
	                q.setParameter( "idStatoNasc", persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER );
	                q.setParameter( "idTipoSoc", null, Hibernate.INTEGER );
	                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
	                List list = q.list();
	                Iterator it = list.iterator();
	                while( it.hasNext() )
	                {
	                    Map<String, Object> row = (Map<String, Object>)it.next();
	                    idSogg = (BigDecimal)row.get( "CODINTESTATARIO" );
	                }
	                Long ret = null;
	                if(idSogg != null) ret = new Long(idSogg.longValue());
	                return ret;
	            }
	        } );
	    }
	 
	 @SuppressWarnings( { "rawtypes", "unchecked" } )
	    public Long inserisciIntestatarioPersGiur(final  TPersonaGiur persona , final String utente)
	    {
	        return (Long)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	            		
	            	BigDecimal idSogg = null;
	                Query q = session.getNamedQuery( "PG_CAMERALINI_inserisciIntestario" );
	                
	                q.setParameter( "codSoggetto", persona.getId().getCodiKIdPersGiur(), Hibernate.LONG );
	                q.setParameter( "idTipoSogg", 'G', Hibernate.CHARACTER );
	                q.setParameter( "nome", null, Hibernate.STRING );
	                q.setParameter( "cognome", null, Hibernate.STRING );
	                q.setParameter( "sesso", null, Hibernate.CHARACTER );
	                q.setParameter( "dataNascita", null, Hibernate.DATE );
	                q.setParameter( "comEsteroNasc", null, Hibernate.STRING );
	                q.setParameter( "codiceFiscale", null, Hibernate.STRING );
	                q.setParameter( "ragioneSociale", persona.getDescRagioneSoc(), Hibernate.STRING );
	                q.setParameter( "comEsteroSede", persona.getDescComEsteroSede(), Hibernate.STRING );
	                q.setParameter( "indirizzoSede", persona.getDescIndirizzoSede(), Hibernate.STRING );
	                q.setParameter( "descPartitaIva", persona.getDescPartitaIva(), Hibernate.STRING );
	                q.setParameter( "descUtente",utente, Hibernate.STRING );
	                q.setParameter( "idUffOwn", persona.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER );
	                q.setParameter( "descNumeCcia", persona.getDescNumeCciaa(), Hibernate.STRING );
	                q.setParameter( "descCodFiscale", persona.getDescCodFisc(), Hibernate.STRING );
	                q.setParameter( "descCivico", null, Hibernate.STRING );
	                q.setParameter( "descCap", persona.getDescCap(), Hibernate.STRING );
	                q.setParameter( "idTipoStrada", persona.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER );
	                q.setParameter( "idComSede", persona.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER );
	                q.setParameter( "idComNascita", null, Hibernate.INTEGER );
	                q.setParameter( "idStatoSede", persona.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER );
	                q.setParameter( "idStatoNasc", null, Hibernate.INTEGER );
	                q.setParameter( "idTipoSoc", persona.getTTpTipoSocieta().getCodiKIdTipoSoc(), Hibernate.INTEGER );
	                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
	                List list = q.list();
	                Iterator it = list.iterator();
	                while( it.hasNext() )
	                {
	                    Map<String, Object> row = (Map<String, Object>)it.next();
	                    idSogg = (BigDecimal)row.get( "CODINTESTATARIO" );
	                }
	                Long ret = null;
	                if(idSogg != null) ret = new Long(idSogg.longValue());
	                return ret;
	            }
	        } );
	    }

	 @SuppressWarnings( { "rawtypes", "unchecked" } )
		public Long inserisciProcedimentoCameraliniInt(final long idProcedimento,final int idUffOwn,final int idTipoProc, 
				final BigDecimal codIntestatario, final String codTipoSoggetto, final String nome, final String cognome,
				final String sesso, final Date dataNascita, final String comuneEsteroNasc,final String codiceFiscalePF,final String codiceFiscalePG,
				final String ragioneSociale, final String comuneEsteroSede, final String indirizzoSede, final String descPartitaIva,final int idUffIntest,
				final  String descNumeCcia, final  String descCivico,final  String descCap, final Long codTipoStrada, final Long codComSede,
				final Long codComNasc,final Long codStatoSede,final Long codStatoNasc,final Long codTipoSoc,
				final Long codSoggInt, final Long idVer, final String descUtente) {

			return (Long) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					Long idProc = null;
					Query q = session.getNamedQuery("PG_CAMERALINI_registraCamereConsiglioInt");
					q.setParameter( "ID_PROC_MERITO", idProcedimento, Hibernate.LONG );
					q.setParameter( "ID_UFFICIO", idUffOwn, Hibernate.INTEGER);
					q.setParameter( "ID_TIPO_PROC", idTipoProc, Hibernate.INTEGER );
					q.setParameter( "CODINTESTATARIO", codIntestatario, Hibernate.BIG_DECIMAL );
					q.setParameter( "CODTIPOSOGGETTO", codTipoSoggetto, Hibernate.STRING );
					q.setParameter( "NOME", nome, Hibernate.STRING );
	                q.setParameter( "COGNOME", cognome, Hibernate.STRING );
	                q.setParameter( "SESSO", sesso, Hibernate.STRING);
	                q.setParameter( "DATANASCITA", dataNascita, Hibernate.DATE );
	                q.setParameter( "COMUNEESTERONASC",comuneEsteroNasc, Hibernate.STRING );
	                q.setParameter( "CODICEFISCALE", codiceFiscalePF, Hibernate.STRING );
	                q.setParameter( "DESC_COD_FISC", codiceFiscalePG, Hibernate.STRING );
	                q.setParameter( "RAGIONESOCIALE", ragioneSociale, Hibernate.STRING );
	                q.setParameter( "COMUNEESTEROSEDE", comuneEsteroSede, Hibernate.STRING );
	                q.setParameter( "INDIRIZZOSEDE", indirizzoSede, Hibernate.STRING );
	                q.setParameter( "DESCPARTITAIVA", descPartitaIva, Hibernate.STRING );
	                q.setParameter( "CODUFFICIOOWN", idUffIntest, Hibernate.INTEGER);
	                q.setParameter( "DESCNUMECCIA", descNumeCcia, Hibernate.STRING );
	                q.setParameter( "DESC_CIVICO", descCivico, Hibernate.STRING );
	                q.setParameter( "DESC_CAP", descCap, Hibernate.STRING );
	                q.setParameter( "CODI_FK_ID_TIPO_STRADA", codTipoStrada, Hibernate.LONG);
	                q.setParameter( "CODI_FK_ID_COM_SEDE", codComSede, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_COM_NASC", codComNasc, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_STATO_SEDE", codStatoSede, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_STATO_NASC", codStatoNasc, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_TIPO_SOC", codTipoSoc, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_SOGG_INT", codSoggInt, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_VER", idVer, Hibernate.LONG );
	                q.setParameter( "DESC_UTENTE", descUtente, Hibernate.STRING );
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List list = q.list();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) it.next();
						idProc = (Long) row.get("IDPROC");
					}
					return idProc;
				}
			});
		}	 

		
  
	 @SuppressWarnings( { "rawtypes", "unchecked" } )
		public BigDecimal updateIntestatario(final long idProcedimento,final int idUffOwn,final int idTipoProc, 
				final BigDecimal codIntestatario, final String codTipoSoggetto, final String nome, final String cognome,
				final String sesso, final Date dataNascita, final String comuneEsteroNasc,final String codiceFiscalePF,final String codiceFiscalePG,
				final String ragioneSociale, final String comuneEsteroSede, final String indirizzoSede, final String descPartitaIva,final int idUffIntest,
				final  String descNumeCcia, final  String descCivico,final  String descCap, final Long codTipoStrada, final Long codComSede,
				final Long codComNasc,final Long codStatoSede,final Long codStatoNasc,final Long codTipoSoc,
				final Long codSoggInt, final Long idVer, final String descUtente) {

			return (BigDecimal) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					BigDecimal idProc = null;
					Query q = session.getNamedQuery("PG_C_PROCEDIMENTO_updateIntestatario");
					q.setParameter( "CODINTESTATARIO", codIntestatario, Hibernate.BIG_DECIMAL );
					q.setParameter( "NOME", nome, Hibernate.STRING );
	                q.setParameter( "COGNOME", cognome, Hibernate.STRING );
	                q.setParameter( "SESSO", sesso, Hibernate.STRING);
	                q.setParameter( "DATANASCITA", dataNascita, Hibernate.DATE );
	                q.setParameter( "COMUNEESTERONASC",comuneEsteroNasc, Hibernate.STRING );
	                q.setParameter( "CODICEFISCALE", codiceFiscalePF, Hibernate.STRING );
	                q.setParameter( "RAGIONESOCIALE", ragioneSociale, Hibernate.STRING );
	                q.setParameter( "COMUNEESTEROSEDE", comuneEsteroSede, Hibernate.STRING );
	                q.setParameter( "INDIRIZZOSEDE", indirizzoSede, Hibernate.STRING );
	                q.setParameter( "DESCPARTITAIVA", descPartitaIva, Hibernate.STRING );
	                q.setParameter( "DESC_UTENTE", descUtente, Hibernate.STRING );
	                q.setParameter( "CODUFFICIOOWN", idUffIntest, Hibernate.INTEGER);
	                q.setParameter( "DESCNUMECCIA", descNumeCcia, Hibernate.STRING );
	                q.setParameter( "DESC_COD_FISC", codiceFiscalePG, Hibernate.STRING );
	                q.setParameter( "DESC_CIVICO", descCivico, Hibernate.STRING );
	                q.setParameter( "DESC_CAP", descCap, Hibernate.STRING );
	                q.setParameter( "CODI_FK_ID_TIPO_STRADA", codTipoStrada, Hibernate.LONG);
	                q.setParameter( "CODI_FK_ID_COM_SEDE", codComSede, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_COM_NASC", codComNasc, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_STATO_SEDE", codStatoSede, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_STATO_NASC", codStatoNasc, Hibernate.LONG );
	                q.setParameter( "CODI_FK_ID_TIPO_SOC", codTipoSoc, Hibernate.LONG );
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List list = q.list();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) it.next();
						idProc = (BigDecimal) row.get("IDPROC");
					}
					return idProc;
				}
			});
		}	
		
		
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IntestatarioDto> getIntestatari(final int idUffOwn, final Long idProc) {
		return (List<IntestatarioDto>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session) throws HibernateException, SQLException {
						Query sqlQuery = session.getNamedQuery("PG_CAMERALINI_getIntestatari");
						sqlQuery.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
						sqlQuery.setParameter("idProc", idProc, Hibernate.LONG);
						sqlQuery.setResultTransformer(Transformers.aliasToBean(IntestatarioDto.class));
						return sqlQuery.list();
					}
				});
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getDettaglioProcedimentoCam(final TProcedimento id) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_CAMERALINI_dettaglioProcedimentoCam");
				q.setParameter("vidProc", id.getCodiKIdProc());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public List<Long> getIdComuneNascita( final String descComune )
    {
        return (List<Long>)getHibernateTemplate().execute( new HibernateCallback()
        {
            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
            {
                Query q = session.getNamedQuery( "PG_CAMERALINI_getIdComuneNascita" );
                q.setParameter( "descComune", descComune, Hibernate.STRING );
                q.setResultTransformer( Transformers.ALIAS_TO_ENTITY_MAP );
                List lista = q.list();
                List<Long> listaProgr = new ArrayList<Long>();
                Iterator it = lista.iterator();
                while( it.hasNext() )
                {
                    Map<String, Object> row = (Map<String, Object>)it.next();
                    Long p = ((BigDecimal)row.get( "CODI_K_ID_COMUNE" )).longValue();
                    listaProgr.add( p );
                }
                return listaProgr;
            }
        } );
    }


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IntestatarioDto getIntestatarioById(final Long idIntest, final Integer idUfficio) {
		return (IntestatarioDto) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session) throws HibernateException, SQLException {
						Query sqlQuery = session.getNamedQuery("PG_CAMERALINI_getIntestatarioById");
						sqlQuery.setParameter("idUfficio", idUfficio, Hibernate.INTEGER);
						sqlQuery.setParameter("idIntest", idIntest, Hibernate.LONG);
						sqlQuery.setResultTransformer(Transformers.aliasToBean(IntestatarioDto.class));
		                List l = sqlQuery.list();
		                if( (l != null) && (l.size() > 0) ) {
		                return l.get( 0 );
		                }
		                return null;
					}
				});
	}

	@SuppressWarnings("unchecked")
	public TRelProcIntestatari getFirstTRelProcIntestatari(final int idUffOwn,final  Long idProcedimento) {
		return getHibernateTemplate().execute(new HibernateCallback<TRelProcIntestatari>() {

			public TRelProcIntestatari doInHibernate(final Session session) throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(TRelProcIntestatari.class);
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
				criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
				
				List<TRelProcIntestatari> relazioni = criteria.list();
				if (relazioni.size() > 0) {
					relazioni.get(0).getTSoggetto().getId();
					return relazioni.get(0);
				}
				return null;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getEsitoDecisione(final long idProcedimento,  final Long idAtto,final int idUfficioUtente, final String codiceUtente) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery("PG_CAMERALINI_getEsitoDecisione");
				query.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				query.setParameter("ID_ATTODECRETO", idAtto, Hibernate.LONG);
				query.setParameter("ID_UFFICIO", idUfficioUtente);
				query.setParameter("DESCUTENTE", codiceUtente);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = query.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

	//Ginesti luglio 2017
	 @SuppressWarnings( { "unchecked", "rawtypes" } )
	    public ProcedimentoDto getProcedimentoByKeysProc( final int idUffOwn, final Long numProc, final Long annoProc, final Long tipoProc)
	    {
	        return (ProcedimentoDto)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_CAMERALINI_GetProcedimentoByKeysProc" );
	                q.setParameter( "idUffOwn", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "numProc", numProc, Hibernate.LONG );
	                q.setParameter( "annoProc", annoProc, Hibernate.LONG );
	                q.setParameter( "tipoProc", tipoProc, Hibernate.LONG );
	                q.setResultTransformer( Transformers.aliasToBean( ProcedimentoDto.class ) );
	                List l = q.list();
	                if( (l != null) && (l.size() > 0) ) {
	                return l.get( 0 );
	                }
	                return null;
	            }
	        } );
	    }

}		
