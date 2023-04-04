import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;


public class AddStudentUnitTests {

    private  Service service;
    private StudentXMLRepository fileRepository1;

    @BeforeEach
    void setUp(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "studenti_test.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme_test.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note_test.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterEach
    void cleanUp(){
        service.deleteStudent("1");
    }

    @Test
    void testCase1(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("1", "nume", -1);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase2(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("1", "nume", 938);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase3(){
        var result = service.saveStudent("1", "nume", 111);
        assertEquals(result, 1);
    }

    @Test
    void testCase4(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("1", "nume", 0);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase6(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("", "nume", 111);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase7(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent(null, "nume", 111);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase8(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("1", "", 111);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase9(){
        assertThrows(Exception.class, () -> {
            var res = service.saveStudent("1", null, 111);
            assertEquals(0, res);
        });
    }

    @Test
    void testCase10(){
        var result = service.saveStudent("1", "nume", 936);
        assertEquals(result, 1);
    }

    @Test
    void testCase11(){
        var result = service.saveStudent("1", "nume", 937);
        assertEquals(result, 1);
    }

    @Test
    void testCase12(){
        service.saveStudent("1", "nume", 937);
        var result = service.saveStudent("1", "nume", 937);
        assertEquals(result, 0);
    }


}

