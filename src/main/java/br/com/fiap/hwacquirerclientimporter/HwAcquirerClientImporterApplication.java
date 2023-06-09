package br.com.fiap.hwacquirerclientimporter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class HwAcquirerClientImporterApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HwAcquirerClientImporterApplication.class, args)));
    }
}
