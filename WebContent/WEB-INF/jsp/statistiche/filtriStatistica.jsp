<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/statistiche/filtriStatistica.htm" />
		<p class="titolo_page">
			<spring:message code="${formTitle.code}" text="" argumentSeparator="${formTitle.argumentSeparator}" arguments="${formTitle.arguments}" />
		</p>
		<form:form method="post" action="${azione}" commandName="statisticheForm">
			<form:hidden id="hiddenIdStampa"  path="idStampa" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="statistiche.filtri.sottotitolo"/></td>
				</tr>
				<logic:present name="hasDataDal">
					<tr>
						<td width="18%"><spring:message code="statistiche.filtro.dataDal" /></td>
						<td width="32%">
							<form:input path="filtroDataDal" maxlength="10" size="12" id="filtroDataDal"/>
						    <form:errors path="filtroDataDal" class="testoRosso"/>
						</td>
					</tr> 
				</logic:present>
				<logic:present name="hasDataAl">
					<tr>
						<td width="18%"><spring:message code="statistiche.filtro.dataAl" /></td>
						<td width="32%">
							<form:input path="filtroDataAl" maxlength="10" size="12" id="filtroDataAl"/>
						    <form:errors path="filtroDataAl" class="testoRosso"/>
						</td> 
					</tr>
				</logic:present>
				<logic:present name="hasMagistrato">
					<tr>
						<td><spring:message code="statistiche.filtro.magistrato" /></td>
						<td colspan="3">
							<form:select  path="magistrato.id" size="1" >
								<form:option value="-1" label="- Seleziona Magistrato -"/>
								<form:options items="${magistrati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="magistrato.id" class="testoRosso" />
						</td>	
					</tr>
				</logic:present>
					<logic:present name="hasAnno">
						<tr>
							<td width="18%"><spring:message code="statistiche.filtro.anno" /></td>
							<td width="82%">
								<form:select  path="anno.id" size="1" >
									<form:option value="-1" label="- Seleziona Anno -"/>
									<form:options items="${anni}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="anno.id" class="testoRosso" />
							</td>
						</tr>
					</logic:present>
					<logic:present name="hasSemestre">
						<tr>
							<td width="18%"><spring:message code="statistiche.filtro.semestre" /></td>
							<td width="82%">
								<form:select  path="semestre.id" size="1" >
									<form:option value="-1" label="- Seleziona Semestre -"/>
									<form:options items="${semestri}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="semestre.id" class="testoRosso" />
							</td>
						</tr>
					</logic:present>
					<logic:present name="hasProcedente">
						<tr>
							<td width="18%"><spring:message code="statistiche.filtro.precedente" /></td>
							<td width="82%">
							<form:select  path="procedente.id" size="1" >
									<form:option value="-1" label="- Seleziona Procedente -"/>
									<form:options items="${procedenti}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="procedente.id" class="testoRosso" />
							</td>
						</tr>
					</logic:present>
				</tr>
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Visualizza Report" name="VisualizzaReport"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#filtroDataDal",true);
				prendiData("#filtroDataAl",true);
		 	});
		</script>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>