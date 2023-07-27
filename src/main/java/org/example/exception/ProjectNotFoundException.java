package org.example.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id) {
        super(String.format("Project with id = '%s' not found", id));
    }
}
