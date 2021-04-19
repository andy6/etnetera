package com.etnetera.hr.data;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Simple data entity describing basic properties of JavaScript framework version.
 *
 */
@Entity
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String label;

    @Column(name = "release_date")
    private Date releaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_framework", nullable=false)
    private JavaScriptFramework framework;

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

    public void setFramework(JavaScriptFramework framework) {
        this.framework = framework;
    }

    @Override
    public String toString() {
        return "Version{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
