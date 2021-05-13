package stockanalyzer.download;

import yahooApi.yahooFinanceIOException;

import java.util.List;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<String> tickers) throws yahooFinanceIOException {
        int count = 0;
        for (String ticker : tickers) {
            String fileName = saveJson2File(ticker);
            if(fileName != null)
                count++;
        }
        return count;
    }
}
