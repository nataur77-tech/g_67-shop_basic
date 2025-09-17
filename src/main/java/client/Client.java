package client;

import app.controller.CustomerController;
import app.controller.ProductController;

import java.util.Scanner;

public class Client {

    private static ProductController productController;
    private static CustomerController customerController;
    private static Scanner scanner;

    public static void main(String[] args) {

        // Создаём объекты контроллеров для взаимодействия с приложением
        productController = new ProductController();
        customerController = new CustomerController();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите желаемую операцию:");
            System.out.println("1 - операции с продуктами");
            System.out.println("2 - операции с покупателями");
            System.out.println("0 - выход");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    productOperations();
                    break;
                case "2":
                    customerOperations();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Некорректный ввод!");
                    break;
            }
        }
    }

    public static void productOperations() {
        while (true) {
            try {
                System.out.println("Выберите желаемую операцию с продуктами:");
                System.out.println("1 - сохранить продукт");
                System.out.println("2 - получить все продукты");
                System.out.println("3 - получить продукт по идентификатору");
                System.out.println("4 - изменить продукт");
                System.out.println("5 - удалить продукт по идентификатору");
                System.out.println("6 - удалить продукт по названию");
                System.out.println("7 - восстановить продукт по идентификатору");
                System.out.println("8 - получить количество продуктов");
                System.out.println("9 - получить суммарную стоимость всех продуктов");
                System.out.println("10 - получить среднюю стоимость продукта");
                System.out.println("0 - выход");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Введите название продукта");
                        String title = scanner.nextLine();
                        System.out.println("Введите цену продукта");
                        String price = scanner.nextLine();
                        System.out.println(productController.save(title, price));
                        break;
                    case "2":
                        productController.getAll().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.println("Введите идентификатор продукта");
                        String id = scanner.nextLine();
                        System.out.println(productController.getById(id));
                        break;
                    case "4":
                        System.out.println("Введите идентификатор продукта");
                        id = scanner.nextLine();
                        System.out.println("Введите новую цену продукта");
                        price = scanner.nextLine();
                        productController.update(id, price);
                        break;
                    case "5":
                        System.out.println("Введите идентификатор продукта");
                        id = scanner.nextLine();
                        productController.deleteById(id);
                        break;
                    case "6":
                        System.out.println("Введите название продукта");
                        title = scanner.nextLine();
                        productController.deleteByTitle(title);
                        break;
                    case "7":
                        System.out.println("Введите идентификатор продукта");
                        id = scanner.nextLine();
                        productController.restoreById(id);
                        break;
                    case "8":
                        System.out.println("Количество продуктов - " + productController.getProductsCount());
                        break;
                    case "9":
                        System.out.println("Суммарная стоимость продуктов - " +
                                productController.getProductsTotalCost());
                        break;
                    case "10":
                        System.out.println("Средняя стоимость продукта - " +
                                productController.getProductsAveragePrice());
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Некорректный ввод!");
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void customerOperations() {
        while (true) {
            try {
                System.out.println("Выберите желаемую операцию с покупателями:");
                System.out.println("1 - сохранить покупателя");
                System.out.println("2 - получить всех покупателей");
                System.out.println("3 - получить покупателя по идентификатору");
                System.out.println("4 - изменить покупателя");
                System.out.println("5 - удалить покупателя по идентификатору");
                System.out.println("6 - удалить покупателя по имени");
                System.out.println("7 - восстановить покупателя по идентификатору");
                System.out.println("8 - получить количество покупателей");
                System.out.println("9 - получить стоимость корзины покупателя");
                System.out.println("10 - получить среднюю стоимость продукта в корзине покупателя");
                System.out.println("11 - добавить товар в корзину покупателя");
                System.out.println("12 - удалить товар из корзины покупателя");
                System.out.println("13 - очистить корзину покупателя");
                System.out.println("0 - выход");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Введите имя покупателя");
                        String name = scanner.nextLine();
                        System.out.println(customerController.save(name));
                        break;
                    case "2":
                        customerController.getAll().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.println("Введите идентификатор");
                        String id = scanner.nextLine();
                        System.out.println(customerController.getById(id));
                        break;
                    case "4":
                        System.out.println("Введите идентификатор");
                        id = scanner.nextLine();
                        System.out.println("Введите новое имя покупателя");
                        name = scanner.nextLine();
                        customerController.update(id, name);
                        break;
                    case "5":
                        System.out.println("Введите идентификатор");
                        id = scanner.nextLine();
                        customerController.deleteById(id);
                        break;
                    case "6":
                        System.out.println("Введите имя покупателя");
                        name = scanner.nextLine();
                        customerController.deleteByName(name);
                        break;
                    case "7":
                        System.out.println("Введите идентификатор");
                        id = scanner.nextLine();
                        customerController.restoreById(id);
                        break;
                    case "8":
                        System.out.println("Количество покупателей - " + customerController.getCustomersNumber());
                        break;
                    case "9":
                        System.out.println("Введите идентификатор");
                        id = scanner.nextLine();
                        System.out.println("Стоимость корзины покупателя - " +
                                customerController.getCustomersCartTotalCost(id));
                        break;
                    case "10":
                        System.out.println("Введите идентификатор");
                        id = scanner.nextLine();
                        System.out.println("Средняя цена продукта в корзине - " +
                                customerController.getCustomersCartAveragePrice(id));
                        break;
                    case "11":
                        System.out.println("Введите идентификатор покупателя");
                        String customerId = scanner.nextLine();
                        System.out.println("Введите идентификатор продукта");
                        String productId = scanner.nextLine();
                        customerController.addProductToCustomersCart(customerId, productId);
                        break;
                    case "12":
                        System.out.println("Введите идентификатор покупателя");
                        customerId = scanner.nextLine();
                        System.out.println("Введите идентификатор продукта");
                        productId = scanner.nextLine();
                        customerController.removeProductFromCustomersCart(customerId, productId);
                        break;
                    case "13":
                        System.out.println("Введите идентификатор покупателя");
                        id = scanner.nextLine();
                        customerController.clearCustomersCart(id);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Некорректный ввод!");
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
