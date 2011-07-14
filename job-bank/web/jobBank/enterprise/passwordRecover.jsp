<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.passwordRecover"/> </h2> 

<logic:messagesPresent property="error" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="error" message="true" bundle="JOB_BANK_RESOURCES"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>
<logic:messagesPresent property="message" message="true">
	<div class="warning1">
		<html:messages id="errorMessage" property="message" message="true" bundle="JOB_BANK_RESOURCES"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<logic:present name="passwordRecover">
	<h3> 
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.recoverPassword.information"/>
	</h3>
	<fr:edit id="passwordRecover" name="passwordRecover" action="/enterprise.do?method=passwordRecover">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Curriculum">
			<fr:slot name="string" key="label.enterprise.loginEmail" validator="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>
		</fr:schema>
		<fr:destination name="cancel" path="/jobBank.do?method=frontPage"/>
	</fr:edit>
</logic:present>


