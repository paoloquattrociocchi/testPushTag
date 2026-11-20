
function disabilitaSubmitIntervenienti(disabilita,profilo,idStatoProc,tipoProcedimento){
                if(disabilita){
                               if(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)){
                                               document.getElementsByName("Modifica")[0].disabled=true;
                               }
                               if(profilo==17 && (idStatoProc!=11)){
                                               document.getElementsByName("Modifica")[0].disabled=true;
                               }
                               if(profilo==6){
                                               document.getElementsByName("Modifica")[0].disabled=true;
                               }               
                               
                               if( document.getElementsByName("Cancella")[0]){
                            	   document.getElementsByName("Cancella")[0].disabled=true;
                               }
                               
                }              
}
	
function disabilitaSubmitErroreCorrezioneMateriale(){
		
		
					if(document.getElementsByName("Dettaglio").length != 0) {
						document.getElementsByName("Dettaglio")[0].disabled=true;
					}
					if(document.getElementsByName("TrasformaBene").length != 0) {
						document.getElementsByName("TrasformaBene")[0].disabled=true;
					}
					if(document.getElementsByName("CopiaBene").length != 0) {
						document.getElementsByName("CopiaBene")[0].disabled=true;
					}
					if(document.getElementsByName("TitoliDiDiritto").length != 0) {
						document.getElementsByName("TitoliDiDiritto")[0].disabled=true;
					}
					if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0) {
						document.getElementsByName("CopiaTitoliDiDiritto")[0].disabled=true;
					}
					if(document.getElementsByName("EliminaBene").length != 0) {
						document.getElementsByName("EliminaBene")[0].disabled=true;
					}
			 
		   
		    
	}

     function disabilitaSubmitBeniDelProcedimento(disabilita,tipoUfficio,tipoProcedimento){
		
    	 if(tipoUfficio==6 || tipoUfficio==17 || tipoUfficio==19 || tipoUfficio==8) {
				if(disabilita){
					if ((tipoUfficio!=19)  || (tipoProcedimento==13 || tipoProcedimento==14)) {
					if(document.getElementsByName("Modifica").length != 0) {
						document.getElementsByName("Modifica")[0].disabled=true;
					}
					}
					//if(document.getElementsByName("TrasformaBene").length != 0) {
					//	document.getElementsByName("TrasformaBene")[0].disabled=true;
					//}
					if(document.getElementsByName("CopiaBene").length != 0) {
						document.getElementsByName("CopiaBene")[0].disabled=true;
					}
					if(tipoProcedimento!=12) {
						if(document.getElementsByName("TitoliDiDiritto").length != 0) {
							document.getElementsByName("TitoliDiDiritto")[0].disabled=true;
						}
					}
					
					if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0) {
						document.getElementsByName("CopiaTitoliDiDiritto")[0].disabled=true;
					}
					if(document.getElementsByName("EliminaBene").length != 0) {
						document.getElementsByName("EliminaBene")[0].disabled=true;
					}
			   }
		    }
	}
     
     function disabilitaSubmitBeniDelProcedimentoPro(disabilita,tipoUfficio,statoProcedimento,tipoProcedimento){
 		
 		if(tipoUfficio==6 || tipoUfficio==19 || tipoUfficio==8) {
 				if(disabilita){
 					if ((tipoUfficio!=19) || (tipoProcedimento==13 || tipoProcedimento==14)) {
 	
 							if(document.getElementsByName("Modifica").length != 0) {
 		 						document.getElementsByName("Modifica")[0].disabled=true;
 		 					}
 							
 					}
 					
 					if(document.getElementsByName("CopiaBene").length != 0) {
 						document.getElementsByName("CopiaBene")[0].disabled=true;
 					}
 					if(tipoProcedimento!=12) {
 						if(document.getElementsByName("TitoliDiDiritto").length != 0) {
 							document.getElementsByName("TitoliDiDiritto")[0].disabled=true;
 						}
 					}
 					
 					if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0) {
 						document.getElementsByName("CopiaTitoliDiDiritto")[0].disabled=true;
 					}
 					if(document.getElementsByName("EliminaBene").length != 0) {
 						document.getElementsByName("EliminaBene")[0].disabled=true;
 					}
 			   }
 		 
 		    }
 		
 		   if(tipoUfficio==17 ) { 
			  if(disabilita){
 					if (statoProcedimento!=35 && statoProcedimento!=38 && statoProcedimento!=34 && statoProcedimento!=21 && statoProcedimento!=2) {
 					
 							if(document.getElementsByName("Modifica").length != 0) {
 		 						document.getElementsByName("Modifica")[0].disabled=true;
 		 					}
 					 
 					}
 					
 					if(document.getElementsByName("CopiaBene").length != 0) {
 						document.getElementsByName("CopiaBene")[0].disabled=true;
 					}
 					if(tipoProcedimento!=12) {
 						if(document.getElementsByName("TitoliDiDiritto").length != 0) {
 							document.getElementsByName("TitoliDiDiritto")[0].disabled=true;
 						}
 					}
 					
 					if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0) {
 						document.getElementsByName("CopiaTitoliDiDiritto")[0].disabled=true;
 					}
 					if(document.getElementsByName("EliminaBene").length != 0) {
 						document.getElementsByName("EliminaBene")[0].disabled=true;
 					}
		   }
 		 }
 	}
	
	function disabilitaSequestro(disabilita,tipoUfficio){
		
		 if(tipoUfficio==8){
				document.getElementsByName("Esecuzione")[0].disabled=true;   	 
	     }
   }	
	

	function disabilitaRevocaSequestro(disabilita,tipoUfficio){

		 if(tipoUfficio==8){
				document.getElementsByName("Esecuzione")[0].disabled=true;   	 
	     }
  }

function disabilitaSubmitTitoliDiInteresse(disabilita, idStatoProc, profilo, attoView){
	
	if(disabilita && profilo==17){
		if(idStatoProc!=2 && idStatoProc!=11){
			var i =document.getElementsByName("identificativoSoggettoProcedimento").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("identificativoSoggettoProcedimento")[j].disabled=true;
			}

			document.getElementsByName("Associa")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoFisico")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoGiuridico")[0].disabled=true;
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("Elimina")[0].disabled=true;
		}
		if(idStatoProc==11 && attoView==""){
			var i =document.getElementsByName("identificativoSoggettoProcedimento").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("identificativoSoggettoProcedimento")[j].disabled=true;
			}
			document.getElementsByName("Associa")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoFisico")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoGiuridico")[0].disabled=true;
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("Elimina")[0].disabled=true;
		}
	}
	if(disabilita && profilo==19){
		if(idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11){
			var i =document.getElementsByName("identificativoSoggettoProcedimento").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("identificativoSoggettoProcedimento")[j].disabled=true;
			}
			document.getElementsByName("Associa")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoFisico")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoGiuridico")[0].disabled=true;
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("Elimina")[0].disabled=true;
		}
		if((idStatoProc==2 || idStatoProc==6 || idStatoProc==11) && (attoView=="")){
			var i =document.getElementsByName("identificativoSoggettoProcedimento").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("identificativoSoggettoProcedimento")[j].disabled=true;
			}
			document.getElementsByName("Associa")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoFisico")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoGiuridico")[0].disabled=true;
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("Elimina")[0].disabled=true;
		}
	} 	
	if(disabilita && profilo==6){
		
			var i =document.getElementsByName("identificativoSoggettoProcedimento").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("identificativoSoggettoProcedimento")[j].disabled=true;
			}
			document.getElementsByName("Associa")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoFisico")[0].disabled=true;
			document.getElementsByName("TerzoSoggettoGiuridico")[0].disabled=true;
		
		
	}	
}

function disabilitaSubmitElencoAssociati(disabilita, idStatoProc, idTipoUfficio){

	if(disabilita && (idStatoProc != 4 && idStatoProc != 11 && idStatoProc != 3  && idStatoProc != 22 && idStatoProc != 29)){
		var i =document.getElementsByName("magistratoForm.id").length;
		var idUfficioCda = 6;
		var idUfficioPG = 8;
		var idStatoProcImpugnatoCda = 12;
		var idStatoProcIscrittoOpposizione = 25;
		for(var j =0;j<i;j++){
			document.getElementsByName("magistratoForm.id")[j].disabled=true;
		}
		if(idStatoProc != idStatoProcImpugnatoCda && idTipoUfficio != idUfficioCda){
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("Elimina")[0].disabled=true;
			document.getElementsByName("Nuovo")[0].disabled=true;			
		}
		//Segnalazione ASS002
		if(idStatoProc == idStatoProcIscrittoOpposizione && idTipoUfficio == idUfficioPG){
			document.getElementsByName("Modifica")[0].disabled=false;
			document.getElementsByName("Elimina")[0].disabled=false;
			document.getElementsByName("Nuovo")[0].disabled=false;			
		}

	}
	
	if(idStatoProc == 23 && idTipoUfficio == 8) {
		var i =document.getElementsByName("magistratoForm.id").length;
		for(var j =0;j<i;j++){
			document.getElementsByName("magistratoForm.id")[j].disabled=false;
		}		
		document.getElementsByName("Modifica")[0].disabled=false;
		document.getElementsByName("Elimina")[0].disabled=false;
		document.getElementsByName("Nuovo")[0].disabled=false;	
	}
	
}

function disabilitaSubmitDirettiInteressati(disabilita,profilo,idStatoProc,tipoProcedimento){
	if(disabilita){
		if(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)){
			document.getElementsByName("Modifica")[0].disabled=true;
		}
		if(profilo==17 && (idStatoProc!=11)){
			document.getElementsByName("Modifica")[0].disabled=true;
		}
		if(profilo==6){
			if(tipoProcedimento != 13 && tipoProcedimento != 14) {
				document.getElementsByName("Modifica")[0].disabled=true;
			}
			document.getElementsByName("MisuraPersonale")[0].disabled=true;
			document.getElementsByName("MisuraPatrimoniale")[0].disabled=true;
		}		
		document.getElementsByName("Cancella")[0].disabled=true;
	}	
	
	if(tipoProcedimento==12) {
		document.getElementsByName("MisuraPersonale")[0].disabled=true;
	}
	
	if(tipoProcedimento==17 || tipoProcedimento==18 || tipoProcedimento==19) {
		document.getElementsByName("Modifica")[0].disabled=true;
		document.getElementsByName("Cancella")[0].disabled=true;
		document.getElementsByName("MisuraPersonale")[0].disabled=true;
		document.getElementsByName("MisuraPatrimoniale")[0].disabled=true;
	}	
	
	if(tipoProcedimento==18) {
		document.getElementsByName("Modifica")[0].disabled=false;
	}
	
	//disabilita per il giudizio definitivp
	if(idStatoProc==22) {
		document.getElementsByName("Modifica")[0].disabled=true;
		document.getElementsByName("MisuraPersonale")[0].disabled=true;
		document.getElementsByName("MisuraPatrimoniale")[0].disabled=true;
	}	

}

function disabilitaSubmitDirettiInteressatiRiesame(disabilita,profilo,idStatoProc){
	if(disabilita 
		&& (
			(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)) ||
			(profilo==17 && (idStatoProc!=11)) ||
			(profilo==6)
		)
	) {
		document.getElementsByName("Modifica")[0].disabled=true; 
		document.getElementsByName("Difensori")[0].disabled=true; 
	}	
}

function disabilitaSubmitDirettiInteressatiTerzi(disabilita,profilo,idStatoProc){
	
	if(disabilita){
		if(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)){
			document.getElementsByName("Modifica")[0].disabled=true;
			if(document.getElementsByName("CANCELLA").length != 0) {
				document.getElementsByName("CANCELLA")[0].disabled=true;
			}
		}
		if(profilo==17 && (idStatoProc!=11)){
			document.getElementsByName("Modifica")[0].disabled=true;
			if(document.getElementsByName("CANCELLA").length != 0) {
				document.getElementsByName("CANCELLA")[0].disabled=true;
			}
		}
		if(profilo==6){
			document.getElementsByName("Modifica")[0].disabled=true;
			if(document.getElementsByName("CANCELLA").length != 0) {
				document.getElementsByName("CANCELLA")[0].disabled=true;
			}
		}
	}	
}
		
function disabilitaSubmitDirettiInteressatiAccertamento(idTipoProc, idStatoProc){
	if(idTipoProc == 8) { //se � un accertamento
		if(idStatoProc == 2) {
			document.getElementsByName("Modifica")[0].disabled=true;
			document.getElementsByName("CANCELLA")[0].disabled=true;
		}
	}
}

function disabilitaSubmitMisuraPatrimonialeWithAtto(disabilita, statoAtto){
	if(disabilita && statoAtto!=1){
		document.getElementsByName("soggetto.tipologiaPrevenzione.id")[0].disabled=true;
		document.getElementsByName("soggetto.normativa.id")[0].disabled=true;
		document.getElementsByName("Associa")[0].disabled=true;
		document.getElementsByName("Elimina")[0].disabled=true;
	}
}

function disabilitaSubmitMisuraPatrimoniale(disabilita){
	if(disabilita){
		document.getElementsByName("soggetto.tipologiaPrevenzione.id")[0].disabled=true;
		document.getElementsByName("soggetto.normativa.id")[0].disabled=true;
		document.getElementsByName("Associa")[0].disabled=true;
		document.getElementsByName("Elimina")[0].disabled=true;
	}
}

//misuraPersonale.jsp
function startWithAtto(disabilita, statoAtto, tipoProcedimento){
	
	if(disabilita) {
		disabilitaCampiWithAtto(statoAtto, tipoProcedimento);
	}
	else {
		abilita();
	}
}
function start(disabilita){
	if(disabilita) {
		disabilitaCampi();
	}
	else {
		abilita();
	}
}

function abilitaUlterioriPrescrizioni(campo) {
	if (campo.value == "32") {
		var id = document.getElementById("note");
		if (campo.checked) {
			id.readOnly = false;
		} else {
			id.value = "";
			id.readOnly = true;
		}

	}
}

function abilitaUlterioriPrescrizioni2(campo, value) {
	if (campo.value == "32") {
		var id = document.getElementById("note");
		if (campo.checked) {
			id.value = value;
			//id.disabled = false;
		} else {
			id.value = "";
			//id.disabled = true;
		}

	}
}

function abilita() {
	//var id = $("#id_prescrizioni :checked");
	var id = document.getElementsByName("misura.idSelezionati");
	for (var i = 0; i < id.length; i++) {
		if (id[i].checked)
			abilitaUlterioriPrescrizioni(id[i]);
	}

}

function disabilitaCampiWithAtto(statoAtto, tipoProcedimento){
	var esitoReadOnly = $('#esitoReadOnly').val();
	if(statoAtto!=1 || (esitoReadOnly != '' && typeof(esitoReadOnly) != 'undefined' && esitoReadOnly=='true')) {
		if(document.getElementsByName("misura.decisioneMisuraPersonale.id").length != 0)
			document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].disabled=true;
	
		document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=true;
		document.getElementsByName("normativa.id")[0].disabled=true;
		document.getElementsByName("misura.tipoMisura.id")[0].disabled=true; 
		document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=true; 
		document.getElementsByName("misura.localita")[0].disabled=true; 
		document.getElementsByName("misura.anni")[0].disabled=true; 
		document.getElementsByName("misura.mesi")[0].disabled=true; 
		document.getElementsByName("misura.giorni")[0].disabled=true; 

		var i = document.getElementsByName("misura.idSelezionati").length;
		for(var j =0;j<i;j++){
			document.getElementsByName("misura.idSelezionati")[j].disabled=true;
		}
		document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=true;
		
		document.getElementsByName("misura.cauzione")[0].disabled=true; 
		document.getElementsByName("misura.cauzione")[1].disabled=true;
	
		if(document.getElementsByName("misura.importo").length != 0) 
			document.getElementsByName("misura.importo")[0].disabled=true;
		
		if(document.getElementsByName("misura.note").length != 0)
			document.getElementsByName("misura.note")[0].disabled=true;
	
		document.getElementsByName("misura.art67")[0].disabled=true; 
		document.getElementsByName("misura.art67")[1].disabled=true;
	
		if(document.getElementsByName("SalvaMisuraDecisorioIGrado").length != 0) 
			document.getElementsByName("SalvaMisuraDecisorioIGrado")[0].disabled=true; 
	
		if(document.getElementsByName("Salva").length != 0)
			document.getElementsByName("Salva")[0].disabled=true; 
	
		document.getElementsByName("ELIMINA")[0].disabled=true;
	} else {
		// Stato dell'atto � 1 = In lavorazione
		if(document.getElementsByName("misura.decisioneMisuraPersonale.id").length != 0){
			// Se � presente "misura.decisioneMisuraPersonale.id" sto nel Decisorio
			if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '-1'
					// Esito (ACCOGLIE) con Desc_carattere_misura = P
				&& (document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '28'
					// Esiti (ACCOGLIE...) con Desc_carattere_misura = G (Aggravamento)
					&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '50'
						&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '51'
							&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '52'
								&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '53'
									//&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '223'
						                && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '226'
						                	&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '227'
						                		&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '228'
						                			&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '229'	
						                				&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '235'
						                				&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '236'
				                                        && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '237'
				                                         && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '238'
				                                        && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '243'	 
				                                         && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '252'	 
				                                        && (tipoProcedimento!='14' || document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '230'))){
				// Se ho selezionato una decisione diversa da "ACCOGLIE"
				
				if(document.getElementsByName("tipologiaPrevenzione")!= null && document.getElementsByName("tipologiaPrevenzione.id")[0]!= null ) document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=true;
				
				if(document.getElementsByName("normativa.id")!= null && document.getElementsByName("normativa.id")[0]!=null ) document.getElementsByName("normativa.id")[0].disabled=true;
				
				if(document.getElementsByName("misura.tipoMisura.id")!= null && document.getElementsByName("misura.tipoMisura.id")[0] != null )	document.getElementsByName("misura.tipoMisura.id")[0].disabled=true; 
				
				if(document.getElementsByName("misura.normativaDiRiferimento.id")!= null && document.getElementsByName("misura.normativaDiRiferimento.id")[0]!= null) document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=true; 
				
				if(document.getElementsByName("misura.localita")!= null && document.getElementsByName("misura.localita")[0]!=null ) document.getElementsByName("misura.localita")[0].disabled=true; 
				
				if(document.getElementsByName("misura.anni")!= null  && document.getElementsByName("misura.anni")[0]!=null ) document.getElementsByName("misura.anni")[0].disabled=true; 
				
				if(document.getElementsByName("misura.mesi")!= null  && document.getElementsByName("misura.mesi")[0]!=null) document.getElementsByName("misura.mesi")[0].disabled=true; 
				
				if(document.getElementsByName("misura.giorni")!= null && document.getElementsByName("misura.giorni")[0]!=null) document.getElementsByName("misura.giorni")[0].disabled=true; 
                
				
				if(document.getElementsByName("misura.idSelezionati")!= null) {
				var i = document.getElementsByName("misura.idSelezionati").length;
				for(var j =0;j<i;j++){
					document.getElementsByName("misura.idSelezionati")[j].disabled=true;
				}
				}
				
				if(document.getElementsByName("misura.ulterioriPrescrizioni")!= null && document.getElementsByName("misura.ulterioriPrescrizioni")[0]!= null ) document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=true;
				
				
				
				if(document.getElementsByName("misura.cauzione")!= null){
					if(document.getElementsByName("misura.cauzione")[0] != null) document.getElementsByName("misura.cauzione")[0].disabled=true; 
					if(document.getElementsByName("misura.cauzione")[1] != null) document.getElementsByName("misura.cauzione")[1].disabled=true;
				}
				
				if(document.getElementsByName("misura.art67")!= null){
					if(document.getElementsByName("misura.art67")[0] != null) document.getElementsByName("misura.art67")[0].disabled=true; 
					if(document.getElementsByName("misura.art67")[1] != null) document.getElementsByName("misura.art67")[1].disabled=true;
				}
				
				if(document.getElementsByName("misura.importo")!= null && document.getElementsByName("misura.importo").length != 0) 
					document.getElementsByName("misura.importo")[0].disabled=true;
				
				if(document.getElementsByName("misura.note")!= null &&  document.getElementsByName("misura.note").length != 0)
					document.getElementsByName("misura.note")[0].disabled=true;
			
				
			}
			else{
				// Se sono al primo accesso alla pagina oppure ho selezionato "ACCOGLIE"
				
//				MODIFICA PERSONALE - le varie tipologie di "ACCOGLIE" vengono gestite singolarmente in maniera diversa
				if(tipoProcedimento=='14') {
//					"ACCOGLIE"
					if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '226'
//						|| document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '227'
						|| document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '228'
						|| document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '229'
						|| document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '230') {
					
						if(document.getElementsByName("tipologiaPrevenzione")!= null && document.getElementsByName("tipologiaPrevenzione.id")[0]!= null ) document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=true;
						
						if(document.getElementsByName("normativa.id")!= null && document.getElementsByName("normativa.id")[0]!=null ) document.getElementsByName("normativa.id")[0].disabled=true;
						
						if(document.getElementsByName("misura.tipoMisura.id")!= null && document.getElementsByName("misura.tipoMisura.id")[0] != null )	document.getElementsByName("misura.tipoMisura.id")[0].disabled=true; 
						
						if(document.getElementsByName("misura.normativaDiRiferimento.id")!= null && document.getElementsByName("misura.normativaDiRiferimento.id")[0]!= null) document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=true; 
						
						if(document.getElementsByName("misura.localita")!= null && document.getElementsByName("misura.localita")[0]!=null ) document.getElementsByName("misura.localita")[0].disabled=true; 
						
						if(document.getElementsByName("misura.anni")!= null  && document.getElementsByName("misura.anni")[0]!=null ) document.getElementsByName("misura.anni")[0].disabled=true; 
						
						if(document.getElementsByName("misura.mesi")!= null  && document.getElementsByName("misura.mesi")[0]!=null) document.getElementsByName("misura.mesi")[0].disabled=true; 
						
						if(document.getElementsByName("misura.giorni")!= null && document.getElementsByName("misura.giorni")[0]!=null) document.getElementsByName("misura.giorni")[0].disabled=true; 
		                
						
						if(document.getElementsByName("misura.idSelezionati")!= null) {
						var i = document.getElementsByName("misura.idSelezionati").length;
						for(var j =0;j<i;j++){
							document.getElementsByName("misura.idSelezionati")[j].disabled=true;
						}
						}
						
						if(document.getElementsByName("misura.ulterioriPrescrizioni")!= null && document.getElementsByName("misura.ulterioriPrescrizioni")[0]!= null ) document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=true;
						
						
						
						if(document.getElementsByName("misura.cauzione")!= null){
							if(document.getElementsByName("misura.cauzione")[0] != null) document.getElementsByName("misura.cauzione")[0].disabled=true; 
							if(document.getElementsByName("misura.cauzione")[1] != null) document.getElementsByName("misura.cauzione")[1].disabled=true;
						}
						
						if(document.getElementsByName("misura.art67")!= null){
							if(document.getElementsByName("misura.art67")[0] != null) document.getElementsByName("misura.art67")[0].disabled=true; 
							if(document.getElementsByName("misura.art67")[1] != null) document.getElementsByName("misura.art67")[1].disabled=true;
						}
						
						if(document.getElementsByName("misura.importo")!= null && document.getElementsByName("misura.importo").length != 0) 
							document.getElementsByName("misura.importo")[0].disabled=true;
						
						if(document.getElementsByName("misura.note")!= null &&  document.getElementsByName("misura.note").length != 0)
							document.getElementsByName("misura.note")[0].disabled=true;
						
						
						
//						ACCOGLIE - RIDUCENDO LA MISURA
						if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '226') {
							document.getElementsByName("misura.anni")[0].disabled=false;
							document.getElementsByName("misura.mesi")[0].disabled=false;
							document.getElementsByName("misura.giorni")[0].disabled=false;
						} 
//						ACCOGLIE - RIDETERMINANDO LA MISURA
//						else if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '227') {
//							
//						} 
//						ACCOGLIE - REVOCA OBBLIGO DI SOGGIORNO
						else if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '228') {
							document.getElementsByName("misura.tipoMisura.id")[0].disabled=false;
							document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=false;
							document.getElementsByName("misura.localita")[0].disabled=false;
						} 
//						ACCOGLIE - MODIFICA LUOGO SOGGIORNO OBBLIGATO
						else if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '229') {
							document.getElementsByName("misura.localita")[0].disabled=false;
						} 
//						ACCOGLIE - MODIFICA LE PRESCRIZIONI
						else if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value == '230') {
							if(document.getElementsByName("misura.idSelezionati")!= null) {
								var i = document.getElementsByName("misura.idSelezionati").length;
								for(var j =0;j<i;j++){
									document.getElementsByName("misura.idSelezionati")[j].disabled=false;
								}
							}
								
							document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=false;
						} 
					}
				}
				else {
				
				
				document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=false;
				document.getElementsByName("normativa.id")[0].disabled=false;
				document.getElementsByName("misura.tipoMisura.id")[0].disabled=false; 
				document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=false; 
				document.getElementsByName("misura.localita")[0].readOnly=false; 
				document.getElementsByName("misura.anni")[0].disabled=false; 
				document.getElementsByName("misura.mesi")[0].disabled=false; 
				document.getElementsByName("misura.giorni")[0].disabled=false; 

				var i = document.getElementsByName("misura.idSelezionati").length;
				for(var j =0;j<i;j++){
					document.getElementsByName("misura.idSelezionati")[j].disabled=false;
				}
				document.getElementsByName("misura.ulterioriPrescrizioni")[0].readOnly=false;
				
				document.getElementsByName("misura.cauzione")[0].disabled=false; 
				document.getElementsByName("misura.cauzione")[1].disabled=false;
			
				if(document.getElementsByName("misura.importo").length != 0) 
					document.getElementsByName("misura.importo")[0].disabled=false;
				
				if(document.getElementsByName("misura.note").length != 0)
					document.getElementsByName("misura.note")[0].disabled=false;
			
				document.getElementsByName("misura.art67")[0].disabled=false; 
				document.getElementsByName("misura.art67")[1].disabled=false;
			}
			}
			document.getElementsByName("ELIMINA")[0].disabled=true;			
		}
		// Se non � presente "misura.decisioneMisuraPersonale.id" non sto nel Decisorio
		else abilita();
	}
}

function disabilitaBottoneSalvaDecisorioIIGrado(disabilita){
	if(disabilita=='true'){	
		if(document.getElementsByName("SalvaMisuraDecisorioIIGrado").length != 0) 
			document.getElementsByName("SalvaMisuraDecisorioIIGrado")[0].disabled=true;	
	} 
}

function disabilitaBottoneSalvaSentenzaCassazione(disabilita){
	if(disabilita=='true'){	
		if(document.getElementsByName("SalvaMisuraSentenzaCassazione").length != 0) 
			document.getElementsByName("SalvaMisuraSentenzaCassazione")[0].disabled=true;	
	} 
}

function disabilitaCampi(){
	if(document.getElementsByName("misura.decisioneMisuraPersonale.id").length != 0)
		document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].disabled=true;

	document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=true;
	document.getElementsByName("normativa.id")[0].disabled=true;
	document.getElementsByName("misura.tipoMisura.id")[0].disabled=true; 
	document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=true; 
	document.getElementsByName("misura.localita")[0].readOnly=true; 
	document.getElementsByName("misura.anni")[0].disabled=true; 
	document.getElementsByName("misura.mesi")[0].disabled=true; 
	document.getElementsByName("misura.giorni")[0].disabled=true; 

	var i = document.getElementsByName("misura.idSelezionati").length;
	for(var j =0;j<i;j++){
		document.getElementsByName("misura.idSelezionati")[j].disabled=true;
	}
	
	document.getElementsByName("misura.cauzione")[0].disabled=true; 
	document.getElementsByName("misura.cauzione")[1].disabled=true;

	if(document.getElementsByName("misura.importo").length != 0) 
		document.getElementsByName("misura.importo")[0].disabled=true; 

	document.getElementsByName("misura.art67")[0].disabled=true; 
	document.getElementsByName("misura.art67")[1].disabled=true;

	if(document.getElementsByName("SalvaMisuraDecisorioIGrado").length != 0) 
		document.getElementsByName("SalvaMisuraDecisorioIGrado")[0].disabled=true; 

	if(document.getElementsByName("Salva").length != 0)
		document.getElementsByName("Salva")[0].disabled=true; 

	document.getElementsByName("ELIMINA")[0].disabled=true;
	}

//FINE misuraPersonale.jsp

function disabilitaCampiNuovaFonte(disabilita, statoAtto){
	if(disabilita){
		document.getElementById("id_fonte").disabled=true;
		document.getElementsByName("organo.id")[0].disabled=true;
		document.getElementsByName("provinciaOrgano.id")[0].disabled=true;
		document.getElementsByName("comuneOrgano.id")[0].disabled=true;
		document.getElementsByName("indirizzoOrgano.id")[0].disabled=true;
		document.getElementById("id_altroOrgano").disabled=true;
		document.getElementsByName("numeroRiferimento")[0].disabled=true;
		document.getElementById("dataInformativa").disabled=true;
		document.getElementById("id_note").readOnly=true;
		document.getElementById("dataRicezione").disabled=true;
		if(document.getElementsByName("ConfermaInserimento").length != 0)
			document.getElementsByName("ConfermaInserimento")[0].disabled=true; 
		if(document.getElementsByName("ConfermaModifica").length != 0)
			document.getElementsByName("ConfermaModifica")[0].disabled=true; 
		document.getElementsByName("fileData")[0].disabled=true;
		if(document.getElementsByName("cancFile").length != 0)
			document.getElementsByName("cancFile")[0].style.visibility="hidden"; 
	}
	else if(statoAtto == 3){
		document.getElementById("id_fonte").disabled=true;
		document.getElementsByName("organo.id")[0].disabled=true;
		document.getElementsByName("provinciaOrgano.id")[0].disabled=true;
		document.getElementsByName("comuneOrgano.id")[0].disabled=true;
		document.getElementsByName("indirizzoOrgano.id")[0].disabled=true;
		if(document.getElementById("id_altroOrgano")!=null)
			document.getElementById("id_altroOrgano").disabled=true;
		document.getElementsByName("numeroRiferimento")[0].disabled=true;
		document.getElementById("dataInformativa").disabled=true;
		document.getElementById("id_note").readOnly=true;
	}
}

function disabilitaCampiAttoAccertamento(disabilita, statoAtto){
	if(disabilita){
		document.getElementById("id_note").readOnly=true;
	}
	else if(statoAtto == 3){
		document.getElementById("id_note").readOnly=true;
	}
}

function disabilitaCampiProposta(disabilita, idStatoProc){
	if(disabilita){
		if(document.getElementsByName("fileData").length != 0)
			document.getElementsByName("fileData")[0].disabled=true;
		if(document.getElementsByName("cancFile").length != 0)
			document.getElementsByName("cancFile")[0].style.visibility="hidden"; 
		if(document.getElementsByName("note").length != 0)
			document.getElementsByName("note")[0].readOnly=true;
		if(document.getElementsByName("numeroRiferimento").length != 0)
			document.getElementsByName("numeroRiferimento").disabled=true;
		if(document.getElementsByName("dataEmissione").length != 0)
			document.getElementsByName("dataEmissione").disabled=true;
		if(document.getElementsByName("dataDeposito").length != 0)
			document.getElementsByName("dataDeposito").disabled=true; 
		if(idStatoProc != 38){
			if(document.getElementsByName("destinatario.id").length != 0)
				document.getElementsByName("destinatario.id")[0].disabled=true;
			if(document.getElementsByName("destinatario2.id").length != 0)
				document.getElementsByName("destinatario2.id")[0].disabled=true;
			if(document.getElementsByName("Conferma").length != 0)
				document.getElementsByName("Conferma")[0].disabled=true;
			if(document.getElementsByName("Invia").length != 0)
				document.getElementsByName("Invia")[0].disabled=true;
			}
	}
}

function disabilitaSubmitVisualizzaUdienze(disabilita){
	if(disabilita){
		document.getElementsByName("ModificaUdi")[0].disabled=true;
	}
}

function disabilitaSubmitConvalidaSeq(disabilita,menu,profilo){
	if(disabilita && menu  == 'atto_decretiConvalida'){
		if(document.getElementsByName("FrazionamentoBene").length != 0)
			document.getElementsByName("FrazionamentoBene")[0].style.visibility="hidden";
		if(document.getElementsByName("EliminaBene").length != 0)
			document.getElementsByName("EliminaBene")[0].style.visibility="hidden"; 
		if(document.getElementsByName("CopiaBene").length != 0)
			document.getElementsByName("CopiaBene")[0].style.visibility="hidden"; 
		if(document.getElementsByName("TitoliDiDiritto").length != 0)
			document.getElementsByName("TitoliDiDiritto")[0].style.visibility="hidden"; 
		if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0)
			document.getElementsByName("CopiaTitoliDiDiritto")[0].style.visibility="hidden";
	}
	if(disabilita && profilo == 6) {
		if(document.getElementsByName("FrazionamentoBene").length != 0)
			document.getElementsByName("FrazionamentoBene")[0].disabled=true;
		if(document.getElementsByName("EliminaBene").length != 0)
			document.getElementsByName("EliminaBene")[0].disabled=true;
		if(document.getElementsByName("CopiaBene").length != 0)
			document.getElementsByName("CopiaBene")[0].disabled=true;
		if(document.getElementsByName("TitoliDiDiritto").length != 0)
			document.getElementsByName("TitoliDiDiritto")[0].disabled=true;
		if(document.getElementsByName("CopiaTitoliDiDiritto").length != 0)
			document.getElementsByName("CopiaTitoliDiDiritto")[0].disabled=true;
	}
}

function disabilitaSubmitCollegioElenco(disabilita, idStatoProc, idTipoProc){

	if(disabilita && (
			(idStatoProc != 2 && idStatoProc != 6 && idStatoProc != 11
					&& idStatoProc != 13 && idStatoProc != 25 && idStatoProc != 26 && idStatoProc != 27
			&& idStatoProc != 43 && idStatoProc != 44 && idStatoProc != 45)
			|| (idTipoProc == 8)
		)
	) {  
		if(document.getElementsByName("Sostituisci").length != 0) {
			document.getElementsByName("Sostituisci")[0].disabled=true;
		}
		if(document.getElementsByName("Modifica").length != 0) {
			document.getElementsByName("Modifica")[0].disabled=true;
		}
		if(document.getElementsByName("Elimina").length != 0) {
			document.getElementsByName("Elimina")[0].disabled=true;
		}
		if(document.getElementsByName("Nuovo").length != 0) {
			document.getElementsByName("Nuovo")[0].disabled=true;
		}
		idTipoProc
	}
}

function disabilitaSubmitCollegioElencoAtto(idStatoAtto,idCategoriaAtto){
	if((idCategoriaAtto == 8 || idCategoriaAtto == 57 || idCategoriaAtto == 58) && idStatoAtto == 3){  
		document.getElementsByName("Modifica")[0].disabled=true;
		document.getElementsByName("Elimina")[0].disabled=true;
		document.getElementsByName("Nuovo")[0].disabled=true;
	}
}

function disabilitaCampiDifensoriAssociati(disabilita){
	if(disabilita){     
		if(document.getElementsByName("ModificaDifensore").length != 0)
			document.getElementsByName("ModificaDifensore")[0].disabled=true;
		if(document.getElementsByName("CancellaDifensore").length != 0)
			document.getElementsByName("CancellaDifensore")[0].disabled=true;
		if(document.getElementsByName("AbilitaRicerca").length != 0)
			document.getElementsByName("AbilitaRicerca")[0].disabled=true;
		if(document.getElementsByName("RicercaDifensore").length != 0)
			document.getElementsByName("RicercaDifensore")[0].disabled=true;
		if(document.getElementsByName("AssociaDifensoreReginde").length != 0)
			document.getElementsByName("AssociaDifensoreReginde")[0].disabled=true;
		if(document.getElementsByName("NuovoDifensore").length != 0)
			document.getElementsByName("NuovoDifensore")[0].disabled=true;
	}
}

function disabilitaDecisorioBeni(disabilita,statoAtto){
	//if(disabilita || statoAtto!=1){
	if(statoAtto!=1){
		document.getElementsByName("decisioneBeni.id")[0].disabled=true;
		var i = document.getElementsByName("listaIdCheckati").length;
		for(var j =0;j<i;j++){
			document.getElementsByName("listaIdCheckati")[j].disabled=true;
		}
		document.getElementsByName("SalvaDecisioneBeniDecisorioIGrado")[0].disabled=true;
	}
}

function disabilitaCopertinaDefinitivita(statoProc, disableDefinitivita){
	
	if(disableDefinitivita == 'true'){
		if(document.getElementById("id_dataDefinitivitaAtto")!=null)
			document.getElementById("id_dataDefinitivitaAtto").disabled=true;
		if(document.getElementById("all")!=null)
			document.getElementById("all").disabled=true;
		if(document.getElementById("Salva")!=null)
			document.getElementById("Salva").disabled=true;
		if(document.getElementById("ConfermaInviaDefinitivita")!=null)
			document.getElementById("ConfermaInviaDefinitivita").disabled=true;
	} 	

}

function disabilitaDefinitivitaIncompetenza(disableDefinitivita){
	if(disableDefinitivita == 'true'){
		if(document.getElementById("ConfermaDefinitivita")!=null)
			document.getElementById("ConfermaDefinitivita").disabled=true;
	} 	
	
function disabilitaPermessi(){
	
	
	if(document.getElementsByName("tipologiaPrevenzione")!= null &&  document.getElementsByName("tipologiaPrevenzione.id")[0] != null ) document.getElementsByName("tipologiaPrevenzione.id")[0].disabled=true;
	
	
	if(document.getElementsByName("normativa.id")!= null &&  document.getElementsByName("normativa.id")[0] != null) document.getElementsByName("normativa.id")[0].disabled=true;
	
	
	if(document.getElementsByName("misura.tipoMisura.id")!= null)	document.getElementsByName("misura.tipoMisura.id")[0].disabled=true; 
	
	
	if(document.getElementsByName("misura.normativaDiRiferimento.id")!= null && document.getElementsByName("misura.normativaDiRiferimento.id")[0]!= null) document.getElementsByName("misura.normativaDiRiferimento.id")[0].disabled=true; 
	
	
	if(document.getElementsByName("misura.localita")!= null && document.getElementsByName("misura.localita")[0]!=null ) document.getElementsByName("misura.localita")[0].disabled=true; 
	
	
	if(document.getElementsByName("misura.anni")!= null  && document.getElementsByName("misura.anni")[0]!=null ) document.getElementsByName("misura.anni")[0].disabled=true; 
	
	
	if(document.getElementsByName("misura.mesi")!= null  && document.getElementsByName("misura.mesi")[0]!=null) document.getElementsByName("misura.mesi")[0].disabled=true; 
	
	
	if(document.getElementsByName("misura.giorni")!= null && document.getElementsByName("misura.giorni")[0]!=null) document.getElementsByName("misura.giorni")[0].disabled=true; 
    
	
	if(document.getElementsByName("misura.idSelezionati")!= null) {
	var i = document.getElementsByName("misura.idSelezionati").length;
	for(var j =0;j<i;j++){
		document.getElementsByName("misura.idSelezionati")[j].disabled=true;
	}
	}
	
	if(document.getElementsByName("misura.ulterioriPrescrizioni")!= null && document.getElementsByName("misura.ulterioriPrescrizioni")[0]!= null ) document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=true;
	
	
	
	
	
}	
	

function disabilitaSubmitAssociaIntervenienti(disabilita,profilo,idStatoProc,tipoProcedimento){
	if(disabilita){
		if(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)){
			document.getElementsByName("AssociaSoggetto")[0].disabled=true;
		}
		if(profilo==17 && (idStatoProc!=11)){
			document.getElementsByName("AssociaSoggetto")[0].disabled=true;
		}
		if(profilo==6){
			document.getElementsByName("AssociaSoggetto")[0].disabled=true;
		}		
		document.getElementsByName("EliminaAssociazioneSoggetto")[0].disabled=true;
	}	
}

}