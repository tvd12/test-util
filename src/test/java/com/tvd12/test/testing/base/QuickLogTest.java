package com.tvd12.test.testing.base;

import org.testng.annotations.Test;

import com.tvd12.test.base.QuickLog;

public class QuickLogTest {

    @Test
    public void test() {
        QuickLog log = new QuickLog();

        log.error("");
        try {
            a();
        } catch (Exception e) {
            log.error("how?", e);
        }

        log.info("");
        log.info("", new Exception());

    }

    public void a() {
        try {
            b();
        } catch (Exception e) {
            throw new IllegalStateException("can be", e);
        }
    }

    public void b() {
        try {
            throw new IllegalArgumentException("no one");
        } catch (Exception e) {
            throw new RuntimeException("error runtime", e);
        }
    }
}
