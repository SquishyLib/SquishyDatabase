package com.github.smuddgge.utility.results.types;

import com.github.smuddgge.utility.results.Result;
import com.github.smuddgge.utility.results.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check
 * if a result is not null
 */
public class ResultNotNull implements Result {

    @Override
    public boolean check(Object value) {
        return value != null;
    }
}
