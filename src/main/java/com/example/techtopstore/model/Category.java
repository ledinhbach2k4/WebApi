package com.example.techtopstore.model;

import com.example.techtopstore.util.SlugUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    // Constructor, Getters, Setters
    public Category() {}

    public Category(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }
    public String getSlug() { return SlugUtil.toSlug(name); }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}