import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apfloat.Apfloat;
import java.math.BigInteger;

public class Main {

    static int numThreads;
    static int precision;
    static String outputFile = "result.txt";
    static boolean quiet = false;
    static eThread[] array;
    static Apfloat sum;
    static Options options;


	/*public static void createOptions() {
	options.addOption("p", true, "select precision point");
	options.addOption("t", true, "enter number of threads");
	options.addOption("o", false, "name of the putput file");
	options.addOption("q", false, "flag - make the regime quite");
	}
    */
    
    public static void main(String[] args) throws FileNotFoundException {
       
      /*	options = new Options();
		createOptions();
	    CommandLineParser parser = new DefaultParser();
	    try {
	        // parse the command line arguments
	        CommandLine line = parser.parse( options, args );
	        String pOption = line.getOptionValue("p");
	        String tOption = line.getOptionValue("t");
	        String oOption = line.getOptionValue("o");
	        String qOption = line.getOptionValue("q");
	        
	        System.out.println(pOption);
	        precision = Integer.parseInt(pOption);
	        numThreads =  Integer.parseInt(tOption);
	        quiet = true;
	    }
	    catch( ParseException exp ) {
	        // oops, something went wrong
	        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
	    }    */
    	precision = 10000;
    	numThreads = 16;
	    long timeOfStart = Calendar.getInstance().getTimeInMillis();
        if (precision <= 0 || numThreads <= 0) {
            System.out.println("ERROR: Args are not correct!!!");
        } else {
            array = new eThread[numThreads];
            BigInteger termsOfThread = BigInteger.valueOf(precision).divide(BigInteger.valueOf(16)).divide(BigInteger.valueOf(numThreads));
            
            while(termsOfThread.compareTo(BigInteger.valueOf(100)) > 0) {
                termsOfThread = termsOfThread.divide(BigInteger.TEN);
            }
            
            BigInteger currentTerm = BigInteger.ZERO;
            sum = new Apfloat(0, precision);

            while (true) {
                for (int t = 0; t < numThreads; t++) {
                    eThread thread = new eThread("Thread " + Integer.toString(t),
                            precision, numThreads,
                            /*firstTerm = */ currentTerm,
                            /*lastTerm = */ currentTerm.add(termsOfThread), quiet);
                    thread.start();
                    array[t] = thread;
                    currentTerm = currentTerm.add(termsOfThread);
                }

                for (int i = 0; i < numThreads; i++) {
                    try {
                        array[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sum = sum.add(array[i].sum);
                }

                if (!quiet) {
                //    System.out.println("sum = " + sum.toString());
                }
                
                // Calculate currentTerm-th term
                Apfloat nth = array[numThreads - 1].lastTermValue;
                if (sum.equals(sum.add(nth))) {
                    break;
                }
            }
        
            long timeOfEnd = Calendar.getInstance().getTimeInMillis();
        // Write to file
        try {
            PrintStream file = new PrintStream(outputFile);
            file.println(sum);
        } catch (FileNotFoundException fnf) {
            System.out.println("File " + outputFile + " not found.");
        }

        System.out.println("Time of calculate: " + (timeOfEnd - timeOfStart) + " ms.");
        if (!quiet) {
            System.out.println("Result: " + sum);
        }
    }
    }
    // Evaluate n!
    public static BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) // n <= 1
            return BigInteger.ONE;
        else
            return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }
}