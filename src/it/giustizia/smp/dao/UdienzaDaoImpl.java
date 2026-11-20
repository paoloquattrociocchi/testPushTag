package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_udienza.UdienzaProcedimentoDto;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAvvUdienza;
import it.giustizia.smp.model.TRelAvvUdienzaId;
import it.giustizia.smp.model.TRelSogintUdienza;
import it.giustizia.smp.model.TRelSogintUdienzaId;
import it.giustizia.smp.model.TRelUdienzaStatoUd;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TUdienzaId;
import it.giustizia.smp.model.TVeicolo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * AttoDaoImpl
 * 
 * @author Almaviva S.p.A.
 *         classe dao all'interno della quale viene fatto l'accesso al DB
 */
@SuppressWarnings("unchecked")
public class UdienzaDaoImpl extends HibernateDaoSupport implements UdienzaDao {

    public void aggiornaDatiUdienza(final long idUfficioUtente, final UdienzaProcedimentoDto dettaglioUdienza) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_aggiorna_dati_udienza");
                // chiave
                q.setParameter("idUdienza", dettaglioUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("idUffOwn", idUfficioUtente, Hibernate.LONG);  
                // parametri
                q.setParameter("dataUdienza", dettaglioUdienza.getDATA_UDIENZA(), Hibernate.DATE);
                q.setParameter("oraInizioUdienza", dettaglioUdienza.getORA_INIZIO_UDIENZA(), Hibernate.STRING);
                if (dettaglioUdienza.getORA_FINE_UDIENZA() != null) {
                    q.setParameter("oraFineUdienza", dettaglioUdienza.getORA_FINE_UDIENZA(), Hibernate.STRING);
                } else {
                    q.setParameter("oraFineUdienza", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_INDIR_AULA()!=null){
                	q.setParameter("indirizzoAula", dettaglioUdienza.getDESC_INDIR_AULA(), Hibernate.STRING);
                }else{
                	q.setParameter("indirizzoAula", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_AULA()!=null){
                	q.setParameter("aula", dettaglioUdienza.getDESC_AULA(), Hibernate.STRING);
                }else{
                	q.setParameter("aula", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_NOME_ASS_GIU()!=null){
                	q.setParameter("nomeCanc", dettaglioUdienza.getDESC_NOME_ASS_GIU(), Hibernate.STRING);
                }else{
                	q.setParameter("nomeCanc", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_COGNOME_ASS_GIU()!=null){
                	q.setParameter("cognomeCanc", dettaglioUdienza.getDESC_COGNOME_ASS_GIU(), Hibernate.STRING);
                }else{
                	q.setParameter("cognomeCanc", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_NOTE_UDIENZA()!=null){
                	q.setParameter("note", dettaglioUdienza.getDESC_NOTE_UDIENZA(), Hibernate.STRING);
                }else{
                	q.setParameter("note", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_ALTRICONV()!=null){
                	q.setParameter("altriConv", dettaglioUdienza.getDESC_ALTRICONV(), Hibernate.STRING);
                }else{
                	q.setParameter("altriConv", null, Hibernate.STRING);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    public void aggiornaVerbaleUdienza(final long idUfficioUtente, final UdienzaProcedimentoDto dettaglioUdienza, final long idAtto, final Blob blobDocumento, final String nomeDocumento, final String fileType) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

            	Query q = session.getNamedQuery("PG_UDIENZA_aggiorna_verbale_udienza");
                // chiave
                q.setParameter("idUdienza", dettaglioUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("idUffOwn", idUfficioUtente, Hibernate.LONG);  
                // parametri
                q.setParameter("dataUdienza", dettaglioUdienza.getDATA_UDIENZA(), Hibernate.DATE);
                q.setParameter("oraInizioUdienza", dettaglioUdienza.getORA_INIZIO_UDIENZA(), Hibernate.STRING);
                if (dettaglioUdienza.getORA_FINE_UDIENZA() != null) {
                    q.setParameter("oraFineUdienza", dettaglioUdienza.getORA_FINE_UDIENZA(), Hibernate.STRING);
                } else {
                    q.setParameter("oraFineUdienza", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_INDIR_AULA()!=null){
                	q.setParameter("indirizzoAula", dettaglioUdienza.getDESC_INDIR_AULA(), Hibernate.STRING);
                }else{
                	q.setParameter("indirizzoAula", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_AULA()!=null){
                	q.setParameter("aula", dettaglioUdienza.getDESC_AULA(), Hibernate.STRING);
                }else{
                	q.setParameter("aula", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_NOME_ASS_GIU()!=null){
                	q.setParameter("nomeCanc", dettaglioUdienza.getDESC_NOME_ASS_GIU(), Hibernate.STRING);
                }else{
                	q.setParameter("nomeCanc", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_COGNOME_ASS_GIU()!=null){
                	q.setParameter("cognomeCanc", dettaglioUdienza.getDESC_COGNOME_ASS_GIU(), Hibernate.STRING);
                }else{
                	q.setParameter("cognomeCanc", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_NOTE_UDIENZA()!=null){
                	q.setParameter("note", dettaglioUdienza.getDESC_NOTE_UDIENZA(), Hibernate.STRING);
                }else{
                	q.setParameter("note", null, Hibernate.STRING);
                }
                if(dettaglioUdienza.getDESC_ALTRICONV()!=null){
                	q.setParameter("altriConv", dettaglioUdienza.getDESC_ALTRICONV(), Hibernate.STRING);
                }else{
                	q.setParameter("altriConv", null, Hibernate.STRING);
                }
                q.setParameter("idAtto", idAtto, Hibernate.LONG);
                if (blobDocumento != null) {
                    q.setParameter("documento", blobDocumento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", fileType, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }

    public void aggiornaFlagDif(final long idUfficioUtente, final long udienzaSelected, final UdienzaProcedimentoDto dettDif) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                TRelAvvUdienza avvUdi = new TRelAvvUdienza();
                TRelAvvUdienzaId id = new TRelAvvUdienzaId();
                id.setCodiKIdUdienza(udienzaSelected);
                id.setCodiKIdAvv(dettDif.getCODI_K_ID_SOGG_DIF().longValue());
                id.setCodiKIdUffOwner(Integer.parseInt("" + idUfficioUtente));
                avvUdi.setId(id);
                if(dettDif.getFLG_PRES() != null && dettDif.getFLG_PRES().SIZE>0){
                	avvUdi.setFlgPres(dettDif.getFLG_PRES());
                }
                session.update(avvUdi);
                session.flush();
                return null;
            }
        });
    }

    public void aggiornaFlagSoggConv(final long idUfficioUtente, final long udienzaSelected, final UdienzaProcedimentoDto dettSoggConv) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                TRelSogintUdienza sogConv = new TRelSogintUdienza();
                TRelSogintUdienzaId id = new TRelSogintUdienzaId();
                id.setCodiKIdUdienza(udienzaSelected);
                id.setCodiKIdSoggetto(dettSoggConv.getCODI_K_ID_SOGG_DIF().longValue());
                id.setCodiKIdUffOwner(Integer.parseInt("" + idUfficioUtente));
                sogConv.setId(id);
                if(dettSoggConv.getFLG_PRES()!=null){
                	sogConv.setFlgPres(dettSoggConv.getFLG_PRES().toString());
                }
                session.update(sogConv);
                session.flush();
                return null;
            }
        });
    }

    public void aggiornaMagUdienza(final long idUfficioUtente, final UdienzaProcedimentoDto magUdienza) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_aggiorna_mag_udienza");
                // chiave
                q.setParameter("vidUdienza", magUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente);
                q.setParameter("vidMag", magUdienza.getCODI_K_ID_MAGISTRATO().longValue(), Hibernate.LONG);
                // valore da aggiornare
                q.setParameter("vruolo", magUdienza.getID_RUOLO_MAG().intValue(), Hibernate.INTEGER);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }

    public void confermaInvioUdienza(final long idProcedimento, final long idUfficioUtente, final String codUtente, final UdienzaProcedimentoDto dettaglioUdienza, final long idProcUdi) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ConfInvio_udienza");
                q.setParameter("vidProc", idProcedimento, Hibernate.LONG);
                q.setParameter("vidUdienza", dettaglioUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidEsito", dettaglioUdienza.getCODI_FK_ID_ESITO_UD().longValue(), Hibernate.LONG);
                if (dettaglioUdienza.getDATA_RINVIO() != null) {
                    q.setParameter("vdataRinvioUdienza", dettaglioUdienza.getDATA_RINVIO(), Hibernate.DATE);
                } else {
                    q.setParameter("vdataRinvioUdienza", null, Hibernate.DATE);
                }
                if (dettaglioUdienza.getORA_UDIENZA_RINVIO() != null) {
                    q.setParameter("voraRinvioUdienza", dettaglioUdienza.getORA_UDIENZA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("voraRinvioUdienza", null, Hibernate.STRING);
                }
                if (dettaglioUdienza.getAULA_RINVIO() != null) {
                    q.setParameter("vaulaRinvioUdienza", dettaglioUdienza.getAULA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("vaulaRinvioUdienza", null, Hibernate.STRING);
                }
                if (dettaglioUdienza.getINDIR_AULA_RINVIO() != null) {
                    q.setParameter("vindAulaRinvioUdienza", dettaglioUdienza.getINDIR_AULA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("vindAulaRinvioUdienza", null, Hibernate.STRING);
                }
                q.setParameter("vidUtente", codUtente);
                q.setParameter("vidUffDest", idProcUdi);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
   //nuova gestione verbale udienza 
    public void confermaInvioVerbaleUdienza(final long idProcedimento, final long idUfficioUtente, final String codUtente, final UdienzaProcedimentoDto dettaglioUdienza, final long idProcUdi, final long idAtto, final Blob blobDocumento, final String nomeDocumento, final String fileType) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ConfInvio_verbaleUdienza");
                q.setParameter("vidProc", idProcedimento, Hibernate.LONG);
                q.setParameter("vidUdienza", dettaglioUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidEsito", dettaglioUdienza.getCODI_FK_ID_ESITO_UD().longValue(), Hibernate.LONG);
                if (dettaglioUdienza.getDATA_RINVIO() != null) {
                    q.setParameter("vdataRinvioUdienza", dettaglioUdienza.getDATA_RINVIO(), Hibernate.DATE);
                } else {
                    q.setParameter("vdataRinvioUdienza", null, Hibernate.DATE);
                }
                if (dettaglioUdienza.getORA_UDIENZA_RINVIO() != null) {
                    q.setParameter("voraRinvioUdienza", dettaglioUdienza.getORA_UDIENZA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("voraRinvioUdienza", null, Hibernate.STRING);
                }
                if (dettaglioUdienza.getAULA_RINVIO() != null) {
                    q.setParameter("vaulaRinvioUdienza", dettaglioUdienza.getAULA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("vaulaRinvioUdienza", null, Hibernate.STRING);
                }
                if (dettaglioUdienza.getINDIR_AULA_RINVIO() != null) {
                    q.setParameter("vindAulaRinvioUdienza", dettaglioUdienza.getINDIR_AULA_RINVIO(), Hibernate.STRING);
                } else {
                    q.setParameter("vindAulaRinvioUdienza", null, Hibernate.STRING);
                }
                q.setParameter("vidUtente", codUtente);
                q.setParameter("vidUffDest", idProcUdi);
                q.setParameter("idCurrentAtto", idAtto, Hibernate.LONG);
                if (blobDocumento != null) {
                    q.setParameter("documento", blobDocumento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", fileType, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    public void eliminaPmMagUdienza(final long idUfficioUtente, final UdienzaProcedimentoDto magUdienza) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_elimina_mag_udienza");
                // chiave
                q.setParameter("vidUdienza", magUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente);
                q.setParameter("vidMag", magUdienza.getCODI_K_ID_MAGISTRATO().longValue(), Hibernate.LONG);
                q.setParameter("vruolo", magUdienza.getID_RUOLO_MAG().intValue(), Hibernate.INTEGER);
                // parametri
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }

    public List<UdienzaProcedimentoDto> getDettaglioUdienza(final TProcedimento id, final long idUfficioUtente, final long udienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_dettaglio_udienza");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidProc", id.getCodiKIdProc());
                q.setParameter("vcodUdienza", udienzaSelected);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }

    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> getUdienzeByIdProc(final TProcedimento id, final long idUfficioUtente) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ricerca_udienze");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidProc", id.getCodiKIdProc());
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> getUdienzeByIdProcEStatoUdi(final TProcedimento id, final long idUfficioUtente, final String idStatoUdienza) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ricerca_udienze_by_stato_udi");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidProc", id.getCodiKIdProc());
                q.setParameter("vidStatoUdi", idStatoUdienza, Hibernate.STRING);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> getUdienzaDecretoSeqConvByCodAtto(final long codAtto, final long idUfficioUtente){
        
        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ricerca_udienze_by_codAtto");
                q.setParameter("vcodAtto", codAtto, Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> getUdienzaByCodAttoEStatoUdi(final long codAtto, final long idUffOwn, final String idStatoUdienza) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_ricerca_udi_by_codAtto_statoUdi");
                q.setParameter("vcodAtto", codAtto, Hibernate.LONG);
                q.setParameter("vidStatoUdi", idStatoUdienza, Hibernate.STRING);
                q.setParameter("vidUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    public Map esisteAttoInLavorazioneConDatiUdienza(final long idProcedimento, final long idUfficioUtente) {

        return (Map)getHibernateTemplate().execute(new HibernateCallback() {

            public  Map<String, Long> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_atto_lavorazione_dati_udi");
                q.setParameter("vidProc", idProcedimento, Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long idAtto = (Long) first.get("IDATTO");
                Long idStatoAtto = (Long) first.get("IDSTATOATTO");
                Long idCatAtto = (Long) first.get("IDCATATTO");
                Map<String, Long> resultMap = new HashMap<String, Long>();
                resultMap.put("ID_ATTO", idAtto);
                resultMap.put("ID_STATO_ATTO", idStatoAtto);
                resultMap.put("ID_CAT_ATTO", idCatAtto);
                return resultMap;
            }
        });
    }

    public void insertMagistratoUdienza(final long idUfficioUtente, final String codUtente, final UdienzaProcedimentoDto dettaglioUdienza) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_inserisci_pm_udienza");
                // chiave
                q.setParameter("vidUdienza", dettaglioUdienza.getCODI_K_ID_UDIENZA().longValue(), Hibernate.LONG);
                q.setParameter("vidUffOwn", idUfficioUtente);
                q.setParameter("vidMag", dettaglioUdienza.getCODI_K_ID_MAGISTRATO().longValue(), Hibernate.LONG);
                q.setParameter("vruolo", dettaglioUdienza.getID_RUOLO_MAG().intValue(), Hibernate.INTEGER);
                q.setParameter("vidUtente", String.valueOf(codUtente));
                // parametri
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }

    public List<UdienzaProcedimentoDto> listaDifensoriUdienza(final long idUfficioUtente, final long udienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_difensori");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vcodUdienza", udienzaSelected);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }

    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> listaMagistratiCollegioUdienza(final TProcedimento id, final long idUfficioUtente, final long udienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_magistrati_collegio");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidProc", id.getCodiKIdProc());
                q.setParameter("vcodUdienza", udienzaSelected);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }

    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> listaMagistratiPmUdienza(final long idUfficioUtente, final long udienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_magistrati_pm");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vcodUdienza", udienzaSelected);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> elencoMagistratiUff(final long idUffOwn, final String cognomeMag, final String nomeMag, final String dataUdienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_lista_magistrati_uff");
                q.setParameter("vidUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("vcognomeMag", cognomeMag, Hibernate.STRING);
                q.setParameter("vnomeMag", nomeMag, Hibernate.STRING);
                q.setParameter("vdataUdienza", dataUdienzaSelected, Hibernate.STRING);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public List<UdienzaProcedimentoDto> elencoPmProcUdi(final String dataUdienzaSelected, final long idProcUdi) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_lista_magistrati_pm_uff");
                q.setParameter("vdataUdienza", dataUdienzaSelected, Hibernate.STRING);
                q.setParameter("vidProcUdi", idProcUdi, Hibernate.LONG);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }


    public List<UdienzaProcedimentoDto> listaSoggettiConvocatiUdienza(final TProcedimento id, final long idUfficioUtente, final long udienzaSelected) {

        return (List<UdienzaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_soggetti_convocati");
                q.setParameter("vidUffOwn", idUfficioUtente, Hibernate.LONG);
                q.setParameter("vidProc", id.getCodiKIdProc());
                q.setParameter("vcodUdienza", udienzaSelected);
                q.setResultTransformer(Transformers.aliasToBean(UdienzaProcedimentoDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
    public void copiaDatiProcPerUdienza(final long idUdienza, final long idUffOwn, final String codUtente, final long idProcedimento) {

       getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_copia_dati_proc_per_udienza");
                q.setParameter("idCurrentUdienza", idUdienza, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("idCodiceUtente", codUtente, Hibernate.STRING);
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                
                List l = q.list();
                return l;
            }
        });
    }
    
    public long getIdUdienzaPrecedente(long idUdienza, int idUffOwn) {

    	TUdienzaId idUdienzaNuova = new TUdienzaId();
    	idUdienzaNuova.setCodiKIdUdienza(idUdienza);
    	idUdienzaNuova.setCodiKIdUffOwner(idUffOwn);
    	
    	List lista = null;
    	
    	DetachedCriteria criteria = DetachedCriteria.forClass(TRelUdienzaStatoUd.class);
    	criteria.createAlias("TUdienzaByTUdienzaStatoudFkUdienza2", "ud2");
    	criteria.add(Restrictions.eq("ud2.id", idUdienzaNuova));
    	criteria.createAlias("TUdienzaByTUdienzaStatoudFkUdienza1", "ud1");
    	criteria.setProjection(Projections.distinct(Property.forName("ud1.id.codiKIdUdienza")));    	
    	lista = getHibernateTemplate().findByCriteria(criteria);
    	
    	long id = (Long) lista.get(0);
    	
    	return id;
    	
	}
    
	public Map inserisciVerbaleUdienza(final long idUfficio, final String descUtente, final long idProcedimento, final long idTipoAtto,
			final long idCategoriaAtto, final long idUdienza, final String nomeMetodo) {
        return (Map)getHibernateTemplate().execute(  new HibernateCallback() {

            public Map<String, Long> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_UDIENZA_insVerbaleUdienza");
                q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
                q.setParameter("descUtente", descUtente, Hibernate.STRING);
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
                q.setParameter("idCategoriaAtto", idCategoriaAtto, Hibernate.LONG);
                q.setParameter("idUdienza", idUdienza, Hibernate.LONG);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                String cate = "nn";
                HashMap first = (HashMap) l.get(0);
                Long idAtto = (Long) first.get("IDCURRENTATTO");
                Map<String, Long> resultMap = new HashMap<String, Long>();
                resultMap.put("ID_ATTO", idAtto);
                return resultMap;
            }
        });
	}
	
	
	@SuppressWarnings("rawtypes")
	public TUdienza getUdienzaRicorso(final long idAtto, final long idUfficio, final String descUtente) {
		
		return (TUdienza)  getHibernateTemplate().execute(new HibernateCallback() {
	
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	
				Query q = session.getNamedQuery("PG_UDIENZA_getUdienzaRicorso");
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUfficio", idUfficio);
				q.setParameter("descUtente", descUtente);
	
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					Map map = (Map)  l.get(0);
					return (TUdienza) map.get("udienza");
				}
				
				return null;
			}
		});
	}	
    
}
