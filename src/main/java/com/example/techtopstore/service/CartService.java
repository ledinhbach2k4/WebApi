package com.example.techtopstore.service;

import com.example.techtopstore.model.Order;
import com.example.techtopstore.model.OrderItem;
import com.example.techtopstore.model.Product;
import com.example.techtopstore.model.User;
import com.example.techtopstore.repository.OrderRepository;
import com.example.techtopstore.repository.OrderItemRepository;
import com.example.techtopstore.repository.ProductRepository;
import com.example.techtopstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserRepository userRepository; // Thêm UserRepository

    private final List<Product> cart = new ArrayList<>();

    public CartService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

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

    @Transactional
    public Order checkout(int userId) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống!");
        }

        // Lấy thông tin người dùng
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với ID: " + userId));

        // Tạo mã đơn hàng duy nhất
        String orderCode = "ORD-" + UUID.randomUUID().toString().substring(0, 8);

        // Tạo đối tượng Order và lưu vào database
        double totalPrice = 0;
        Order order = new Order(orderCode, "pending", user, totalPrice);

        // Thêm các OrderItem vào danh sách orderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (Product product : cart) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Liên kết Order với OrderItem
            orderItem.setProduct(product); // Liên kết Product với OrderItem
            orderItem.setQuantity(product.getQuantity());
            orderItem.setPrice(product.getPrice());

            // Tính tổng giá trị đơn hàng
            totalPrice += product.getPrice() * product.getQuantity();
            orderItems.add(orderItem);
        }

        // Gán danh sách OrderItems vào Order
        order.setOrderItems(orderItems);

        // Lưu Order và các OrderItems
        order = orderRepository.save(order); // Lưu Order (OrderItems cũng sẽ được lưu nhờ Cascade)

        // Cập nhật tổng giá trị đơn hàng vào Order
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);  // Lưu lại Order với tổng giá trị

        // Xóa giỏ hàng
        cart.clear();

        return order;
    }

    public int getTotalCartItems() {
        return cart.stream().mapToInt(Product::getQuantity).sum();
    }

}
