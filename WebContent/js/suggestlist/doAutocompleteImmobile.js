/**
 * 
 */

var ITALIA = 1;
var PATTERN_LENGTH = 2;
// contextPath è definita in header.jsp
var CONTEXT = contextPath;
var ERROR_TEXT = "Errore durante la comunicazione con il server";

// funzione che resetta il campo autocomplete quando si posizione sopra il cursore del mouse
function controllaSelezione(nomeCampo) {
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	var idLoadingDiv='id_loading'+nomeCampo;
	var descrizione = document.getElementById(idDescrizione).value;	
	if(descrizione.length>0) {
		document.getElementById(idDescrizione).value='';
		// resetto il div relativo allo spinner e al testo
		$("#"+idLoadingDiv).html('');
	}
	document.getElementById(idHiddenDescrizione).value='';		
}

// funzione di controllo dell'abilitazione/disabilitazione del campo autocomplete in 
// relazione alla selezione dello stato
function controllaInizializzazioneCampi(idStato,nomeCampo){
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
	}
	else {// selezionato stato estero: disabilito l'autocomplete			
		$( "#"+idDescrizione ).autocomplete({ disabled: true });
		
		if(nomeCampo == 'ComuneCatastaleP'){
			$("#id_descComuneCatastale").val("");
			$("#id_hiddenIdComuneCatastale").val("");
			$("#id_codiceCatastale1").val("");
			
			$("#id_descComuneCatastaleP").val("");
			$("#id_hiddenIdComuneCatastaleP").val("");
			$("#id_codiceCatastale1P").val("");
		}
		
	}
}
// funzione che effetua la chiamata al server con DWR e ritorna al campo autocomplete
// una lista di oggetti JSON
function doAutocompleteImmobili(nomeCampo) {
	var idDescrizione='id_descrizione'+nomeCampo;
	var idHiddenDescrizione='id_hidden'+nomeCampo;
	$("#"+idDescrizione).autocomplete({
		// al momento dell'attivazione dell'autocomplete apro l'immagine di loading
		search  : function(){
			$("#id_loading"+nomeCampo).html('<img src=\"'+CONTEXT+'/css/jquery/images/ui-anim_basic_16x16.gif\" alt=\"Attendi...\" />'); 
		}, 
		// rimuovo l'immagine di loading
		open    : function(){
			$("#id_loading"+nomeCampo).html('');
		}, 
		source: function (request, response) {      
			var arrayJSON = new Array();           
			AjaxAutoComplete.getSuggestionsComboForm($( "#"+idDescrizione ).val(),{ 
					
					    callback: function(TheDataFromServer)  { 
							for(var i = 0;i < TheDataFromServer.length;i++)  {  
								var descrizioneComune =  TheDataFromServer[i].descrizione;
								var regex = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $( "#"+idDescrizione ).val().replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi");								  
								arrayJSON[i] = { id: TheDataFromServer[i].id, value: descrizioneComune.replace(regex, "<span class=\"autocomplete\">$1</span>") };              
							}    
							// se l'elenco e' vuoto visualizzo un messaggio di warning
							if(arrayJSON.length==0) {
								$("#id_loading"+nomeCampo).html('');
								$("#id_loading"+nomeCampo).html('<span class=\"autocomplete\">Nessun risultato</span>');
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
									}		
								});
							});
						} 
			});
			
		},         
		minLength: PATTERN_LENGTH,   
		select: function (event, ui) {			
			document.getElementById(idHiddenDescrizione).value=ui.item.id;
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");
		},   
		focus: function(event, ui){ 
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");			
		},
		change: function (event, ui) {
//			if (ui.item) {				
//			}
//			else {
//				$(this).val(''); 
//			}
		},
		close: function(event,ui){

			if(nomeCampo == 'ComuneCatastaleP'){
				AjaxAutoComplete.getCodiceCatastaleComune($( "#"+idHiddenDescrizione ).val(),
						function(codiceCatastale){
							$("#id_codiceCatastale1P").val(codiceCatastale);
						});
				
			}
			
			else if(nomeCampo == 'Comune'){
				AjaxAutoComplete.getCodiceCatastaleComune($( "#"+idHiddenDescrizione ).val(),
						function(codiceCatastale){
							$("#id_codiceCatastale1").val(codiceCatastale);
							$("#id_descComuneCatastale").val("");
							$("#id_hiddenIdComuneCatastale").val("");
							$("#id_descComuneCatastale").val($("#" + idDescrizione).val());
							$("#id_hiddenIdComuneCatastale").val($("#" + idHiddenDescrizione).val());			
						});
			}
			
		}
	}). data('autocomplete')._renderItem = function( ul, item ) {
			return $( "<li></li>" ).data( "item.autocomplete", item ).append( '<a>' + item.label + '</a>' ).appendTo( ul );     
	}; 
}

//function riempiCodiceCatastale(nomeCampoId, nomeCampoCodiceCatastale) {
//	alert(nomeCampoId);
//	alert(nomeCampoCodiceCatastale);
//	AjaxAutoComplete.getCodiceCatastaleComune($( "#"+nomeCampoId ).val(),
//												function(codiceCatastale){$("#"+nomeCampoCodiceCatastale).val(codiceCatastale);});
//	
//	
//}
