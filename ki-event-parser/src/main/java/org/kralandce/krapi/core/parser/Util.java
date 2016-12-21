package org.kralandce.krapi.core.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A bunch of usefull thing
 * @author hello-gitty
 *
 */
public class Util {
	
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

	public static String toPrettyJson(Object oo) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(oo);
	}

	public static Document getDocument(String url) throws MalformedURLException, IOException {
		return Jsoup.parse(new URL(url).openStream(), Constantes.ISO, url);
	}
	
}
