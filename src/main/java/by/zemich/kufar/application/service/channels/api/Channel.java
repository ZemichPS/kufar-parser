package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.application.service.api.Notifiable;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;
import by.zemich.kufar.domain.service.PolicyChecker;
import jakarta.annotation.PostConstruct;

import java.util.List;

public abstract class Channel implements AdvertisementPublisher, Notifiable, ChannelApi {

    protected final PolicyChecker<Advertisement> policyChecker;

    protected Channel() {
        this.policyChecker = new PolicyChecker<>();
    }

    protected boolean checkPolicies(Advertisement advertisement) {
        return policyChecker.checkAll(advertisement);
    }

    protected  abstract List<Policy<Advertisement>> createPolicies();

    @PostConstruct
    private void setPolicies(){
        List<Policy<Advertisement>> policies = createPolicies();
        if (policies == null) {
            throw new IllegalStateException("Policies cannot be null");
        }
        policyChecker.initializePolicies(policies);
    }
}
