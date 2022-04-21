package com.tvd12.test.testing.performance;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import com.tvd12.test.performance.Script;

public class PerformanceTest extends BaseTest {

    @Test
    public void test() {
        long time = Performance.create()
            .loop(1000000000)
            .test(new Script() {
                @Override
                public void execute() {
                    hello();
                }

                public void hello() {
                }
            })
            .getTime();
        assert time >= 0;
    }

    @Test
    public void testInConcurrentMode() {
        long time = Performance.create()
            .loop(10000)
            .threadCount(100)
            .test(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
            .getTime();
        System.out.println(time);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testExceptionCase() {
        long time = Performance.create()
            .loop(1000000000)
            .test(() -> {
                throw new IllegalArgumentException("just test");
            })
            .getTime();
        assert time >= 0;
    }
}
