package ec.com.sofka.request.customer;

import ec.com.sofka.generics.utils.Request;
import ec.com.sofka.model.util.StatusEnum;

import java.time.LocalDate;

public class DeleteCustomerRequest extends Request {

    public DeleteCustomerRequest(final String aggregateId) {
        super(aggregateId);
    }
}
