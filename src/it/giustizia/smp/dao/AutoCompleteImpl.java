package it.giustizia.smp.dao;

import it.giustizia.smp.dto.BeneTitoloDirittoDto;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AutoCompleteImpl extends HibernateDaoSupport implements AutoCompleteDao {

    String                     sql;
    private BeneDao            beneDao;
    private CommonDao          commonDao;
    private ConservatorieDao   conservatorieDao;
    @Autowired(required = true)
    private HttpServletRequest request;
    
    @Autowired
    private BeneService         beneService;

    public AutoCompleteImpl() {

    }

    public AutoCompleteImpl(String sql) {

        super();
        this.sql = sql;
    }

    private static boolean esisteIdBene(String input2, Long val) {

        String[] idBeneDaEscludere = input2.split(",");
        long lIdBeneDaEscludere = 0l;
        for (String element : idBeneDaEscludere) {
            lIdBeneDaEscludere = Long.parseLong(element);
            if (val == lIdBeneDaEscludere) {
                return true;
            }
        }
        return false;
    }

    public String getCodiceCatastaleComune(long input) throws DataAccessException {

        String item = null;
        TAnComuni comune = commonDao.getComuneById(input);
        if (comune != null) {
            item = comune.getDescCatasto();
        }
        return item;
    }

    public List<ComboForm> getCodiciCabComboForm(String input, String codiceAbi) throws DataAccessException {

        List<TAnOperFinanz> lista = this.commonDao.getCodiciCab(input, codiceAbi, null);
        List<ComboForm> cabList = new ArrayList<ComboForm>();
        HashMap<String,String> listCab = new HashMap<String,String>();
        Iterator<TAnOperFinanz> iter = lista.iterator();
        while (iter.hasNext()) {
            TAnOperFinanz elem = iter.next();
            String cab = elem.getId().getCodiKIdCab();
            if(!listCab.containsKey(cab)){
                listCab.put(cab, cab);
                cabList.add(new ComboForm(cab,cab));
            }
        }
        return cabList;
    }


	public List<ComboForm> getOperatoriFinanziariPolizzeComboForm(String input) throws DataAccessException {
		String inputNew = input.replaceAll("'", "''");
		inputNew = inputNew.toUpperCase();
		List<TAnAltriOper> lista = this.commonDao.getOperatoriFinanziariPolizze(inputNew);
		List<ComboForm> opFinList = new ArrayList<ComboForm>();
		Iterator<TAnAltriOper> iter = lista.iterator();
		while (iter.hasNext()) {
			TAnAltriOper elem = iter.next();
			String id = String.valueOf(elem.getCodiKIdOperatore());
			String desc = elem.getDescOperatore();
			opFinList.add(new ComboForm(id, desc));
		}
		return opFinList;
	}

    public List<ComboForm> getOperatoriFinanziariComboForm(String input) throws DataAccessException {

        String inputNew = input.replaceAll("'", "''");
        inputNew = inputNew.toUpperCase();
        List<TAnOperFinanz> lista = this.commonDao.getOperatoriFinanziari(inputNew);
        List<ComboForm> opFinList = new ArrayList<ComboForm>();
        HashMap<String,String> listOper = new HashMap<String,String>();
        Iterator<TAnOperFinanz> iter = lista.iterator();
        while (iter.hasNext()) {
            TAnOperFinanz elem = iter.next();
            String abi = elem.getId().getCodiKIdAbi();
            String desc = elem.getDescBanca();
            if(!listOper.containsKey(abi)){
                listOper.put(abi, desc);
                opFinList.add(new ComboForm(elem.getId().getCodiKIdAbi(),elem.getDescBanca()));
            }
        }
        return opFinList;
    }

    public List<String> getResults(String input) throws DataAccessException {

        List<String> items = new ArrayList<String>();
        List<TAnComuni> comuni = commonDao.getAllComunyStartingWith(input);
        Iterator<TAnComuni> iter = comuni.iterator();
        while (iter.hasNext()) {
            TAnComuni current = iter.next();
            items.add(current.getDescComune() + " " + current.getDescCapComune());
        }
        return items;
    }

    public List<ComboForm> getResultsComboForm(String input) throws DataAccessException {

        List<ComboForm> items = new ArrayList<ComboForm>();
        List<TAnComuni> comuni = commonDao.getAllComunyStartingWith(input.toUpperCase());
        Iterator<TAnComuni> iter = comuni.iterator();
        while (iter.hasNext()) {
            TAnComuni current = iter.next();
            items.add(new ComboForm("" + current.getCodiKIdComune(), current.getDescComune() + 
            	" (" + current.getTAnProvincia().getDescSiglaProvincia() + ")"));
        }
        return items;
    }

    public List<ComboForm> getConservatComboForm( String nomeCombo, String input )
    {
        List<ComboForm> items = new ArrayList<ComboForm>();
        input = input + "%";
        List<Object[]> comuni = null;
        if( nomeCombo.equals( "Comune" ) )
        {
            comuni = conservatorieDao.getComuniSediTribun( input.toUpperCase() );
        } else
            if( nomeCombo.equals( "ComuneCatastaleP" ) )
            {
                comuni = conservatorieDao.getAllComuniProv( input.toUpperCase() );
            } else
                if( nomeCombo.equals( "ComboComuniIstat" ) )
                {
                    comuni = conservatorieDao.getIstatComuniStartingWith( input.toUpperCase() );
                } else
                    if( nomeCombo.equals( "ComboComuniBelfiore" ) )
                    {
                        comuni = conservatorieDao.getBelfComuniStartingWith( input.toUpperCase() );
                    }
        Iterator<Object[]> iter = comuni.iterator();
        while( iter.hasNext() )
        {
            Object[] current = iter.next();
            items.add( new ComboForm( "" + current[0], current[1] + " (" + current[2] + ")" ) );
        }
        return items;
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // input2 rappresenta una stringa di valori di idbene da escludere dalla
    // lista,
    // serve per la funziona di copia titoli di diritto.
    // input3 sarà uguale ad 'S' se ci troviamo nel caso di decreto di sequestro
    public List<String> getResultsIdBene(String input, String input2, String input3) throws DataAccessException {

        List queryResults = null;
        List<String> risultati = new ArrayList<String>();
        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
        try {
            if ((input3 != null) && !input3.equals("")) {
                String delim = "[;]";
                String[] ids = input3.split(delim);
                long idDecretoSequestro = Long.parseLong(ids[0].trim());
                long idProposta = Long.parseLong(ids[1].trim());
                queryResults = beneDao.listaBeniProcDecretoFiltro(1, idUfficioUtente, idProcedimento, Long.parseLong(input), null, null, null, idDecretoSequestro, idProposta, null, null);
                
                if ((queryResults != null) && !queryResults.isEmpty()) {
                    Iterator iterator = queryResults.iterator();
                    while (iterator.hasNext()) {
                        Map<String, Object> row = (Map<String, Object>) iterator.next();
                        TBene bene = (TBene) row.get("bene");
                        if (bene.getId() != null) {
                            if (input2 != null) {
                                if (!esisteIdBene(input2, bene.getId().getCodiKIdBene())) {
                                    risultati.add(bene.getId().getCodiKIdBene() + "");
                                }
                            } else {
                                risultati.add(bene.getId().getCodiKIdBene() + "");
                            }
                        }
                    }
                }

            } else {
                queryResults = beneDao.listaBeniPerProcedimento(1, idUfficioUtente, idProcedimento, Long.parseLong(input), null, null, null, null, null);

                if ((queryResults != null) && !queryResults.isEmpty()) {
                    Iterator iterator = queryResults.iterator();
                    while (iterator.hasNext()) {
                    	BeneTitoloDirittoDto row = (BeneTitoloDirittoDto) iterator.next();
                        if (row.getCODIKIDBENE()!=null) {
                        	long codIdBene =  row.getCODIKIDBENE().longValue();
                            if (input2 != null) {
                                if (!esisteIdBene(input2, codIdBene)) {
                                    risultati.add(codIdBene + "");
                                }
                            } else {
                                risultati.add(codIdBene + "");
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return risultati;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // input2 rappresenta una stringa di valori di idbene da escludere dalla
    // lista,
    // serve per la funziona di copia titoli di diritto.
    // input3 sarà uguale ad 'S' se ci troviamo nel caso di decreto di sequestro

    public List<String> getResultsIdBeneCopia(String input, String input2, String input3) throws DataAccessException {
        List queryResults = null;
        List<String> risultati = new ArrayList<String>();
        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
        long idAtto = 0l;
        try {
            if ((input3 != null) && !input3.equals("")) {
                String delim = "[;]";
                String[] ids = input3.split(delim);
                long idDecretoSequestro = Long.parseLong(ids[0].trim());
                long idProposta = Long.parseLong(ids[1].trim());
                queryResults = beneDao.listaBeniProcDecretoFiltro(1, idUfficioUtente, idProcedimento, Long.parseLong(input), null, null, null, idDecretoSequestro, idProposta, null, null);
            } else {
                queryResults = beneDao.listaBeniPerProcedimento(1, idUfficioUtente, idProcedimento, Long.parseLong(input), null, null, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((queryResults != null) && !queryResults.isEmpty()) {
            Iterator iterator = queryResults.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> row = (Map<String, Object>) iterator.next();
                TBene bene = (TBene) row.get("bene");
                if (bene.getId() != null) {
                    if (input2 != null) {
                        if (!esisteIdBene(input2, bene.getId().getCodiKIdBene())) {
                            risultati.add(bene.getId().getCodiKIdBene() + "");
                        }
                    } else {
                        risultati.add(bene.getId().getCodiKIdBene() + "");
                    }
                }
            }
        }
        List<String> risultatiFinali = new ArrayList<String>();
        //tolgo dai suggerimenti gli id dei beni che non hanno titoli di diritto
        for(int i =0; i< risultati.size(); i++){
        	List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento, idUfficioUtente, Long.valueOf(risultati.get(i)), idAtto);
    		if ( !((beniSoggetti == null) || beniSoggetti.isEmpty()) ) {
    			risultatiFinali.add(risultati.get(i));
    		}        	
        }
        return risultatiFinali;
    }

    public void setBeneDao(BeneDao beneDao) {

        this.beneDao = beneDao;
    }

    public void setCommonDao(CommonDao commonDao) {

        this.commonDao = commonDao;
    }

    public void setConservatorieDao( ConservatorieDao conservatorieDao )
    {
        this.conservatorieDao = conservatorieDao;
    }

}
