package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAllPositions() {
        return positionRepository.getAll();
    }

    public boolean savePosition(Position position) {
        return positionRepository.save(position);
    }

    public Optional<Position> getPosition(Integer id) {
        return positionRepository.get(id);
    }

    public void updatePosition(Position position) {
        positionRepository.update(position);
    }

    public void deletePosition(Integer id) {
        positionRepository.delete(id);
    }


}
