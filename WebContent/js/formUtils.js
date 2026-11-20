function selezionaTutteMisure(idMisPatr,checkBoxMisPatr,idMisPers,checkBoxMisPers) {
	
	var valchk = document.getElementById('all').checked;
	var notvalchk = true;
	if(valchk == true) 
		notvalchk = false;

	var allMisPatr = document.getElementById(idMisPatr).rows;
	if(allMisPatr.length > 0) {
		$("table#" + idMisPatr+" INPUT[@name=" + checkBoxMisPatr + "][type='checkbox']").attr('checked', valchk);
		document.getElementById('id_selectAll_'+checkBoxMisPatr).value=notvalchk;
	}
	
	var allMisPers = document.getElementById(idMisPers).rows;
	if(allMisPers.length > 0) {
		$("table#" + idMisPers+" INPUT[@name=" + checkBoxMisPers + "][type='checkbox']").attr('checked', valchk);
		document.getElementById('id_selectAll_'+checkBoxMisPers).value=notvalchk;
	}
}

function selezionaTutto(idTabella,checkBoxName) {
	// il campo hidden ha id del tipo id_select_All_<checkBoxName>
	var selectAll = document.getElementById('id_selectAll_'+checkBoxName).value;
	if(selectAll=='true') {
		$("table#" + idTabella+" INPUT[@name=" + checkBoxName + "][type='checkbox']").attr('checked', true);
		// poni nel campo hidden !select
		document.getElementById('id_selectAll_'+checkBoxName).value='false';
	}
	else {
		$("table#" + idTabella+" INPUT[@name=" + checkBoxName + "][type='checkbox']").attr('checked', false);
		document.getElementById('id_selectAll_'+checkBoxName).value='true';
	}
}

function selezionaTuttoSkipDisable(idTabella,checkBoxName) {
	// il campo hidden ha id del tipo id_select_All_<checkBoxName>
	var selectAll = document.getElementById('id_selectAll_'+checkBoxName).value;
	if(selectAll=='true') {
		$("table#" + idTabella+" INPUT[@name=" + checkBoxName + "][type='checkbox'][disabled='false']").attr('checked', true);
		// poni nel campo hidden !select
		document.getElementById('id_selectAll_'+checkBoxName).value='false';
	}
	else {
		$("table#" + idTabella+" INPUT[@name=" + checkBoxName + "][type='checkbox']").attr('checked', false);
		document.getElementById('id_selectAll_'+checkBoxName).value='true';
	}
}



function divScomparsa(nomeCampo){
	// $("#" + nomeCampo).animate({"height": "toggle"}, { duration: 500 });
	var options = { to: { width: 200, height: 60 } };	
	$( "#" + nomeCampo ).toggle( 'blind', options, 300 );
}

function goBack_(id,idForm, idButton, method, action, params, target,commandName) {
	// la variabile action gia contiene il context path
	if (method.toUpperCase() == 'GET') {
		window.location.href = action + '';
	} else if (method.toUpperCase() == 'POST') {
		var idSubmit = "id_button_submit_"+id;
		
		$("#" + idForm).append("<input id=\""+idSubmit+"\" type=\"submit\" value=\"\" style=\"display:none\"/>");
		// $("#" + idButton).attr('type', 'submit');
		if(target!=null && target !='' && target!='null'){
			setTargetBefore(target);
		}
		
		var form = $('#' + idForm);
		form.attr('action', action);
		if(commandName!=null && commandName!='' && commandName!='null'){
			$('#' + idForm).attr('commandName', commandName);
		}
		$('#'+idSubmit).attr('name', params);
		$('#'+idSubmit).attr('value', params);
// form.submit();
		$('#'+idSubmit).click();
	}  
}

function setCampoForm(idForm,nameInput,val){
// var element = $('#'+idForm+' :input[name="' + nameInput+ '"]');
	if($('#'+nameInput).length){
		$('#'+nameInput).val(val);
	}else{
		$("#" + idForm).append("<input type=\"hidden\" name=\""+nameInput+"\" value=\""+val+"\">");
	}
}


function setTargetBefore(src) {
	$('#id_hiddenTarget').val('' + src);
}

function indietro(url) {
	// context path � definita in header.jsp
	window.location.href = contextPath+''+url;
}


function redirigi(url) {
	// context path � definita in header.jsp
	window.location.href = contextPath+''+url;
}

function isNumeric(input){
    var RE = /^-{0,1}\d*\.{0,1}\d+$/;
    return (RE.test(input));
}

function validaCampoNumerico(elem){
	var element = $(elem);
	var name = element.attr('name');
	name = name+"errors";
	if(!isNumeric(element.val())){
		$("#"+name).remove();
		element.after('<span id="'+name+'" class="testoRosso">Il campo deve essere numerico</span>');
	}else{
		$("#"+name).remove();
	}
}

function validaLunghezzaCampo(elem,lunghezza){
	var element = $(elem);
	var name = element.attr('name');
	name = name+"errors";
	if(element.val().length<lunghezza){
		
		$("#"+name).remove();
		element.after('<span id="'+name+'" class="testoRosso">Il campo deve essere composto da '+lunghezza+' caratteri</span>');
	}else{
		$("#"+name).remove();
	}
}

// GC/PQ 28/08/2024: TT#202407020117
/*checkPatternChars : verifica un pattern specifico, utilizzato come parametro, di tipo regexp
 * nm = Nome del campo che si sta verificando; verrà utilizzato nel messaggio di out
 * vlu = Valore da verificare
 * pattern = pattern regex da utilizzare per la verifica
 * required = true/false se il campo è richiesto o no
 */
function checkPatternChars(nm,vlu,pattern,required){
	if ( required === undefined ) {
		required = false;
	} 
	if(!required && vlu==""){
		return true;
	}
	if (!pattern.test(vlu)){
		alert("Valore immesso nel campo "+nm+" non valido!");
		return false;
	}
	else { 
		return true; 
	}
}

