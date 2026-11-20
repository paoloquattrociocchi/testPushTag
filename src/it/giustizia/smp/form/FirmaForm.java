package it.giustizia.smp.form;


public class FirmaForm {
    
    private Object userName;
    private Object password;
    private Object documentalePin;
    
    // se vale true il documento è firmato contestualmente all'inserimento
    // se vale false la firma del documento è differita in un secondo momento
    private boolean firmaImmediata = false;
    
    /**
     * @return the userName
     */
    public Object getUserName() {
    
        return userName;
    }
    
    /**
     * @param userName the userName to set
     */
    public void setUserName(Object userName) {
    
        this.userName = userName;
    }
    
    /**
     * @return the password
     */
    public Object getPassword() {
    
        return password;
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(Object password) {
    
        this.password = password;
    }
    
    /**
     * @return the documentalePin
     */
    public Object getDocumentalePin() {
    
        return documentalePin;
    }
    
    /**
     * @param documentalePin the documentalePin to set
     */
    public void setDocumentalePin(Object documentalePin) {
    
        this.documentalePin = documentalePin;
    }

    
    /**
     * @return the firmaImmediata
     */
    public boolean isFirmaImmediata() {
    
        return firmaImmediata;
    }

    
    /**
     * @param firmaImmediata the firmaImmediata to set
     */
    public void setFirmaImmediata(boolean firmaImmediata) {
    
        this.firmaImmediata = firmaImmediata;
    }    
}
