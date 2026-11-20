package it.giustizia.smp.dao;


import it.giustizia.smp.model.TRelSoggSpese;


/**
 * SpeseProcessualiDao
 * 
 * @author 
 *         Interfaccia SpeseProcessuali DAO
 */

@SuppressWarnings("rawtypes")
public interface SpeseProcessualiDao {


    //salva spese processuali
    public void salvaRelSpeseProc(TRelSoggSpese speseP);
    
}