package stockanalyzer.download;

import yahooApi.yahooFinanceIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    @Override
    public int process(List<String> urls) throws yahooFinanceIOException {
        int cnt = 0;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<String>> parallelDownload = new ArrayList<>();

        for(String url: urls){
            parallelDownload.add(executor.submit(()->saveJson2File(url)));
        }

        for(Future<String> futures : parallelDownload){
            try {
                String filename = futures.get();
                if(filename != null){
                    cnt++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new yahooFinanceIOException("Multithreading error");
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new yahooFinanceIOException("Multithreading error 2");
            }
        }

        return cnt;
    }
}