package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * ReportDaoImpl
 * 
 * @author Almaviva S.p.A.
 *         classe dao all'interno della quale viene fatto l'accesso al DB
 */
@SuppressWarnings("unchecked")
public class ReportDaoImpl extends HibernateDaoSupport implements ReportDao {

   
    /**
     * listaMagistratiPerAtto
     * 
     * @param idUffOwn
     * @param idAtto
     * @return List<ReportMispatrBeneSogDto>
     */
    @SuppressWarnings("rawtypes")
    public List<ReportMispatrBeneSogDto> getMisPatrBeneSog(final long idAtto, final int idUffOwn, final long idProc) {

        return (List<ReportMispatrBeneSogDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_REPORT_ricerca_mispatr_bene_sog");
                q.setParameter("idAtto", idAtto);
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("idProc", idProc);
                q.setResultTransformer(Transformers.aliasToBean(ReportMispatrBeneSogDto.class));
                lista = q.list();
                return lista;
            }
        });
    }

   
}
