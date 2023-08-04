package com.example.socktrack.controller;

import com.example.socktrack.model.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.service.SocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }
    @PostMapping("/income")
    public ResponseEntity<String> addSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null || socks.getCottonPart() == null || socks.getQuantity() == null) {
            return ResponseEntity.badRequest().body("Missing or invalid parameters");
        }
        if (socks.getCottonPart() < 0 || socks.getCottonPart() > 100) {
            return ResponseEntity.badRequest().body("Invalid CottonPart value");
        }
        if (socks.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("Invalid Quantity value");
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
    @PostMapping("/outcome")
    public ResponseEntity<String> decreaseSocksQuantity(@RequestParam String color, @RequestParam Integer cottonPart, @RequestParam Integer quantity) {
        // Проверяем наличие всех необходимых параметров запроса
        if (color == null || cottonPart == null || quantity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing request parameters");
        }
        // Проверяем, что значение параметра "quantity" является положительным числом и CotonPart в рамках от 0 до 100
        if (quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid quantity parameter");
        }
        if (cottonPart < 0 || cottonPart> 100) {
            return ResponseEntity.badRequest().body("Invalid CottonPart value");
        }

        // Получаем список носков с учетом заданных условий
        List<Socks> socksList = socksService.getSocksByColorAndCottonPart(color, cottonPart);



        // Вычисляем общее количество носков в полученном списке
        int totalQuantity = socksList.stream().mapToInt(Socks::getQuantity).sum();

        // Проверяем, что общее количество носков достаточно
        if (totalQuantity < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough socks available");
        }

        // Уменьшаем количество носков
        int remainingQuantity = quantity;
        for (Socks socks : socksList) {
            int currentQuantity = socks.getQuantity();

            if (remainingQuantity <= currentQuantity) {
                // Остаток количества носков после вычитания больше нуля
                socks.setQuantity(currentQuantity - remainingQuantity);
                socksService.saveSocks(socks);
                break;
            } else if (remainingQuantity > currentQuantity) {
                // Остаток количества носков после вычитания отрицателен
                remainingQuantity -= currentQuantity;
                socksService.deleteSocksAsObject(socks);
            }
        }

        return ResponseEntity.ok("Socks successfully decreased");
    }
    @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteSocksById(@PathVariable Long id) {
        socksService.deleteSocks(id);
        return ResponseEntity.ok("Socks deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSocksById(@PathVariable Long id, @RequestBody Socks socks) {
        socksService.updateSocksById(id, socks);
        return ResponseEntity.ok("Socks updated successfully");
    }
}
