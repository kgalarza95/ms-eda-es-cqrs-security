package ec.com.sofka.queries.usecase;



import ec.com.sofka.queries.account.GetAccountQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GetAccountQueryTest {

    private String aggregateId = "customer-123";
    private String accountNumber = "account-456";
    private GetAccountQuery query;

    @BeforeEach
    void setUp() {
        query = new GetAccountQuery(aggregateId, accountNumber);
    }

    @Test
    void shouldReturnCorrectAggregateId() {
        assertEquals(aggregateId, query.getAggregateId());
    }

    @Test
    void shouldReturnCorrectAccountNumber() {
        assertEquals(accountNumber, query.getNumber());
    }

    @Test
    void shouldReturnNullCustomerName() {
        assertNull(query.getCustomerName());
    }

    @Test
    void shouldReturnNullBalance() {
        assertNull(query.getBalance());
    }

    @Test
    void shouldCreateQueryWithCorrectValues() {
        assertEquals(aggregateId, query.getAggregateId());
        assertEquals(accountNumber, query.getNumber());
    }

}
