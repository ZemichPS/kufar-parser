package by.zemich.kufar.domain.service;

import by.zemich.kufar.domain.policy.api.Policy;
import lombok.Setter;

import java.util.List;

public class PolicyChecker<T> {
    private List<Policy<T>> policies;

    public PolicyChecker() {
    }

    public PolicyChecker(List<Policy<T>> policies) {
        this.policies = policies;
    }

    public void initializePolicies(List<Policy<T>> policies){
        this.policies = policies;
    }

    public boolean checkAll(T item) {
        return policies.stream().allMatch(policy -> policy.isSatisfiedBy(item));
    }
}