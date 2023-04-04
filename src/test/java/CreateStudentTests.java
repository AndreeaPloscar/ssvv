import de.rwth.swc.coffee4j.algorithmic.sequential.generator.ipog.Ipog;
import de.rwth.swc.coffee4j.engine.configuration.model.InputParameterModel;
import de.rwth.swc.coffee4j.junit.engine.annotation.CombinatorialTest;
import de.rwth.swc.coffee4j.junit.engine.annotation.configuration.sequential.generation.EnableGeneration;
import de.rwth.swc.coffee4j.junit.engine.annotation.parameter.parameter.InputParameter;
import domain.Student;
import validation.StudentValidator;
import validation.ValidationException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static de.rwth.swc.coffee4j.engine.configuration.model.InputParameterModel.inputParameterModel;
import static de.rwth.swc.coffee4j.engine.configuration.model.Parameter.parameter;
import static de.rwth.swc.coffee4j.engine.configuration.model.constraints.ConstraintBuilder.constrain;

public class CreateStudentTests {

//    private static final Logger LOG = LoggerFactory.getLogger(CreateStudentTests.class);


    private static InputParameterModel model() {
        return inputParameterModel("example-model")
                .positiveTestingStrength(1)
                .parameters(
                        parameter("idStudent").values("id1", "id2"),
                        parameter("nume").values("Ana", "Diana", "Zamfira", "Ion"),
                        parameter("grupa").values(931, 932, 933, 934, 935, 936, 937)
                )
                .errorConstraints(
                        constrain("grupa", "nume")
                                .withName("invalid-grupa-nume-combination")
                                .by((Integer grupa, String nume) -> {
                                    if(nume.equals("Ana") && grupa != 931){
                                        return false;
                                    }

                                    return !nume.equals("Zamfira") || grupa == 937;
                                } )).build();
    }

    private static InputParameterModel modelErori() {
        return inputParameterModel("example-model")
                .positiveTestingStrength(1)
                .parameters(
                        parameter("idStudent").values(null, ""),
                        parameter("nume").values(null, ""),
                        parameter("grupa").values(100, -10, 1000)
                ).build();
    }

    @CombinatorialTest(inputParameterModel = "model")
    @EnableGeneration(algorithms = {Ipog.class})
    void testStudentiValid(
            @InputParameter("idStudent") String idStudent,
            @InputParameter("nume") String nume,
            @InputParameter("grupa") Integer grupa) {
        Student stud = new Student(idStudent, nume, grupa);
        StudentValidator validator = new StudentValidator();
        assertDoesNotThrow(() -> validator.validate(stud));
    }


    @CombinatorialTest(inputParameterModel = "modelErori")
    @EnableGeneration(algorithms = {Ipog.class})
    void testStudentiErrors(
            @InputParameter("idStudent") String idStudent,
            @InputParameter("nume") String nume,
            @InputParameter("grupa") Integer grupa) {
        Student stud = new Student(idStudent, nume, grupa);
        StudentValidator validator = new StudentValidator();
        assertThrows(ValidationException.class, () -> validator.validate(stud));
    }
}
