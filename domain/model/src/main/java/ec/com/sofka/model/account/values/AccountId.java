package ec.com.sofka.model.account.values;

import ec.com.sofka.generics.utils.Identity;

//3. Implementing generics: For account case, create the Identity
public class AccountId extends Identity {
    public AccountId() {
        super();
    }

    //wtf why private??
    private AccountId(final String id) {
        super(id);
    }


    //who tf are you?? I am the method to access/make instances the id with the private modifier :D
    public static AccountId of(final String id) {
        return new AccountId(id);
    }
}
