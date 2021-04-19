package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Smoke test makes sure that JavaScriptFrameworkController is loaded.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SmokeTest {

    @Autowired
    private JavaScriptFrameworkController controller;

    @Test
    public void contextLoads() {
        assertNotNull("JavaScriptFrameworkController is not loaded", controller);
    }
}
