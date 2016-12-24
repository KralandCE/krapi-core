package org.kralandce.krapi.core.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.kralandce.krapi.core.bean.Event;
import org.kralandce.krapi.core.bean.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Hello-Gitty
 *
 */
public class EventParser {

    final static Logger logger = LoggerFactory.getLogger(EventParser.class);
    
    private Events theEvenements;

    /*
     * Algo:
     * 
     * Batch à lancer une fois par jour, sera chargé de récupérer les évènements de tous les empires de J-1.
     * Pour ça il faut récupérer tous les évènements de tous les empires, par 250 ou moins peut importe de toute façon il faut explorer la page pour:
     * -Récupérer le nombre de pages existantes pour les évènements
     * -Dans chaque page trouver la cellule de tableau qui contient la date (th class="forum-c3"), si la date correspond on parcours le tableau
     * -Pour chaque ligne on trouve l'empire/Province/ville/heure et on persiste 
     * 
     * PB:
     * Le formulaire des évènements et les sécurités relou: tentative depuis un serveur externe. // 
     */
    
    
    
    
    private static Connection getConnection(String url, Map<String, String> cookies, String referer) {
        Connection result = null;
        result = Jsoup.connect(url).method(Method.GET).header("Host", "www.kraland.org");
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
    

    public Events proceed() throws MalformedURLException, IOException {
        
        // Check if need to specify the timezone

        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(1);
        this.theEvenements = new Events();
        this.theEvenements.setJour(localDate);
        
        // TODO passer une date en param
        // the date of the event that we need
        String eventDate = localDate.toString(); //"2016-03-22";//  
        String beforeEventDate = localDate.minusDays(1).toString(); // "2016-03-21"; //
        
        Connection conn = null;
        Connection.Response res = null;
        Document doc = null;
        
        
        Map<String, String> cookies = authentificationKi();
        
        conn = getConnection("http://www.kraland.org/main.php?p=4_4", cookies, null);
        
        // Connetion à la page
        res = conn.execute();
        doc = res.parse();
        
        // on récupère la page de tous les évènements.
        Elements ellls = doc.getElementsByAttributeValueMatching("title", "Rapport complet");
        String lien = "http://www.kraland.org/" +ellls.get(0).attr("href");
        
        System.out.println(lien);
        conn = getConnection(lien, cookies, lien);
        res = conn.execute();
        doc = res.parse();
        
        ellls = doc.getElementsByAttributeValueMatching("title", "Rapport complet");
        lien = "http://www.kraland.org/" +ellls.get(0).attr("href");
        
        conn = getConnection("http://www.kraland.org/main.php", cookies, lien);
        conn.method(Method.POST)
        .data("p","4_4")
        .data("p7","Tous+les+empires+%2B+provinces+et+villes")
        .data("a","1")
        .data("Submit","Actualiser")
        .data("p1","250")
        .data("p2","on").data("t",getHiddenT(doc));
                
        res = conn.execute();
        doc = res.parse();
        // là on a la page principale de travail.
        // On doit ensuite faire des get des autres pages avec le bon referrer
        // les cookies et le bon lien 
        // et c'est gagné.

        
        Elements selectPage = doc.getElementsByClass("forum-selpage");
        int page = 0;
        // Lookup for page list
        if (!selectPage.isEmpty()) {
            Element pageList = selectPage.get(0);
            pageList = pageList.parent();
            // nombre de page
            page = pageList.childNodeSize()/2;
            logger.debug("Nombre de page : "+ page);
        }
        
        // Lookup for DATE
        Elements listJour = doc.getElementsByClass("forum-c3");
        boolean stop = false;
        do {
          
          stop = parseCenter(listJour, eventDate, beforeEventDate);
          // TODO need plus eleguant
          if (!stop) {
              page = page - 1;
              conn = getConnection("http://www.kraland.org/main.php?p=4_4_"+page, cookies, lien);
              res = conn.execute();
              doc = res.parse();
              listJour = doc.getElementsByClass("forum-c3");
          }
        // stop if no page or event found for the day before
        } while (page != 0 && !stop);
        logger.debug("Page restante : " + page);
        
        return this.theEvenements;
    }
    

    
    public boolean parseCenter(Elements listJour, String day, String theDayBefore) {
        boolean theDayBeforeFound = false;
         
        
        Element found = null;
        for (Element el : listJour) {
            String valDateNode = Util.getFullText(el);
            if (day.equalsIgnoreCase(valDateNode)) {
                found = el;
            }
        }

        if (found != null) {
            Element sib = found.nextElementSibling();
            boolean sep = false;
            while (sib != null && !sep) {
                if (!sib.hasClass("forum-c3") && sib.childNodeSize() == 3) {
                    Event ev = parseNode(day, sib);
                    this.theEvenements.add(ev);
                } else {
                    // on a fini de traiter les évènements du jour
                    sep = true;
                    theDayBeforeFound = theDayBefore.equalsIgnoreCase(Util.getFullText(sib));
                }
                sib = sib.nextElementSibling();
            }
        }
        return theDayBeforeFound;
    }
    
    
    private static String getHiddenT(Document doc) {
        return doc.getElementById("report-col3").getElementsByAttributeValue("name", "t").get(0).attr("value");
    }
    
    /**
     * Handle authentification if needed for cookie
     * @return
     * @throws IOException
     */
    private static Map<String, String> authentificationKi() throws IOException {
        Connection.Response res = null;
        if (EventParserParam.AUTHENFICATION.isValue()) {
            Connection conn = getConnection("http://www.kraland.org/main.php?p=5&a=100", null, null);
            conn.data("p1", EventParserParam.KI_SLAVE_LOGIN.getValue(), "p2", EventParserParam.KI_SLAVE_PASS.getValue(),
                    "Submit", "Ok").method(Method.POST).execute();
            res = conn.execute();
        } else {
            res = getConnection("http://www.kraland.org/main.php", null, null).execute();
        }
        Map<String, String> cookies = res.cookies();

        logger.debug("cookies: ", cookies);
        return cookies;
    }
    
    
    
    /**
     * Only for test for now.
     * 
     * @param args
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        PropertiesHandler.load();
        EventParser eventP = new EventParser();
        Events events = eventP.proceed();
        logger.info("NB event: " + events.getEvents().size());
        logger.info((Util.toPrettyJson(events)));
    }


    /**
     * Parse a tr node to an event
     * 
     * @param node
     * @return an event
     */
    private Event parseNode(String eventDate, Element node) {
        Event result = new Event();
        Node loca = node.childNode(0);
        // TODO CONSTANTES
        String[] listEmpire = { "RK", "EB", "PC", "TS", "PV", "KE", "CL", "RR", "PI" };
        String empire = "";
        String province = "";
        String ville = "";

        Node empi = loca.childNode(0);
        if (empi.hasAttr("src")) {
            String im = empi.attr("src");
            im = im.substring(im.length() - 5, im.length() - 4);
            int pos = Util.parseInt(im) - 1;
            empire = listEmpire[pos];
        }
        // Si plus d'un noeud, le second c'est la province
        if (loca.childNodeSize() > 1) {
            province = Util.getText(loca.childNode(1));
        }
        // Si plus d'un noeud, le troisieme c'est la ville
        if (loca.childNodeSize() > 2) {
            ville = Util.getText(loca.childNode(2));
        }
        result.setData(node.childNode(2).toString());

        String heure = Util.getText(node.childNode(1));
        heure = heure.replace("\u00A0", "").trim(); // Espace insecable pourri
        result.setDateHeure(LocalDateTime.parse(eventDate + "T" + heure));
        result.setVille(ville);
        result.setProvince(province);
        result.setEmpire(empire);
        return result;
    }

}
