/**
 * 
 */ 

var INPUT_LENGTH = 5;
// contextPath è definita in header.jsp
var CONTEXT = contextPath;
var ERROR_TEXT = "Errore durante la comunicazione con il server";

// funzione che effetua la chiamata al server con DWR e ritorna al campo autocomplete
// una lista di oggetti JSON
function doAutocompleteOpFinanzPolizza(nomeCampo) {
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
			AjaxAutoComplete.getOperatoriFinanziariPolizze($( "#"+idDescrizione ).val(),{ 
					
					    callback: function(TheDataFromServer)  { 
							for(i = 0;i < TheDataFromServer.length;i++)  {  
								var descrizioneBanca =  TheDataFromServer[i].descrizione;
								var regex = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $( "#"+idDescrizione ).val().replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi");								  
								arrayJSON[i] = { id: TheDataFromServer[i].id, value: descrizioneBanca.replace(regex, "<span class=\"autocomplete\">$1</span>") };              
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
			})
			
		},         
		minLength: INPUT_LENGTH,
		select: function (event, ui) {
			document.getElementById(idHiddenDescrizione).value=ui.item.id;
			document.getElementById(idDescrizione).value=ui.item.value;
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");
			document.getElementById('id_beneForm').submit();
		},   
		focus: function(event, ui){ 
			var reInizio = new RegExp("<\/*span class=\"autocomplete\">", "gi");
			var reFine = new RegExp("<\/*span>", "gi");
			ui.item.value=ui.item.value.replace(reInizio,"");	
			ui.item.value=ui.item.value.replace(reFine,"");
		},
		change: function (event, ui) {
			if (ui.item) {				
			}
			else {
				//why?
				//$(this).val(''); 
			}
		}     
	}). data('autocomplete')._renderItem = function( ul, item ) {
			return $( "<li></li>" ).data( "item.autocomplete", item ).append( '<a>' + item.label + '</a>' ).appendTo( ul );     
	}; 
}
