package com.etnetera.hr.util;

import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import java.util.Collections;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestPreconditionsTest {

    public RestPreconditionsTest() {
    }

    @Test
    public void givenIdentifier_whenIdentifierIsCorrect_thenReturnIdentifier() {
        Long identifier = RestPreconditions.checkIdentifier(1L);

        assertEquals(identifier, Long.valueOf(1L));
    }

    @Test(expected = JavaScriptFrameworkNotFoundException.class)
    public void givenIdentifier_whenIdentifierIsInCorrect_thenThrowException() {
        RestPreconditions.checkIdentifier(0);
    }

    @Test
    public void givenValue_whenValueIsNotEmpty_thenReturnValue() {
        String value = RestPreconditions.checkFound("non-empty-value");

        assertEquals(value, "non-empty-value");
    }

    @Test(expected = JavaScriptFrameworkNotFoundException.class)
    public void givenValue_whenValueIsEmpty_thenThrowException() {
        RestPreconditions.checkFound(null);
    }

    @Test
    public void givenCollection_whenCollectionIsNotEmpty_thenReturnCollection() {
        Set<String> simpleSet = Collections.singleton("value");
        Set<String> checkedSet = RestPreconditions.checkFound(simpleSet);

        assertEquals(checkedSet, simpleSet);
    }

    @Test(expected = JavaScriptFrameworkNotFoundException.class)
    public void givenCollection_whenCollectionIsEmpty_thenThrowException() {
        RestPreconditions.checkFound(Collections.emptyList());
    }
}