package it.giustizia.smp.utility.report;

import java.math.BigDecimal;
import java.util.List;

import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;

import javax.servlet.http.HttpServletRequest;


import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.group.CustomGroupBuilder;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import static net.sf.dynamicreports.report.builder.DynamicReports.* ;


public class ReportFactory {

	private JasperReportBuilder jrb;
	
	@SuppressWarnings("unchecked")
	public JasperReportBuilder creaReportDecretoSequestro(String templateName, HttpServletRequest request, List datiSub) throws Exception {
		
		jrb = ReportUtility.createJasperReportBuilder(templateName, request);
		
		/*jrb.title(//cmp.text(exp.jasperSyntax("Il $F{atto.descTipoUfficio} di +$F{atto.descSedeUfficio}, sezione per l'applicazione delle misure di prevenzione, riunito in Camera di Consiglio, composto dai Magistrati :")),
					cmp.subreport(exp.jasperSyntax("$F{subreportMagistrati}"))
									.setDataSource(exp.jasperSyntax("$F{dataSourceMagistrati}"))
									.setFixedWidth(400));*/
		jrb.columnHeader(cmp.text(exp.jasperSyntax("\"Il \" + $F{atto.descTipoUfficio} + \" di \" + $F{atto.descSedeUfficio} + \", sezione per l'applicazione delle misure di prevenzione, " +
													"riunito in Camera di Consiglio, composto dai Magistrati :\"")),
						cmp.verticalGap(20),
						cmp.horizontalList().add(cmp.horizontalGap(12),
												cmp.subreport(exp.jasperSyntax("$F{subreportMagistrati}")).setDataSource(exp.jasperSyntax("$F{dataSourceMagistrati}")).setFixedWidth(400)
												),
						cmp.verticalGap(20),
						cmp.text(exp.jasperSyntax("\"riunito in camera di consiglio per deliberare sulla proposta della Procura della Repubblica presso il \" + $F{atto.descTipoUfficio} + " + 
													"\" di \" + $F{atto.descSedeUfficio} + \" del 23/10/2012 con la quale si chiede l'integrazione del sequestro dei beni, in funzione della successiva confisca nei confronti di:\"" )));
		
//		jrb.columnHeader(cmp.text(exp.jasperSyntax("$F{atto.descTipoUfficio} + \" prova\"")),
//						cmp.subreport(exp.jasperSyntax("$F{subreportMagistrati}")).setDataSource(exp.jasperSyntax("$F{dataSourceMagistrati}")).setFixedWidth(400),
//						cmp.text(exp.jasperSyntax("$F{atto.descTipoUfficio}" )));
		
		JasperReportBuilder subBuilder = preparaDatiMispatrBeneSog(datiSub ,request);
		
		jrb.detailHeader(cmp.subreport(subBuilder)
									.setDataSource(exp.jasperSyntax("$F{dataSourceMispatrBeneSog}"))
									.setFixedWidth(525).setPositionType(ComponentPositionType.FIX_RELATIVE_TO_TOP));
		
		jrb.setParameter("P_URL_LOGO_MIN", ReportUtility.getLogoMinistero(request));
		
		return jrb;
	}
	
//	public void preparaDatiMispatrBeneSog(List<ReportMispatrBeneSogDto> listaMispatrBeneSogDto) {
//
//		for(ReportMispatrBeneSogDto dto : listaMispatrBeneSogDto) {
//			String testoSoggettoInteressato = "";
//			String testoBene = "";
//			String testoSoggettoTitolare = "";
//			if(dto.getTIPO_PERSONA_INTERESSATO().equals("F")) 
//				testoSoggettoInteressato += dto.getCOGNOME_INTERESSATO() + " " + dto.getNOME_INTERESSATO();
//			else 
//				testoSoggettoInteressato += dto.getRAG_SOC_INTERESSATO();
//			
//			if(dto.getTIPO_PERSONA_INTERESSATO().equals("F")) {
//				if(dto.getSESSO_INTERESSATO().equals("M"))
//					testoSoggettoInteressato += " nato a ";
//				else
//					testoSoggettoInteressato += " nata a ";
//				if(dto.getDESC_COMUNE_NASC_INTERESSATO() != null || !dto.getDESC_COMUNE_NASC_INTERESSATO().equals(""))
//					testoSoggettoInteressato += dto.getDESC_COMUNE_NASC_INTERESSATO() + "(" + dto.getSIGLA_PROV_NASC_INTERESSATO() + ")";
//				else
//					testoSoggettoInteressato += dto.getCOM_EST_NASC_INTERESSATO() + "(" + dto.getDESC_STATO_NASC_INTERESSATO() + ")";
//			} else {
//				testoSoggettoInteressato += " con sede a ";
//				if(dto.getDESC_COMUNE_SEDE_INTERESSATO() != null && !dto.getDESC_COMUNE_SEDE_INTERESSATO().equals(""))
//					testoSoggettoInteressato += dto.getDESC_COMUNE_SEDE_INTERESSATO() + "(" + dto.getSIGLA_PROV_SEDE_INTERESSATO() + ")";
//				else
//					testoSoggettoInteressato += dto.getCOM_EST_SEDE_INTERESSATO() + "(" + dto.getDESC_STATO_SEDE_INTERESSATO() + ")";
//			}
//				
//			
//			dto.setTestoSoggettoInteressato(testoSoggettoInteressato);
//			dto.setTestoBene(testoBene);
//			dto.setTestoSoggettoTitolare(testoSoggettoTitolare);
//		}
//	}
	
	public JasperReportBuilder preparaDatiMispatrBeneSog(List<ReportMispatrBeneSogDto> listaMispatrBeneSogDto, HttpServletRequest request) throws Exception {

		
		JasperReportBuilder jrb = ReportUtility.createJasperReportBuilder("MispatrBeneSog_dynamic", request);
		
		CustomGroupBuilder gruppoSoggetto = grp.group("ID_SOGGETTO_INTERESSATO", BigDecimal.class);
		CustomGroupBuilder gruppoBene = grp.group("ID_BENE", BigDecimal.class);
		jrb.groupBy(gruppoSoggetto, gruppoBene);
		
		for(ReportMispatrBeneSogDto dto : listaMispatrBeneSogDto) {
			String testoSoggettoInteressato = "";
			String testoBene = "";
			String testoSoggettoTitolare = "";
			if(dto.getTIPO_PERSONA_INTERESSATO().equals("F")) 
				testoSoggettoInteressato += dto.getCOGNOME_INTERESSATO() + " " + dto.getNOME_INTERESSATO();
			else
				jrb.groupHeader(gruppoSoggetto, cmp.text(exp.jasperSyntax("$F{RAG_SOC_INTERESSATO}")).setPrintWhenExpression(exp.jasperSyntax("$F{TIPO_PERSONA_INTERESSATO}.equals( \"F\" )", Boolean.class)), cmp.filler());

				//testoSoggettoInteressato += dto.getRAG_SOC_INTERESSATO();
				
			
			if(dto.getTIPO_PERSONA_INTERESSATO().equals("F")) {
				if(dto.getSESSO_INTERESSATO().equals("M"))
					testoSoggettoInteressato += " nato a ";
				else
					testoSoggettoInteressato += " nata a ";
				if(dto.getDESC_COMUNE_NASC_INTERESSATO() != null || !dto.getDESC_COMUNE_NASC_INTERESSATO().equals(""))
					testoSoggettoInteressato += dto.getDESC_COMUNE_NASC_INTERESSATO() + "(" + dto.getSIGLA_PROV_NASC_INTERESSATO() + ")";
				else
					testoSoggettoInteressato += dto.getCOM_EST_NASC_INTERESSATO() + "(" + dto.getDESC_STATO_NASC_INTERESSATO() + ")";
			} else {
				testoSoggettoInteressato += " con sede a ";
				if(dto.getDESC_COMUNE_SEDE_INTERESSATO() != null && !dto.getDESC_COMUNE_SEDE_INTERESSATO().equals(""))
					testoSoggettoInteressato += dto.getDESC_COMUNE_SEDE_INTERESSATO() + "(" + dto.getSIGLA_PROV_SEDE_INTERESSATO() + ")";
				else
					testoSoggettoInteressato += dto.getCOM_EST_SEDE_INTERESSATO() + "(" + dto.getDESC_STATO_SEDE_INTERESSATO() + ")";
			}
				
			
			dto.setTestoSoggettoInteressato(testoSoggettoInteressato);
			dto.setTestoBene(testoBene);
			dto.setTestoSoggettoTitolare(testoSoggettoTitolare);
		}
		
		return jrb;
	}
	
	
	

}
