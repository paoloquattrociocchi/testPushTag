<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/collegio/associaAdAtto.htm" />
		<p class="titolo_page">
			<spring:message code="magistrato.titolo.associati.atto" />
		</p>
		<form:form method="post" action="${azione}"	commandName="magistratoForm">

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />					
			</div>
			<br/>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.lista.associati" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${azione}" >
					<display:column property="generalita" title="Cognome Nome" sortable="false" />
					<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="false" />
					<display:column property="ruolo.descrizione" title="Ruolo" sortable="true" />
					<display:column property="descrizioneCortaRelatore" title="Relatore" sortable="true" />
			</display:table>

			<br>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.titolo.ricerca" /></td>
				</tr>
				<tr>
					<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
					<td><form:input path="cognome"  maxlength="30"/>
						<form:errors path="cognome" class="testoRosso" /></td>
					<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
					<td><form:input path="nome"  maxlength="30"/>
						<form:errors path="nome" class="testoRosso" /></td>
				</tr>
			</table>

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Ricerca" name="Ricerca">
					</td>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/colleggio/elencoCollegioAssociatoAtto.htm')"></td>
				</tr>
			</table>
			<br />
			<!-- Modifica 01/2014 -->
			<logic:present name="risultati">
				<display:table class="tb_lista" uid="risultati" name="${risultati}" pagesize="10" requestURI="${azione}" >
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="id" value="${risultati.id}" />
					</display:column>
					<display:column property="cognome" title="Cognome" sortable="false" />
					<display:column property="nome" title="Nome" sortable="false" />
				</display:table>
			</logic:present>
			<!-- Modifica 01/2014 -->
			<logic:notEmpty name="risultati">
				<table class="tb_form" align="center">
					<tr>
					<td><form:label path="ruolo.id">
							<spring:message code="magistrato.ruolo" />
						</form:label></td>
					<td><form:select path="ruolo.id" size="1">
							<form:option value="-1" label="------ Seleziona Ruolo Magistrato--------" />
							<form:options items="${tipoRuoliMagistrati}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					</tr>
					<tr>
						<td><form:label path="relatore"><spring:message code="magistrato.relatore" /></form:label></td>
						<td colspan="3"><table>
								<tr>
									<td style="border-bottom: none;"><form:radiobutton path="relatore" value="S" /></td>
									<td style="border-bottom: none;">Si</td>
								</tr>
								<tr>
									<td style="border-bottom: none;"><form:radiobutton path="relatore" value="N" /></td>
									<td style="border-bottom: none;">No</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa">
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
