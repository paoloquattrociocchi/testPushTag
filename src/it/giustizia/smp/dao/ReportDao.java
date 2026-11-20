package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;

import java.util.List;


/**
 * AttoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Report DAO
 */
public interface ReportDao {

	/**
	 * @param idAtto
	 * @param idUffOwn
	 * @param idProc
	 * @return List<ReportMispatrBeneSogDto>
	 */
	public List<ReportMispatrBeneSogDto> getMisPatrBeneSog(final long idAtto, final int idUffOwn, final long idProc) ;
}
