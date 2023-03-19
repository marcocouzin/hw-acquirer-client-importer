package br.com.fiap.hwacquirerclientimporter.batchprocessing;

import br.com.fiap.hwacquirerclientimporter.model.StudentIn;
import br.com.fiap.hwacquirerclientimporter.model.StudentOut;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentItemProcessor implements ItemProcessor<StudentIn, StudentOut>{
    @Bean
    public ItemProcessor<StudentIn, StudentOut> itemProcessor() {
        return destinationIn -> {
            StudentOut out = new StudentOut();
            
            out.setName(destinationIn.getName().toUpperCase());
            out.setEmail(destinationIn.getEmail());
            out.setBirthDate(destinationIn.getBirthDate());
            out.setCourse(destinationIn.getCourse());
            out.setStatus(destinationIn.getStatus());
            
            return out;
        };
    }

    @Override
    public StudentOut process(StudentIn destinationIn) throws Exception {
        return destinationIn -> {
            StudentOut out = new StudentOut();

            out.setName(destinationIn.getName().toUpperCase());
            out.setEmail(destinationIn.getEmail());
            out.setBirthDate(destinationIn.getBirthDate());
            out.setCourse(destinationIn.getCourse());
            out.setStatus(destinationIn.getStatus());

            return out;
    }
}
