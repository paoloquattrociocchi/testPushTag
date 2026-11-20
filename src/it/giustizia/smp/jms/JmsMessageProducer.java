package it.giustizia.smp.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsMessageProducer {

  private static final Logger logger = Logger.getLogger(JmsMessageProducer.class);

  @Autowired
  private JmsTemplate jmsTemplate = null;

  public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
   * Generates JMS message
   */
  public void sendMessage(JmsMessage in) throws JMSException {
      StringBuilder textMessage = new StringBuilder();
      textMessage.append(in.getTextMessage());
      final String payload = textMessage.toString();
      final String idFascicolo = in.getIdFascicolo();
      final String codIdUfficio = in.getCodIdUfficio();
      final String descUfficio = in.getDescUfficio();
      final String schema = in.getSchema();
      final String idUtente = in.getIdUtente();
      final String tipoFascicolo = in.getTipoFascicolo();

      jmsTemplate.send(new MessageCreator() {
          public Message createMessage(Session session) throws JMSException {
              TextMessage message = session.createTextMessage(payload);
              message.setStringProperty("idFascicolo", idFascicolo);
              message.setStringProperty("codIdUfficio", codIdUfficio);
              message.setStringProperty("descUfficio", descUfficio);
              message.setStringProperty("schema", schema);
              message.setStringProperty("idUtente", idUtente);
              message.setStringProperty("tipoFascicolo", tipoFascicolo);
              
              logger.info("Sending message: '" + payload + "'");
              
              return message;
          }
      });
  }
}
