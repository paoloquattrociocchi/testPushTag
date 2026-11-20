package it.giustizia.smp.utility;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.service.CommonService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Component("utility")
public class Utility {
	
	
	@Autowired
	private CommonService			commonService;

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String calcolaHash(String orig) throws NoSuchAlgorithmException {

		MessageDigest sha = null;
		sha = MessageDigest.getInstance("SHA-1");
		sha.update(orig.getBytes());
		return toHexString(sha.digest()).toLowerCase();
	}

	public static byte[] calcolaHashFile(String fileNameOrig, String fileNameDigest) throws Exception {

		int len = 0;
		int bufLen = 20000 * 2048;
		byte[] buf = new byte[bufLen];
		byte[] tmp = null;
		MessageDigest sha = null;
		InputStream in = null;
		in = new BufferedInputStream(new FileInputStream(fileNameOrig));
		// sha = MessageDigest.getInstance("SHA-1");
		sha = MessageDigest.getInstance("MD5");
		while ((len = in.read(buf, 0, bufLen)) != -1) {
			tmp = new byte[len];
			System.arraycopy(buf, 0, tmp, 0, len);
			sha.update(tmp);
			tmp = null;
		}
		in.close();
		FileWriter fstream = new FileWriter(fileNameDigest);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(toHexString(sha.digest()).toLowerCase());
		out.close();
		return sha.digest();
	}

	public static String ControllaCodiceFiscale(String cf) {

		int i, s, c;
		String cf2;
		int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
		if (cf.length() == 0) {
			return "";
		}
		if (cf.length() != 16) {
			return "error.codiceFiscale.lunghezza";
		}
		cf2 = cf.toUpperCase();
		for (i = 0; i < 16; i++) {
			c = cf2.charAt(i);
			if (!(((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'Z')))) {
				return "error.codiceFiscale.caratteriNonValidi";
			}
		}
		s = 0;
		for (i = 1; i <= 13; i += 2) {
			c = cf2.charAt(i);
			if ((c >= '0') && (c <= '9')) {
				s = s + c - '0';
			} else {
				s = s + c - 'A';
			}
		}
		for (i = 0; i <= 14; i += 2) {
			c = cf2.charAt(i);
			if ((c >= '0') && (c <= '9')) {
				c = c - '0' + 'A';
			}
			s = s + setdisp[c - 'A'];
		}
		if (s % 26 + 'A' != cf2.charAt(15)) {
			return "error.codiceFiscale.codiceControllo";
		}
		return "";
	}


	public static void controlloPartitaIva(SoggettoForm soggetto, Errors errors, BeneForm bene) {
		String message="";

		if(soggetto!=null){
			if(soggetto.getPartitaIvaSiglaStato()!=null 
					&& soggetto.getPartitaIvaSiglaStato().trim().length()>0){
				if(Constants.ITALIA_SIGLA.equalsIgnoreCase(soggetto.getPartitaIvaSiglaStato()) && (soggetto.getPartitaIva()!=null && soggetto.getPartitaIva().trim().length() > 0 && soggetto.getPartitaIva().trim().length() < 12)){
					message = controllaPIVA(soggetto.getPartitaIva());
				}else if (!Constants.ITALIA_SIGLA.equalsIgnoreCase(soggetto.getPartitaIvaSiglaStato()) 
						&& (soggetto.getPartitaIva()==null || (soggetto.getPartitaIva()!=null && soggetto.getPartitaIva().trim().length() > 12))){
					message = "error.partitaIva.lunghezza12";
				}

			}
		}else if(bene!=null){
			if(bene.getPartitaIvaSiglaStato()!=null 
					&& bene.getPartitaIvaSiglaStato().trim().length()>0){
				if(Constants.ITALIA_SIGLA.equalsIgnoreCase(bene.getPartitaIvaSiglaStato())){
					message = controllaPIVA(bene.getPartitaIva());
				}else if (!Constants.ITALIA_SIGLA.equalsIgnoreCase(bene.getPartitaIvaSiglaStato()) 
						&& (bene.getPartitaIva()==null || (bene.getPartitaIva()!=null && bene.getPartitaIva().trim().length() > 12))){
					message = "error.partitaIva.lunghezza12";
				}
			}
		}

		if(StringUtils.isNotBlank(message)){
			errors.rejectValue("partitaIva", message);
		}
	}
	
	public static void controlloPartitaIvaVer(SoggettoForm soggetto, Errors errors, BeneForm bene) {
		String message="";

		if(soggetto!=null){
			if(soggetto.getPartitaIvaSiglaStato()!=null 
					&& soggetto.getPartitaIvaSiglaStato().trim().length()>0){
				if(Constants.ITALIA_SIGLA.equalsIgnoreCase(soggetto.getPartitaIvaSiglaStato()) && (soggetto.getPartitaIva()!=null && soggetto.getPartitaIva().trim().length() > 0 && soggetto.getPartitaIva().trim().length() < 12)){
					message = controllaPIVA(soggetto.getPartitaIva());
				}else if (!Constants.ITALIA_SIGLA.equalsIgnoreCase(soggetto.getPartitaIvaSiglaStato()) 
						&& (soggetto.getPartitaIva()==null || (soggetto.getPartitaIva()!=null && soggetto.getPartitaIva().trim().length() > 12))){
					message = "error.partitaIva.lunghezza12";
				}

			}
		}else if(bene!=null){
			if(bene.getPartitaIvaSiglaStato()!=null 
					&& bene.getPartitaIvaSiglaStato().trim().length()>0){
				if(Constants.ITALIA_SIGLA.equalsIgnoreCase(bene.getPartitaIvaSiglaStato())){
					message = controllaPIVA(bene.getPartitaIva());
				}else if (!Constants.ITALIA_SIGLA.equalsIgnoreCase(bene.getPartitaIvaSiglaStato()) 
						&& (bene.getPartitaIva()==null || (bene.getPartitaIva()!=null && bene.getPartitaIva().trim().length() > 12))){
					message = "error.partitaIva.lunghezza12";
				}
			}
		}

		if(StringUtils.isNotBlank(message)){
			errors.rejectValue("soggetto.partitaIva", message);
		}
	}	

	public static String controllaPIVA(String pi) {
		int i, c, s;
		//		if (pi.length() == 0) {
		//			return false;
		//		}
		if (pi==null || pi.length() != 11) {
			return "error.partitaIva.lunghezza";
		}
		for (i = 0; i < 11; i++) {
			if (pi.charAt(i) < '0' || pi.charAt(i) > '9')
				return "error.partitaIva.caratteriNonValidi";
		}
		s = 0;
		for (i = 0; i <= 9; i += 2) {
			s += pi.charAt(i) - '0';
		}
		for (i = 1; i <= 9; i += 2) {
			c = 2 * (pi.charAt(i) - '0');
			if (c > 9) {
				c = c - 9;
			}
			s += c;
		}
		if ((10 - s % 10) % 10 != pi.charAt(10) - '0') {
			return "error.partitaIva.codiceControllo";
		}
		return "";
	}

	public static String convertDateToString(Date data) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
		sdf.setTimeZone(tz);
		if (data != null) {
			return sdf.format(data);
		}
		return "";
	}

	public static String decryptString(String strEncrypted) throws IOException, CryptoException {

		byte[] idB = decode64(strEncrypted);
		byte[] idCrypt = DecryptDati(idB);
		return new String(idCrypt, "UTF-8");
	}

	public static String encryptString(String str) throws IOException, CryptoException {

		byte[] idB = str.getBytes("UTF-8");
		byte[] idCrypt = CryptDati(idB);
		return encode64(idCrypt);
	}

	public static String formattaData(Date dataD, String formato) {

		String fin = "";
		SimpleDateFormat sfinale;
		sfinale = new SimpleDateFormat(formato);
		sfinale.setLenient(false);
		fin = sfinale.format(dataD);
		return fin;
	}

	public static byte[] fromHexString(String s) {

		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException("fromHexString requires an even number of hex characters");
		}
		byte[] b = new byte[stringLength / 2];
		for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
			int high = charToNibble(s.charAt(i));
			int low = charToNibble(s.charAt(i + 1));
			b[j] = (byte) ((high << 4) | low);
		}
		return b;
	}

	public static String generatePassword(String id) {

		String randomPassword = RandomStringUtils.randomAlphanumeric(7);
		return randomPassword.substring(0, 4) + id + randomPassword.substring(4, randomPassword.length());
	}

	public static String getDescrizioneCampoEstero(String descrizioneCampoEstero, ComboForm comuneCombo) {

		if ((descrizioneCampoEstero == null) && (comuneCombo != null) && (((comuneCombo.getId() == null) || comuneCombo.getId().equals("")) && (comuneCombo.getDescrizione() != null))) {
			return comuneCombo.getDescrizione();
		} else {
			return descrizioneCampoEstero;
		}
	}

	public static String getDescrizioneCampoEsteroAndSetComuneCombo(String descrizioneCampoEsteroRef, String descrizioneCampoEsteroVal, ComboForm comuneComboRef) {

		descrizioneCampoEsteroRef = getDescrizioneCampoEstero(descrizioneCampoEsteroVal, comuneComboRef);
		if (descrizioneCampoEsteroVal != null) {
			if (comuneComboRef == null) {
				comuneComboRef = new ComboForm();
			}
			comuneComboRef.setId(null);
			comuneComboRef.setDescrizione(descrizioneCampoEsteroVal);
		}
		return descrizioneCampoEsteroRef;
	}

	public static ProcedimentoForm getProcedimentoByListaSoggetti(ProcedimentoForm procedimento, List<SoggettoForm> listaSoggetti, int idTipoFascicolo) {

		if (procedimento == null) {
			procedimento = new ProcedimentoForm();
		}
		boolean ordinaria = false;
		boolean antimafia = false;
		boolean antiterrorismo = false;
		for (int i = 0; i < listaSoggetti.size(); i++) {
			SoggettoForm sogg = listaSoggetti.get(i);
			ComboForm tipologPrev = sogg.getTipologiaPrevenzione();
			if ((tipologPrev != null) && (tipologPrev.getId() != null)) {
				try {
					String tipoPrev = tipologPrev.getId();
					if (tipoPrev.equals(Constants.TIPOPREV_ORDINARIA)) {
						ordinaria = true;
					} else
						if (tipoPrev.equals(Constants.TIPOPREV_ANTIMAFIA)) {
							antimafia = true;
						} else
							if (tipoPrev.equals(Constants.TIPOPREV_ANTITERRORISMO)) {
								antiterrorismo = true;
							}
				} catch (Exception e) {}
			}
		}
		procedimento.setOrdinaria(ordinaria);
		procedimento.setAntimafia(antimafia);
		procedimento.setAntiterrorismo(antiterrorismo);
		if(Constants.TIPO_FASCICOLO_ACCERTAMENTO==idTipoFascicolo){
			procedimento.setTipoProcedimento(getComboFormTipoProcGenAccertamento(listaSoggetti));
		}else if(Constants.TIPO_FASCICOLO_MERITO==idTipoFascicolo) {
			procedimento.setTipoProcedimento(getComboFormTipoProcGen(listaSoggetti));
		}
		return procedimento;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isDefinito(Object target) {

		if (target == null) {
			return false;
		}
		if (target instanceof ComboForm) {
			return !( ((ComboForm) target).getId() == null || ((ComboForm) target).getId().trim().isEmpty() || ((ComboForm) target).getId().equals(Constants.NO_ITEM_SELECTED));
		}
		if (target instanceof String) {
			return !((String) target).trim().isEmpty();
		}
		if (target instanceof Collection) {
			return !((Collection) target).isEmpty();
		}
		return true; // da generalizzare secondo necessità
	}

	public static ProfiloForm parseProfiloFromString(String profiloStr, String separatore) {

		ProfiloForm profiloUtente = new ProfiloForm();
		StringTokenizer st = new StringTokenizer(profiloStr, separatore);
		int tokens = st.countTokens();
		// se i token sono 5 significa che in coda c'è il commonName dell'utente
		while (st.hasMoreTokens()) {
			profiloUtente.setDistretto(st.nextToken());
			profiloUtente.setCitta(st.nextToken());
			profiloUtente.setDescrizioneUfficio(st.nextToken());
			profiloUtente.setRuolo(st.nextToken());
			if (tokens == 5) {
				profiloUtente.setCommonName(st.nextToken());
			}
		}
		return profiloUtente;
	}

	public static String readProperties(String key) throws IOException {

		ResourceBundle bundle = ResourceBundle.getBundle("it/giustizia/smp/properties/application", Locale.getDefault());
		String properties = bundle.getString(key);
		return properties;
	}

	public static void streamToFile(String fileName, byte[] dati) throws Exception {

		OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
		out.write(dati);
		out.close();
		return;
	}

	public static Date stringToDate(String date, String format) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date dataConvertita = null;
		try {
			dataConvertita = formatter.parse(date);
		} catch (ParseException e) {
			System.out.println("ERRORE stringToDate");
		}
		
		return dataConvertita;
	}

	public static byte[] toBinArray(String hexStr) {

		byte bArray[] = new byte[hexStr.length() / 2];
		for (int i = 0; i < (hexStr.length() / 2); i++) {
			byte firstNibble = Byte.parseByte(hexStr.substring(2 * i, 2 * i + 1), 16); // [x,y)
			byte secondNibble = Byte.parseByte(hexStr.substring(2 * i + 1, 2 * i + 2), 16);
			int finalByte = (secondNibble) | (firstNibble << 4); // bit-operations
			// only with
			// numbers,
			// not
			// bytes.
			bArray[i] = (byte) finalByte;
		}
		return bArray;
	}

	public static String toHexString(byte[] b) {

		StringBuffer sb = new StringBuffer(b.length * 2);
		for (byte element : b) {
			// look up high nibble char
			sb.append(hexChar[(element & 0xf0) >>> 4]); // fill left
			// with zero
			// bits
			// look up low nibble char
			sb.append(hexChar[element & 0x0f]);
		}
		return sb.toString();
	}

	public static String toHexString(byte[] b, int len) {

		StringBuffer sb = new StringBuffer(len * 2);
		for (int i = 0; i < len; i++) {
			// look up high nibble char
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]); // fill left with
			// zero bits
			// look up low nibble char
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/*
	 * Checks for invalid characters
	 * in email addresses
	 */
	@SuppressWarnings("unused")
	public static String validazioneEmail(String input) {

		String output = "";
		// Checks for email addresses starting with
		// inappropriate symbols like dots or @ signs.
		Pattern p = Pattern.compile("^\\.|^\\@");
		Matcher m = p.matcher(input);
		if (m.find()) {
			output = "Indirizzo email errato. Carattere iniziale" + " dots o @.";
		}
		// Checks for email addresses that start with
		// www. and prints a message if it does.
		p = Pattern.compile("^WWW\\.");
		m = p.matcher(input);
		if (m.find()) {
			output = "Indirizzo email errato. Soli i siti web" + " iniziano con \"WWW.\"";
		}
		// p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
		p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		if (m.find() == true) {
			output = "";
		} else {
			output = "Email errata";
		}
		/*
		 * boolean result = m.find();
		 * boolean deletedIllegalChars = false;
		 * while(result) {
		 * deletedIllegalChars = true;
		 * m.appendReplacement(sb, "");
		 * result = m.find();
		 * }
		 * m.appendTail(sb);
		 * input = sb.toString();
		 * if (deletedIllegalChars) {
		 * output = "Indirizzo email con caratteri errati: " +
		 * "spazio o virgola";
		 * }
		 */
		if (output.length() > 0) {
			return "error.pecErrata";
		}
		return "";
	}

	private static int charToNibble(char c) {

		if (('0' <= c) && (c <= '9')) {
			return c - '0';
		} else
			if (('a' <= c) && (c <= 'f')) {
				return c - 'a' + 0xa;
			} else
				if (('A' <= c) && (c <= 'F')) {
					return c - 'A' + 0xa;
				} else {
					throw new IllegalArgumentException("Invalid hex character: " + c);
				}
	}

	/**
	 * il metodo consente di cifrare con algoritmo 3DES dati in formato binario
	 * 
	 * @param origine
	 *            dati da cifrare in formato binario
	 * @return dati cifrati in formato binario
	 */
	private static byte[] CryptDati(byte[] origine) throws CryptoException {

		if (origine == null) {
			throw new CryptoException(new NullPointerException("CryptDati origine non trovata"));
		}
		byte[] crittati = null;
		try {
			DESedeKeySpec theKeySpec = new DESedeKeySpec(readProperties("cryptographicKey").getBytes());
			SecretKeyFactory theSkf = SecretKeyFactory.getInstance("DESede");
			SecretKey theDesKey = theSkf.generateSecret(theKeySpec);
			Cipher desCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			desCipher.init(Cipher.ENCRYPT_MODE, theDesKey);
			byte[] daCrittare = origine;
			crittati = desCipher.doFinal(daCrittare);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
		return crittati;
	}

	private static byte[] decode64(String str) throws IOException {

		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(str);
	}

	/**
	 * il metodo consente di decifrare dati cifrati con algoritmo 3DES
	 * 
	 * @param origine
	 *            dati cifrati
	 * @return dati decifrati
	 */
	private static byte[] DecryptDati(byte[] origine) throws CryptoException {

		if (origine == null) {
			throw new CryptoException(new NullPointerException("CryptDati origine non trovata"));
		}
		byte[] DeCrittati = null;
		try {
			DESedeKeySpec theKeySpec = new DESedeKeySpec(readProperties("cryptographicKey").getBytes());
			SecretKeyFactory theSkf = SecretKeyFactory.getInstance("DESede");
			SecretKey theDesKey = theSkf.generateSecret(theKeySpec);
			Cipher desCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			desCipher.init(Cipher.DECRYPT_MODE, theDesKey);
			DeCrittati = desCipher.doFinal(origine);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
		return DeCrittati;
	}

	private static String encode64(byte[] bytes) {

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(bytes);
	}

	private static ComboForm getComboFormTipoProcGen(List<SoggettoForm> listaSoggetti) {

		ComboForm tipoProcGen = new ComboForm();
		boolean misuraPersonale = false;
		boolean misuraPatrimoniale = false;
		for (int i = 0; i < listaSoggetti.size(); i++) {
			SoggettoForm sogg = listaSoggetti.get(i);
			ComboForm ipotesiMis = sogg.getIpotesiMisura();
			if ((ipotesiMis != null) && (ipotesiMis.getId() != null)) {
				try {
					String tipoProc = ipotesiMis.getId();
					if (tipoProc.equals(Constants.TIPOPROC_PATRIMONIALE)) {
						misuraPatrimoniale = true;
					} else
						if (tipoProc.equals(Constants.TIPOPROC_PERSONALE)) {
							misuraPersonale = true;
						} else
							if (tipoProc.equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE)) {
								misuraPersonale = true;
								misuraPatrimoniale = true;
							}
				} catch (Exception e) {}
			}
		}
		if (misuraPersonale && !misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PERSONALE);
		}
		if (!misuraPersonale && misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PATRIMONIALE);
		}
		if (misuraPersonale && misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE);
		}
		return tipoProcGen;
	}
	
	private static ComboForm getComboFormTipoProcGenAccertamento(List<SoggettoForm> listaSoggetti) {

		ComboForm tipoProcGen = new ComboForm();
		boolean misuraPersonale = false;
		boolean misuraPatrimoniale = false;
		for (int i = 0; i < listaSoggetti.size(); i++) {
			SoggettoForm sogg = listaSoggetti.get(i);
			ComboForm ipotesiMis = sogg.getIpotesiMisura();
			if ((ipotesiMis != null) && (ipotesiMis.getId() != null)) {
				try {
					String tipoProc = ipotesiMis.getId();
					if (tipoProc.equals(Constants.TIPOPROC_PATRIMONIALE_ACCERTAMENTO)) {
						misuraPatrimoniale = true;
					} else
						if (tipoProc.equals(Constants.TIPOPROC_PERSONALE_ACCERTAMENTO)) {
							misuraPersonale = true;
						} else
							if (tipoProc.equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE_ACCERTAMENTO)) {
								misuraPersonale = true;
								misuraPatrimoniale = true;
							}
				} catch (Exception e) {}
			}
		}
		if (misuraPersonale && !misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PERSONALE_ACCERTAMENTO);
		}
		if (!misuraPersonale && misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PATRIMONIALE_ACCERTAMENTO);
		}
		if (misuraPersonale && misuraPatrimoniale) {
			tipoProcGen.setId(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE_ACCERTAMENTO);
		}
		return tipoProcGen;
	}

	public Date convertStringToDate(String data, String format) {

		Date ritorno = null;
		if (("".equals(data)) || (data == null)) {
			return ritorno;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			ritorno = sdf.parse(data);
		} catch (ParseException e) {
			return null;
		}
		return ritorno;
	}

	public void readBinFile(String fileName) {

		File file = null;
		int i_data = 0;
		double d_data = 0.0;
		// Get the file from the argument line.
		file = new File(fileName);
		try {
			// Wrap the FileInputStream with a DataInputStream
			FileInputStream file_input = new FileInputStream(file);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				try {
					i_data = data_in.readInt();
					// i_data = data_in.read(bb, 1, 1);
					d_data = data_in.readDouble();
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
				System.out.printf("%3d. Data = %8.3e %n", i_data, d_data);
			}
			data_in.close();
		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
	}

	public static IndirizzoForm costruisciIndirizzo(BeneForm beneForm) {

		IndirizzoForm indirizzo = new IndirizzoForm();
		String denominazione = null;
		String descIndirizzo = beneForm.getIndirizzo().getDenominazione();
		String descComune = beneForm.getComune().getDescrizione();
		String localita = beneForm.getLocalita();
		String frazione = beneForm.getFrazione();
		String prov = beneForm.getProvincia().getId();
		
		
		if (beneForm.getAbi() != null && Constants.EnteFittizio.ABI.equals(beneForm.getAbi())){
			descIndirizzo = Constants.EnteFittizio.INDIRIZZO;
			descComune = Constants.EnteFittizio.INDIRIZZO;
			
			frazione = Constants.EnteFittizio.INDIRIZZO;
		}
		
		denominazione = descIndirizzo + " - " + descComune;
		
		if ((localita != null) && (frazione != null)) {
			denominazione = denominazione + " - loc." + localita + ", " + frazione;
		} else {
			if (localita != null) {
				denominazione = denominazione + " - loc." + localita;
			}
			if (frazione != null) {
				denominazione = denominazione + " - " + frazione;
			}
		}
		denominazione = denominazione + " (" + prov + ")";
		indirizzo.setDenominazione(denominazione);
		indirizzo.setCap("" + beneForm.getCap());
		return indirizzo;
	}

	/**
	 * 
	 * @param comboList
	 * @param value
	 * @return label corrispondente al value della comboList, blank se non trova corrispondenza
	 */
	public static String findLabelByValue(List<ComboForm> comboList, String value) {

		Iterator<ComboForm> it = comboList.iterator();

		while (it.hasNext()) {
			ComboForm el = it.next();
			if(el.getId().equals(value)){
				return el.getDescrizione();
			}
		}

		return "";   
	}

	/**
	 * 
	 * @param codTipAtto
	 * @return true se codTipAtto è un atto che presente anche in tabella T_Decreto
	 */
	public static boolean isTDecretoRecord(String codTipAtto) {

		return (Constants.TIPO_ATTO_DECRETO.equals(codTipAtto) || 
				Constants.TIPO_ATTO_DECRETO_II_GRADO.equals(codTipAtto) ||
				Constants.TIPO_ATTO_SENTENZA_CASSAZIONE.equals(codTipAtto) ||
				Constants.TIPO_ATTO_SENTENZA_CASSAZIONE_DA_CDA.equals(codTipAtto));
	}
	
	public static boolean isTImpugnazioneRecord(String codTipAtto) {

		return (Constants.TIPO_ATTO_IMPUGNAZIONE.equals(codTipAtto) || Constants.TIPO_ATTO_IMPUGNAZIONE_II_GRADO.equals(codTipAtto));
	}	

	/**
	 * 
	 * @param codTipAtto
	 * @return true se codTipAtto è un atto che presente anche in tabella T_Istanza
	 */
	public static boolean isTIstanzaRecord(String codTipAtto) {

		return (Constants.TIPO_ATTO_ISTANZA.equals(codTipAtto) || 
				Constants.TIPO_ATTO_PROPOSTA.equals(codTipAtto) ||
				Constants.TIPO_ATTO_FONTE.equals(codTipAtto) ||
				Constants.TIPO_ATTO_ACCERTAMENTO.equals(codTipAtto)||
				Constants.TIPO_ATTO_ISTANZA_CAM.equals(codTipAtto)||
				Constants.TIPO_ATTO_RELAZIONE_AMM.equals(codTipAtto));
	}
	
	public static boolean isTAttoGenericoRecord(String codTipAtto) {
		return (Constants.TipoAtto.TIPO_ATTO_GENERICO_S.equals(codTipAtto));
	}

	public static boolean isTProvvFGRecord(String codTipAtto) {

		return (Constants.TIPO_ATTO_PROVVEDIMENTO.equals(codTipAtto));
	}
	
	public static Date removeTime(Date data) {
		if (data == null)
			return data;
		String sd = formatDate(data, "dd/MM/yyyy");

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.ITALY);
		java.util.Date d = null;
		try {
			d = df.parse(sd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * Restiuisce la data in formato stringa secondo il formato specificato e
	 * conforma ai formati di SimpleDateFormat, Se la data è null ritorna ""
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		String retDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			retDate = sdf.format(date);
		} catch (Exception e) {
		}
		// System.out.println("Data convertita: " + retDate);
		return retDate;

	}

	/**
	 * Restiuisce la data in formato stringa secondo "dd/MM/yyyy" e conforma ai
	 * formati di SimpleDateFormat, Se la data è null ritorna ""
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "dd/MM/yyyy");

	}


	/**
	 * 
	 * @param beneSoggettoForm
	 * @param operation
	 * @param quota
	 * @param quotaEsistente
	 * @param tipoCarattereMisura
	 * @return
	 * date due quote restituisce la somma/differenza 
	 * a seconda del tipo del carattere di misura (sequestri vari o revoca del sequestro)
	 */
	public static double sommaQuotaPercentuale(BeneSoggettoForm beneSoggettoForm, String operation, double quota, double quotaEsistente, String tipoCarattereMisura){
		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.CREATE)){

			return aggiornaQuotaPercentuale(quota, quotaEsistente,
					tipoCarattereMisura);

		}

		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.UPDATE)){
			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					) ){
				quotaEsistente -= beneSoggettoForm.getQuotaSequestroOriginaria();
			}else if(tipoCarattereMisura!=null 
					&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA))){{

								quotaEsistente += beneSoggettoForm.getQuotaSequestroOriginaria();
							}
			}

			return aggiornaQuotaPercentuale(quota, quotaEsistente,
					tipoCarattereMisura);
		}

		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.DELETE)){
			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_NON_CONVALIDATO))
					) ){
				quotaEsistente -= beneSoggettoForm.getQuotaSequestroOriginaria();
			}else if(tipoCarattereMisura!=null 
					&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA))){{
								quotaEsistente += beneSoggettoForm.getQuotaSequestroOriginaria();
							}
			}

		}

		return quotaEsistente;
	}


	private static double aggiornaQuotaPercentuale(double quota,
			double quotaEsistente, String tipoCarattereMisura) {
		double sommaQuota=0;
		if(tipoCarattereMisura!=null && (
				(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
				|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
				|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
				) ){


			sommaQuota = quota+quotaEsistente;

		}

		if(tipoCarattereMisura!=null 
				&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA)
						|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_NON_CONVALIDATO)))){


			sommaQuota = quota-quotaEsistente;

		}
		return sommaQuota;
	}

	/**
	 * 
	 * @param beneSoggettoForm
	 * @param operation (a seconda di quale tipo di operazione 
	 * 		  andrò a fare cambiarà la logica di calcolo. Esempio: 'Update' va prima stornato la percentuale esistente e poi sommata quella nuova
	 * @param frazione1
	 * @param frazione2
	 * @param tipoCarattereMisura
	 * @return
	 */

	public static Frazione sommaFrazioniSequestrate(BeneSoggettoForm beneSoggettoForm, String operation,
			Frazione frazione1, Frazione frazione2, String tipoCarattereMisura) {
		Frazione frazioneSequestroAggiornataPreModifica = new Frazione();
		Frazione frazioneRitorno  = new Frazione();

		Frazione frazioneOriginaria = new Frazione();
		frazioneOriginaria.setNumeratore(beneSoggettoForm.getQuotaFrazioneNumeratoreSeqOriginaria());
		frazioneOriginaria.setDenominatore(beneSoggettoForm.getQuotaFrazioneDenominatoreSeqOriginaria());

		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.CREATE)){
			frazioneRitorno= minimoComuneMultiplo(frazione1, frazione2,  tipoCarattereMisura);

		}
		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.UPDATE)){

			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					) ){
				frazioneSequestroAggiornataPreModifica = minimoComuneMultiplo(frazione1,
						frazioneOriginaria, Constants.TipoCarattereMisura.SEQUESTRO_REVOCA);
			}else if(tipoCarattereMisura!=null 
					&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA))){
				frazioneSequestroAggiornataPreModifica = minimoComuneMultiplo(frazione1, frazioneOriginaria,  Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO);
			}

			frazioneRitorno= minimoComuneMultiplo(frazioneSequestroAggiornataPreModifica,
					frazione2,  tipoCarattereMisura);
		}


		if(operation.equalsIgnoreCase(Constants.TipiOperazioneCRUD.DELETE)){

			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					) ){
				frazioneSequestroAggiornataPreModifica = minimoComuneMultiplo(frazione1,frazioneOriginaria, Constants.TipoCarattereMisura.SEQUESTRO_REVOCA);
			}else if(tipoCarattereMisura!=null && (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA)
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_NON_CONVALIDATO)))){
				frazioneSequestroAggiornataPreModifica = minimoComuneMultiplo(frazione1, frazioneOriginaria, Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO);
			}
			frazioneRitorno= minimoComuneMultiplo(frazioneSequestroAggiornataPreModifica,
					frazione2,  tipoCarattereMisura);
		}
		return frazioneRitorno;
	}

	/** Metodo statico che date due frazioni restituisce una nuova 
	 * ottenuta con il minimo comune multiplo.
	 * Se le due frazione saranno sommate o sottratte a seconda del
	 * Tipo Carattere Misura
	 * */
	public static Frazione minimoComuneMultiplo(Frazione frazione1,
			Frazione frazione2,
			String tipoCarattereMisura) {
		Frazione frazioneRitorno = new Frazione();
		int numeratore = 0;
		int denominatore = 0;
		int numFrazione1 = frazione1.getNumeratore();
		int denFrazione1 = frazione1.getDenominatore();
		int numFrazione2 = frazione2.getNumeratore();
		int denFrazione2 = frazione2.getDenominatore();
		try{
			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					)){



				if(numFrazione1>0 && denFrazione1>0){
					int mcm = mcm(denFrazione1,denFrazione2);
					numeratore = (numFrazione1*(mcm/denFrazione1)) + (numFrazione2*(mcm/denFrazione2));
					denominatore = mcm;
				}else{
					numeratore = numFrazione2;
					denominatore = denFrazione2;
				}

				frazioneRitorno.setNumeratore(numeratore);
				frazioneRitorno.setDenominatore(denominatore);


			}

			if(tipoCarattereMisura!=null 
					&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA)
							|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_NON_CONVALIDATO)))){

				if(numFrazione1>0 && denFrazione1>0){
					int mcm = mcm(denFrazione1,denFrazione2);
					numeratore = (numFrazione1*(mcm/denFrazione1)) - (numFrazione2*(mcm/denFrazione2));
					if(numeratore==0){
						denominatore=0;
					}else{
						denominatore = mcm;
					}
				}else{
					numeratore = numFrazione2;
					denominatore = denFrazione2;
				}

				frazioneRitorno.setNumeratore(numeratore);
				frazioneRitorno.setDenominatore(denominatore);
			}

		}catch (Exception e) {
		}
		return frazioneRitorno;
	}


	/**
	 * Precondizione: a e b devono essere di tipo int
	 * Postcondizione: calcola il valore del mcm
	 * tra a e b
	 * @param a primo intero
	 * @param b secondo intero
	 * @return mcm (minimo comune multiplo)
	 */
	private static int mcm(int a, int b) {
		int mcm = 1;
		int aIniz = a;
		boolean continua = true;

		if(a==0 && b == 0){
			return 0;
		}else if (a==0){
			return b;
		}else if (b==0){
			return a;
		}

		while (continua) {
			int restoA = a % 2;
			int restoB = b % 2;

			if (restoA == 0 && restoB == 0) {
				int tempA = a / 2;
				a = tempA;
				int tempB = b / 2;
				b = tempB;
			} else {
				mcm = aIniz * b;
				continua = false;
			}

		}

		return mcm;
	}

	public static String creaCodiceFascicolo(String prefisso, int tipoProcedimento, String idProcedimento, String descUfficioGiudiziario) {

		int annoProcedimento = GregorianCalendar.getInstance().get(Calendar.YEAR);
		String codiceFascicolo = prefisso+"-" + tipoProcedimento + "-" + annoProcedimento + "/" + idProcedimento + "-" + descUfficioGiudiziario;

		return codiceFascicolo;
	}


	public static void popolaDocumento(AttoForm attoForm, TAtto atto) {
		byte b[] = null;
		
		if(attoForm.getAllegato().getFileData()!=null)
			b = attoForm.getAllegato().getFileData().getBytes();   
		if (b!=null && b.length > 0) {
			Blob blob = Hibernate.createBlob(b);
			atto.setFileDocumento(blob);
			atto.setDescNomeDocumento(attoForm.getAllegato().getFileName());
			atto.setDescTipoDocumento(attoForm.getAllegato().getContentType());
		}else if(attoForm.getAllegato()!=null){
			atto.setFileDocumento(attoForm.getAllegato().getDocumento());		
		}else{
			atto.setFileDocumento(null);
		}
		
		
	}

	public static BigInteger longToBigInteger(long idDocumentale) {

		return new BigInteger(String.valueOf(idDocumentale));
	}

	public static BigInteger longToBigInteger(Long idDocumentale) {

		if(idDocumentale != null){
			return new BigInteger(String.valueOf(idDocumentale));
		}else{
			return new BigInteger("0");
		}
	}
	
	/**
	 * Verifica se la comboForm è selezionata o meno
	 * @param comboForm
	 * @return true se selezionato, altrimenti false
	 */
	public static boolean isComboFormSelected(ComboForm comboForm){
		
		return (comboForm != null && !StringUtils.isEmpty(comboForm.getId()) && NumberUtils.toInt(comboForm.getId(), 0) >= 0);
	}

	
	/**
	 * Formatta correttamente la lista passata di soggetti, creando una combo di soggetti, 
	 * differenziati tra soggetti fisici e soggetti giuridici
	 * @param soggettiForm
	 * @return List<ComboForm>
	 */
	public List<ComboForm> creaComboSoggetti(List<SoggettoForm> soggettiForm) {
		List<ComboForm> listaCbo = new ArrayList<ComboForm>();
		for (SoggettoForm soggettoForm : soggettiForm) {
			if("F".equalsIgnoreCase(soggettoForm.getTipoSoggetto())) {
				listaCbo.add(new ComboForm(String.valueOf(soggettoForm.getIdSoggetto()), String.valueOf(soggettoForm.getCognome() +" "+ soggettoForm.getNome())));
			}else{
				listaCbo.add(new ComboForm(String.valueOf(soggettoForm.getIdSoggetto()), String.valueOf(soggettoForm.getRagioneSociale())));
			}
		}
		return listaCbo;
	}
	
	/**
	 * carica la combo categoria bene passando in input l'id della tipologia
	 * @param id
	 * @return
	 */
	public List<ComboForm> caricaComboCategoria(String id) {

		List<ComboForm> lCbo = new ArrayList<ComboForm>();
		BeneForm bf = new BeneForm();
		bf.setTipologia(new ComboForm(id, null)); 
		lCbo = commonService.listaCategorieDelBene(bf);
		return lCbo;
	}
	
	/**
	 * carica la combo sotto categoria bene passando in input l'id della categoria
	 * @param id
	 * @return
	 */
	public List<ComboForm> caricaComboSottoCategoria(String id) {

		List<ComboForm> lCbo = new ArrayList<ComboForm>();
		BeneForm bf = new BeneForm();
		bf.setCategoria(new ComboForm(id, null)); 
		lCbo = commonService.listaSottocategorieDelBene(bf);
		return lCbo;
	}
	
	public List<ComboForm> creaComboAvvocati(List<TAvvocati> avvocati) {
		List<ComboForm> listaCbo = new ArrayList<ComboForm>();
		for (TAvvocati avvocato : avvocati) {
				listaCbo.add(new ComboForm(String.valueOf(avvocato.getCodiKIdAvvocato()), String.valueOf(avvocato.getDescCognomeAvv() +" "+ avvocato.getDescNomeAvv())));	
		}
		return listaCbo;
	}	

	public static String limitaStringa(String stringa, int len){
		return StringUtils.left(StringUtils.trim(stringa), len);
	}
	
	static public String formatDecimalNumber(double value) {
		return formatDecimalNumber(value, "###,##0.00");
	}
	
	static public String formatDecimalNumber(double value, String pattern) {
		DecimalFormat myFormatter = (DecimalFormat) NumberFormat.getInstance(Locale.ITALIAN);
		myFormatter.applyPattern(pattern);
		return myFormatter.format(value);
	}

	static public boolean isValid(Map<?, ?> in) {
		return (in != null) && !in.isEmpty();
	}

	static public boolean isValid(Collection<?> in) {
		return (in != null) && !in.isEmpty();
	}

	static public boolean isValid(String in) {
		return (in != null) && !in.trim().isEmpty();
	}

	static public boolean isValid(Character in) {
		return (in != null);
	}

	static public boolean isValid(ComboForm in) {
		return (in != null);
	}

	static public boolean isValid(BeneForm in) {
		return (in != null);
	}

	static public boolean isValid(Double in) {
		return isValid(in, false);
	}

	static public boolean isValid(Double in, boolean isZeroAccepted) {
		return ((in != null) && (isZeroAccepted || (in.doubleValue() > 0)));
	}

	static public boolean isValid(BigDecimal in) {
		return isValid(in, false);
	}

	static public boolean isValid(BigDecimal in, boolean isZeroAccepted) {
		return ((in != null) && (isZeroAccepted || (in.doubleValue() > 0)));
	}

	static public boolean isNumber(String in) {
		return isValid(in) ? StringUtils.isNumeric(in.trim()) : false;
	}

	static public boolean hasValidId(ComboForm in) {
		return (in != null) && isValid(in.getId());
	}

	static public double getValidDouble(String value) {
		return isValid(value) ? Double.parseDouble(value.trim().replaceAll("\\.", "").replaceAll("\\,", "\\.")) : 0;
	}

	static public BigDecimal getValid(BigDecimal value) {
		return isValid(value, true) ? value.setScale(2, BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);
	}

	static public Blob serializeInBlob(byte[] byteArray) {
		if(byteArray == null)
			return null;
		return Hibernate.createBlob(byteArray);
	}
	
	static public Clob serializeInClob(byte[] byteArray) {
		return Hibernate.createClob(byteArray.toString());
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar out = null;
		if (date != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			try {
				out = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	public static BigDecimal getImporto(String in) {
		BigDecimal out = BigDecimal.ZERO;
		if ((in != null) && (StringUtils.isNumeric(in))) {
			try {
				out = BigDecimal.valueOf(Double.parseDouble(in));
			} catch (Exception e) {
			}
		} else {
			try {
				out = BigDecimal.valueOf(Double.parseDouble(in.replaceAll("[.]", "").replace(",", ".")));
			} catch (Exception e) {
			}
		}
		return out;
	}

	public static BigDecimal getImporto_Valuta(BigDecimal euro, BigDecimal valuta, BigDecimal importo, boolean isValuta) {
		// isValuta = true: 
		//		preferenza agli importi in valuta,
		// altrimenti
		// 		preferenza al valore contenuto nel campo 'importo' dell'oggetto in input
		return (isValuta) ?
				(isValid(euro) ? euro : (isValid(valuta) ? valuta : importo)) :
				(isValid(importo) ? importo : (isValid(euro) ? euro : (isValid(valuta) ? valuta : BigDecimal.ZERO)));
	}

	public static String getImporto(BeneForm in) {
		//return getImporto(((in != null) ? (isValid(in.getValutaEur()) ? in.getValutaEur() : (isValid(in.getValutaEst()) ? in.getValutaEst() : getImporto(in.getImporto()))) : BigDecimal.ZERO));
		return getImporto(((in != null) ? getImporto_Valuta(in.getValutaEur(), in.getValutaEst(), getImporto(in.getImporto()), false) : BigDecimal.ZERO));
	}

	public static String getImporto(BigDecimal in) {
		return getImporto(getValid(in).setScale(2 , BigDecimal.ROUND_UP).doubleValue());
	}

	public static String getImporto(double in) {
		return formatDecimalNumber(in, "0.00");
	}

	public static Character getValid(Character in) {
		return isValid(in) ? in : Character.valueOf('_');
	}

	public static String convert(Blob blob) {
		String out = null;
		try {
			byte[] bdata = blob.getBytes(1, (int) blob.length());
			out = new String(bdata);
		} catch (Exception e) {}
		return out;
	}

	public static String importoTotaleParziale(String importo, String importoTotale) {
		return (getImporto(importo).compareTo(getImporto(importoTotale)) < 0) ? "P" : "T";
	}

	public static final String formattaImporto(final BigDecimal importo) {
		String res = "";
		if (importo != null) {
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			format.setMaximumFractionDigits(2);
			res = format.format(importo);
		}
		return res;
	}

	public static String getSystemProperty(String in, String def) {
		String out = def;
		try {
			out = StringUtils.defaultString(System.getProperties().getProperty(in), def);
			if (out!=null) {
				out = out.trim();
			}
		} catch (Exception e) {
			System.out.println("!!! " + e.getMessage() + " !!!");
		}
		return out;
	}

	public static Object sendAndReceive(WebServiceTemplate serviceTemplate, String uri, Object in) throws Exception {
		setServiceUri(serviceTemplate, uri);
		return serviceTemplate.marshalSendAndReceive(in);
	}
	

	
	public static Object sendAndReceiveGL(WebServiceTemplate serviceTemplate, String uri, Object in, final String codugne) throws Exception {
		setServiceUri(serviceTemplate, uri);
		return serviceTemplate.marshalSendAndReceive(in, new WebServiceMessageCallback() {
		    public void doWithMessage(WebServiceMessage message) {
		        SoapMessage soapMessage = (SoapMessage) message;
		    	 //QName datiAutenticazione = new QName("http://it/mig/sippi/service/richiestaCertificato/type","DATI_AUTENTICAZIONE");
		        QName datiAutenticazione = new QName("http://www.netserv.it/anag/security","InvocationDomain");
		    	 //QName datiAutenticazione = new QName("","DATI_AUTENTICAZIONE");
		    	 SoapHeaderElement e = soapMessage.getSoapHeader().addHeaderElement(datiAutenticazione);
		    	 System.out.println("codugne:" + codugne);
		    	 e.addAttribute(new QName("group"), codugne);
		    	 e.addAttribute(new QName("name"), "GLPEC");
		    	 
		    }
		});
	}

	public static void setServiceUri(WebServiceTemplate serviceTemplate, String uri) {
		serviceTemplate.setDefaultUri(Utility.getSystemProperty(uri, serviceTemplate.getDefaultUri()));
	}

	public static boolean isPolizzaNonBancaria(BeneForm in) {
		return isValid(in) && isPolizzaNonBancaria(in.getCategoria(), in.getSottocategoria());
	}

	public static boolean isPolizzaNonBancaria(ComboForm categoria, ComboForm sottoCategoria) {
		return (isValid(categoria) && isValid(sottoCategoria)) && 
				isPolizzaNonBancaria(StringUtils.defaultString(categoria.getId(), "-1"), StringUtils.defaultString(sottoCategoria.getId(), "-1"));
	}

	public static boolean isPolizzaNonBancaria(String categoria, String sottoCategoria) {
		return (categoria.equals(Constants.CategoriaBeni.ID_POLIZZE_S) && !sottoCategoria.equals(Constants.SottoCategoriaBeni.POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA_S));
	}
	
	public static List<Long> removeDuplicates(List<Long> list) {
        List<Long> uniqueList = new ArrayList<Long>();
        HashSet<Long> set = new HashSet<Long>();

        for (Long l : list) {
            if (set.add(l)) { // Aggiungi l'elemento al set. Restituisce true se l'elemento non era già presente
                uniqueList.add(l); // Se l'elemento non era presente, aggiungilo alla lista univoca
            }
        }

        return uniqueList;
    }
}
