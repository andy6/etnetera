package com.etnetera.hr.converter;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.Version;
import com.etnetera.hr.data.VersionDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Converter from transfer objects to access objects and reverse.
 */
@Component
public class VersionConverter {

    public VersionDto fromEntity(Version version) {
        VersionDto versionDto = new VersionDto();
        versionDto.setId(version.getId());
        versionDto.setLabel(version.getLabel());
        versionDto.setReleaseDate(version.getReleaseDate());

        return versionDto;
    }

    public Set<VersionDto> fromEntity(Set<Version> versions) {
        return versions.stream()
                .map(this::fromEntity)
                .collect(Collectors.toSet());
    }

    public Version fromDto(JavaScriptFramework framework, VersionDto versionDto) {
        Version version = new Version();
        version.setId(versionDto.getId());
        version.setLabel(versionDto.getLabel());
        version.setReleaseDate(versionDto.getReleaseDate());
        version.setFramework(framework);

        return version;
    }

    public Set<Version> fromDto(JavaScriptFramework framework, Set<VersionDto> versionsDto) {
        return versionsDto.stream()
                .map(versionDto -> fromDto(framework, versionDto))
                .collect(Collectors.toSet());
    }
}
