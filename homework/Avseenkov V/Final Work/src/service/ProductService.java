package service;

import bean.Product;
import interfaces.ProductInterface;
import stat.Messages;

import static service.UserInput.*;

import java.math.BigDecimal;
import java.util.List;

import static database.InMemoryDataBase.dataBase;

public class ProductService implements ProductInterface {
    @Override
    public void setItem() {
        Product product = new Product(userInputName(), userInputBigDecimalPrice(), userInputEnum());
        if (checkCorrectUserInput(product)) {
            dataBase.add(product);
            System.out.println("\n" + Messages.PRODUCT_ADDED);
        }
    }

    @Override
    public void editItemPrice(Long id, BigDecimal newPrice) {
        if (checkIfExistID(id)) {
            dataBase.stream()
                    .filter(i -> i.getId().equals(id))
                    .forEach(i -> i.setPrice(newPrice));
        }
    }

    @Override
    public void setItemDiscount(Long id, BigDecimal newDiscount) {
        if (checkIfExistID(id)) {
            dataBase.stream()
                    .filter(i -> i.getId().equals(id))
                    .forEach(i -> i.setDiscount(newDiscount));
        }
    }

    @Override
    public void editItemDescription(Long id, String description) {
        dataBase.stream()
                .filter(i -> i.getId().equals(id))
                .forEach(i -> i.setDescription(description));
    }

    @Override
    public void removeItemById(Long id) {
        if (checkIfExistID(id)) {
            dataBase.removeIf(entry -> entry.getId().compareTo(id) == 0);
            System.out.println(Messages.SUCSESSFULLY_REMOVED);
        }
    }

    @Override
    public Product getProductById(Long id) {
        for (Product item : dataBase) {
            if (item.getId().compareTo(id) == 0) {
                if (item != null) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> getListOfProducts() {
        return dataBase;
    }

    private boolean checkCorrectUserInput(Product product) {
        if (product.getName() == null) {
            System.out.println(Messages.INCORRECT_NAME);
            return false;
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(0)) < 0) {
            System.out.println(Messages.INCORRECT_PRICE);
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfExistID(Long id) {
        if (dataBase.stream().anyMatch(entry -> entry.getId().compareTo(id) == 0)) {
            return true;
        } else {
            System.err.println(Messages.NO_ITEM);
            return false;
        }
    }
}
