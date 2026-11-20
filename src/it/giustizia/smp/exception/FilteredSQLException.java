package it.giustizia.smp.exception;

import java.sql.SQLException;

public class FilteredSQLException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5771109098555054676L;

	public FilteredSQLException(Throwable cause) {
		super(cause);
	}
	
	public String getMessage() {
		
		String msgFiltered = "";
		String message = super.getMessage();
		String strFind = "**";
		int indI = message.indexOf(strFind);
		
		if(indI==-1) return message;
		else {
			indI = indI + strFind.length();
			int indF = indI;
			int i = 0;
			while( (indF < message.length()) && ( i = message.indexOf(strFind, indF+1)) != -1) {
				indF = i;
			}
			
			if(indF==0) return message;
			else {
				msgFiltered = message.substring(indI, indF);  
				return msgFiltered;
			}
		}
		
	}
}
