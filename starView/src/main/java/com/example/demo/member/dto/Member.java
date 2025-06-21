package com.example.demo.member.dto; // ⭐ 패키지 선언 변경됨

import java.time.LocalDate; // java.sql.Date 대신 java.time.LocalDate 사용 권장

public class Member {
    private String userid;
    private String passwd; // 실제 운영 환경에서는 반드시 암호화된 비밀번호를 저장해야 합니다.
    private String name;
    private String email;
    private LocalDate joinDate; // DATE 타입과 매핑

    // Lombok을 사용하지 않는다면 아래와 같이 Getter/Setter를 직접 생성해야 합니다.
    // 현재 pom.xml에 lombok이 있으므로 @Data 어노테이션을 사용하는 것이 더 간편합니다.
    // @Data 어노테이션을 사용하려면 Lombok 의존성(pom.xml)이 제대로 설정되었는지 확인하세요.
    // @Data를 추가하면 아래 Getter/Setter를 직접 작성할 필요 없습니다.

    // @Data 어노테이션 사용 예시 (Member 클래스 위에 추가)
    // import lombok.Data;
    // @Data
    // public class Member { ... }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}