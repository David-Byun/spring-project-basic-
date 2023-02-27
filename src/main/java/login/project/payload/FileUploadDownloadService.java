package login.project.payload;

import login.project.domain.UploadFile;
import login.project.error.CustomException;
import login.project.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadDownloadService {
    private final Path fileLocation;
    private UploadFileRepository uploadFileRepository;
    private UploadFile uploadFile;

    @Autowired
    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
        log.info("파일 경로 = {} ",fileLocation);
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new CustomException("파일을 업로드할 디렉토리를 생성하지 못했습니다", ErrorCode.UPLOAD_FAIL);
        }
    }

    public UploadFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("파일명 = {}", fileName);
        try {
            //파일명에 부적합 문자가 있는지 확인한다.
            if (fileName.contains("..")) {
                throw new CustomException("파일명에 부적합 문자가 포함되어 있습니다." + fileName, ErrorCode.DOWNLOAD_FAIL);
            }
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            uploadFile.setFileName(fileName);
            uploadFile.setSize(file.getSize());
            uploadFile.setMimeType(file.getContentType());
            uploadFile.setInsertDate(new Date());
            uploadFileRepository.upload(uploadFile);
            return uploadFile;
        } catch (Exception e) {
            throw new CustomException("[" + fileName + "] 파일 업로드에 실패했습니다. 다시 시도하십시오.", ErrorCode.UPLOAD_FAIL);
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new CustomException(fileName + "파일을 찾을 수 없습니다", ErrorCode.RESOURCE_NOT_FOUND);
            }

        } catch (MalformedURLException e) {
            throw new CustomException(fileName + "파일을 찾을수 없습니다.", ErrorCode.UPLOAD_FAIL);
        }
    }

    public List<UploadFile> findAll() {
        List<UploadFile> allFile = uploadFileRepository.findAll();
        if (allFile.isEmpty()) {
            throw new CustomException("업로드된 파일이 존재하지 않습니다", ErrorCode.ALL_FIlE_NOT_FOUND);
        }
        return allFile;
    }

    public UploadFile findById(int id) {
        UploadFile findByIdFile = uploadFileRepository.findById(id);
        return findByIdFile;
    }
}
