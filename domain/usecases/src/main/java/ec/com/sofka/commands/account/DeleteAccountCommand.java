package ec.com.sofka.commands.account;

import ec.com.sofka.generics.utils.Command;

//Usage of the Request class
public class DeleteAccountCommand extends Command {

    public DeleteAccountCommand(final String aggregateId) {
        super(aggregateId);

    }

}
