package com.film.library.service;

import com.film.library.model.Customer;
import com.film.library.model.Product;
import com.film.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);

    ShoppingCart deleteItemFromCart(Product product, Customer customer);

    ShoppingCart deleteShoppingCart(ShoppingCart cart);
}
