package login.project.payload;

import login.project.domain.UploadFile;

import java.util.List;

public interface UploadFileRepository {
    void upload(UploadFile uploadFile);

    List<UploadFile> findAll();

    UploadFile findById(int id);
}
