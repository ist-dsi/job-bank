package module.jobBank.domain.beans;

import java.util.Set;
import java.util.StringTokenizer;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.utils.IPredicate;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.util.Search;
import net.sourceforge.fenixedu.domain.RemoteDegree;

import org.apache.commons.lang.StringUtils;

public class SearchOffer extends Search<JobOfferProcess> {

    private String query;
    private JobOfferType jobOfferType;
    private RemoteDegree remoteDegree;

    public SearchOffer() {
	setQuery(StringUtils.EMPTY);
    }

    public JobOfferType getJobOfferType() {
	return jobOfferType;
    }

    public void setJobOfferType(JobOfferType jobOfferType) {
	this.jobOfferType = jobOfferType;
    }

    public void setRemoteDegrees(RemoteDegree remoteDegree) {
	this.remoteDegree = remoteDegree;
    }

    public RemoteDegree getRemoteDegrees() {
	return remoteDegree;
    }

    @Override
    public Set<JobOfferProcess> search() {
	final User user = UserView.getCurrentUser();
	Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer offer = object.getJobOffer();
		return offer.isActive() && offer.isCandidancyPeriod() && isSatisfiedJobOfferType(offer)
			&& isSatisfiedDegres(offer);
	    }
	});

	// Search Query
	StringTokenizer tokens = getTokens();
	while (tokens.hasMoreElements()) {
	    jobOfferProcesses = matchQuery(tokens.nextElement().toString(), jobOfferProcesses);
	}

	return jobOfferProcesses;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getQuery() {
	return query;
    }

    public StringTokenizer getTokens() {
	String delim = " ";
	return new StringTokenizer(getQuery() == null ? StringUtils.EMPTY : getQuery(), delim);
    }

    public boolean isEmptyQuery() {
	return !getTokens().hasMoreElements();
    }

    private Set<JobOfferProcess> matchQuery(final String key, Set<JobOfferProcess> jobOfferProcesses) {
	return JobBankSystem.getInstance().readValuesToSatisfiedPredicate(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer jobOffer = object.getJobOffer();
		return isSatisfiedQuery(key, jobOffer);
	    }
	}, jobOfferProcesses);

    }

    private boolean isSatisfiedQuery(String key, JobOffer offer) {
	return isEmptyQuery() || match(key, offer.getEnterpriseName().getContent())
		|| match(key, offer.getFunction().getContent()) || match(key, offer.getPlace());
    }

    private boolean isSatisfiedJobOfferType(JobOffer offer) {
	return getJobOfferType() == null || offer.getJobOfferType() == getJobOfferType();
    }

    private boolean isSatisfiedDegres(JobOffer offer) {
	return getRemoteDegrees() == null || offer.getRemoteDegrees().equals(getRemoteDegrees());
    }

    private boolean match(String key, String value) {
	return value.toLowerCase().contains(key.toLowerCase());
    }
}
