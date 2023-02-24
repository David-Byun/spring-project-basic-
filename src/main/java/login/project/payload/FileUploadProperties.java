package login.project.payload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//prefix="file"로 선언된 부분은 application.properties에 선언한 file.upload-dir 필드에 접근한다는 의미
//@ConfigurationProperties를 통해 application.properties 파일 연결
@ConfigurationProperties(prefix="file")
public class FileUploadProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
