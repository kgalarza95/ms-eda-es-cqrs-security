package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class DeleteAccountRequest extends Request {

    public DeleteAccountRequest(final String aggregateId) {
        super(aggregateId);

    }

}
