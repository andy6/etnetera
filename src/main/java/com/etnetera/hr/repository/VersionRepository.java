package com.etnetera.hr.repository;

import com.etnetera.hr.data.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring data repository interface used for accessing the data in database.
 *
 */
public interface VersionRepository extends CrudRepository<Version, Long> {
}
