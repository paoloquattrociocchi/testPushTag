var DIALOG_WIDTH = 390;
var DIALOG_TITLE = 'Autenticazione firma remota';

function inserisciAtto(idSubmitButton, docGiaFirmato) {
	var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
	var dataDeposito = $('#id_dataDeposito').val();
	var dataEmissione = $('#id_dataEmissione').val();
	var dataDepositoName = $('#id_dataDeposito').attr('name');
	var dataEmissioneName = $('#id_dataEmissione').attr('name');
	var check = true;
	if(dataDeposito != '' && typeof(dataDeposito) != 'undefined' && !dataDeposito.match(re)) {
		$('#'+dataDepositoName).remove();
		$('#id_dataDeposito').after('<span id="'+dataDepositoName+'" class="testoRosso">Valore non valido</span>');
		check = false;
	}
	if(dataEmissione != '' && typeof(dataEmissione) != 'undefined' && !dataEmissione.match(re)) {
		$('#'+dataEmissioneName).remove();
		$('#id_dataEmissione').after('<span id="'+dataEmissioneName+'" class="testoRosso">Valore non valido</span>');
		check = false;
	}
	if(check) {
		inserisciAttoPostCheck(idSubmitButton, docGiaFirmato);
	}
}

/*
 * Funzione invocata dal bottone di conferma e che, dalla lettura del flag id_checkFirmaImmediata, apre oppure meno 
 * il dialog della firma. L'apertura � subordinata alla validazione positiva della data associata al documento e del file
 */
function inserisciAttoPostCheck(idSubmitButton, docGiaFirmato) {
	$(":disabled").removeAttr('disabled');
	//alert('idSubmitButton: ['+idSubmitButton+ '] docGiaFirmato ['+ docGiaFirmato+']');
	var firmaImmediata = $('#id_checkFirmaImmediata').attr('checked');
	if (firmaImmediata == null || firmaImmediata == false || docGiaFirmato == 'S') {
		$('#' + idSubmitButton).click();
	} else {
		apriDialogPIN(idSubmitButton, '');
	}
}

function inserisciAttoPosticipato(idSubmitButton, docGiaFirmato, actionOld) {
	$(":disabled").removeAttr('disabled');
	//alert('idSubmitButton: ['+idSubmitButton+ '] docGiaFirmato ['+ docGiaFirmato+']');
	var firmaImmediata = $('#id_checkFirmaImmediata').attr('checked');
	if (firmaImmediata == null || firmaImmediata == false || docGiaFirmato == 'S') {
		$('#' + idSubmitButton).click();
	} else {
		apriDialogPIN(idSubmitButton, actionOld);
	}
}

/*
 * Funzione che permette la gestione del dialog della firma remota
 * 
 */
function apriDialogPIN(idSubmitButton, actionOld) {

	UtilityDwr.existsError({
		callback : function(data) {
		},
		errorHandler : function(msg) {
			window.location.href = contextPath + '/j_spring_security_logout';
		}
	});
	width_dialog = DIALOG_WIDTH;
	var titolo = DIALOG_TITLE;

	$("#id_dialogForm").dialog({
		show : {
			effect : 'slide',
			duration : 300,
			complete : function() {
				$('#id_inputUserName').focus();
			}
		},
		hide : {
			effect : "fade",
			duration : 250
		},
		close : function() {
			resetAllFields();
			$(".nonModificabile").attr("disabled", true);
			$("#id_checkFirmaImmediata").attr('checked', false);
			if(actionOld!=''){
				$('#id_formConfermaInvia').attr('action', actionOld);
			}
		},
		height : 330,
		width : width_dialog,
		modal : true,
		resizable : false,
		draggable : true,
		open : function(event, ui) {
			$("span.ui-dialog-title").text(titolo);
			// cambia il colore della barra di intestazione
			$(".ui-widget-header").css("background", "#1c548c");
			// cambia il colore relativo al testo sulla barra di intestazione
			$(".ui-widget-header").css("color", "#FFFFFF");
		},
		buttons : {
			Conferma : function() {
				conferma(idSubmitButton);
			},
			Annulla : function() {
				$(".nonModificabile").attr("disabled", true);
				$("#id_checkFirmaImmediata").attr('checked', false);
				if(actionOld!=''){
					$('#id_formConfermaInvia').attr('action', actionOld);
				}
				$(this).dialog("close");
				//CATEGORIA ATTO = Ricorso in Appello - STATO ATTO = Registrato
				if($('#id_categoriaAtto').val()==5 && $('#id_statoAtto').val())
				{
						$('#id_formConfermaInvia').submit();
				}
					
				
			}
		}
	});
}

/*
 * Valida il form della firma remota e, in caso positivo, effettua il submit
 * inviando l'evento di click sul bottone hidden di submit
 */
function conferma(idSubmitButton) {
	var flg_a = validate('UserName', 'REQUIRED');
	var flg_b = validate('Password', 'REQUIRED');
	var flg_c = validate('Pin', 'REQUIRED');
	if (flg_a && flg_b && flg_c) {
		$('#id_hiddenFirmaRemotaUserName').val(
				'' + $('#id_inputUserName').val());
		$('#id_hiddenFirmaRemotaPassword').val(
				'' + $('#id_inputPassword').val());
		$('#id_hiddenFirmaRemotaPin').val('' + $('#id_inputPin').val());
		$('#id_dialogForm').dialog("close");
		$('#' + idSubmitButton).click();
	}
}

/*
 * Reimposta il form della firma remota
 */
function resetAllFields() {
	resetError('UserName');
	resetError('Password');
	resetError('Pin');
	resetField('UserName');
	resetField('Password');
	resetField('Pin');
}

/*
 * Valida il campo di cui � specificato l'ID in base al criterio di validazione
 * specificato (al momento, vale solo 'REQUIRED')
 */
function validate(idField, validation) {
	var msg = '';
	if (validation == 'REQUIRED')
		msg = 'Obbligatorio';
	var fieldVal = $('#id_input' + idField).val();
	if (fieldVal == '') {
		$("#id_div" + idField + " span").text(msg);
		return false;
	} else {
		$("#id_div" + idField + " span").text("");
		return true;
	}
}

/*
 * Reimposta per il campo di cui � specificato l'id la visualizzazione
 * dell'errore
 */
function resetError(idField) {
	$("#id_div" + idField + " span").text('');
}

/*
 * Reimposta il campo di cui � specificato l'id
 */
function resetField(idField) {
	$('#id_input' + idField).val('');
}

//Utilizzato per il download/cancellazione/firmaPosticipata del documento
//Su tutte le pagine dove � presente la possibilit� di scaricare/cancellare/firmare il documento � presente 
//un id sul form cos� indicato: id="id_formConfermaInvia".
//Questa pagina non esiste fisicamente ma viene valorizzata da firmaRemota.js quando viene 
//invocata la firma del documento
//la stessa logica � presente su AttoControllore sui metodi: rimuoviAllegatoDaMemoriaDocumentale/downloadDocumentale/firmaPosticipataDocumentale
function gestioneDocumento(contextPath,azione) {
	$('#id_buttonGestioneDocumento').attr('name', azione);
	//� obbligatorio riportare l'action nel forward iniziare per evitare che possa generare errori 
	//di mapping sul controller
	var actionOld = $('#id_formConfermaInvia').attr('action');
	if (azione == 'rimuoviAllegatoDaMemoriaDocumentale') {
		var confermaCancellazione = confirm("Si sta per cancellare l'allegato.Si desidera procedere?");
		if (!confermaCancellazione){
			$('#id_formConfermaInvia').attr('action', actionOld);
			return;
		}else{
			$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/rimuoviAllegatoDaMemoriaDocumentale.htm');
		}
	}
	if (azione == 'downloadDocumentale') {
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/downloadDocumentale.htm');
	}
	if (azione == 'firmaPosticipataDocumentale') {
		$("#id_checkFirmaImmediata").attr('checked', true);
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/firmaPosticipataDocumentale.htm');
		inserisciAttoPosticipato('id_buttonGestioneDocumento', 'N', actionOld);
		return;
	}
	$('#id_buttonGestioneDocumento').click();
	$('#id_formConfermaInvia').attr('action', actionOld);
}

function gestioneDocumentoUdi(contextPath,azione) {
	$('#id_buttonGestioneDocumento').attr('name', azione);
	//� obbligatorio riportare l'action nel forward iniziare per evitare che possa generare errori 
	//di mapping sul controller
	var actionOld = $('#id_formConfermaInvia').attr('action');
	if (azione == 'rimuoviAllegatoDaMemoriaDocumentale') {
		var confermaCancellazione = confirm("Si sta per cancellare l'allegato.Si desidera procedere?");
		if (!confermaCancellazione){
			$('#id_formConfermaInvia').attr('action', actionOld);
			return;
		}else{
			$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/rimuoviAllegatoDaMemoriaDocumentaleUdi.htm');
		}
	}
	if (azione == 'downloadDocumentale') {
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/downloadDocumentaleUdi.htm');
	}
	if (azione == 'firmaPosticipataDocumentale') {
		$("#id_checkFirmaImmediata").attr('checked', true);
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/atto/firmaPosticipataDocumentaleUdi.htm');
		inserisciAttoPosticipato('id_buttonGestioneDocumento', 'N', actionOld);
		return;
	}
	$('#id_buttonGestioneDocumento').click();
	$('#id_formConfermaInvia').attr('action', actionOld);
}


function gestioneDocumentoStralcio(contextPath,azione) {
	$('#id_buttonGestioneDocumento').attr('name', azione);
	//� obbligatorio riportare l'action nel forward iniziare per evitare che possa generare errori 
	//di mapping sul controller
	var actionOld = $('#id_formConfermaInvia').attr('action');
	if (azione == 'rimuoviAllegatoDaMemoriaDocumentale') {
		var confermaCancellazione = confirm("Si sta per cancellare l'allegato.Si desidera procedere?");
		if (!confermaCancellazione){
			$('#id_formConfermaInvia').attr('action', actionOld);
			return;
		}else{
			$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/stralcio/rimuoviAllegatoDaMemoriaDocumentale.htm');
		}
	}
	if (azione == 'downloadDocumentale') {
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/stralcio/downloadDocumentale.htm');
	}
	if (azione == 'firmaPosticipataDocumentale') {
		$("#id_checkFirmaImmediata").attr('checked', true);
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/stralcio/firmaPosticipataDocumentale.htm');
		inserisciAttoPosticipato('id_buttonGestioneDocumento', 'N', actionOld);
		return;
	}
	$('#id_buttonGestioneDocumento').click();
	$('#id_formConfermaInvia').attr('action', actionOld);
}

function gestioneDocumentoRiunione(contextPath,azione) {
	
//	alert("contextPath: "+ contextPath + "azione: "+azione);
	
	$('#id_buttonGestioneDocumento').attr('name', azione);
	//� obbligatorio riportare l'action nel forward iniziare per evitare che possa generare errori 
	//di mapping sul controller
	var actionOld = $('#id_formConfermaInvia').attr('action');
	if (azione == 'rimuoviAllegatoDaMemoriaDocumentale') {
		var confermaCancellazione = confirm("Si sta per cancellare l'allegato.Si desidera procedere?");
		if (!confermaCancellazione){
			$('#id_formConfermaInvia').attr('action', actionOld);
			return;
		}else{
			$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/riunione/rimuoviAllegatoDaMemoriaDocumentale.htm');
		}
	}
	if (azione == 'downloadDocumentale') {
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/riunione/downloadDocumentale.htm');
	}
	if (azione == 'firmaPosticipataDocumentale') {
		$("#id_checkFirmaImmediata").attr('checked', true);
		$('#id_formConfermaInvia').attr('action', contextPath+'/procedimento/riunione/firmaPosticipataDocumentale.htm');
		inserisciAttoPosticipato('id_buttonGestioneDocumento', 'N', actionOld);
		return;
	}
	$('#id_buttonGestioneDocumento').click();
	$('#id_formConfermaInvia').attr('action', actionOld);
}
