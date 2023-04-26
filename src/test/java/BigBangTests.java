import domain.Nota;
import domain.Pair;
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


public class BigBangTests {

    private Service service;
    private StudentXMLRepository fileRepository;
    private TemaXMLRepository temaXMLRepository;
    private NotaXMLRepository notaXMLRepository;


    @BeforeEach
    void setUp(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        fileRepository = new StudentXMLRepository(studentValidator, "studenti_test.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme_test.xml");
        notaXMLRepository = new NotaXMLRepository(notaValidator, "note_test.xml");

        service = new Service(fileRepository, fileRepository2, notaXMLRepository);
    }

    @AfterEach
    void cleanUp(){
        service.deleteStudent("1");
        service.deleteTema("2");

        notaXMLRepository.delete(new Pair<>("1", "2"));
    }

    @Test
    void testStudent(){
        var result = service.saveStudent("1", "nume", 111);
        assertEquals(result, 1);
    }


    @Test
    void testAssignment(){
        var result = service.saveTema("2", "Abc", 2, 1);
        assertEquals(result, 1);
    }

    @Test
    void testGrade(){
        service.saveStudent("1", "nume", 111);
        service.saveTema("2", "Abc", 13, 1);
        var result = service.saveNota("1", "2", 10, 2, "foarte bine");
        assertEquals(1, result);
    }


    @Test
    void testIntegration(){
        var result = service.saveStudent("1", "nume", 111);
        assertEquals(1, result);
        result = service.saveTema("2", "Abc", 13, 1);
        assertEquals(1, result);
        result = service.saveNota("1", "2", 10, 2, "foarte bine");
        assertEquals(1, result);
    }
}
