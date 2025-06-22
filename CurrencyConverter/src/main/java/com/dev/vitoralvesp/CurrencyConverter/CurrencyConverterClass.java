package com.dev.vitoralvesp.CurrencyConverter;

import java.util.Scanner;
import java.math.BigDecimal;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class CurrencyConverterClass {
	
	public static void main(String args[]) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print(">> enter the currency to convert from (usd, brl, ...): ");
		String convertFrom = scanner.nextLine();
		System.out.print(">> enter currency to convert to (usd, brl, ...): ");
		String convertTo = scanner.nextLine();
		System.out.print(">> enter the quantity to convert (1 to Infinity): ");
		BigDecimal quantity = scanner.nextBigDecimal();
		
		String urlString = "https://api.frankfurter.dev/v1/latest?base=" + convertFrom.toUpperCase();
		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(urlString)
				.get()
				.build();
		
		Response response;
		try {
			response = client.newCall(request).execute();
			String stringResponse = response.body().string();
			JSONObject jsonObject = new JSONObject(stringResponse);
			JSONObject ratesObject = jsonObject.getJSONObject("rates");
			BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());
			BigDecimal result = rate.multiply(quantity);
			System.out.println(">> convert result: " + result);
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
