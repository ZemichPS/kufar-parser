package by.zemich.kufar.input.telegram.sessions;

public interface DialogSession<T> {
    T getValueByCriteria(String criteriaName);

    void addStepValue(String criteriaName, T value);

}
