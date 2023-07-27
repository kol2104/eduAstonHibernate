package org.example.controller;

import org.example.exception.PositionNotFoundException;
import org.example.model.Position;
import org.example.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/position")
@ControllerAdvice
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/{id}")
    public ResponseEntity getPosition(@PathVariable("id") Long id) {
        Optional<Position> position = positionService.getPosition(id);
        if (position.isPresent()){
            return new ResponseEntity<>(position.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity getAllPositions() {
        return new ResponseEntity<>(positionService.getAllPositions(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity savePosition(@RequestBody Position position) {
        if (positionService.savePosition(position)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity updatePosition(@RequestBody Position position) {
        positionService.updatePosition(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePosition(@PathVariable("id") Long id) {
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({PositionNotFoundException.class})
    public ResponseEntity handleException(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity(errorBody, HttpStatus.NOT_FOUND);
    }
}
