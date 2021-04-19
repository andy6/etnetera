package com.etnetera.hr.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Transfer object of JavaScript Framework represents REST layer
 */
public class JavaScriptFrameworkDto {

    @Positive
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Size(max = 30)
    @JsonProperty("name")
    private String name;

    @Valid
    @JsonProperty("versions")
    private Set<VersionDto> versions = new HashSet<>();

    @JsonProperty("deprecation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deprecationDate;

    @JsonProperty("hype_level")
    private HypeLevelEnum hypeLevel;

    public JavaScriptFrameworkDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VersionDto> getVersions() {
        return versions;
    }

    public void addVersion(VersionDto version) {
        this.versions.add(version);
    }

    public void setVersions(Set<VersionDto> versions) {
        this.versions = versions;
    }

    public Date getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(Date deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public HypeLevelEnum getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(HypeLevelEnum hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    @Override
    public String toString() {
        return "JavaScriptFrameworkDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", versions=" + versions +
                ", deprecationDate=" + deprecationDate +
                ", hypeLevel=" + hypeLevel +
                '}';
    }
}
