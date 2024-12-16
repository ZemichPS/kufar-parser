package by.zemich.kufar.input.telegram.sessions;

import java.util.HashMap;
import java.util.Map;

public class AbstractDialogSession<T> implements DialogSession<T> {

   protected final Map<String, T> criteriaMap = new HashMap<>();

    @Override
    public T getValueByCriteria(String criteriaName) {
        return null;
    }

    @Override
    public void addStepValue(String criteriaName, T value) {
        criteriaMap.put(criteriaName, value);
    }
}
