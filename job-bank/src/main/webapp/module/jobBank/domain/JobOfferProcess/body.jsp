<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>


<jsp:include page="../processStageView.jsp"/>


<bean:define id="jobOffer" name="process" property="jobOffer"/>
<bean:define id="candidates" name="process" property="jobOffer.activeOfferCandidacies"/>
<bean:define id="OID" name="process" property="externalId"/>

<p>
	<bean:message key="message.enterprise.view.process.help.information" bundle="JOB_BANK_RESOURCES"/>
</p>

<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffer.information"/>
</h3>

<div class="infobox mvert1">
	<fr:view name="jobOffer">
		<fr:schema type="module.jobBank.domain.JobOffer" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification"/>
			<fr:slot name="externalCandidacy" key="label.enterprise.jobOffer.candidacyType.externalCandidacy"/>
			<logic:equal name="jobOffer" property="externalCandidacy" value="true">
				<fr:slot name="jobOfferExternal" key="label.enterprise.JobofferExternal.externalLink" layout="link">
					<fr:property name="contextRelative" value="false"/>
					<fr:property name="linkFormat" value="${externalLink}" />
					<fr:property name="format" value="${externalLink}" />
				</fr:slot>
			</logic:equal>
			<fr:slot name="creationDate" key="label.enterprise.offer.creationDate"/>
			<fr:slot name="enterpriseName" layout="link" key="label.enterprise.name">
				<fr:property name="useParent" value="true"/>
				<fr:property name="linkFormat" value="/workflowProcessManagement.do?method=viewProcess&processId=${enterprise.enterpriseProcess.externalId}"/>
			</fr:slot>
			<fr:slot name="place" key="label.enterprise.jobOffer.place"/>
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType"/>
			<fr:slot name="function" key="label.enterprise.jobOffer.function"/>
			<fr:slot name="functionDescription" key="label.enterprise.jobOffer.functionDescription"/>
			<fr:slot name="requirements" key="label.enterprise.jobOffer.requirements"/>
			<fr:slot name="terms" key="label.enterprise.jobOffer.terms"/>
			<fr:slot name="beginDate" key="label.enterprise.offer.beginDate"/> 
			<fr:slot name="endDate" key="label.enterprise.offer.endDate"/>			
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="tview-horizontal"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>



<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacies"/>
</h3>

<table class="tview-horizontal"> 
	<tr> 
	<th><bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.total.vacancies"/></th> 
	<td><bean:write name="jobOffer"  property="vacancies"/></td> 
</tr> 
<tr> 
	<th><bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.total.free.vacancies"/></th> 
	<td><bean:write name="jobOffer"  property="numberOfFreeVacancies"/></td> 
	</tr> 
</table> 

<logic:notEmpty name="candidates">
	<fr:view name="candidates">
		<fr:layout name="tabular">
	
			<fr:property name="classes" value="tview-vertical"/>
			<fr:property name="link(view)" value="<%="/enterprise.do?method=viewStudentCurriculumForOfferCandidacy&OID="+OID %>" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="externalId/candidateOID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
	
			<fr:property name="classes" value="tview-vertical"/>
			<fr:property name="link(select)" value="<%="/enterprise.do?method=selectCandidateToJobOffer&OID="+OID %>"/>
			<fr:property name="key(select)" value="link.jobBank.selectCandidate" />
			<fr:property name="param(select)" value="externalId/candidateOID" />
			<fr:property name="bundle(select)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(select)" value="canSelectCandidacy" />
			
			<fr:property name="classes" value="tview-vertical"/>
			<fr:property name="columnClasses" value="aleft,,,,"/>
			<fr:property name="headerClasses" value="aleft,,,,"/>
			<fr:property name="link(remove)" value="<%="/enterprise.do?method=removeCandidateToJobOffer&OID="+OID %>"/>
			<fr:property name="key(remove)" value="link.jobBank.removeCandidate" />
			<fr:property name="param(remove)" value="externalId/candidateOID" />
			<fr:property name="bundle(remove)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(remove)" value="canRemoveCandidacy" />
	
		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="student.name" key="label.curriculum.name" />
			<fr:slot name="selected" key="label.offerCandidacy.selected" />
		</fr:schema>
	</fr:view>
</logic:notEmpty>


<logic:empty name="candidates">
	<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.candidates"/>.</em></p>
</logic:empty>





