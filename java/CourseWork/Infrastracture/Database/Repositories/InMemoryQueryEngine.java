package CourseWork.Infrastracture.Database.Repositories;

import java.util.*;

public class InMemoryQueryEngine<T> {
    private final Map<UUID, T> data = new HashMap<>();

    public boolean insert(UUID id, T obj) {
        if (data.containsKey(id)) return false;
        data.put(id, obj);
        return true;
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean update(UUID id, T updatedObj) {
        if (!data.containsKey(id)) return false;
        data.put(id, updatedObj);
        return true;
    }

    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}
