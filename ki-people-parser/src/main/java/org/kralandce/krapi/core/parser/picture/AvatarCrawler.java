package org.kralandce.krapi.core.parser.picture;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.kralandce.krapi.core.model.kraland.MKAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AvatarCrawler {

    private final static Logger logger = LoggerFactory.getLogger(AvatarCrawler.class);

    private final MKAccount.Identifier kralandIdentifier;
    private final MKAccount.AvatarURL avatarURL;

    private Exception error;
    private BufferedImage avatar;

    public AvatarCrawler(MKAccount.Identifier identifier, MKAccount.AvatarURL url) {
        this.kralandIdentifier = checkNotNull(identifier);
        this.avatarURL = checkNotNull(url);
        this.error = null;
        this.avatar = null;
    }

    public Optional<Exception> getError() {
        return Optional.ofNullable(this.error);
    }

    public Optional<BufferedImage> getAvatar() {
        return Optional.ofNullable(this.avatar);
    }

    public MKAccount.Identifier getKralandIdentifier() {
        return this.kralandIdentifier;
    }

    public MKAccount.AvatarURL getAvatarURL() {
        return this.avatarURL;
    }

    public void fetch() {
        Optional<Connection> connectionOptional = getConnection(this.avatarURL.asString());

        if( !connectionOptional.isPresent() ) {
            return;
        }

        Optional<Connection.Response> responseOptional = getResponse(connectionOptional.get());

        if( !responseOptional.isPresent() ) {
            return;
        }

        Optional<byte[]> imageAsBytesOptional = getImageAsBytes(responseOptional.get());

        if( !imageAsBytesOptional.isPresent() ) {
            return;
        }

        Optional<BufferedImage> imageOptional = getImage(imageAsBytesOptional.get());

        if( !imageOptional.isPresent() ) {
            return;
        }

        this.avatar = imageOptional.get();
    }

    private void handleException(Exception realClass, Class<? extends Exception> parentClass) {
        if( !realClass.getClass().equals(parentClass) ) {
            logger.debug("Unhandled exception : {} should be caught instead of {}", realClass.getClass(), parentClass);
            logger.debug("URL: {}", this.avatarURL.asString());

            Arrays.asList(realClass.getStackTrace()).forEach(stackTraceElement -> logger.debug(stackTraceElement.toString()));
        }

        this.error = checkNotNull(realClass);
    }

    private Optional<Connection> getConnection(String url) {
        checkNotNull(url);
        Connection connection = null;
        try {
            connection = Jsoup.connect(url);//
        }
        catch(IllegalArgumentException e) {
            handleException(e, IllegalArgumentException.class);
        }
        catch(Exception e) {
            handleException(e, Exception.class);
        }
        return Optional.ofNullable(connection);
    }

    private Optional<Connection.Response> getResponse(Connection connection) {
        checkNotNull(connection);

        Connection.Response response = null;

        try {
            response = connection.ignoreContentType(true).execute();
        }
        catch(SSLHandshakeException e) {
            handleException(e, SSLHandshakeException.class);
        }
        catch(NoRouteToHostException e) {
            handleException(e, NoRouteToHostException.class);
        }
        catch(HttpStatusException e) {
            handleException(e, HttpStatusException.class);
        }
        catch(SocketTimeoutException e) {
            handleException(e, SocketTimeoutException.class);
        }
        catch(ConnectException e) {
            handleException(e, ConnectException.class);
        }
        catch(SocketException e) {
            handleException(e, SocketException.class);
        }
        catch(UnknownHostException e) {
            handleException(e, UnknownHostException.class);
        }
        catch(IllegalArgumentException e) {
            handleException(e, IllegalArgumentException.class);
        }
        catch(IOException e) {
            handleException(e, IOException.class);
        }
        catch(Exception e) {
            handleException(e, Exception.class);
        }

        return Optional.ofNullable(response);
    }

    private Optional<byte[]> getImageAsBytes(Connection.Response response) {
        checkNotNull(response);
        return Optional.of(response.bodyAsBytes());
    }

    private Optional<BufferedImage> getImage(byte[] bytes) {
        checkNotNull(bytes);

        BufferedImage image = null;

        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        }
        catch(ArrayIndexOutOfBoundsException e) {
            handleException(e, ArrayIndexOutOfBoundsException.class);
        }
        catch(IIOException e) {
            handleException(e, IIOException.class);
        }
        catch(IllegalArgumentException e) {
            handleException(e, IllegalArgumentException.class);
        }
        catch(IOException e) {
            handleException(e, IOException.class);
        }
        catch(Exception e) {
            handleException(e, Exception.class);
        }

        return Optional.ofNullable(image);
    }

    @Override
    public String toString() {
        return "AvatarCrawler{" + "kralandIdentifier=" + this.kralandIdentifier + ", avatarURL=" + this.avatarURL + ", error="
            + this.error + '}';
    }
}
