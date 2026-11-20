<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
	</tr>
</table>
<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">

	<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
	<display:column property="dataIscrizione" title="Data iscrizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
	<display:column property="misura.descrizioneMisura" title="Misure" sortable="true" />
	<display:column property="misura.cauzione" title="Cauzione" sortable="true" />
	<display:column property="misura.art67" title="Art.67" sortable="true" />
</display:table>
<br />
<br />
<logic:notEmpty name="direttiInteressatiNonValidi">
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimentoNonValidi" /></td>
		</tr>
	</table>
	<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressatiNonValidi}" pagesize="10" requestURI="${azione}">
		<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
		<display:column property="descrizioneProcedimentoDestinazione" title="Stralciato al/ai numeri" />
	</display:table>
</logic:notEmpty>