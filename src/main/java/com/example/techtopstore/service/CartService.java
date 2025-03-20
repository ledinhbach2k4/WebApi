package com.example.techtopstore.service;

import com.example.techtopstore.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final List<Product> cart = new ArrayList<>();

    public void addProductToCart(Product product) {
        boolean exists = cart.stream().anyMatch(p -> p.getId() == product.getId());
        if (!exists) {
            product.setQuantity(1);
            cart.add(product);
        }
    }

    public void updateProductQuantity(int productId, int quantity) {
        for (Product item : cart) {
            if (item.getId() == productId) {
                item.setQuantity(quantity);
                break;
            }
        }
    }

    public void removeProductFromCart(int productId) {
        cart.removeIf(item -> item.getId() == productId);
    }

    public List<Product> getCartItems() {
        return cart;
    }
}
