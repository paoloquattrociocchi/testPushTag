package it.giustizia.smp.exception;

public class EccezioneGenerica extends Exception {

	private static final long serialVersionUID = -6170159204738630811L;
	
	private String code = "";
	private String message = "";

	public EccezioneGenerica() {
		this("");
	}

	public EccezioneGenerica(String code) {
		this(code, "");
	}

	public EccezioneGenerica(String code, String message) {
		setCode(code);
		setMessage(message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
