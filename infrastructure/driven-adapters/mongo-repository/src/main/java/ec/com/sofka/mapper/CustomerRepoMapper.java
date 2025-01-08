package ec.com.sofka.mapper;

import ec.com.sofka.document.ClientEntity;
import ec.com.sofka.gateway.dto.CustomerDTO;
import ec.com.sofka.model.customer.Customer;

public class CustomerRepoMapper {

    public static CustomerDTO toDomain(ClientEntity client) {
        if (client == null) {
            return null;
        }

        CustomerDTO customer = new CustomerDTO();
        customer.setId(client.getId());
        customer.setIdentification(client.getIdentification());
        customer.setFirstName(client.getFirstName());
        customer.setLastName(client.getLastName());
        customer.setEmail(client.getEmail());
        customer.setPhone(client.getPhone());
        customer.setAddress(client.getAddress());
        customer.setBirthDate(client.getBirthDate());

        return customer;
    }

    public static ClientEntity toEntity(CustomerDTO customer) {
        if (customer == null) {
            return null;
        }

        ClientEntity client = new ClientEntity();
        client.setId(customer.getId());
        client.setIdentification(customer.getIdentification());
        client.setFirstName(customer.getFirstName());
        client.setLastName(customer.getLastName());
        client.setEmail(customer.getEmail());
        client.setPhone(customer.getPhone());
        client.setAddress(customer.getAddress());
        client.setBirthDate(customer.getBirthDate());

        return client;
    }
}
