package ec.com.sofka;


import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.request.CreateAccountRequest;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCase;
import ec.com.sofka.responses.CreateAccountResponse;

//Usage of the IUseCase interface
public class CreateAccountUseCase implements IUseCase<CreateAccountRequest,CreateAccountResponse> {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }


    //Of course, you have to create that class Response in usecases module on a package called responses or you can also group the command with their response class in a folder (Screaming architecture)
    //You maybe want to check Jacobo's repository to see how he did it
    public CreateAccountResponse execute(CreateAccountRequest cmd) {
        //Create the aggregate, remember this usecase is to create the account the first time so just have to create it.
        Customer customer = new Customer();

        //Then we create the account
        customer.createAccount(cmd.getBalance(), cmd.getNumber(), cmd.getCustomerName());

        //Save the account on the account repository
        accountRepository.save(
                new AccountDTO(
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getNumber().getValue(),
                        customer.getAccount().getName().getValue()

                ));

        //Last step for events to be saved
        customer.getUncommittedEvents().forEach(repository::save);

        customer.markEventsAsCommitted();

        //Return the response
        return new CreateAccountResponse(
                customer.getId().getValue(),
                customer.getAccount().getNumber().getValue(),
                customer.getAccount().getName().getValue());
    }
}
