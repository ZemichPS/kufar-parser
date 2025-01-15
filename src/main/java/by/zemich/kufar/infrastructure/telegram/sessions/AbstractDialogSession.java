package by.zemich.kufar.infrastructure.telegram.sessions;

import jakarta.persistence.Id;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbstractDialogSession<T> implements DialogSession<T> {

    @Id
    @Getter
    private final UUID id;

    private final UUID userID;
    protected final Map<String, T> criteriaMap = new HashMap<>();

    public AbstractDialogSession(UUID id, UUID userID) {
        this.id = id;
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
