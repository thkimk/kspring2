package com.kkk.kspring2.thymeleaf;

/**
 * 타임리프는 기본 cache 설정이 true 이므로 개발시에는 spring.thymeleaf.cache를 false를 설정하여 두는게 수정 사항이 바로 반영되므로 편리합니다.
 * 뷰 리졸버 설정도 있어야 한다는 문서들도 있었지만, 없어도 잘 동작하였기에 리졸버 설정을 하지 않았습니다.
 * 앞에서의 설정에 따라 컨트롤러에서 반환하는 뷰 이름이 "thymeleaf/"로  시작하면 Thymeleaf 템플릿으로 처리됩니다
 */
public class ThymeData {
    private String userId;
    private String userPwd;
    private String name;
    private String authType;

    public ThymeData(String userId, String name, String authType) {
        super();
        this.userId = userId;
        this.name = name;
        this.authType = authType;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", authType=" + authType + "]";
    }
}
