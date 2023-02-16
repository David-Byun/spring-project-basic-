package login.project.configuration;

import login.project.repository.item.ItemRepository;
import login.project.repository.mybatis.item.ItemMapper;
import login.project.repository.mybatis.item.MyBatisItemRepository;
import login.project.service.item.ItemService;
import login.project.service.item.ItemServiceImpl;
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
