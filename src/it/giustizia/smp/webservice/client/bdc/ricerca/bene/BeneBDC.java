/**
 * BeneBDCType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class BeneBDC  implements java.io.Serializable {
    private long idBene;

    private long versioneBene;

    private java.lang.String descBene;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipologia;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC categoria;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC sottocategoria;

    private java.lang.String ragioneSociale;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC settoreAttivita;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC stato;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC regione;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC provincia;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comune;

    private java.lang.String desComuneEstero;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.IndirizzoFormBeneBDC indirizzo;

    private java.lang.String numeroCCIAA;

    private java.lang.String numeroDipendenti;

    private java.lang.String partitaIva;

    private java.lang.String codiceFiscale;

    private java.math.BigDecimal capitaleSociale;

    private java.lang.String amministratore;

    private java.lang.String gravame;

    private java.lang.String scala;

    private java.lang.String interno;

    private java.lang.String piano;

    private java.lang.String edificio;

    private java.lang.String lotto;

    private long idComuneCatastale;

    private java.lang.String codiceCatastale1;

    private java.lang.String tipoCatasto;

    private java.util.Date dataAccatastamento;

    private java.lang.String fondo;

    private java.lang.String sezione;

    private java.lang.String foglio;

    private java.lang.String particella1;

    private java.lang.String particella2;

    private java.lang.String sub1;

    private java.lang.String sub2;

    private java.lang.String tipoDenuncia;

    private java.math.BigInteger numeroDenuncia1;

    private java.lang.Short numeroDenuncia2;

    private java.lang.Short annoDenuncia;

    private java.lang.String schedaAccatastamento;

    private java.lang.Long zonaCensuaria;

    private java.math.BigDecimal renditaCatastale;

    private java.math.BigDecimal renditaDominicale;

    private java.math.BigInteger ettari;

    private java.math.BigInteger are;

    private java.math.BigInteger centiare;

    private java.lang.Short vaniIntero;

    private java.lang.Short vaniDecimale;

    private java.lang.Short metriCubiIntero;

    private java.lang.Short metriCubiDecimale;

    private java.lang.Short metriQuadriIntero;

    private java.lang.Byte metriQuadriDecimale;

    private java.lang.String appartenenza;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comuneCatastaleP;

    private java.lang.String codiceCatastale1P;

    private java.lang.String codiceCatastale2P;

    private java.lang.String tipoCatastoP;

    private java.lang.String sezioneP;

    private java.lang.String foglioP;

    private java.lang.String particella1P;

    private java.lang.String particella2P;

    private java.lang.String sub1P;

    private java.lang.String sub2P;

    private java.lang.String tipoDenunciaP;

    private java.math.BigInteger numeroDenuncia1P;

    private java.lang.Short numeroDenuncia2P;

    private java.lang.Short annoDenunciaP;

    private java.lang.String tipoOccupanti;

    private java.lang.Long numRegBeneMobileRegistrato;

    private java.lang.String targa;

    private java.util.Date dataRegistrazione;

    private java.lang.String modelloVeicolo;

    private java.lang.Long codImmatricolazioneVeicolo;

    private java.lang.String telaio;

    private java.lang.String coloreVeicolo;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC vincolo;

    private java.lang.String descrizioneVincolo;

    private java.util.Date dataScadenza;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC descOperFinanz;

    private java.lang.String intestatario;

    private java.lang.String numeroRegistrazione;

    private java.lang.String codiceIban;

    private java.math.BigDecimal valutaEst;

    private java.math.BigDecimal valutaEur;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipoValuta;

    private java.lang.String codiceAbi;

    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC codiceCab;

    private java.lang.String numBollettino;

    private java.lang.Long numModelloTribunale;

    private java.lang.String indirizzoBeneFin;

    private java.lang.String direttoreProtempore;

    private java.lang.String descrizioneCapo;

    private java.lang.Long numeroAnimali;

    private java.lang.String descrizioneCollezione;

    private java.lang.Long numeroPezzi;

    private java.lang.String descUbicazione;

    public BeneBDC() {
    }

    public BeneBDC(
           long idBene,
           long versioneBene,
           java.lang.String descBene,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipologia,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC categoria,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC sottocategoria,
           java.lang.String ragioneSociale,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC settoreAttivita,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC stato,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC regione,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC provincia,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comune,
           java.lang.String desComuneEstero,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.IndirizzoFormBeneBDC indirizzo,
           java.lang.String numeroCCIAA,
           java.lang.String numeroDipendenti,
           java.lang.String partitaIva,
           java.lang.String codiceFiscale,
           java.math.BigDecimal capitaleSociale,
           java.lang.String amministratore,
           java.lang.String gravame,
           java.lang.String scala,
           java.lang.String interno,
           java.lang.String piano,
           java.lang.String edificio,
           java.lang.String lotto,
           long idComuneCatastale,
           java.lang.String codiceCatastale1,
           java.lang.String tipoCatasto,
           java.util.Date dataAccatastamento,
           java.lang.String fondo,
           java.lang.String sezione,
           java.lang.String foglio,
           java.lang.String particella1,
           java.lang.String particella2,
           java.lang.String sub1,
           java.lang.String sub2,
           java.lang.String tipoDenuncia,
           java.math.BigInteger numeroDenuncia1,
           java.lang.Short numeroDenuncia2,
           java.lang.Short annoDenuncia,
           java.lang.String schedaAccatastamento,
           java.lang.Long zonaCensuaria,
           java.math.BigDecimal renditaCatastale,
           java.math.BigDecimal renditaDominicale,
           java.math.BigInteger ettari,
           java.math.BigInteger are,
           java.math.BigInteger centiare,
           java.lang.Short vaniIntero,
           java.lang.Short vaniDecimale,
           java.lang.Short metriCubiIntero,
           java.lang.Short metriCubiDecimale,
           java.lang.Short metriQuadriIntero,
           java.lang.Byte metriQuadriDecimale,
           java.lang.String appartenenza,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comuneCatastaleP,
           java.lang.String codiceCatastale1P,
           java.lang.String codiceCatastale2P,
           java.lang.String tipoCatastoP,
           java.lang.String sezioneP,
           java.lang.String foglioP,
           java.lang.String particella1P,
           java.lang.String particella2P,
           java.lang.String sub1P,
           java.lang.String sub2P,
           java.lang.String tipoDenunciaP,
           java.math.BigInteger numeroDenuncia1P,
           java.lang.Short numeroDenuncia2P,
           java.lang.Short annoDenunciaP,
           java.lang.String tipoOccupanti,
           java.lang.Long numRegBeneMobileRegistrato,
           java.lang.String targa,
           java.util.Date dataRegistrazione,
           java.lang.String modelloVeicolo,
           java.lang.Long codImmatricolazioneVeicolo,
           java.lang.String telaio,
           java.lang.String coloreVeicolo,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC vincolo,
           java.lang.String descrizioneVincolo,
           java.util.Date dataScadenza,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC descOperFinanz,
           java.lang.String intestatario,
           java.lang.String numeroRegistrazione,
           java.lang.String codiceIban,
           java.math.BigDecimal valutaEst,
           java.math.BigDecimal valutaEur,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipoValuta,
           java.lang.String codiceAbi,
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC codiceCab,
           java.lang.String numBollettino,
           java.lang.Long numModelloTribunale,
           java.lang.String indirizzoBeneFin,
           java.lang.String direttoreProtempore,
           java.lang.String descrizioneCapo,
           java.lang.Long numeroAnimali,
           java.lang.String descrizioneCollezione,
           java.lang.Long numeroPezzi,
           java.lang.String descUbicazione) {
           this.idBene = idBene;
           this.versioneBene = versioneBene;
           this.descBene = descBene;
           this.tipologia = tipologia;
           this.categoria = categoria;
           this.sottocategoria = sottocategoria;
           this.ragioneSociale = ragioneSociale;
           this.settoreAttivita = settoreAttivita;
           this.stato = stato;
           this.regione = regione;
           this.provincia = provincia;
           this.comune = comune;
           this.desComuneEstero = desComuneEstero;
           this.indirizzo = indirizzo;
           this.numeroCCIAA = numeroCCIAA;
           this.numeroDipendenti = numeroDipendenti;
           this.partitaIva = partitaIva;
           this.codiceFiscale = codiceFiscale;
           this.capitaleSociale = capitaleSociale;
           this.amministratore = amministratore;
           this.gravame = gravame;
           this.scala = scala;
           this.interno = interno;
           this.piano = piano;
           this.edificio = edificio;
           this.lotto = lotto;
           this.idComuneCatastale = idComuneCatastale;
           this.codiceCatastale1 = codiceCatastale1;
           this.tipoCatasto = tipoCatasto;
           this.dataAccatastamento = dataAccatastamento;
           this.fondo = fondo;
           this.sezione = sezione;
           this.foglio = foglio;
           this.particella1 = particella1;
           this.particella2 = particella2;
           this.sub1 = sub1;
           this.sub2 = sub2;
           this.tipoDenuncia = tipoDenuncia;
           this.numeroDenuncia1 = numeroDenuncia1;
           this.numeroDenuncia2 = numeroDenuncia2;
           this.annoDenuncia = annoDenuncia;
           this.schedaAccatastamento = schedaAccatastamento;
           this.zonaCensuaria = zonaCensuaria;
           this.renditaCatastale = renditaCatastale;
           this.renditaDominicale = renditaDominicale;
           this.ettari = ettari;
           this.are = are;
           this.centiare = centiare;
           this.vaniIntero = vaniIntero;
           this.vaniDecimale = vaniDecimale;
           this.metriCubiIntero = metriCubiIntero;
           this.metriCubiDecimale = metriCubiDecimale;
           this.metriQuadriIntero = metriQuadriIntero;
           this.metriQuadriDecimale = metriQuadriDecimale;
           this.appartenenza = appartenenza;
           this.comuneCatastaleP = comuneCatastaleP;
           this.codiceCatastale1P = codiceCatastale1P;
           this.codiceCatastale2P = codiceCatastale2P;
           this.tipoCatastoP = tipoCatastoP;
           this.sezioneP = sezioneP;
           this.foglioP = foglioP;
           this.particella1P = particella1P;
           this.particella2P = particella2P;
           this.sub1P = sub1P;
           this.sub2P = sub2P;
           this.tipoDenunciaP = tipoDenunciaP;
           this.numeroDenuncia1P = numeroDenuncia1P;
           this.numeroDenuncia2P = numeroDenuncia2P;
           this.annoDenunciaP = annoDenunciaP;
           this.tipoOccupanti = tipoOccupanti;
           this.numRegBeneMobileRegistrato = numRegBeneMobileRegistrato;
           this.targa = targa;
           this.dataRegistrazione = dataRegistrazione;
           this.modelloVeicolo = modelloVeicolo;
           this.codImmatricolazioneVeicolo = codImmatricolazioneVeicolo;
           this.telaio = telaio;
           this.coloreVeicolo = coloreVeicolo;
           this.vincolo = vincolo;
           this.descrizioneVincolo = descrizioneVincolo;
           this.dataScadenza = dataScadenza;
           this.descOperFinanz = descOperFinanz;
           this.intestatario = intestatario;
           this.numeroRegistrazione = numeroRegistrazione;
           this.codiceIban = codiceIban;
           this.valutaEst = valutaEst;
           this.valutaEur = valutaEur;
           this.tipoValuta = tipoValuta;
           this.codiceAbi = codiceAbi;
           this.codiceCab = codiceCab;
           this.numBollettino = numBollettino;
           this.numModelloTribunale = numModelloTribunale;
           this.indirizzoBeneFin = indirizzoBeneFin;
           this.direttoreProtempore = direttoreProtempore;
           this.descrizioneCapo = descrizioneCapo;
           this.numeroAnimali = numeroAnimali;
           this.descrizioneCollezione = descrizioneCollezione;
           this.numeroPezzi = numeroPezzi;
           this.descUbicazione = descUbicazione;
    }


    /**
     * Gets the idBene value for this BeneBDCType.
     * 
     * @return idBene
     */
    public long getIdBene() {
        return idBene;
    }


    /**
     * Sets the idBene value for this BeneBDCType.
     * 
     * @param idBene
     */
    public void setIdBene(long idBene) {
        this.idBene = idBene;
    }


    /**
     * Gets the versioneBene value for this BeneBDCType.
     * 
     * @return versioneBene
     */
    public long getVersioneBene() {
        return versioneBene;
    }


    /**
     * Sets the versioneBene value for this BeneBDCType.
     * 
     * @param versioneBene
     */
    public void setVersioneBene(long versioneBene) {
        this.versioneBene = versioneBene;
    }


    /**
     * Gets the descBene value for this BeneBDCType.
     * 
     * @return descBene
     */
    public java.lang.String getDescBene() {
        return descBene;
    }


    /**
     * Sets the descBene value for this BeneBDCType.
     * 
     * @param descBene
     */
    public void setDescBene(java.lang.String descBene) {
        this.descBene = descBene;
    }


    /**
     * Gets the tipologia value for this BeneBDCType.
     * 
     * @return tipologia
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this BeneBDCType.
     * 
     * @param tipologia
     */
    public void setTipologia(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the categoria value for this BeneBDCType.
     * 
     * @return categoria
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this BeneBDCType.
     * 
     * @param categoria
     */
    public void setCategoria(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC categoria) {
        this.categoria = categoria;
    }


    /**
     * Gets the sottocategoria value for this BeneBDCType.
     * 
     * @return sottocategoria
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getSottocategoria() {
        return sottocategoria;
    }


    /**
     * Sets the sottocategoria value for this BeneBDCType.
     * 
     * @param sottocategoria
     */
    public void setSottocategoria(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC sottocategoria) {
        this.sottocategoria = sottocategoria;
    }


    /**
     * Gets the ragioneSociale value for this BeneBDCType.
     * 
     * @return ragioneSociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this BeneBDCType.
     * 
     * @param ragioneSociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the settoreAttivita value for this BeneBDCType.
     * 
     * @return settoreAttivita
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getSettoreAttivita() {
        return settoreAttivita;
    }


    /**
     * Sets the settoreAttivita value for this BeneBDCType.
     * 
     * @param settoreAttivita
     */
    public void setSettoreAttivita(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC settoreAttivita) {
        this.settoreAttivita = settoreAttivita;
    }


    /**
     * Gets the stato value for this BeneBDCType.
     * 
     * @return stato
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this BeneBDCType.
     * 
     * @param stato
     */
    public void setStato(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC stato) {
        this.stato = stato;
    }


    /**
     * Gets the regione value for this BeneBDCType.
     * 
     * @return regione
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getRegione() {
        return regione;
    }


    /**
     * Sets the regione value for this BeneBDCType.
     * 
     * @param regione
     */
    public void setRegione(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC regione) {
        this.regione = regione;
    }


    /**
     * Gets the provincia value for this BeneBDCType.
     * 
     * @return provincia
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this BeneBDCType.
     * 
     * @param provincia
     */
    public void setProvincia(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the comune value for this BeneBDCType.
     * 
     * @return comune
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this BeneBDCType.
     * 
     * @param comune
     */
    public void setComune(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comune) {
        this.comune = comune;
    }


    /**
     * Gets the desComuneEstero value for this BeneBDCType.
     * 
     * @return desComuneEstero
     */
    public java.lang.String getDesComuneEstero() {
        return desComuneEstero;
    }


    /**
     * Sets the desComuneEstero value for this BeneBDCType.
     * 
     * @param desComuneEstero
     */
    public void setDesComuneEstero(java.lang.String desComuneEstero) {
        this.desComuneEstero = desComuneEstero;
    }


    /**
     * Gets the indirizzo value for this BeneBDCType.
     * 
     * @return indirizzo
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.IndirizzoFormBeneBDC getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this BeneBDCType.
     * 
     * @param indirizzo
     */
    public void setIndirizzo(it.giustizia.smp.webservice.client.bdc.ricerca.bene.IndirizzoFormBeneBDC indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the numeroCCIAA value for this BeneBDCType.
     * 
     * @return numeroCCIAA
     */
    public java.lang.String getNumeroCCIAA() {
        return numeroCCIAA;
    }


    /**
     * Sets the numeroCCIAA value for this BeneBDCType.
     * 
     * @param numeroCCIAA
     */
    public void setNumeroCCIAA(java.lang.String numeroCCIAA) {
        this.numeroCCIAA = numeroCCIAA;
    }


    /**
     * Gets the numeroDipendenti value for this BeneBDCType.
     * 
     * @return numeroDipendenti
     */
    public java.lang.String getNumeroDipendenti() {
        return numeroDipendenti;
    }


    /**
     * Sets the numeroDipendenti value for this BeneBDCType.
     * 
     * @param numeroDipendenti
     */
    public void setNumeroDipendenti(java.lang.String numeroDipendenti) {
        this.numeroDipendenti = numeroDipendenti;
    }


    /**
     * Gets the partitaIva value for this BeneBDCType.
     * 
     * @return partitaIva
     */
    public java.lang.String getPartitaIva() {
        return partitaIva;
    }


    /**
     * Sets the partitaIva value for this BeneBDCType.
     * 
     * @param partitaIva
     */
    public void setPartitaIva(java.lang.String partitaIva) {
        this.partitaIva = partitaIva;
    }


    /**
     * Gets the codiceFiscale value for this BeneBDCType.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this BeneBDCType.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the capitaleSociale value for this BeneBDCType.
     * 
     * @return capitaleSociale
     */
    public java.math.BigDecimal getCapitaleSociale() {
        return capitaleSociale;
    }


    /**
     * Sets the capitaleSociale value for this BeneBDCType.
     * 
     * @param capitaleSociale
     */
    public void setCapitaleSociale(java.math.BigDecimal capitaleSociale) {
        this.capitaleSociale = capitaleSociale;
    }


    /**
     * Gets the amministratore value for this BeneBDCType.
     * 
     * @return amministratore
     */
    public java.lang.String getAmministratore() {
        return amministratore;
    }


    /**
     * Sets the amministratore value for this BeneBDCType.
     * 
     * @param amministratore
     */
    public void setAmministratore(java.lang.String amministratore) {
        this.amministratore = amministratore;
    }


    /**
     * Gets the gravame value for this BeneBDCType.
     * 
     * @return gravame
     */
    public java.lang.String getGravame() {
        return gravame;
    }


    /**
     * Sets the gravame value for this BeneBDCType.
     * 
     * @param gravame
     */
    public void setGravame(java.lang.String gravame) {
        this.gravame = gravame;
    }


    /**
     * Gets the scala value for this BeneBDCType.
     * 
     * @return scala
     */
    public java.lang.String getScala() {
        return scala;
    }


    /**
     * Sets the scala value for this BeneBDCType.
     * 
     * @param scala
     */
    public void setScala(java.lang.String scala) {
        this.scala = scala;
    }


    /**
     * Gets the interno value for this BeneBDCType.
     * 
     * @return interno
     */
    public java.lang.String getInterno() {
        return interno;
    }


    /**
     * Sets the interno value for this BeneBDCType.
     * 
     * @param interno
     */
    public void setInterno(java.lang.String interno) {
        this.interno = interno;
    }


    /**
     * Gets the piano value for this BeneBDCType.
     * 
     * @return piano
     */
    public java.lang.String getPiano() {
        return piano;
    }


    /**
     * Sets the piano value for this BeneBDCType.
     * 
     * @param piano
     */
    public void setPiano(java.lang.String piano) {
        this.piano = piano;
    }


    /**
     * Gets the edificio value for this BeneBDCType.
     * 
     * @return edificio
     */
    public java.lang.String getEdificio() {
        return edificio;
    }


    /**
     * Sets the edificio value for this BeneBDCType.
     * 
     * @param edificio
     */
    public void setEdificio(java.lang.String edificio) {
        this.edificio = edificio;
    }


    /**
     * Gets the lotto value for this BeneBDCType.
     * 
     * @return lotto
     */
    public java.lang.String getLotto() {
        return lotto;
    }


    /**
     * Sets the lotto value for this BeneBDCType.
     * 
     * @param lotto
     */
    public void setLotto(java.lang.String lotto) {
        this.lotto = lotto;
    }


    /**
     * Gets the idComuneCatastale value for this BeneBDCType.
     * 
     * @return idComuneCatastale
     */
    public long getIdComuneCatastale() {
        return idComuneCatastale;
    }


    /**
     * Sets the idComuneCatastale value for this BeneBDCType.
     * 
     * @param idComuneCatastale
     */
    public void setIdComuneCatastale(long idComuneCatastale) {
        this.idComuneCatastale = idComuneCatastale;
    }


    /**
     * Gets the codiceCatastale1 value for this BeneBDCType.
     * 
     * @return codiceCatastale1
     */
    public java.lang.String getCodiceCatastale1() {
        return codiceCatastale1;
    }


    /**
     * Sets the codiceCatastale1 value for this BeneBDCType.
     * 
     * @param codiceCatastale1
     */
    public void setCodiceCatastale1(java.lang.String codiceCatastale1) {
        this.codiceCatastale1 = codiceCatastale1;
    }


    /**
     * Gets the tipoCatasto value for this BeneBDCType.
     * 
     * @return tipoCatasto
     */
    public java.lang.String getTipoCatasto() {
        return tipoCatasto;
    }


    /**
     * Sets the tipoCatasto value for this BeneBDCType.
     * 
     * @param tipoCatasto
     */
    public void setTipoCatasto(java.lang.String tipoCatasto) {
        this.tipoCatasto = tipoCatasto;
    }


    /**
     * Gets the dataAccatastamento value for this BeneBDCType.
     * 
     * @return dataAccatastamento
     */
    public java.util.Date getDataAccatastamento() {
        return dataAccatastamento;
    }


    /**
     * Sets the dataAccatastamento value for this BeneBDCType.
     * 
     * @param dataAccatastamento
     */
    public void setDataAccatastamento(java.util.Date dataAccatastamento) {
        this.dataAccatastamento = dataAccatastamento;
    }


    /**
     * Gets the fondo value for this BeneBDCType.
     * 
     * @return fondo
     */
    public java.lang.String getFondo() {
        return fondo;
    }


    /**
     * Sets the fondo value for this BeneBDCType.
     * 
     * @param fondo
     */
    public void setFondo(java.lang.String fondo) {
        this.fondo = fondo;
    }


    /**
     * Gets the sezione value for this BeneBDCType.
     * 
     * @return sezione
     */
    public java.lang.String getSezione() {
        return sezione;
    }


    /**
     * Sets the sezione value for this BeneBDCType.
     * 
     * @param sezione
     */
    public void setSezione(java.lang.String sezione) {
        this.sezione = sezione;
    }


    /**
     * Gets the foglio value for this BeneBDCType.
     * 
     * @return foglio
     */
    public java.lang.String getFoglio() {
        return foglio;
    }


    /**
     * Sets the foglio value for this BeneBDCType.
     * 
     * @param foglio
     */
    public void setFoglio(java.lang.String foglio) {
        this.foglio = foglio;
    }


    /**
     * Gets the particella1 value for this BeneBDCType.
     * 
     * @return particella1
     */
    public java.lang.String getParticella1() {
        return particella1;
    }


    /**
     * Sets the particella1 value for this BeneBDCType.
     * 
     * @param particella1
     */
    public void setParticella1(java.lang.String particella1) {
        this.particella1 = particella1;
    }


    /**
     * Gets the particella2 value for this BeneBDCType.
     * 
     * @return particella2
     */
    public java.lang.String getParticella2() {
        return particella2;
    }


    /**
     * Sets the particella2 value for this BeneBDCType.
     * 
     * @param particella2
     */
    public void setParticella2(java.lang.String particella2) {
        this.particella2 = particella2;
    }


    /**
     * Gets the sub1 value for this BeneBDCType.
     * 
     * @return sub1
     */
    public java.lang.String getSub1() {
        return sub1;
    }


    /**
     * Sets the sub1 value for this BeneBDCType.
     * 
     * @param sub1
     */
    public void setSub1(java.lang.String sub1) {
        this.sub1 = sub1;
    }


    /**
     * Gets the sub2 value for this BeneBDCType.
     * 
     * @return sub2
     */
    public java.lang.String getSub2() {
        return sub2;
    }


    /**
     * Sets the sub2 value for this BeneBDCType.
     * 
     * @param sub2
     */
    public void setSub2(java.lang.String sub2) {
        this.sub2 = sub2;
    }


    /**
     * Gets the tipoDenuncia value for this BeneBDCType.
     * 
     * @return tipoDenuncia
     */
    public java.lang.String getTipoDenuncia() {
        return tipoDenuncia;
    }


    /**
     * Sets the tipoDenuncia value for this BeneBDCType.
     * 
     * @param tipoDenuncia
     */
    public void setTipoDenuncia(java.lang.String tipoDenuncia) {
        this.tipoDenuncia = tipoDenuncia;
    }


    /**
     * Gets the numeroDenuncia1 value for this BeneBDCType.
     * 
     * @return numeroDenuncia1
     */
    public java.math.BigInteger getNumeroDenuncia1() {
        return numeroDenuncia1;
    }


    /**
     * Sets the numeroDenuncia1 value for this BeneBDCType.
     * 
     * @param numeroDenuncia1
     */
    public void setNumeroDenuncia1(java.math.BigInteger numeroDenuncia1) {
        this.numeroDenuncia1 = numeroDenuncia1;
    }


    /**
     * Gets the numeroDenuncia2 value for this BeneBDCType.
     * 
     * @return numeroDenuncia2
     */
    public java.lang.Short getNumeroDenuncia2() {
        return numeroDenuncia2;
    }


    /**
     * Sets the numeroDenuncia2 value for this BeneBDCType.
     * 
     * @param numeroDenuncia2
     */
    public void setNumeroDenuncia2(java.lang.Short numeroDenuncia2) {
        this.numeroDenuncia2 = numeroDenuncia2;
    }


    /**
     * Gets the annoDenuncia value for this BeneBDCType.
     * 
     * @return annoDenuncia
     */
    public java.lang.Short getAnnoDenuncia() {
        return annoDenuncia;
    }


    /**
     * Sets the annoDenuncia value for this BeneBDCType.
     * 
     * @param annoDenuncia
     */
    public void setAnnoDenuncia(java.lang.Short annoDenuncia) {
        this.annoDenuncia = annoDenuncia;
    }


    /**
     * Gets the schedaAccatastamento value for this BeneBDCType.
     * 
     * @return schedaAccatastamento
     */
    public java.lang.String getSchedaAccatastamento() {
        return schedaAccatastamento;
    }


    /**
     * Sets the schedaAccatastamento value for this BeneBDCType.
     * 
     * @param schedaAccatastamento
     */
    public void setSchedaAccatastamento(java.lang.String schedaAccatastamento) {
        this.schedaAccatastamento = schedaAccatastamento;
    }


    /**
     * Gets the zonaCensuaria value for this BeneBDCType.
     * 
     * @return zonaCensuaria
     */
    public java.lang.Long getZonaCensuaria() {
        return zonaCensuaria;
    }


    /**
     * Sets the zonaCensuaria value for this BeneBDCType.
     * 
     * @param zonaCensuaria
     */
    public void setZonaCensuaria(java.lang.Long zonaCensuaria) {
        this.zonaCensuaria = zonaCensuaria;
    }


    /**
     * Gets the renditaCatastale value for this BeneBDCType.
     * 
     * @return renditaCatastale
     */
    public java.math.BigDecimal getRenditaCatastale() {
        return renditaCatastale;
    }


    /**
     * Sets the renditaCatastale value for this BeneBDCType.
     * 
     * @param renditaCatastale
     */
    public void setRenditaCatastale(java.math.BigDecimal renditaCatastale) {
        this.renditaCatastale = renditaCatastale;
    }


    /**
     * Gets the renditaDominicale value for this BeneBDCType.
     * 
     * @return renditaDominicale
     */
    public java.math.BigDecimal getRenditaDominicale() {
        return renditaDominicale;
    }


    /**
     * Sets the renditaDominicale value for this BeneBDCType.
     * 
     * @param renditaDominicale
     */
    public void setRenditaDominicale(java.math.BigDecimal renditaDominicale) {
        this.renditaDominicale = renditaDominicale;
    }


    /**
     * Gets the ettari value for this BeneBDCType.
     * 
     * @return ettari
     */
    public java.math.BigInteger getEttari() {
        return ettari;
    }


    /**
     * Sets the ettari value for this BeneBDCType.
     * 
     * @param ettari
     */
    public void setEttari(java.math.BigInteger ettari) {
        this.ettari = ettari;
    }


    /**
     * Gets the are value for this BeneBDCType.
     * 
     * @return are
     */
    public java.math.BigInteger getAre() {
        return are;
    }


    /**
     * Sets the are value for this BeneBDCType.
     * 
     * @param are
     */
    public void setAre(java.math.BigInteger are) {
        this.are = are;
    }


    /**
     * Gets the centiare value for this BeneBDCType.
     * 
     * @return centiare
     */
    public java.math.BigInteger getCentiare() {
        return centiare;
    }


    /**
     * Sets the centiare value for this BeneBDCType.
     * 
     * @param centiare
     */
    public void setCentiare(java.math.BigInteger centiare) {
        this.centiare = centiare;
    }


    /**
     * Gets the vaniIntero value for this BeneBDCType.
     * 
     * @return vaniIntero
     */
    public java.lang.Short getVaniIntero() {
        return vaniIntero;
    }


    /**
     * Sets the vaniIntero value for this BeneBDCType.
     * 
     * @param vaniIntero
     */
    public void setVaniIntero(java.lang.Short vaniIntero) {
        this.vaniIntero = vaniIntero;
    }


    /**
     * Gets the vaniDecimale value for this BeneBDCType.
     * 
     * @return vaniDecimale
     */
    public java.lang.Short getVaniDecimale() {
        return vaniDecimale;
    }


    /**
     * Sets the vaniDecimale value for this BeneBDCType.
     * 
     * @param vaniDecimale
     */
    public void setVaniDecimale(java.lang.Short vaniDecimale) {
        this.vaniDecimale = vaniDecimale;
    }


    /**
     * Gets the metriCubiIntero value for this BeneBDCType.
     * 
     * @return metriCubiIntero
     */
    public java.lang.Short getMetriCubiIntero() {
        return metriCubiIntero;
    }


    /**
     * Sets the metriCubiIntero value for this BeneBDCType.
     * 
     * @param metriCubiIntero
     */
    public void setMetriCubiIntero(java.lang.Short metriCubiIntero) {
        this.metriCubiIntero = metriCubiIntero;
    }


    /**
     * Gets the metriCubiDecimale value for this BeneBDCType.
     * 
     * @return metriCubiDecimale
     */
    public java.lang.Short getMetriCubiDecimale() {
        return metriCubiDecimale;
    }


    /**
     * Sets the metriCubiDecimale value for this BeneBDCType.
     * 
     * @param metriCubiDecimale
     */
    public void setMetriCubiDecimale(java.lang.Short metriCubiDecimale) {
        this.metriCubiDecimale = metriCubiDecimale;
    }


    /**
     * Gets the metriQuadriIntero value for this BeneBDCType.
     * 
     * @return metriQuadriIntero
     */
    public java.lang.Short getMetriQuadriIntero() {
        return metriQuadriIntero;
    }


    /**
     * Sets the metriQuadriIntero value for this BeneBDCType.
     * 
     * @param metriQuadriIntero
     */
    public void setMetriQuadriIntero(java.lang.Short metriQuadriIntero) {
        this.metriQuadriIntero = metriQuadriIntero;
    }


    /**
     * Gets the metriQuadriDecimale value for this BeneBDCType.
     * 
     * @return metriQuadriDecimale
     */
    public java.lang.Byte getMetriQuadriDecimale() {
        return metriQuadriDecimale;
    }


    /**
     * Sets the metriQuadriDecimale value for this BeneBDCType.
     * 
     * @param metriQuadriDecimale
     */
    public void setMetriQuadriDecimale(java.lang.Byte metriQuadriDecimale) {
        this.metriQuadriDecimale = metriQuadriDecimale;
    }


    /**
     * Gets the appartenenza value for this BeneBDCType.
     * 
     * @return appartenenza
     */
    public java.lang.String getAppartenenza() {
        return appartenenza;
    }


    /**
     * Sets the appartenenza value for this BeneBDCType.
     * 
     * @param appartenenza
     */
    public void setAppartenenza(java.lang.String appartenenza) {
        this.appartenenza = appartenenza;
    }


    /**
     * Gets the comuneCatastaleP value for this BeneBDCType.
     * 
     * @return comuneCatastaleP
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getComuneCatastaleP() {
        return comuneCatastaleP;
    }


    /**
     * Sets the comuneCatastaleP value for this BeneBDCType.
     * 
     * @param comuneCatastaleP
     */
    public void setComuneCatastaleP(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC comuneCatastaleP) {
        this.comuneCatastaleP = comuneCatastaleP;
    }


    /**
     * Gets the codiceCatastale1P value for this BeneBDCType.
     * 
     * @return codiceCatastale1P
     */
    public java.lang.String getCodiceCatastale1P() {
        return codiceCatastale1P;
    }


    /**
     * Sets the codiceCatastale1P value for this BeneBDCType.
     * 
     * @param codiceCatastale1P
     */
    public void setCodiceCatastale1P(java.lang.String codiceCatastale1P) {
        this.codiceCatastale1P = codiceCatastale1P;
    }


    /**
     * Gets the codiceCatastale2P value for this BeneBDCType.
     * 
     * @return codiceCatastale2P
     */
    public java.lang.String getCodiceCatastale2P() {
        return codiceCatastale2P;
    }


    /**
     * Sets the codiceCatastale2P value for this BeneBDCType.
     * 
     * @param codiceCatastale2P
     */
    public void setCodiceCatastale2P(java.lang.String codiceCatastale2P) {
        this.codiceCatastale2P = codiceCatastale2P;
    }


    /**
     * Gets the tipoCatastoP value for this BeneBDCType.
     * 
     * @return tipoCatastoP
     */
    public java.lang.String getTipoCatastoP() {
        return tipoCatastoP;
    }


    /**
     * Sets the tipoCatastoP value for this BeneBDCType.
     * 
     * @param tipoCatastoP
     */
    public void setTipoCatastoP(java.lang.String tipoCatastoP) {
        this.tipoCatastoP = tipoCatastoP;
    }


    /**
     * Gets the sezioneP value for this BeneBDCType.
     * 
     * @return sezioneP
     */
    public java.lang.String getSezioneP() {
        return sezioneP;
    }


    /**
     * Sets the sezioneP value for this BeneBDCType.
     * 
     * @param sezioneP
     */
    public void setSezioneP(java.lang.String sezioneP) {
        this.sezioneP = sezioneP;
    }


    /**
     * Gets the foglioP value for this BeneBDCType.
     * 
     * @return foglioP
     */
    public java.lang.String getFoglioP() {
        return foglioP;
    }


    /**
     * Sets the foglioP value for this BeneBDCType.
     * 
     * @param foglioP
     */
    public void setFoglioP(java.lang.String foglioP) {
        this.foglioP = foglioP;
    }


    /**
     * Gets the particella1P value for this BeneBDCType.
     * 
     * @return particella1P
     */
    public java.lang.String getParticella1P() {
        return particella1P;
    }


    /**
     * Sets the particella1P value for this BeneBDCType.
     * 
     * @param particella1P
     */
    public void setParticella1P(java.lang.String particella1P) {
        this.particella1P = particella1P;
    }


    /**
     * Gets the particella2P value for this BeneBDCType.
     * 
     * @return particella2P
     */
    public java.lang.String getParticella2P() {
        return particella2P;
    }


    /**
     * Sets the particella2P value for this BeneBDCType.
     * 
     * @param particella2P
     */
    public void setParticella2P(java.lang.String particella2P) {
        this.particella2P = particella2P;
    }


    /**
     * Gets the sub1P value for this BeneBDCType.
     * 
     * @return sub1P
     */
    public java.lang.String getSub1P() {
        return sub1P;
    }


    /**
     * Sets the sub1P value for this BeneBDCType.
     * 
     * @param sub1P
     */
    public void setSub1P(java.lang.String sub1P) {
        this.sub1P = sub1P;
    }


    /**
     * Gets the sub2P value for this BeneBDCType.
     * 
     * @return sub2P
     */
    public java.lang.String getSub2P() {
        return sub2P;
    }


    /**
     * Sets the sub2P value for this BeneBDCType.
     * 
     * @param sub2P
     */
    public void setSub2P(java.lang.String sub2P) {
        this.sub2P = sub2P;
    }


    /**
     * Gets the tipoDenunciaP value for this BeneBDCType.
     * 
     * @return tipoDenunciaP
     */
    public java.lang.String getTipoDenunciaP() {
        return tipoDenunciaP;
    }


    /**
     * Sets the tipoDenunciaP value for this BeneBDCType.
     * 
     * @param tipoDenunciaP
     */
    public void setTipoDenunciaP(java.lang.String tipoDenunciaP) {
        this.tipoDenunciaP = tipoDenunciaP;
    }


    /**
     * Gets the numeroDenuncia1P value for this BeneBDCType.
     * 
     * @return numeroDenuncia1P
     */
    public java.math.BigInteger getNumeroDenuncia1P() {
        return numeroDenuncia1P;
    }


    /**
     * Sets the numeroDenuncia1P value for this BeneBDCType.
     * 
     * @param numeroDenuncia1P
     */
    public void setNumeroDenuncia1P(java.math.BigInteger numeroDenuncia1P) {
        this.numeroDenuncia1P = numeroDenuncia1P;
    }


    /**
     * Gets the numeroDenuncia2P value for this BeneBDCType.
     * 
     * @return numeroDenuncia2P
     */
    public java.lang.Short getNumeroDenuncia2P() {
        return numeroDenuncia2P;
    }


    /**
     * Sets the numeroDenuncia2P value for this BeneBDCType.
     * 
     * @param numeroDenuncia2P
     */
    public void setNumeroDenuncia2P(java.lang.Short numeroDenuncia2P) {
        this.numeroDenuncia2P = numeroDenuncia2P;
    }


    /**
     * Gets the annoDenunciaP value for this BeneBDCType.
     * 
     * @return annoDenunciaP
     */
    public java.lang.Short getAnnoDenunciaP() {
        return annoDenunciaP;
    }


    /**
     * Sets the annoDenunciaP value for this BeneBDCType.
     * 
     * @param annoDenunciaP
     */
    public void setAnnoDenunciaP(java.lang.Short annoDenunciaP) {
        this.annoDenunciaP = annoDenunciaP;
    }


    /**
     * Gets the tipoOccupanti value for this BeneBDCType.
     * 
     * @return tipoOccupanti
     */
    public java.lang.String getTipoOccupanti() {
        return tipoOccupanti;
    }


    /**
     * Sets the tipoOccupanti value for this BeneBDCType.
     * 
     * @param tipoOccupanti
     */
    public void setTipoOccupanti(java.lang.String tipoOccupanti) {
        this.tipoOccupanti = tipoOccupanti;
    }


    /**
     * Gets the numRegBeneMobileRegistrato value for this BeneBDCType.
     * 
     * @return numRegBeneMobileRegistrato
     */
    public java.lang.Long getNumRegBeneMobileRegistrato() {
        return numRegBeneMobileRegistrato;
    }


    /**
     * Sets the numRegBeneMobileRegistrato value for this BeneBDCType.
     * 
     * @param numRegBeneMobileRegistrato
     */
    public void setNumRegBeneMobileRegistrato(java.lang.Long numRegBeneMobileRegistrato) {
        this.numRegBeneMobileRegistrato = numRegBeneMobileRegistrato;
    }


    /**
     * Gets the targa value for this BeneBDCType.
     * 
     * @return targa
     */
    public java.lang.String getTarga() {
        return targa;
    }


    /**
     * Sets the targa value for this BeneBDCType.
     * 
     * @param targa
     */
    public void setTarga(java.lang.String targa) {
        this.targa = targa;
    }


    /**
     * Gets the dataRegistrazione value for this BeneBDCType.
     * 
     * @return dataRegistrazione
     */
    public java.util.Date getDataRegistrazione() {
        return dataRegistrazione;
    }


    /**
     * Sets the dataRegistrazione value for this BeneBDCType.
     * 
     * @param dataRegistrazione
     */
    public void setDataRegistrazione(java.util.Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }


    /**
     * Gets the modelloVeicolo value for this BeneBDCType.
     * 
     * @return modelloVeicolo
     */
    public java.lang.String getModelloVeicolo() {
        return modelloVeicolo;
    }


    /**
     * Sets the modelloVeicolo value for this BeneBDCType.
     * 
     * @param modelloVeicolo
     */
    public void setModelloVeicolo(java.lang.String modelloVeicolo) {
        this.modelloVeicolo = modelloVeicolo;
    }


    /**
     * Gets the codImmatricolazioneVeicolo value for this BeneBDCType.
     * 
     * @return codImmatricolazioneVeicolo
     */
    public java.lang.Long getCodImmatricolazioneVeicolo() {
        return codImmatricolazioneVeicolo;
    }


    /**
     * Sets the codImmatricolazioneVeicolo value for this BeneBDCType.
     * 
     * @param codImmatricolazioneVeicolo
     */
    public void setCodImmatricolazioneVeicolo(java.lang.Long codImmatricolazioneVeicolo) {
        this.codImmatricolazioneVeicolo = codImmatricolazioneVeicolo;
    }


    /**
     * Gets the telaio value for this BeneBDCType.
     * 
     * @return telaio
     */
    public java.lang.String getTelaio() {
        return telaio;
    }


    /**
     * Sets the telaio value for this BeneBDCType.
     * 
     * @param telaio
     */
    public void setTelaio(java.lang.String telaio) {
        this.telaio = telaio;
    }


    /**
     * Gets the coloreVeicolo value for this BeneBDCType.
     * 
     * @return coloreVeicolo
     */
    public java.lang.String getColoreVeicolo() {
        return coloreVeicolo;
    }


    /**
     * Sets the coloreVeicolo value for this BeneBDCType.
     * 
     * @param coloreVeicolo
     */
    public void setColoreVeicolo(java.lang.String coloreVeicolo) {
        this.coloreVeicolo = coloreVeicolo;
    }


    /**
     * Gets the vincolo value for this BeneBDCType.
     * 
     * @return vincolo
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getVincolo() {
        return vincolo;
    }


    /**
     * Sets the vincolo value for this BeneBDCType.
     * 
     * @param vincolo
     */
    public void setVincolo(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC vincolo) {
        this.vincolo = vincolo;
    }


    /**
     * Gets the descrizioneVincolo value for this BeneBDCType.
     * 
     * @return descrizioneVincolo
     */
    public java.lang.String getDescrizioneVincolo() {
        return descrizioneVincolo;
    }


    /**
     * Sets the descrizioneVincolo value for this BeneBDCType.
     * 
     * @param descrizioneVincolo
     */
    public void setDescrizioneVincolo(java.lang.String descrizioneVincolo) {
        this.descrizioneVincolo = descrizioneVincolo;
    }


    /**
     * Gets the dataScadenza value for this BeneBDCType.
     * 
     * @return dataScadenza
     */
    public java.util.Date getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this BeneBDCType.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the descOperFinanz value for this BeneBDCType.
     * 
     * @return descOperFinanz
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getDescOperFinanz() {
        return descOperFinanz;
    }


    /**
     * Sets the descOperFinanz value for this BeneBDCType.
     * 
     * @param descOperFinanz
     */
    public void setDescOperFinanz(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC descOperFinanz) {
        this.descOperFinanz = descOperFinanz;
    }


    /**
     * Gets the intestatario value for this BeneBDCType.
     * 
     * @return intestatario
     */
    public java.lang.String getIntestatario() {
        return intestatario;
    }


    /**
     * Sets the intestatario value for this BeneBDCType.
     * 
     * @param intestatario
     */
    public void setIntestatario(java.lang.String intestatario) {
        this.intestatario = intestatario;
    }


    /**
     * Gets the numeroRegistrazione value for this BeneBDCType.
     * 
     * @return numeroRegistrazione
     */
    public java.lang.String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }


    /**
     * Sets the numeroRegistrazione value for this BeneBDCType.
     * 
     * @param numeroRegistrazione
     */
    public void setNumeroRegistrazione(java.lang.String numeroRegistrazione) {
        this.numeroRegistrazione = numeroRegistrazione;
    }


    /**
     * Gets the codiceIban value for this BeneBDCType.
     * 
     * @return codiceIban
     */
    public java.lang.String getCodiceIban() {
        return codiceIban;
    }


    /**
     * Sets the codiceIban value for this BeneBDCType.
     * 
     * @param codiceIban
     */
    public void setCodiceIban(java.lang.String codiceIban) {
        this.codiceIban = codiceIban;
    }


    /**
     * Gets the valutaEst value for this BeneBDCType.
     * 
     * @return valutaEst
     */
    public java.math.BigDecimal getValutaEst() {
        return valutaEst;
    }


    /**
     * Sets the valutaEst value for this BeneBDCType.
     * 
     * @param valutaEst
     */
    public void setValutaEst(java.math.BigDecimal valutaEst) {
        this.valutaEst = valutaEst;
    }


    /**
     * Gets the valutaEur value for this BeneBDCType.
     * 
     * @return valutaEur
     */
    public java.math.BigDecimal getValutaEur() {
        return valutaEur;
    }


    /**
     * Sets the valutaEur value for this BeneBDCType.
     * 
     * @param valutaEur
     */
    public void setValutaEur(java.math.BigDecimal valutaEur) {
        this.valutaEur = valutaEur;
    }


    /**
     * Gets the tipoValuta value for this BeneBDCType.
     * 
     * @return tipoValuta
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getTipoValuta() {
        return tipoValuta;
    }


    /**
     * Sets the tipoValuta value for this BeneBDCType.
     * 
     * @param tipoValuta
     */
    public void setTipoValuta(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC tipoValuta) {
        this.tipoValuta = tipoValuta;
    }


    /**
     * Gets the codiceAbi value for this BeneBDCType.
     * 
     * @return codiceAbi
     */
    public java.lang.String getCodiceAbi() {
        return codiceAbi;
    }


    /**
     * Sets the codiceAbi value for this BeneBDCType.
     * 
     * @param codiceAbi
     */
    public void setCodiceAbi(java.lang.String codiceAbi) {
        this.codiceAbi = codiceAbi;
    }


    /**
     * Gets the codiceCab value for this BeneBDCType.
     * 
     * @return codiceCab
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getCodiceCab() {
        return codiceCab;
    }


    /**
     * Sets the codiceCab value for this BeneBDCType.
     * 
     * @param codiceCab
     */
    public void setCodiceCab(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC codiceCab) {
        this.codiceCab = codiceCab;
    }


    /**
     * Gets the numBollettino value for this BeneBDCType.
     * 
     * @return numBollettino
     */
    public java.lang.String getNumBollettino() {
        return numBollettino;
    }


    /**
     * Sets the numBollettino value for this BeneBDCType.
     * 
     * @param numBollettino
     */
    public void setNumBollettino(java.lang.String numBollettino) {
        this.numBollettino = numBollettino;
    }


    /**
     * Gets the numModelloTribunale value for this BeneBDCType.
     * 
     * @return numModelloTribunale
     */
    public java.lang.Long getNumModelloTribunale() {
        return numModelloTribunale;
    }


    /**
     * Sets the numModelloTribunale value for this BeneBDCType.
     * 
     * @param numModelloTribunale
     */
    public void setNumModelloTribunale(java.lang.Long numModelloTribunale) {
        this.numModelloTribunale = numModelloTribunale;
    }


    /**
     * Gets the indirizzoBeneFin value for this BeneBDCType.
     * 
     * @return indirizzoBeneFin
     */
    public java.lang.String getIndirizzoBeneFin() {
        return indirizzoBeneFin;
    }


    /**
     * Sets the indirizzoBeneFin value for this BeneBDCType.
     * 
     * @param indirizzoBeneFin
     */
    public void setIndirizzoBeneFin(java.lang.String indirizzoBeneFin) {
        this.indirizzoBeneFin = indirizzoBeneFin;
    }


    /**
     * Gets the direttoreProtempore value for this BeneBDCType.
     * 
     * @return direttoreProtempore
     */
    public java.lang.String getDirettoreProtempore() {
        return direttoreProtempore;
    }


    /**
     * Sets the direttoreProtempore value for this BeneBDCType.
     * 
     * @param direttoreProtempore
     */
    public void setDirettoreProtempore(java.lang.String direttoreProtempore) {
        this.direttoreProtempore = direttoreProtempore;
    }


    /**
     * Gets the descrizioneCapo value for this BeneBDCType.
     * 
     * @return descrizioneCapo
     */
    public java.lang.String getDescrizioneCapo() {
        return descrizioneCapo;
    }


    /**
     * Sets the descrizioneCapo value for this BeneBDCType.
     * 
     * @param descrizioneCapo
     */
    public void setDescrizioneCapo(java.lang.String descrizioneCapo) {
        this.descrizioneCapo = descrizioneCapo;
    }


    /**
     * Gets the numeroAnimali value for this BeneBDCType.
     * 
     * @return numeroAnimali
     */
    public java.lang.Long getNumeroAnimali() {
        return numeroAnimali;
    }


    /**
     * Sets the numeroAnimali value for this BeneBDCType.
     * 
     * @param numeroAnimali
     */
    public void setNumeroAnimali(java.lang.Long numeroAnimali) {
        this.numeroAnimali = numeroAnimali;
    }


    /**
     * Gets the descrizioneCollezione value for this BeneBDCType.
     * 
     * @return descrizioneCollezione
     */
    public java.lang.String getDescrizioneCollezione() {
        return descrizioneCollezione;
    }


    /**
     * Sets the descrizioneCollezione value for this BeneBDCType.
     * 
     * @param descrizioneCollezione
     */
    public void setDescrizioneCollezione(java.lang.String descrizioneCollezione) {
        this.descrizioneCollezione = descrizioneCollezione;
    }


    /**
     * Gets the numeroPezzi value for this BeneBDCType.
     * 
     * @return numeroPezzi
     */
    public java.lang.Long getNumeroPezzi() {
        return numeroPezzi;
    }


    /**
     * Sets the numeroPezzi value for this BeneBDCType.
     * 
     * @param numeroPezzi
     */
    public void setNumeroPezzi(java.lang.Long numeroPezzi) {
        this.numeroPezzi = numeroPezzi;
    }


    /**
     * Gets the descUbicazione value for this BeneBDCType.
     * 
     * @return descUbicazione
     */
    public java.lang.String getDescUbicazione() {
        return descUbicazione;
    }


    /**
     * Sets the descUbicazione value for this BeneBDCType.
     * 
     * @param descUbicazione
     */
    public void setDescUbicazione(java.lang.String descUbicazione) {
        this.descUbicazione = descUbicazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BeneBDC)) return false;
        BeneBDC other = (BeneBDC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idBene == other.getIdBene() &&
            this.versioneBene == other.getVersioneBene() &&
            ((this.descBene==null && other.getDescBene()==null) || 
             (this.descBene!=null &&
              this.descBene.equals(other.getDescBene()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria()))) &&
            ((this.sottocategoria==null && other.getSottocategoria()==null) || 
             (this.sottocategoria!=null &&
              this.sottocategoria.equals(other.getSottocategoria()))) &&
            ((this.ragioneSociale==null && other.getRagioneSociale()==null) || 
             (this.ragioneSociale!=null &&
              this.ragioneSociale.equals(other.getRagioneSociale()))) &&
            ((this.settoreAttivita==null && other.getSettoreAttivita()==null) || 
             (this.settoreAttivita!=null &&
              this.settoreAttivita.equals(other.getSettoreAttivita()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.regione==null && other.getRegione()==null) || 
             (this.regione!=null &&
              this.regione.equals(other.getRegione()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.desComuneEstero==null && other.getDesComuneEstero()==null) || 
             (this.desComuneEstero!=null &&
              this.desComuneEstero.equals(other.getDesComuneEstero()))) &&
            ((this.indirizzo==null && other.getIndirizzo()==null) || 
             (this.indirizzo!=null &&
              this.indirizzo.equals(other.getIndirizzo()))) &&
            ((this.numeroCCIAA==null && other.getNumeroCCIAA()==null) || 
             (this.numeroCCIAA!=null &&
              this.numeroCCIAA.equals(other.getNumeroCCIAA()))) &&
            ((this.numeroDipendenti==null && other.getNumeroDipendenti()==null) || 
             (this.numeroDipendenti!=null &&
              this.numeroDipendenti.equals(other.getNumeroDipendenti()))) &&
            ((this.partitaIva==null && other.getPartitaIva()==null) || 
             (this.partitaIva!=null &&
              this.partitaIva.equals(other.getPartitaIva()))) &&
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.capitaleSociale==null && other.getCapitaleSociale()==null) || 
             (this.capitaleSociale!=null &&
              this.capitaleSociale.equals(other.getCapitaleSociale()))) &&
            ((this.amministratore==null && other.getAmministratore()==null) || 
             (this.amministratore!=null &&
              this.amministratore.equals(other.getAmministratore()))) &&
            ((this.gravame==null && other.getGravame()==null) || 
             (this.gravame!=null &&
              this.gravame.equals(other.getGravame()))) &&
            ((this.scala==null && other.getScala()==null) || 
             (this.scala!=null &&
              this.scala.equals(other.getScala()))) &&
            ((this.interno==null && other.getInterno()==null) || 
             (this.interno!=null &&
              this.interno.equals(other.getInterno()))) &&
            ((this.piano==null && other.getPiano()==null) || 
             (this.piano!=null &&
              this.piano.equals(other.getPiano()))) &&
            ((this.edificio==null && other.getEdificio()==null) || 
             (this.edificio!=null &&
              this.edificio.equals(other.getEdificio()))) &&
            ((this.lotto==null && other.getLotto()==null) || 
             (this.lotto!=null &&
              this.lotto.equals(other.getLotto()))) &&
            this.idComuneCatastale == other.getIdComuneCatastale() &&
            ((this.codiceCatastale1==null && other.getCodiceCatastale1()==null) || 
             (this.codiceCatastale1!=null &&
              this.codiceCatastale1.equals(other.getCodiceCatastale1()))) &&
            ((this.tipoCatasto==null && other.getTipoCatasto()==null) || 
             (this.tipoCatasto!=null &&
              this.tipoCatasto.equals(other.getTipoCatasto()))) &&
            ((this.dataAccatastamento==null && other.getDataAccatastamento()==null) || 
             (this.dataAccatastamento!=null &&
              this.dataAccatastamento.equals(other.getDataAccatastamento()))) &&
            ((this.fondo==null && other.getFondo()==null) || 
             (this.fondo!=null &&
              this.fondo.equals(other.getFondo()))) &&
            ((this.sezione==null && other.getSezione()==null) || 
             (this.sezione!=null &&
              this.sezione.equals(other.getSezione()))) &&
            ((this.foglio==null && other.getFoglio()==null) || 
             (this.foglio!=null &&
              this.foglio.equals(other.getFoglio()))) &&
            ((this.particella1==null && other.getParticella1()==null) || 
             (this.particella1!=null &&
              this.particella1.equals(other.getParticella1()))) &&
            ((this.particella2==null && other.getParticella2()==null) || 
             (this.particella2!=null &&
              this.particella2.equals(other.getParticella2()))) &&
            ((this.sub1==null && other.getSub1()==null) || 
             (this.sub1!=null &&
              this.sub1.equals(other.getSub1()))) &&
            ((this.sub2==null && other.getSub2()==null) || 
             (this.sub2!=null &&
              this.sub2.equals(other.getSub2()))) &&
            ((this.tipoDenuncia==null && other.getTipoDenuncia()==null) || 
             (this.tipoDenuncia!=null &&
              this.tipoDenuncia.equals(other.getTipoDenuncia()))) &&
            ((this.numeroDenuncia1==null && other.getNumeroDenuncia1()==null) || 
             (this.numeroDenuncia1!=null &&
              this.numeroDenuncia1.equals(other.getNumeroDenuncia1()))) &&
            ((this.numeroDenuncia2==null && other.getNumeroDenuncia2()==null) || 
             (this.numeroDenuncia2!=null &&
              this.numeroDenuncia2.equals(other.getNumeroDenuncia2()))) &&
            ((this.annoDenuncia==null && other.getAnnoDenuncia()==null) || 
             (this.annoDenuncia!=null &&
              this.annoDenuncia.equals(other.getAnnoDenuncia()))) &&
            ((this.schedaAccatastamento==null && other.getSchedaAccatastamento()==null) || 
             (this.schedaAccatastamento!=null &&
              this.schedaAccatastamento.equals(other.getSchedaAccatastamento()))) &&
            ((this.zonaCensuaria==null && other.getZonaCensuaria()==null) || 
             (this.zonaCensuaria!=null &&
              this.zonaCensuaria.equals(other.getZonaCensuaria()))) &&
            ((this.renditaCatastale==null && other.getRenditaCatastale()==null) || 
             (this.renditaCatastale!=null &&
              this.renditaCatastale.equals(other.getRenditaCatastale()))) &&
            ((this.renditaDominicale==null && other.getRenditaDominicale()==null) || 
             (this.renditaDominicale!=null &&
              this.renditaDominicale.equals(other.getRenditaDominicale()))) &&
            ((this.ettari==null && other.getEttari()==null) || 
             (this.ettari!=null &&
              this.ettari.equals(other.getEttari()))) &&
            ((this.are==null && other.getAre()==null) || 
             (this.are!=null &&
              this.are.equals(other.getAre()))) &&
            ((this.centiare==null && other.getCentiare()==null) || 
             (this.centiare!=null &&
              this.centiare.equals(other.getCentiare()))) &&
            ((this.vaniIntero==null && other.getVaniIntero()==null) || 
             (this.vaniIntero!=null &&
              this.vaniIntero.equals(other.getVaniIntero()))) &&
            ((this.vaniDecimale==null && other.getVaniDecimale()==null) || 
             (this.vaniDecimale!=null &&
              this.vaniDecimale.equals(other.getVaniDecimale()))) &&
            ((this.metriCubiIntero==null && other.getMetriCubiIntero()==null) || 
             (this.metriCubiIntero!=null &&
              this.metriCubiIntero.equals(other.getMetriCubiIntero()))) &&
            ((this.metriCubiDecimale==null && other.getMetriCubiDecimale()==null) || 
             (this.metriCubiDecimale!=null &&
              this.metriCubiDecimale.equals(other.getMetriCubiDecimale()))) &&
            ((this.metriQuadriIntero==null && other.getMetriQuadriIntero()==null) || 
             (this.metriQuadriIntero!=null &&
              this.metriQuadriIntero.equals(other.getMetriQuadriIntero()))) &&
            ((this.metriQuadriDecimale==null && other.getMetriQuadriDecimale()==null) || 
             (this.metriQuadriDecimale!=null &&
              this.metriQuadriDecimale.equals(other.getMetriQuadriDecimale()))) &&
            ((this.appartenenza==null && other.getAppartenenza()==null) || 
             (this.appartenenza!=null &&
              this.appartenenza.equals(other.getAppartenenza()))) &&
            ((this.comuneCatastaleP==null && other.getComuneCatastaleP()==null) || 
             (this.comuneCatastaleP!=null &&
              this.comuneCatastaleP.equals(other.getComuneCatastaleP()))) &&
            ((this.codiceCatastale1P==null && other.getCodiceCatastale1P()==null) || 
             (this.codiceCatastale1P!=null &&
              this.codiceCatastale1P.equals(other.getCodiceCatastale1P()))) &&
            ((this.codiceCatastale2P==null && other.getCodiceCatastale2P()==null) || 
             (this.codiceCatastale2P!=null &&
              this.codiceCatastale2P.equals(other.getCodiceCatastale2P()))) &&
            ((this.tipoCatastoP==null && other.getTipoCatastoP()==null) || 
             (this.tipoCatastoP!=null &&
              this.tipoCatastoP.equals(other.getTipoCatastoP()))) &&
            ((this.sezioneP==null && other.getSezioneP()==null) || 
             (this.sezioneP!=null &&
              this.sezioneP.equals(other.getSezioneP()))) &&
            ((this.foglioP==null && other.getFoglioP()==null) || 
             (this.foglioP!=null &&
              this.foglioP.equals(other.getFoglioP()))) &&
            ((this.particella1P==null && other.getParticella1P()==null) || 
             (this.particella1P!=null &&
              this.particella1P.equals(other.getParticella1P()))) &&
            ((this.particella2P==null && other.getParticella2P()==null) || 
             (this.particella2P!=null &&
              this.particella2P.equals(other.getParticella2P()))) &&
            ((this.sub1P==null && other.getSub1P()==null) || 
             (this.sub1P!=null &&
              this.sub1P.equals(other.getSub1P()))) &&
            ((this.sub2P==null && other.getSub2P()==null) || 
             (this.sub2P!=null &&
              this.sub2P.equals(other.getSub2P()))) &&
            ((this.tipoDenunciaP==null && other.getTipoDenunciaP()==null) || 
             (this.tipoDenunciaP!=null &&
              this.tipoDenunciaP.equals(other.getTipoDenunciaP()))) &&
            ((this.numeroDenuncia1P==null && other.getNumeroDenuncia1P()==null) || 
             (this.numeroDenuncia1P!=null &&
              this.numeroDenuncia1P.equals(other.getNumeroDenuncia1P()))) &&
            ((this.numeroDenuncia2P==null && other.getNumeroDenuncia2P()==null) || 
             (this.numeroDenuncia2P!=null &&
              this.numeroDenuncia2P.equals(other.getNumeroDenuncia2P()))) &&
            ((this.annoDenunciaP==null && other.getAnnoDenunciaP()==null) || 
             (this.annoDenunciaP!=null &&
              this.annoDenunciaP.equals(other.getAnnoDenunciaP()))) &&
            ((this.tipoOccupanti==null && other.getTipoOccupanti()==null) || 
             (this.tipoOccupanti!=null &&
              this.tipoOccupanti.equals(other.getTipoOccupanti()))) &&
            ((this.numRegBeneMobileRegistrato==null && other.getNumRegBeneMobileRegistrato()==null) || 
             (this.numRegBeneMobileRegistrato!=null &&
              this.numRegBeneMobileRegistrato.equals(other.getNumRegBeneMobileRegistrato()))) &&
            ((this.targa==null && other.getTarga()==null) || 
             (this.targa!=null &&
              this.targa.equals(other.getTarga()))) &&
            ((this.dataRegistrazione==null && other.getDataRegistrazione()==null) || 
             (this.dataRegistrazione!=null &&
              this.dataRegistrazione.equals(other.getDataRegistrazione()))) &&
            ((this.modelloVeicolo==null && other.getModelloVeicolo()==null) || 
             (this.modelloVeicolo!=null &&
              this.modelloVeicolo.equals(other.getModelloVeicolo()))) &&
            ((this.codImmatricolazioneVeicolo==null && other.getCodImmatricolazioneVeicolo()==null) || 
             (this.codImmatricolazioneVeicolo!=null &&
              this.codImmatricolazioneVeicolo.equals(other.getCodImmatricolazioneVeicolo()))) &&
            ((this.telaio==null && other.getTelaio()==null) || 
             (this.telaio!=null &&
              this.telaio.equals(other.getTelaio()))) &&
            ((this.coloreVeicolo==null && other.getColoreVeicolo()==null) || 
             (this.coloreVeicolo!=null &&
              this.coloreVeicolo.equals(other.getColoreVeicolo()))) &&
            ((this.vincolo==null && other.getVincolo()==null) || 
             (this.vincolo!=null &&
              this.vincolo.equals(other.getVincolo()))) &&
            ((this.descrizioneVincolo==null && other.getDescrizioneVincolo()==null) || 
             (this.descrizioneVincolo!=null &&
              this.descrizioneVincolo.equals(other.getDescrizioneVincolo()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.descOperFinanz==null && other.getDescOperFinanz()==null) || 
             (this.descOperFinanz!=null &&
              this.descOperFinanz.equals(other.getDescOperFinanz()))) &&
            ((this.intestatario==null && other.getIntestatario()==null) || 
             (this.intestatario!=null &&
              this.intestatario.equals(other.getIntestatario()))) &&
            ((this.numeroRegistrazione==null && other.getNumeroRegistrazione()==null) || 
             (this.numeroRegistrazione!=null &&
              this.numeroRegistrazione.equals(other.getNumeroRegistrazione()))) &&
            ((this.codiceIban==null && other.getCodiceIban()==null) || 
             (this.codiceIban!=null &&
              this.codiceIban.equals(other.getCodiceIban()))) &&
            ((this.valutaEst==null && other.getValutaEst()==null) || 
             (this.valutaEst!=null &&
              this.valutaEst.equals(other.getValutaEst()))) &&
            ((this.valutaEur==null && other.getValutaEur()==null) || 
             (this.valutaEur!=null &&
              this.valutaEur.equals(other.getValutaEur()))) &&
            ((this.tipoValuta==null && other.getTipoValuta()==null) || 
             (this.tipoValuta!=null &&
              this.tipoValuta.equals(other.getTipoValuta()))) &&
            ((this.codiceAbi==null && other.getCodiceAbi()==null) || 
             (this.codiceAbi!=null &&
              this.codiceAbi.equals(other.getCodiceAbi()))) &&
            ((this.codiceCab==null && other.getCodiceCab()==null) || 
             (this.codiceCab!=null &&
              this.codiceCab.equals(other.getCodiceCab()))) &&
            ((this.numBollettino==null && other.getNumBollettino()==null) || 
             (this.numBollettino!=null &&
              this.numBollettino.equals(other.getNumBollettino()))) &&
            ((this.numModelloTribunale==null && other.getNumModelloTribunale()==null) || 
             (this.numModelloTribunale!=null &&
              this.numModelloTribunale.equals(other.getNumModelloTribunale()))) &&
            ((this.indirizzoBeneFin==null && other.getIndirizzoBeneFin()==null) || 
             (this.indirizzoBeneFin!=null &&
              this.indirizzoBeneFin.equals(other.getIndirizzoBeneFin()))) &&
            ((this.direttoreProtempore==null && other.getDirettoreProtempore()==null) || 
             (this.direttoreProtempore!=null &&
              this.direttoreProtempore.equals(other.getDirettoreProtempore()))) &&
            ((this.descrizioneCapo==null && other.getDescrizioneCapo()==null) || 
             (this.descrizioneCapo!=null &&
              this.descrizioneCapo.equals(other.getDescrizioneCapo()))) &&
            ((this.numeroAnimali==null && other.getNumeroAnimali()==null) || 
             (this.numeroAnimali!=null &&
              this.numeroAnimali.equals(other.getNumeroAnimali()))) &&
            ((this.descrizioneCollezione==null && other.getDescrizioneCollezione()==null) || 
             (this.descrizioneCollezione!=null &&
              this.descrizioneCollezione.equals(other.getDescrizioneCollezione()))) &&
            ((this.numeroPezzi==null && other.getNumeroPezzi()==null) || 
             (this.numeroPezzi!=null &&
              this.numeroPezzi.equals(other.getNumeroPezzi()))) &&
            ((this.descUbicazione==null && other.getDescUbicazione()==null) || 
             (this.descUbicazione!=null &&
              this.descUbicazione.equals(other.getDescUbicazione())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getIdBene()).hashCode();
        _hashCode += new Long(getVersioneBene()).hashCode();
        if (getDescBene() != null) {
            _hashCode += getDescBene().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        if (getSottocategoria() != null) {
            _hashCode += getSottocategoria().hashCode();
        }
        if (getRagioneSociale() != null) {
            _hashCode += getRagioneSociale().hashCode();
        }
        if (getSettoreAttivita() != null) {
            _hashCode += getSettoreAttivita().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getRegione() != null) {
            _hashCode += getRegione().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getDesComuneEstero() != null) {
            _hashCode += getDesComuneEstero().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
        }
        if (getNumeroCCIAA() != null) {
            _hashCode += getNumeroCCIAA().hashCode();
        }
        if (getNumeroDipendenti() != null) {
            _hashCode += getNumeroDipendenti().hashCode();
        }
        if (getPartitaIva() != null) {
            _hashCode += getPartitaIva().hashCode();
        }
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCapitaleSociale() != null) {
            _hashCode += getCapitaleSociale().hashCode();
        }
        if (getAmministratore() != null) {
            _hashCode += getAmministratore().hashCode();
        }
        if (getGravame() != null) {
            _hashCode += getGravame().hashCode();
        }
        if (getScala() != null) {
            _hashCode += getScala().hashCode();
        }
        if (getInterno() != null) {
            _hashCode += getInterno().hashCode();
        }
        if (getPiano() != null) {
            _hashCode += getPiano().hashCode();
        }
        if (getEdificio() != null) {
            _hashCode += getEdificio().hashCode();
        }
        if (getLotto() != null) {
            _hashCode += getLotto().hashCode();
        }
        _hashCode += new Long(getIdComuneCatastale()).hashCode();
        if (getCodiceCatastale1() != null) {
            _hashCode += getCodiceCatastale1().hashCode();
        }
        if (getTipoCatasto() != null) {
            _hashCode += getTipoCatasto().hashCode();
        }
        if (getDataAccatastamento() != null) {
            _hashCode += getDataAccatastamento().hashCode();
        }
        if (getFondo() != null) {
            _hashCode += getFondo().hashCode();
        }
        if (getSezione() != null) {
            _hashCode += getSezione().hashCode();
        }
        if (getFoglio() != null) {
            _hashCode += getFoglio().hashCode();
        }
        if (getParticella1() != null) {
            _hashCode += getParticella1().hashCode();
        }
        if (getParticella2() != null) {
            _hashCode += getParticella2().hashCode();
        }
        if (getSub1() != null) {
            _hashCode += getSub1().hashCode();
        }
        if (getSub2() != null) {
            _hashCode += getSub2().hashCode();
        }
        if (getTipoDenuncia() != null) {
            _hashCode += getTipoDenuncia().hashCode();
        }
        if (getNumeroDenuncia1() != null) {
            _hashCode += getNumeroDenuncia1().hashCode();
        }
        if (getNumeroDenuncia2() != null) {
            _hashCode += getNumeroDenuncia2().hashCode();
        }
        if (getAnnoDenuncia() != null) {
            _hashCode += getAnnoDenuncia().hashCode();
        }
        if (getSchedaAccatastamento() != null) {
            _hashCode += getSchedaAccatastamento().hashCode();
        }
        if (getZonaCensuaria() != null) {
            _hashCode += getZonaCensuaria().hashCode();
        }
        if (getRenditaCatastale() != null) {
            _hashCode += getRenditaCatastale().hashCode();
        }
        if (getRenditaDominicale() != null) {
            _hashCode += getRenditaDominicale().hashCode();
        }
        if (getEttari() != null) {
            _hashCode += getEttari().hashCode();
        }
        if (getAre() != null) {
            _hashCode += getAre().hashCode();
        }
        if (getCentiare() != null) {
            _hashCode += getCentiare().hashCode();
        }
        if (getVaniIntero() != null) {
            _hashCode += getVaniIntero().hashCode();
        }
        if (getVaniDecimale() != null) {
            _hashCode += getVaniDecimale().hashCode();
        }
        if (getMetriCubiIntero() != null) {
            _hashCode += getMetriCubiIntero().hashCode();
        }
        if (getMetriCubiDecimale() != null) {
            _hashCode += getMetriCubiDecimale().hashCode();
        }
        if (getMetriQuadriIntero() != null) {
            _hashCode += getMetriQuadriIntero().hashCode();
        }
        if (getMetriQuadriDecimale() != null) {
            _hashCode += getMetriQuadriDecimale().hashCode();
        }
        if (getAppartenenza() != null) {
            _hashCode += getAppartenenza().hashCode();
        }
        if (getComuneCatastaleP() != null) {
            _hashCode += getComuneCatastaleP().hashCode();
        }
        if (getCodiceCatastale1P() != null) {
            _hashCode += getCodiceCatastale1P().hashCode();
        }
        if (getCodiceCatastale2P() != null) {
            _hashCode += getCodiceCatastale2P().hashCode();
        }
        if (getTipoCatastoP() != null) {
            _hashCode += getTipoCatastoP().hashCode();
        }
        if (getSezioneP() != null) {
            _hashCode += getSezioneP().hashCode();
        }
        if (getFoglioP() != null) {
            _hashCode += getFoglioP().hashCode();
        }
        if (getParticella1P() != null) {
            _hashCode += getParticella1P().hashCode();
        }
        if (getParticella2P() != null) {
            _hashCode += getParticella2P().hashCode();
        }
        if (getSub1P() != null) {
            _hashCode += getSub1P().hashCode();
        }
        if (getSub2P() != null) {
            _hashCode += getSub2P().hashCode();
        }
        if (getTipoDenunciaP() != null) {
            _hashCode += getTipoDenunciaP().hashCode();
        }
        if (getNumeroDenuncia1P() != null) {
            _hashCode += getNumeroDenuncia1P().hashCode();
        }
        if (getNumeroDenuncia2P() != null) {
            _hashCode += getNumeroDenuncia2P().hashCode();
        }
        if (getAnnoDenunciaP() != null) {
            _hashCode += getAnnoDenunciaP().hashCode();
        }
        if (getTipoOccupanti() != null) {
            _hashCode += getTipoOccupanti().hashCode();
        }
        if (getNumRegBeneMobileRegistrato() != null) {
            _hashCode += getNumRegBeneMobileRegistrato().hashCode();
        }
        if (getTarga() != null) {
            _hashCode += getTarga().hashCode();
        }
        if (getDataRegistrazione() != null) {
            _hashCode += getDataRegistrazione().hashCode();
        }
        if (getModelloVeicolo() != null) {
            _hashCode += getModelloVeicolo().hashCode();
        }
        if (getCodImmatricolazioneVeicolo() != null) {
            _hashCode += getCodImmatricolazioneVeicolo().hashCode();
        }
        if (getTelaio() != null) {
            _hashCode += getTelaio().hashCode();
        }
        if (getColoreVeicolo() != null) {
            _hashCode += getColoreVeicolo().hashCode();
        }
        if (getVincolo() != null) {
            _hashCode += getVincolo().hashCode();
        }
        if (getDescrizioneVincolo() != null) {
            _hashCode += getDescrizioneVincolo().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDescOperFinanz() != null) {
            _hashCode += getDescOperFinanz().hashCode();
        }
        if (getIntestatario() != null) {
            _hashCode += getIntestatario().hashCode();
        }
        if (getNumeroRegistrazione() != null) {
            _hashCode += getNumeroRegistrazione().hashCode();
        }
        if (getCodiceIban() != null) {
            _hashCode += getCodiceIban().hashCode();
        }
        if (getValutaEst() != null) {
            _hashCode += getValutaEst().hashCode();
        }
        if (getValutaEur() != null) {
            _hashCode += getValutaEur().hashCode();
        }
        if (getTipoValuta() != null) {
            _hashCode += getTipoValuta().hashCode();
        }
        if (getCodiceAbi() != null) {
            _hashCode += getCodiceAbi().hashCode();
        }
        if (getCodiceCab() != null) {
            _hashCode += getCodiceCab().hashCode();
        }
        if (getNumBollettino() != null) {
            _hashCode += getNumBollettino().hashCode();
        }
        if (getNumModelloTribunale() != null) {
            _hashCode += getNumModelloTribunale().hashCode();
        }
        if (getIndirizzoBeneFin() != null) {
            _hashCode += getIndirizzoBeneFin().hashCode();
        }
        if (getDirettoreProtempore() != null) {
            _hashCode += getDirettoreProtempore().hashCode();
        }
        if (getDescrizioneCapo() != null) {
            _hashCode += getDescrizioneCapo().hashCode();
        }
        if (getNumeroAnimali() != null) {
            _hashCode += getNumeroAnimali().hashCode();
        }
        if (getDescrizioneCollezione() != null) {
            _hashCode += getDescrizioneCollezione().hashCode();
        }
        if (getNumeroPezzi() != null) {
            _hashCode += getNumeroPezzi().hashCode();
        }
        if (getDescUbicazione() != null) {
            _hashCode += getDescUbicazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BeneBDC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "beneBDCType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idBene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "idBene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versioneBene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "versioneBene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descBene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descBene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sottocategoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sottocategoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragioneSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "ragioneSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settoreAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "settoreAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "regione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desComuneEstero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "desComuneEstero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "indirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "indirizzoFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCCIAA");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroCCIAA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDipendenti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroDipendenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partitaIva");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "partitaIva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capitaleSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "capitaleSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amministratore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "amministratore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gravame");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "gravame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scala");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "scala"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "interno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("piano");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "piano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("edificio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "edificio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lotto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "lotto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idComuneCatastale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "idComuneCatastale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCatastale1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceCatastale1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCatasto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoCatasto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAccatastamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dataAccatastamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fondo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "fondo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foglio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "foglio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDenuncia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoDenuncia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDenuncia1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroDenuncia1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDenuncia2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroDenuncia2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoDenuncia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "annoDenuncia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedaAccatastamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "schedaAccatastamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonaCensuaria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "zonaCensuaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("renditaCatastale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "renditaCatastale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("renditaDominicale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "renditaDominicale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ettari");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "ettari"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("are");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "are"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centiare");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "centiare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vaniIntero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "vaniIntero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vaniDecimale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "vaniDecimale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metriCubiIntero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "metriCubiIntero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metriCubiDecimale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "metriCubiDecimale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metriQuadriIntero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "metriQuadriIntero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metriQuadriDecimale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "metriQuadriDecimale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "byte"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appartenenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "appartenenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuneCatastaleP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comuneCatastaleP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCatastale1P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceCatastale1P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCatastale2P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceCatastale2P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCatastoP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoCatastoP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezioneP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sezioneP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foglioP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "foglioP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella1P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella1P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella2P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella2P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub1P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub1P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub2P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub2P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDenunciaP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoDenunciaP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDenuncia1P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroDenuncia1P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDenuncia2P");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroDenuncia2P"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoDenunciaP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "annoDenunciaP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOccupanti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoOccupanti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegBeneMobileRegistrato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numRegBeneMobileRegistrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "targa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRegistrazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dataRegistrazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelloVeicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "modelloVeicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codImmatricolazioneVeicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codImmatricolazioneVeicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telaio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "telaio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coloreVeicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "coloreVeicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vincolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "vincolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneVincolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descrizioneVincolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descOperFinanz");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descOperFinanz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intestatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "intestatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistrazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroRegistrazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIban");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceIban"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valutaEst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "valutaEst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valutaEur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "valutaEur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoValuta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoValuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAbi");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceAbi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCab");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "codiceCab"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numBollettino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numBollettino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numModelloTribunale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numModelloTribunale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzoBeneFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "indirizzoBeneFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direttoreProtempore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "direttoreProtempore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCapo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descrizioneCapo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAnimali");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroAnimali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCollezione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descrizioneCollezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPezzi");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroPezzi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descUbicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descUbicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
