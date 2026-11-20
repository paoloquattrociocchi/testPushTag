package it.giustizia.smp.utility;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import org.springframework.web.servlet.view.document.AbstractExcelView;


public class StatisticheBuilder extends AbstractExcelView{
	
	private HSSFSheet sheet;
	private CellStyle style;
	private Font font;
	private HSSFRow header;
	
	
	private enum TipoStatistica {
		statistica1, statistica2
	}
	
	private void impostaFoglioExcel(HSSFWorkbook workbook){
		// create a new Excel sheet
		sheet = workbook.createSheet("Statistica");
        sheet.setDefaultColumnWidth(30);
         
        // create style for header cells
        style = workbook.createCellStyle();
        font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
      
        //create header row
        header = sheet.createRow(0);
	}

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		impostaFoglioExcel(workbook);
		
		switch(TipoStatistica.valueOf(model.get("tipoStatistica").toString())){
				case statistica1:
						metodo1(model,request,response);
					break;
				case statistica2:
					metodo2(model,request,response);
				break;
				default:
					break;
		}
		
        
    }

	protected void metodo1(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)
	{
		 
	}
	
	protected void metodo2(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)
	{
		 
	}
	

	
	
}
