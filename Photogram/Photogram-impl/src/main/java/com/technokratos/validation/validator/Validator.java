package com.technokratos.validation.validator;

public interface Validator<T> {

    void validate(T t) throws RuntimeException;
}
