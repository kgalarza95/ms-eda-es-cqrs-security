package ec.com.sofka.model.transaction.values;

import ec.com.sofka.generics.utils.Identity;

public class TransactionId extends Identity {

    public TransactionId(){super();}

    private TransactionId(String id){super(id);}

    public static TransactionId of(String id){ return new TransactionId(id);}
}
