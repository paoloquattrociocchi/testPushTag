package it.giustizia.smp.utility;

import it.giustizia.smp.model.TAnStato;

import java.util.Comparator;

public class StatoComparator implements Comparator<TAnStato> {

    public int compare(TAnStato stato1, TAnStato stato2) {

        if (stato1.getDescStato().compareTo(stato2.getDescStato()) == 0) {
            return 0;
        }
        if (stato1.getDescStato().compareTo(stato2.getDescStato()) > 0) {
            return 1;
        }
        return -1;
    }
}
