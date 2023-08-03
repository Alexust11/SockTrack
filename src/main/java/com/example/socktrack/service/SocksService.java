package com.example.socktrack.service;

import com.example.socktrack.model.Operation;
import com.example.socktrack.model.Socks;
import com.example.socktrack.repository.SocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocksService {

    @Autowired
    private SocksRepository socksRepository;

    public List<Socks> getAllSocks() {
        return socksRepository.findAll();
    }

    public Optional<Socks> getSocksById(Long id) {
        return socksRepository.findById(id);
    }

    public Socks createSocks(Socks socks) {
        return socksRepository.save(socks);
    }

    public Optional<Socks> updateSocks(Long id, Socks updatedSocks) {
        Optional<Socks> socksOptional = socksRepository.findById(id);
        if (socksOptional.isPresent()) {
            Socks existingSocks = socksOptional.get();
            existingSocks.setColor(updatedSocks.getColor());
            existingSocks.setCottonPart(updatedSocks.getCottonPart());
            existingSocks.setQuantity(updatedSocks.getQuantity());
            return Optional.of(socksRepository.save(existingSocks));
        } else {
            return Optional.empty();
        }
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
}
