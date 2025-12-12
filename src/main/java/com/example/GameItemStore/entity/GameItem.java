package com.example.GameItemStore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GameItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    private boolean isSold = false;
}