package com.techgen.client;

import java.util.List;

import com.techgen.entity.Guide;
import com.techgen.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Client {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = null;
		EntityManager entityManager = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("student-guide");
			entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();

			// persistGuideStudent(entityManager, transaction);
			// getStudents(entityManager, transaction);
			// getStudents1(entityManager, transaction);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
			if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
				entityManagerFactory.close();
			}
		}
	}

	private static void persistGuideStudent(EntityManager entityManager, EntityTransaction transaction) {
		transaction.begin();
		Guide guide1 = new Guide("David Crow", 3000, "2000DC");
		Student student1 = new Student("2014R", "Rahul Singh", guide1);
		guide1.addStudent(student1);
		entityManager.persist(guide1);
		transaction.commit();
	}

	private static void getStudents(EntityManager entityManager, EntityTransaction transaction) {
		transaction.begin();
		Query query = entityManager.createQuery("select student from Student student");
		List<Student> students = query.getResultList();
		for (Student student : students) {
			System.out.println("Student Name     : " + student.getName());
			System.out.println("Student EnrollId : " + student.getEnrollmentId());
			System.out.println();
		}
		transaction.commit();
	}

	private static void getStudents1(EntityManager entityManager, EntityTransaction transaction) {
		transaction.begin();
		Query query = entityManager.createQuery("select student from Student student left join fetch student.guide");
		List<Student> students = query.getResultList();
		for (Student student : students) {
			System.out.println("Student Name     : " + student.getName());
			System.out.println("Student EnrollId : " + student.getEnrollmentId());
			if (student.getGuide() != null) {
				System.out.println("Guide   Name     : " + student.getGuide().getName());
			}

			System.out.println();
		}
		transaction.commit();
	}

}
