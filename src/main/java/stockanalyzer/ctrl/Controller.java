package stockanalyzer.ctrl;
import stockanalyzer.download.Downloader;

import yahooApi.yahooFinanceIOException;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class Controller {

	Stock stock = null;

	public void process(String ticker) throws yahooFinanceIOException {
		System.out.println("Start process");

		//TODO implement Error handling
		//TODO implement methods for
		//1) Daten laden
		//2) Daten Analyse

		try {
			stock = YahooFinance.get(ticker); //getting symbol

			Calendar from = Calendar.getInstance();
			from.add(Calendar.DAY_OF_MONTH, -30); //last 30 days

			var result = stock.getHistory(from, Interval.DAILY).stream() //detects automatically the datatype of a variable
					.mapToDouble(q -> q.getClose().doubleValue())
					.max()
					.orElse(0.0);

			var result2 = stock.getHistory().stream()
					.mapToDouble(q -> q.getClose().doubleValue())
					.average()
					.orElse(0.0);


			var result3 = stock.getHistory().stream()
					.mapToDouble(q -> q.getClose().doubleValue())
					.count();


			System.out.println("=====");
			System.out.println(ticker);
			System.out.println("=====");

			System.out.println("= = = =  maximum course = = = =");
			System.out.println(result);

			System.out.println();
			System.out.println("= = = =  average course = = = =");
			System.out.println(result2);

			System.out.println();
			System.out.println("= = = =  amount = = = =");
			System.out.println(result3);

		} catch (IOException e) {
			e.printStackTrace();
		}


		/*
		try {
			stock = yahoofinance.YahooFinance.get("AAPL");
			stock.getHistory().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	

	public Object getData(String searchString) {
		/*YahooFinance yahoofinance = new YahooFinance();
		YahooResponse yahooresponse = yahoofinance.getCurrentData(Arrays.asList(searchString.split(",")));
		return yahooresponse.getQuoteResponse();*/
		return null;
	}

	public void downloadTickers(List<String> tickers, Downloader downloader) throws yahooFinanceIOException {
		downloader.process(tickers);
	}


	public void closeConnection() {
		
	}
}
