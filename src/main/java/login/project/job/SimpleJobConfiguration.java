package login.project.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration //Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용
public class SimpleJobConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        //simpleStep1 이란 이름의 Batch Step을 생성
        return jobBuilderFactory.get("simpleJob").start(simpleStep1()).build();

    }

    //simpleStep1이라는 Batch Step을 생성. jobBuilderFactory.get("simpleJob")와 마찬가지로 Builder를 통해 이름 지정
    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1").tasklet(((contribution, chunkContext) -> {
            //Step 안에서 수행될 기능들 명시
            //Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용
            //Batch가 수행되면 log.info(">>>>> This is Step1") 출력
            log.info(">>>>> This is Step1");
            return RepeatStatus.FINISHED;
        })).build();
    }
}
