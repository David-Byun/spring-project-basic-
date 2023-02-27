package login.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import login.project.domain.Item;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


//MockMvc를 사용하기 위한 설정을 자동으로 주입해준다(Rest 관련 어노테이션)
@AutoConfigureMockMvc
@NoArgsConstructor
@WebAppConfiguration
@Transactional
@Slf4j
@ExtendWith(MockitoExtension.class)
//테스트 클래스임을 스프링에 알려준다. 스프링은 이 어노테이션을 가지고 실제 구동환경과 같은 context를 load한다
@SpringBootTest
class ItemControllerTest {

    //가짜 Mock 생성을 위해 @Mock 어노테이션
    @Autowired
    private MockMvc mockMvc;

    //Java 객체 Json 출력으로 역 직렬화
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {

    }

    //날짜 데이터 방식 변환 :  "date":"2019-05-05T08:22:03"
    @BeforeEach
    public void beforeEach() {
        objectMapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).modules(new JavaTimeModule()).build();
    }


    //아이템 추가 테스트
    @Test
    public void setTest() throws Exception {
        String url = "/items/add";
        Item item = new Item();
        item.setId(1L);
        item.setItemName("Mock test");
        item.setPrice(2999);
        item.setQuantity(100);

        String content = objectMapper.writeValueAsString(item);
        log.info(content);

        mockMvc.perform(MockMvcRequestBuilders.post(url).content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(result -> {
            MockHttpServletResponse response = result.getResponse();
            log.info(response.getContentAsString());
        });


    }
    //아이템 조회 서비스
    @Test
    public void getTest() throws Exception {
        String url = "/items/1";
        mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    log.info(response.getContentAsString());
                });
    }



















}