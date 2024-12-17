package by.zemich.kufar.input.telegram.sessions;

import java.util.List;
import java.util.UUID;

public interface DialogSession<T> {
    T getValueByCriteria(String criteriaName);
    List<T> getValues();
    void addStepValue(String criteriaName, T value);
    UUID getUserId();


}
