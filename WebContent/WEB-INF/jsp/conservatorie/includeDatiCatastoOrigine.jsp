<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

			</br>
			</br>
			</br>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="bene.immobile.datiCatastaliOrigine" /></td>
				</tr>
			</table>

<c:if test="${comunicazioneBeniForm.tipoOperazione =='Modifica'}">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td width="12%"><form:label path="comuneCatastaleP" ><spring:message code="comune" /></form:label></td>
					<td colspan="3">
                    <!-- 
                        <div class="ui-widget">
                            <form:input id="id_descrizioneComuneCatastaleP" path="comuneCatastaleP.descrizione" size="50"
                                    onclick="javascript:controllaSelezione('ComuneCatastaleP')" title="Immettere almeno 2 lettere per avviare la ricerca" />
                            <div id="id_loadingComuneCatastaleP"></div>
                            <form:hidden id="id_hiddenComuneCatastaleP" path="comuneCatastaleP.id" />
                        </div>
                    -->
						<div class="ui-widget">
							<form:input path="comuneCatastaleP.descrizione" size="50"
                                id="id_descrizioneComuneCatastaleP" 
							    title="Immettere almeno 2 lettere per avviare la ricerca" 
                                value="${comunicazioneBeniForm.descComuneBeneP }" onchange="componiCodCatastoP()"/>
							
                            <div id="id_loadingComuneCatastaleP"></div>
							<form:hidden id="id_hiddenComuneCatastaleP" path="comuneCatastaleP.id" />
							<form:hidden id="id_hiddenCatastoP_Originale" path="codiceCatastale1POld" value="${comunicazioneBeniForm.codiceCatastale1P }" />
							<form:hidden id="id_hiddenCatastoP_DescComuneBeneP" path="descComuneBeneP" />
						</div>
					</td>
				</tr>
				<tr>
					<td><form:label  path="codiceCatastale1P" ><spring:message code="bene.immobile.codicecatastale" /></form:label></td>
					<td colspan="3"><form:input id="codiceCatastale1P" path="codiceCatastale1P" readonly="true" /> 
						<form:input id="codiceCatastale2P" path="codiceCatastale2P" onfocus="javascript:controllaCodiceCastasto('id_codiceCatastale1P',this)" maxlength="1" size="3" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form:label path="tipoCatastoP" ><spring:message code="bene.immobile.tipocatasto" /></form:label>
						&nbsp;&nbsp;
						<form:select id="id_tipoCatastoP" path="tipoCatastoP" size="1"  value="${comunicazioneBeniForm.tipoCatastoP}" onchange="abilitaInputP()">
							<form:option value="" label="" />
							<form:options items="${tipoCatasto}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
				</tr>
			</table>
			</br>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" width="3%">
						<c:if test="${comunicazioneBeniForm.identificazioneCatastaleP!=true}" >
							<form:checkbox path="identificazioneCatastaleP" id="identificazioneCatastaleP" onclick="abilitaIdenCatastaleP()"/>
						</c:if>
						<c:if test="${comunicazioneBeniForm.identificazioneCatastaleP==true}" >
							<form:checkbox path="identificazioneCatastaleP" id="identificazioneCatastaleP" disabled="true" onclick="abilitaIdenCatastaleP()"/>
						</c:if>
					</td>
					<td class="td_titolo"><spring:message code="conservatoria.IdentificazioneCatastoOrigine" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="3%"><form:label path="descSezioneP"><spring:message code="bene.immobile.sezione" /></form:label></td>
					<td width="10%"><form:input id="descSezioneP" path="descSezioneP" maxlength="3" size="5" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}"/></td>
					<td width="3%"><form:label path="descFoglioP" ><spring:message code="bene.immobile.foglio" /></form:label></td>
					<td width="10%"><form:input id="descFoglioP" path="descFoglioP" maxlength="4" size="6" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}" /></td>
					<td width="3%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<td><form:label path="descParticella1P" ><spring:message code="bene.immobile.particella" /></form:label></td>
					<td><form:input id="descParticella1P" path="descParticella1P" maxlength="5" size="7" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}" />/ <form:input id="descParticella2P" path="descParticella2P" maxlength="4" size="6" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}" /></td>
					<td><form:label path="descSubalterno1P"><spring:message code="bene.immobile.sub" /></form:label></td>
					<td><form:input id="descSubalterno1P" path="descSubalterno1P" maxlength="4" size="6" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}" />/ <form:input id="descSubalterno2P" path="descSubalterno2P" maxlength="3" size="5" disabled="${comunicazioneBeniForm.disabledCampiIdentificazioneP}" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			</br>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" width="3%">
						<c:if test="${comunicazioneBeniForm.denunciaAccatastamentoP!=true}" >
							<form:checkbox path="denunciaAccatastamentoP" id="denunciaAccatastamentoP" onclick="abilitaDenunciaCatastaleP()"/>
						</c:if>
						<c:if test="${comunicazioneBeniForm.denunciaAccatastamentoP==true}" >
							<form:checkbox path="denunciaAccatastamentoP" id="denunciaAccatastamentoP" disabled="true" onclick="abilitaDenunciaCatastaleP()"/>
						</c:if>
					</td>
					<td class="td_titolo"><spring:message code="bene.immobile.denunciaaccatastamentoOrigine" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="5%"><form:label path="descTipoDenunciaP" ><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
					<td>
						<form:select id="tipoDenunciaP" path="tipoDenunciaP" size="1" disabled="${comunicazioneBeniForm.disabledCampiDenunciaP}" >
							<form:option value="" label="" />
							<form:options items="${tipoDenuncia}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
					<td colspan="2"><form:label path="numeDenuncia1P" ><spring:message code="procedimento.numero" /></form:label>
						&nbsp;&nbsp;
						<form:input id="numeDenuncia1P" path="numeDenuncia1P" maxlength="9" size="11" disabled="${comunicazioneBeniForm.disabledCampiDenunciaP}" onblur="javascript:validaCampoNumerico(this)" />/
						<form:input id="numeDenuncia2P" path="numeDenuncia2P" maxlength="4" size="6" disabled="${comunicazioneBeniForm.disabledCampiDenunciaP}" onblur="javascript:validaCampoNumerico(this)" /> 
						<form:errors path="numeDenuncia1P" class="testoRosso" /> 
						<form:errors path="numeDenuncia2P" class="testoRosso" />
					</td>
				</tr>
				<tr>
					<td><form:label path="numeAnnoDenunciaP" ><spring:message code="bene.immobile.del" /></form:label></td>
					<td colspan="3">
						<form:input id="numeAnnoDenunciaP" path="numeAnnoDenunciaP" maxlength="4" size="6" disabled="${comunicazioneBeniForm.disabledCampiDenunciaP}" onblur="javascript:validaCampoNumerico(this)" />
						<form:errors path="numeAnnoDenunciaP" class="testoRosso" />
					</td>
				</tr>
			</table>
</c:if>
<c:if test="${comunicazioneBeniForm.tipoOperazione =='Dettaglio'}">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td width="10%"><form:label path="comuneCatastaleP.id"><spring:message code="comune" /></form:label></td>
					<td colspan="3">
						<c:out value="${comunicazioneBeniForm.descComuneBeneP}" />
					</td>
				</tr>
				<tr>
					<td width="3%"><form:label path="codiceCatastale1P"><spring:message code="bene.immobile.codicecatastale" /></form:label></td>
					<td>
						<c:out value="${comunicazioneBeniForm.codiceCatastale1P}" />&nbsp;&nbsp;<c:out value="${comunicazioneBeniForm.codiceCatastale2P}"/>
					</td>
					<td><form:label path="tipoCatastoP"><spring:message code="bene.immobile.tipocatasto" /></form:label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.tipoCatastoP}" />
					</td>
<!--  
					<td width="10%">
						<c:out value="${comunicazioneBeniForm.tipoCatastoP}" />
					</td>
-->
  				</tr>
			</table>
			</br>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="conservatoria.IdentificazioneCatastoOrigine" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="3%"><form:label path="descSezioneP"><spring:message code="bene.immobile.sezione" /></form:label></td>
					<td width="10%"><c:out value="${comunicazioneBeniForm.descSezioneP}" /></td>
					<td width="3%"><form:label path="descFoglioP"><spring:message code="bene.immobile.foglio" /></form:label></td>
					<td width="10%"><c:out value="${comunicazioneBeniForm.descFoglioP}" /></td>
					<td width="3%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<td><form:label path="descParticella1P"><spring:message code="bene.immobile.particella" /></form:label></td>
					<td><c:out value="${comunicazioneBeniForm.descParticella1P}" />/ <c:out value="${comunicazioneBeniForm.descParticella2P}" /></td>
					<td><form:label path="descSubalterno1P"><spring:message code="bene.immobile.sub" /></form:label></td>
					<td><c:out value="${comunicazioneBeniForm.descSubalterno1P}" />/ <c:out value="${comunicazioneBeniForm.descSubalterno2P}" /></td>
					<td width="3%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
			</table>
			</br>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="bene.immobile.denunciaaccatastamentoOrigine" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="3%"><form:label path="descTipoDenunciaP"><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
					<td width="10%">
						<c:out value="${comunicazioneBeniForm.descTipoDenunciaP}" />
					</td>
					<td width="3%"><form:label path="numeDenuncia1P"><spring:message code="procedimento.numero" /></form:label></td>
					<td width="10%">
						<c:out value="${comunicazioneBeniForm.numeDenuncia1P}" />/ <c:out value="${comunicazioneBeniForm.numeDenuncia2P}" />
					</td>
					<td width="3%">&nbsp;</td>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<td><form:label path="numeAnnoDenunciaP"><spring:message code="bene.immobile.del" /></form:label></td>
					<td colspan="5">
						<c:out value="${comunicazioneBeniForm.numeAnnoDenunciaP}" />
					</td>
				</tr>
			</table>
			</br>
</c:if>

<script type="text/javascript">
    doAutocompleteConservat('ComuneCatastaleP');
</script>
            
