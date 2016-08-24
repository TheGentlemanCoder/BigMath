import java.math.BigDecimal;
import java.math.MathContext;

//@author Nicholas Ryan Smith
//santasmith123@hotmail.com

public class BigMath {
	
	private static final int precision = 100;
	private static final int maxIteration = 1000;
	// TODO: implement ability to hold pi to arbitrary precision
	private static final BigDecimal pi = new BigDecimal("3.14159265358979323846264338327950288419716939937510582097494459");
	
	public static MathContext getContext() {
		return new MathContext(precision);
	}

	public static BigDecimal factorial(BigDecimal n) {
		if (n.equals(1) || n.equals(0)) {
			return BigDecimal.ONE;
		} else {
			BigDecimal factorial = new BigDecimal(1);
			for (BigDecimal i = n; i.compareTo(BigDecimal.ZERO) > 0; i = i.subtract(BigDecimal.ONE)) {
				factorial = factorial.multiply(i);
			}
			
			return factorial;
		}
	}
	
	public static BigDecimal radiansToDegrees(BigDecimal radians) {
		return radians.multiply(new BigDecimal(180), getContext()).divide(pi, getContext());
	}
	
	public static BigDecimal degreesToRadians(BigDecimal degrees) {
		return degrees.multiply(pi, getContext()).divide(new BigDecimal(180), getContext());
	}
	
	// An adaptation of the infite series form
	// of the cosine function to accomadate arbitrary
	// percision, found at:
	//
	// http://mathworld.wolfram.com/Cosine.html
	
	public static BigDecimal cos(BigDecimal angle) {
		BigDecimal approximateCos = new BigDecimal(0);
		
		for (int n = 0; n < maxIteration; n++) {
			BigDecimal addOrSub = new BigDecimal(Math.pow(-1, n));
			BigDecimal angleToN = angle.pow(2 * n);
			BigDecimal factorial = factorial(new BigDecimal(2 * n));
			BigDecimal fraction = angleToN.divide(factorial, BigMath.getContext());
			fraction = fraction.multiply(addOrSub);
			
			approximateCos = approximateCos.add(fraction);
		}
		
		return approximateCos;
	}
	
	public static BigDecimal sin(BigDecimal angle) {
		BigDecimal approximateSin = new BigDecimal(0);
		
		for (int n = 1; n < maxIteration; n++) {
			BigDecimal addOrSub = new BigDecimal(Math.pow(-1, n - 1));
			BigDecimal angleToN = angle.pow((2*n) - 1);
			BigDecimal factorial = factorial(new BigDecimal((2*n -1)));
			BigDecimal fraction = angleToN.divide(factorial, BigMath.getContext());
			fraction = fraction.multiply(addOrSub);
			
			approximateSin = approximateSin.add(fraction);
		}
		
		return approximateSin;
	}
	
	public static BigDecimal tan(BigDecimal angle) {
		return sin(angle).divide(cos(angle), getContext());
	}
	
	// 
	
	public static BigDecimal calculatePiTo(BigDecimal digit) {
		BigDecimal approximatePi = new BigDecimal(0);
		BigDecimal two = new BigDecimal(2);
		
		for (int n = 0; n < maxIteration; n++) {
			BigDecimal twoToNPlusOne = two.pow(n+1);
			BigDecimal nFactorialSquared = factorial(new BigDecimal(n)).pow(2);
			BigDecimal twoNPlusOneFactorial = factorial(two.multiply(new BigDecimal(n)).add(BigDecimal.ONE));
			approximatePi = twoToNPlusOne.multiply(nFactorialSquared).divide(twoNPlusOneFactorial, getContext());
		}
		
		return approximatePi;
	}
	
	public static void main(String[] args) {
		System.out.println("PI: " + calculatePiTo(pi));
	}
}
