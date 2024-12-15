package by.zemich.kufar.input.telegram.dialogs.api;

import java.util.HashMap;
import java.util.Map;

public interface DialogSession<T> {
    T getValueByCriteria(String criteria);

    void addStepValue(String criteria, T value);

}
