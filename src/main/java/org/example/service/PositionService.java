package org.example.service;

import org.example.model.Position;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Transactional
    public List<Position> getAllPositions() {
        return positionRepository.getAll();
    }

    @Transactional
    public boolean savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Transactional
    public Optional<Position> getPosition(Long id) {
        return positionRepository.get(id);
    }

    @Transactional
    public void updatePosition(Position position) {
        positionRepository.update(position);
    }

    @Transactional
    public void deletePosition(Long id) {
        positionRepository.delete(id);
    }


}
