package app.controller;

/*
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
import app.service.ProductService;

import java.util.List;

public class ProductController {

    private final ProductService service = new ProductService();

    //    Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
    public Product save(String title, String price) {
        double numericPrice = Double.parseDouble(price);
        Product product = new Product(title, numericPrice);
        return service.save(product);
    }

    //    Вернуть все продукты из базы данных (активные).
    public List<Product> getAll() {
        return service.getAllActiveProducts();
    }

    //    Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Product getById(String id) {
        long numericId = Long.parseLong(id);
        return service.getActiveProductById(numericId);
    }

    //    Изменить один продукт в базе данных по его идентификатору.
    public void update(String id, String newPrice) {
        long numericId = Long.parseLong(id);
        double numericNewPrice = Double.parseDouble(newPrice);
        service.update(numericId, numericNewPrice);
    }

    //    Удалить продукт из базы данных по его идентификатору.
    public void deleteById(String id) {
        long numericId = Long.parseLong(id);
        service.deleteById(numericId);
    }

    //    Удалить продукт из базы данных по его наименованию.
    public void deleteByTitle(String title) {
        service.deleteByTitle(title);
    }

    //    Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(String id) {
        long numericId = Long.parseLong(id);
        service.restoreById(numericId);
    }

    //    Вернуть общее количество продуктов в базе данных (активных).
    public int getProductsCount() {
        return service.getActiveProductsCount();
    }

    //    Вернуть суммарную стоимость всех продуктов в базе данных (активных).
    public double getProductsTotalCost() {
        return service.getActiveProductsTotalCost();
    }

    //    Вернуть среднюю стоимость продукта в базе данных (из активных).
    public double getProductsAveragePrice() {
        return service.getActiveProductsAveragePrice();
    }
}
