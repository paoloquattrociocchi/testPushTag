 <%@page import="it.giustizia.smp.form.ProfiloForm"%>
 <% 
   ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
 %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"  %>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/flussi/visualizzaFlussoDaElaborare.htm" />
		<p class="titolo_page">
			<spring:message code="flussi" />
		</p>
		<form:form method="post" action="${azione}" commandName="flussiForm">
			<br/>
			
			<table class="tb_form">	
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="flussi.elenco" />
						<form:hidden id="id_hiddenIdUfficioMittente"  path="idUffMittente" />
						<form:hidden id="id_hiddenDescUfficioMittente"  path="descSedeUffMittenteUnita" />
					</td>
				</tr>
			</table>

			
		     <table class="tb_lista">
                <tr>
                    <th><spring:message code="seleziona" /></th>
                    <th><spring:message code="flussi.ufficiomittente"/></th>
                    <th>Procedimento</th>                    
                    <th><spring:message code="flussi.dataflusso"/></th>
                    <th><spring:message code="flussi.tipomessaggio"/></th>
                    <th>Soggetto</th>   
                    <c:if test="${sessionScope.profilo.idTipoUfficio == 8}">	
                       <th>Procedimento procura</th>   
                    </c:if>                                                         
                </tr>
                <tbody>
			    <c:if test="${risultatiRicercaHolder.nrOfElements == 0}">
                  <tr><td class="empty" colspan="0">Nessun risultato trovato.</td></tr>
                </c:if>
   	            <c:if test="${risultatiRicercaHolder.nrOfElements > 0}">	
                
                  <c:forEach var="risultati" items="${risultatiRicercaHolder.pageList}">

   			        <c:set var="indexLista" value="${indexLista + 1}" />
			        <c:if test="${((indexLista % 2) == 0)}">
				        <c:set var="oddEven" value="even" />
			        </c:if>
			        <c:if test="${((indexLista % 2) == 1)}">
				        <c:set var="oddEven" value="odd" />
			        </c:if>
               
                   <tr class="${oddEven}">
                     <td style="text-align: center;">
                       <form:radiobutton id="listaIdRadio" path="idFlusso" value="${risultati.idFlusso}" 
						onclick="impostaHidden(${risultati.idUffMittente},'${fn:escapeXml(risultati.descSedeUffMittenteUnita)}')"/>
                     </td>
                     <td>${risultati.descSedeUffMittenteUnita}</td>
                    
                     <smp:flussoAggiunte flussoId="si" formFlusso="${risultati}"></smp:flussoAggiunte>
                    
                     <td><fmt:formatDate dateStyle="full" pattern="dd/MM/yyyy" value="${risultati.dataFlusso}" /></td>
                     <td>${risultati.descTipoMessaggio}</td>
                    
                     <smp:flussoAggiunte soggetto="si" formFlusso="${risultati}"></smp:flussoAggiunte>
                     
                     <c:if test="${sessionScope.profilo.idTipoUfficio == 8}">	
                        <smp:flussoAggiunte proposta="si" formFlusso="${risultati}"></smp:flussoAggiunte>
                     </c:if>
                       
                  </tr>
                </c:forEach>
                
                </tbody>
	       </table>	
	       
           <c:if test="${risultatiRicercaHolder.nrOfElements == 1}">
              <span class="pagebanner">1 risultato trovato.</span>
	       </c:if>       
           <c:if test="${risultatiRicercaHolder.nrOfElements <= 10}">
              <span class="pagebanner">${risultatiRicercaHolder.nrOfElements} risultati trovati, visualizzati tutti i risultati.</span>
	       </c:if>
	       
           <c:if test="${risultatiRicercaHolder.nrOfElements > 10}">
                        
              <c:set var="elemsDa" value="${(risultatiRicercaHolder.page)*10+1}" />
              <c:set var="elemsA" value="${(risultatiRicercaHolder.page)*10+10}" />              
              <c:if test="${risultatiRicercaHolder.lastPage}">   
                  <c:set var="elemsA" value="${risultatiRicercaHolder.nrOfElements}" />
              </c:if>      
              <span class="pagebanner">${risultatiRicercaHolder.nrOfElements} risultati trovati, visualizzati da ${elemsDa} a ${elemsA}.</span> 
       
              <c:set var="offset" value="${7}" />
              
 <c:if test="${risultatiRicercaHolder.pageCount <= offset}">
  	              <c:if test="${risultatiRicercaHolder.firstPage}">          
  	          	    <span class="pagelinks">[Prima/Precedente]
                  </c:if>
                  <c:if test="${!risultatiRicercaHolder.firstPage}">
                     <span class="pagelinks">[<a href="flussiDaElaborare.htm?page=first">Prima</a>/<a href="flussiDaElaborare.htm?page=previous&n=${risultatiRicercaHolder.page+1}">Precedente</a>] 
                  </c:if>
                  <c:forEach begin="${0}" end="${risultatiRicercaHolder.pageCount-1}" var="i"> 
                      <c:choose>
                      <c:when test="${risultatiRicercaHolder.page == i}"> 
                           <input type="hidden" name="id_hiddenPagina" value="${i+1}" />                                                                       
                           <c:if test="${i != risultatiRicercaHolder.pageCount-1}">
                              <strong>${i+1},&nbsp;</strong>		
                           </c:if>    
                           <c:if test="${i == risultatiRicercaHolder.pageCount-1}">
                              <strong>${i+1}&nbsp;</strong>		 
                           </c:if>
                      </c:when>
                      <c:otherwise>    
                          <c:if test="${i != risultatiRicercaHolder.pageCount-1}">                       
                            <a href="flussiDaElaborare.htm?page=${i+1}" title="Vai a pagina ${i+1}">${i+1},&nbsp;</a> 
                          </c:if>
                         <c:if test="${i == risultatiRicercaHolder.pageCount-1}">                       
                            <a href="flussiDaElaborare.htm?page=${i+1}" title="Vai a pagina ${i+1}">${i+1}&nbsp;</a> 
                          </c:if>
                      </c:otherwise> 
                      </c:choose>
                   </c:forEach>
  	              <c:if test="${risultatiRicercaHolder.lastPage}">
   	                 [Successiva/Ultima]</span>  
  	              </c:if>        
   	              <c:if test="${!risultatiRicercaHolder.lastPage}">  
                     [<a href="flussiDaElaborare.htm?page=next&n=${risultatiRicercaHolder.page+1}">Successiva</a>/<a href="flussiDaElaborare.htm?page=last">Ultima</a>]</span>                       
                  </c:if>                   
             
</c:if>
<c:if test="${risultatiRicercaHolder.pageCount > offset}">
          

 	          <c:if test="${risultatiRicercaHolder.firstPage}">          
  	          	          
                 <span class="pagelinks">[Prima/Precedente] 
                   <strong>1,&nbsp;</strong> 
<input type="hidden" name="id_hiddenPagina" value="1" />	                   
                   <c:forEach begin="${2}" end="${offset+1}" var="i"> 
                       <a href="flussiDaElaborare.htm?page=${i}" title="Vai a pagina ${i}">${i},&nbsp;</a> 
                    </c:forEach>               
                   [<a href="flussiDaElaborare.htm?page=next&n=${risultatiRicercaHolder.page+1}">Successiva</a>/<a href="flussiDaElaborare.htm?page=last">Ultima</a>]
                 </span>				                 
              </c:if>
              
              <c:if test="${!risultatiRicercaHolder.firstPage && !risultatiRicercaHolder.lastPage}">
                 <span class="pagelinks">[<a href="flussiDaElaborare.htm?page=first">Prima</a>/<a href="flussiDaElaborare.htm?page=previous&n=${risultatiRicercaHolder.page+1}">Precedente</a>] 
                    <c:set var="currentPage" value="${risultatiRicercaHolder.page + 1}" />
                    <c:set var="startingPoint" value="${currentPage - 4}" /> 
                    <c:if test="${currentPage - 4 <= 0}">
                       <c:set var="startingPoint" value="${1}" />
                    </c:if>                                        
                    <c:if test="${currentPage - 4 > 0}">
                       <c:set var="startingPoint" value="${currentPage - 4}" />                    
                    </c:if>                  
                    <c:set var="endingPoint" value="${startingPoint + offset}" />
                    <c:if test="${(startingPoint + offset) > risultatiRicercaHolder.pageCount}">
                       <c:set var="endingPoint" value="${risultatiRicercaHolder.pageCount}" />
                    </c:if>
                    <c:set var="count" value="${0}" />
                    <c:forEach begin="${startingPoint}" end="${endingPoint}" var="i"> 
                       <c:set var="count" value="${count + 1}" />
                    </c:forEach>
                    <c:if test="${count == 7}">
                       <c:set var="startingPoint" value="${startingPoint - 1}" />
                    </c:if>
                    <c:forEach begin="${startingPoint}" end="${endingPoint}" var="i"> 
                      <c:choose>
                      <c:when test="${currentPage == i}"> 
                         <strong>${i},&nbsp;</strong>
<input type="hidden" name="id_hiddenPagina" value="${i}" />					                        
                      </c:when>
                      <c:otherwise>                      
                         <a href="flussiDaElaborare.htm?page=${i}" title="Vai a pagina ${i}">${i},&nbsp;</a> 
                      </c:otherwise> 
                      </c:choose>
                    </c:forEach>                                                 
                 [<a href="flussiDaElaborare.htm?page=next&n=${risultatiRicercaHolder.page+1}">Successiva</a>/<a href="flussiDaElaborare.htm?page=last">Ultima</a>]</span> 
              </c:if>

              <c:if test="${risultatiRicercaHolder.lastPage}">
                 <span class="pagelinks">[<a href="flussiDaElaborare.htm?page=first">Prima</a>/<a href="flussiDaElaborare.htm?page=previous&n=${risultatiRicercaHolder.page+1}">Precedente</a>] 
                    <c:forEach begin="${risultatiRicercaHolder.pageCount - offset}" end="${risultatiRicercaHolder.pageCount}" var="i"> 
                      <c:set var="currentPage" value="${risultatiRicercaHolder.page + 1}" />
                      <c:choose>
                      <c:when test="${currentPage == i}"> 
                         <strong>${i}</strong>
<input type="hidden" name="id_hiddenPagina" value="${i}" />                         
                      </c:when>
                      <c:otherwise>                      
                         <a href="flussiDaElaborare.htm?page=${i}" title="Vai a pagina ${i}">${i},&nbsp;</a> 
                      </c:otherwise> 
                      </c:choose>
                    </c:forEach>                                
                    [Successiva/Ultima]
                 </span>
              </c:if>           

</c:if>

			</c:if>
			
	      </c:if>

<c:if test="${risultatiRicercaHolder.nrOfElements > 0}">	
		
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Visualizza" name="Visualizza"></td>
				</tr>
			</table>
			
</c:if>


		</form:form>
		
		<script type="text/javascript">
			function impostaHidden(idUffMittente,descUffMittente){
				document.getElementById("id_hiddenIdUfficioMittente").value = idUffMittente;
				document.getElementById("id_hiddenDescUfficioMittente").value = descUffMittente;
			}
		</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>