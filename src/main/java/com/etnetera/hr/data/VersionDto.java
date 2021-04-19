package com.etnetera.hr.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Transfer object of Framework Version represents REST layer
 */
public class VersionDto {
    @Positive
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Size(max = 30)
    @JsonProperty("label")
    private String label;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "VersionDto{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
