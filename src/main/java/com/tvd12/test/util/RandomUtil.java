package com.tvd12.test.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class RandomUtil {

	private static final int MAX_SMALL_SIZE = 8;
	private static final int MAX_SMALL_SIZE_EXCLUSIVE = MAX_SMALL_SIZE + 1;
	private static final String HEX_CHARS = "0123456789abcdef";
	private static final String ALPHABET_CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static final long HUNDRED_YEAR_SECONDS = 100L * 365L * 24L * 60L * 60L;
	private static final Instant MIN_INSTANT = Instant.ofEpochSecond(1);
	private static final Instant MAX_INSTANT = Instant.now().plusSeconds(HUNDRED_YEAR_SECONDS);
	private static final Date MIN_DATE = Date.from(MIN_INSTANT);
	private static final Date MAX_DATE = Date.from(MAX_INSTANT);
	private static final LocalDate MIN_LOCAL_DATE = LocalDate.of(1000, 1, 1);
	private static final LocalDate MAX_LOCAL_DATE = LocalDate.of(9999, 12, 31);
	private static final LocalDateTime MIN_LOCAL_DATE_TIME = MIN_LOCAL_DATE.atStartOfDay();
	private static final LocalDateTime MAX_LOCAL_DATE_TIME = MAX_LOCAL_DATE.atStartOfDay();
	
	private RandomUtil() {}
	
	public static <T> T random(Class<T> type) {
		if(type == boolean.class || type == Boolean.class)
			return (T)Boolean.valueOf(randomBoolean());
		if(type == byte.class || type == Byte.class)
			return (T)Byte.valueOf(randomByte());
		if(type == char.class || type == Character.class)
			return (T)Character.valueOf(randomChar());
		if(type == double.class || type == Double.class)
			return (T)Double.valueOf(randomDouble());
		if(type == float.class || type == Float.class)
			return (T)Float.valueOf(randomFloat());
		if(type == int.class || type == Integer.class)
			return (T)Integer.valueOf(randomInt());
		if(type == long.class || type == Long.class)
			return (T)Long.valueOf(randomLong());
		if(type == short.class || type == Short.class)
			return (T)Short.valueOf(randomShort());
		if(type == String.class)
			return (T)randomShortAlphabetString();
		if(type == BigDecimal.class)
			return (T)random32BitBigDecimal();
		if(type == BigInteger.class)
			return (T)random32BitBigInteger();
		if(type == Instant.class)
			return (T)randomInstant();
		if(type == Date.class)
			return (T)randomDate();
		if(type == LocalDate.class)
			return (T)randomLocalDate();
		if(type == LocalTime.class)
			return (T)randomLocalTime();
		if(type == LocalDateTime.class)
			return (T)randomLocalDateTime();
		throw new UnsupportedOperationException("can not random value of: " + type);
	}
	
	public static boolean randomBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	public static byte randomByte() {
		return (byte)ThreadLocalRandom.current().nextInt(Byte.MAX_VALUE);
	}
	
	public static char randomChar() {
		return ALPHABET_CHARS.charAt(randomInt(0, ALPHABET_CHARS.length()));
	}
	
	public static double randomDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}
	
	public static double randomDouble(double fromInclusive, double toExclusive) {
		return ThreadLocalRandom.current().nextDouble(fromInclusive, toExclusive);
	}
	
	public static double randomSmallDouble() {
		return randomDouble(0, MAX_SMALL_SIZE);
	}
	
	public static float randomFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
	
	public static float randomFloat(float fromInclusive, float toExclusive) {
		return (float)ThreadLocalRandom.current().nextDouble(fromInclusive, toExclusive);
	}
	
	public static float randomSmallFloat() {
		return randomFloat(0, MAX_SMALL_SIZE);
	}
	
	public static int randomInt() {
		return ThreadLocalRandom.current().nextInt();
	}
	
	public static int randomInt(int fromInclusive, int toExclusive) {
		return ThreadLocalRandom.current().nextInt(fromInclusive, toExclusive);
	}
	
	public static int randomSmallInt() {
		return randomInt(0, MAX_SMALL_SIZE_EXCLUSIVE);
	}
	
	public static long randomLong() {
		return ThreadLocalRandom.current().nextLong();
	}
	
	public static long randomLong(long fromInclusive, long toExclusive) {
		return ThreadLocalRandom.current().nextLong(fromInclusive, toExclusive);
	}
	
	public static short randomShort() {
		return (short)ThreadLocalRandom.current().nextInt(Short.MAX_VALUE);
	}
	
	public static short randomSmallShort() {
		return randomShort((short)0, (short)MAX_SMALL_SIZE_EXCLUSIVE);
	}
	
	public static short randomShort(short fromInclusive, short toExclusive) {
		return (short)ThreadLocalRandom.current().nextInt(fromInclusive, toExclusive);
	}
	
	public static String randomHexString(int size, boolean hasPrefix) {
		StringBuilder builder = new StringBuilder(hasPrefix ? "0x" : "");
		for(int i = 0 ; i < size ; ++i) {
			builder.append(HEX_CHARS.charAt(ThreadLocalRandom.current().nextInt(HEX_CHARS.length())));
		}
		return builder.toString();
	}
	
	public static String randomShortHexString() {
		return randomShortHexString(false);
	}
	
	public static String randomShortHexString(boolean hasPrefix) {
		return randomHexString(MAX_SMALL_SIZE_EXCLUSIVE, hasPrefix);
	}
	
	public static String randomAlphabetString(int size) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < size ; ++i) {
			builder.append(ALPHABET_CHARS.charAt(ThreadLocalRandom.current().nextInt(HEX_CHARS.length())));
		}
		return builder.toString();
	}
	
	public static String randomShortAlphabetString() {
		return randomAlphabetString(MAX_SMALL_SIZE_EXCLUSIVE);
	}
	
	public static BigInteger randomBigInteger(long fromInclusive, long toExclusive) {
		return BigInteger.valueOf(randomLong(fromInclusive, toExclusive));
	}
	
	public static BigInteger random32BitBigInteger() {
		return randomBigInteger(0, Integer.MAX_VALUE);
	}
	
	public static BigInteger random64BitBigInteger() {
		return randomBigInteger(0, Long.MAX_VALUE);
	}
	
	public static BigDecimal randomBigDecimal(double fromInclusive, double toExclusive) {
		return BigDecimal.valueOf(randomDouble(fromInclusive, toExclusive));
	}
	
	public static BigDecimal random32BitBigDecimal() {
		return randomBigDecimal(0, Float.MAX_VALUE);
	}
	
	public static BigDecimal random64BitBigDecimal() {
		return randomBigDecimal(0, Double.MAX_VALUE);
	}
	
	public static boolean[] randomBooleanArray(int size) {
		boolean[] array = new boolean[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = randomBoolean();
		}
		return array;
	}
	
	public static boolean[] randomShortBooleanArray() {
		return randomBooleanArray(MAX_SMALL_SIZE);
	}
	
	public static byte[] randomByteArray(int size) {
		byte[] bytes = new byte[size];
		ThreadLocalRandom.current().nextBytes(bytes);
		return bytes;
	}
	
	public static byte[] randomShortByteArray() {
		return randomByteArray(MAX_SMALL_SIZE);
	}
	
	public static char[] randomCharArray(int size) {
		char[] array = new char[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = randomChar();
		}
		return array;
	}
	
	public static char[] randomShortCharArray() {
		return randomCharArray(MAX_SMALL_SIZE);
	}
	
	public static double[] randomDoubleArray(int size) {
		double[] array = new double[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = ThreadLocalRandom.current().nextDouble();
		}
		return array;
	}
	
	public static double[] randomShortDoubleArray() {
		return randomDoubleArray(MAX_SMALL_SIZE);
	}
	
	public static float[] randomFloatArray(int size) {
		float[] array = new float[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = ThreadLocalRandom.current().nextFloat();
		}
		return array;
	}
	
	public static float[] randomShortFloatArray() {
		return randomFloatArray(MAX_SMALL_SIZE);
	}
	
	public static int[] randomIntArray(int size) {
		int[] array = new int[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = ThreadLocalRandom.current().nextInt();
		}
		return array;
	}
	
	public static int[] randomShortIntArray() {
		return randomIntArray(MAX_SMALL_SIZE);
	}
	
	public static long[] randomLongArray(int size) {
		long[] array = new long[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = ThreadLocalRandom.current().nextLong();
		}
		return array;
	}
	
	public static long[] randomShortLongArray() {
		return randomLongArray(MAX_SMALL_SIZE);
	}
	
	public static short[] randomShortArray(int size) {
		short[] array = new short[size];
		for(int i = 0 ; i < size ; ++i) {
			array[i] = (short)ThreadLocalRandom.current().nextInt(Short.MAX_VALUE);
		}
		return array;
	}
	
	public static short[] randomShortShortArray() {
		return randomShortArray(MAX_SMALL_SIZE);
	}
	
	public static <T> T[] randomArray(int size, Class<T> itemType) {
		T[] array = (T[])Array.newInstance(itemType, size);
		for(int i = 0 ; i < size ; ++i) {
			array[i] = random(itemType);
		}
		return array;
	}
	
	public static <T> List<T> randomList(int size, Class<T> itemType) {
		List<T> list = new ArrayList<>();
		for(int i = 0 ; i < size ; ++i) {
			list.add(random(itemType));
		}
		return list;
	}
	
	public static <T> List<T> randomList(int size, Supplier<T> random) {
		List<T> list = new ArrayList<>();
		for(int i = 0 ; i < size ; ++i) {
			list.add(random.get());
		}
		return list;
	}
	
	public static <T> List<T> randomList(int size, Function<Random, T> random) {
		List<T> list = new ArrayList<>();
		for(int i = 0 ; i < size ; ++i) {
			list.add(random.apply(ThreadLocalRandom.current()));
		}
		return list;
	}
	
	public static Instant randomInstant() {
		return randomInstant(MIN_INSTANT, MAX_INSTANT);
	}
	
	public static Instant randomInstantBeforeNow() {
		return randomInstantBefore(Instant.now());
	}
	
	public static Instant randomInstantBefore(Instant upperExclusive) {
	    return randomInstant(MIN_INSTANT, upperExclusive);
	}
	
	public static Instant randomInstantAfter(Instant startInclusive) {
	    return randomInstant(startInclusive, MAX_INSTANT);
	}
	
	public static Instant randomInstant(Instant startInclusive, Instant endExclusive) {
	    long startSeconds = startInclusive.getEpochSecond();
	    long endSeconds = endExclusive.getEpochSecond();
	    long random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);
	    return Instant.ofEpochSecond(random);
	}
	
	public static Date randomDate() {
		return randomDate(MIN_DATE, MAX_DATE);
	}
	
	public static Date randomDateBeforeNow() {
		return randomDateBefore(Date.from(Instant.now()));
	}
	
	public static Date randomDateBefore(Date upperExclusive) {
	    return randomDate(MIN_DATE, upperExclusive);
	}
	
	public static Date randomDateAfter(Date startInclusive) {
	    return randomDate(startInclusive, MAX_DATE);
	}
	
	public static Date randomDate(Date startInclusive, Date endExclusive) {
	    long startMillis = startInclusive.getTime();
	    long endMillis = endExclusive.getTime();
	    long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
	    return new Date(randomMillisSinceEpoch);
	}
	
	public static LocalDate randomLocalDate() {
		return randomLocalDate(MIN_LOCAL_DATE, MAX_LOCAL_DATE);
	}
	
	public static LocalDate randomLocalDateBeforeNow() {
		return randomLocalDateBefore(LocalDate.now());
	}
	
	public static LocalDate randomLocalDateBefore(LocalDate upperExclusive) {
	    return randomLocalDate(MIN_LOCAL_DATE, upperExclusive);
	}
	
	public static LocalDate randomLocalDateAfter(LocalDate startInclusive) {
	    return randomLocalDate(startInclusive, MAX_LOCAL_DATE);
	}
	
	public static LocalDate randomLocalDate(LocalDate startInclusive, LocalDate endExclusive) {
	    long startEpochDay = startInclusive.toEpochDay();
	    long endEpochDay = endExclusive.toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
	    return LocalDate.ofEpochDay(randomDay);
	}
	
	public static LocalTime randomLocalTime() {
		return randomLocalTime(LocalTime.MIN, LocalTime.MAX);
	}
	
	public static LocalTime randomLocalTimeBeforeNow() {
		return randomLocalTimeBefore(LocalTime.now());
	}
	
	public static LocalTime randomLocalTimeBefore(LocalTime upperExclusive) {
	    return randomLocalTime(LocalTime.MIN, upperExclusive);
	}
	
	public static LocalTime randomLocalTimeAfter(LocalTime startInclusive) {
	    return randomLocalTime(startInclusive, LocalTime.MAX);
	}
	
	public static LocalTime randomLocalTime(LocalTime startTime, LocalTime endTime) {
	    int startSeconds = startTime.toSecondOfDay();
	    int endSeconds = endTime.toSecondOfDay();
	    int randomTime = ThreadLocalRandom
	      .current()
	      .nextInt(startSeconds, endSeconds);

	    return LocalTime.ofSecondOfDay(randomTime);
	}
	
	public static LocalDateTime randomLocalDateTime() {
		return randomLocalDateTime(MIN_LOCAL_DATE_TIME, MAX_LOCAL_DATE_TIME);
	}
	
	public static LocalDateTime randomLocalDateTimeBeforeNow() {
		return randomLocalDateTimeBefore(LocalDateTime.now());
	}
	
	public static LocalDateTime randomLocalDateTimeBefore(LocalDateTime upperExclusive) {
	    return randomLocalDateTime(MIN_LOCAL_DATE_TIME, upperExclusive);
	}
	
	public static LocalDateTime randomLocalDateTimeAfter(LocalDateTime startInclusive) {
	    return randomLocalDateTime(startInclusive, MAX_LOCAL_DATE_TIME);
	}
	
	public static LocalDateTime randomLocalDateTime(LocalDateTime startTime, LocalDateTime endTime) {
	    long startSeconds = startTime.toEpochSecond(ZoneOffset.UTC);
	    long endSeconds = endTime.toEpochSecond(ZoneOffset.UTC);
	    long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);
	    return LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC);
	}
}
