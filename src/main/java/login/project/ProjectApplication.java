package login.project;

import login.project.payload.FileUploadProperties;
import login.project.configuration.MyBatisConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;




@EnableBatchProcessing //배치 기능 활성화
@SpringBootApplication
@EnableConfigurationProperties({FileUploadProperties.class})
@Import(MyBatisConfig.class)
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
