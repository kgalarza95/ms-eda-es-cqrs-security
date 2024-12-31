package ec.com.sofka.generics.utils;

//10. Generics creation to apply DDD: Request - Abstract class to generate request/commands
public abstract class Request {
    private final String aggregateId;

    protected Request(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}
