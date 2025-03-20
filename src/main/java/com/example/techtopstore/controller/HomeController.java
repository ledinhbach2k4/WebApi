package com.example.techtopstore.controller;

import com.example.techtopstore.model.Category;
import com.example.techtopstore.model.Product;
import com.example.techtopstore.service.CartService;
import com.example.techtopstore.service.CategoryService;
import com.example.techtopstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model model) {
        List<Product> allProducts = productService.getAllProducts();
        List<Product> featuredProducts = allProducts.size() > 8 ? allProducts.subList(0, 8) : allProducts;

        loadCategoryList(model);
        model.addAttribute("featuredProducts", featuredProducts);
        return "index";
    }

    @GetMapping("shop")
    public String shop(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "shop";
    }

    /* Cart Controller */
    @GetMapping("cart")
    public String viewCart(Model model) {
        List<Product> cartItems = cartService.getCartItems();
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    // Giữ nguyên endpoint GET cũ
    @GetMapping("cart/add/{id}")
    public String addToCart(@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            cartService.addProductToCart(product);
        }
        return "redirect:/cart";
    }

    // Thêm endpoint POST để xử lý form với số lượng
    @PostMapping("cart/add/{id}")
    public String addToCartWithQuantity(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Product product = productService.getProductById(id);
        if (product != null) {
            // Đặt số lượng cho sản phẩm trước khi thêm vào giỏ hàng
            product.setQuantity(quantity);
            cartService.addProductToCart(product);
        }
        return "redirect:/cart";
    }

    @PostMapping("cart/update/{id}")
    public String updateCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        cartService.updateProductQuantity(id, quantity);
        return "redirect:/cart";
    }

    @GetMapping("cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") int id) {
        cartService.removeProductFromCart(id);
        return "redirect:/cart";
    }

    /* Checkout Controller */
    @GetMapping("checkout")
    public String checkout(Model model) {
        List<Product> cartItems = cartService.getCartItems();
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        if (cartItems.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống. Hãy thêm sản phẩm trước khi thanh toán.");
            return "cart";
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

    @GetMapping("contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("products/{slug}-{id}.html")
    public String viewProduct(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/";
        }
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("category/{slug}-{id}.html")
    public String viewCategory(@PathVariable("id") int id, Model model) {
        List<Product> productList = productService.getProductsByCategory(id);
        if (productList == null) {
            return "redirect:/";
        }
        model.addAttribute("productList", productList);
        return "category";
    }

    @GetMapping("search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Product> productList = productService.searchProducts(keyword);
        if (productList == null) {
            return "redirect:/";
        }
        model.addAttribute("productList", productList);
        return "search";
    }

    private void loadCategoryList(Model model) {
        List<Category> categoryList = categoryService.getAllCateories();
        model.addAttribute("categoryList", categoryList);
    }
}