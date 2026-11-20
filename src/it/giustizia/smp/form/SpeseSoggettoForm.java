package it.giustizia.smp.form;

import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.util.AutoPopulatingList;

/**
 * SpeseSoggettoForm
 * 
 * @author 
 */
public class SpeseSoggettoForm {

    private BeneForm               bene;
    private SoggettoForm           soggetto;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                   dataDefinitivitaAtto;
    private ComboForm              speseProcessuali;
    private ComboForm              soggettiTitolari;
    private ComboForm              tipologia;
    private ComboForm              categoria;
    private ComboForm              sottocategoria;
    @NumberFormat(style = Style.NUMBER)
    private Integer                denominatoreFrazioneQuota;
    private String                 descrizioneTipoInteresse;
    private String                 criteriFiltro;
    private boolean                visualizzaFiltro;
    private boolean                applicaTutti;
    private long                   identificativoDelSoggettoDelBene;
    private int                    idTipoInteresse;
    private List<Long>             listaIdCheckati  = new ArrayList<Long>();
    private List<MisuraForm>       misure;
    @NumberFormat(style = Style.NUMBER)
    private Integer                numeratoreFrazioneQuota;
    //@NumberFormat(style = Style.NUMBER, pattern = "##,##")
	@NumberFormat(pattern = "###.##")
    private Double                 quotaInteresse;
    private List<BeneForm>         selected        = new ArrayList<BeneForm>();
    private List<SoggettoForm>     soggetti;
    
    private double quotaSequestroOriginaria;
	private int quotaFrazioneNumeratoreSeqOriginaria;
	private int quotaFrazioneDenominatoreSeqOriginaria;

	private long					idSoggettiTitolari;
	private long					idTipologia;
    private long					idCategoria;
    private long					idSottocategoria;
    
    private long					idProcedimento;
    private int					idUfficio;
    
    //da indagini, queste liste dovrebbero servire per salvare i valori selezionati dalle maschere 
    private List<SpeseSoggettoForm> speseSoggettoLista    = new AutoPopulatingList<SpeseSoggettoForm>(SpeseSoggettoForm.class);
    private List<BeneForm>         beniProcedimento = new AutoPopulatingList<BeneForm>(BeneForm.class);
    private List<SoggettoForm>     soggettiSpeseP = new AutoPopulatingList<SoggettoForm>(SoggettoForm.class);
    private String paginazione="N";
    
  //utilizzato per SpeseProcessuali per contenere importo
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
    @Max(value = 1000000000000L)
    private BigDecimal   importoSpese;
  //utilizzato per SpeseProcessuali per contenere id 
    private int						  idSpeseProcessuali;
  //Utilizzato per check SpeseProcessuali
    private boolean                   checkTutti;
    
  //Utilizzato per le spese processuali
    private List<ComboForm>                listaSpeseProcessuali;
    
    private String                 motivazione1;
    private BigDecimal             importo1;
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataPresentazioneCda;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataDepositoCda;
    private String                 motivazione2;
    private BigDecimal             importo2;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataDefinitivitaCda;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataPresentazioneCass;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataDepositoCass;
    private String                 motivazione3;
    private BigDecimal             importo3;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataDefinitivitaCass;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataDecisione;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataDepositoDecisione;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataSentenza;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataDepositoSentenza;
    
    private Long idProcedimentoCda;
    
    private String	descRichiesta;
    
    
	public String getDescRichiesta() {
		return descRichiesta;
	}

	public void setDescRichiesta(String descRichiesta) {
		this.descRichiesta = descRichiesta;
	}

	public Long getIdProcedimentoCda() {
		return idProcedimentoCda;
	}

	public void setIdProcedimentoCda(Long idProcedimentoCda) {
		this.idProcedimentoCda = idProcedimentoCda;
	}
    
    public Date getDataPresentazioneCda() {
		return dataPresentazioneCda;
	}

	public void setDataPresentazioneCda(Date dataPresentazioneCda) {
		this.dataPresentazioneCda = dataPresentazioneCda;
	}

	public Date getDataDepositoCda() {
		return dataDepositoCda;
	}

	public void setDataDepositoCda(Date dataDepositoCda) {
		this.dataDepositoCda = dataDepositoCda;
	}

	public Date getDataDefinitivitaCda() {
		return dataDefinitivitaCda;
	}

	public void setDataDefinitivitaCda(Date dataDefinitivitaCda) {
		this.dataDefinitivitaCda = dataDefinitivitaCda;
	}

	public Date getDataPresentazioneCass() {
		return dataPresentazioneCass;
	}

	public void setDataPresentazioneCass(Date dataPresentazioneCass) {
		this.dataPresentazioneCass = dataPresentazioneCass;
	}

	public Date getDataDepositoCass() {
		return dataDepositoCass;
	}

	public void setDataDepositoCass(Date dataDepositoCass) {
		this.dataDepositoCass = dataDepositoCass;
	}

	public Date getDataDefinitivitaCass() {
		return dataDefinitivitaCass;
	}

	public void setDataDefinitivitaCass(Date dataDefinitivitaCass) {
		this.dataDefinitivitaCass = dataDefinitivitaCass;
	}

	public List<BeneForm> estrapolaListaBeniConIdSoggettiCheckati() {
		//System.out.println("Estrapolalistabeniconidchechekati" + beniProcedimento.size() );
        if ((beniProcedimento == null) || beniProcedimento.isEmpty()) {
            return null;
        }else{
        	//System.out.println("BeniProcedimento = " + beniProcedimento.toString()  );
        	List<BeneForm> risultati = new ArrayList<BeneForm>();
        	//System.out.println("BeniProcedimento dopo risultati "  );
        	List<BeneForm> benePro =  new ArrayList<BeneForm>();
        	benePro.addAll(beniProcedimento);
        	for (BeneForm tmp : beniProcedimento) {
        		//System.out.println("Beneform = "   );
        		if (tmp != null){				//AG
        			//System.out.println("Beneform != null " + tmp.getIdBene() );
        			if (tmp.getIdBene() != null) {
        				risultati.add(tmp);
        			}
        		}   //AG	  
        	}
        	//System.out.println("risultati" + risultati.size() );
        	return (!risultati.isEmpty()) ? risultati : null;
        }
    }

    public List<SoggettoForm> estrapolaListaSoggettiSpesePConIdCheckati() {

        if ((soggettiSpeseP == null) || soggettiSpeseP.isEmpty()) {
            return null;
        }
        List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
        for (SoggettoForm tmp : soggettiSpeseP) {
            if (tmp.getIdSoggetto() != null) {
                risultati.add(tmp);
            }
        }
        return (!risultati.isEmpty()) ? risultati : null;
    }
    /**
     * @return the criteriFiltro
     */
 
    public String getCriteriFiltro() {
         if ((soggettiTitolari!= null && soggettiTitolari.getId() != null && !soggettiTitolari.getId().equals("-1")) || 
        	(tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) || 
        	(categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || 
        	(sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
            StringBuilder descrizione = new StringBuilder();
            descrizione.append(" con");
            if (soggettiTitolari != null && soggettiTitolari.getId() != null && !soggettiTitolari.getId().equals("-1")) {
                descrizione.append(" Soggetti Titolari: ");
                descrizione.append(soggettiTitolari.getDescrizione());
                if ((tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) || (categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) {
                descrizione.append(" Tipologia: ");
                descrizione.append(tipologia.getDescrizione());
                if ((categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) {
                descrizione.append(" Categoria: ");
                descrizione.append(categoria.getDescrizione());
                if ((sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1")) {
                descrizione.append(" Sottocategoria: ");
                descrizione.append(sottocategoria.getDescrizione());
            }
            this.criteriFiltro = descrizione.toString();
        } else criteriFiltro = null;
        return criteriFiltro;
    }

    /**
     * @return the soggettiTitolari
     */

    public ComboForm getSoggettiTitolari() {

        return soggettiTitolari;
    }


    /**
     * @return the tipologia
     */

    public ComboForm getTipologia() {

        return tipologia;
    }

    /**
     * @return the categoria
     */

    public ComboForm getCategoria() {

        return categoria;
    }

    /**
     * @return the sottocategoria
     */

    public ComboForm getSottocategoria() {

        return sottocategoria;
    }

    /**
     * @return the bene
     */
    public BeneForm getBene() {

        return bene;
    }

    /**
     * @return the speseSoggettoLista
     */
    public List<SpeseSoggettoForm> getSpeseSoggettoLista() {

        return speseSoggettoLista;
    }    

    public List<BeneForm> getBeniProcedimento() {

        return beniProcedimento;
    }

    /**
     * @return the dataDefinitivitaAtto
     */
    public Date getDataDefinitivitaAtto() {
    
        return dataDefinitivitaAtto;
    }

    /**
     * @return the spese Processuali
     */
    public ComboForm getSpeseProcessuali() {

        return speseProcessuali;
    }
    
    public Integer getDenominatoreFrazioneQuota() {

        return denominatoreFrazioneQuota;
    }

    public String getDescrizioneFrazioneOQuota() {

        if (Utility.isDefinito(numeratoreFrazioneQuota) && Utility.isDefinito(denominatoreFrazioneQuota)) {
            return "" + numeratoreFrazioneQuota + "/" + denominatoreFrazioneQuota;
        }
        //quotaInteresse è un intero
        if(Utility.isDefinito(quotaInteresse) && java.lang.Math.floor(quotaInteresse)==quotaInteresse)
        	return String.valueOf(quotaInteresse.intValue())+"%";
        	     
	       String quota = String.valueOf(quotaInteresse).replace('.',',');
	       if(quota!=null && !quota.equalsIgnoreCase("null")){
	       if(quota.substring(quota.indexOf(',')+1).length()!=2)
	    	   quota = quota + "0";
	       }else{
	    	   quota = "0";
	       }
	       return quota+"%";
    }

    /**
     * @return the descrizioneTipoInteresse
     */
    public String getDescrizioneTipoInteresse() {

        return descrizioneTipoInteresse;
    }

    /**
     * @return the identificativoDelSoggettoDelBene
     */
    public long getIdentificativoDelSoggettoDelBene() {

        return identificativoDelSoggettoDelBene;
    }

    /**
     * @return the idTipoInteresse
     */
    public int getIdTipoInteresse() {

        return idTipoInteresse;
    }

    public List<Long> getListaIdCheckati() {

        return listaIdCheckati;
    }

    
    /**
     * @return the misure
     */
    public List<MisuraForm> getMisure() {
    
        return misure;
    }

    public Integer getNumeratoreFrazioneQuota() {

        return numeratoreFrazioneQuota;
    }

    /**
     * @return the quotaInteresse
     */
    public Double getQuotaInteresse() {

        return quotaInteresse;
    }

    /**
     * @return the selected
     */
    public List<BeneForm> getSelected() {

        return selected;
    }

    /**
     * @return the soggetti
     */
    public List<SoggettoForm> getSoggetti() {

        return soggetti;
    }

    /**
     * @return the soggettiSpeseP
     */
    public List<SoggettoForm> getSoggettiSpeseP() {
    
        return soggettiSpeseP;
    }

    /**
     * @return the soggetto
     */
    public SoggettoForm getSoggetto() {

        return soggetto;
    }
    
    /**
     * @return the visualizzaFiltro
     */
    public boolean isVisualizzaFiltro() {

        return visualizzaFiltro;
    }
    
    /**
     * @return the applicaTutti
     */
    public boolean isApplicaTutti() {

        return applicaTutti;
    }
    public long getIdSoggettiTitolari() {
		idSoggettiTitolari = 0;
		if(soggettiTitolari!=null && soggettiTitolari.getId()!=null){
			idSoggettiTitolari = Long.parseLong(soggettiTitolari.getId());
		}
		return idSoggettiTitolari;
	}
	public void setIdSoggettiTitolari(long idSoggettiTitolari) {
		this.idSoggettiTitolari = idSoggettiTitolari;
	}
    public long getIdTipologia() {
		idTipologia = 0;
		if(tipologia!=null && tipologia.getId()!=null){
			idTipologia = Integer.parseInt(tipologia.getId());
		}
		return idTipologia;
	}
	public void setIdTipologia(long idTipologia) {
		this.idTipologia = idTipologia;
	}
	public long getIdCategoria() {
		idCategoria = 0;
		if(categoria!=null && categoria.getId()!=null){
			idCategoria = Integer.parseInt(categoria.getId());
		}
		return idCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public long getIdSottocategoria() {
		idSottocategoria = 0;
		if(sottocategoria !=null && sottocategoria.getId()!=null){
			idSottocategoria = Integer.parseInt(sottocategoria.getId());
		}
		return idSottocategoria;
	}
	public void setIdSottocategoria(long idSottocategoria) {
		this.idSottocategoria = idSottocategoria;
	}

    /**
     * @param criteriFiltro
     *            the criteriFiltro to set
     */
    public void setCriteriFiltro(String criteriFiltro) {

        this.criteriFiltro = criteriFiltro;
    }

    /**
     * @param visualizzaFiltro
     *            the visualizzaFiltro to set
     */
    public void setVisualizzaFiltro(boolean visualizzaFiltro) {

        this.visualizzaFiltro = visualizzaFiltro;
    }
    
    /**
     * @param applicaTutti
     *            the applicaTutti to set
     */
    public void setApplicaTutti(boolean applicaTutti) {

        this.applicaTutti = applicaTutti;
    }

    /**
     * @param soggettiTitolari
     *            the soggettiTitolari to set
     */

    public void setSoggettiTitolari(ComboForm soggettiTitolari) {

        this.soggettiTitolari = soggettiTitolari;
    }

    /**
     * @param tipologia
     *            the tipologia to set
     */

    public void setTipologia(ComboForm tipologia) {

        this.tipologia = tipologia;
    }

    /**
     * @param categoria
     *            the categoria to set
     */

    public void setCategoria(ComboForm categoria) {

        this.categoria = categoria;
    }

    /**
     * @param sottocategoria
     *            the sottocategoria to set
     */

    public void setSottocategoria(ComboForm sottocategoria) {

        this.sottocategoria = sottocategoria;
    }

    /**
     * @param bene
     *            the bene to set
     */
    public void setBene(BeneForm bene) {

        this.bene = bene;
    }

    /**
     * @param speseSoggettoLista
     *            the speseSoggettoLista to set
     */
    public void setSpeseSoggetto(List<SpeseSoggettoForm> speseSoggettoLista) {

        this.speseSoggettoLista = speseSoggettoLista;
    }

    public void setBeniProcedimento(List<BeneForm> beniProcedimento) {

        this.beniProcedimento = beniProcedimento;
    }

    /**
     * @param dataDefinitivitaAtto
     *            the dataDefinitivitaAtto to set
     */
    public void setDataDefinitivitaAtto(Date dataDefinitivitaAtto) {
    
        this.dataDefinitivitaAtto = dataDefinitivitaAtto;
    }

    /**
     * @param speseprocessuali
     *            the speseProcessuali to set
     */
    public void setSpeseProcessuali(ComboForm speseProcessuali) {

        this.speseProcessuali = speseProcessuali;
    }

    public void setDenominatoreFrazioneQuota(Integer denominatoreFrazioneQuota) {

        this.denominatoreFrazioneQuota = denominatoreFrazioneQuota;
    }

    /**
     * @param descrizioneTipoInteresse
     *            the descrizioneTipoInteresse to set
     */
    public void setDescrizioneTipoInteresse(String descrizioneTipoInteresse) {

        this.descrizioneTipoInteresse = descrizioneTipoInteresse;
    }

    /**
     * @param identificativoDelSoggettoDelBene
     *            the identificativoDelSoggettoDelBene to set
     */
    public void setIdentificativoDelSoggettoDelBene(long identificativoDelSoggettoDelBene) {

        this.identificativoDelSoggettoDelBene = identificativoDelSoggettoDelBene;
    }

    /**
     * @param idTipoInteresse
     *            the idTipoInteresse to set
     */
    public void setIdTipoInteresse(int idTipoInteresse) {

        this.idTipoInteresse = idTipoInteresse;
    }

    public void setListaIdCheckati(List<Long> listaIdCheckati) {

        this.listaIdCheckati = listaIdCheckati;
    }

    
    /**
     * @param misure the misure to set
     */
    public void setMisure(List<MisuraForm> misure) {
    
        this.misure = misure;
    }

    public void setNumeratoreFrazioneQuota(Integer numeratoreFrazioneQuota) {

        this.numeratoreFrazioneQuota = numeratoreFrazioneQuota;
    }

    /**
     * @param quotaInteresse
     *            the quotaInteresse to set
     */
    public void setQuotaInteresse(Double quotaInteresse) {

        this.quotaInteresse = quotaInteresse;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(List<BeneForm> selected) {

        this.selected = selected;
    }

    /**
     * @param soggetti
     *            the soggetti to set
     */
    public void setSoggetti(List<SoggettoForm> soggetti) {

        this.soggetti = soggetti;
    }
    
    /**
     * @param soggettiSpeseP
     *            the soggettiSpeseP to set
     */
    public void setSoggettiSpeseP(List<SoggettoForm> soggettiSpeseP) {
    
        this.soggettiSpeseP = soggettiSpeseP;
    }

    /**
     * @param soggetto
     *            the soggetto to set
     */
    public void setSoggetto(SoggettoForm soggetto) {

        this.soggetto = soggetto;
    }

	public double getQuotaSequestroOriginaria() {
		return quotaSequestroOriginaria;
	}

	public void setQuotaSequestroOriginaria(double quotaSequestroOriginaria) {
		this.quotaSequestroOriginaria = quotaSequestroOriginaria;
	}

	public int getQuotaFrazioneNumeratoreSeqOriginaria() {
		return quotaFrazioneNumeratoreSeqOriginaria;
	}

	public void setQuotaFrazioneNumeratoreSeqOriginaria(
			int quotaFrazioneNumeratoreSeqOriginaria) {
		this.quotaFrazioneNumeratoreSeqOriginaria = quotaFrazioneNumeratoreSeqOriginaria;
	}

	public int getQuotaFrazioneDenominatoreSeqOriginaria() {
		return quotaFrazioneDenominatoreSeqOriginaria;
	}

	public void setQuotaFrazioneDenominatoreSeqOriginaria(
			int quotaFrazioneDenominatoreSeqOriginaria) {
		this.quotaFrazioneDenominatoreSeqOriginaria = quotaFrazioneDenominatoreSeqOriginaria;
	}

	public String getPaginazione() {
		return paginazione;
	}

	public void setPaginazione(String paginazione) {
		this.paginazione = paginazione;
	}

	public String getMotivazione1() {
		return motivazione1;
	}

	public void setMotivazione1(String motivazione1) {
		this.motivazione1 = motivazione1;
	}

	public BigDecimal getImporto1() {
		return importo1;
	}

	public void setImporto1(BigDecimal importo1) {
		this.importo1 = importo1;
	}

	public String getMotivazione2() {
		return motivazione2;
	}

	public void setMotivazione2(String motivazione2) {
		this.motivazione2 = motivazione2;
	}

	public BigDecimal getImporto2() {
		return importo2;
	}

	public void setImporto2(BigDecimal importo2) {
		this.importo2 = importo2;
	}

	public String getMotivazione3() {
		return motivazione3;
	}

	public void setMotivazione3(String motivazione3) {
		this.motivazione3 = motivazione3;
	}

	public BigDecimal getImporto3() {
		return importo3;
	}

	public void setImporto3(BigDecimal importo3) {
		this.importo3 = importo3;
	}

	public long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public int getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(int idUfficio) {
		this.idUfficio = idUfficio;
	}

	public Date getDataDecisione() {
		return dataDecisione;
	}

	public void setDataDecisione(Date dataDecisione) {
		this.dataDecisione = dataDecisione;
	}

	public Date getDataDepositoDecisione() {
		return dataDepositoDecisione;
	}

	public void setDataDepositoDecisione(Date dataDepositoDecisione) {
		this.dataDepositoDecisione = dataDepositoDecisione;
	}

	public Date getDataSentenza() {
		return dataSentenza;
	}

	public void setDataSentenza(Date dataSentenza) {
		this.dataSentenza = dataSentenza;
	}

	public Date getDataDepositoSentenza() {
		return dataDepositoSentenza;
	}

	public void setDataDepositoSentenza(Date dataDepositoSentenza) {
		this.dataDepositoSentenza = dataDepositoSentenza;
	}
	
	//mev Spese Processuali
		public BigDecimal getImportoSpese() {
			return importoSpese;
		}

		public void setImportoSpese(BigDecimal importoSpese) {
			this.importoSpese = importoSpese;
		}
		
		public int getIdSpeseProcessuali() {
			return idSpeseProcessuali;
		}
		
		public void setIdSpeseProcessuali(int idSpeseProcessuali) {
			this.idSpeseProcessuali = idSpeseProcessuali;
		}
		
		/**
	     * @return the checkTutti
	     */
	    public boolean isCheckTutti() {
	    
	        return checkTutti;
	    }
	      
	    /**
	     * @param checkTutti
	     *            the checkTutti to set
	     */
	    public void setCheckTutti(boolean checkTutti) {
	    
	        this.checkTutti = checkTutti;
	    } 
	    
	    /**
	     * @return the speseProcessuali
	     */
	    public List<ComboForm> getListaSpeseProcessuali() {

	        return listaSpeseProcessuali;
	    }
	    
	    /**
	     * @param listaSpeseProcessuali
	     *            the speseProcessuali to set
	     */
	    public void setListaSpeseProcessuali(List<ComboForm> listaSpeseProcessuali) {

	        this.listaSpeseProcessuali = listaSpeseProcessuali;
	         
	    }

		//mev Spese Processuali

}
