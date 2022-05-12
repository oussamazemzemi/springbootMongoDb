package com.student.information.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.information.system.dto.StudentDTO;
import com.student.information.system.model.Student;
import com.student.information.system.service.StudentService;
import com.student.information.system.util.ObjectMapperUtils;

/**
 * @author oz
 */
@CrossOrigin(allowedHeaders = "*", origins = "*", maxAge = 3600) // or http://localhost:4200
@RestController
@RequestMapping("/students")
public class StudentRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentRestController.class);

	@Autowired
	private StudentService studentService;

	@GetMapping(value = "/")
	public List<StudentDTO> getAllStudents() {
		LOGGER.info("getAllStudents");
		return ObjectMapperUtils.mapAll(studentService.findAll(), StudentDTO.class);
	}

	@GetMapping(value = "/byStudentNumber/{studentNumber}")
	public StudentDTO getStudentByStudentNumber(@PathVariable("studentNumber") Long studentNumber) {
		return ObjectMapperUtils.map(studentService.findByStudentNumber(studentNumber), StudentDTO.class);
	}

	@GetMapping(value = "/byEmail/{email}")
	public StudentDTO getStudentByEmail(@PathVariable("email") String email) {
		return ObjectMapperUtils.map(studentService.findByEmail(email), StudentDTO.class);
	}

	@GetMapping(value = "/orderByGpa")
	public List<StudentDTO> findAllByOrderByGpaDesc() {
		return ObjectMapperUtils.mapAll(studentService.findAllByOrderByGpaDesc(), StudentDTO.class);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<?> saveOrUpdateStudent(@RequestBody StudentDTO studentDTO) {
		studentService.saveOrUpdateStudent(ObjectMapperUtils.map(studentDTO, Student.class));
		return new ResponseEntity("Student added successfully", HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{studentNumber}")
	public ResponseEntity<?> deleteStudentByStudentNumber(@PathVariable long studentNumber) {
		studentService.deleteStudentById(studentService.findByStudentNumber(studentNumber).getId());
		return new ResponseEntity("Student deleted successfully", HttpStatus.OK);
	}

}
