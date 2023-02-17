package com.github.smuddgge.results.types;

import com.github.smuddgge.results.Result;
import com.github.smuddgge.results.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check if the
 * value is an instance of another class
 */
public class ResultInstanceOf implements Result {

    /**
     * The class used to check the instance of the value
     */
    private final Class object;

    /**
     * Used to create a new {@link ResultInstanceOf}
     *
     * @param object The class
     * @param <T>    The type of class
     */
    public <T> ResultInstanceOf(Class<T> object) {
        this.object = object;
    }

    @Override
    public boolean check(Object value) {
        return object.isAssignableFrom(value.getClass());
    }
}
