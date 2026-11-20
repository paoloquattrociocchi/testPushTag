// contextPath è definita in header.jsp
var CONTEXT = contextPath;

// funzione che effetua la chiamata al server con DWR e ritorna al campo autocomplete
// una lista di oggetti JSON
function doAutocompleteIdBeneCopia(nomeCampo,nomeCampo2,nomeCampo3) {
	var input=nomeCampo;
	var input2=null;
	var tmp2 = null;
	var input3=null;
	var tmp3 = null;
	
	if(nomeCampo2 =='listaIdCheckbox'){
		input2 = nomeCampo2;
	    tmp2 = document.getElementById(input2).value;
	}
	
	if(nomeCampo3 =='idDecretoSequestroEProposta'){
		input3 = nomeCampo3;
	    tmp3 = document.getElementById(input3).value;
	}	
	
	$("#"+input).autocomplete({
		 
		source: function (request, response) {      
			var arrayJSON = new Array();    
			
			AjaxAutoComplete.getSuggestionsIdBeneCopia($( "#"+input ).val(),tmp2,tmp3,{ 
					
					    callback: function(TheDataFromServer)  {

							for(var i = 0;i < TheDataFromServer.length;i++)  {  
								var idBene =  TheDataFromServer[i];
								var regex = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $( "#"+input ).val().replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi");									  			
								 arrayJSON[i] = {id: idBene, value: idBene.replace(regex, "<span class=\"autocomplete\">$1</span>") };          

							   }
							response(arrayJSON);            
						}//callback
			}); // ajax
			
		}, //source       
		select: function (event, ui) {	
			document.getElementById('inputIdBene').value= ui.item.value;
			$("#beneForm").submit();
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
			if (ui.item) {				
			}
			else {
				$(this).val(''); 
			}
		}     
	}).data('autocomplete')._renderItem = function( ul, item ) {
			return $( "<li></li>" ).data( "item.autocomplete", item ).append( '<a>' + item.label + '</a>' ).appendTo( ul );     
	}; //autocomplete
} //doAutocomplete
