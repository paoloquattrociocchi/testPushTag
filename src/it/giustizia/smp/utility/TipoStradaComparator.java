package it.giustizia.smp.utility;

import it.giustizia.smp.model.TTpTipoStrada;

import java.util.Comparator;

public class TipoStradaComparator implements Comparator<TTpTipoStrada> {

    public int compare(TTpTipoStrada t1, TTpTipoStrada t2) {

        if (t1.getDescTipoStrada().compareTo(t2.getDescTipoStrada()) == 0) {
            return 0;
        }
        if (t1.getDescTipoStrada().compareTo(t2.getDescTipoStrada()) > 0) {
            return 1;
        }
        return -1;
    }
}
