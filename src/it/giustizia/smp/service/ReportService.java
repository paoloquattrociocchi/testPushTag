package it.giustizia.smp.service;

import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;

import java.util.List;


/**
 * ReportService
 * 
 * @author Almaviva S.p.A.
 *         Interfaccia Report Service
 */
public interface ReportService {

	
	/**
	 * @param idAtto
	 * @param idUffOwn
	 * @param idProc
	 * @return List
	 */
	public List<ReportMispatrBeneSogDto> getMisPatrBeneSog(final long idAtto, final int idUffOwn, final long idProc);

}
