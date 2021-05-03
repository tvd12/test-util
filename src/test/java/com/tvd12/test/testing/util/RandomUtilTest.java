package com.tvd12.test.testing.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.util.RandomUtil;

public class RandomUtilTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return RandomUtil.class;
	}
	
	@Test
	public void randomTest() {
		RandomUtil.random(boolean.class);
		RandomUtil.random(byte.class);
		RandomUtil.random(char.class);
		RandomUtil.random(double.class);
		RandomUtil.random(float.class);
		RandomUtil.random(int.class);
		RandomUtil.random(long.class);
		RandomUtil.random(short.class);
		
		Asserts.assertThat(RandomUtil.random(Boolean.class)).isEqualsType(Boolean.class);
		Asserts.assertThat(RandomUtil.random(Byte.class)).isEqualsType(Byte.class);
		Asserts.assertThat(RandomUtil.random(Character.class)).isEqualsType(Character.class);
		Asserts.assertThat(RandomUtil.random(Double.class)).isEqualsType(Double.class);
		Asserts.assertThat(RandomUtil.random(Float.class)).isEqualsType(Float.class);
		Asserts.assertThat(RandomUtil.random(Integer.class)).isEqualsType(Integer.class);
		Asserts.assertThat(RandomUtil.random(Long.class)).isEqualsType(Long.class);
		Asserts.assertThat(RandomUtil.random(Short.class)).isEqualsType(Short.class);
		Asserts.assertThat(RandomUtil.random(String.class)).isEqualsType(String.class);
		
		Asserts.assertThat(RandomUtil.random(BigDecimal.class)).isEqualsType(BigDecimal.class);
		Asserts.assertThat(RandomUtil.random(BigInteger.class)).isEqualsType(BigInteger.class);
		Asserts.assertThat(RandomUtil.random(Instant.class)).isEqualsType(Instant.class);
		Asserts.assertThat(RandomUtil.random(Date.class)).isEqualsType(Date.class);
		Asserts.assertThat(RandomUtil.random(LocalDate.class)).isEqualsType(LocalDate.class);
		Asserts.assertThat(RandomUtil.random(LocalTime.class)).isEqualsType(LocalTime.class);
		Asserts.assertThat(RandomUtil.random(LocalDateTime.class)).isEqualsType(LocalDateTime.class);
	}
	
	@Test
	public void randomUnsupportType() {
		Asserts.assertThat(() -> RandomUtil.random(Void.class))
			.willThrows(UnsupportedOperationException.class);
	}
	
	@Test
	public void randomPrimitive() {
		Asserts.assertTrue(RandomUtil.randomSmallDouble() <= 8.0D);
		Asserts.assertTrue(RandomUtil.randomSmallFloat() <= 8.0F);
		Asserts.assertTrue(RandomUtil.randomSmallInt() <= 8);
		Asserts.assertTrue(RandomUtil.randomSmallShort() <= 8);
		Asserts.assertTrue(RandomUtil.randomLong(0, 123456L) < 123456L);
	}
	
	@Test
	public void randomWrapper() {
		Asserts.assertFalse(RandomUtil.randomShortHexString().startsWith("0x"));
		Asserts.assertTrue(RandomUtil.randomShortHexString(true).startsWith("0x"));
		Asserts.assertThat(RandomUtil.random64BitBigDecimal()).isEqualsType(BigDecimal.class);
		Asserts.assertThat(RandomUtil.random64BitBigInteger()).isEqualsType(BigInteger.class);
	}
	
	@Test
	public void randomPrimitiveArray() {
		Asserts.assertThat(RandomUtil.randomShortBooleanArray())
			.test(it -> ((boolean[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortByteArray())
			.test(it -> ((byte[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortCharArray())
			.test(it -> ((char[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortDoubleArray())
			.test(it -> ((double[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortFloatArray())
			.test(it -> ((float[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortIntArray())
			.test(it -> ((int[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortLongArray())
			.test(it -> ((long[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomShortShortArray())
			.test(it -> ((short[])it).length == 8);
	}
	
	@Test
	public void randomWrapperArray() {
		Asserts.assertThat(RandomUtil.randomArray(8, Boolean.class))
			.test(it -> ((Boolean[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Byte.class))
			.test(it -> ((Byte[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Character.class))
			.test(it -> ((Character[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Double.class))
			.test(it -> ((Double[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Float.class))
			.test(it -> ((Float[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Integer.class))
			.test(it -> ((Integer[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Long.class))
			.test(it -> ((Long[])it).length == 8);
		Asserts.assertThat(RandomUtil.randomArray(8, Short.class))
			.test(it -> ((Short[])it).length == 8);
	}
	
	@Test
	public void randomWrapperListByType() {
		Asserts.assertThat(RandomUtil.randomList(8, Boolean.class))
			.test(it -> ((List<Boolean>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Byte.class))
			.test(it -> ((List<Byte>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Character.class))
			.test(it -> ((List<Character>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Double.class))
			.test(it -> ((List<Double>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Float.class))
			.test(it -> ((List<Float>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Integer.class))
			.test(it -> ((List<Integer>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Long.class))
			.test(it -> ((List<Long>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, Short.class))
			.test(it -> ((List<Short>)it).size() == 8);
	}
	
	@Test
	public void randomWrapperListByRandom() {
		Asserts.assertThat(RandomUtil.randomList(8, it -> it.nextBoolean()))
			.test(it -> ((List<Boolean>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, RandomUtil::randomByte))
			.test(it -> ((List<Byte>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, RandomUtil::randomChar))
			.test(it -> ((List<Character>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, it -> it.nextDouble()))
			.test(it -> ((List<Double>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, it -> it.nextFloat()))
			.test(it -> ((List<Float>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, it -> it.nextInt()))
			.test(it -> ((List<Integer>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, it -> it.nextLong()))
			.test(it -> ((List<Long>)it).size() == 8);
		Asserts.assertThat(RandomUtil.randomList(8, RandomUtil::randomShort))
			.test(it -> ((List<Short>)it).size() == 8);
	}
	
	@Test
	public void randomDateTime() {
		Asserts.assertThat(RandomUtil.randomInstant()).isEqualsType(Instant.class);
		Asserts.assertThat(RandomUtil.randomInstantBeforeNow()).isEqualsType(Instant.class);
		Asserts.assertThat(RandomUtil.randomInstantAfter(Instant.now())).isEqualsType(Instant.class);
		
		Asserts.assertThat(RandomUtil.randomDate()).isEqualsType(Date.class);
		Asserts.assertThat(RandomUtil.randomDateBeforeNow()).isEqualsType(Date.class);
		Asserts.assertThat(RandomUtil.randomDateAfter(Date.from(Instant.now()))).isEqualsType(Date.class);
		
		Asserts.assertThat(RandomUtil.randomLocalDate()).isEqualsType(LocalDate.class);
		Asserts.assertThat(RandomUtil.randomLocalDateBeforeNow()).isEqualsType(LocalDate.class);
		Asserts.assertThat(RandomUtil.randomLocalDateAfter(LocalDate.now())).isEqualsType(LocalDate.class);
		
		Asserts.assertThat(RandomUtil.randomLocalTime()).isEqualsType(LocalTime.class);
		Asserts.assertThat(RandomUtil.randomLocalTimeBeforeNow()).isEqualsType(LocalTime.class);
		Asserts.assertThat(RandomUtil.randomLocalTimeAfter(LocalTime.now())).isEqualsType(LocalTime.class);
		
		Asserts.assertThat(RandomUtil.randomLocalDateTime()).isEqualsType(LocalDateTime.class);
		Asserts.assertThat(RandomUtil.randomLocalDateTimeBeforeNow()).isEqualsType(LocalDateTime.class);
		Asserts.assertThat(RandomUtil.randomLocalDateTimeAfter(LocalDateTime.now())).isEqualsType(LocalDateTime.class);
	}
	
	@Test
	public void randomMap() {
		Asserts.assertThat(RandomUtil.randomMap(8, int.class, String.class))
			.test(it -> it.size() == 8);
		Asserts.assertThat(RandomUtil.randomMap(8, RandomUtil::randomSmallInt, RandomUtil::randomShortHexString))
			.test(it -> it.size() == 8);
		Asserts.assertThat(RandomUtil.randomMap(8, kr -> kr.nextInt(), vr -> vr.nextLong()))
				.test(it -> it.size() == 8);
	}
	
	@Test
	public void randomSet() {
		Asserts.assertThat(RandomUtil.randomSet(8, int.class))
			.test(it -> it.size() == 8);
		Asserts.assertThat(RandomUtil.randomSet(8, () -> RandomUtil.randomSmallInt()))
			.test(it -> it.size() == 8);
		Asserts.assertThat(RandomUtil.randomSet(8, () -> RandomUtil.randomShortHexString()))
			.test(it -> it.size() == 8);
		Asserts.assertThat(RandomUtil.randomSet(8, vr -> vr.nextLong()))
			.test(it -> it.size() == 8);
	}
	
}
