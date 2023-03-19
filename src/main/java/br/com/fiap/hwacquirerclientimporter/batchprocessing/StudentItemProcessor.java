package br.com.fiap.hwacquirerclientimporter.batchprocessing;

import br.com.fiap.hwacquirerclientimporter.model.StudentIn;
import br.com.fiap.hwacquirerclientimporter.model.StudentOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentItemProcessor implements ItemProcessor<StudentIn, StudentOut> {

    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public StudentOut process(final StudentIn studentIn) {
        StudentOut studentOut = new StudentOut();

        studentOut.setName(studentIn.getName().toUpperCase());
        studentOut.setEmail(studentIn.getEmail());
        studentOut.setBirthDate(LocalDate.parse(studentIn.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        studentOut.setCourse(studentIn.getCourse());
        studentOut.setStatus(studentIn.getStatus());

        log.info("Converting (" + studentIn + ") into (" + studentOut + ")");

        return studentOut;
    }
}
