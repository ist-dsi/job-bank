package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import module.organization.domain.AccountabilityType;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.util.BundleUtil;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class EnterpriseAgreementApprovalInformation extends ApprovalInformation {

    private static final long serialVersionUID = 1L;

    private MultiLanguageString enterpriseName;

    private JobBankAccountabilityType newAgreement;

    public EnterpriseAgreementApprovalInformation(final EnterpriseProcess process,
	    WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity,
	    MultiLanguageString enterpriseName, AccountabilityType newAgreement) {
	super(process, activity);
	setNewAgreement(JobBankAccountabilityType.readAccountabilityType(newAgreement));
	setEnterpriseName(enterpriseName);
	updateMessage();
    }

    @Override
    public void updateMessage() {
	StringBuilder body = new StringBuilder();
	body.append(getBody(enterpriseName));
	body.append(getSignature());
	setMessage(body.toString());
    }

    private String getBody(MultiLanguageString enterpriseName) {
	String bundleName = "message.jobbank.enterprise.agreement.approval.email.body";

	if (!isApprove()) {
	    bundleName = "message.jobbank.enterprise.agreement.rejection.email.body";
	}

	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundleName,
		enterpriseName.toString());
    }
    
    private String getSignature() {
	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "message.jobbank.ist.signature");
    }

    public void setEnterpriseName(MultiLanguageString enterpriseName) {
	this.enterpriseName = enterpriseName;
    }

    public MultiLanguageString getEnterpriseName() {
	return enterpriseName;
    }

    public JobBankAccountabilityType getNewAgreement() {
	return newAgreement;
    }

    public void setNewAgreement(JobBankAccountabilityType newAgreement) {
	this.newAgreement = newAgreement;
    }
}
