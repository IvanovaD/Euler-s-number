
import org.apfloat.Apfloat;
import java.math.BigInteger;
import java.util.Calendar;

public class eThread extends Thread {

    String nameThread;
    int precision;
    int numThread;
    BigInteger firstTerm;
    BigInteger lastTerm;
    Apfloat lastTermValue;
    boolean quiet;
    long timeOfStart;
    long timeOfEnd;
    Apfloat sum;
    
    public eThread(String nameThread, int precision, int numThread, BigInteger firstTerm, BigInteger lastTerm, boolean quiet) {
        this.nameThread = nameThread;
        this.precision = precision;
        this.numThread = numThread;
        this.firstTerm = firstTerm;
        this.lastTerm = lastTerm;
        this.quiet = quiet;
        sum = new Apfloat(0, precision);
    }

    public void run() {
        timeOfStart = Calendar.getInstance().getTimeInMillis();
        if (!quiet) {
            System.out.println(nameThread + " is starting! Calculate from " + firstTerm + " to " + lastTerm + " term!");
        }

        // Calculate firstTerm value
        Apfloat numerator = new Apfloat(firstTerm.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE), precision);
        Apfloat denominator;
        if (firstTerm.compareTo(BigInteger.ZERO) == 0) {
            denominator = new Apfloat(1, precision);
        } else {
            denominator = new Apfloat(factorial(firstTerm.multiply(BigInteger.valueOf(2))), precision);
        }
        sum = sum.add(numerator.divide(denominator));

        // Calculate other terms
        for (BigInteger t = firstTerm.add(BigInteger.ONE); t.compareTo(lastTerm) < 0; t = t.add(BigInteger.ONE)) {
            numerator = numerator.add(new Apfloat(2, precision));
            denominator = denominator.multiply(new Apfloat(t.multiply(BigInteger.valueOf(2)).multiply(t.multiply(BigInteger.valueOf(2)).subtract(BigInteger.ONE)), precision));
           
            sum = sum.add(numerator.divide(denominator));
        }
        // Calculate lastTerm value
        numerator = numerator.add(new Apfloat(2, precision));
        denominator = denominator.multiply(new Apfloat(lastTerm.multiply(BigInteger.valueOf(2)).multiply(lastTerm.multiply(BigInteger.valueOf(2).subtract(BigInteger.ONE))), precision));
        lastTermValue = numerator.divide(denominator);

        timeOfEnd = Calendar.getInstance().getTimeInMillis();

        if (!quiet) {
       //     System.out.println(nameThread + " is finishing!\nIt works " + (timeOfEnd - timeOfStart) + " ms.\nSub-sum is " + sum.toString() + "!\n");
        }
    }

    // Evaluate n!
    public static BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) // n <= 1
        {
            return BigInteger.ONE;
        } else {
            return n.multiply(factorial(n.subtract(BigInteger.ONE)));
        }
    }
}