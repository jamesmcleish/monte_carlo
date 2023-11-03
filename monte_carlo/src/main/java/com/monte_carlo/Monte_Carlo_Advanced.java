package monte_carlo.src.main.java.com.monte_carlo;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import monte_carlo.src.main.java.com.monte_carlo.FetchPrices;

public class Monte_Carlo_Advanced {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numWalks;
        int numDays;
        double initialPrice;
        double meanReturn = 0.01;
        double volatility = 0.2;
        String ticker;
        Random random = new Random();


        do {
            System.out.print("Enter a value for S (number of days) (1-365): ");
            numDays = scanner.nextInt();
        } while (numDays < 1 || numDays > 365); // keep inputs in range specified

        do {
            System.out.print("Enter a value for N (number of walks) (1-100000): ");
            numWalks = scanner.nextInt();
        } while (numWalks < 1 || numWalks > 100000);

        System.out.println("Enter a stock ticker symbol from the FTSE100 to generate initial price");
        ticker = scanner.next();
        do {
            initialPrice = getPriceByTicker(ticker);
        } while(false);

        System.out.println(simulateGBM(initialPrice, meanReturn, volatility, numDays, random, numWalks));
    }

    public static List<Double> simulateGBM(double initialPrice, double meanReturn, double volatility, int numDays, Random random, int numWalks) {
        double price = initialPrice;
        List<Double> priceList = new ArrayList<>();

        for (int walk = 0; walk < numWalks; walk++){
        for (int day = 0; day < numDays; day++) {
            // Generate a random daily return using GBM formula
            double dailyReturn = meanReturn / 252.0 + volatility * random.nextGaussian() * Math.sqrt(1.0 / 252.0);
            price *= Math.exp(dailyReturn);
        }
        priceList.add(price);
        }
        return priceList;
    }

    public static Double getPriceByTicker(String ticker){
        return FetchPrices.priceQuote(ticker);
    }

}