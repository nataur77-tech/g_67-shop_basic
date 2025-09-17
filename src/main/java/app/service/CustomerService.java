package app.service;

/*
Функционал сервиса покупателей.

Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается активным).
Вернуть всех покупателей из базы данных (активных).
Вернуть одного покупателя из базы данных по его идентификатору (если он активен).
Изменить одного покупателя в базе данных по его идентификатору.
Удалить покупателя из базы данных по его идентификатору.
Удалить покупателя из базы данных по его имени.
Восстановить удалённого покупателя в базе данных по его идентификатору.
Вернуть общее количество покупателей в базе данных (активных).
Вернуть стоимость корзины покупателя по его идентификатору (если он активен).
Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
Удалить товар из корзины покупателя по их идентификаторам
Полностью очистить корзину покупателя по его идентификатору (если он активен)
 */

import app.domain.Customer;
import app.domain.Product;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository = new CustomerRepository();
    private final ProductService productService =  ProductService.getInstance();

    public Customer save(Customer customer) {
        if (customer == null) {
            throw new CustomerSaveException("Покупатель не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Имя покупателя не должно быть пустым");
        }
        customer.setActive(true);
        return repository.save(customer);
    }

    public List<Customer> getAllActiveCustomers() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
    }

    public Customer getActiveCustomerById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }
    public void update(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new CustomerUpdateException("Имя покупателя не может быть пустым");
        }
        repository.update(id, newName);
    }
    public void deleteById(Long id) {
        Customer customer = getActiveCustomerById(id);
        customer.setActive(false);
    }
    public void deleteByName(String name) {
        getAllActiveCustomers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }
    public void restoreById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        customer.setActive(true);
    }
    public int getActiveCustomersNumber() {
        return getAllActiveCustomers().size();
    }
    public double getCustomersCartTotalCost(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .sum();
    }
    public double getCustomersCartAveragePrice(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }
    public void addProductToCustomersCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomerById(customerId);
        Product product = productService.getActiveProductById(productId);
        customer.getCart().add(product);
    }
    public void removeProductFromCustomersCart(Long customerId, Long productId) {
//        Customer customer = getActiveCustomerById(customerId);
//        customer.getCart().removeIf(x -> x.getId().equals(productId));

        Customer customer = getActiveCustomerById(customerId);
        Product product = productService.getActiveProductById(productId);
        customer.getCart().remove(product);
    }
    public void clearCustomersCart(Long customerId) {
        Customer customer = getActiveCustomerById(customerId);
        customer.getCart().clear();
    }
}
