package it.giustizia.smp.validation;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class BaseValidation {

	@Autowired(required = true)
	protected HttpServletRequest	request;

	protected void addMessageIntoList(List<Message> messageList, String tipoMessaggio, String codiceMessaggio, String arguments) {

		messageList.add(new Message(null, codiceMessaggio, arguments, Message.SEPARATOR));
	}

	protected void setMessage(String tipoMessaggio, String codiceMessaggio, String arguments, boolean redirect) {

		Message message = new Message(null, codiceMessaggio, arguments, Message.SEPARATOR);
		if (redirect) {
			request.getSession().setAttribute(tipoMessaggio, message);
		} else {
			request.setAttribute(tipoMessaggio, message);
		}
	}

	protected void setMessages(List<Message> messages, boolean redirect) {

		if (redirect) {
			request.getSession().setAttribute(Constants.MESSAGELIST, messages);
		} else {
			request.setAttribute(Constants.MESSAGELIST, messages);
		}
	}

	protected void addMessageIntoList(List<Message> messageList, String codiceMessaggio) {

		addMessageIntoList(messageList, null, codiceMessaggio, null);
	}

	public void setMessage(String testo, boolean redirect, Errors errors) {

		if (testo != null && !testo.trim().isEmpty()) {
			List<Message> messaggi = new ArrayList<Message>();
			Message messaggio = new Message(null, testo, null, Message.SEPARATOR);
			messaggi.add(messaggio);
			setMessages(messaggi, redirect, errors);
		}
	}

	public void setMessages(List<Message> messages, boolean redirect, Errors errors) {

		if (messages != null && !messages.isEmpty()) {
			errors.reject(Constants.MESSAGE);
		}
		if (redirect) {
			request.getSession().setAttribute(Constants.MESSAGELIST, messages);
		} else {
			request.setAttribute(Constants.MESSAGELIST, messages);
		}
	}
}
