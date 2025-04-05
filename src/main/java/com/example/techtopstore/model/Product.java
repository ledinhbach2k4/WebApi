package com.example.techtopstore.model;

import com.example.techtopstore.util.SlugUtil;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Convert;
import jakarta.persistence.AttributeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "thumbnail_extra", columnDefinition = "TEXT")
    @Convert(converter = JsonListConverter.class)
    @JsonProperty("thumbnail_extra")
    private List<String> thumbnailExtra;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "rating")
    private float rating;

    @Column(name = "sold_count")
    private int soldCount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor mặc định
    public Product() {
        this.thumbnailExtra = new ArrayList<>();
        this.rating = 0.0f;
        this.soldCount = 0;
    }

    // Constructor đầy đủ
    public Product(String name, String description, String thumbnail, List<String> thumbnailExtra, Double price, Integer quantity, float rating, int soldCount, Category category) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtra = (thumbnailExtra != null) ? thumbnailExtra : new ArrayList<>();
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.soldCount = soldCount;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    // Getters và Setters
    public String getSlug() { return SlugUtil.toSlug(name); }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public List<String> getThumbnailExtra() { return thumbnailExtra; }  // Getter method is here
    public void setThumbnailExtra(List<String> thumbnailExtra) { this.thumbnailExtra = (thumbnailExtra != null) ? thumbnailExtra : new ArrayList<>(); }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public int getSoldCount() { return soldCount; }
    public void setSoldCount(int soldCount) { this.soldCount = soldCount; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

// Converter để lưu List<String> dưới dạng JSON trong cột TEXT
@Converter
class JsonListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to list", e);
        }
    }
}