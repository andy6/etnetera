package com.etnetera.hr.service;

import com.etnetera.hr.converter.JavaScriptFrameworkConverter;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.repository.VersionRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for verifying arguments incoming from controllers.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JavaScriptFrameworkServiceTest {

    @MockBean
    private JavaScriptFrameworkRepository repository;

    @MockBean
    private VersionRepository versionRepository;

    @MockBean
    private JavaScriptFrameworkConverter converter;

    @Autowired
    public JavaScriptFrameworkService service;

    @Test
    public void whenCreateFramework_thenReturnCreatedFramework() {
        JavaScriptFramework framework = mock(JavaScriptFramework.class);
        JavaScriptFrameworkDto frameworkDto = mock(JavaScriptFrameworkDto.class);

        when(converter.fromEntity(any())).thenReturn(frameworkDto);
        when(converter.fromDto(any())).thenReturn(framework);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(framework));
        when(repository.save(framework)).thenReturn(framework);
        when(versionRepository.saveAll(any())).thenReturn(new HashSet<>());

        assertEquals(service.create(frameworkDto), frameworkDto);
    }

    @Test
    public void whenUpdateFramework_thenReturnUpdatedFramework() {
        JavaScriptFramework framework = mock(JavaScriptFramework.class);
        JavaScriptFrameworkDto frameworkDto = mock(JavaScriptFrameworkDto.class);

        when(converter.fromEntity(any())).thenReturn(frameworkDto);
        when(converter.fromDto(any())).thenReturn(framework);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(framework));
        when(repository.save(framework)).thenReturn(framework);

        assertEquals(service.update(1L, frameworkDto), frameworkDto);
    }

    @Test
    public void whenDeleteFramework_thenDoNotThrowException() {
        JavaScriptFramework framework = mock(JavaScriptFramework.class);
        JavaScriptFrameworkDto frameworkDto = mock(JavaScriptFrameworkDto.class);

        when(converter.fromEntity(any())).thenReturn(frameworkDto);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(framework));

        service.deleteById(1L);
    }

    @Test(expected = JavaScriptFrameworkNotFoundException.class)
    public void whenDeleteNonExistentFramework_thenThrowException() {
        JavaScriptFrameworkDto frameworkDto = mock(JavaScriptFrameworkDto.class);

        when(converter.fromEntity(any())).thenReturn(frameworkDto);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        service.deleteById(1L);
    }
}