package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import stockanalyzer.download.Downloader;
import stockanalyzer.download.ParallelDownloader;
import stockanalyzer.download.SequentialDownloader;

import stockanalyzer.ctrl.Controller;
import yahooApi.yahooFinanceIOException;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		try {
			ctrl.process("AAPL");	//starting process method from controller class
		} catch (yahooFinanceIOException e) {
			e.printStackTrace(); 		//prints the throwable along with other details like the line number and class name where the exception occurred
		}

	}

	public void getDataFromCtrl2(){
		try {
			ctrl.process("AMZN");
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl3(){
		try {
			ctrl.process("TSLA");
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}

	}
/*
	public void getDataForCustomInput() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter stock name: ");
		String ticker = scan.nextLine();

		try{
			System.out.println(ctrl.process(ticker));
		}
		catch(yahooFinanceIOException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
*/

	private void getDownloadData() {
		long start, end;
		Downloader sequentialDownloader = new SequentialDownloader();
		Downloader parallelDownloader = new ParallelDownloader();
		List<String> tickers = Arrays.asList("FB","TSLA","MSFT","NFLX","NOK","GOOG","GME","AAPL","BTC-USD","DOGE-USD","ETH-USD",
				"OMV.VI","EBS.VI","DOC.VI","SBO.VI","RBI.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI","WIE.VI","CAI.VI","BG.VI",
				"POST.VI","LNZ.VI","UQA.VI","SPI.VI");

		try{
			start = System.currentTimeMillis();
			ctrl.downloadTickers(tickers,sequentialDownloader);
			end = System.currentTimeMillis();
			System.out.printf("Sequential Download Timer: %dms\n",end-start);

			start = System.currentTimeMillis();
			ctrl.downloadTickers(tickers, parallelDownloader);
			end = System.currentTimeMillis();
			System.out.printf("Parallel Download Timer: %dms\n",end-start);
		}
		catch(yahooFinanceIOException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}


	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		//menu.insert("d", "User Choice",this::getDataForCustomInput);
		menu.insert("e","Download Tickerlist", this::getDownloadData);

		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
