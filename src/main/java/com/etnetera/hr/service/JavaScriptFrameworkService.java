package com.etnetera.hr.service;

import com.etnetera.hr.converter.JavaScriptFrameworkConverter;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.repository.VersionRepository;
import com.etnetera.hr.util.RestPreconditions;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service provides centralized access to JavaScript Frameworks data.
 */
@Service
public class JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository frameworkRepository;
    private final VersionRepository versionRepository;
    private final JavaScriptFrameworkConverter frameworkConverter;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository frameworkRepository,
                                      VersionRepository versionRepository,
                                      JavaScriptFrameworkConverter frameworkConverter) {
        this.frameworkRepository = frameworkRepository;
        this.versionRepository = versionRepository;
        this.frameworkConverter = frameworkConverter;
    }

    public Iterable<JavaScriptFrameworkDto> findAll() {
        return StreamSupport.stream(frameworkRepository.findAll().spliterator(), false)
                .map(frameworkConverter::fromEntity)
                .collect(Collectors.toSet());
    }

    public Iterable<JavaScriptFrameworkDto> search(String name, String versionLabel, Date deprecatedAfter) {
        return StreamSupport.stream(frameworkRepository.search(name, versionLabel, deprecatedAfter).spliterator(), false)
                .map(frameworkConverter::fromEntity)
                .collect(Collectors.toSet());
    }

    public JavaScriptFrameworkDto findById(long id) {
        RestPreconditions.checkIdentifier(id);

        return frameworkRepository.findById(id)
                .map(frameworkConverter::fromEntity)
                .orElseThrow(() ->
                        new JavaScriptFrameworkNotFoundException("JavaScript Framework with id #" + id + " was not found."));
    }

    public JavaScriptFrameworkDto create(JavaScriptFrameworkDto frameworkDto) {
        RestPreconditions.checkFound(frameworkDto);

        JavaScriptFramework javaScriptFramework = frameworkConverter.fromDto(frameworkDto);
        JavaScriptFramework framework = frameworkRepository.save(javaScriptFramework);

        versionRepository.saveAll(javaScriptFramework.getVersions());

        return frameworkConverter.fromEntity(framework);
    }

    public JavaScriptFrameworkDto update(long id, JavaScriptFrameworkDto frameworkDto) {
        RestPreconditions.checkFound(findById(id));

        JavaScriptFramework javaScriptFramework = frameworkConverter.fromDto(frameworkDto);
        javaScriptFramework.setId(id);

        JavaScriptFramework framework = frameworkRepository.save(javaScriptFramework);

        return frameworkConverter.fromEntity(framework);
    }

    public void deleteById(long id) {
        RestPreconditions.checkFound(findById(id));

        frameworkRepository.deleteById(id);
    }
}
