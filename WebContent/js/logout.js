var keyCd, altKy, mouseBtn, ctlKy, shiftKy;
var Browser = navigator.appName;
var Net = Browser.indexOf("Netscape");
var Micro = Browser.indexOf("Microsoft");
var Netscape = false;
var IE = false;

//disabilita il caching per le chiamate ajax
$.ajaxSetup({cache: false}); 

if (Net >= 0) {
    window.captureEvents(Event.MOUSEDOWN);
    window.onmousedown = HandleClose;
    Netscape = true;
}
if (Micro >= 0) {
    IE = true;
}

window.onbeforeunload = function(event) {
	return HandleClose(event);
	
}

function getKeyCode(event) {
    if (IE == true) {
        keyCd = event.keyCode;
        altKy = event.altKey;
        ctlKy = event.ctlKey;
        shiftKy = event.shiftKey;
    }
    if (Netscape == true) {
        if (event.which == null)
            keyCd = event.keyCode;
        else
            keyCd = event.which;
        altKy = event.altKey;
        ctlKy = event.ctrlKey;
        shiftKy = event.shiftKey;
    }
    if (keyCd == 13) {
        document.getElementById("ctl00_BtnLogout").disabled = true;
    }  
}
function getMouseButton(event) {
    if (IE == true) {
        mouseBtn = event.button;
    }
    if (Netscape == true) {
        if (event.which == null)
            mouseBtn = event.button;
        else
            mouseBtn = event.which;
       
    }
}
function HandleClose(e) {
	
    if (IE == true) {
        //Browser Closes through ALT + F4 buttons
        if (event.altKey == true && event.keyCode == 0) {
            //alert('Chiusura browser IE ALT+F4');
            return cleanSession();
        }
        //Browser Closes through Browser Close (X) button
        Xwidth = window.document.body.offsetWidth - window.event.clientX
        YHeight = window.event.clientY
        if (Xwidth <= 30 && YHeight < 0) {
        	 //alert('Chiusura browser IE 1');
        	 return cleanSession();
        }
        //Browser Closes through CTRL + F4 and CTRL + W buttons
        if (event.ctrlKey == true && event.keyCode == 0) {
        	//alert('Chiusura browser IE 2');
        	return cleanSession();
        }
        //Browser Closes through ALT + SPACE + C buttons
        if (keyCd == 32 && altKy == true) {
        	//alert('Chiusura browser IE 3');
        	return cleanSession();
        }
        //Browser Closes through Mouse right Click in Task bar
        if (mouseBtn == null && keyCd == null) {
            if (window.event.clientX > 1000) {
            	// nessuna azione perchè questo blocco viene eseguito
            	// quando si preme il bottone di logout
            	
            }
        }
    }
    else if (Netscape == true) {   
        //Browser Closes through ALT + F4 buttons
        if (altKy == true && keyCd == 115) {
        	alert('Chiusura browser Netscape 1');
        	return cleanSession();
        }
     
        //Browser Closes through CTRL + F4 and CTRL + W buttons
        if (ctlKy == true && (keyCd == 87 || keyCd == 115)) {
        	alert('Chiusura browser Netscape 2');
        	return cleanSession();
            
        }
        //Browser Closes through ALT + SPACE + C buttons
        if (altKy == true && keyCd == 18) {
        	alert('Chiusura browser Netscape 3');
        	return cleanSession();
        }
       
        //Browser Closes through Mouse right Click in Task bar
        if (mouseBtn == null && keyCd == null) {
        	
        }
    }
}

function cleanSession() {	
	//	alert(contextPath);
	//alert(logoutFromIAA);
	$("#logoutDiv").load(contextPath+"/logout.htm");	
	
	var p = window.open(logoutFromIAA,'_blank',''); 	 
	return p.close();	
}
