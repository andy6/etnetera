package com.etnetera.hr.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    @Query("SELECT jf FROM JavaScriptFramework jf, Version v " +
            " WHERE (?1 IS NULL OR jf.name LIKE %?1%) " +
            " AND (?2 IS NULL OR v.label LIKE %?2%)" +
            " AND (?3 IS NULL OR jf.deprecationDate > ?3)"
    )
    Iterable<JavaScriptFramework> search(@Param("name") String name,
                                         @Param("version_label") String versionLabel,
                                         @Param("deprecated_after") Date deprecatedAfter);


}
