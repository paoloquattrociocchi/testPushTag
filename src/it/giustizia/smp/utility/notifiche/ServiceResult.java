package it.giustizia.smp.utility.notifiche;

import java.util.ArrayList;

public class ServiceResult {
	
	public enum Result {
		OK,
		KO
	}
	
	public ServiceResult(Result returnCode) {
		super();
		this.returnCode = returnCode;
	}
	
	public ServiceResult(Result returnCode, String errorDescription) {
		super();
		this.returnCode = returnCode;
		this.errorDescription = errorDescription;
	}

	private Result returnCode;
	private ArrayList<String> warnigList = new ArrayList<String>();
	private String errorDescription;
	
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setKO(String errorDescription) {
		this.returnCode = returnCode.KO;
		this.errorDescription = errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public Result getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Result returnCode) {
		this.returnCode = returnCode;
	}
	public ArrayList<String> getWarnigList() {
		return warnigList;
	}
	public void setWarnigList(ArrayList<String> warnigList) {
		this.warnigList = warnigList;
	}
	public boolean addWarning(String e) {
		return warnigList.add(e);
	}
	
	
	
}
