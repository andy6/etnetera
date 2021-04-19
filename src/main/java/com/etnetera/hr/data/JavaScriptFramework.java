package com.etnetera.hr.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deprecation_date")
	private Date deprecationDate;

	@Column
	@Enumerated(EnumType.STRING)
	private HypeLevelEnum hypeLevel;

	@OneToMany(mappedBy="framework", cascade = CascadeType.ALL)
	private Set<Version> versions = new HashSet<>();

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

	public Set<Version> getVersions() {
		return versions;
	}

	public void addVersion(Version version) {
		this.versions.add(version);
	}

	public void setVersions(Set<Version> versions) {
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
		return "JavaScriptFramework{" +
				"id=" + id +
				", name='" + name + '\'' +
				", deprecationDate=" + deprecationDate +
				", hypeLevel=" + hypeLevel +
				", versions=" + versions +
				'}';
	}
}
