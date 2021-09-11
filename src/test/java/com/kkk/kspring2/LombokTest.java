package com.kkk.kspring2;

//import com.kkk.kspring2.lombok.LombokData;
import com.kkk.kspring2.lombok.LombokData;
import org.junit.jupiter.api.Test;

public class LombokTest {
    @Test
    public void test1() {
        LombokData lombokData = new LombokData("str1", "str2", 100);
        System.out.printf(lombokData.toString());
    }
}
