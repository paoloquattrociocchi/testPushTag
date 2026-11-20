<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<body style="margin-top: 0px; margin-bottom: 0px; margin-left: 0px; margin-right: 0px; padding: 0;">
	<!--  
	<embed src="${pageContext.request.contextPath}/procedimento/atto/scaricaPDFCertificatoPenale.htm" width="500" height="375" type='application/pdf'>
	-->
	<object data="${pageContext.request.contextPath}/procedimento/atto/scaricaPDFCertificatoPenale.htm" type="application/pdf" width="100%" height="100%">
  		<p>Per visualizzare il certificato è richiesto Acrobat.</p>
  	</object>
</body>
</html>