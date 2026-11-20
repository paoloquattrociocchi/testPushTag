package it.giustizia.smp.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FtpClient {

	
	private String SFTPHOST; //ip del server ftp
	private int    SFTPPORT = 22;
	private String SFTPUSER; //username per il server ftp
	private String SFTPPASS; //password per il server ftp
	private String SFTPWORKINGDIR;// directory sul server ftp su cui operare ad es. "/home/sitmp/Apps/";
	private String FILETOTRANSFER;// percorso del file locale da cui leggere o scrivere ad es. "c:\\test\\FileTest.txt";
	private Session     session     = null;
	private Channel     channel     = null;
	private ChannelSftp channelSftp = null;
	
	
	
	/**
	 * Metodo per scaricare un file da un server ftp
	 * @param host indirizzo ip del server ftp
	 * @param user username per la login
	 * @param password per la login
	 * @param ftpDir directory su cui operare sul server ftp
	 * @param localFilePath percorso completo locale del file da caricare 
	 * @return true se operazione andata a buon fine, false altrimenti
	 */
	public boolean putFile(String host,String user, String password, String ftpDir, String localFilePath){
		boolean result = true;
		setSFTPHOST(host);
		setSFTPUSER(user);
		setSFTPPASS(password);
		setSFTPWORKINGDIR(ftpDir);
		setFILETOTRANSFER(localFilePath);
		
		try{
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            
            File f = new File(FILETOTRANSFER);
            channelSftp.put(new FileInputStream(f), f.getName());
            
            channelSftp.disconnect();
            channel.disconnect();
            session.disconnect();
            
		}catch(Exception ex){
			System.out.println("Errore:" + ex);
			result = false;
		}
		return result;
	}
	
	public void getFile(String host,String user, String password, String ftpDir, String localFilePath){
		setSFTPHOST(host);
		setSFTPUSER(user);
		setSFTPPASS(password);
		setSFTPWORKINGDIR(ftpDir);
		setFILETOTRANSFER(localFilePath);
		
		try{
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            
            File f = new File(FILETOTRANSFER);
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileOutputStream fos = new FileOutputStream(FILETOTRANSFER);
			//channelSftp.get( filename, out );

            channelSftp.get("test.txt", out);
            fos.write(out.toByteArray());
            
            out.close();
            fos.close();
            //channelSftp.put(new FileInputStream(f), f.getName());
            
            //session.disconnect();

	}catch(Exception ex){
		System.out.println("Errore:" + ex);
	}

}

	
	private void setSFTPHOST(String sFTPHOST) {
		SFTPHOST = sFTPHOST;
	}
	private void setSFTPUSER(String sFTPUSER) {
		SFTPUSER = sFTPUSER;
	}
	private void setSFTPPASS(String sFTPPASS) {
		SFTPPASS = sFTPPASS;
	}
	private void setSFTPWORKINGDIR(String sFTPWORKINGDIR) {
		SFTPWORKINGDIR = sFTPWORKINGDIR;
	}
	private void setFILETOTRANSFER(String fILETOTRANSFER) {
		FILETOTRANSFER = fILETOTRANSFER;
	}
}
