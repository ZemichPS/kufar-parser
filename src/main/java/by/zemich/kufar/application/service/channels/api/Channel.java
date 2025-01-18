package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.application.service.api.Notifiable;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;
import by.zemich.kufar.domain.service.PolicyChecker;

import java.util.List;

public abstract class Channel implements AdvertisementPublisher, Notifiable, ChannelApi {
    private final PolicyChecker<Advertisement> policyChecker;

    protected Channel(List<Policy<Advertisement>> policies) {
        this.policyChecker = new PolicyChecker<>(policies);
    }

    protected boolean checkPolicies(Advertisement advertisement) {
        return policyChecker.checkAll(advertisement);
    }
}
