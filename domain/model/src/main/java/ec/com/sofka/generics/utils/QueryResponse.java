package ec.com.sofka.generics.utils;

import java.util.List;
import java.util.Optional;

public class QueryResponse <R>{
    private final R singleResult;
    private final List<R> multipleResults;

    private QueryResponse(R singleResult, List<R> multipleResults) {
        this.singleResult = singleResult;
        this.multipleResults = multipleResults;
    }

    public static <R> QueryResponse<R> ofSingle(R singleResult) {
        return new QueryResponse<>(singleResult, null);
    }

    public static <R> QueryResponse<R> ofMultiple(List<R> multipleResults) {
        return new QueryResponse<>(null, multipleResults);
    }

    public Optional<R> getSingleResult() {
        return Optional.ofNullable(singleResult);
    }

    public List<R> getMultipleResults() {
        return multipleResults != null ? multipleResults : List.of();
    }
}
