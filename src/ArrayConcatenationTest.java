import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrayConcatenationTest {

	private static final int arraySize = 500000;

	public static void main(String[] args) {
		Double[] a = randomArray();
		Double[] b = randomArray();

		long start1 = System.nanoTime();
		Double[] c = concatenate(a, b);
		long end1 = System.nanoTime();

		System.out.println(end1 - start1 + "ns");

		long start2 = System.nanoTime();
		Double[] d = ArrayUtils.addAll(a, b);
		long end2 = System.nanoTime();

		System.out.println(end2 - start2 + "ns, " + (end2 - start2) / (end1 - start1) + " times slower than method 1");

		long start3 = System.nanoTime();
		List<Double> doubleList = new ArrayList<Double>(a.length + b.length);
		doubleList.addAll(Arrays.asList(a));
		doubleList.addAll(Arrays.asList(b));
		Double[] e = doubleList.toArray(new Double[doubleList.size()]);
		long end3 = System.nanoTime();

		System.out.println(end3 - start3 + "ns, " + (end3 - start3) / (end1 - start1) + " times slower than method 1");
	}

	private static Double[] randomArray() {
		Double[] aArray = new Double[arraySize];
		for (int i = 0; i < aArray.length; i++) {
			aArray[i] = randomFill();
		}

		return aArray;
	}

	private static double randomFill() {
		Random rand = new Random();
		return rand.nextDouble();
	}

	public static <T> T[] concatenate (T[] a, T[] b) {
		int aLen = a.length;
		int bLen = b.length;

		@SuppressWarnings("unchecked")
		T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);

		return c;
	}
}
