import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockTests {

    private Service service;
    @Mock
    private StudentXMLRepository studentRepository;
    @Mock
    private NotaXMLRepository notaRepository;
    @Mock
    private TemaXMLRepository temaRepository;


    @BeforeEach
    void setUp() {
        service = new Service(studentRepository, temaRepository, notaRepository);
    }

    @Test
    void testStudent() {
        when(studentRepository.save(new Student("1", "nume", 111))).thenReturn(null);

        var result = service.saveStudent("1", "nume", 111);
        assertEquals(result, 1);
    }

    @Test
    void testAssignment() {
        when(studentRepository.save(new Student("1", "nume", 111))).thenReturn(null);
        when(temaRepository.save(new Tema("2", "Abc", 2, 1))).thenReturn(null);

        var resultStudent = service.saveStudent("1", "nume", 111);
        assertEquals(resultStudent, 1);

        var resultTema = service.saveTema("2", "Abc", 2, 1);
        assertEquals(resultTema, 1);
    }

    @Test
    void testGrade() {
        var stud = new Student("1", "nume", 111);
        var tema = new Tema("2", "Abc", 2, 1);

        when(studentRepository.save(stud)).thenReturn(null);
        when(temaRepository.save(tema)).thenReturn(null);
        when(notaRepository.save(new Nota(new Pair<>("1", "2"), 10, 2, "foarte bine"))).thenReturn(null);
        when(studentRepository.findOne("1")).thenReturn(stud);
        when(temaRepository.findOne("2")).thenReturn(tema);

        var resultStudent = service.saveStudent("1", "nume", 111);
        assertEquals(resultStudent, 1);

        var resultTema = service.saveTema("2", "Abc", 2, 1);
        assertEquals(resultTema, 1);

        var resultNota = service.saveNota("1", "2", 10, 2, "foarte bine");
        assertEquals(1, resultNota);
    }
}
