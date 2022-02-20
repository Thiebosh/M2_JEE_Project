package com.videoGamesWeb.vgcore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
@DiscriminatorColumn(name="product_type",
discriminatorType = DiscriminatorType.INTEGER)
public abstract class Product {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "rating")
    private float rating;

    @Column(name = "img")
    private String img;

    @Column(name = "release_date")
    private Date releaseDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany //TODO à lier avec panier plutôt ?
    @JoinTable(name="orders_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name="order_id"))
    List<Order> orders;
}
