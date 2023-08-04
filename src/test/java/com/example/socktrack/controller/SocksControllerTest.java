package com.example.socktrack.controller;

import com.example.socktrack.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.service.SocksService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SocksControllerTest {
    @Mock
    private SocksService socksService;

    @InjectMocks
    private SocksController socksController;

    public SocksControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addSocks() {
        Socks socks = new Socks();
        socks.setColor("red");
        socks.setCottonPart(50);
        socks.setQuantity(10);

        ResponseEntity<String> response = socksController.addSocks(socks);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Socks added successfully", response.getBody());

        verify(socksService, times(1)).createSocks(socks);
    }

    @Test
    void getTotalQuantity() {
        String color = "red";
        Operation operation = Operation.EQUAL;
        Integer cottonPart = 50;

        int totalQuantity = 20;
        when(socksService.getTotalQuantity(color, operation, cottonPart)).thenReturn(totalQuantity);

        ResponseEntity<String> response = socksController.getTotalQuantity(color, operation, cottonPart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.valueOf(totalQuantity), response.getBody());

        verify(socksService, times(1)).getTotalQuantity(color, operation, cottonPart);
    }

    @Test
    void decreaseSocksQuantity() {
        String color = "red";
        Integer cottonPart = 50;
        Integer quantity = 5;

        List<Socks> socksList = new ArrayList<>();
        Socks socks1 = new Socks();
        socks1.setColor("red");
        socks1.setCottonPart(50);
        socks1.setQuantity(10);
        socksList.add(socks1);
        Socks socks2 = new Socks();
        socks2.setColor("red");
        socks2.setCottonPart(50);
        socks2.setQuantity(15);
        socksList.add(socks2);

        when(socksService.getSocksByColorAndCottonPart(color, cottonPart)).thenReturn(socksList);

        ResponseEntity<String> response = socksController.decreaseSocksQuantity(color, cottonPart, quantity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Socks quantity decreased successfully", response.getBody());

        verify(socksService, times(1)).getSocksByColorAndCottonPart(color, cottonPart);
        verify(socksService, times(1)).saveSocks(socks1);
        verify(socksService, times(1)).saveSocks(socks2);
    }
    @Test
    void deleteSocksById() {
        SocksController socksController = new SocksController(socksService);
        Long id = 1L;

        ResponseEntity<String> response = socksController.deleteSocksById(id);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Socks deleted successfully", response.getBody());

        verify(socksService, times(1)).deleteSocks(id);
    }
    @Test
    void updateSocksById() {
        SocksController socksController = new SocksController(socksService);
        Long id = 1L;
        Socks socks = new Socks("red", 50, 10);

        ResponseEntity<String> response = socksController.updateSocksById(id, socks);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Socks updated successfully", response.getBody());

        verify(socksService, times(1)).updateSocksById(id, socks);
    }
}