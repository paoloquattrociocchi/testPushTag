package it.giustizia.smp.utility;

import it.giustizia.smp.model.TTpTipoSocieta;

import java.util.Comparator;

public class TipoSocietaComparator implements Comparator<TTpTipoSocieta> {

	public int compare(TTpTipoSocieta tipoSoc1, TTpTipoSocieta tipoSoc2) {
		
		if (tipoSoc1.getDescTipoSoc().compareTo(tipoSoc2.getDescTipoSoc()) == 0) {
            return 0;
        }
        if (tipoSoc1.getDescTipoSoc().compareTo(tipoSoc2.getDescTipoSoc()) > 0) {
            return 1;
        }
        return -1;
	}

}
