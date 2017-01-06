package org.kralandce.krapi.core.parser.event;

import com.google.common.collect.ImmutableList;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kralandce.krapi.core.KHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Ricorei
 */
public final class EventCrawler {

    private final static Logger logger = LoggerFactory.getLogger(EventCrawler.class);

    private EventCrawler() {

    }

    public static ImmutableList<Document> fetch(KHttpClient kHttpClient) {

        kHttpClient.openSession();
        kHttpClient.navigate(Constantes.URL_EVENT_KRALAND);

        kHttpClient.click(document -> {
            Elements el = document.getElementsByAttributeValueMatching("title", "Rapport complet");
            return Constantes.URL_KRALAND + el.get(0).attr("href");
        });

        String hiddenFormValue = kHttpClient.find(
            document -> document.getElementById("report-col3").getElementsByAttributeValue("name", "t").get(0).attr("value"));

        kHttpClient.navigate(Constantes.URL_KRALAND_MAIN, connection -> {
            connection.method(Connection.Method.POST)
                .data("p", "4_4")
                .data("p7", "Tous+les+empires+%2B+provinces+et+villes")
                .data("a", "1")
                .data("Submit", "Actualiser")
                .data("p1", "250")
                .data("p2", "on")
                .data("t", hiddenFormValue);
        });

        Elements selectPage = kHttpClient.find(document -> document.getElementsByClass(Constantes.CLASS_EVENT_PAGE_SELECT));

        int expectedPageCount = 0;

        if( !selectPage.isEmpty() ) {
            Element pageList = selectPage.get(0);
            pageList = pageList.parent();
            expectedPageCount = pageList.childNodeSize() / 2;
        }

        logger.debug("Nombre de page : {}", expectedPageCount);

        ImmutableList.Builder<Document> documentBuilder = ImmutableList.builder();

        for( int currentPage = 1; currentPage <= expectedPageCount; currentPage++ ) {

            String currentPageURl = Constantes.URL_EVENT_KRALAND + "_" + currentPage;

            kHttpClient.navigate(currentPageURl);

            Optional<Document> optionalDocument = kHttpClient.getPage();

            optionalDocument.ifPresent(documentBuilder::add);
        }

        ImmutableList<Document> fetchedPages = documentBuilder.build();

        if( fetchedPages.isEmpty() || expectedPageCount != fetchedPages.size() ) {
            return ImmutableList.of();
        }
        else {
            return fetchedPages;
        }
    }
}
