package org.kralandce.krapi.core;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Ricorei
 */
public final class KHttpClient {

    private final static Logger logger = LoggerFactory.getLogger(KHttpClient.class);

    private final Map<String, String> cookies;
    private String referrer;
    private Document currentPage;
    private String login;
    private String password;
    private Connection.Response currentResult;

    public KHttpClient() {
        this.cookies = new HashMap<>();

        this.referrer = null;
        this.currentPage = null;
        this.currentResult = null;
        this.login = null;
        this.password = null;
    }

    public void openSession() {
        if( isSignedIn() ) {
            return;
        }

        if( this.login != null && this.password != null ) {
            execute(CommonConst.URL_AUTH_KRALAND, connection -> {
                connection.method(Connection.Method.POST).data("p1", this.login, "p2", this.password, "Submit", "Ok");
            });
        }
        else {
            execute(CommonConst.URL_KRALAND_MAIN);
        }

        this.cookies.clear();
        this.cookies.putAll(this.currentResult.cookies());
    }

    public boolean isSignedIn() {
        return isSessionOpen() //
            && this.cookies.get("login") != null  //
            && this.cookies.get("id") != null //
            && this.cookies.get("password") != null //
            && this.cookies.get("pc_id") != null; //
    }

    public boolean isSessionOpen() {
        return this.cookies.get("PHPSESSID") != null;
    }

    public void disconnect() {
        this.cookies.clear();
        this.referrer = null;
        this.currentPage = null;
    }

    private void execute(String url) {
        execute(url, connection -> {});
    }

    private void execute(String url, Consumer<Connection> connectionConsumer) {
        Connection connection = Util.getConnection(url, this.cookies, this.referrer);
        connectionConsumer.accept(connection);

        logger.debug("Visiting {}", url);

        try {
            this.currentPage = null;
            this.currentResult = connection.execute();
            this.currentPage = this.currentResult.parse();
            this.referrer = url;
        }
        catch(IOException e) {
            logger.error("Could not open {}", url);
        }
    }

    public KHttpClient navigate(String url) {
        checkState(isSessionOpen());
        execute(url);
        return this;
    }

    public KHttpClient navigate(String url, Consumer<Connection> connectionConsumer) {
        checkState(isSessionOpen());
        execute(url, connectionConsumer);
        return this;
    }

    public Optional<Document> getPage() {
        return Optional.of(this.currentPage);
    }

    public KHttpClient click(Function<Document, String> documentToUrl) {
        checkNotNull(documentToUrl);
        checkState(this.currentPage != null);
        String url = documentToUrl.apply(this.currentPage);
        return navigate(url);
    }

    public <T> T find(Function<Document, T> documentConsumer) {
        checkNotNull(documentConsumer);
        checkState(this.currentPage != null);
        return documentConsumer.apply(this.currentPage);
    }
}
