package org.kralandce.krapi.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;


/**
 * A bunch of usefull thing
 * @author hello-gitty
 * @param <E>
 *
 */
public class Util {

    final static Logger logger = LoggerFactory.getLogger(Util.class);

	// private
	private Util() {
	}

	public static String getText(Node node) {

		if (node.childNodes().size() == 0) {
			return Jsoup.parse(node.toString()).text();
		}
		return getText(node.childNode(0));
	}

	public static String getFullText(Node node) {

		if (node.childNodes().size() == 0) {
			return Jsoup.parse(node.toString()).text();
		}

		String result = "";
		for (Node no : node.childNodes()) {
			result +=getFullText(no) + " ";
		}

		return result.trim();
	}

	public static float parseFloat(String ss) {
		float result = 0f;

		try {
			result = Float.parseFloat(ss);
		} catch (Exception e) {
			// Rien
		}

		return result;
	}

	public static double parseDouble(String ss) {
		double result = 0d;

		try {
			result = Double.parseDouble(ss);
		} catch (Exception e) {
			// Rien
		}

		return result;
	}

	public static int parseInt(String ss) {
		return parseInt(ss, 1);
	}

	public static int parseInt(String ss, int defaut) {
		int result = defaut;

		try {
			result = Integer.parseInt(ss);
		} catch (Exception e) {
			// Rien
		}

		return result;
	}

	public static String toJson(Object oo) {
		Gson gson = new Gson();
		return gson.toJson(oo);
	}
	
	public static <E> E fromJson (File data, Class<E> clazz) throws IOException {
	    Gson gson = new GsonBuilder().create();
	    JsonReader jr = gson.newJsonReader(new FileReader(data));
	    E oo = gson.fromJson(jr, clazz);
	    jr.close();
	    return oo;
	}
	

	public static String toPrettyJson(Object oo) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(oo);
	}

	public static Document getDocument(String url) throws IOException {
		return Jsoup.parse(new URL(url).openStream(), CommonConst.ISO, url);
	}

	/**
     * Get an object connection with referer and cookie setted
     * @param url url to get
     * @param cookies cookies for the new connection
	 * @param referer
	 * @return a connection
	 */
    public static Connection getConnection(String url, Map<String, String> cookies, String referer) {
        Connection result = null;
        result = Jsoup.connect(url).method(Method.GET).header("Host", CommonConst.HOST_KRALAND);
        if (referer != null) {
            result.referrer(referer);
        }
        if (cookies != null) {
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                result.cookie(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

	/**
     * Handle authentification if needed for cookie
     * @return
     * @throws IOException
     */
    public static Map<String, String> authentificationKi() throws IOException {
        Connection.Response res = null;
        if (AuthentificationParam.AUTHENFICATION.isValue()) {
            Connection conn = getConnection(CommonConst.URL_AUTH_KRALAND, null, null);
            conn.data("p1", AuthentificationParam.KI_SLAVE_LOGIN.getValue(), "p2", AuthentificationParam.KI_SLAVE_PASS.getValue(),
                    "Submit", "Ok").method(Method.POST).execute();
            res = conn.execute();
        } else {
            res = getConnection(CommonConst.URL_KRALAND_MAIN, null, null).execute();
        }
        Map<String, String> cookies = res.cookies();

		logger.debug("cookies: {}", cookies);
		return cookies;
    }
}
