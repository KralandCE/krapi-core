package org.kralandce.krapi.core.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
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

/**
 * 
 * @author Hello-Gitty
 *
 */
public class EventParser {

    private Events theEvenements;

    //private static final String LOCAL_PAGE = "file:///C:/Users/Dart/Desktop/Kraland/workon/Kraland%20Interactif%20-%20Cybermonde%20-%20%C3%89v%C3%A9nements.htm";
    private static final String LOCAL_PAGE = "";
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

    public Events proceed() throws MalformedURLException, IOException {
        // Check if need to specify the timezone

        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(1);
        this.theEvenements = new Events();
        this.theEvenements.setJour(localDate);
        
        Connection conn = Jsoup.connect("http://www.kraland.org/main.php?p=4_4");
        
        Document    
        doc =  conn.url("http://www.kraland.org/main.php?p=4_4&t=211cc3&a=2&p1=10").referrer("http://www.kraland.org/main.php?p=4_4&t=211cc3&a=2&p1=10").get();
        
        System.out.println(doc);
        
        // the date of the event that we need
        String eventDate = localDate.toString(); //"2016-03-22";// 
        
        String beforeEventDate =  localDate.minusDays(1).toString(); //"2016-03-21"; //
        

        // GET a PAGE
        
        // Lookup for DATE
        Elements listJour = doc.getElementsByClass("forum-c3");
        int page = 0;
        // Lookup for page list
        if (listJour.size() > 1) {
            Element pageList = listJour.get(0);
            pageList = pageList.previousElementSibling();
            // nombre de page
            page = pageList.child(0).childNodeSize()/2;
        }
        
        boolean stop = false;
        do {
          
          stop = parseCenter(listJour, eventDate, beforeEventDate);
          // TODO need plus eleguant
          if (!stop) {
              page = page - 1;
              //    Jsoup.connect("http://www.kraland.org/main.php?p=4_4_"+page";
              doc = getPageByPass(LOCAL_PAGE);
              listJour = doc.getElementsByClass("forum-c3");
          }
        // stop if no page or event found for the day before
        } while (page != 0 && !stop);
        
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
                    // TODO to be Test
                    theDayBeforeFound = theDayBefore.equalsIgnoreCase(Util.getFullText(sib));
                }
                sib = sib.nextElementSibling();
            }
        }
        return theDayBeforeFound;
    }
    
    
    private static Connection copyCookie(Connection.Response res, Connection conn) {
        if (res != null && conn != null) {
            Map<String, String> cookies = res.cookies();
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                conn.cookie(entry.getKey(), entry.getValue());
               // System.out.println(entry.getKey());
            }
        }
        return conn;
    }
    
    private static String getHiddenT(Document doc) {
        
        return doc.getElementById("report-col3").getElementsByAttributeValue("name", "t").get(0).attr("value");
    }
    
    /**
     * 
     * @return
     * @throws IOException
     */
    private static Connection.Response authentificationKi() throws IOException {
        Connection.Response res = null;
        if (EventParserParam.AUTHENFICATION.isVal()) {
            res = Jsoup.connect("http://www.kraland.org/main.php?p=5&a=100")
                    .data("p1", EventParserParam.KI_SLAVE_LOGIN.getVal(), "p2", EventParserParam.KI_SLAVE_PASS.getVal(),
                            "Submit", "Ok")
                    .method(Method.POST).execute();
        } else {
            res = Jsoup.connect("http://www.kraland.org/main.php").method(Method.GET).execute();
        }
        return res;
    }
    
    
    
    /**
     * Only for test for now.
     * 
     * @param args
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        //Util.toPrettyJson(new EventParser().proceed());
        Connection conn = null;
        Connection.Response res = null;
        Document doc = null;
        
        
        
        Connection.Response resref = authentificationKi();
        
        conn = Jsoup.connect("http://www.kraland.org/main.php?p=4_4").method(Method.GET);
        conn = copyCookie(resref, conn);
        
        // Connetion à la page
        res = conn.execute();
        doc = res.parse();
        
        // on récupère la page de tous les évènements.
        Elements ellls = doc.getElementsByAttributeValueMatching("title", "Rapport complet");
        
        String lien = "http://www.kraland.org/" +ellls.get(0).attr("href");
        System.out.println(lien);
        conn = Jsoup.connect(lien).method(Method.GET);
        conn.referrer(lien).header("Host", "www.kraland.org");
        conn = copyCookie(resref, conn);
        
        
        res = conn.execute();
        doc = res.parse();
        //System.out.println(doc);
        ellls = doc.getElementsByAttributeValueMatching("title", "Rapport complet");
        lien = "http://www.kraland.org/" +ellls.get(0).attr("href");
        
        conn = Jsoup.connect("http://www.kraland.org/main.php").method(Method.POST);
        conn.referrer(lien)
        .header("Host", "www.kraland.org")
        .data("p","4_4")
        .data("p7","Tous+les+empires+%2B+provinces+et+villes")
        .data("a","1")
        .data("Submit","Actualiser")
        .data("p1","250")
        .data("p2","on").data("t",getHiddenT(doc));
        conn = copyCookie(resref, conn);
                
        res = conn.execute();
        doc = res.parse();
        System.out.println(doc);
        
        // là on a la page principale de travail.
        // On doit ensuite faire des get des autres pages avec le bon referrer
        // les cookies et le bon lien 
        // et c'est gagné.
        
        
        

    }

    private Document getPageByPass(String url) throws MalformedURLException, IOException {
        // TODO quand on pourra de nouveau voir les evenement
        // trouver comment bypass la securite serveur exterieur tout ca [:f]
        return Util.getDocument(url);
    }

    //
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
        // TODO Trouver comment récupérer proprement les images etc.
        result.setData(Util.getFullText(node.childNode(2)));

        String heure = Util.getText(node.childNode(1));
        heure = heure.replace("\u00A0", "").trim(); // Espace insecable pourri
        // TODO plus joli ?
        result.setDateHeure(LocalDateTime.parse(eventDate + "T" + heure));
        result.setVille(ville);
        result.setProvince(province);
        result.setEmpire(empire);
        System.out.println(Util.toPrettyJson(result));
        return result;
    }

}
