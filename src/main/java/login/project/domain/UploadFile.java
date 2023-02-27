package login.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    private int id;
    private String fileName;
    private long size;
    private String mimeType;
    private Date insertDate;

}
