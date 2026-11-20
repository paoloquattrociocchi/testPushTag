/**
 * 
 */
package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.model.TAtto;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

public class RiunioneDaoImpl extends HibernateDaoSupport implements RiunioneDao {

	@SuppressWarnings("unchecked")
	public KeyMessageDto controlloRiunione(final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo){

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_RIUNIONE_prc_cntr_main");
				if(idProcedimentoRiunente > 0){
					q.setParameter("IJ_ID_PROC_RIUNENTE", idProcedimentoRiunente, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_RIUNENTE", null, Hibernate.LONG);
				}
				if(idProcedimentoRiunito > 0){
					q.setParameter("IJ_ID_PROC_RIUNITO", idProcedimentoRiunito, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_RIUNITO", null, Hibernate.LONG);
				}
				if(codiceUtente != null){
					q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				}else{
					q.setParameter("IJ_UTENTE", null, Hibernate.STRING);
				}
				if(idUfficio > 0){
					q.setParameter("IJ_UFFICIO", idUfficio, Hibernate.INTEGER);
				}else{
					q.setParameter("IJ_UFFICIO", 0, Hibernate.INTEGER);
				}
				if(nomeMetodo != null){
					q.setParameter("IJ_NOME_METODO", nomeMetodo, Hibernate.STRING);
				}else{
					q.setParameter("IJ_NOME_METODO", null, Hibernate.STRING);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return new KeyMessageDto(list);
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	public KeyMessageDto esecuzioneRiunione(final String funzione,final long idAtto, final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo, final long magistrato, final Date dataRiunione, final TAtto atto) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_RIUNIONE_prc_insert_main");
				
				q.setParameter("IJ_FUNZIONE", funzione, Hibernate.STRING);
				
				if(idAtto > 0){
					q.setParameter("IJ_ID_ATTO", idAtto, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_ATTO", null, Hibernate.LONG);
				}
								
				if(idProcedimentoRiunente > 0){
					q.setParameter("IJ_ID_PROC_RIUNENTE", idProcedimentoRiunente, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_RIUNENTE", null, Hibernate.LONG);
				}
				if(idProcedimentoRiunito > 0){
					q.setParameter("IJ_ID_PROC_RIUNITO", idProcedimentoRiunito, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROC_RIUNITO", null, Hibernate.LONG);
				}
				if(codiceUtente != null ){
					q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);
				}else{
					q.setParameter("IJ_UTENTE", null, Hibernate.STRING);
				}
				if(idUfficio > 0){
					q.setParameter("IJ_UFFICIO", new Integer(idUfficio), Hibernate.INTEGER);
				}else{
					q.setParameter("IJ_UFFICIO", new Integer(0), Hibernate.INTEGER);
				}
				if(nomeMetodo != null){
					q.setParameter("IJ_NOME_METODO", nomeMetodo, Hibernate.STRING);
				}else{
					q.setParameter("IJ_NOME_METODO", null, Hibernate.STRING);
				}
				if(magistrato > 0){
					q.setParameter("IJ_MAGISTRATO", magistrato, Hibernate.LONG);
				}else{
					q.setParameter("IJ_MAGISTRATO", 0L, Hibernate.LONG);
				}
				if(dataRiunione != null){
					q.setParameter("IJ_DATARIUNIONE", dataRiunione, Hibernate.DATE);
				}else{
					q.setParameter("IJ_DATARIUNIONE", null, Hibernate.DATE);
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
	
	
}
	
