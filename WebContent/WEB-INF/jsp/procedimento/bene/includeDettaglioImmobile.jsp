<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


					<tr>
						<td class="td_titolo" colspan="6"><form:label path=""><spring:message	code="bene.immobile.ubicazione" /></form:label></td>
					</tr>
					<tr>
						<td><form:label path="stato.descrizione" ><spring:message code="stato"/></form:label></td>
	        		    <td colspan="6"><c:out value="${beneForm.stato.descrizione}" /></td>
					</tr>	
					<tr>
					    <td><form:label path="comune.descrizione" ><spring:message code="comune"/></form:label></td>					
		        	    <td colspan="6">
		        	    	<c:out value="${beneForm.comune.descrizione}" /><c:out value="${beneForm.desComuneEstero}" />
		        	    	<form:hidden path="tipologia.id" value="${beneForm.tipologia.id}" />
		        	    </td>
					</tr>					
					<tr>
					    <td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
						<td><c:out value="${beneForm.indirizzo.toponimo.descrizione}" /> <c:out value="${beneForm.indirizzo.denominazione}" /></td>
						
						<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.indirizzo.civico}" /></td>
					</tr>
					<tr>
						<td><form:label path="scala"><spring:message code="bene.immobile.scala" /></form:label></td>
						<td><c:out value="${beneForm.scala}" /></td>
						
						<td><form:label path="interno"><spring:message code="bene.immobile.interno" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.interno}" /></td>
					</tr>						
					<tr>						
						<td><form:label path="piano"><spring:message code="bene.immobile.piano" /></form:label></td>
						<td><c:out value="${beneForm.piano}" /></td>

						<td><form:label path="edificio"><spring:message code="bene.immobile.edificio" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.edificio}" /></td>
					</tr>
					<tr>						
						<td><form:label path="lotto"><spring:message code="bene.immobile.lotto" /></form:label></td>
						<td><c:out value="${beneForm.lotto}" /></td>
						
						<td><form:label path="cap"><spring:message code="bene.immobile.cap" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.indirizzo.cap}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="6">
							<form:label path=""><spring:message code="bene.immobile.daticatastali" /></form:label>
						</td>
					</tr>
					<tr>
						<td><form:label path="descComuneCatastale"><spring:message code="bene.immobile.comunecatastale" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.comune.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="codiceCatastale1"><spring:message code="bene.immobile.codicecatastale" /></form:label></td>
						<td><c:out value="${beneForm.codiceCatastale1}" /><c:out value="${beneForm.codiceCatastale2}" /></td>
						
						<td><form:label path="tipoCatasto"><spring:message code="bene.immobile.tipocatasto" /></form:label></td>
						<td colspan="6">
							<c:if test="${beneForm.tipoCatasto==''}"><c:out value="" /></c:if>
							<c:if test="${beneForm.tipoCatasto=='U'}"><c:out value="NCEU" /></c:if>
							<c:if test="${beneForm.tipoCatasto=='T'}"><c:out value="NCT" /></c:if>
						</td>
					</tr>
					<tr>
						<td><form:label path="categoriaCatastale"><spring:message code="bene.immobile.categoriacatastale" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.categoriaCatastale.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataAccatastamento"><spring:message code="bene.immobile.dataaccatastamento" /></form:label></td>
						<td colspan="6"><fmt:formatDate value="${beneForm.dataAccatastamento}" type="date" pattern="dd/MM/yyyy" /></td>	
					</tr>
					<tr>
						<td><form:label path="fondo"><spring:message code="bene.immobile.fondo" /></form:label></td>
						<td colspan="6">
							<c:if test="${beneForm.fondo=='D'}"><c:out value="Dominante" /></c:if>
							<c:if test="${beneForm.fondo=='S'}"><c:out value="Servente" /></c:if>
						</td>
					</tr>
					<c:if test="${beneForm.switchDenunciaIdentifImmobile=='I'}">
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="bene.immobile.identificativoimmobile" /></td>
						</tr>
						<tr>
							<td><form:label path="sezione"><spring:message code="bene.immobile.sezione" /></form:label></td>
							<td><c:out value="${beneForm.sezione}" /></td>
							
							<td><form:label path="foglio"><spring:message code="bene.immobile.foglio" /></form:label></td>
							<td><c:out value="${beneForm.foglio}" /></td>
						</tr>
						<tr>
							<td><form:label path="particella1"><spring:message code="bene.immobile.particella" /></form:label></td>
							<td><c:out value="${beneForm.particella1}" />/ <c:out value="${beneForm.particella2}" /></td>
							
							<td><form:label path="sub1"><spring:message code="bene.immobile.sub" /></form:label></td>
							<td><c:out value="${beneForm.sub1}" />/ <c:out value="${beneForm.sub2}" /></td>
						</tr>
					</c:if>				
					<c:if test="${beneForm.switchDenunciaIdentifImmobile=='D'}">
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="bene.immobile.denunciaaccatastamento" /></td>
						</tr>
						<tr>
							<td><form:label path="tipoDenuncia"><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
							<td>
								<c:if test="${beneForm.tipoDenuncia=='P'}"><c:out value="Protocollo" /></c:if>
								<c:if test="${beneForm.tipoDenuncia=='S'}"><c:out value="Scheda" /></c:if>
								<c:if test="${beneForm.tipoDenuncia=='V'}"><c:out value="Variazione" /></c:if>
							</td>
							
							<td><form:label path="numeroDenuncia1"><spring:message code="procedimento.numero" /></form:label></td>
							<td><c:out value="${beneForm.numeroDenuncia1}" />/ <c:out value="${beneForm.numeroDenuncia2}" /></td>
						</tr>
						<tr>					
							<td><form:label path="annoDenuncia"><spring:message code="bene.immobile.del" /></form:label></td>
							<td colspan="6"><c:out value="${beneForm.annoDenuncia}" /></td>
						</tr>
					</c:if>
					<tr>
						<td class="td_titolo" colspan="6" />
					</tr>
					<tr>
						<td><form:label path="schedaAccatastamento"><spring:message code="bene.immobile.schedaaccatastamento" /></form:label></td>
						<td><c:out value="${beneForm.schedaAccatastamento}" /></td>
						
						<td><form:label path="zonaCensuaria"><spring:message code="bene.immobile.zonacensuaria" /></form:label></td>
						<td><c:out value="${beneForm.zonaCensuaria}" /></td>
					</tr>
					<tr>
						<td><form:label path="renditaCatastale"><spring:message code="bene.immobile.renditacatastale" /></form:label></td>
						<td><c:out value="${beneForm.renditaCatastale}" /></td>
						
						<td><form:label path="renditaDominicale"><spring:message code="bene.immobile.renditadominicale" /></form:label></td>
						<td><c:out value="${beneForm.renditaDominicale}" /></td>
					</tr>
					<tr>
						<td><form:label path="ettari"><spring:message code="bene.immobile.ettari" /></form:label></td>
						<td><c:out value="${beneForm.ettari}" /></td>
						
						<td><form:label path="are"><spring:message code="bene.immobile.are" /></form:label></td>
						<td><c:out value="${beneForm.are}" /></td>
					</tr>
					<tr>
						<td><form:label path="centiare"><spring:message code="bene.immobile.centiare" /></form:label></td>
						<td colspan="6"><c:out value="${beneForm.centiare}" /></td>
					</tr>
					<tr>
						<td><form:label path="vaniIntero"><spring:message code="bene.immobile.vani" /></form:label></td>
						<td><c:out value="${beneForm.vaniIntero}" default="0"/>,<fmt:formatNumber type="number" pattern="00" value="${beneForm.vaniDecimale}" var="vaniDecimale"/><c:out value="${vaniDecimale}" default="00"/></td>
						
						<td><form:label path="metriCubiIntero"><spring:message code="bene.immobile.metricubi" /></form:label></td>
						<td><c:out value="${beneForm.metriCubiIntero}" default="0"/>,<fmt:formatNumber type="number" pattern="00" value="${beneForm.metriCubiDecimale}" var="metriCubiDecimale"/><c:out value="${metriCubiDecimale}" default="00"/></td>
					</tr>
					<tr>
						<td><form:label path="metriQuadriIntero"><spring:message code="bene.immobile.metriquadri" /></form:label></td>						
						<td colspan="6"><c:out value="${beneForm.metriQuadriIntero}" default="0"/>,<fmt:formatNumber type="number" pattern="00" value="${beneForm.metriQuadriDecimale}" var="metriQuadriDecimale"/><c:out value="${metriQuadriDecimale}" default="00"/></td>
					</tr>
					<tr>
						<td><form:label path="appartenenza"><spring:message code="bene.immobile.appartenenza" /></form:label></td>
						<td colspan="3">
							<form:radiobutton disabled="true" path="appartenenza" value="S" label="SI"/>
					 		<form:radiobutton disabled="true" path="appartenenza" value="N" label="NO"/>
						</td>
					</tr>
					
					<tr>
						<td class="td_titolo" colspan="6">
							<form:label path=""><spring:message code="bene.immobile.datiCatastaliOrigine" /></form:label>
						</td>
					</tr>
					<tr>
						<td><form:label path="comuneCatastaleP.id" ><spring:message code="comune"/></form:label></td>
						<td colspan="6"><c:out value="${beneForm.comuneCatastaleP.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="codiceCatastale1P"><spring:message code="bene.immobile.codicecatastale" /></form:label></td>
						<td><c:out value="${beneForm.codiceCatastale1P}" /><c:out value="${beneForm.codiceCatastale2P}" /></td>
						
						<td><form:label path="tipoCatastoP"><spring:message code="bene.immobile.tipocatasto" /></form:label></td>
						<td colspan="6">
							<c:if test="${beneForm.tipoCatastoP==''}"><c:out value="" /></c:if>
							<c:if test="${beneForm.tipoCatastoP=='U'}"><c:out value="NCEU" /></c:if>
							<c:if test="${beneForm.tipoCatastoP=='T'}"><c:out value="NCT" /></c:if>
						</td>
					</tr>
					
					<c:if test="${beneForm.switchDenunciaIdentifImmobile=='I'}">
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="bene.immobile.identificativoimmobile" /></td>
						</tr>
						<tr>
							<td><form:label path="sezioneP"><spring:message code="bene.immobile.sezione" /></form:label></td>
							<td><c:out value="${beneForm.sezioneP}" /></td>
							
							<td><form:label path="foglioP"><spring:message code="bene.immobile.foglio" /></form:label></td>
							<td><c:out value="${beneForm.foglioP}" /></td>
						</tr>
						<tr>
							<td><form:label path="particella1P"><spring:message code="bene.immobile.particella" /></form:label></td>
							<td><c:out value="${beneForm.particella1P}" />/ <c:out value="${beneForm.particella2P}" /></td>
							
							<td><form:label path="sub1P"><spring:message code="bene.immobile.sub" /></form:label></td>
							<td><c:out value="${beneForm.sub1P}" />/ <c:out value="${beneForm.sub2P}" /></td>
						</tr>
					</c:if>
						
					<c:if test="${beneForm.switchDenunciaIdentifImmobileP=='D'}">				
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="bene.immobile.denunciaaccatastamento" /></td>
						</tr>
						<tr>
							<td><form:label path="tipoDenunciaP"><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
							<td>
								<c:if test="${beneForm.tipoDenuncia=='P'}"><c:out value="Protocollo" /></c:if>
								<c:if test="${beneForm.tipoDenuncia=='S'}"><c:out value="Scheda" /></c:if>
								<c:if test="${beneForm.tipoDenuncia=='V'}"><c:out value="Variazione" /></c:if>
							</td>
							
							<td><form:label path="numeroDenuncia1P"><spring:message code="procedimento.numero" /></form:label></td>
							<td><c:out value="${beneForm.numeroDenuncia1P}" />/ <c:out value="${beneForm.numeroDenuncia2P}" /></td>
						</tr>
						<tr>					
							<td><form:label path="annoDenunciaP"><spring:message code="bene.immobile.del" /></form:label></td>
							<td colspan="6"><c:out value="${beneForm.annoDenunciaP}" /></td>
						</tr>
					</c:if>
					
					<!-- Segnalazione EMS-166 -->
					<logic:present name="beneGraffato">
						<tr>
							<td class="td_titolo" colspan="6">
								<form:label path=""><spring:message code="bene.immobile.titolo.graffato" /></form:label>
							</td>
						</tr>
						<tr>
							<td><form:label path="idBene"><spring:message code="bene.identificativobene" /></form:label></td>
							<td><c:out value="${beneGraffato.idBene}" /></td>
							<td><form:label path="descrizioneBene"><spring:message code="bene.mobile.altro.descrizione" /></form:label></td>
							<td><c:out value="${beneGraffato.descrizioneBene}" /></td>
						</tr>   
					</logic:present>
					
					<tr>
						<td class="td_titolo" colspan="6" />
					</tr>
					
					<tr>
						<td><form:label path="vincolo"><spring:message code="bene.immobile.vincolo" /></form:label></td>
						<td><c:out value="${beneForm.vincolo.descrizione}" /></td>
						
						<td><form:label path="descrizioneVincolo"><spring:message code="bene.immobile.descrizionevincolo" /></form:label></td>
						<td><c:out value="${beneForm.descrizioneVincolo}" /></td>
					</tr>
					<tr>
						<td><form:label path="statoConservazione.id"><spring:message code="bene.statoconservazione" /></form:label></td>
						<c:if test="${beneForm.dettAssBene=='Dettaglio'}">
							<td><c:out value="${beneForm.statoConservazione.descrizione}" /></td>
						</c:if>
						
						<c:if test="${beneForm.dettAssBene=='Associa'}">
							<td><form:select path="statoConservazione.id" size="1">
									<form:option value="0" label="-------Seleziona Stato Conservazione-------" />
									<form:options items="${statiConservazione}" itemValue="id" itemLabel="descrizione"/>
								</form:select>
							</td>
						</c:if>
						
						<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
						<td>
						 <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
						 <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
					   </td>
					</tr>
					<tr>  
						<td><form:label path="comboDisponibilita"><spring:message code="bene.immobile.disponibilita" /></form:label></td>
						<td><c:out value="${beneForm.comboDisponibilita.descrizione}" /></td>
						
						<td><form:label path="tipoOccupanti"><spring:message code="bene.immobile.tipooccupanti" /></form:label></td>
						<td>
							<%-- 
							<c:if test="${beneForm.tipoOccupanti=='P'}"><c:out value="Prevenuto/Proposto" /></c:if>
							<c:if test="${beneForm.tipoOccupanti=='T'}"><c:out value="Terzi" /></c:if>
							<c:if test="${beneForm.tipoOccupanti=='F'}"><c:out value="Familiari preventuo/proposto" /></c:if>
							<c:if test="${beneForm.tipoOccupanti=='C'}"><c:out value="Comunità" /></c:if>
							<c:if test="${beneForm.tipoOccupanti=='A'}"><c:out value="Associazione" /></c:if>
							--%>
							<c:out value="${beneForm.tipoOccupanti.descrizione}"></c:out>
						</td>
					<tr>
						<td><form:label path="note"><spring:message code="note" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.note}" /></td>
					</tr>