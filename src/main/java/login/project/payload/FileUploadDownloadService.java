package login.project.payload;

import jdk.internal.loader.Resource;
import login.project.error.CustomException;
import login.project.error.ErrorCode;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadDownloadService {
    private final Path fileLocation;

    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new CustomException("파일을 업로드할 디렉토리를 생성하지 못했습니다", ErrorCode.UPLOAD_FAIL);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            //파일명에 부적합 문자가 있는지 확인한다.
            if (fileName.contains("..")) {
                throw new CustomException("파일명에 부적합 문자가 포함되어 있습니다." + fileName, ErrorCode.DOWNLOAD_FAIL);
            }
            Path targetLocation = fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {

            throw new CustomException("[" + fileName + "] 파일 업로드에 실패했습니다. 다시 시도하십시오.", ErrorCode.UPLOAD_FAIL);
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            this.fileLocation.
        }
    }
}
