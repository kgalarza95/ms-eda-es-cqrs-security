package ec.com.sofka.usecase.account;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.model.util.StatusEnum;
import ec.com.sofka.request.account.UpdateAccountRequest;
import ec.com.sofka.responses.account.UpdateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeleteAccountUseCase implements IUseCaseExecute<UpdateAccountRequest, UpdateAccountResponse> {
    private final IAccountRepositoryGateway IAccountRepositoryGateway;
    private final IEventStoreGateway eventRepository;

    public DeleteAccountUseCase(IAccountRepositoryGateway IAccountRepositoryGateway, IEventStoreGateway eventRepository) {
        this.IAccountRepositoryGateway = IAccountRepositoryGateway;
        this.eventRepository = eventRepository;
    }


    @Override
    public Mono<UpdateAccountResponse> execute(UpdateAccountRequest request) {

        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getNumber().getValue(),
                            customer.getAccount().getName().getValue(),
                            StatusEnum.INACTIVE.name()
                    );

                    AccountDTO accountDTO = new AccountDTO(
                            customer.getAccount().getId().getValue(),
                            customer.getAccount().getNumber().getValue(),
                            customer.getAccount().getName().getValue(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getStatus().getValue()
                    );

                    return IAccountRepositoryGateway.delete(accountDTO)
                            .flatMap(result -> {
                                if (result != null) {
                                    // Guarda los eventos no confirmados
                                    return Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(eventRepository::save)
                                            .then(Mono.fromCallable(() -> {

                                                customer.markEventsAsCommitted();

                                                return new UpdateAccountResponse(
                                                        request.getAggregateId(),
                                                        accountDTO.getId(),
                                                        accountDTO.getAccountNumber(),
                                                        accountDTO.getName(),
                                                        accountDTO.getStatus()
                                                );
                                            }));
                                } else {
                                    return Mono.just(new UpdateAccountResponse());
                                }
                            });
                });
    }


//    @Override
//    public UpdateAccountResponse execute(UpdateAccountRequest request) {
//        //Get events related to the aggregateId on the request
//        List<DomainEvent> events = eventRepository.findAggregate(request.getAggregateId());
//
//        //Rebuild the aggregate
//        CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(),events);
//
//        customer.updateAccount(
//                customer.getAccount().getId().getValue(),
//                customer.getAccount().getBalance().getValue(),
//                customer.getAccount().getNumber().getValue(),
//                customer.getAccount().getName().getValue(),
//                StatusEnum.INACTIVE.name()
//        );
//
//        //"Delete" the account
//        AccountDTO result = accountRepositoryGateway.delete(
//                new AccountDTO(customer.getAccount().getId().getValue(),
//                        customer.getAccount().getNumber().getValue(),
//                        customer.getAccount().getName().getValue(),
//                        customer.getAccount().getBalance().getValue(),
//                        customer.getAccount().getStatus().getValue()
//                ));
//
//        if (result != null) {
//            //Last step for events to be saved
//            customer.getUncommittedEvents().forEach(eventRepository::save);
//
//            customer.markEventsAsCommitted();
//
//            return new UpdateAccountResponse(
//                    request.getAggregateId(),
//                    result.getId(),
//                    result.getAccountNumber(),
//                    result.getName(),
//                    result.getStatus());
//        }
//
//        return new UpdateAccountResponse();
//    }
}
