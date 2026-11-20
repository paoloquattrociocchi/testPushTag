package it.giustizia.smp.validation;

import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.FlussiPoliziaForm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("allegatoValidation")
public class AllegatoValidation extends BaseValidation implements Validator {

	
	public boolean supports(Class<?> clazz) {

		return AllegatoValidation.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

	}

	public void validateAllegatoFirmaPosticipata(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto) {
		
		// verifica che, se presente un allegato da firmare, esso sia un PDF
        if( allegatoForm !=null && 
                !allegatoForm.getContentType().equalsIgnoreCase("application/pdf") 
                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
        }
	}
	
	public void validateAllegatoEFirma(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto) {

		boolean obbl = false;
		if (tTpCatAtto != null && tTpCatAtto.getFlgAllegObbl() != null && 'S'== tTpCatAtto.getFlgAllegObbl()) {
			obbl =  true;
		}		
		
		boolean mancaAllegato = false;
		
		//se l'allegato è obbligatorio ...
		if (obbl && allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))) {
			mancaAllegato= true;
		}
		
		// verifica che, se la firma immediata vale true, esista un documento allegato
        if( allegatoForm.getFirmaRemota() != null 
                && allegatoForm.getFirmaRemota().isFirmaImmediata() 
                && allegatoForm.getFileData() != null
                && allegatoForm.getFileData().isEmpty() )
        	mancaAllegato= true;   
        
        // verifica che, se presente un allegato da firmare, esso sia un PDF
        if( allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() != null && !"".equals(allegatoForm.getFileData().getFileItem().getName())) &&
                !allegatoForm.getFileData().getContentType().equalsIgnoreCase("application/pdf") 
                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
        }
        
        if(mancaAllegato){
        	errors.rejectValue("allegato.fileData", "NotNull");
        }
		
	}

	public void validateAllegatoEFirmaAtto(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto, AttoForm attoForm) {

		boolean obbl = false;
		if (tTpCatAtto != null && tTpCatAtto.getFlgAllegObbl() != null && 'S'== tTpCatAtto.getFlgAllegObbl()) {
			obbl =  true;
		}		
		
		boolean mancaAllegato = false;
		
		//se l'allegato è obbligatorio ...
		if (obbl && allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))) {
			mancaAllegato= true;
		}
		
		// verifica che, se la firma immediata vale true, esista un documento allegato
        if( allegatoForm.getFirmaRemota() != null 
                && allegatoForm.getFirmaRemota().isFirmaImmediata() 
                && allegatoForm.getFileData() != null
                && allegatoForm.getFileData().isEmpty() )
        	mancaAllegato= true;   
        
        // verifica che, se presente un allegato da firmare, esso sia un PDF
        if( allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() != null && !"".equals(allegatoForm.getFileData().getFileItem().getName())) &&
                !allegatoForm.getFileData().getContentType().equalsIgnoreCase("application/pdf") 
                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
        }
        
        if(mancaAllegato){
        	errors.rejectValue("allegato.fileData", "NotNull");
        }

        if(mancaAllegato){
			if ( allegatoForm.getFileData() !=null 
					&& 
				(allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))
					||
				attoForm.getDataEmissione() == null
					||
				attoForm.getDataDeposito() == null
				) {
	
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			}	        
        }
        else{
        	if (attoForm.getDataEmissione() == null
					||
				attoForm.getDataDeposito() == null
				) {
	
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			}	         	
        }
	}

	public void validateAllegatoEFirmaAttoCorrezioneErroreMateriale(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto, AttoForm attoForm) {

		boolean obbl = false;
		if (tTpCatAtto != null && tTpCatAtto.getFlgAllegObbl() != null && 'S'== tTpCatAtto.getFlgAllegObbl()) {
			obbl =  true;
		}		
		
		boolean mancaAllegato = false;
		
		//se l'allegato è obbligatorio ...
		if (obbl && allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))) {
			mancaAllegato= true;
		}
		
		// verifica che, se la firma immediata vale true, esista un documento allegato
        if( allegatoForm.getFirmaRemota() != null 
                && allegatoForm.getFirmaRemota().isFirmaImmediata() 
                && allegatoForm.getFileData() != null
                && allegatoForm.getFileData().isEmpty() )
        	mancaAllegato= true;   
        
        // verifica che, se presente un allegato da firmare, esso sia un PDF
        if( allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() != null && !"".equals(allegatoForm.getFileData().getFileItem().getName())) &&
                !allegatoForm.getFileData().getContentType().equalsIgnoreCase("application/pdf") 
                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
        }
        
        if(mancaAllegato){
        	errors.rejectValue("allegato.fileData", "NotNull");
        }

        if(mancaAllegato){
			if ( allegatoForm.getFileData() !=null 
					&& 
				(allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))
					||
				attoForm.getDataEmissione() == null
				) {
	
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
				errors.rejectValue("dataEmissione", "NotNull");
			}	        
        }
        else{
        	if (attoForm.getDataEmissione() == null
				) {
	
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
				errors.rejectValue("dataEmissione", "NotNull");
			}	         	
        }
	}
	
	public boolean validateAllegatoEDateIncompletiCorrezioneErroreMateriale(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, AttoForm attoForm) {

		boolean nessunDatoDaSalvare = false;
		
		AttoForm attoFormRecuperato = RequestUtil.recuperaAtto(request);
		
		if ( allegatoForm.getFileData() !=null 
				&& 
			(allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))
				&&
			attoForm.getDataEmissione() == null
				&&
			attoFormRecuperato.getDataEmissione() == null
			) {

			nessunDatoDaSalvare = true;
			super.setMessage(Constants.MESSAGE, "errors.campi.mancanti", null, false);
		}	
		
		return nessunDatoDaSalvare;				
	}
	

	public boolean validateAllegatoEDateIncompleti(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, AttoForm attoForm) {

		boolean nessunDatoDaSalvare = false;
		
		AttoForm attoFormRecuperato = RequestUtil.recuperaAtto(request);
		
		if ( allegatoForm.getFileData() !=null 
				&& 
			(allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))
				&&
			attoForm.getDataEmissione() == null
				&&
			attoForm.getDataDeposito() == null
				&&
			attoFormRecuperato.getDataEmissione() == null
				&&
			attoFormRecuperato.getDataDeposito() == null
			) {

			nessunDatoDaSalvare = true;
			super.setMessage(Constants.MESSAGE, "errors.campi.mancanti", null, false);
		}	
		
		return nessunDatoDaSalvare;				
	}
	
	public void validateAllegatoEFirmaObbligatorio(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto) {

		boolean obbl=true;
		
		boolean mancaAllegato = false;
		
		//se l'allegato è obbligatorio ...
		if (obbl && allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))) {
			mancaAllegato= true;
		}
		
		// verifica che, se la firma immediata vale true, esista un documento allegato
        if( allegatoForm.getFirmaRemota() != null 
                && allegatoForm.getFirmaRemota().isFirmaImmediata() 
                && allegatoForm.getFileData() != null
                && allegatoForm.getFileData().isEmpty() )
        	mancaAllegato= true;   
        
        // verifica che, se presente un allegato da firmare, esso sia un PDF
        if( allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() != null && !"".equals(allegatoForm.getFileData().getFileItem().getName())) &&
                !allegatoForm.getFileData().getContentType().equalsIgnoreCase("application/pdf") 
                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
        }
        
        if(mancaAllegato){
        	errors.rejectValue("allegato.fileData", "NotNull");
        }
		
	}
	
	public void validaFlussiFormPolizia(FlussiPoliziaForm flussiForm, Errors error, HttpServletRequest request){
		if (flussiForm.getDataRicezione()==null || flussiForm.getDataRicezione().length()==0) 
			error.rejectValue("dataRicezione", "NotNull");
		
		if(flussiForm.getAllegato() != null && flussiForm.getAllegato().getFileData() == null)
			error.rejectValue("allegato.fileData", "NotNull");
		
		if (flussiForm != null && flussiForm.getAllegato() != null && flussiForm.getAllegato().getFileData() != null) {
			boolean mancaAllegato = false;
			
			if (flussiForm.getAllegato() !=null && 
					(flussiForm.getAllegato().getFileData().getFileItem().getName() == null || "".equals(flussiForm.getAllegato().getFileData().getFileItem().getName()))) {
				mancaAllegato= true;
			}
			
			// verifica che, se la firma immediata vale true, esista un documento allegato
	        if( flussiForm.getAllegato().getFirmaRemota() != null 
	                && flussiForm.getAllegato().getFirmaRemota().isFirmaImmediata() 
	                && flussiForm.getAllegato().getFileData() != null
	                && flussiForm.getAllegato().getFileData().isEmpty() )
	        	mancaAllegato= true;   
	        
	        // verifica che, se presente un allegato da firmare, esso sia un PDF
	        if( flussiForm.getAllegato().getFileData() !=null && 
	        		(flussiForm.getAllegato().getFileData().getFileItem().getName() != null && !"".equals(flussiForm.getAllegato().getFileData().getFileItem().getName())) &&
	                !flussiForm.getAllegato().getFileData().getContentType().equalsIgnoreCase("application/pdf") 
	                 && flussiForm.getAllegato().getFirmaRemota() != null  && flussiForm.getAllegato().getFirmaRemota().isFirmaImmediata()) {
	            		error.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
	        }
	        
	        if(mancaAllegato){
	        	error.rejectValue("allegato.fileData", "NotNull");
	        }
		}
	}
}
