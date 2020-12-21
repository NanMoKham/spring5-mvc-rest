package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerReposity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerReposity customerReposity) {
        this.categoryRepository = categoryRepository;
        this.customerReposity = customerReposity;
    }

    private CustomerReposity customerReposity;



    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded = " + categoryRepository.count());
    }

    private void loadCustomers(){
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Jone");
        customer1.setLastName("Smith");
        customerReposity.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Smile");
        customer2.setLastName("Lay");
        customerReposity.save(customer2);

        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setFirstName("Mark");
        customer3.setLastName("Pink");
        customerReposity.save(customer3);

        System.out.println("Data Loaded = " + customerReposity.count());
    }
}
