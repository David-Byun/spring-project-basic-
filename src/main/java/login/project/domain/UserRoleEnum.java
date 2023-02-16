package login.project.domain;

public enum UserRoleEnum {

    //spring security 가 제공하는 ROLE 네이밍 정책이 <ROLE_권한>이므로 맞춰서 작성
    ROLE_ADMIN("관리자"), ROLE_MEMBER("일반사용자");

    private String description;

    UserRoleEnum(String description) {
        this.description = description;
    }
}
