package it.giustizia.smp.service;

import it.giustizia.smp.dao.UdienzaDao;
import it.giustizia.smp.dto.pg_udienza.UdienzaProcedimentoDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelBeneProcDisp;
import it.giustizia.smp.model.TRelImmProcVinc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * UdienzaServiceImpl
 * 
 * @author Almaviva S.p.A.
 *         classe dell'Udienza
 */
@Transactional(rollbackFor = Exception.class)
public class UdienzaServiceImpl implements UdienzaService {

    @Autowired
    private Mapper mapperBean;

    private UdienzaDao udienzaDao;

    public void aggiornaDatiConfUdienza(long idProcedimento, long idUfficioUtente, String idUtente, UdienzaForm udienzaForm, String tipologia, long idProcUdi, long idAtto, long tipoUfficio) {

        UdienzaProcedimentoDto dettaglioUdienza = new UdienzaProcedimentoDto();
        if (tipologia.equals("ConfInvia")) {
            Date dataRinvio = null;
            // IMPOSTO CODICE ESITO
            long esito = Long.parseLong(udienzaForm.getEsito().getId());
            BigDecimal codiEsito = new BigDecimal(esito);
            dettaglioUdienza.setCODI_FK_ID_ESITO_UD(codiEsito);
            if (esito != 10) {
                dataRinvio = udienzaForm.getDataRinvio();// Resta null solo se
                                                         // l'Esito è RISERVA
                // IMPOSTO DATI RINVIO
                dettaglioUdienza.setDATA_RINVIO(dataRinvio);
                // IMPOSTO ORA INIZIO E ORA FINE RINVIO UDIENZA
                String oraInizioRin = udienzaForm.getOraUdienzaRinvioStr();
                dettaglioUdienza.setORA_UDIENZA_RINVIO(oraInizioRin);
            }
        }
        // IMPOSTO ORA INIZIO E ORA FINE
        String oraInizio = udienzaForm.getOraInizioUdienzaStr();
        String oraFine = null;
        if (!udienzaForm.getOraFineUdienza().getId().equals("-1")) {
            oraFine = udienzaForm.getOraFineUdienzaStr();
        }
        dettaglioUdienza.setORA_INIZIO_UDIENZA(oraInizio);
        dettaglioUdienza.setORA_FINE_UDIENZA(oraFine);
        mapperBean.map(udienzaForm, dettaglioUdienza);
        //nuova gestione verbale udienza 
        if(tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(tipoUfficio).equals(Constants.TIPO_UFFICIO_TRIBUNALE)) {
        	udienzaDao.aggiornaVerbaleUdienza(idUfficioUtente, dettaglioUdienza, idAtto, udienzaForm.getAllegato().getDocumento(), udienzaForm.getAllegato().getFileName(), udienzaForm.getAllegato().getContentType());
    	}else{
    		udienzaDao.aggiornaDatiUdienza(idUfficioUtente, dettaglioUdienza); 		
    	}
        // AGGIORNAMENTO DEI FLAG PRESENZA
        List<SoggettoForm> sogg = udienzaForm.getListaSoggettiConvocatiForm();
        List<SoggettoForm> dif = udienzaForm.getListaDifensoriForm();
        // AGGIORNA FLAG PRESENZA DEI SOGGETTI CONVOCATI
        for (int i = 0; i < sogg.size(); i++) {
            SoggettoForm form = sogg.get(i);
            UdienzaProcedimentoDto dettSoggConv = new UdienzaProcedimentoDto();
            mapperBean.map(form, dettSoggConv);
            udienzaDao.aggiornaFlagSoggConv(idUfficioUtente, udienzaForm.getCodUdienza(), dettSoggConv);
        }
        // AGGIORNA FLAG PRESENZA DEI DIFENSORI
        for (int i = 0; i < dif.size(); i++) {
            SoggettoForm form = dif.get(i);
            UdienzaProcedimentoDto dettDif = new UdienzaProcedimentoDto();
            mapperBean.map(form, dettDif);
            udienzaDao.aggiornaFlagDif(idUfficioUtente, udienzaForm.getCodUdienza(), dettDif);
        }
        //nuova gestione verbale udienza - il metodo udienzaDao.confermaInvioUdienza andrà abbandonato 
        if (tipologia.equals("ConfInvia")) {
        	if(tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(tipoUfficio).equals(Constants.TIPO_UFFICIO_TRIBUNALE)) {
        		udienzaDao.confermaInvioVerbaleUdienza(idProcedimento, idUfficioUtente, idUtente, dettaglioUdienza, idProcUdi, idAtto, udienzaForm.getAllegato().getDocumento(), udienzaForm.getAllegato().getFileName(), udienzaForm.getAllegato().getContentType());
        	}else{
        	 	udienzaDao.confermaInvioUdienza(idProcedimento, idUfficioUtente, idUtente, dettaglioUdienza, idProcUdi); 		
        	}
        }
        return;
    }

    public UdienzaForm dettaglioUdienzaSelezionata(long idProcedimento, long idUfficioUtente, long udienzaSelected) {

        UdienzaForm udienzaForm = new UdienzaForm();
        TProcedimento id = new TProcedimento();
        id.setCodiKIdProc(idProcedimento);
        List<UdienzaProcedimentoDto> udienza = udienzaDao.getDettaglioUdienza(id, idUfficioUtente, udienzaSelected);
        UdienzaProcedimentoDto dettaglioUdienza = udienza.get(0);
        // IMPOSTAZIONE MANUALE ESITO UDIENZA
        String descEsito = "";
        String idEsito = "-1";
        if (dettaglioUdienza.getCODI_FK_ID_ESITO_UD() != null) {
            idEsito = "" + dettaglioUdienza.getCODI_FK_ID_ESITO_UD();
            if (dettaglioUdienza.getDESC_ESITO_UDIENZA() != null) {
                descEsito = dettaglioUdienza.getDESC_ESITO_UDIENZA();
            }
        } else {
            if (dettaglioUdienza.getDESC_ESITO_UD() != null) {
                descEsito = dettaglioUdienza.getDESC_ESITO_UD();
            }
        }
        ComboForm esito = new ComboForm(idEsito, descEsito);
        udienzaForm.setEsito(esito);
        // IMPOSTAZIONE MANUALE COMBOFORM ORA INIZIO UDIENZA
        ComboForm comboFormOraIni = new ComboForm();
        ComboForm comboFormMinutiIni = new ComboForm();
        if (dettaglioUdienza.getORA_INIZIO_UDIENZA() != null) {
            StringTokenizer st = new StringTokenizer(dettaglioUdienza.getORA_INIZIO_UDIENZA(), ":");
            while (st.hasMoreTokens()) {
                // ORA
                String tokenOra = st.nextToken();
                comboFormOraIni.setId(tokenOra);
                comboFormOraIni.setDescrizione(null);
                // MINUTI
                String tokenMin = st.nextToken();
                comboFormMinutiIni.setId(tokenMin);
                comboFormMinutiIni.setDescrizione(null);
            }
        }
        udienzaForm.setOraInizioUdienza(comboFormOraIni);
        udienzaForm.setMinutiInizioUdienza(comboFormMinutiIni);
        // IMPOSTAZIONE MANUALE COMBOFORM ORA FINE UDIENZA
        ComboForm comboFormOraFin = new ComboForm();
        ComboForm comboFormMinutiFin = new ComboForm();
        if (dettaglioUdienza.getORA_FINE_UDIENZA() != null) {
            StringTokenizer st = new StringTokenizer(dettaglioUdienza.getORA_FINE_UDIENZA(), ":");
            while (st.hasMoreTokens()) {
                // ORA
                String tokenOra = st.nextToken();
                comboFormOraFin.setId(tokenOra);
                comboFormOraFin.setDescrizione(null);
                // MINUTI
                String tokenMin = st.nextToken();
                comboFormMinutiFin.setId(tokenMin);
                comboFormMinutiFin.setDescrizione(null);
            }
        } else {
            // ORA
            comboFormOraFin.setId("-1");
            comboFormOraFin.setDescrizione(null);
            // MINUTI
            comboFormMinutiFin.setId("-1");
            comboFormMinutiFin.setDescrizione(null);
        }
        udienzaForm.setOraFineUdienza(comboFormOraFin);
        udienzaForm.setMinutiFineUdienza(comboFormMinutiFin);
        // IMPOSTAZIONE MANUALE COMBOFORM ORA UDIENZA RINVIO
        ComboForm comboFormOraIniRin = new ComboForm();
        ComboForm comboFormMinutiIniRin = new ComboForm();
        if (dettaglioUdienza.getORA_UDIENZA_RINVIO() != null) {
            StringTokenizer st = new StringTokenizer(dettaglioUdienza.getORA_UDIENZA_RINVIO(), ":");
            while (st.hasMoreTokens()) {
                // ORA
                String tokenOra = st.nextToken();
                comboFormOraIniRin.setId(tokenOra);
                comboFormOraIniRin.setDescrizione(null);
                // MINUTI
                String tokenMin = st.nextToken();
                comboFormMinutiIniRin.setId(tokenMin);
                comboFormMinutiIniRin.setDescrizione(null);
            }
        } else {
            // ORA
            comboFormOraIniRin.setId("-1");
            comboFormOraIniRin.setDescrizione(null);
            // MINUTI
            comboFormMinutiIniRin.setId("-1");
            comboFormMinutiIniRin.setDescrizione(null);
        }
        // udienzaForm.setDataUdienzaStr(Utility.convertDateToString(dettaglioUdienza.getDATA_UDIENZA()));
        udienzaForm.setOraUdienzaRinvio(comboFormOraIniRin);
        udienzaForm.setMinutiUdienzaRinvio(comboFormMinutiIniRin);
        mapperBean.map(dettaglioUdienza, udienzaForm);
        return udienzaForm;
    }

    public void gestioneMagistrati(long udienzaSelected, long idUfficioUtente, String idUtente, MagistratoForm form, String tipologia) {

        UdienzaProcedimentoDto magUdienza = new UdienzaProcedimentoDto();
        magUdienza.setCODI_K_ID_UDIENZA(BigDecimal.valueOf(udienzaSelected));
        mapperBean.map(form, magUdienza);
        if (tipologia.equalsIgnoreCase("aggiorna")) {
            udienzaDao.aggiornaMagUdienza(idUfficioUtente, magUdienza);
        } else
            if (tipologia.equalsIgnoreCase("elimina")) {
                udienzaDao.eliminaPmMagUdienza(idUfficioUtente, magUdienza);
            } else {
                udienzaDao.insertMagistratoUdienza(idUfficioUtente, idUtente, magUdienza);
            }
        return;
    }

    public List<SoggettoForm> listaDifensoriUdienza(long idUfficioUtente, long udienzaSelected) {

        List<UdienzaProcedimentoDto> soggetti = udienzaDao.listaDifensoriUdienza(idUfficioUtente, udienzaSelected);
        List<SoggettoForm> difensori = new ArrayList<SoggettoForm>();
        for (int i = 0; i < soggetti.size(); i++) {
            UdienzaProcedimentoDto dettDif = soggetti.get(i);
            SoggettoForm form = new SoggettoForm();
            mapperBean.map(dettDif, form);
            difensori.add(form);
        }
        return difensori;
    }

    public List<MagistratoForm> listaMagistratiCollegioUdienza(long idProcedimento, long idUfficioUtente, long udienzaSelected) {

        TProcedimento id = new TProcedimento();
        id.setCodiKIdProc(idProcedimento);
        List<UdienzaProcedimentoDto> collegio = udienzaDao.listaMagistratiCollegioUdienza(id, idUfficioUtente, udienzaSelected);
        List<MagistratoForm> magistrati = new ArrayList<MagistratoForm>();
        for (int i = 0; i < collegio.size(); i++) {
            UdienzaProcedimentoDto dettCollegio = collegio.get(i);
            MagistratoForm form = new MagistratoForm();
            mapperBean.map(dettCollegio, form);
            magistrati.add(form);
        }
        return magistrati;
    }

    public List<MagistratoForm> listaMagistratiPmUdienza(long idUfficioUtente, long udienzaSelected) {

        List<UdienzaProcedimentoDto> collegio = udienzaDao.listaMagistratiPmUdienza(idUfficioUtente, udienzaSelected);
        List<MagistratoForm> magistrati = new ArrayList<MagistratoForm>();
        for (int i = 0; i < collegio.size(); i++) {
            UdienzaProcedimentoDto dettCollegio = collegio.get(i);
            MagistratoForm form = new MagistratoForm();
            mapperBean.map(dettCollegio, form);
            magistrati.add(form);
        }
        return magistrati;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<MagistratoForm> ricercaMagistratiUff(MagistratoForm magistrato, long idUfficioUtente, String dataUdienza) {

        List<UdienzaProcedimentoDto> listaMagistrati = udienzaDao.elencoMagistratiUff(idUfficioUtente, magistrato.getCognome(), magistrato.getNome(), dataUdienza);
        List<MagistratoForm> elencoMagistrati = new ArrayList<MagistratoForm>();
        for (int i = 0; i < listaMagistrati.size(); i++) {
            UdienzaProcedimentoDto dettMagistrato = listaMagistrati.get(i);
            MagistratoForm mag = new MagistratoForm();
            mag.setId(dettMagistrato.getCODI_K_ID_MAGISTRATO().longValue());
            mag.setNome(dettMagistrato.getDESC_NOME_MAG());
            mag.setCognome(dettMagistrato.getDESC_COGNOME_MAG());
            elencoMagistrati.add(mag);
        }
        return elencoMagistrati;
    }
    
    public List<ComboForm> elencoPmProcUdi(String dataUdienzaSelected, long idProcUdi) {

        List<UdienzaProcedimentoDto> listaPmSelectPerUdienza = udienzaDao.elencoPmProcUdi(dataUdienzaSelected, idProcUdi);
        List<ComboForm> elencoPm = new ArrayList<ComboForm>();
        for (int i = 0; i < listaPmSelectPerUdienza.size(); i++) {
            UdienzaProcedimentoDto dettPm = listaPmSelectPerUdienza.get(i);
            ComboForm form = new ComboForm(String.valueOf(dettPm.getCODI_K_ID_MAGISTRATO().longValue()),dettPm.getDESC_COGNOME_MAG() + " " + dettPm.getDESC_NOME_MAG());
            elencoPm.add(form);
        }
        return elencoPm;
    }

    public List<SoggettoForm> listaSoggettiConvocatiUdienza(long idProcedimento, long idUfficioUtente, long udienzaSelected) {

        TProcedimento id = new TProcedimento();
        id.setCodiKIdProc(idProcedimento);
        List<UdienzaProcedimentoDto> soggetti = udienzaDao.listaSoggettiConvocatiUdienza(id, idUfficioUtente, udienzaSelected);
        List<SoggettoForm> soggettiConv = new ArrayList<SoggettoForm>();
        for (int i = 0; i < soggetti.size(); i++) {
            UdienzaProcedimentoDto dettSoggConv = soggetti.get(i);
            SoggettoForm form = new SoggettoForm();
            mapperBean.map(dettSoggConv, form);
            soggettiConv.add(form);
        }
        return soggettiConv;
    }

    public List<UdienzaForm> listaUdienzePerProcedimento(long idProcedimento, long idUfficioUtente) {

        List<UdienzaForm> lista = new ArrayList<UdienzaForm>();
        TProcedimento id = new TProcedimento();
        id.setCodiKIdProc(idProcedimento);
        List<UdienzaProcedimentoDto> udienzeProcedimento = udienzaDao.getUdienzeByIdProc(id, idUfficioUtente);
        for (int i = 0; i < udienzeProcedimento.size(); i++) {
            UdienzaForm udienzaForm = new UdienzaForm();
            UdienzaProcedimentoDto udienzaDto = udienzeProcedimento.get(i);
            String descEsito = "";
            String idEsito = "";
            if (udienzaDto.getCODI_FK_ID_ESITO_UD() != null) {
                idEsito = "" + udienzaDto.getCODI_FK_ID_ESITO_UD();
                if (udienzaDto.getDESC_ESITO_UDIENZA() != null) {
                    descEsito = udienzaDto.getDESC_ESITO_UDIENZA();
                }
            } else {
                if (udienzaDto.getDESC_ESITO_UD() != null) {
                    descEsito = udienzaDto.getDESC_ESITO_UD();
                }
            }
            ComboForm esito = new ComboForm();
            esito.setDescrizione(descEsito);
            esito.setId(idEsito);
            udienzaForm.setEsito(esito);
            // IMPOSTAZIONE COMBOFORM ORA INIZIO UDIENZA
            ComboForm comboFormOra = new ComboForm();
            ComboForm comboFormMinuti = new ComboForm();
            if (udienzaDto.getORA_INIZIO_UDIENZA() != null) {
                StringTokenizer st = new StringTokenizer(udienzaDto.getORA_INIZIO_UDIENZA(), ":");
                while (st.hasMoreTokens()) {
                    // ORA
                    String tokenOra = st.nextToken();
                    comboFormOra.setId(tokenOra);
                    comboFormOra.setDescrizione(tokenOra);
                    // MINUTI
                    String tokenMin = st.nextToken();
                    comboFormMinuti.setId(tokenMin);
                    comboFormMinuti.setDescrizione(tokenMin);
                }
            }
            udienzaForm.setOraInizioUdienza(comboFormOra);
            udienzaForm.setMinutiInizioUdienza(comboFormMinuti);
            mapperBean.map(udienzaDto, udienzaForm);
            lista.add(udienzaForm);
        }
        return lista;
    }
    
    public List<UdienzaForm> listaUdienzePerProcedimentoEStatoUdienza(long idProcedimento, long idUfficioUtente, String idStatoUdienza) {

        List<UdienzaForm> lista = new ArrayList<UdienzaForm>();
        TProcedimento id = new TProcedimento();
        id.setCodiKIdProc(idProcedimento);
        List<UdienzaProcedimentoDto> udienzeProcedimento = udienzaDao.getUdienzeByIdProcEStatoUdi(id, idUfficioUtente, idStatoUdienza);
        for (int i = 0; i < udienzeProcedimento.size(); i++) {
            UdienzaForm udienzaForm = new UdienzaForm();
            UdienzaProcedimentoDto udienzaDto = udienzeProcedimento.get(i);
//            String descEsito = "";
//            String idEsito = "";
//            if (udienzaDto.getCODI_FK_ID_ESITO_UD() != null) {
//                idEsito = "" + udienzaDto.getCODI_FK_ID_ESITO_UD();
//                if (udienzaDto.getDESC_ESITO_UDIENZA() != null) {
//                    descEsito = udienzaDto.getDESC_ESITO_UDIENZA();
//                }
//            } else {
//                if (udienzaDto.getDESC_ESITO_UD() != null) {
//                    descEsito = udienzaDto.getDESC_ESITO_UD();
//                }
//            }
//            ComboForm esito = new ComboForm();
//            esito.setDescrizione(descEsito);
//            esito.setId(idEsito);
//            udienzaForm.setEsito(esito);
            // IMPOSTAZIONE COMBOFORM ORA INIZIO UDIENZA
            ComboForm comboFormOra = new ComboForm();
            ComboForm comboFormMinuti = new ComboForm();
            if (udienzaDto.getORA_INIZIO_UDIENZA() != null) {
                udienzaForm.setOraInizioUdienzaStr(udienzaDto.getORA_INIZIO_UDIENZA());
                StringTokenizer st = new StringTokenizer(udienzaDto.getORA_INIZIO_UDIENZA(), ":");
                while (st.hasMoreTokens()) {
                    // ORA
                    String tokenOra = st.nextToken();
                    comboFormOra.setId(tokenOra);
                    comboFormOra.setDescrizione(tokenOra);
                    // MINUTI
                    String tokenMin = st.nextToken();
                    comboFormMinuti.setId(tokenMin);
                    comboFormMinuti.setDescrizione(tokenMin);
                }
            }
            udienzaForm.setOraInizioUdienza(comboFormOra);
            udienzaForm.setMinutiInizioUdienza(comboFormMinuti);
            mapperBean.map(udienzaDto, udienzaForm);
            lista.add(udienzaForm);
        }
        return lista;
    }
    
    public List<UdienzaForm> getUdienzaByCodAttoEStatoUdi(long codAtto, String idStatoUdienza, long idUffOwn) {

        List<UdienzaForm> lista = new ArrayList<UdienzaForm>();
        List<UdienzaProcedimentoDto> udienzeProcedimento = udienzaDao.getUdienzaByCodAttoEStatoUdi(codAtto, idUffOwn, idStatoUdienza);
        for (int i = 0; i < udienzeProcedimento.size(); i++) {
            UdienzaForm udienzaForm = new UdienzaForm();
            UdienzaProcedimentoDto udienzaDto = udienzeProcedimento.get(i);
            mapperBean.map(udienzaDto, udienzaForm);
            lista.add(udienzaForm);
        }
        return lista;
    }
    
    public String esisteAttoInLavorazioneConDatiUdienza(long idProcedimento, long idUfficioUtente, long codAtto) {

        Map<String, Long> statoAttoInLav = null;
        statoAttoInLav = udienzaDao.esisteAttoInLavorazioneConDatiUdienza(idProcedimento, idUfficioUtente);
        long idAtto = statoAttoInLav.get("ID_ATTO");
        // Se l'atto in lavorazione con i dati dell'udienza è l'atto corrente
        if(idAtto == codAtto){
            return null;
        }
        else{
            long idStatoAtto = statoAttoInLav.get("ID_STATO_ATTO");
            if(idStatoAtto==Constants.STATOATTO_IN_LAVORAZIONE){
                long statoAtto = statoAttoInLav.get("ID_CAT_ATTO");
                return String.valueOf(statoAtto);
            }
            else return null;
        }
    }
    
    public UdienzaForm caricaUdienzaDecretoSeqConv(long codAtto, long idUfficioUtente) {

        List<UdienzaProcedimentoDto> udienza = udienzaDao.getUdienzaDecretoSeqConvByCodAtto(codAtto, idUfficioUtente);
        if(udienza.size()>0){
            UdienzaProcedimentoDto udienzaDto = udienza.get(0);
            UdienzaForm datiUdienza = new UdienzaForm();
            // IMPOSTAZIONE COMBOFORM ORA INIZIO UDIENZA
            ComboForm comboFormOra = new ComboForm();
            ComboForm comboFormMinuti = new ComboForm();
            if (udienzaDto.getORA_INIZIO_UDIENZA() != null) {
                StringTokenizer st = new StringTokenizer(udienzaDto.getORA_INIZIO_UDIENZA(), ":");
                while (st.hasMoreTokens()) {
                    // ORA
                    String tokenOra = st.nextToken();
                    comboFormOra.setId(tokenOra);
                    comboFormOra.setDescrizione(tokenOra);
                    // MINUTI
                    String tokenMin = st.nextToken();
                    comboFormMinuti.setId(tokenMin);
                    comboFormMinuti.setDescrizione(tokenMin);
                }
            }
            datiUdienza.setOraInizioUdienza(comboFormOra);
            datiUdienza.setMinutiInizioUdienza(comboFormMinuti);
            mapperBean.map(udienzaDto, datiUdienza);
            return datiUdienza;
        }
        else return null;
    }
    
    public long getUdienzaPrecedente(long idUdienza, int idUffOwn) {
    	
    	return udienzaDao.getIdUdienzaPrecedente(idUdienza, idUffOwn);
    	
    	
    }
    
    public void copiaDatiProcPerUdienza(long idUdienza, long idUffOwn, String codUtente, long idProcedimento) {
    	
    	udienzaDao.copiaDatiProcPerUdienza(idUdienza, idUffOwn, codUtente, idProcedimento);
    	
    }

    public void setUdienzaDao(UdienzaDao udienzaDao) {

        this.udienzaDao = udienzaDao;
    }

  //Caterina... nuova gestione verbale udienza.     
    public Map<String, Long> inserisciVerbaleUdienza(long idUfficio, String descUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, long idUdienza, final String nomeMetodo) {

        return udienzaDao.inserisciVerbaleUdienza(idUfficio, descUtente, idProcedimento, idTipoAtto, idCategoriaAtto,idUdienza, nomeMetodo);
    }
    
    public UdienzaForm getUdienzaByIdAtto(long codAtto, long idUfficioUtente) {
    	
        List<UdienzaProcedimentoDto> udienza = udienzaDao.getUdienzaDecretoSeqConvByCodAtto(codAtto, idUfficioUtente);
        UdienzaForm datiUdienza = new UdienzaForm();
        if(udienza.size()>0){
        	UdienzaProcedimentoDto udienzaDto = udienza.get(0);
        	datiUdienza.setCodUdienza(Long.parseLong(udienzaDto.getCODI_K_ID_UDIENZA().toString())); 
        }
        return datiUdienza;
    }    
    
    /**
     * 
     * @param idAtto
     * @param idUfficio
     * @param descUtente
     * @return
     */
    public UdienzaForm getUdienzaRicorso(long idAtto,long idUfficio,String descUtente) {
    	
        TUdienza udienza = udienzaDao.getUdienzaRicorso(idAtto, idUfficio, descUtente);
        
        UdienzaForm datiUdienza = new UdienzaForm();
        if (udienza != null ){
             mapperBean.map(udienza, datiUdienza);
        }
        
        return datiUdienza;
    } 
    
}
