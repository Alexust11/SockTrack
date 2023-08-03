package com.example.socktrack.controller;

import com.example.socktrack.model.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.service.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<String> getTotalQuantity(
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "operation", required = false) Operation operation,
            @RequestParam(value = "cottonPart", required = false) Integer cottonPart) {

        // Проверяем наличие обязательных параметров
        if (color == null || operation == null || cottonPart == null) {
            return ResponseEntity.badRequest().body("Missing parameters");
        }

        try {
            // Вызываем сервисный метод для получения общего количества носков
            int totalQuantity = socksService.getTotalQuantity(color, operation, cottonPart);
            return ResponseEntity.ok(String.valueOf(totalQuantity));
        } catch (IllegalArgumentException e) {
            // Если возникла ошибка валидации параметров, возвращаем HTTP 400
            return ResponseEntity.badRequest().body("Invalid parameters");
        } catch (Exception e) {
            // Если произошла другая ошибка, возвращаем HTTP 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}
