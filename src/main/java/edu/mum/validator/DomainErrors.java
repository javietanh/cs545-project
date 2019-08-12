package edu.mum.validator;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DomainErrors {
    private String errorType;
    private List<DomainError> errorFields = new ArrayList<>();

    public void addErrorField(String path, String message) {
        this.errorFields.add(new DomainError(path, message));
    }
}
