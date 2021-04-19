package com.etnetera.hr.controller;

import com.etnetera.hr.exception.JavaScriptFrameworkNotFoundException;
import com.etnetera.hr.util.RestPreconditions;
import com.etnetera.hr.data.JavaScriptFrameworkDto;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import java.util.Date;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkService service;
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptFrameworkController.class);

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkService service) {
		this.service = service;
	}

	@GetMapping("/frameworks")
	public ResponseEntity<Iterable<JavaScriptFrameworkDto>> frameworks() {

		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<Iterable<JavaScriptFrameworkDto>> search(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "version", required = false) String versionLabel,
			@RequestParam(value = "deprecated_after", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date deprecatedAfter) {

		try {
			return ResponseEntity.ok(RestPreconditions.checkFound(service.search(name, versionLabel, deprecatedAfter)));

		} catch (JavaScriptFrameworkNotFoundException e) {
			String errorMessage = "No JavaScript Framework was found.";
			logger.error(errorMessage);

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<JavaScriptFrameworkDto> findById(@PathVariable("id") long id) {
		try {
			return ResponseEntity.ok(service.findById(id));

		} catch (JavaScriptFrameworkNotFoundException e) {
			String errorMessage = "JavaScript Framework with id #" + id + " was not found.";
			logger.error(errorMessage);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
		}
	}

	@PostMapping("/")
	public ResponseEntity<JavaScriptFrameworkDto> create(
			@Valid @RequestBody JavaScriptFrameworkDto frameworkInputDto) {

		return ResponseEntity.ok(service.create(frameworkInputDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<JavaScriptFrameworkDto> update(
			@PathVariable("id") long id,
			@Valid @RequestBody JavaScriptFrameworkDto frameworkInputDto) {

		try {
			long identifier = RestPreconditions.checkIdentifier(id);
			JavaScriptFrameworkDto frameworkDto = RestPreconditions.checkFound(frameworkInputDto);

			return ResponseEntity.ok(service.update(identifier, frameworkDto));

		} catch (JavaScriptFrameworkNotFoundException e) {
			String errorMessage = "JavaScript Framework was not found.";
			logger.error(errorMessage);

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable long id) {
		try {
			long identifier = RestPreconditions.checkIdentifier(id);
			service.deleteById(identifier);

			return ResponseEntity.noContent().build();

		} catch (JavaScriptFrameworkNotFoundException e) {
			String errorMessage = "JavaScript Framework with id #" + id + " was not found.";
			logger.error(errorMessage);

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
		}
	}

}
