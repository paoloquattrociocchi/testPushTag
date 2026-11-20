package it.giustizia.smp.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.model.TAnManuali;
import it.giustizia.smp.service.ProcedimentoService;

@Controller
public class HelpController {

	@Autowired
	private ProcedimentoService procedimentoService;
	
	@RequestMapping(value = { "/ManualeUtente/{manualeUtente}" }, method = RequestMethod.GET)
	public File visualizzaManualeUtente(@PathVariable("manualeUtente") long manualeUtente, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return downlaodPDF(manualeUtente, request, response);
	}

	private File downlaodPDF(long manualeUtente, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OutputStream out = response.getOutputStream();
		TAnManuali manuale = procedimentoService.getTAnManualiById(manualeUtente);
		String contentype = (manuale.getDescTipoManuale() != null) ? manuale.getDescTipoManuale() : "application/pdf";
		byte[] doc = (manuale.getFileManuale() != null) ? manuale.getFileManuale().getBytes(1L, (int)manuale.getFileManuale().length()) : getManuale(manualeUtente, request);
        
		String nomeFile = "Manuale.pdf";
        if(manuale.getDescNomeManuale() != null && !manuale.getDescNomeManuale().isEmpty())
           nomeFile = manuale.getDescNomeManuale();
		
		// Scrittura del file sulla response
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		response.setContentType(contentype);
		response.setHeader("Content-Disposition", "attachment;filename="+nomeFile);
		baos.write(doc, 0, doc.length);
		byte[] bytes = baos.toByteArray();
		out.write(bytes);
		out.flush();
		return null;
	}

	private byte[] getManuale(long manualeUtente, HttpServletRequest request) throws Exception {
		byte [] out = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		String profiloUtente = profilo.getRuolo() + " - " + profilo.getDescrizioneUfficio() + " di " + profilo.getCitta();
		String image = request.getSession().getServletContext().getRealPath("images") + "/MinisteroDellaGiustizia.jpg";
		PDDocument document = null;
		PDPageContentStream contentStream = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Create a document and add a page to it
			document = new PDDocument();
			PDPage page = new PDPage(PDPage.PAGE_SIZE_A6);
			//page.setMediaBox(PDPage.PAGE_SIZE_A6);
			boolean land = true;
			page.setRotation((land) ? 90 : 0);

			PDRectangle pageSize = page.getMediaBox();
			float hight = pageSize.getHeight();
			float width = pageSize.getWidth();
			float larghezza = land ? hight : width;

			document.addPage(page);

			// Create a new font object selecting one of the PDF base fonts
			PDFont font = PDType1Font.TIMES_BOLD;

			// Start a new content stream which will "hold" the to be created content
			contentStream = new PDPageContentStream(document, page);

			// Define a text content stream using the selected font, moving the cursor
			// and drawing the text
			String title = "Manuale '" + manualeUtente + "' non disponibile";
			int fontSize = 16; // Or whatever font size you want.
			float titleWidth = getWidth(title, font, fontSize);
			
			float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
			
			float marginTop = ((land ? width : hight) - titleHeight) / 4f;
			
			float posX = (larghezza - titleWidth) / 2f;
      float posY = (land ? width : hight) - marginTop - titleHeight;

			PDFont perFont = PDType1Font.TIMES_ITALIC;
			String per = "per il profilo";
      float perWidth = font.getStringWidth(per) / 1000 * fontSize;
			float posX_per = (larghezza - perWidth) / 2f;
			float posY_per = posY - (titleHeight * 1.5f);

      PDFont profiloFont = PDType1Font.TIMES_ROMAN;
      float profiloUtenteWidth = profiloFont.getStringWidth(profiloUtente) / 1000 * fontSize;
			int profiloFontSize = getProfiloFont(profiloUtente, profiloFont, fontSize, (larghezza * 0.99f));
			profiloUtenteWidth = getWidth(profiloUtente, profiloFont, profiloFontSize);
			float posX_prof = (larghezza - profiloUtenteWidth) / 2f;
			float posY_prof = posY_per - (titleHeight * 1.5f);

			if (land) contentStream.concatenate2CTM(0, 1, -1, 0, width, 0);

			PDXObjectImage img = new PDJpeg(new PDDocument(), new FileInputStream(image));
			contentStream.drawImage(img, 0f, 0f);

			contentStream.beginText();
			//enjoy(contentStream, pageSize, posX, posY);
			contentStream.setFont(font, fontSize);
			contentStream.moveTextPositionByAmount(posX, posY);
			contentStream.setNonStrokingColor(Color.BLACK);
			contentStream.drawString(title);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(perFont, fontSize);
			contentStream.moveTextPositionByAmount(posX_per, posY_per);
			contentStream.drawString(per);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(profiloFont, profiloFontSize);
			contentStream.moveTextPositionByAmount(posX_prof, posY_prof);
			contentStream.drawString(profiloUtente);
			contentStream.endText();

			// Make sure that the content stream is closed:
			contentStream.close();

			// Save the results and ensure that the document is properly closed:
			document.save(baos);
			out = baos.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			// Make sure that the content stream is closed:
			if (contentStream != null) try {contentStream.close();} catch (Exception e){}
			// Ensure that the document is properly closed:
			if (document != null) try {document.close();} catch (Exception e){}
		}
		return out;
	}

	private float getWidth(String in, PDFont font, int fontSize) throws Exception {
		return font.getStringWidth(in) / 1000 * fontSize;
	}

	private int getProfiloFont(String profiloUtente, PDFont font, int fontSize, float larghezza) throws Exception {
		float profiloUtenteWidth = getWidth(profiloUtente, font, fontSize);
		while (profiloUtenteWidth > larghezza) {
			fontSize--;
			profiloUtenteWidth = getWidth(profiloUtente, font, fontSize);
		} 
		return fontSize;
	}

	private void enjoy(PDPageContentStream contentStream, PDRectangle pageSize, float posX, float posY) throws Exception {
		// counterclockwise rotation
		for (int i = 0; i < 8; i++) {
			contentStream.setTextRotation(i * Math.PI * 0.25, posX, pageSize.getHeight() - posY);
			contentStream.drawString("Andrea " + i);
		}
	}

	private byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
	}

}
