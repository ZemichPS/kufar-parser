package by.zemich.kufar.input.telegram.sessions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbstractDialogSession<T> implements DialogSession<T> {

    protected final Map<String, T> criteriaMap = new HashMap<>();
    private final UUID userID;

    public AbstractDialogSession(UUID userID) {
        this.userID = userID;
    }

    @Override
    public T getValueByCriteria(String criteriaName) {
        return null;
    }

    @Override
    public List<T> getValues() {
        return criteriaMap.values().stream().toList();
    }

    @Override
    public void addStepValue(String criteriaName, T value) {
        criteriaMap.put(criteriaName, value);
    }

    @Override
    public UUID getUserId() {
        return userID;
    }


}
