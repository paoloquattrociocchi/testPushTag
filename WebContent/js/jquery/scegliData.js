var d = new Date();			
var curr_year = d.getFullYear();
var curr_year_2 = d.getFullYear()+10;
var urlCalendario = contextPath+'/images/calendario.gif';
function prendiData(var1,locked) {
	
	if(locked){
	$.datepicker.setDefaults($.datepicker.regional['it']);
	$(var1).datepicker({
		dateFormat: 'dd/mm/yy',
		changeYear : true,
		changeMonth : true,
		showOn : "button",
		buttonText: 'Apre/Chiude il calendario per la selezione della data',
		buttonImage :   urlCalendario,
		yearRange: '1900:'+curr_year,
		maxDate : '-0D',
		buttonImageOnly : true
	});	
	iconaPuntatore();
	}else{
		$.datepicker.setDefaults($.datepicker.regional['it']);
		$(var1).datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear : true,
			changeMonth : true,
			showOn : "button",
			buttonText: 'Apre/Chiude il calendario per la selezione della data',
			buttonImage :   urlCalendario,
			yearRange: '1900:'+curr_year_2,
			buttonImageOnly : true
		});	
		iconaPuntatore();
	}
}

// Trasforma il cursore del mouse in puntatore quando si passa sul bottone
// di apertura/chiusura del calandario
function iconaPuntatore() {
	$(function() { 
		try	{ 
			$("img[src$='calendario.gif']").mouseover(function() {
				$(this).css('cursor','pointer');
			});
		}
		catch(error) { 
			alert("ERRORE icona calendario: "+error.message);
		}});
}
