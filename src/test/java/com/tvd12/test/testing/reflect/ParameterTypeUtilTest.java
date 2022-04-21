package com.tvd12.test.testing.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.ParameterTypeUtil;

public class ParameterTypeUtilTest extends BaseTest {

    @SuppressWarnings({"rawtypes"})
    @Test
    public void test() {
        String str = ParameterTypeUtil.toString((Class[]) null);
        assertEquals(str, "");
        str = ParameterTypeUtil.toString(Class.class, Class.class);
        assertNotNull(str);

        str = ParameterTypeUtil.toString((List<Class<?>>) null);
        assertEquals(str, "");
        str = ParameterTypeUtil.toString(Lists.newArrayList(Class.class));
        assertNotNull(str);
    }

    @Override
    public Class<?> getTestClass() {
        return ParameterTypeUtil.class;
    }
}
