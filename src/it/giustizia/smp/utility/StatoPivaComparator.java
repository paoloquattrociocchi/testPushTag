package it.giustizia.smp.utility;

import it.giustizia.smp.model.TAnStato;

import java.util.Comparator;

public class StatoPivaComparator implements Comparator<TAnStato> {

    public int compare(TAnStato stato1, TAnStato stato2) {

        if (stato1.getDescSiglaPIva().compareTo(stato2.getDescSiglaPIva()) == 0) {
            return 0;
        }
        if (stato1.getDescSiglaPIva().compareTo(stato2.getDescSiglaPIva()) > 0) {
            return 1;
        }
        return -1;
    }
}
