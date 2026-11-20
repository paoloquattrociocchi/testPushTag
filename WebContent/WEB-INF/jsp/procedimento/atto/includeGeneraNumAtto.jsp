<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
	<td class="td_tasto">
		<input type="button" onclick="return confermaGenerazione()" value="Genera n&deg; atto" title="Genera Numero Atto" class="nowait" />
	</td> 
	<input id="GeneraNumAtto" type="submit" name="GeneraNumAtto" value="" style="display: none" />

	<script type="text/javascript">	function confermaGenerazione() {
		if (confirm("Si vuole procedere con la generazione del numero atto prima dell'invio? Si ricorda che in caso affermativo verrà generato il numero definitivo dell'atto e non sarà più possibile eliminare né il numero né l'atto stesso.")) {
			inserisciAtto('GeneraNumAtto','${attoForm.allegato.documentoFirmato}');
			return true;
		}
		return false;
	}
	</script>
</c:if>