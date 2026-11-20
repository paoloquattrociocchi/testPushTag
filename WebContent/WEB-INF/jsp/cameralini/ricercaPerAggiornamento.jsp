<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@page import="it.giustizia.smp.form.ComboForm"%>
<%@page import="java.util.List"%>


<tiles:insertDefinition name="base.definition">
    <tiles:putAttribute name="body-content">
        <spring:url var="azione" value="/procedimento/aggiorna/${ricerca.funzioneCameralini}/ricerca.htm" />
        <p class="titolo_page">
            <spring:message code="cameralini.prevenzione.titolo.ricercaSuIstanza" />
            ${ricerca.funzioneCameralini}
        </p>
        <form:form method="post" action="${azione}" commandName="ricerca">
            <form:hidden path="funzioneCameralini" />
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td class="td_titolo" colspan="5">
                        <spring:message code="procedimento.puntuale" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="procedCamerale.anno">
                            <spring:message code="cameralini.procedimento" />
                        </form:label>
                    </td>
                    <td>
                        <form:label path="procedCamerale.anno" cssClass="required">
                            <spring:message code="procedimento.anno" />
                        </form:label>
                        <form:input path="procedCamerale.anno" maxlength="4" size="5" />
                        <form:errors path="procedCamerale.anno" class="testoRosso" />
                    </td>
                    <td colspan="2">
                        <form:label path="procedCamerale.numero" cssClass="required">
                            <spring:message code="procedimento.numero" />
                        </form:label>
                        <form:input path="procedCamerale.numero" maxlength="12" />
                        <form:errors path="procedCamerale.numero" class="testoRosso" />
                    </td>
                </tr>
            </table>
            <br />
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td class="td_titolo" colspan="5">
                        <spring:message code="procedimento.criteri" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="procedCamerale.annoCriterio">
                            <spring:message code="cameralini.procedimento" />
                        </form:label>
                    </td>
                    <td>
                        <form:label path="procedCamerale.annoCriterio">
                            <spring:message code="procedimento.anno" />
                        </form:label>
                        <form:input path="procedCamerale.annoCriterio" id="anno" maxlength="4" size="5" />
                        <form:errors path="procedCamerale.annoCriterio" class="testoRosso" />
                    </td>
                    <td>
                        <form:label path="numeroMin">
                            <spring:message code="procedimento.numeroMin" />
                        </form:label>
                        <form:input path="numeroMin" maxlength="12" />
                        <form:errors path="numeroMin" class="testoRosso" />
                    </td>
                    <td>
                        <form:label path="numeroMax">
                            <spring:message code="procedimento.numeroMax" />
                        </form:label>
                        <form:input path="numeroMax" maxlength="12" />
                        <form:errors path="numeroMax" class="testoRosso" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="dataIscrizioneMin">
                            <spring:message code="procedimento.dataIscrizione" />
                        </form:label>
                    </td>
                    <td>
                        <form:label path="dataIscrizioneMin">
                            <spring:message code="procedimento.dataIscrizioneMin" />
                        </form:label>
                        <form:input path="dataIscrizioneMin" id="dataIscrizioneMin" maxlength="10" size="12" />
                        <form:errors path="dataIscrizioneMin" class="testoRosso" />
                    </td>
                    <td colspan="2">
                        <form:label path="dataIscrizioneMax">
                            <spring:message code="procedimento.dataIscrizioneMax" />
                        </form:label>
                        <form:input path="dataIscrizioneMax" id="dataIscrizioneMax" maxlength="10" size="12" />
                        <form:errors path="dataIscrizioneMax" class="testoRosso" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="comboStatoProcedimento.id">
                            <spring:message code="procedimento.statoProcedimento" />
                        </form:label>
                    </td>
                    <td colspan="3">
                        <form:select path="comboStatoProcedimento.id" size="1">
                            <form:option value="-1" label="--- Seleziona Stato ---" />
                            <form:options items="${statiProcedimento}" itemValue="id" itemLabel="descrizione" />
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="procedPadre.anno">
                            <spring:message code="cameralini.appellativoProcedimentoPadre" />
                        </form:label>
                    </td>
                    <td>
                        <form:label path="procedPadre.anno">
                            <spring:message code="procedimento.anno" />
                        </form:label>
                        <form:input path="procedPadre.anno" id="anno" maxlength="4" size="5" />
                        <form:errors path="procedPadre.anno" class="testoRosso" />
                    </td>
                    <td colspan="2">
                        <form:label path="procedPadre.numero">
                            <spring:message code="procedimento.numero" />
                        </form:label>
                        <form:input path="procedPadre.numero" maxlength="12" />
                        <form:errors path="procedPadre.numero" class="testoRosso" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="magistrato">
                            <spring:message code="stralcio.magistrato" />
                        </form:label>
                    </td>
                    <td colspan="3">
                        <form:input path="magistrato" />
                        <form:errors path="magistrato" class="testoRosso" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" rowspan="2">
                        <form:label path="cognomeSoggetto">
                            <spring:message code="cameralini.prevenzione.soggetto" />
                        </form:label>
                    </td>
                    <td colspan="1">
                        <form:label path="cognomeSoggetto">
                            <spring:message code="procedimento.cognomeSoggetto" />
                        </form:label>
                        <form:input path="cognomeSoggetto" />
                        <form:errors path="cognomeSoggetto" class="testoRosso" />
                    </td>
                    <td colspan="2">
                        <form:label path="nomeSoggetto">
                            <spring:message code="procedimento.nomeSoggetto" />
                        </form:label>
                        <form:input path="nomeSoggetto" />
                        <form:errors path="nomeSoggetto" class="testoRosso" />
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <form:label path="ragioneSociale">
                            <spring:message code="soggettogiuridico.ragioneSociale" />
                        </form:label>
                        <form:input path="ragioneSociale" />
                        <form:errors path="ragioneSociale" class="testoRosso" />
                    </td>
                </tr>
            </table>
            <br />
            <br />
            <table class="tb_bottoni" align="center">
                <tr>
                    <td class="td_tasto">
                        <input type="submit" name="Ricerca" title="Ricerca" value="Ricerca"/>
                    </td>
                </tr>
            </table>
            <br />
            <br />
            <logic:present name="risultati">
            
                <display:table id="row" name="${risultati}" class="tb_lista" uid="risultati" pagesize="10" requestURI="${azione}">
                    <display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
                        <form:radiobutton path="idProcedimento" value="${risultati.idProcedimento}" />
                    </display:column>
                    <display:column property="annoNumeroProcedimento" sortable="false" title="Procedimento" />
                    <display:column property="dataApertura" sortable="false" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" />
                    <display:column property="statoProcedimento.descrizione" sortable="false" title="Stato" />
                    <c:if test="${sessionScope.profilo.idTipoUfficio==17}">
                        <display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />
                        <display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />
                        <display:column property="NUM_PG" sortable="false" title="Proc.PG" />
                    </c:if>
                    <c:if test="${sessionScope.profilo.idTipoUfficio==8}">                  
                        <display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
                        <display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />
                        <display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />                     
                    </c:if>
                    <c:if test="${sessionScope.profilo.idTipoUfficio==19}">
                        <display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
                        <display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />                                         
                        <display:column property="NUM_PG" sortable="false" title="Proc.PG" />
                    </c:if>
                    <c:if test="${sessionScope.profilo.idTipoUfficio==6}">
                        <display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
                        <display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />                                         
                        <display:column property="NUM_PG" sortable="false" title="Proc.PG" />
                    </c:if>
                </display:table>
                <br />
                <br />
            </logic:present>
            <logic:notEmpty name="risultati">
                <table class="tb_bottoni" align="center">
                    <tr>
                        <td class="td_tasto">
                            <input type="submit" name="Dettaglio" title="Dettaglio" value="Dettaglio" />
                        </td>
                    </tr>
                </table>
                <br />
                <br />
            </logic:notEmpty>
        </form:form>
        <script type="text/javascript">
            $( document ).ready( function()
            {
                prendiData( "#dataIscrizioneMin", true );
                prendiData( "#dataIscrizioneMax", true );
            } );
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
<%--
 --%>