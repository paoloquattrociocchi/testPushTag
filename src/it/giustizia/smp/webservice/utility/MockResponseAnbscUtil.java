package it.giustizia.smp.webservice.utility;

import java.util.Random;

import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.Esito;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;

public class MockResponseAnbscUtil {

	public static SequestroResponse getMockSequestroResponse() {
		SequestroResponse response = new SequestroResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static ConfiscaPrimoGradoResponse getMockConfiscaPrimoGradoResponse() {
		ConfiscaPrimoGradoResponse response = new ConfiscaPrimoGradoResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static ConfiscaSecondoGradoResponse getMockConfiscaSecondoGradoResponse() {
		ConfiscaSecondoGradoResponse response = new ConfiscaSecondoGradoResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static StralcioResponse getMockStralcioResponse() {
		StralcioResponse response = new StralcioResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static RiunioneResponse getMockRiunioneResponse() {
		RiunioneResponse response = new RiunioneResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static RevocaSequestroResponse getMockRevocaSequestroResponse() {
		RevocaSequestroResponse response = new RevocaSequestroResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static ConfiscaDefinitivaResponse getMockConfiscaDefinitivaResponse() {
		ConfiscaDefinitivaResponse response = new ConfiscaDefinitivaResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	public static RevocaConfiscaResponse getMockRevocaConfiscaResponse() {
		RevocaConfiscaResponse response = new RevocaConfiscaResponse();
		Esito esito = new Esito();
		if(getRandomBoolean()) {
			esito.setEsito(true);
		} else {
			esito.setErrordescription("ERRORE AUTOGENERATO PER PROVA");
			esito.setEsito(false);
		}
		response.setEsito(esito);
		return response;
	}
	
	
	private static boolean getRandomBoolean() {
		Random randomBoolean = new Random();
		return randomBoolean.nextBoolean();
	}
}
