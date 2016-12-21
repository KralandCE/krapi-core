package org.kralandce.krapi.core.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.kralandce.krapi.core.bean.Event;

/**
 *  
 * @author Hello-Gitty
 *
 */
public class EventParser {

        
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
    
    
    
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        
        // Check if need to specify the timezone
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sfd = new SimpleDateFormat("YYYY-MM-DD");
        // the date of the event that we need
        String eventDate = sfd.format(cal.getTime());
        
        
        // TODO à mettre dans des constantes.
        // Document doc = Util.getDocument();
        Connection.Response res = Jsoup.connect("http://www.kraland.org/main.php?p=5&a=100")
                .data("p1", EventParserParam.KI_SLAVE_LOGIN.getVal(), "p2", EventParserParam.KI_SLAVE_PASS.getVal(),
                        "Submit", "Ok")
                .method(Method.POST)
                .execute();
        Document docRes = res.parse();
          
        Connection conn = Jsoup.connect("http://www.kraland.org/main.php?p=4_4&t=11a46f&a=2&p1=10")
                .referrer("Referer: http://www.kraland.org/main.php?p=4_4");
        String[] cookies = {"PHPSESSID", "FUSEGUID", "id" ,"login", "password", "pc_id", "pus-idv"};
        for (String cooki : cookies) {
            if (res.cookie(cooki) != null) {
            conn.cookie(cooki, res.cookie(cooki));
            } else {
                System.out.println(cooki);
            }

        }
        
        Document doc = conn.get();

        System.out.println(doc);
        Elements listJour = doc.getElementsByClass("forum-c3");
        for (Element el : listJour) {
            System.out.println(Util.getFullText(el));
        }
    }
    
    //
    /**
     * Parse a tr node to an event
     * @param node
     * @return an event
     */
    private static Event parseNode(Node node) {
        return null;
    }
    
    
    
}
