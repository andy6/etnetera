package com.etnetera.hr.util;

import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import java.util.stream.StreamSupport;


/**
 * Verifying arguments incoming from controllers.
 */
public final class RestPreconditions {

    /**
     * Check if the identifier is natural number, otherwise throw exception.
     *
     * @param id identifier
     * @return identifier has value if it is natural number
     * @throws JavaScriptFrameworkNotFoundException if identifier is not natural number
     */
    public static long checkIdentifier(final long id) {
        if (id < 1) {
            throw new JavaScriptFrameworkNotFoundException("Identifier does not exist.");
        }

        return id;
    }

    /**
     * Check if some value is not null, otherwise throw exception.
     *
     * @param value for not null check
     * @throws JavaScriptFrameworkNotFoundException if value is null
     */
    public static <T> T checkFound(final T value) {
        if (value == null) {
            throw new JavaScriptFrameworkNotFoundException("Value must not be null.");
        }

        return value;
    }

    /**
     * Check if some iterable collection is not empty, otherwise throw exception.
     *
     * @param collection for non empty check
     * @throws JavaScriptFrameworkNotFoundException if value is not empty check
     */
    public static <T extends Iterable<?>> T checkFound(final T collection) {
        if (collection == null || StreamSupport.stream(collection.spliterator(), false).count() < 1) {
            throw new JavaScriptFrameworkNotFoundException("Value must not be empty");
        }

        return collection;
    }
}
