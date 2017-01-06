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
    private boolean requireAuthentication;

    public KHttpClient() {
        this.cookies = new HashMap<>();

        this.referrer = null;
        this.currentPage = null;
        this.requireAuthentication = false;
    }

    public void setRequireAuthentication(boolean requireAuth) {
        this.requireAuthentication = requireAuth;
    }

    public void connect() {
        if( isConnected() ) {
            return;
        }

        if( !this.requireAuthentication ) {
            return;
        }

        try {
            Map<String, String> tempCookies = Util.authentificationKi();
            if( tempCookies != null ) {
                this.cookies.putAll(tempCookies);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.cookies.get("PHPSESSID") != null //
            && this.cookies.get("login") != null  //
            && this.cookies.get("id") != null //
            && this.cookies.get("password") != null //
            && this.cookies.get("pc_id") != null; //
    }

    public void disconnect() {
        this.cookies.clear();
        this.referrer = null;
        this.currentPage = null;
    }

    public KHttpClient navigate(String url) {
        execute(url, connection -> {});
        return this;
    }

    public KHttpClient navigate(String url, Consumer<Connection> connectionConsumer) {
        execute(url, connectionConsumer);
        return this;
    }

    private void execute(String url, Consumer<Connection> connectionConsumer) {
        checkState(!this.requireAuthentication || isConnected());
        Connection connection = Util.getConnection(url, this.cookies, this.referrer);
        connectionConsumer.accept(connection);

        logger.debug("Visiting {} via {}", url, connection);

        Connection.Response result;

        try {
            this.currentPage = null;
            result = connection.execute();
            this.currentPage = result.parse();
            this.referrer = url;
        }
        catch(IOException e) {
            logger.error("Could not open {}", url);
        }
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
