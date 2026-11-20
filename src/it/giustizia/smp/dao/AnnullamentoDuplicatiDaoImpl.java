/**
 * 
 */
package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author s.panarella
 * 
 */

public class AnnullamentoDuplicatiDaoImpl extends HibernateDaoSupport implements AnnullamentoDuplicatiDao {

	@SuppressWarnings("unchecked")
	public KeyMessageDto annullamentoSoggettoDuplicato(final long idProcedimento,
			final long idSoggettoDuplicato, final long idSoggettoValido, final int idUfficio,
			final String utente, final String nomeMetodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_ANNUL_DUPLICATI_fnc_annulla_main");
				if(idProcedimento > 0){
					q.setParameter("IJ_ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_PROCEDIMENTO", null, Hibernate.LONG);
				}
				if(idSoggettoDuplicato > 0){
					q.setParameter("IJ_ID_SOGG1_ANN", idSoggettoDuplicato, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_SOGG1_ANN", null, Hibernate.LONG);
				}
				if(idSoggettoValido > 0 ){
					q.setParameter("IJ_ID_SOGG2", idSoggettoValido, Hibernate.LONG);
				}else{
					q.setParameter("IJ_ID_SOGG2", null, Hibernate.LONG);
				}
				if(idUfficio > 0){
					q.setParameter("IJ_UFF_OWNER", idUfficio, Hibernate.INTEGER);
				}else{
					q.setParameter("IJ_UFF_OWNER", null, Hibernate.INTEGER);
				}
				if(utente != null){
					q.setParameter("IJ_UTENTE", utente, Hibernate.STRING);
				}else{
					q.setParameter("IJ_UTENTE", null, Hibernate.STRING);
				}	
				if(nomeMetodo != null){
					q.setParameter("IJ_NOMEMETODO", nomeMetodo, Hibernate.STRING);
				}else{
					q.setParameter("IJ_NOMEMETODO", null, Hibernate.STRING);
				}				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return new KeyMessageDto(list);
			}
		});
	}
	

}
