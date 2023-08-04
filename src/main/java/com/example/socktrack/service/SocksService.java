package com.example.socktrack.service;

import com.example.socktrack.exception.SocksNotFoundException;
import com.example.socktrack.model.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.repository.SocksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocksService {


    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }



    public Socks createSocks(Socks socks) {
        return socksRepository.save(socks);
    }



    public void deleteSocks(Long id) {
        socksRepository.deleteById(id);
    }

    public int getTotalQuantity(String color, Operation operation, Integer cottonPart) {
        List<Socks> socksList = socksRepository.findByColor(color);

        int totalQuantity = 0;

        for (Socks socks : socksList) {
            if (operation == Operation.MORE_THAN && socks.getCottonPart() > cottonPart) {
                totalQuantity += socks.getQuantity();
            } else if (operation == Operation.LESS_THAN && socks.getCottonPart() < cottonPart) {
                totalQuantity += socks.getQuantity();
            } else if (operation == Operation.EQUAL && socks.getCottonPart() == cottonPart) {
                totalQuantity += socks.getQuantity();
            }
        }

        return totalQuantity;
    }

    public List<Socks> getSocksByColorAndCottonPart(String color, Integer cottonPart) {
        return socksRepository.findByColorAndCottonPart(color, cottonPart);
    }

    public void saveSocks(Socks socks) {
        socksRepository.save(socks);
    }

    public void deleteSocksAsObject(Socks socks) {
        socksRepository.delete(socks);
    }

    public void updateSocksById(Long id, Socks socks) {
        Socks existingSocks = socksRepository.getSocksById(id);
        if (existingSocks == null) {
            throw new SocksNotFoundException("Socks with id " + id + " not found");
        }

        existingSocks.setColor(socks.getColor());
        existingSocks.setCottonPart(socks.getCottonPart());
        existingSocks.setQuantity(socks.getQuantity());

        socksRepository.save(existingSocks);
    }
}
