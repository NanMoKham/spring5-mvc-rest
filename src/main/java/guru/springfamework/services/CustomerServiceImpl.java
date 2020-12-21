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

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        Customer saveCustomer = customerReposity.save(customer);

        CustomerDTO returnDTO = customerMapper.custermToCustomerDTO(saveCustomer);

        returnDTO.setCustomerUrl("/api/v1/customers/" + saveCustomer.getId());

        return returnDTO;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerReposity.save(customer);

        CustomerDTO returnDto = customerMapper.custermToCustomerDTO(savedCustomer);

        returnDto.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

        return returnDto;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerReposity.findById(id).map(customer -> {

            if(customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if(customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnDto = customerMapper.custermToCustomerDTO(customerReposity.save(customer));

            returnDto.setCustomerUrl("/api/v1/customer/" + id);

            return returnDto;

        }).orElseThrow(RuntimeException::new); //todo implement better exception handling;
    }
}
