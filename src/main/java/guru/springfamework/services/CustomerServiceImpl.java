package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerReposity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerReposity customerReposity;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerReposity customerReposity) {
        this.customerMapper = customerMapper;
        this.customerReposity = customerReposity;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerReposity
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.custermToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("api/v1/customers" + customer.getId());
                    return customerDTO;
                } )
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerReposity.findById(id)
                .map(customerMapper::custermToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }
}
