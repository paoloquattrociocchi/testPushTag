package it.giustizia.smp.webservice.client.glmp.serviziodepositi;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.bind.DatatypeConverter;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

public class HttpUrlConnectionMassageSenderWithAuth
    extends HttpUrlConnectionMessageSender {

  private String username = "ADMIN";
  private String password = "admin";
  private String authorization;

  /**
   * Gets the {@code username} property.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the {@code username} property.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the {@code password} property.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the {@code password} property.
   */
  public void setPassword(String password) {
    this.password = password;
  }
  
  /**
   * {@inheritDoc}
   */
  public void afterPropertiesSet() throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("Basic ");
    sb.append(credential());
    authorization = sb.toString();
   // super.afterPropertiesSet();
  }

  private String credential() throws Exception {
	StringBuilder sb = new StringBuilder();
    sb.append(getUsername());
    sb.append(':');
    sb.append(getPassword());
    return encodeAsString(sb.toString());
  }
  
  public static String encodeAsString(String plaintext) throws Exception
  {
	  byte[] message = plaintext.getBytes("UTF-8");
	  return  DatatypeConverter.printBase64Binary(message);
  }

    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareConnection(HttpURLConnection connection)
      throws IOException {
    try {
    	afterPropertiesSet();
    }catch (Exception e) {}	  
    super.prepareConnection(connection);

    connection.setRequestProperty("Authorization", authorization);
  }

}