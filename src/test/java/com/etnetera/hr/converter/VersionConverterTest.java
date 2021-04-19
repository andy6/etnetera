package com.etnetera.hr.converter;

import com.etnetera.hr.EntityCreator;
import com.etnetera.hr.data.HypeLevelEnum;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.Version;
import com.etnetera.hr.data.VersionDto;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test converting version entity to transfer object and reverse.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class VersionConverterTest {

    @Autowired
    private VersionConverter versionConverter;

    @Autowired
    private EntityCreator entityCreator;

    @Test
    public void givenVersionEntity_whenEntityIsConverted_thenVersionDtoIsCreated() {
        Version version = entityCreator.createVersionEntity(1L, "tested-version-dao-label.", new Date());

        VersionDto versionDto = versionConverter.fromEntity(version);
        assertEquals(version.getId(), versionDto.getId());
        assertEquals(version.getLabel(), versionDto.getLabel());
        assertEquals(version.getReleaseDate(), versionDto.getReleaseDate());
    }

    @Test
    public void givenListOfVersionEntities_whenEntitiesAreConverted_thenListOfVersionDtoIsCreated() {
        Version firstVersion = entityCreator.createVersionEntity(1L, "tested-version-label-1.", new Date());
        Version secondVersion = entityCreator.createVersionEntity(2L, "tested-version-label-2.", new Date());
        Version thirdVersion = entityCreator.createVersionEntity(3L, "tested-version-label-3.", new Date());

        Set<Version> versionEntitySet = Stream.of(firstVersion, secondVersion, thirdVersion)
                .collect(Collectors.toSet());
        Set<VersionDto> versionDtoSet = versionConverter.fromEntity(versionEntitySet);

        assertEquals(versionDtoSet.stream()
                .filter(versionDto ->
                        versionDto.getId().equals(firstVersion.getId()) ||
                        versionDto.getLabel().equals(secondVersion.getLabel()) ||
                        versionDto.getReleaseDate().equals(thirdVersion.getReleaseDate()))
                .count(), 3);
    }

    @Test
    public void givenVersionDto_whenDtoIsConverted_thenVersionEntityIsCreated() {
        VersionDto versionDto = entityCreator.createVersionDto("tested-version-dto-label.", new Date());
        JavaScriptFramework framework = entityCreator.createFrameworkEntity("tested-framework-dto-label.", HypeLevelEnum.BAD, new Date());

        Version version = versionConverter.fromDto(framework, versionDto);
        assertEquals(version.getId(), versionDto.getId());
        assertEquals(version.getLabel(), versionDto.getLabel());
        assertEquals(version.getReleaseDate(), versionDto.getReleaseDate());
    }

    @Test
    public void givenListOfVersionDto_whenDtoAreConverted_thenListOfVersionEntitiesIsCreated() {
        JavaScriptFramework framework = entityCreator.createFrameworkEntity("tested-framework-dto-label.", HypeLevelEnum.BAD, new Date());
        VersionDto firstVersion = entityCreator.createVersionDto("tested-version-label-1.", new Date());
        VersionDto secondVersion = entityCreator.createVersionDto("tested-version-label-2.", new Date());
        VersionDto thirdVersion = entityCreator.createVersionDto("tested-version-label-3.", new Date());

        Set<VersionDto> versionEntitySet = Stream.of(firstVersion, secondVersion, thirdVersion)
                .collect(Collectors.toSet());
        Set<Version> versionSet = versionConverter.fromDto(framework, versionEntitySet);

        assertEquals(versionSet.stream()
                .filter(versionDto ->
                        versionDto.getReleaseDate().equals(firstVersion.getReleaseDate()) ||
                                versionDto.getLabel().equals(secondVersion.getLabel()) ||
                                versionDto.getReleaseDate().equals(thirdVersion.getReleaseDate()))
                .count(), 3);
    }
}