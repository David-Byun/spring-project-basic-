package login.project.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum ErrorCode implements EnumModel {
    INVALID_CODE(400, "C001", "Invalid Code"),
    RESOURCE_NOT_FOUND(204, "C002", "Resource not found"),
    EXPIRED_CODE(400, "C003", "Expired Code"),
    // AWS
    AWS_ERROR(400, "A001", "aws client error"),
    ADMIN_TOKEN(400, "J001", "관리자 암호가 일치하지 않습니다."),
    SAME_USER(400, "J002", "동일한 이메일이 존재합니다"),
    NO_USER(400, "J003", "없는 사용자입니다"),
    NO_LOGIN(400, "J004", "로그인이 필요합니다"),
    NO_ADMIN(400, "J005", "권한이 없는 사용자입니다"),

    // File
    UPLOAD_FAIL(400, "U001", "파일 업로드가 실패했습니다"),
    DOWNLOAD_FAIL(400, "D001", "파일 다운로드가 실패했습니다");

    private int status;
    private String code;
    private String message;
    private String detail;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}