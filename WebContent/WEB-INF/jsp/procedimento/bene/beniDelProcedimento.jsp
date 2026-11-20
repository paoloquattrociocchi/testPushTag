<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@page import="it.giustizia.smp.form.AttoForm"%>
<%@page import="org.displaytag.decorator.CheckboxTableDecorator"%>


<%!
String tipoAtto;
String categoriaAtto;
AttoForm atto ;
%>

<% if (request.getSession().getAttribute(Constants.ATTO) != null) {
atto=(AttoForm) request.getSession().getAttribute(Constants.ATTO);
if (atto.getTipoAtto()!= null && atto.getTipoAtto().getId()!= null)  tipoAtto=atto.getTipoAtto().getId();
else tipoAtto=null;
if (atto.getCategoriaAtto()!= null && atto.getCategoriaAtto().getId()!= null) categoriaAtto=atto.getCategoriaAtto().getId();
else categoriaAtto=null;
} else {
	atto=null;
	tipoAtto=null;
	categoriaAtto=null;
}
%>

<%
CheckboxTableDecorator decorator = new CheckboxTableDecorator();
decorator.setId("idBene");
decorator.setFieldName("_chk");//_chk - listaIdCheckbox
//anomalia sul componente displaytag: non è possibile settare il field name a piacere
pageContext.setAttribute("checkboxDecorator", decorator);
%>

<c:set var="TIPO_UFFICIO_CORTE_D_APPELLO" value="<%=Constants.TIPO_UFFICIO_CORTE_D_APPELLO%>"/>
<c:set var="TIPO_UFFICIO_PROCURA_M" value="<%=Constants.TIPO_UFFICIO_PROCURA_M%>"/>
<c:set var="categoriaAtto"  scope="page"  value="<%=categoriaAtto%>" />

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type="text/javascript">
			var arrayIdBene = new Array();
			var i=-1;

			// anomalia sul componente displaytag: se gli indici della paginazione si mettono in basso (paging.banner.placement=bottom)
			// non viene aggiuta la funzione js displaytagform e alcuni campi hidden 
			function displaytagform(formname, fields) {
				//console.log("displaytagform - formname: "+formname);
				//console.log("displaytagform - fields.length: "+fields.length);
				var objfrm = document.forms[formname];
				for (j = fields.length - 1; j >= 0; j--) {
					//console.log("fields[j].f: "+fields[j].f);
					//console.log("fields[j].v: "+fields[j].v);
					var f = objfrm.elements[fields[j].f];
					if (f) {
						//console.log("find: "+fields[j].f);
						f.value = fields[j].v;
					} else {
						//console.log("not find: "+fields[j].f);
						var input = document.createElement("input");
						input.setAttribute("type", "hidden");
						input.setAttribute("name", fields[j].f);
						input.setAttribute("value", fields[j].v);
						//append to form element that you want .
						objfrm.appendChild(input);
					}
				}

				//append cambiopagina form element
				var cambio = document.createElement("input");
				cambio.setAttribute("type", "hidden");
				cambio.setAttribute("name", "cambiopagina");
				cambio.setAttribute("value", "on");
				objfrm.appendChild(cambio);
				
				objfrm.method ="get";
				objfrm.submit();
			}
		</script>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		
		<spring:url var="azione" value="/procedimento/bene/gestioneBeniDelProcedimento.htm" />
		<p class="titolo_page">
			<spring:message code="bene.benidelprocedimento" />
		</p>
		
		<form:form method="post" action="${azione}" commandName="beneForm" name="beneForm">
	     
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
			
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4">
					<form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
					<spring:message code="bene.filtrosuibeni" /></td>
				</tr>
				<logic:equal name="beneForm" property="visualizzaFiltro" value="true">
				<tr>
					<td><form:label path="idBene"><spring:message code="bene.identificativobene" /></form:label></td>
					<td colspan="3">
						<div class="ui-widget">
							<form:input id="inputIdBene" path="idBene" maxlength="15" size="17"/>
							<div id="id_loading_inputIdBene"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td><form:label path="tipologia.id"><spring:message code="bene.tipologia" /></form:label></td>
					<td  colspan="3"><form:select path="tipologia.id" size="1" onchange="this.form.submit()">
							<form:option value="-1" label="------Seleziona Tipologia-------" />
							<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					<form:hidden path="tipologia.descrizione"/>
				</tr>
				<tr>
					<td><form:label path="categoria.id">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td  colspan="3"><form:select path="categoria.id" size="1" onchange="submit()">
							<form:option value="-1" label="------Seleziona Categoria-------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:hidden path="categoria.descrizione"/>
					</td>
				</tr>
				<logic:notEqual name="beneForm" property="tipologia.id" value="5">
					<tr>
						<td><form:label path="sottocategoria.id"><spring:message code="bene.sottocategoria" /></form:label></td>
						<td  colspan="3">
							<form:select path="sottocategoria.id" size="1" onchange="submit()">
								<form:option value="-1" label="------Seleziona Sottocategoria-------" />
								<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
						<form:hidden path="sottocategoria.descrizione"/>
					</tr>
				</logic:notEqual>
			</logic:equal>
			</table>
			
			<table class="tb_form">
			
				<logic:notEmpty name="beneForm" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimentofiltrati" />
					</td>
				</tr>
				</logic:notEmpty>
				<logic:empty name="beneForm" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
				</tr>
				</logic:empty>
			</table>
			
			<logic:present name="risultatiRicerca">	
				<c:forEach items="${risultatiRicerca}" var="bene">  
					<script type="text/javascript">	
						arrayIdBene[++i] = <c:out value="${bene.idBene}"/>;
					</script>
				</c:forEach>
				
				<display:table class="tb_lista" uid="risultatiRicerca" name="${risultatiRicerca}" pagesize="10" decorator="checkboxDecorator" form="beneForm" excludedParams="_chk">
					<display:column property="checkbox" title="Seleziona" />
					<display:column property="idBene" title="Identificativo" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
					<c:if test="${procedimento.tipoProcedimento == null || (procedimento.tipoProcedimento.id != 9 && procedimento.tipoProcedimento.id != 10 && procedimento.tipoProcedimento.id != 17)}">						
						<display:column property="titoliDiDiritto" title="Titoli di Diritto" sortable="false" />
					</c:if>
					<c:if test="${procedimento.tipoFascicolo == null || (procedimento.tipoFascicolo.id == 8)}">						
						<display:column property="isBeneInIstanzaCamerale" title="Presenza istanze camerali" sortable="false" />
					</c:if>					
				</display:table>

				<c:if test="${procedimento.tipoFascicolo != null && procedimento.tipoFascicolo.id == 8}">						
	             <logic:notEmpty name="listaNuoviBeni">	             
	              <br />
	              <br />	
   			      <table class="tb_form">
	               <tr>
					<td class="td_titolo" colspan="4">
                       <spring:message code="bene.nuoviBeniRelazioneAmministratore" />	
                    </td>
                    </tr> 
                  </table>                 
				  <display:table class="tb_lista" uid="listaNuoviBeni" name="${listaNuoviBeni}" pagesize="10" requestURI="${azione}" >
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">	
					    <c:set var="chiave" value="" />
					    <c:if test="${listaNuoviBeni.idDeposito != null}">
					      <c:set var="chiave" value="${listaNuoviBeni.idDeposito}" />
					    </c:if> 					    
					    <c:if test="${listaNuoviBeni.tipologia.descrizione != null}">
					      <c:set var="chiave" value="${chiave}@${listaNuoviBeni.tipologia.descrizione}" />
					    </c:if> 
					    <c:if test="${listaNuoviBeni.categoria.descrizione != null}">
					      <c:set var="chiave" value="${chiave}@${listaNuoviBeni.categoria.descrizione}" />
					    </c:if> 
					    <c:if test="${listaNuoviBeni.sottocategoria.descrizione != null}">
					      <c:set var="chiave" value="${chiave}@${listaNuoviBeni.sottocategoria.descrizione}" />
					    </c:if> 	
                        <c:set var="chiave" value="${fn:replace(chiave, ',', '§')}" />
					    
					    <input type="checkbox" 	id="listaIdCheckboxString" name="listaIdCheckboxString" value="${chiave}" />							    	    			
					</display:column>					
					<display:column property="idBene" title="Identificativo" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
				  </display:table>
				 </logic:notEmpty>
                </c:if>									
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<c:choose>
							<c:when test="${procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id == 18 }">
								<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
							</c:when>
							<c:otherwise>
								<c:if test="${procedimento.statoProcedimento.id != 11 }">
								<logic:notEmpty name="risultatiRicerca">
									<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
									<!-- c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO || procedimento.tipoFascicolo == null || procedimento.tipoFascicolo.id !=6  }"-->
									<c:if test="${ procedimento.tipoFascicolo == null || procedimento.tipoFascicolo.id !=6  }">
										 <c:choose>
											<c:when test="${procedimento.tipoProcedimento.id == 17 || procedimento.statoProcedimento.id == 24 || (profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO && procedimento.statoProcedimento.id == 15)
													     || procedimento.statoProcedimento.id == 1 || procedimento.statoProcedimento.id == 3 || procedimento.statoProcedimento.id == 4 || procedimento.statoProcedimento.id == 8
													     || procedimento.statoProcedimento.id == 9 || procedimento.statoProcedimento.id == 10 || procedimento.statoProcedimento.id == 12 || procedimento.statoProcedimento.id == 13
													     || procedimento.statoProcedimento.id == 14 || procedimento.statoProcedimento.id == 15 || procedimento.statoProcedimento.id == 16 || procedimento.statoProcedimento.id == 18
													     || procedimento.statoProcedimento.id == 19 || procedimento.statoProcedimento.id == 20 || procedimento.statoProcedimento.id == 22 || procedimento.statoProcedimento.id == 23
													     || procedimento.statoProcedimento.id == 25 || procedimento.statoProcedimento.id == 26 || procedimento.statoProcedimento.id == 27
													     || procedimento.statoProcedimento.id == 28 || procedimento.statoProcedimento.id == 29 || procedimento.statoProcedimento.id == 30 || procedimento.statoProcedimento.id == 31
													     || procedimento.statoProcedimento.id == 32 || procedimento.statoProcedimento.id == 33 || procedimento.statoProcedimento.id == 36
													     || procedimento.statoProcedimento.id == 37 || procedimento.statoProcedimento.id == 39 || procedimento.statoProcedimento.id == 40
													     || procedimento.statoProcedimento.id == 41 || procedimento.statoProcedimento.id == 42 || procedimento.statoProcedimento.id == 43 || procedimento.statoProcedimento.id == 44
													     || (profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO && procedimento.statoProcedimento.id == 46)
													     || procedimento.statoProcedimento.id == 45 || procedimento.statoProcedimento.id == 47}">
											
												<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled" ></td>
											</c:when>
											<c:otherwise> 
												<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" ></td>
											</c:otherwise>
										</c:choose> 
									</c:if>
									
									<!-- c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO }"  -->
									<!-- Segnalazione SMP-04 -->
										<c:choose>
											<c:when test="${profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO}">
												<td class="td_tasto"><input type="submit" value="Copia Bene" name="CopiaBene" disabled="disabled"></td>							
												<td class="td_tasto"><input type="submit" value="Titoli Di Diritto/Interesse" name="TitoliDiDiritto" disabled="disabled"></td>
												<td class="td_tasto"><input type="submit" value="Copia Titoli Di Diritto/Interesse" name="CopiaTitoliDiDiritto" disabled="disabled"></td>
											</c:when>
											<c:otherwise>
												<td class="td_tasto"><input type="submit" value="Copia Bene" name="CopiaBene"></td>	
												<c:choose>
													<c:when test="${procedimento.tipoProcedimento.id == 12}">						
														<td class="td_tasto"><input type="submit" value="Titoli Di Diritto/Interesse" name="TitoliDiDiritto" disabled="disabled"></td>
													</c:when>
													<c:otherwise>
														<td class="td_tasto"><input type="submit" value="Titoli Di Diritto/Interesse" name="TitoliDiDiritto"></td>
													</c:otherwise>
												</c:choose>
												<td class="td_tasto"><input type="submit" value="Copia Titoli Di Diritto/Interesse" name="CopiaTitoliDiDiritto"></td>
											</c:otherwise>
										</c:choose>
										<!-- 
										<td class="td_tasto"><input type="submit" value="Copia Bene" name="CopiaBene"></td>							
										<td class="td_tasto"><input type="submit" value="Titoli Di Diritto/Interesse" name="TitoliDiDiritto"></td>
										<td class="td_tasto"><input type="submit" value="Copia Titoli Di Diritto/Interesse" name="CopiaTitoliDiDiritto"></td>
										-->
									    <c:if test="${procedimento.tipoProcedimento.id == 1000}">
									    <!-- 1000  basta togliere la if che ovviamente non è verifiabile se bisogna rimetterlo dai beni del procedimento -->							
									    
										    <c:if test="${procedimento.tipoProcedimento == null ||(procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id != 13) }">							
												<c:choose>
													<c:when test="${procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 13
													                || procedimento.tipoProcedimento.id == 14 || procedimento.tipoProcedimento.id == 15
													                || procedimento.tipoProcedimento.id == 16  || procedimento.tipoProcedimento.id == 18
													                || procedimento.tipoProcedimento.id == 19 || profilo.idTipoUfficio != TIPOPROC_CORREZIONE_ERRORI_CAM   }">
														<td class="td_tasto"><input type="submit" value="Trasforma Bene" name="TrasformaBene" disabled="disabled"></td>
													</c:when>
													<c:otherwise>
														<td class="td_tasto"><input type="submit" value="Trasforma Bene" name="TrasformaBene" disabled="disabled"></td>
													</c:otherwise>
												</c:choose>
										    </c:if>
									    </c:if>
									    	
									<!--  /c:if  -->
									<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO || procedimento.tipoFascicolo == null || procedimento.tipoFascicolo.id !=6 }">
										<c:choose>
											<c:when test="${profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO  && procedimento.statoProcedimento.id == 15 }">
												<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaBene" disabled="disabled"></td>
											</c:when>
											<c:otherwise>
												<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaBene"></td>
											</c:otherwise>
										</c:choose>
									</c:if>
								</logic:notEmpty>
								</c:if>	
								
								<c:if test="${procedimento.statoProcedimento.id == 11 }">
								<logic:notEmpty name="risultatiRicerca">
									<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
									<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
									<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO }">
									
										<td class="td_tasto"><input type="submit" value="Copia Bene" name="CopiaBene" disabled="disabled"></td>							
										<td class="td_tasto"><input type="submit" value="Titoli Di Diritto/Interesse" name="TitoliDiDiritto" disabled="disabled"></td>
										<td class="td_tasto"><input type="submit" value="Copia Titoli Di Diritto/Interesse" name="CopiaTitoliDiDiritto" disabled="disabled"></td>						
										<c:choose>
											<c:when test="${procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 13
											                || procedimento.tipoProcedimento.id == 14 || procedimento.tipoProcedimento.id == 15
											                || procedimento.tipoProcedimento.id == 16  || procedimento.tipoProcedimento.id == 18
											                || procedimento.tipoProcedimento.id == 19 || profilo.idTipoUfficio != TIPOPROC_CORREZIONE_ERRORI_CAM  }">
												<td class="td_tasto"><input type="submit" value="Trasforma Bene" name="TrasformaBene" disabled="disabled"></td>
											</c:when>
											<c:otherwise>
								            	<td class="td_tasto"><input type="submit" value="Trasforma Bene" name="TrasformaBene" disabled="disabled"> ></td>
								             </c:otherwise>
										</c:choose>
										
									</c:if> 
									<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaBene" disabled="disabled"></td>
								</logic:notEmpty>
								</c:if>	
							</c:otherwise>
						</c:choose>						
					</tr>
				</table>
			</logic:present>
		</form:form>
		
		
		
		<logic:equal name="beneForm" property="visualizzaFiltro" value="true">
		
			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteIdBene.js"></script>
		
			<script type="text/javascript">
					$(document).ready(function() {
						doAutocompleteIdBene("inputIdBene",null,null);
					});
			</script>
		</logic:equal>
		
		<script type="text/javascript">
				$(document).ready(function() {
					
					disabilitaSubmitBeniDelProcedimentoPro('${disabledViewProc}', '${profilo.idTipoUfficio}', '${sessionScope.procedimento.statoProcedimento.id}', '${sessionScope.procedimento.tipoProcedimento.id}');
				});	
		</script>
		
	   <c:if test="${categoriaAtto!=null}">
		<c:if test="${categoriaAtto=='314'}">
		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitErroreCorrezioneMateriale();
            	});	
		</script>
         </c:if>
		 </c:if>
		 
		 <c:if test="${profilo.ruolo=='MAGISTRATO' || sessionScope.TIPO_FASCICOLO == 8}">
		 <script type="text/javascript">
				$(document).ready(function() {
					
					disabilitaSubmitBeniDelProcedimento('true', '17', '${sessionScope.procedimento.tipoProcedimento.id}');
				});	
		</script>
		  </c:if>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
