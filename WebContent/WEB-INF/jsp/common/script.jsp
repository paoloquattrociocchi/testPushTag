
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="it.giustizia.smp.utility.Constants"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<link href="${pageContext.request.contextPath}/css/generale.css" media="all" rel="stylesheet" type="text/css" charset="utf-8" />
<link href="${pageContext.request.contextPath}/css/jquery/jquery.ui.all.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery/jquery.ui.dialog.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery/jquery.ui.autocomplete.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/suggestlist/doAutocomplete.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/tree/tree.css" rel="stylesheet" type="text/css" />
<!-- 
<script	type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.corner.js"></script>
 -->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.core.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery/ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker-it.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.bgiframe.min.js"></script>


<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/AjaxAutoComplete.js'></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/rotator/jQueryRotate.2.1.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/UtilityDwr.js'></script>

<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";

	$(document).ready(function() {
		//menu
		controllaMenu();
	
	    //disabilito i button e i submit
	    $("input[type='submit']").click(function(e){
		    if(!$(this).hasClass("nowait")){
		    	return handleSubmit(e);
			}
	    });

	    $("button, input[type='button']").click(function(e){
	    	if(!$(this).hasClass("nowait")){
	    		e.preventDefault();
	    		return handleSubmit(e);
	    	}
	    });

	 // focus on the first text input field in the first field on the page
		$("input[type='text']:first", document.forms[0]).focus();

		//$(".round-body").corner("round 7px");
	<c:forEach items="${NAME_DA_DISABILITARE}" var="item">
		$('[name="${item}"]').attr('disabled', 'disabled');
	</c:forEach>

	disabilitaBackspace();
	
	});

	//non permette al browser di andare indietro di una pagina ma lo fa ritornare sempre alla pagina stessa
	function preventBack(){
		window.history.forward();
	}

   setTimeout("preventBack()", 0);
    window.onunload=function(){null};
    
	
	function disabilitaBackspace() {
		$(document).keydown(function(e) {
			var $target = $(e.target);
			var element = e.target.nodeName.toLowerCase();
			if (element != 'input' && element != 'textarea') {
				if (e.keyCode === 8) {
					return false;
				}
			} else {
				var readonly = $target.attr("readonly");
				var is_readonly = (readonly != 'undefined' && readonly == 'readonly')||readonly;
				var disabled_or_readonly = $target.is(':disabled') || is_readonly;
				if (disabled_or_readonly)
					return false;
			}
			return true;
		});
	}

	function handleSubmit(e) {
		$("#waitDialog").dialog({
			//autoOpen:false,
			height : 100,
			width : 250,
			modal : true,
			draggable : false,
			resizable : false,
			open : function(event, ui) {
				$(".ui-dialog-titlebar").hide();
				$("#waitDialog").css('overflow-y', 'hidden');
				$(".ui-dialog").css('opacity', '0.90').css('filter', 'alpha(opacity=90)');
			}
		});
		return true;
	}

	function showHideMenu() {
		var td_elem = document.getElementById("td_menu");
		var div_elem = document.getElementById("div_menu");
		var img_elem = document.getElementById("img_Menu");

		if (div_elem.style.display == "none") {
			div_elem.style.display = "block";
			td_elem.style.width = "";
			img_elem.src = '${pageContext.request.contextPath}/images/icoPiu.png';
			img_elem.title = "Nascondi Navigatore Laterale";
		} else {
			div_elem.style.display = "none";
			td_elem.style.width = "15px";
			img_elem.src = '${pageContext.request.contextPath}/images/icoMeno.png';
			img_elem.title = "Mostra Navigatore Laterale";
		}
	}


	   		
</script>



<script type='text/javascript' src="${pageContext.request.contextPath}/js/logout.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/formUtils.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/dateUtils.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery/scegliData.js"></script>

<script type='text/javascript' src="${pageContext.request.contextPath}/js/suggestlist/doAutocomplete.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteOpFinanziari.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteCodiciCab.js"></script>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteImmobile.js"></script>




<script type='text/javascript' src="${pageContext.request.contextPath}/js/tree/tree.js"></script>

<script type='text/javascript' src='${pageContext.request.contextPath}/js/firma/firmaRemota.js'></script>


<style type="text/css">
#id_a :hover {
	font-style: italic;
}

.round-body{
	border: 1px solid #D8D6D8;
	margin: 0 9px 20px 20px; 
	min-height: 200px;
	padding: 10px;
	width: 95%;
	/*float: right;*/
	
	
	border-radius: 7px;
	-moz-border-radius: 7px;
	-webkit-border-radius:7px;
	/**/
	
}

</style>






	