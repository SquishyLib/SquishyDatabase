package com.github.smuddgge.utility.results.types;

import com.github.smuddgge.utility.results.Result;
import com.github.smuddgge.utility.results.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check
 * if a result is null
 */
public class ResultNull implements Result {

    @Override
    public boolean check(Object value) {
        return value == null;
    }
}
