package ec.com.sofka.generics.domain;

import ec.com.sofka.generics.interfaces.IApplyEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

//6. Generics creation to apply DDD: DomainActionsHandler
public class DomainActionsHandler {
    private final List<DomainEvent> events = new LinkedList<>();
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();
    private final Set<Consumer<? super DomainEvent>> actions = new HashSet<>();


    public List<DomainEvent> getEvents() {
        return events;
    }

    public void subscribe(final DomainActionsContainer container) {
        actions.addAll(container.domainActions);
    }

    public IApplyEvent append(final DomainEvent event){
        events.add(event);
        return () -> apply(event);
    }

    private long increaseVersion(final DomainEvent event){
        final AtomicLong current = versions.get(event.getEventType());
        final long newVersion = current != null ? current.incrementAndGet():event.getVersion();
        versions.put(event.getEventType(), new AtomicLong(newVersion));
        return newVersion;
    }

    private void handle(final DomainEvent event, final Consumer<? super DomainEvent> action){
        try{ //To avoid a casting issue
            action.accept(event);
            long version = increaseVersion(event);
            event.setVersion(version);
        }catch(Exception ignored){}
    }

    private void apply(final DomainEvent event){
        actions.forEach(action -> handle(event, action));
    }
}
