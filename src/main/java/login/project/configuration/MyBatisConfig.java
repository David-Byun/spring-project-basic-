package login.project.configuration;

import login.project.payload.FileUploadDownloadService;
import login.project.payload.MyBatisUploadFileRepository;
import login.project.payload.UploadFileRepository;
import login.project.payload.UploadMapper;
import login.project.repository.item.ItemRepository;
import login.project.repository.mybatis.item.ItemMapper;
import login.project.repository.mybatis.item.MyBatisItemRepository;
import login.project.repository.mybatis.user.MyBatisUserRepository;
import login.project.repository.mybatis.user.UserMapper;
import login.project.repository.user.UserRepository;
import login.project.service.item.ItemService;
import login.project.service.item.ItemServiceImpl;
import login.project.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final UploadMapper uploadMapper;

    @Bean
    public ItemService itemService(){
        return new ItemServiceImpl(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }

    @Bean
    public UserDetailsServiceImpl userService() {
        return new UserDetailsServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MyBatisUserRepository(userMapper);
    }

    @Bean
    public UploadFileRepository uploadFileRepository() {
        return new MyBatisUploadFileRepository(uploadMapper);
    }




}
