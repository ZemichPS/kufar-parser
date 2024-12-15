package by.zemich.kufar.input.telegram.dialogs.api;

import java.util.HashMap;
import java.util.Map;

public class AbstractDialogSession<T> implements DialogSession<T> {
    private final Map<String, T> criteriaMap = new HashMap<>();

    @Override
    public T getValueByCriteria(String criteria) {
        return null;
    }

    @Override
    public void addStepValue(String criteria, T value) {
        criteriaMap.put(criteria, value);
    }
}
