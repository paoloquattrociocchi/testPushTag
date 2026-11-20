package it.giustizia.smp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.utility.Constants;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AmministratoreDaoImpl extends HibernateDaoSupport implements AmministratoreDao {

	@Autowired
	private BeneService beneService;

	@Autowired
	private AttoService attoService;	
	
	@Override
	public void copiaAmministratori(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo) {

		// Estraggo tutti gli amministratori presenti per un determinato procedimento/ufficio
		List<BeneForm> listaBeniInAmministrazione = new ArrayList<BeneForm>();
		List<AmministratoreForm> listaAmministratori = beneService.getAmministratori(idUfficio, idProcedimento, null, null, true);
		
		// Inserisco le relazioni REL_PROC_BENE_ATTO_AMM
		if (listaAmministratori != null) {
			for (AmministratoreForm amministratoreForm : listaAmministratori) {
				// Inserisco la relazione REL_PROC_ATTO_AMM se la data di fine incarico non e' valorizzata
				if (amministratoreForm.getDataFineIncarico() == null)
					beneService.inserisciRelAmministratoreAttoTuttiParametri(idUfficio, amministratoreForm.getId(), idAtto, idProcedimento, amministratoreForm.getDataInizioIncarico(), codiceUtente, nomeMetodo);

				listaBeniInAmministrazione = beneService.listaBeniInAmministrazione(idUfficio, idProcedimento, null, amministratoreForm.getId());
				for (BeneForm beneForm : listaBeniInAmministrazione) {
					// Inserisco la relazione REL_PROC_BENE_ATTO_AMM se la data di fine incarico non e' valorizzata
					if (beneForm.getDataFineIncarico() == null)
						beneService.inserisciRelAmministratoreBeneAttoTuttiParametri(idUfficio, amministratoreForm.getId(), idAtto, idProcedimento, beneForm.getDataInizioIncarico(), beneForm.getIdBene(), codiceUtente, nomeMetodo);
				}
			}
		}		
		
		
	}

	@Override
	public Object aggiornaRecords() {

		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				session.flush();
				
				return null;
				
			}
		});

	}

	@Override
	public Object aggiornaRecords(final ArrayList<TRelProcAttoAmm> listaRecordDaCancellare) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				for(TRelProcAttoAmm record : listaRecordDaCancellare) {
					session.delete(record);
				}
				
				session.flush();
				
				return null;
				
			}
		});
	}

	@Override
	public void copiaAmministratoriIIGrado(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo) {

		List<BeneForm> listaBeniInAmministrazione = new ArrayList<BeneForm>();
		List<AttoForm> listaDecretiSequestri;
		
		// Estraggo tutti id Atto dei sequestri di II grado
		listaDecretiSequestri = attoService.getAttiByProcedimento(idProcedimento, Long.parseLong(Constants.TIPO_ATTO_DECRETO_II_GRADO), Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO);
		
		List<AmministratoreForm> listaAmministratori = new ArrayList<AmministratoreForm>();
		List<AmministratoreForm> listaAmministratoriTemp  = new ArrayList<AmministratoreForm>();
		
		// Estraggo tutti gli amministratori associati ad ogni decreto. 
		for (AttoForm attoform : listaDecretiSequestri) {
			listaAmministratoriTemp = beneService.getAmministratori(idUfficio, idProcedimento, attoform.getCodAtto(), null, false);
			if (listaAmministratoriTemp != null && listaAmministratoriTemp.size() > 0) {
				listaAmministratori.addAll(beneService.getAmministratori(idUfficio, idProcedimento, attoform.getCodAtto(), null, false));
			}
		}
							
		// Inserisco le relazioni REL_PROC_BENE_ATTO_AMM
		if (listaAmministratori != null) {
			for (AmministratoreForm amministratoreForm : listaAmministratori) {
				// Inserisco la relazione REL_PROC_ATTO_AMM se la data di fine incarico non e' valorizzata
				if (amministratoreForm.getDataFineIncarico() == null)
					beneService.inserisciRelAmministratoreAttoTuttiParametri(idUfficio, amministratoreForm.getId(), idAtto, idProcedimento, amministratoreForm.getDataInizioIncarico(), codiceUtente, nomeMetodo);

				listaBeniInAmministrazione.clear();
				for (AttoForm attoSeqestro : listaDecretiSequestri) {
					listaBeniInAmministrazione.addAll(beneService.listaBeniInAmministrazione(idUfficio, idProcedimento, attoSeqestro.getCodAtto(), amministratoreForm.getId()));					
				}
				
				for (BeneForm beneForm : listaBeniInAmministrazione) {
					// Inserisco la relazione REL_PROC_BENE_ATTO_AMM se la data di fine incarico non e' valorizzata
					if (beneForm.getDataFineIncarico() == null)
						beneService.inserisciRelAmministratoreBeneAttoTuttiParametri(idUfficio, amministratoreForm.getId(), idAtto, idProcedimento, beneForm.getDataInizioIncarico(), beneForm.getIdBene(), codiceUtente, nomeMetodo);
				}
			}
		}		
		
	}

	@SuppressWarnings("rawtypes")
	public Object updateBeneAmministrato(final ArrayList<Long> elencoFascicoli, final Long idBene, final Date dataFineIncarico ) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class );
	            criteria.add(Restrictions.in("id.codiKIdProc", elencoFascicoli ));
	            criteria.add(Restrictions.eq("id.codiKIdBene", idBene ));
	            
	            List<TRelProcBeneAttoAmm> pbamModel = (List<TRelProcBeneAttoAmm>)getHibernateTemplate().findByCriteria( criteria );
	            Iterator it = pbamModel.iterator();
	            
				while (it.hasNext()) {
					TRelProcBeneAttoAmm row = (TRelProcBeneAttoAmm) it.next();
					row.setDataFineIncarico(dataFineIncarico);
					
					session.update(row);
				}

				session.flush();
				return null;
			}
		});
    }

	/*
	@SuppressWarnings("rawtypes")
	public Object updateAmministratoriCopertina(final ArrayList<Long> elencoFascicoli, final Date dataFineIncarico ) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				// filtro per i fascicoli
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class );
	            criteria.add(Restrictions.in("id.codiKIdProc", elencoFascicoli ));
	            criteria.addOrder(Order.asc("id.codiKIdProc"));
	            criteria.addOrder(Order.asc("id.codiKIdAtto"));
	            criteria.addOrder(Order.asc("id.codiKIdAmmin"));
	            criteria.addOrder(Order.asc("id.codiKIdBene"));
	            
	            Criteria dist = session.createCriteria(TRelProcBeneAttoAmm.class);
	            dist.setProjection(Projections.distinct(Projections.property("id.codiKIdProc")));
	            dist.add(Restrictions.in("id.codiKIdProc", elencoFascicoli ));
	            dist.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	            
	            List<TRelProcBeneAttoAmm> pbamModel = dist.list();
	            
	            Iterator it = pbamModel.iterator();
	            
				while (it.hasNext()) {
					TRelProcBeneAttoAmm row = (TRelProcBeneAttoAmm) it.next();
					row.setDataFineIncarico(dataFineIncarico);
					
					session.update(row);
				}

				session.flush();
				return null;
			}
		});
    }
	*/
	
}
