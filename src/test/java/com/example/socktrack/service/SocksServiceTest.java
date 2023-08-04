package com.example.socktrack.service;

import com.example.socktrack.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.repository.SocksRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

class SocksServiceTest {
    @Mock
    private SocksRepository socksRepository;

    @InjectMocks
    private SocksService socksService;
    @Test
    void createSocks() {
        Socks socks = new Socks();
        when(socksRepository.save(socks)).thenReturn(socks);
        Socks createdSocks = socksService.createSocks(socks);
        assertEquals(socks, createdSocks);
        verify(socksRepository, times(1)).save(socks);
    }

    @Test
    void deleteSocks() {
        Long id = 1L;
        socksService.deleteSocks(id);
        verify(socksRepository, times(1)).deleteById(id);
    }

    @Test
    void getTotalQuantity() {
        String color = "white";
        Operation operation = Operation.MORE_THAN;
        Integer cottonPart = 50;

        List<Socks> socksList = new ArrayList<>();
        socksList.add(new Socks("white", 60, 10));
        socksList.add(new Socks("white", 40, 5));
        socksList.add(new Socks("black", 50, 8));

        when(socksRepository.findByColor(color)).thenReturn(socksList);

        int totalQuantity = socksService.getTotalQuantity(color, operation, cottonPart);

        assertEquals(10, totalQuantity);
        verify(socksRepository, times(1)).findByColor(color);
    }

    @Test
    void getSocksByColorAndCottonPart() {
        String color = "white";
        Integer cottonPart = 50;

        List<Socks> expectedSocksList = new ArrayList<>();
        expectedSocksList.add(new Socks("white", 50, 10));
        expectedSocksList.add(new Socks("white", 50, 5));

        when(socksRepository.findByColorAndCottonPart(color, cottonPart)).thenReturn(expectedSocksList);

        List<Socks> socksList = socksService.getSocksByColorAndCottonPart(color, cottonPart);

        assertEquals(expectedSocksList, socksList);
        verify(socksRepository, times(1)).findByColorAndCottonPart(color, cottonPart);
    }

    @Test
    void saveSocks() {
        Socks socks = new Socks();
        socksService.saveSocks(socks);
        verify(socksRepository, times(1)).save(socks);
    }

    @Test
    void deleteSocksAsObject() {
        Socks socks = new Socks();
        socksService.deleteSocksAsObject(socks);
        verify(socksRepository, times(1)).delete(socks);

    }

    @Test
    void updateSocksById() {

    }
}