package com.student.information.system.service;

import java.util.List;

import com.student.information.system.model.Student;

/**
 * @author OZ
 */
public interface StudentService {

	List<Student> findAll();

	Student findByStudentNumber(long studentNumber);

	Student findByEmail(String email);

	List<Student> findAllByOrderByGpaDesc();

	Student saveOrUpdateStudent(Student student);

	void deleteStudentById(String id);

}
