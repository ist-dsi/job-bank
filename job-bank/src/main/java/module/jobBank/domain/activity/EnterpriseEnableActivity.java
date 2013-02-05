package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class EnterpriseEnableActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseEnableOrDisableInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
        return process.getEnterprise().isDisable() && JobBankSystem.getInstance().isNPEMember(user);
    }

    @Override
    protected void process(EnterpriseEnableOrDisableInformation activityInformation) {
        Enterprise enterprise = activityInformation.getProcess().getEnterprise();
        enterprise.enable();
        sendEmail(enterprise, activityInformation);
    }

    private void sendEmail(Enterprise enterprise, EnterpriseEnableOrDisableInformation eedi) {
        List<String> toAddresses = new ArrayList<String>();
        toAddresses.add(enterprise.getLoginEmail());
        JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        new Email(jobBankSystem.getEmailValidationFromName(), jobBankSystem.getEmailValidationFromEmail(), new String[] {},
                toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, getEmailSubject(), eedi.getMessage());
    }

    private String getEmailSubject() {
        String bundle = "message.jobbank.message.jobbank.email.enterprise.enable.subject";
        return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundle);
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
        Enterprise enterprise = process.getEnterprise();
        return new EnterpriseEnableOrDisableInformation(process, this, enterprise.getName(), !enterprise.isDisable());
    }

    @Override
    public String getUsedBundle() {
        return JobBankSystem.JOB_BANK_RESOURCES;
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
        return false;
    }
}
