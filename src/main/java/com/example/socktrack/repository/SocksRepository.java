package com.example.socktrack.repository;

import com.example.socktrack.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocksRepository extends JpaRepository<Socks, Long>{
    List<Socks> findByColor(String color);
}
