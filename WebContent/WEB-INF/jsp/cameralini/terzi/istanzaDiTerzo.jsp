<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
			<form:form id="id_terzi" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			 <br /><br />
				<table class="tb_bottoni" align="center">
					<tr>
					    <td class="td_tasto"><input type="submit" value="Soggetto fisico"    name="SoggettoFisico"        title="SoggettoFisico"></td>
                      	<td class="td_tasto"><input type="submit" value="Soggetto giuridico" name="SoggettoGiuridico"     title="SoggettoGiuridico" /></td>
                      	<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro" title="Indietro" /></td>
					</tr>
				</table>
			<br/>
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>