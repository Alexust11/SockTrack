package com.example.socktrack.repository;

import com.example.socktrack.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocksRepository extends JpaRepository<Socks, Long>{}
