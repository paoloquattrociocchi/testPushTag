/**
 * 
 */

var ITALIA = 1;
var PATTERN_LENGTH = 2;
// contextPath � definita in header.jsp
var CONTEXT = contextPath;
var ERROR_TEXT = "Errore durante la comunicazione con il server";

//timeout per la connessione (es. 10000=10 sec)
var timeOut = 90000;
var timer;
var expired;

//funzione che viene lanciata al caricamento della pagina(onload/ready) che abilita o disabilita l'autocomplete e il campo dei comuni a seconda dello stato 
function controllaInizializzazioneCampi(idStato,nomeCampo){
	document.getElementById('id_messagesAutocomplete').style.visibility="hidden";
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	if(idStato == -1) {
		document.getElementById(idDescrizione).disabled=true;
		document.getElementById(idDescrizione).value='';
		document.getElementById(idHiddenDescrizione).value='';	
		return;
	}	
	// abilito il campo relativo al comune
	document.getElementById(idDescrizione).disabled=false;		
	if(idStato == ITALIA ) {	//selezionato stato 'ITALIA'		
		$( "#"+idDescrizione ).autocomplete({ disabled: false });
		doAutocomplete(nomeCampo);
	}
	else {// selezionato stato estero: disabilito l'autocomplete			
		$( "#"+idDescrizione ).autocomplete({ disabled: true });
	}
}
//DG Mev Marzo-Giugno Prog 14 - Errore Compilazione campo Comune dopo ricerca
//funzione che viene lanciata al caricamento della pagina(onload/ready) che abilita o disabilita l'autocomplete e il campo dei comuni a seconda dello stato 
function controllaInizializzazioneCampiComune(idStato,nomeCampo){
	document.getElementById('id_messagesAutocomplete').style.visibility="hidden";
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	if(idStato == -1) {
		document.getElementById(idDescrizione).disabled=true;
		document.getElementById(idDescrizione).value='';
		document.getElementById(idHiddenDescrizione).value='';	
		return;
	}	
	// abilito il campo relativo al comune
	document.getElementById(idDescrizione).disabled=false;		
	if(idStato == ITALIA ) {	//selezionato stato 'ITALIA'		
		$( "#"+idDescrizione ).autocomplete({ disabled: false });
		doAutocomplete(nomeCampo);
	}
	else {// selezionato stato estero: disabilito l'autocomplete			
		$( "#"+idDescrizione ).autocomplete({ disabled: true });
	}
}
// funzione che viene lanciata all'onchange dello stato che abilita/disabilita il campo autocomplete e sbianca i valori
function controllaCampiEAutocomplete(idStato,nomeCampo){
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	if(idStato == -1) {
		document.getElementById(idDescrizione).disabled=true;
		document.getElementById(idDescrizione).value='';
		document.getElementById(idHiddenDescrizione).value='';	
		return;
	}	
	// abilito il campo relativo al comune
	document.getElementById(idDescrizione).disabled=false;		
	if(idStato == ITALIA ) {	//selezionato stato 'ITALIA'
		document.getElementById(idDescrizione).value='';
		document.getElementById(idHiddenDescrizione).value='';	
		$( "#"+idDescrizione ).autocomplete({ disabled: false });	
		doAutocomplete(nomeCampo);
	}
	else {// selezionato stato estero: disabilito l'autocomplete	
		document.getElementById(idDescrizione).value='';
		document.getElementById(idHiddenDescrizione).value='';	
		$( "#"+idDescrizione ).autocomplete({ disabled: true });
		
	}
}

function clearAutoCompleteField(nomeCampo) {
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	document.getElementById(idDescrizione).value='';
	document.getElementById(idHiddenDescrizione).value='';	
}
//
//funzione che effetua la chiamata al server con DWR e ritorna al campo autocomplete
//una lista di oggetti JSON
function doAutocompleteConservat(nomeCampo) {
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	var arrayJSON = new Array();  
	var flag_trovato = false;
	var flag_selezione = false;
	
	$("#"+idDescrizione).autocomplete({
		// al momento dell'attivazione dell'autocomplete apro l'immagine di loading
		search  : function(){
			$("#id_loading"+nomeCampo).html('<img src=\"'+CONTEXT+'/css/jquery/images/ui-anim_basic_16x16.gif\" alt=\"Attendi...\" />');
			// imposta il timeout
			expired = false;
			timer = setTimeout(function(){
				$("#id_loading"+nomeCampo).html('<span class=\"autocomplete\"><img src=\"'+CONTEXT+'/images/small_error.gif\"/>&nbsp;timeout della connessione</span>');
				expired = true;
			},timeOut);
		}, 
		// rimuovo l'immagine di loading
		open    : function(){
			$("#id_loading"+nomeCampo).html('');
		}, 
		source: function (request, response) {      
			arrayJSON = new Array();           
			AjaxAutoComplete.getConservatComboForm(nomeCampo, $( "#"+idDescrizione ).val(),{ 
					
					    callback: function(TheDataFromServer)  { 
							// blocca il timeout
							clearTimeout(timer);
							if( expired )
								return;
							for(var i = 0;i < TheDataFromServer.length;i++)  {  
								var descrizioneComune =  TheDataFromServer[i].descrizione;
								var regex = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $( "#"+idDescrizione ).val().replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi");								  
								arrayJSON[i] = { id: TheDataFromServer[i].id, value: descrizioneComune.replace(regex, "<span class=\"autocomplete\">$1</span>") };              
							}    
							// se l'elenco e' vuoto visualizzo un messaggio di warning
							if(arrayJSON.length==0) {
								$("#id_loading"+nomeCampo).html('');
								$("#id_loading"+nomeCampo).html('<span class=\"autocomplete\">Nessun risultato</span>');
								document.getElementById('id_hidden'+nomeCampo).value = '9999';
							}
							response(arrayJSON);            
						},//callback
						errorHandler:function(msg) {							
							$(function() {
								$( "#dialog:ui-dialog" ).dialog( "destroy" );
								$( "#id_textDialog" ).html("<span class=\"dialogSpan\">"+ERROR_TEXT+"</span>");
								$( "#id_dialogIcon" ).addClass("dialogIcon");
								$( "#dialog-message" ).dialog({			
									modal: true,
									resizable: false,
									buttons: {				
										Chiudi: function() {											
											$( this ).dialog( "close" );
											window.location.href = CONTEXT;
										}			
									},	
									close: function(event, ui) { 
										window.location.href = CONTEXT;
									}									
								});
							});
						} 
			});
			
		},         
		minLength: PATTERN_LENGTH,  
		timeout: 1000, 		
		select: function (event, ui) {			
			document.getElementById(idHiddenDescrizione).value=ui.item.id;
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");
			flag_selezione = true;
		},   
		focus: function(event, ui){ 
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");			
		},
		change: function (event, ui) {
			
		},
		close: function (event, ui) {	
			if( !flag_selezione ) {
				flag_trovato = false;
				for(var i = 0; i < arrayJSON.length; i++) {
					var currentDescrizione = arrayJSON[i].value;
					// elimina la stringa relativa allo span
					var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
					var reFine = new RegExp("<\/*span>", "gi");
					currentDescrizione=currentDescrizione.replace(reInizio,"");	
					currentDescrizione=currentDescrizione.replace(reFine,"");
					// elimina il pattern della sigla provincia
					
					var indexInizio_pprov = currentDescrizione.indexOf(' (');
					currentDescrizione = currentDescrizione.substring(0, indexInizio_pprov);
					var inputStr = $( "#"+idDescrizione ).val() ;
					
					if( currentDescrizione == inputStr.toUpperCase() ) {
						document.getElementById(idHiddenDescrizione).value=arrayJSON[i].id;
						//var reInizio_2 = new RegExp("<\/*span class=\"autocomplete\">", "gi");
						//var reFine_2 = new RegExp("<\/*span>", "gi");
						arrayJSON[i].value=arrayJSON[i].value.replace(reInizio,"");	
						arrayJSON[i].value=arrayJSON[i].value.replace(reFine,"");
						document.getElementById(idDescrizione).value=arrayJSON[i].value;	
						flag_trovato = true;
						break;				
					}			
				}
				if( !flag_trovato ) 
					document.getElementById('id_hidden'+nomeCampo).value = '9999';
					alert('Campo non selezionato correttamente');
			}
			else
				flag_selezione = false;
		}     		
	}). data('autocomplete')._renderItem = function( ul, item ) {
			return $( "<li></li>" ).data( "item.autocomplete", item ).append( '<a>' + item.label + '</a>' ).appendTo( ul );     
	}; 
		
}


// funzione che effetua la chiamata al server con DWR e ritorna al campo autocomplete
// una lista di oggetti JSON
function doAutocomplete(nomeCampo) {
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	var arrayJSON = new Array();  
	var flag_trovato = false;
	var flag_selezione = false;
	
	$("#"+idDescrizione).autocomplete({
		// al momento dell'attivazione dell'autocomplete apro l'immagine di loading
		search  : function(){
			$("#id_loading"+nomeCampo).html('<img src=\"'+CONTEXT+'/css/jquery/images/ui-anim_basic_16x16.gif\" alt=\"Attendi...\" />');
			// imposta il timeout
			expired = false;
			timer = setTimeout(function(){
				$("#id_loading"+nomeCampo).html('<span class=\"autocomplete\"><img src=\"'+CONTEXT+'/images/small_error.gif\"/>&nbsp;timeout della connessione</span>');
				expired = true;
			},timeOut);
		}, 
		// rimuovo l'immagine di loading
		open    : function(){
			$("#id_loading"+nomeCampo).html('');
		}, 
		source: function (request, response) {      
			arrayJSON = new Array();           
			AjaxAutoComplete.getSuggestionsComboForm($( "#"+idDescrizione ).val(),{ 
					
					    callback: function(TheDataFromServer)  { 
							// blocca il timeout
							clearTimeout(timer);
							if( expired )
								return;
							for(var i = 0;i < TheDataFromServer.length;i++)  {  
								var descrizioneComune =  TheDataFromServer[i].descrizione;
								var regex = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $( "#"+idDescrizione ).val().replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi");								  
								arrayJSON[i] = { id: TheDataFromServer[i].id, value: descrizioneComune.replace(regex, "<span class=\"autocomplete\">$1</span>") };              
							}    
							// se l'elenco e' vuoto visualizzo un messaggio di warning
							if(arrayJSON.length==0) {
								$("#id_loading"+nomeCampo).html('');
								$("#id_loading"+nomeCampo).html('<span class=\"autocomplete\">Nessun risultato</span>');
								document.getElementById('id_hidden'+nomeCampo).value = '9999';
							}
							response(arrayJSON);            
						},//callback
						errorHandler:function(msg) {							
							$(function() {
								$( "#dialog:ui-dialog" ).dialog( "destroy" );
								$( "#id_textDialog" ).html("<span class=\"dialogSpan\">"+ERROR_TEXT+"</span>");
								$( "#id_dialogIcon" ).addClass("dialogIcon");
								$( "#dialog-message" ).dialog({			
									modal: true,
									resizable: false,
									buttons: {				
										Chiudi: function() {											
											$( this ).dialog( "close" );
											window.location.href = CONTEXT;
										}			
									},	
									close: function(event, ui) { 
										window.location.href = CONTEXT;
									}									
								});
							});
						} 
			});
			
		},         
		minLength: PATTERN_LENGTH,  
		timeout: 1000, 		
		select: function (event, ui) {			
			document.getElementById(idHiddenDescrizione).value=ui.item.id;
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");
			flag_selezione = true;
		},   
		focus: function(event, ui){ 
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");			
		},
		change: function (event, ui) {
			
		},
		close: function (event, ui) {	
			if( !flag_selezione ) {
				flag_trovato = false;
				for(var i = 0; i < arrayJSON.length; i++) {
					var currentDescrizione = arrayJSON[i].value;
					// elimina la stringa relativa allo span
					var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
					var reFine = new RegExp("<\/*span>", "gi");
					currentDescrizione=currentDescrizione.replace(reInizio,"");	
					currentDescrizione=currentDescrizione.replace(reFine,"");
					// elimina il pattern della sigla provincia
					
					var indexInizio_pprov = currentDescrizione.indexOf(' (');
					currentDescrizione = currentDescrizione.substring(0, indexInizio_pprov);
					var inputStr = $( "#"+idDescrizione ).val() ;
					
					if( currentDescrizione == inputStr.toUpperCase() ) {
						document.getElementById(idHiddenDescrizione).value=arrayJSON[i].id;
						//var reInizio_2 = new RegExp("<\/*span class=\"autocomplete\">", "gi");
						//var reFine_2 = new RegExp("<\/*span>", "gi");
						arrayJSON[i].value=arrayJSON[i].value.replace(reInizio,"");	
						arrayJSON[i].value=arrayJSON[i].value.replace(reFine,"");
						document.getElementById(idDescrizione).value=arrayJSON[i].value;	
						flag_trovato = true;
						break;				
					}			
				}
				if( !flag_trovato ) 
					document.getElementById('id_hidden'+nomeCampo).value = '9999';
					alert('Campo non selezionato correttamente');
			}
			else
				flag_selezione = false;
		}     		
	}). data('autocomplete')._renderItem = function( ul, item ) {
			return $( "<li></li>" ).data( "item.autocomplete", item ).append( '<a>' + item.label + '</a>' ).appendTo( ul );     
	}; 
		
}

//passare mode="write" per autocomplete in situazioni di scrittura su db 
//passare mode="search" per autocomplete in situazioni di ricerca
function testAndCleanComuni(campoStato, campoComune, mode) {
	if($("#id_descrizione" + campoComune).val() != null && $("#id_descrizione" + campoComune).val() != undefined) {
		$("#id_descrizione" + campoComune).autocomplete("close");
		if( document.getElementById('id_descrizione'+campoComune) != null ) {
			var l = $('#id_descrizione'+campoComune).val().length;					
			if( l==0 )
				clearAutoCompleteField(campoComune);
				
		}
		
		//
		if(mode == 'write') {
			if($("#id_" + campoStato).val() == 1 && $("#id_hidden" + campoComune).val() == '9999') {
				$("#id_descrizione" + campoComune).autocomplete("close");
				if($("#id_" + campoStato).val() == 1 && $("#id_hidden" + campoComune).val() == '9999') {
					document.getElementById('id_messagesAutocomplete').style.visibility="visible";
					
					var code = 'autocomplete.valoreNonInLista.' + campoComune;
					var message = "error getAutocompleteComuniError: " + campoComune;
					
					AjaxAutoComplete.getAutocompleteComuniError(code, {
						    callback: function(TheDataFromServer) {
						    	message = TheDataFromServer;
							    var stringForAppend = '<div id="autocompleteMessage" class="testo">' + message + '</div>';
								$("#id_messagesAutocomplete").append(stringForAppend);
							}
						});
					
					window.scrollTo(0,0);	
					return false;
				}
			}
		}
	}
	

	return true;
}

//passare mode="write" per autocomplete in situazioni di scrittura su db 
//passare mode="search" per autocomplete in situazioni di ricerca
function submitWithAutocompleteComuni(stati, comuni, mode) {
	var ok = true;
	var error = false; 
	for(var i = 0; i < stati.length; i++) {
		ok = testAndCleanComuni(stati[i], comuni[i], mode);
		if(!ok) 
			error = true;
	}
	if(!error) {
		$("#id_messagesAutocomplete").empty();
		document.getElementById("id_messagesAutocomplete").style.visibility = "hidden";
	}
	
	return !error;
}

//da richiamare sempre al ready della pagina.
//nomeSubmit = attributo 'name' del submit che andr� ad operare con i campi autocomplete
//da richiamare per ogni submit che operer� con i campi autocomplete
function inizializzaSubmitAutocomplete(nomeSubmit) {
	
//		$.i18n.properties({
//		    name:'messages', 
//		    path: 'C:/', 
//		    mode:'both',
//		    encoding: 'ISO-8859-1',
//		    callback: function() {alert("callback");}
//		    });
	
	$('input[name="' + nomeSubmit + '"]').mouseup(function() {
		//alert("evento click");
		$("#id_messagesAutocomplete").empty();
		document.getElementById("id_messagesAutocomplete").style.visibility = "hidden";
	});
}
