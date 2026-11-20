<%@page import="it.giustizia.smp.utility.Constants.TipiTrasformazione"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/bene/trasformaBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.trasformazione" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="beneFromBDC" />
			<form:hidden path="idBene" />
			<form:hidden path="descrizioneBene" />
			<form:hidden path="versioneBene" />
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /> - ${beneForm.descrizioneBene }</td>
				</tr>

			</table>
			<table class="tb_form" border=0>

			<c:if test="${ beneForm.altraTrasformazione!='S'}">
				<tr>
					<td><form:label path="tipoTrasformazione.id" cssClass="required">
							<spring:message code="bene.tipiTrasformazione" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select id="tipoTrasformazione" path="tipoTrasformazione.id" size="1" onchange="submit()">
							<form:option value="-1" label="------Seleziona Trasformazione -------" />
							<form:options items="${tipiTrasformazione}" itemValue="id" itemLabel="descrizione"/>
						</form:select>
						<form:errors path="tipoTrasformazione.id" class="testoRosso"/>
					</td>
				</tr>
			 </c:if>
			 
			 <c:if test="${ beneForm.altraTrasformazione=='S'}">	
			   	<form:hidden path="tipoTrasformazione.id" />
			 </c:if>
			 
			 	
				
				
				<%-- se ho selezionato Altra Trasformazione --%>
				<c:if test="${ beneForm.tipoTrasformazione.id==13}">
					<tr>
						<td><form:label path="tipologia.id" cssClass="required"><spring:message code="bene.tipologia" /></form:label></td>
						<td colspan="3">
							<form:select path="tipologia.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="------Seleziona Tipologia-------" />
								<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="tipologia.id" class="testoRosso"/>
						</td>
					</tr>
				

					<c:if test="${beneForm.tipologia.id!=null and beneForm.tipologia.id!=-1}">
						<tr>
							<td><form:label path="categoria.id" cssClass="required"><spring:message code="bene.categoria" /></form:label></td>
							<td colspan="3">
								<form:select path="categoria.id" size="1" onchange="submit()">
									<form:option value="-1" label="------Seleziona Categoria-------" />
									<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
								</form:select>
								<form:errors path="categoria.id" class="testoRosso"/>
							</td>
						</tr>
						<logic:notEqual name="beneForm" property="tipologia.id" value="5">
							<tr>
								<td><form:label path="sottocategoria.id" cssClass="required">
										<spring:message code="bene.sottocategoria" />
									</form:label></td>
								<td colspan="3">
									<form:select path="sottocategoria.id" size="1">
										<form:option value="-1" label="------Seleziona Sottocategoria-------" />
										<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
									</form:select>
									<form:errors path="sottocategoria.id" class="testoRosso"/>
								</td>
							</tr>
						</logic:notEqual>
					</c:if>
					
					
				</c:if>
				
			</table>
			<p><br></p>
				
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="TrasformaBene" value="TrasformaBene" title="Trasforma Bene"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		
		</form:form>

		
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>
