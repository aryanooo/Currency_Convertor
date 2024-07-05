package CurrencyConvertorApp;

import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the base currency (e.g., USD , EUR):");
        String baseCurrency = scanner.next().toUpperCase();
        
        System.out.println("Enter the amount in base currency:");
        double amount = scanner.nextDouble();
        
        System.out.println("Enter the target currency (e.g., GBP):");
        String targetCurrency = scanner.next().toUpperCase();
        
        String apiKey = "f0838577565caf28fcfcda9e"; // Replace with your actual API key
        String baseUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/";
        String fullUrl = baseUrl + baseCurrency + "/" + targetCurrency + "/" + amount; // Assuming EUR as base currency

        double conversionResult = 0;
        try {
            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            conn.disconnect();
            
            System.out.println(content);
            
            // Using regex to find the conversion rate
            Pattern pattern = Pattern.compile("\"conversion_result\":\\s*([\\d\\.]+)");
            Matcher matcher = pattern.matcher(content.toString());
            if (matcher.find()) {
                conversionResult = Double.parseDouble(matcher.group(1));
                System.out.println("Converted Amount: " + conversionResult);
            } else {
                System.out.println("Error: Unable to find conversion result.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Ensure resources are closed even if an exception occurs
            try {
                if (scanner != null) {
                    scanner.close(); // Close the scanner
                }
            } catch (Exception e) {
                System.out.println("Error closing scanner: " + e.getMessage());
            }
        }
    }
}
