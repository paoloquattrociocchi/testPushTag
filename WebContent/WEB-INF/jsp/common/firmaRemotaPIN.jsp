<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<table style="width: 100%" align="center" cellspacing="4"
	cellpadding="0" border="0">
	<tr>
		<td colspan="2">
			<div id="id_divEsito"></div>
		</td>
	</tr>
	<tr>
		<td><form:label path="allegato.firmaRemota.userName" cssClass="required"> 
				<spring:message code="documentale.firmaremota.usrname" />
			</form:label>
		</td>
		<td><input id="id_inputUserName" type="text" maxlength="40"
			style="width: 200" />
			<div id="id_divUserName">
				<span class="testoRosso" style="font-size: 10"></span>
			</div>
		</td>
	</tr>
	<tr>
		<td><form:label path="allegato.firmaRemota.password" cssClass="required">
				<spring:message code="documentale.firmaremota.pwd" />
					</form:label></td>
		<td><input id="id_inputPassword" type="password" maxlength="40"
			style="width: 200" />
			<div id="id_divPassword">
				<span class="testoRosso" style="font-size: 10"></span>
			</div>
		</td>
	</tr>
	<tr>
		<td><form:label path="allegato.firmaRemota.documentalePin" cssClass="required">
				<spring:message code="documentale.firmaremota.pin" />
					</form:label></td>
		<td><input id="id_inputPin" type="password" maxlength="28"
			style="width: 200" />

			<div id="id_divPin">
				<span class="testoRosso" style="font-size: 10"></span>
			</div>
		</td>
	</tr>
</table>
