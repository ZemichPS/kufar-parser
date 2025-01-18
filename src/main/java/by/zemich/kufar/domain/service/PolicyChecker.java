package by.zemich.kufar.domain.service;

import by.zemich.kufar.domain.policy.api.Policy;

import java.util.List;

public class PolicyChecker<T> {
    private final List<Policy<T>> policies;

    public PolicyChecker(List<Policy<T>> policies) {
        this.policies = policies;
    }

    public boolean checkAll(T item) {
        return policies.stream().allMatch(policy -> policy.isSatisfiedBy(item));
    }
}