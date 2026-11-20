<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="it.giustizia.smp.utility.TipoBollettino"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/dettaglioBeneFug.htm" />
		<p class="titolo_page">
			<spring:message code="titolo.dettaglio.bene" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" onsubmit="componiNumeroBollettino()">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>
			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.sottocategoria.descrizione}" /></td>
				</tr>

				<logic:equal name="beneForm" property="tipologia.id" value="2">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/fug/includeFugDenaro.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/fug/includeFugBeneFinanziario.jsp" />
				</logic:equal>
			<form:hidden path="tipologia.id" />
			<form:hidden path="categoria.id" />
			<form:hidden path="sottocategoria.id" />
			<form:hidden path="idBene" />
		<tr>
				<td><form:label path="modelloFugSelected.id">
						<spring:message code="fug.modello.label"/>
					</form:label>
				</td>
				<td colspan="3">
					<form:select path="modelloFugSelected.id" onchange="submit()">
						<form:option value="-1" label="-------Seleziona Modello -------"></form:option>
						<form:option value="A" label="Modello A"></form:option>
						<form:option value="C" label="Modello C"></form:option>
						<form:option value="C2" label="Modello C-Bis"></form:option>
						<form:option value="D" label="Modello D"></form:option>
						<form:option value="H" label="Modello H"></form:option>
					</form:select>
					<form:errors path="modelloFugSelected.id" class="testoRosso"/>
				</td>
		</tr>
	</table>
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva bene" name="Modifica"></td>
					<td class="td_tasto"><input type="submit" name="ConfermaModello" value="Conferma modello"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
		
		function myTrim(x) {
		    return x.replace(/^\s+|\s+$/gm,'');
		}
		
		function componiNumeroBollettino(){
			//var numBollettino = $("#numBollettinoSx").val().trim() + $("#numBollettinoCx").html().trim()+ $("#numBollettinoDx").val().trim(); 
			var step1 = $("#numBollettinoSx").val(); 
			var step2 = $("#numBollettinoCx").html(); 
			var step3 = $("#numBollettinoDx").val();
			var numBollettino = myTrim(step1) + myTrim(step2) + myTrim(step3) ; 
			$("#numBollettino").val(numBollettino);
			//console.log("componiNumeroBollettino: "+numBollettino);
		}

		function formattaCampiNumeroBollettino(combo){
			var idBollettino = $(combo).val();
			//console.log("formattaCampiNumeroBollettino: "+idBollettino);
			if (mappaturaTipoBollettino.hasOwnProperty(idBollettino)) {
				var tipoBollettino = mappaturaTipoBollettino[idBollettino];
				var tag = tipoBollettino[0];
				var maxSx = tipoBollettino[1];
				var maxDx = tipoBollettino[2];
				//console.log(idBollettino + " -> " + tag + ", " + maxSx + "." + maxDx);
				$("#numBollettinoCx").html(tag);
				$("#numBollettinoSx").val(''); 
				$("#numBollettinoDx").val('');
				if (maxSx=='0'){
					$("#numBollettinoSx").hide(); 
				} else {
					$("#numBollettinoSx").show(); 
				}
				var sx1 = parseInt(maxSx);
				var sx2 = sx1+3;
				if (sx2<10){
					sx2=10;
				}
				var dx1 = parseInt(maxDx);
				var dx2 = dx1+3;
				if (dx2<10){
					dx2=10;
				}
				//console.log("sx: "+sx1+"->"+sx2);
				//console.log("dx: "+dx1+"->"+dx2);
				$("#numBollettinoSx").attr('maxlength',maxSx);
				$("#numBollettinoSx").attr('size',sx2);
				$("#numBollettinoDx").attr('maxlength',maxDx);
				$("#numBollettinoDx").attr('size',dx2);
			}
		}

		var mappaturaTipoBollettino = { '-1' : [ '', '0', '4' ]
<c:forEach items="${mappaTipoBollettino}" var="entry">
	    , '${entry.key}' : ['${entry.value.tag}', '${entry.value.sizeSx}', '${entry.value.sizeDx}']
</c:forEach>
		};
		</script>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
