package com.etnetera.hr;

import com.etnetera.hr.data.HypeLevelEnum;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JavaScriptFrameworkTestConfiguration.class)
public class JavaScriptFrameworkTests {

    @Autowired
    private EntityCreator entityCreator;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Run before each test.
     */
    @Before
    public void init() {
    }

    /**
     * Run only once.
     */
    @BeforeClass
    public static void runBeforeAllTestMethods() {
    }

    @Test
    public void givenFrameworkEntity_whenEntityIsCreated_thenNewEntityIsReceived() throws Exception {
        String frameworkName = "inserted-framework-name";

        // insert new framework
        JavaScriptFrameworkDto insertedFramework = insertFramework(frameworkName);

        // returned entity
        assertNotNull("New framework must not be null.", insertedFramework);
        assertEquals("New framework has different name.", frameworkName, insertedFramework.getName());

        // framework already exists
        assertTrue("New framework does not exist yet.", getListOfFrameworks().stream()
                .anyMatch(fr -> frameworkName.equals(fr.getName())));
    }

    @Test
    public void givenFrameworkEntity_whenFrameworkIsSubmitted_thenFrameworkIsReturned() throws Exception {
        JavaScriptFrameworkDto insertedFramework = insertFramework("inserted-framework-name");

        // get framework
        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", insertedFramework.getId()));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenFrameworkWithLongName_whenFrameworkIsSubmitted_thenBadRequestStatusReceived() throws Exception {
        JavaScriptFrameworkDto insertedFramework = insertFramework("inserted-framework-name-with-long-name");

        // get framework
        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.POST, getRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", insertedFramework.getId()));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenFrameworkEntity_whenFrameworkIsDeleted_thenFrameworkIsDeleted() throws Exception {
        // insert new framework
        JavaScriptFrameworkDto insertedFramework = insertFramework("original-framework-name");
        // delete it
        HttpEntity<String> deleteRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.DELETE, deleteRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", insertedFramework.getId()));


        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.NO_CONTENT);
        assertFalse("Deleted framework still exists.", getListOfFrameworks().stream()
                .anyMatch(fr -> insertedFramework.getName().equals(fr.getName())));
    }

    @Test
    public void givenNothing_whenFrameworkIsDeleted_thenThrowsException() {
        HttpEntity<String> deleteRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.DELETE, deleteRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", 10L));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenFrameworkEntity_whenEntityIsUpdated_thenUpdatedEntityIsReceived() throws Exception {
        String frameworkName = "updated-framework-name";

        // insert new framework
        JavaScriptFrameworkDto insertedFramework = insertFramework(frameworkName);

        insertedFramework.setName(frameworkName);
        // returned entity
        restTemplate.put("/{id}", insertedFramework, insertedFramework.getId());

        // framework was updated
        assertTrue("Updated framework does not exist.", getListOfFrameworks().stream()
                .anyMatch(fr -> frameworkName.equals(fr.getName())));
    }

    @Test
    public void givenFrameworkForUpdate_whenFrameworkIsUpdated_thenNotFoundStatusReceived() {
        JavaScriptFrameworkDto framework = entityCreator.createFrameworkDto("framework-for-update", HypeLevelEnum.BAD, new Date());

        HttpEntity<JavaScriptFrameworkDto> updateRequest = new HttpEntity<>(framework, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.PUT, updateRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", 10L));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenGetFrameworksRequest_whenSendRequest_thenListOfFrameworksIsReceived() {
        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto[]> response  = restTemplate.exchange("/frameworks",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto[].class);

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.OK);
        assertEquals("Content type of response is incorrect.", response.getHeaders().getContentType(), MediaType.APPLICATION_JSON_UTF8);
        assertNotNull("Body of response must not be null.", response.getBody());
    }

    @Test
    public void givenGetSearchRequest_whenSendRequest_thenListOfFrameworksIsReceived() throws Exception {
        // insert new framework
        JavaScriptFrameworkDto framework = entityCreator.createFrameworkDto(
                "framework-test-name", HypeLevelEnum.BAD, simpleDateFormat.parse("2010-10-01"));
        HttpEntity<String> insertRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(framework), headers);
        restTemplate.postForObject("/", insertRequest, JavaScriptFrameworkDto.class);

        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto[]> response  = restTemplate.exchange(
                "/search?name={name}&version={version}&deprecated_after={date}",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto[].class,
                Map.of("name","test", "version", "1.1.1-RELEASE", "date", "2010-09-01"));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.OK);
        assertEquals("Content type of response is incorrect.", response.getHeaders().getContentType(), MediaType.APPLICATION_JSON_UTF8);
        assertNotNull("Body of response must not be null.", response.getBody());
    }

    @Test
    public void givenGetSearchRequest_whenSendRequest_thenResponseNotFoundReceived() throws Exception {
        // insert new framework
        JavaScriptFrameworkDto framework = entityCreator.createFrameworkDto(
                "framework-test-name", HypeLevelEnum.BAD, simpleDateFormat.parse("2010-10-01"));
        HttpEntity<String> insertRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(framework), headers);
        restTemplate.postForObject("/", insertRequest, JavaScriptFrameworkDto.class);

        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response = restTemplate.exchange(
                "/search?name={name}",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto.class,
                Map.of("name","not-exists"));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals("Content type of response is incorrect.", response.getHeaders().getContentType(), MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    public void givenFramework_whenUseNonExistentMethod_thenNotAllowedStatusReceived() throws Exception {
        JavaScriptFrameworkDto insertedFramework = insertFramework("inserted-framework-name-with-long-name");

        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto> response  = restTemplate.exchange("/{id}",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto.class,
                Collections.singletonMap("id", insertedFramework.getId()));

        assertEquals("HTTP status of response is incorrect.", response.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private List<JavaScriptFrameworkDto> getListOfFrameworks() {
        HttpEntity<String> getRequest = new HttpEntity<>(null, headers);
        ResponseEntity<JavaScriptFrameworkDto[]> frameworks  = restTemplate.exchange("/frameworks",
                HttpMethod.GET, getRequest, JavaScriptFrameworkDto[].class);

        return Arrays.asList(Objects.requireNonNull(frameworks.getBody()));
    }

    private JavaScriptFrameworkDto insertFramework(String name) throws Exception {
        JavaScriptFrameworkDto framework = entityCreator.createFrameworkDto(name, HypeLevelEnum.BAD, new Date());

        HttpEntity<String> insertRequest = new HttpEntity<>(new ObjectMapper().writeValueAsString(framework), headers);
        return restTemplate.postForObject("/", insertRequest, JavaScriptFrameworkDto.class);
    }
}
