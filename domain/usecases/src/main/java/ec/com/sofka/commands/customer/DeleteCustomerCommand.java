package ec.com.sofka.commands.customer;

import ec.com.sofka.generics.utils.Command;

public class DeleteCustomerCommand extends Command {

    public DeleteCustomerCommand(final String aggregateId) {
        super(aggregateId);
    }
}
