package com.kkk.kspring2.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @AllArgsConstructor  // 모든 필드 값을 파라미터로 받는 생성자를 만들어 준다
 * @ToString(exclude = "tmpData")  // exclude를 사용하면 toString() 결과에서 id를 제외시킬 수 있다
 * @Data는 위에서 설명한 @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 한번에 설정해주는 어노테이션이다.
 * equals :  두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
 * hashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자
 */
@Data
@AllArgsConstructor
public class LombokData {
    String strData;
    String tmpData;
    int intData;
}
