package ec.com.sofka.request.account;

import ec.com.sofka.generics.utils.Request;

//Usage of the Request class
public class DeleteAccountRequest extends Request {

    public DeleteAccountRequest(final String aggregateId) {
        super(aggregateId);

    }

}
