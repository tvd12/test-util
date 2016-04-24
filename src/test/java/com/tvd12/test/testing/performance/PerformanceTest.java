package com.tvd12.test.testing.performance;

import org.junit.Test;

import com.tvd12.test.performance.Performance;
import com.tvd12.test.performance.Script;

public class PerformanceTest {

    @Test
    public void test() {
        Performance.create()
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
    }
    
}
