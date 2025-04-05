package com.example.techtopstore.controller;

import com.example.techtopstore.model.Category;
import com.example.techtopstore.model.Product;
import com.example.techtopstore.service.CartService;
import com.example.techtopstore.service.CategoryService;
import com.example.techtopstore.service.ProductService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes("cartCount") // Lưu trữ cartCount trong session
public class HomeController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<Category> getCategoryList() {
        return categoryService.getAllCateories();
    }

    @ModelAttribute("cartCount")
    public int getCartCount() {
        return cartService.getTotalCartItems();
    }

    @GetMapping
    public String home(Model model) {
        List<Product> featuredProducts = productService.getTopProducts(8);
        model.addAttribute("featuredProducts", featuredProducts);
        return "index";
    }

    @GetMapping("shop")
    public String shop(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "sortType", required = false) String sortType,
            Model model) {

        List<Product> filteredProducts;

        if (categoryId != null && categoryId > 0) {
            filteredProducts = productService.getProductsByCategory(categoryId);
        } else {
            filteredProducts = productService.getAllProducts();
        }

        // Lọc theo khoảng giá
        if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
            filteredProducts = productService.getProductsByPriceRange(minPrice, maxPrice);
        }

        // Sắp xếp danh sách sản phẩm
        if (sortType != null && !sortType.equals("default")) {
            filteredProducts = productService.getSortedProducts(sortType, 12);
        }

        model.addAttribute("filteredProducts", filteredProducts);
        return "shop";
    }


    @GetMapping("cart")
    public String viewCart(Model model) {
        List<Product> cartItems = cartService.getCartItems();
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartCount", cartService.getTotalCartItems()); // Cập nhật số lượng giỏ hàng
        return "cart";
    }

    @GetMapping("cart/add/{id}")
    public String addToCart(@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            cartService.addProductToCart(product);
        }
        return "redirect:/cart";
    }

    @PostMapping("cart/add/{id}")
    public String addToCartWithQuantity(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Product product = productService.getProductById(id);
        if (product != null) {
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
}
