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
}
