package com.example.socktrack.controller;

import com.example.socktrack.model.Socks;
import com.example.socktrack.service.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socks")
public class SocksController {
    @Autowired
    private SocksService socksService;

    @PostMapping("/income")
    public ResponseEntity<String> addSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null || socks.getCottonPart() == null || socks.getQuantity() == null) {
            return ResponseEntity.badRequest().body("Missing or invalid parameters");
        }

        try {
            socksService.createSocks(socks);
            return ResponseEntity.ok("Socks added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
