package org.sep;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SepApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setCountryOfOrigin("USA");
        student.setCountryOfResidence("UK");
        student.setLinkedinLink("https://linkedin.com/in/johndoe");
        student.setBio("A software engineer with 5 years of experience.");

        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/api/students", student, Student.class);
        assertThat(createResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getId()).isNotNull();

        ResponseEntity<Student[]> getResponse = restTemplate.getForEntity("/api/students", Student[].class);
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().length).isEqualTo(1);
        assertThat(getResponse.getBody()[0].getLastName()).isEqualTo("Doe");

        ResponseEntity<Student> getByIdResponse = restTemplate.getForEntity("/api/students/1", Student.class);
        assertThat(getByIdResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getByIdResponse.getBody()).isNotNull();
        assertThat(getByIdResponse.getBody().getLastName()).isEqualTo("Doe");

        getByIdResponse = restTemplate.getForEntity("/api/students/2", Student.class);
        assertThat(getByIdResponse.getStatusCodeValue()).isEqualTo(404);
        assertThat(getByIdResponse.getBody()).isNull();
    }


    @Test
    void testCreateFeedback() {
        Feedback feedback = new Feedback();
        feedback.setCourseTitle("Deep Neural Networks");
        feedback.setYearTaken(2023);
        feedback.setScore(9);
        feedback.setDescription("Great course, very informative!");

        ResponseEntity<Feedback> createResponse = restTemplate.postForEntity("/api/feedback", feedback, Feedback.class);
        assertThat(createResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getId()).isNotNull();

        ResponseEntity<Feedback[]> getResponse = restTemplate.getForEntity("/api/feedback", Feedback[].class);
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().length).isEqualTo(1);
        assertThat(getResponse.getBody()[0].getCourseTitle()).isEqualTo("Deep Neural Networks");

        ResponseEntity<Feedback> getByIdResponse = restTemplate.getForEntity("/api/feedback/1", Feedback.class);
        assertThat(getByIdResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getByIdResponse.getBody()).isNotNull();
        assertThat(getByIdResponse.getBody().getCourseTitle()).isEqualTo("Deep Neural Networks");

        getByIdResponse = restTemplate.getForEntity("/api/feedback/2", Feedback.class);
        assertThat(getByIdResponse.getStatusCodeValue()).isEqualTo(404);
        assertThat(getByIdResponse.getBody()).isNull();
    }
}
