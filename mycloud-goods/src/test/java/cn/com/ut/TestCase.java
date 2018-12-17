package cn.com.ut;

import org.junit.Test;

import javax.persistence.Temporal;
import java.util.Optional;

/**
 * @author wuxiaohua
 * @since 2018/7/10
 */
public class TestCase {

    @Test
    public void test() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test2() {

        String s = null;
//        s = "test";
        Optional<String> optionalS = Optional.ofNullable(s);
        System.out.println(optionalS.get());
    }
}
