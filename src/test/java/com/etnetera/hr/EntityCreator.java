package com.etnetera.hr;

import com.etnetera.hr.data.HypeLevelEnum;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.etnetera.hr.data.Version;
import com.etnetera.hr.data.VersionDto;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Creates entities for testing.
 */
@Component
public class EntityCreator {

    public JavaScriptFramework createFrameworkEntity(String name, HypeLevelEnum level, Date deprecationDate) {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setName(name);
        framework.addVersion(createVersionEntity(1L, "1.1.1-RELEASE", new Date()));
        framework.addVersion(createVersionEntity(2L, "1.2.1-RELEASE", new Date()));
        framework.setHypeLevel(level);
        framework.setDeprecationDate(deprecationDate);

        return framework;
    }

    public JavaScriptFrameworkDto createFrameworkDto(String name, HypeLevelEnum level, Date deprecationDate) {
        JavaScriptFrameworkDto framework = new JavaScriptFrameworkDto();
        framework.setName(name);
        framework.addVersion(createVersionDto("1.1.1-RELEASE", new Date()));
        framework.addVersion(createVersionDto("1.2.1-RELEASE", new Date()));
        framework.setHypeLevel(level);
        framework.setDeprecationDate(deprecationDate);

        return framework;
    }

    public VersionDto createVersionDto(String textVersion, Date date) {
        VersionDto versionDto = new VersionDto();
        versionDto.setLabel(textVersion);
        versionDto.setReleaseDate(date);

        return versionDto;
    }

    public Version createVersionEntity(long id, String label, Date date) {
        Version version = new Version();
        version.setId(id);
        version.setLabel(label);
        version.setReleaseDate(date);

        return version;
    }
}
