package app.service;

/*
Функционал сервиса продуктов.

Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
Вернуть все продукты из базы данных (активные).
Вернуть один продукт из базы данных по его идентификатору (если он активен).
Изменить один продукт в базе данных по его идентификатору.
Удалить продукт из базы данных по его идентификатору.
Удалить продукт из базы данных по его наименованию.
Восстановить удалённый продукт в базе данных по его идентификатору.
Вернуть общее количество продуктов в базе данных (активных).
Вернуть суммарную стоимость всех продуктов в базе данных (активных).
Вернуть среднюю стоимость продукта в базе данных (из активных).
 */

import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository repository = new ProductRepository();


    public Product save(Product product) {
        if (product == null) {
            throw new ProductSaveException("Продукт не может быть null");
        }

        String title = product.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ProductSaveException("Наименование продукта не должно быть пустым");
        }

        if (product.getPrice() < 0) {
            throw new ProductSaveException("Цена продукта не должна быть отрицательной");
        }

        product.setActive(true);
        return repository.save(product);
    }

    public List<Product> getAllActiveProducts() {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .toList();
    }

    public Product getActiveProductById(Long id) {
        Product product = repository.findById(id);

        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }
    public void update(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new ProductUpdateException("Цена продукта не должна быть отрицательной");
        }
        repository.update(id, newPrice);
    }
    public void deleteById(Long id) {
        Product product = getActiveProductById(id);
        product.setActive(false);
    }
    public void deleteByTitle(String title) {
        getAllActiveProducts()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));
    }
    public void restoreById(Long id) {
        Product product = repository.findById(id);

        if ((product == null)) {
            throw new ProductNotFoundException(id);
        }

        product.setActive(true);

    }

    public int getActiveProductsCount() {
        return getAllActiveProducts().size();
    }

    public double getActiveProductsTotalCost() {
//        double sum = 0.0;
//        for (Product product : getAllActiveProducts()) {
//            sum += product.getPrice();
//        }
//        return sum;
        return  getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public double getActiveProductsAveragePrice() {
/*        int productCount = getActiveProductsCount();

        if (productCount == 0) {
            return 0.0;
        }
        return getActiveProductsTotalCost() / productCount;

 */
        return getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }
}
