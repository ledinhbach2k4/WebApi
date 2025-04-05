package com.example.techtopstore.controller.api;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.model.Product;
import com.example.techtopstore.service.OrderItemService;
import com.example.techtopstore.service.OrderService;
import com.example.techtopstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ApiCartController {
    private static final Logger logger = LoggerFactory.getLogger(ApiCartController.class);

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<OrderItem> addToCart(
            @RequestParam("product_id") int productId,
            @RequestParam("order_id") int orderId,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") double price) {
        logger.info("Received request to add to cart: productId={}, orderId={}, quantity={}, price={}",
                productId, orderId, quantity, price);

        // Kiểm tra đầu vào
        if (quantity <= 0 || price < 0) {
            logger.warn("Invalid input: quantity={} or price={} is invalid", quantity, price);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // Lấy thông tin order
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            logger.warn("Order not found for orderId: {}", orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Lấy thông tin product
        Product product = productService.getProductById(productId);
        if (product == null) {
            logger.warn("Product not found for productId: {}", productId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Kiểm tra giá
        if (price != product.getPrice()) {
            logger.warn("Price mismatch for productId: {}, expected: {}, provided: {}",
                    productId, product.getPrice(), price);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            OrderItem existingItem = orderItems.stream()
                    .filter(item -> item.getProduct().getId() == productId)
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                // Nếu đã có, cập nhật số lượng
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                orderItemService.updateOrderItem(existingItem.getId(), existingItem);
                logger.info("Updated existing item in cart: productId={}, orderId={}", productId, orderId);
                return new ResponseEntity<>(existingItem, HttpStatus.OK);
            } else {
                // Nếu chưa có, tạo mới
                OrderItem orderItem = new OrderItem(order, product, quantity, price);
                orderItemService.createOrderItem(orderItem);
                logger.info("Added new item to cart: productId={}, orderId={}", productId, orderId);
                return new ResponseEntity<>(orderItem, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error adding/updating item in cart: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/{orderId}")
    public ResponseEntity<List<OrderItem>> getCartItems(@PathVariable("orderId") int orderId) {
        logger.info("Received request to get cart items for orderId: {}", orderId);

        // Lấy danh sách OrderItem
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        if (orderItems == null || orderItems.isEmpty()) {
            logger.warn("No items found for orderId: {}", orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        logger.info("Successfully retrieved {} items for orderId: {}", orderItems.size(), orderId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getCartRoot() {
        logger.info("Accessed root cart endpoint");
        return new ResponseEntity<>("Welcome to the cart API", HttpStatus.OK);
    }
}