/**
 * 
 */
package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.utility.Constants;

import java.util.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author massimo.demattia
 *
 */

public class StralcioDaoImpl extends HibernateDaoSupport implements StralcioDao {

	@SuppressWarnings("unchecked")
	public List<RicercaStralcioDto> ricercaStralcio(final long idProcedimento, final long idUffOwn, final String idsMisure, final String idsSoggInt, final String idsSoggTit, final Integer idTipoBene, final Integer idCatBene, final Integer idSottoCatBene, final String flagValido) {

		return (List<RicercaStralcioDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_STRALCIO_ricerca_stralcio");
				if(idProcedimento > 0){
					q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if(idUffOwn > 0){
					q.setParameter("ID_UFF_OWNER", idUffOwn, Hibernate.LONG);
				}else{
					q.setParameter("ID_UFF_OWNER", 0, Hibernate.LONG);
				}
				if(idsMisure != null){
					q.setParameter("IDS_MIS", idsMisure, Hibernate.STRING);
				}else{
					q.setParameter("IDS_MIS", null, Hibernate.STRING);
				}
				
				if(idsSoggInt != null){
					q.setParameter("IDS_SOGG_INT", idsSoggInt, Hibernate.STRING);
				}else{
					q.setParameter("IDS_SOGG_INT", null, Hibernate.STRING);
				}
				
				if(idsSoggTit != null){
					q.setParameter("IDS_SOGG_TIT", idsSoggTit, Hibernate.STRING);
				}else{
					q.setParameter("IDS_SOGG_TIT", null, Hibernate.STRING);
				}
				
				if(idTipoBene != null){
					q.setParameter("ID_TIPO_BENE", idTipoBene, Hibernate.INTEGER);
				}else{
					q.setParameter("ID_TIPO_BENE", null, Hibernate.INTEGER);
				}
				
				if(idCatBene != null){
					q.setParameter("ID_CAT_BENE", idCatBene, Hibernate.INTEGER);
				}else{
					q.setParameter("ID_CAT_BENE", null, Hibernate.INTEGER);
				}
				if(idSottoCatBene != null){
					q.setParameter("ID_SCTG_BENE", idSottoCatBene, Hibernate.INTEGER);
				}else{
					q.setParameter("ID_SCTG_BENE", null, Hibernate.INTEGER);
				}
				
				if(flagValido != null){
					q.setParameter("FLG_VALIDO", flagValido, Hibernate.STRING);
				}else{
					q.setParameter("FLG_VALIDO", null, Hibernate.STRING);
				}
				
				
				
				/*
				String query = "";
				
				Query q = session.createQuery(query);
				
				q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("ID_UFF_OWNER", idUffOwn, Hibernate.LONG);
				 */
				
				
				q.setResultTransformer(Transformers.aliasToBean(RicercaStralcioDto.class));
				List l = q.list();
				return l;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Character impostaStralcioMisPatr(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, final long idBene, final String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente){
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_STRALCIO_imposta_stralcio_mis_patr");
				
				if(idProcedimento > 0){
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				
				if(idUffOwner > 0){
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				}else{
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}
				
				if(idBene > 0){
					q.setParameter("VID_BENE", idBene, Hibernate.LONG);
				}else{
					q.setParameter("VID_BENE", null, Hibernate.LONG);
				}
				
				if(idSoggInt > 0){
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				}else{
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}
				
								
				if(idSoggTit != null && !"".equals(idSoggTit)){
					q.setParameter("VID_SOGG_TIT", idSoggTit, Hibernate.STRING);
				}else{
					q.setParameter("VID_SOGG_TIT", null, Hibernate.STRING);
				}
				
				
				if(tipoBene > 0){
					q.setParameter("VID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_TIPO_BENE", null, Hibernate.INTEGER);
				}
				
				if(catBene > 0){
					q.setParameter("VID_CAT_BENE", catBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_CAT_BENE", null, Hibernate.INTEGER);
				}
				
				if(sottoCatBene > 0){
					q.setParameter("VID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_SCTG_BENE", null, Hibernate.INTEGER);
				}
				
				if(checkedSoggValido){
					q.setParameter("VFLG_VALIDO", 'O');
				}else{
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;
				
			}
		});
	}	
	
	@SuppressWarnings("unchecked")
	public Character impostaStralcioMisPers(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, final String codiceUtente){
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_STRALCIO_imposta_stralcio_mis_pers");
				
				if(idProcedimento > 0){
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				
				if(idUffOwner > 0){
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				}else{
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}
				
				if(idSoggInt > 0){
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				}else{
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}
				
				if(checkedSoggValido){
					q.setParameter("VFLG_VALIDO", 'O');
				}else{
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Character impostaStralcioSoggetto(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, 
			final String idsSoggTit, final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente){
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_STRALCIO_imposta_stralcio_soggetto");
				
				if(idProcedimento > 0){
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				
				if(idUffOwner > 0){
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				}else{
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}
				
				if(idSoggInt > 0){
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				}else{
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}
				
				///
				q.setParameter("VID_BENE", null, Hibernate.STRING);
				
				if(idsSoggTit != null && !"".equals(idsSoggTit) && !idsSoggTit.equalsIgnoreCase(Constants.UNDEFINED)){
					q.setParameter("VID_SOGG_TIT", idsSoggTit, Hibernate.STRING);
				}else{
					q.setParameter("VID_SOGG_TIT", null, Hibernate.STRING);
				}
				
				if(idsMisure != null && !"".equals(idsMisure)){
					q.setParameter("VIDS_MIS", idsMisure, Hibernate.STRING);
				}else{
					q.setParameter("VIDS_MIS", null, Hibernate.STRING);
				}
				
				if(tipoBene > 0){
					q.setParameter("VID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_TIPO_BENE", null, Hibernate.INTEGER);
				}
				
				if(catBene > 0){
					q.setParameter("VID_CAT_BENE", catBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_CAT_BENE", null, Hibernate.INTEGER);
				}
				
				if(sottoCatBene > 0){
					q.setParameter("VID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				}else{
					q.setParameter("VID_SCTG_BENE", null, Hibernate.INTEGER);
				}
				///
				if(checkedSoggValido){
					q.setParameter("VFLG_VALIDO", 'O');
				}else{
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				Character upd = null;
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;
				
			}
		});
	}

	@SuppressWarnings("unchecked")
	public KeyMessageDto controlloStralcio(final long idProcedimento) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_STRALCIO_fnc_cntr_main");
				if(idProcedimento > 0){
					q.setParameter("IJ_ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				
				return new KeyMessageDto(list);
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	public KeyMessageDto esecuzioneStralcio(final String funzione,final long idAtto, final long idProcedimento, final String descUtente, final Date dataStralcio, final long magistrato, final TAtto atto) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_STRALCIO_fnc_insert_main");
				
				q.setParameter("IJ_FUNZIONE", funzione, Hibernate.STRING);
				
				if(idAtto > 0){
					q.setParameter("IJ_ID_ATTO", idAtto, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_ATTO", null, Hibernate.LONG);
				}
				
				if(idProcedimento > 0){
					q.setParameter("IJ_ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				
				if(descUtente != null){
					q.setParameter("IJ_UTENTE", descUtente, Hibernate.STRING);
				}else{
					q.setParameter("IJ_UTENTE", null, Hibernate.STRING);
				}
				
				if(dataStralcio != null){
					q.setParameter("IJ_DATASTRALCIO", dataStralcio, Hibernate.DATE);
				}else{
					q.setParameter("IJ_DATASTRALCIO", null, Hibernate.DATE);
				}
				
				if(magistrato > 0){
					q.setParameter("IJ_MAGISTRATO", magistrato, Hibernate.LONG);
				}else{
					q.setParameter("IJ_MAGISTRATO", 0l, Hibernate.LONG);
				}
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("DOCUMENTO", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("TIPO_DOCUMENTO", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("NOME_DOCUMENTO", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("DOCUMENTO", null, Hibernate.BLOB);
					q.setParameter("TIPO_DOCUMENTO", null, Hibernate.STRING);
					q.setParameter("NOME_DOCUMENTO", null, Hibernate.STRING);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return new KeyMessageDto(list);
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	public KeyMessageDto copiaSpostaAttoGenerico(final long idProcedimento, final long idProcedimentoDestinatario, final long idAtto, final String operazione, final String codiceUtente, final long idUffOwn, final String nomeMetodo){
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//				TODO decommentare il codice seguente e definire la sql query nel hbm
				
				Query q = session.getNamedQuery("PG_STRALCIO_copiaSpostaAttoGenerico");
				
				if(idProcedimento > 0){
					q.setParameter("IJ_ID_PROC_ORIGINE", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_ORIGINE", 0, Hibernate.LONG);
				}
				
				if(idProcedimentoDestinatario > 0){
					q.setParameter("IJ_ID_PROC_STRALCIO", idProcedimentoDestinatario, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_STRALCIO", 0, Hibernate.LONG);
				}
				
				if(idAtto> 0){
					q.setParameter("IJ_ID_ATTO", idAtto, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_ATTO", null, Hibernate.LONG);
				}
				
				q.setParameter("IJ_FLG_TRATTAMENTO", operazione);
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				q.setParameter("IJ_UFFICIO", idUffOwn, Hibernate.LONG);
				q.setParameter("IJ_NOME_METODO", nomeMetodo, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return new KeyMessageDto(list);
				
//				return new KeyMessageDto();
				
			}
		});
	}
	
	
}
	
