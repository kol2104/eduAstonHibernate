package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Project {
    private Integer id;
    private String name;
    private String client;

    public Project(Integer id) {
        this.id = id;
    }
}
