package com.example.socktrack.model;



import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Data
@Entity
@Table(name="socks")
public class Socks {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @Column(name = "color")
    private String color;

    @Column(name = "cotton_part")
    private Integer cottonPart;

    @Column(name = "quantity")
    private Integer quantity;

    public Socks(String color, Integer cottonPart, Integer quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public Socks(Long id, String color, Integer cottonPart, Integer quantity) {
        this.id = id;
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public Socks() {

    }
}
