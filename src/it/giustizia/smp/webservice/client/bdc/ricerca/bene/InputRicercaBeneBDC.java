/**
 * InputRicercaBeneBDCType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class InputRicercaBeneBDC  implements java.io.Serializable {
    
    private Long idUfficio;
    
    private Long idTipoUfficio;
    
    private java.lang.Long id;

    private java.lang.Long tipologia;

    private java.lang.Long categoria;

    private java.lang.Long sottoCategoria;

    private java.lang.String ragioneSociale;

    private java.lang.Long settoreAttivita;

    private java.lang.Long stato;

    private java.lang.Long comune;

    private java.lang.String descComuneEstero;

    private java.lang.Long toponimo;

    private java.lang.String descIndirizzo;

    private java.lang.String civico;

    private java.lang.String sezione;

    private java.lang.String foglio;

    private java.lang.String particella1;

    private java.lang.String particella2;

    private java.lang.String sub1;

    private java.lang.String sub2;

    private java.util.Date dataRegistrazioneMin;

    private java.util.Date dataRegistrazioneMax;

    private java.lang.String numeroRegistrazione;

    private java.lang.String targa;

    private java.lang.String numeroIdentificazione;

    private java.lang.String descrizioneEnte;

    private java.lang.String tipoValuta;

    public InputRicercaBeneBDC() {
    }

    public InputRicercaBeneBDC(
           Long idUfficio,
           Long idTipoUfficio,
           java.lang.Long id,
           java.lang.Long tipologia,
           java.lang.Long categoria,
           java.lang.Long sottoCategoria,
           java.lang.String ragioneSociale,
           java.lang.Long settoreAttivita,
           java.lang.Long stato,
           java.lang.Long comune,
           java.lang.String descComuneEstero,
           java.lang.Long toponimo,
           java.lang.String descIndirizzo,
           java.lang.String civico,
           java.lang.String sezione,
           java.lang.String foglio,
           java.lang.String particella1,
           java.lang.String particella2,
           java.lang.String sub1,
           java.lang.String sub2,
           java.util.Date dataRegistrazioneMin,
           java.util.Date dataRegistrazioneMax,
           java.lang.String numeroRegistrazione,
           java.lang.String targa,
           java.lang.String numeroIdentificazione,
           java.lang.String descrizioneEnte,
           java.lang.String tipoValuta) {
           this.idUfficio = idUfficio;
           this.idTipoUfficio = idTipoUfficio;
           this.id = id;
           this.tipologia = tipologia;
           this.categoria = categoria;
           this.sottoCategoria = sottoCategoria;
           this.ragioneSociale = ragioneSociale;
           this.settoreAttivita = settoreAttivita;
           this.stato = stato;
           this.comune = comune;
           this.descComuneEstero = descComuneEstero;
           this.toponimo = toponimo;
           this.descIndirizzo = descIndirizzo;
           this.civico = civico;
           this.sezione = sezione;
           this.foglio = foglio;
           this.particella1 = particella1;
           this.particella2 = particella2;
           this.sub1 = sub1;
           this.sub2 = sub2;
           this.dataRegistrazioneMin = dataRegistrazioneMin;
           this.dataRegistrazioneMax = dataRegistrazioneMax;
           this.numeroRegistrazione = numeroRegistrazione;
           this.targa = targa;
           this.numeroIdentificazione = numeroIdentificazione;
           this.descrizioneEnte = descrizioneEnte;
           this.tipoValuta = tipoValuta;
    }
    
    /**
     * @return the idUfficio
     */
    public Long getIdUfficio() {
    
        return idUfficio;
    }

    
    /**
     * @param idUfficio the idUfficio to set
     */
    public void setIdUfficio(Long idUfficio) {
    
        this.idUfficio = idUfficio;
    }

    
    /**
     * @return the idTipoUfficio
     */
    public Long getIdTipoUfficio() {
    
        return idTipoUfficio;
    }

    
    /**
     * @param idTipoUfficio the idTipoUfficio to set
     */
    public void setIdTipoUfficio(Long idTipoUfficio) {
    
        this.idTipoUfficio = idTipoUfficio;
    }

    /**
     * Gets the id value for this InputRicercaBeneBDCType.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this InputRicercaBeneBDCType.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the tipologia value for this InputRicercaBeneBDCType.
     * 
     * @return tipologia
     */
    public java.lang.Long getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this InputRicercaBeneBDCType.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.Long tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the categoria value for this InputRicercaBeneBDCType.
     * 
     * @return categoria
     */
    public java.lang.Long getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this InputRicercaBeneBDCType.
     * 
     * @param categoria
     */
    public void setCategoria(java.lang.Long categoria) {
        this.categoria = categoria;
    }


    /**
     * Gets the sottoCategoria value for this InputRicercaBeneBDCType.
     * 
     * @return sottoCategoria
     */
    public java.lang.Long getSottoCategoria() {
        return sottoCategoria;
    }


    /**
     * Sets the sottoCategoria value for this InputRicercaBeneBDCType.
     * 
     * @param sottoCategoria
     */
    public void setSottoCategoria(java.lang.Long sottoCategoria) {
        this.sottoCategoria = sottoCategoria;
    }


    /**
     * Gets the ragioneSociale value for this InputRicercaBeneBDCType.
     * 
     * @return ragioneSociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this InputRicercaBeneBDCType.
     * 
     * @param ragioneSociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the settoreAttivita value for this InputRicercaBeneBDCType.
     * 
     * @return settoreAttivita
     */
    public java.lang.Long getSettoreAttivita() {
        return settoreAttivita;
    }


    /**
     * Sets the settoreAttivita value for this InputRicercaBeneBDCType.
     * 
     * @param settoreAttivita
     */
    public void setSettoreAttivita(java.lang.Long settoreAttivita) {
        this.settoreAttivita = settoreAttivita;
    }


    /**
     * Gets the stato value for this InputRicercaBeneBDCType.
     * 
     * @return stato
     */
    public java.lang.Long getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this InputRicercaBeneBDCType.
     * 
     * @param stato
     */
    public void setStato(java.lang.Long stato) {
        this.stato = stato;
    }


    /**
     * Gets the comune value for this InputRicercaBeneBDCType.
     * 
     * @return comune
     */
    public java.lang.Long getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this InputRicercaBeneBDCType.
     * 
     * @param comune
     */
    public void setComune(java.lang.Long comune) {
        this.comune = comune;
    }


    /**
     * Gets the descComuneEstero value for this InputRicercaBeneBDCType.
     * 
     * @return descComuneEstero
     */
    public java.lang.String getDescComuneEstero() {
        return descComuneEstero;
    }


    /**
     * Sets the descComuneEstero value for this InputRicercaBeneBDCType.
     * 
     * @param descComuneEstero
     */
    public void setDescComuneEstero(java.lang.String descComuneEstero) {
        this.descComuneEstero = descComuneEstero;
    }


    /**
     * Gets the toponimo value for this InputRicercaBeneBDCType.
     * 
     * @return toponimo
     */
    public java.lang.Long getToponimo() {
        return toponimo;
    }


    /**
     * Sets the toponimo value for this InputRicercaBeneBDCType.
     * 
     * @param toponimo
     */
    public void setToponimo(java.lang.Long toponimo) {
        this.toponimo = toponimo;
    }


    /**
     * Gets the descIndirizzo value for this InputRicercaBeneBDCType.
     * 
     * @return descIndirizzo
     */
    public java.lang.String getDescIndirizzo() {
        return descIndirizzo;
    }


    /**
     * Sets the descIndirizzo value for this InputRicercaBeneBDCType.
     * 
     * @param descIndirizzo
     */
    public void setDescIndirizzo(java.lang.String descIndirizzo) {
        this.descIndirizzo = descIndirizzo;
    }


    /**
     * Gets the civico value for this InputRicercaBeneBDCType.
     * 
     * @return civico
     */
    public java.lang.String getCivico() {
        return civico;
    }


    /**
     * Sets the civico value for this InputRicercaBeneBDCType.
     * 
     * @param civico
     */
    public void setCivico(java.lang.String civico) {
        this.civico = civico;
    }


    /**
     * Gets the sezione value for this InputRicercaBeneBDCType.
     * 
     * @return sezione
     */
    public java.lang.String getSezione() {
        return sezione;
    }


    /**
     * Sets the sezione value for this InputRicercaBeneBDCType.
     * 
     * @param sezione
     */
    public void setSezione(java.lang.String sezione) {
        this.sezione = sezione;
    }


    /**
     * Gets the foglio value for this InputRicercaBeneBDCType.
     * 
     * @return foglio
     */
    public java.lang.String getFoglio() {
        return foglio;
    }


    /**
     * Sets the foglio value for this InputRicercaBeneBDCType.
     * 
     * @param foglio
     */
    public void setFoglio(java.lang.String foglio) {
        this.foglio = foglio;
    }


    /**
     * Gets the particella1 value for this InputRicercaBeneBDCType.
     * 
     * @return particella1
     */
    public java.lang.String getParticella1() {
        return particella1;
    }


    /**
     * Sets the particella1 value for this InputRicercaBeneBDCType.
     * 
     * @param particella1
     */
    public void setParticella1(java.lang.String particella1) {
        this.particella1 = particella1;
    }


    /**
     * Gets the particella2 value for this InputRicercaBeneBDCType.
     * 
     * @return particella2
     */
    public java.lang.String getParticella2() {
        return particella2;
    }


    /**
     * Sets the particella2 value for this InputRicercaBeneBDCType.
     * 
     * @param particella2
     */
    public void setParticella2(java.lang.String particella2) {
        this.particella2 = particella2;
    }


    /**
     * Gets the sub1 value for this InputRicercaBeneBDCType.
     * 
     * @return sub1
     */
    public java.lang.String getSub1() {
        return sub1;
    }


    /**
     * Sets the sub1 value for this InputRicercaBeneBDCType.
     * 
     * @param sub1
     */
    public void setSub1(java.lang.String sub1) {
        this.sub1 = sub1;
    }


    /**
     * Gets the sub2 value for this InputRicercaBeneBDCType.
     * 
     * @return sub2
     */
    public java.lang.String getSub2() {
        return sub2;
    }


    /**
     * Sets the sub2 value for this InputRicercaBeneBDCType.
     * 
     * @param sub2
     */
    public void setSub2(java.lang.String sub2) {
        this.sub2 = sub2;
    }


    /**
     * Gets the dataRegistrazioneMin value for this InputRicercaBeneBDCType.
     * 
     * @return dataRegistrazioneMin
     */
    public java.util.Date getDataRegistrazioneMin() {
        return dataRegistrazioneMin;
    }


    /**
     * Sets the dataRegistrazioneMin value for this InputRicercaBeneBDCType.
     * 
     * @param dataRegistrazioneMin
     */
    public void setDataRegistrazioneMin(java.util.Date dataRegistrazioneMin) {
        this.dataRegistrazioneMin = dataRegistrazioneMin;
    }


    /**
     * Gets the dataRegistrazioneMax value for this InputRicercaBeneBDCType.
     * 
     * @return dataRegistrazioneMax
     */
    public java.util.Date getDataRegistrazioneMax() {
        return dataRegistrazioneMax;
    }


    /**
     * Sets the dataRegistrazioneMax value for this InputRicercaBeneBDCType.
     * 
     * @param dataRegistrazioneMax
     */
    public void setDataRegistrazioneMax(java.util.Date dataRegistrazioneMax) {
        this.dataRegistrazioneMax = dataRegistrazioneMax;
    }


    /**
     * Gets the numeroRegistrazione value for this InputRicercaBeneBDCType.
     * 
     * @return numeroRegistrazione
     */
    public java.lang.String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }


    /**
     * Sets the numeroRegistrazione value for this InputRicercaBeneBDCType.
     * 
     * @param numeroRegistrazione
     */
    public void setNumeroRegistrazione(java.lang.String numeroRegistrazione) {
        this.numeroRegistrazione = numeroRegistrazione;
    }


    /**
     * Gets the targa value for this InputRicercaBeneBDCType.
     * 
     * @return targa
     */
    public java.lang.String getTarga() {
        return targa;
    }


    /**
     * Sets the targa value for this InputRicercaBeneBDCType.
     * 
     * @param targa
     */
    public void setTarga(java.lang.String targa) {
        this.targa = targa;
    }


    /**
     * Gets the numeroIdentificazione value for this InputRicercaBeneBDCType.
     * 
     * @return numeroIdentificazione
     */
    public java.lang.String getNumeroIdentificazione() {
        return numeroIdentificazione;
    }


    /**
     * Sets the numeroIdentificazione value for this InputRicercaBeneBDCType.
     * 
     * @param numeroIdentificazione
     */
    public void setNumeroIdentificazione(java.lang.String numeroIdentificazione) {
        this.numeroIdentificazione = numeroIdentificazione;
    }


    /**
     * Gets the descrizioneEnte value for this InputRicercaBeneBDCType.
     * 
     * @return descrizioneEnte
     */
    public java.lang.String getDescrizioneEnte() {
        return descrizioneEnte;
    }


    /**
     * Sets the descrizioneEnte value for this InputRicercaBeneBDCType.
     * 
     * @param descrizioneEnte
     */
    public void setDescrizioneEnte(java.lang.String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
    }


    /**
     * Gets the tipoValuta value for this InputRicercaBeneBDCType.
     * 
     * @return tipoValuta
     */
    public java.lang.String getTipoValuta() {
        return tipoValuta;
    }


    /**
     * Sets the tipoValuta value for this InputRicercaBeneBDCType.
     * 
     * @param tipoValuta
     */
    public void setTipoValuta(java.lang.String tipoValuta) {
        this.tipoValuta = tipoValuta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InputRicercaBeneBDC)) return false;
        InputRicercaBeneBDC other = (InputRicercaBeneBDC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idUfficio==null && other.getIdUfficio()==null) || 
             (this.idUfficio!=null &&
              this.idUfficio.equals(other.getIdUfficio()))) &&
            ((this.idTipoUfficio==null && other.getIdTipoUfficio()==null) || 
             (this.idTipoUfficio!=null &&
              this.idTipoUfficio.equals(other.getIdTipoUfficio()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria()))) &&
            ((this.sottoCategoria==null && other.getSottoCategoria()==null) || 
             (this.sottoCategoria!=null &&
              this.sottoCategoria.equals(other.getSottoCategoria()))) &&
            ((this.ragioneSociale==null && other.getRagioneSociale()==null) || 
             (this.ragioneSociale!=null &&
              this.ragioneSociale.equals(other.getRagioneSociale()))) &&
            ((this.settoreAttivita==null && other.getSettoreAttivita()==null) || 
             (this.settoreAttivita!=null &&
              this.settoreAttivita.equals(other.getSettoreAttivita()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.descComuneEstero==null && other.getDescComuneEstero()==null) || 
             (this.descComuneEstero!=null &&
              this.descComuneEstero.equals(other.getDescComuneEstero()))) &&
            ((this.toponimo==null && other.getToponimo()==null) || 
             (this.toponimo!=null &&
              this.toponimo.equals(other.getToponimo()))) &&
            ((this.descIndirizzo==null && other.getDescIndirizzo()==null) || 
             (this.descIndirizzo!=null &&
              this.descIndirizzo.equals(other.getDescIndirizzo()))) &&
            ((this.civico==null && other.getCivico()==null) || 
             (this.civico!=null &&
              this.civico.equals(other.getCivico()))) &&
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
            ((this.dataRegistrazioneMin==null && other.getDataRegistrazioneMin()==null) || 
             (this.dataRegistrazioneMin!=null &&
              this.dataRegistrazioneMin.equals(other.getDataRegistrazioneMin()))) &&
            ((this.dataRegistrazioneMax==null && other.getDataRegistrazioneMax()==null) || 
             (this.dataRegistrazioneMax!=null &&
              this.dataRegistrazioneMax.equals(other.getDataRegistrazioneMax()))) &&
            ((this.numeroRegistrazione==null && other.getNumeroRegistrazione()==null) || 
             (this.numeroRegistrazione!=null &&
              this.numeroRegistrazione.equals(other.getNumeroRegistrazione()))) &&
            ((this.targa==null && other.getTarga()==null) || 
             (this.targa!=null &&
              this.targa.equals(other.getTarga()))) &&
            ((this.numeroIdentificazione==null && other.getNumeroIdentificazione()==null) || 
             (this.numeroIdentificazione!=null &&
              this.numeroIdentificazione.equals(other.getNumeroIdentificazione()))) &&
            ((this.descrizioneEnte==null && other.getDescrizioneEnte()==null) || 
             (this.descrizioneEnte!=null &&
              this.descrizioneEnte.equals(other.getDescrizioneEnte()))) &&
            ((this.tipoValuta==null && other.getTipoValuta()==null) || 
             (this.tipoValuta!=null &&
              this.tipoValuta.equals(other.getTipoValuta())));
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
        if (getIdUfficio() != null) {
            _hashCode += getIdUfficio().hashCode();
        }
        if (getIdTipoUfficio() != null) {
            _hashCode += getIdTipoUfficio().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        if (getSottoCategoria() != null) {
            _hashCode += getSottoCategoria().hashCode();
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
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getDescComuneEstero() != null) {
            _hashCode += getDescComuneEstero().hashCode();
        }
        if (getToponimo() != null) {
            _hashCode += getToponimo().hashCode();
        }
        if (getDescIndirizzo() != null) {
            _hashCode += getDescIndirizzo().hashCode();
        }
        if (getCivico() != null) {
            _hashCode += getCivico().hashCode();
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
        if (getDataRegistrazioneMin() != null) {
            _hashCode += getDataRegistrazioneMin().hashCode();
        }
        if (getDataRegistrazioneMax() != null) {
            _hashCode += getDataRegistrazioneMax().hashCode();
        }
        if (getNumeroRegistrazione() != null) {
            _hashCode += getNumeroRegistrazione().hashCode();
        }
        if (getTarga() != null) {
            _hashCode += getTarga().hashCode();
        }
        if (getNumeroIdentificazione() != null) {
            _hashCode += getNumeroIdentificazione().hashCode();
        }
        if (getDescrizioneEnte() != null) {
            _hashCode += getDescrizioneEnte().hashCode();
        }
        if (getTipoValuta() != null) {
            _hashCode += getTipoValuta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InputRicercaBeneBDC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputRicercaBeneBDCType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUfficio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "idUfficio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoUfficio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "idTipoUfficio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sottoCategoria");
//        Segnalazione EMS-165
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sottoCategoria_id"));
//        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sottoCategoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragioneSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "ragioneSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settoreAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "settoreAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descComuneEstero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descComuneEstero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toponimo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "toponimo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descIndirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descIndirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("civico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "civico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foglio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "foglio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "particella2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sub2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "sub2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRegistrazioneMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dataRegistrazioneMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRegistrazioneMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dataRegistrazioneMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
        elemField.setFieldName("targa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "targa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroIdentificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "numeroIdentificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "descrizioneEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoValuta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "tipoValuta"));
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
