package ec.com.sofka.generics.utils;

//10. Generics creation to apply DDD: Request - Abstract class to generate request/commands
public abstract class Query {
    private final String aggregateId;

    protected Query(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}
