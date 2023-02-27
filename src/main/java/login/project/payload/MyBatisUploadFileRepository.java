package login.project.payload;

import login.project.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MyBatisUploadFileRepository implements UploadFileRepository{
    private final UploadMapper uploadMapper;

    @Override
    public void upload(UploadFile uploadFile) {
      uploadMapper.upload(uploadFile);
    }

    @Override
    public List<UploadFile> findAll() {
        List<UploadFile> allFile = uploadMapper.findAll();
        return allFile;
    }

    @Override
    public UploadFile findById(int id) {
        UploadFile findByIdFile = uploadMapper.findById(id);
        return findByIdFile;
    }
}
