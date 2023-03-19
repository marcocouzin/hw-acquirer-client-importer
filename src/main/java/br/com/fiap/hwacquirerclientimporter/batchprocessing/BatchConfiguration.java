package br.com.fiap.hwacquirerclientimporter.batchprocessing;

import br.com.fiap.hwacquirerclientimporter.model.StudentIn;
import br.com.fiap.hwacquirerclientimporter.model.StudentOut;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;

@Component
public class BatchConfiguration {

    @Bean
    public ItemReader<StudentIn> itemReader(@Value("${fiap.acquirer.resource}") Resource resource) {
        return new FlatFileItemReaderBuilder<StudentIn>()
                .name("file reader")
                .resource(resource)
                .delimited().delimiter(DelimitedLineTokenizer.DELIMITER_TAB)
                .names("name", "email", "birthDate", "course", "status")
                .targetType(StudentIn.class)
                .build();
    }


    @Bean
    public StudentItemProcessor processor() {
        return new StudentItemProcessor();
    }


    @Bean
    public ItemWriter<StudentOut> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<StudentOut>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("INSERT INTO TB_STUDENT" +
                        "(NAME, EMAIL, BIRTH_DATE, COURSE, STATUS, CREATED_DATE, MODIFIED_DATE) " +
                        "VALUES " +
                        "(:name, :email, :birthDate, :course, :status, '" + LocalDate.now() + "', '" + LocalDate.now() + "')")
                .build();
    }


    @Bean
    public Step step(
            StepBuilderFactory stepBuilderFactory,
            ItemReader<StudentIn> itemReader,
            ItemProcessor<StudentIn, StudentOut> itemProcessor,
            ItemWriter<StudentOut> itemWriter
    ) {
        return stepBuilderFactory.get("destination process step")
                .<StudentIn, StudentOut>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("destination job")
                .start(step)
                .build();
    }
}
