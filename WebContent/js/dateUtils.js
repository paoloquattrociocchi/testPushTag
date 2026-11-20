function checkDate() {
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
	return check;
}

