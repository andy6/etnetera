package com.etnetera.hr.converter;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter from transfer objects to access objects and reverse.
 */
@Component
public class JavaScriptFrameworkConverter {
    private final VersionConverter versionConverter;

    @Autowired
    public JavaScriptFrameworkConverter(VersionConverter versionConverter) {
        this.versionConverter = versionConverter;
    }

    public JavaScriptFrameworkDto fromEntity(JavaScriptFramework framework) {
        JavaScriptFrameworkDto frameworkDto = new JavaScriptFrameworkDto();
        frameworkDto.setId(framework.getId());
        frameworkDto.setName(framework.getName());
        frameworkDto.setDeprecationDate(framework.getDeprecationDate());
        frameworkDto.setHypeLevel(framework.getHypeLevel());
        frameworkDto.setVersions(versionConverter.fromEntity(framework.getVersions()));

        return frameworkDto;
    }

    public JavaScriptFramework fromDto(JavaScriptFrameworkDto frameworkDto) {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setId(framework.getId());
        framework.setName(frameworkDto.getName());
        framework.setDeprecationDate(frameworkDto.getDeprecationDate());
        framework.setHypeLevel(frameworkDto.getHypeLevel());
        framework.setVersions(versionConverter.fromDto(framework, frameworkDto.getVersions()));

        return framework;
    }
}
