package login.project.configuration;

import login.project.repository.ItemRepository;
import login.project.repository.mybatis.ItemMapper;
import login.project.repository.mybatis.MyBatisItemRepository;
import login.project.service.ItemService;
import login.project.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService(){
        return new ItemServiceImpl(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }
}
