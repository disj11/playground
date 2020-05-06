package jsoup.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

public abstract class Crawler {
    // jsoup 은 기본적으로 Minetype 이 text/*, application/xml, or application/*+xml 인 것만 허용함
    private static final Pattern filters = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private final CrawlerConfig config;

    public Crawler() {
        this.config = new CrawlerConfig();
    }

    public Crawler(CrawlerConfig config) {
        this.config = config;
    }

    /**
     * 발견된 페이지를 파싱하여 document 형태로 넘겨줌
     * @param doc 발견된 페이지
     */
    abstract void visit(Document doc);

    /**
     * 크롤링 시작
     * @param url 크롤링할 주소
     * @throws IOException html doc 파싱 중 exception
     */
    public void start(String url) throws IOException {
        Queue<String> queue = new LinkedList<>();
        queue.add(url);

        int endIndex = url.indexOf("/", url.indexOf("://") + 3);
        endIndex = endIndex < 0 ? url.length() : endIndex;

        String crawlDomain = url.substring(0, endIndex);
        start(crawlDomain, queue);
    }

    private void start(String crawlDomain, Queue<String> urlQueue) throws IOException {
        int depth = 0;
        List<String> visitUrl = new ArrayList<>();

        // BFS 로 검색된 url 순회하며 파싱
        while (!urlQueue.isEmpty()) {
            // 지정했던 depth 가 넘으면 종료
            if (depth > config.getMaxDepth()) {
                break;
            }

            int size = urlQueue.size();
            for (int i = 0; i < size; i++) {
                String url = urlQueue.poll();

                // url 이 null / 방문한 적이 있는 url 있음 / 크롤링할 도메인이 아님 / 파싱할 수 없는 url
                if (url == null || visitUrl.contains(url) || !url.startsWith(crawlDomain) || filters.matcher(url).matches()) {
                    continue;
                }
                visitUrl.add(url);

                // html doc 파싱 후 visit 메서드 호출해줌
                Document doc = Jsoup.parse(new URL(url), config.getTimeoutMills());
                visit(doc);

                Elements elements = doc.getElementsByTag("a");
                if (elements.isEmpty()) {
                    continue;
                }

                // 발견된 url 이 있으면 큐에 추가
                for (Element element : elements) {
                    String absHref = element.absUrl("href").trim();
                    if (!absHref.isEmpty()) {
                        urlQueue.add(absHref);
                    }
                }
            }

            // depth 증가
            depth++;
        }
    }
}
