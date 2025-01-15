package by.zemich.kufar.domain.policy.api;

public interface Policy<T> {
    /**
     * Проверяет, удовлетворяет ли объект домена данной политике.
     *
     * @param domainObject объект домена, для которого проверяется политика
     * @return true, если политика выполняется, иначе false
     */
    boolean isSatisfiedBy(T domainObject);

    default Policy<T> and(Policy<T> other) {
        return domainObject -> isSatisfiedBy(domainObject) && other.isSatisfiedBy(domainObject);
    }

    default Policy<T> or(Policy<T> other) {
        return domainObject -> isSatisfiedBy(domainObject) || other.isSatisfiedBy(domainObject);
    }

    default Policy<T> not(Policy<T> other) {
        return domainObject -> !other.isSatisfiedBy(domainObject);
    }

}
