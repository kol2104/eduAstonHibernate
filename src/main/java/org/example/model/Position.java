package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Position {
    private Integer id;
    private String name;

    public Position(Integer id) {
        this.id = id;
    }
}
