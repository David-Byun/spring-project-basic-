package login.project.payload;

import login.project.domain.UploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {
    void upload(UploadFile uploadFile);
    List<UploadFile> findAll();

    UploadFile findById(int id);
}
