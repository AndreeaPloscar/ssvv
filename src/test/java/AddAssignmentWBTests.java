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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentWBTests {

    private Service service;
    private StudentXMLRepository fileRepository1;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "studenti_test.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme_test.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note_test.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterEach
    void cleanUp() {
        service.deleteTema("1");
    }

    @Test
    void testCase1() {
        var result = service.saveTema("1", "Abc", 2, 1);
        assertEquals(result, 1);
    }

    @Test
    void testCase2() {
        assertThrows(Exception.class, () -> service.saveTema(null, "Abc", 2, 1));
    }

    @Test
    void testCase3() {
        assertThrows(Exception.class, () -> service.saveTema("1", null, 2, 1));
    }

    @Test
    void testCase4() {
        assertThrows(Exception.class, () -> service.saveTema("1", "Abc", -10, 1));
    }

    @Test
    void testCase5() {
        assertThrows(Exception.class, () -> service.saveTema("1", "Abc", 2, -10));
    }

    @Test
    void testCase6() {
        service.saveTema("1", "A", 2, 1);


        var result = service.saveTema("1", "Abc", 2, 1);
        assertEquals(result, 0);
    }
}
