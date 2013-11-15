package module.jobBank.domain;

import module.jobBank.domain.beans.JobOfferBean;

public class JobOfferExternal extends JobOfferExternal_Base {

    public JobOfferExternal(JobOfferBean bean) {
        setCreateJobOffer(bean);
        if (checkHttpInLink(bean.getExternalLink()) == false) {
            setExternalLink("http://" + bean.getExternalLink());
        } else {
            setExternalLink(bean.getExternalLink());
        }
    }

    private boolean checkHttpInLink(String externalLink) {
        return externalLink.startsWith("http");
    }

    @Override
    public boolean isExternalCandidacy() {
        return true;
    }

    @Override
    public JobOfferExternal getJobOfferExternal() {
        return this;
    }

    @Override
    public String getExternalLink() {
        return checkHttpInLink(super.getExternalLink()) ? super.getExternalLink() : "http://" + super.getExternalLink();
    }

    @Deprecated
    public boolean hasExternalLink() {
        return getExternalLink() != null;
    }

}
