package es.mmm.managerartillery.infrastructure.secondary;

import es.mmm.managerartillery.domain.repository.GetCannonAvailable;
import es.mmm.managerartillery.domain.valueobject.CannonAvailable;
import es.mmm.managerartillery.infrastructure.secondary.dto.Cannon;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.*;

@Repository
public class CannonRepository implements GetCannonAvailable {

    private TreeMap<Integer, Cannon> data = new TreeMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void putData(Map<Integer, Cannon> treeMap)
    {
        this.data = (TreeMap<Integer, Cannon>) treeMap;
    }

    @Override
    public CannonAvailable getAvailableCannon() {
        Optional<Map.Entry<Integer, Cannon>> cannon = getCannonEntry();

        return cannon.map(
                integerCannonEntry ->
                        new CannonAvailable(integerCannonEntry.getKey(), integerCannonEntry.getValue().path()))
                .orElseGet(this::waitForCannonAvailability);
    }

    private Optional<Map.Entry<Integer, Cannon>> getCannonEntry() {
        return data.entrySet().stream()
                .filter(item -> item.getValue().available())
                .findFirst();
    }

    private CannonAvailable waitForCannonAvailability() {
        CompletableFuture<CannonAvailable> future = new CompletableFuture<>();
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            Optional<Map.Entry<Integer, Cannon>> cannon = getCannonEntry();
            cannon.ifPresent(
                    integerCannonEntry -> future.complete(
                            new CannonAvailable(integerCannonEntry.getKey(), integerCannonEntry.getValue().path())
                    ));
        }, 0, 300, TimeUnit.MILLISECONDS);
        try {
            return future.get();
        } catch (Exception ignored) {}
        finally {
            scheduledFuture.cancel(true);
        }
        return null;
    }
}
