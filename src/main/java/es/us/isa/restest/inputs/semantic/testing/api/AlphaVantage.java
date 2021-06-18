package es.us.isa.restest.inputs.semantic.testing.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static es.us.isa.restest.inputs.semantic.testing.MainTesting.printResponse;

public class AlphaVantage {

    private static final String baseUri = "https://alpha-vantage.p.rapidapi.com";

    // /query?function=TIME_SERIES_WEEKLY&symbol=MSFT&datatype=json (TIME_SERIES_WEEKLY)
    // symbol
    public static void alphaVantage_timeSeriesWeekly_symbol(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?function=TIME_SERIES_WEEKLY&symbol=" + semanticInput;
        printResponse(url);
    }

    // /query?from_symbol=EUR&to_symbol=USD&function=FX_MONTHLY&datatype=json (FX_MONTHLY)
    // from_symbol
    public static void alphaVantage_fxMonthly_fromSymbol(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?from_symbol=" + semanticInput + "&to_symbol=USD&function=FX_MONTHLY";
        printResponse(url);
    }

    // to_symbol
    public static void alphaVantage_fxMonthly_toSymbol(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?from_symbol=EUR&to_symbol=" + semanticInput + "&function=FX_MONTHLY";
        printResponse(url);
    }

    // /query?to_currency=JPY&function=CURRENCY_EXCHANGE_RATE&from_currency=USD (CURRENCY_EXCHANGE_RATE)
    // from_currency
    public static void alphaVantage_currencyExchangeRate_fromCurrency(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?to_currency=JPY&function=CURRENCY_EXCHANGE_RATE&from_currency=" + semanticInput;
        printResponse(url);
    }

    // to_currency
    public static void alphaVantage_currencyExchangeRate_toCurrency(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?to_currency=" + semanticInput + "&function=CURRENCY_EXCHANGE_RATE&from_currency=USD";
        printResponse(url);
    }

    // REGEX GENERATION
    // /query?to_currency=JPY&function=CURRENCY_EXCHANGE_RATE&from_currency=USD (CURRENCY_EXCHANGE_RATE)
    // from_currency
    public static String alphaVantage_currencyExchangeRate_fromCurrency_regex(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?to_currency=BOB&function=CURRENCY_EXCHANGE_RATE&from_currency=" + semanticInput;
        System.out.println(url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-host", host)
                .addHeader("x-rapidapi-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("RESPONSE CODE: " + response.code());
        System.out.println("--------------------------------------------------------------------------------------");

        return response.body().string();
    }

    // to_currency
    public static String alphaVantage_currencyExchangeRate_toCurrency_regex(String semanticInput, String apiKey, String host) throws IOException {
        String url = baseUri + "/query?to_currency=" + semanticInput + "&function=CURRENCY_EXCHANGE_RATE&from_currency=BOB";

        System.out.println(url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-host", host)
                .addHeader("x-rapidapi-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("RESPONSE CODE: " + response.code());
        System.out.println("--------------------------------------------------------------------------------------");

        return response.body().string();
    }



}
