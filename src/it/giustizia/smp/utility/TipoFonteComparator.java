package it.giustizia.smp.utility;

import it.giustizia.smp.model.TTpCatAtto;

import java.util.Comparator;

public class TipoFonteComparator implements Comparator<TTpCatAtto> {

	public int compare(TTpCatAtto atto1, TTpCatAtto atto2) {
        
		if (atto1.getDescCatAtto().compareTo(atto2.getDescCatAtto()) == 0) {
            return 0;
        }
        if (atto1.getDescCatAtto().compareTo(atto2.getDescCatAtto()) > 0) {
            return 1;
        }
        return -1;
	}

}


