package it.giustizia.smp.service;

import it.giustizia.smp.dao.ReportDao;
import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * AttoServiceImpl
 * 
 * @author Almaviva S.p.A. classe del atto
 */
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    private ReportDao reportDao;

    @Autowired
    private Mapper mapperBean;

    /**
     * @param idAtto
     * @param idUffOwn
     * @param idProc
     * @return List<ReportMispatrBeneSogDto>
     */
    public List<ReportMispatrBeneSogDto> getMisPatrBeneSog(final long idAtto, final int idUffOwn, final long idProc) {

        List<ReportMispatrBeneSogDto> queryResults = null;
//        try {
            queryResults = reportDao.getMisPatrBeneSog(idAtto, idUffOwn, idProc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if ((queryResults == null) || queryResults.isEmpty()) {
            return null;
        }
        
        return queryResults;
    }



	/**
	 * @param reportDao the reportDao to set
	 */
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

    
    
}
