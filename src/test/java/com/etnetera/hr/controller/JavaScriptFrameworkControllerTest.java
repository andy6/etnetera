package com.etnetera.hr.controller;

import com.etnetera.hr.EntityCreator;
import com.etnetera.hr.data.HypeLevelEnum;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JavaScriptFrameworkControllerTest {

    @Autowired
    private EntityCreator entityCreator;

    @Autowired
    private JavaScriptFrameworkController controller;

    @MockBean
    private JavaScriptFrameworkService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void givenSetOfFrameworks_whenCallFrameworksMethod_thenCreatedFrameworksAndOkStatusAreReceived() throws Exception {
        Set<JavaScriptFrameworkDto> frameworkDtoSet = Stream.of(
                entityCreator.createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2010-10-01")),
                entityCreator.createFrameworkDto("React.js", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2014-05-30")),
                entityCreator.createFrameworkDto("Vue.js", HypeLevelEnum.GOOD, simpleDateFormat.parse("2018-09-21")))
                .collect(Collectors.toSet());

        when(service.findAll()).thenReturn(frameworkDtoSet);

        ResponseEntity<Iterable<JavaScriptFrameworkDto>> controllerResponse = controller.frameworks();

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
        assertEquals("Body of response must the same.", controllerResponse.getBody(), frameworkDtoSet);
    }

    @Test
    public void givenSetOfFrameworks_whenCallSearchMethod_thenFrameworksAndOkStatusAreReceived() throws Exception {
        Set<JavaScriptFrameworkDto> frameworkDtoSet = Stream.of(
                entityCreator.createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2010-10-01")),
                entityCreator.createFrameworkDto("React.js", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2014-05-30")),
                entityCreator.createFrameworkDto("Vue.js", HypeLevelEnum.GOOD, simpleDateFormat.parse("2018-09-21")))
                .collect(Collectors.toSet());

        when(service.search(anyString(), anyString(), any())).thenReturn(frameworkDtoSet);

        ResponseEntity<Iterable<JavaScriptFrameworkDto>> controllerResponse = controller
                .search("framework-name", "framework-version", simpleDateFormat.parse("2014-05-30"));

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
        assertEquals("Body of response must be the same.", controllerResponse.getBody(), frameworkDtoSet);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNothing_whenCallSearchMethod_thenThrowException() {
        when(service.search(anyString(), anyString(), any())).thenThrow(JavaScriptFrameworkNotFoundException.class);

        ResponseEntity<Iterable<JavaScriptFrameworkDto>> controllerResponse = controller
                .search("framework-name", "framework-version", new Date());

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenFramework_whenCallFindByIdMethod_thenCreatedFrameworkAndOkStatusAreReceived() throws Exception {
        JavaScriptFrameworkDto createdFramework = entityCreator
                .createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("11-12-2023"));

        when(service.findById(anyLong())).thenReturn(createdFramework);

        ResponseEntity<JavaScriptFrameworkDto> controllerResponse = controller.findById(1L);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
        assertEquals("Body of response must be the same.", controllerResponse.getBody(), createdFramework);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNoFramework_whenCallFindByIdMethod_thenThrowException() {
        when(service.findById(anyLong())).thenThrow(new JavaScriptFrameworkNotFoundException());

        ResponseEntity<JavaScriptFrameworkDto> controllerResponse = controller.findById(1L);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenFramework_whenCallCreateMethod_thenCreatedFrameworkAndOkStatusAreReceived() throws Exception {
        JavaScriptFrameworkDto createdFramework = entityCreator
                .createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("11-12-2023"));

        when(service.create(any())).thenReturn(createdFramework);

        ResponseEntity<JavaScriptFrameworkDto> controllerResponse = controller.create(createdFramework);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
        assertEquals("Body of response must be the same.", controllerResponse.getBody(), createdFramework);
    }

    @Test
    public void givenFramework_whenCallUpdateMethod_thenUpdatedFrameworkAndOkStatusAreReceived() throws Exception {
        JavaScriptFrameworkDto updatedFramework = entityCreator
                .createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2023-11-12"));

        when(service.update(anyLong(), any())).thenReturn(updatedFramework);

        ResponseEntity<JavaScriptFrameworkDto> controllerResponse = controller.update(1L, updatedFramework);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.OK);
        assertEquals("Body of response must be the same.", controllerResponse.getBody(), updatedFramework);
    }

    @Test
    public void givenFramework_whenCallDeleteByIdMethod_thenNoContentStatusIsReceived() throws Exception {
        JavaScriptFrameworkDto createdFramework = entityCreator
                .createFrameworkDto("AngularJS", HypeLevelEnum.ENJOY, simpleDateFormat.parse("2023-01-12"));

        when(service.findById(anyLong())).thenReturn(createdFramework);

        ResponseEntity<Void> controllerResponse = controller.deleteById(1L);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNonExistingFramework_whenCallDeleteByIdMethod_thenThrowException() {
        willThrow(new JavaScriptFrameworkNotFoundException()).given(service).deleteById(anyLong());

        ResponseEntity<Void> controllerResponse = controller.deleteById(10L);

        assertEquals("HTTP status of response is incorrect.", controllerResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}