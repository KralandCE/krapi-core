package org.kralandce.krapi.core.parser.event;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.kralandce.krapi.core.AuthentificationParam;
import org.kralandce.krapi.core.CommonConst;
import org.kralandce.krapi.core.PropertiesHandler;
import org.kralandce.krapi.core.Util;
import org.kralandce.krapi.core.bean.CybermondeDatas;
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
    private CybermondeDatas datas;

    /*
     * Algo:
     * 
     * Batch à lancer une fois par jour, sera chargé de récupérer les évènements de tous les empires de J-1.
     * Pour ça il faut récupérer tous les évènements de tous les empires, par 250 ou moins peut importe de toute façon il faut explorer la page pour:
     * -Récupérer le nombre de pages existantes pour les évènements
     * -Dans chaque page trouver la cellule de tableau qui contient la date (th class="forum-c3"), si la date correspond on parcours le tableau
     * -Pour chaque ligne on trouve l'empire/Province/ville/heure et on parse 
     * 
     */

    /**
     * Proceed to find event from J-1
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public Events proceed() throws MalformedURLException, IOException {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(1);
        return proceed(localDate);
    }
    
    /**
     * Proceed to find event for the day dateToCapute
     * @param dateToCapute
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public Events proceed(LocalDate dateToCapute) throws MalformedURLException, IOException {
        
        // Check if need to specify the timezone

        LocalDate localDate = dateToCapute;
        this.theEvenements = new Events();
        this.theEvenements.setJour(localDate);
        this.datas = new CybermondeDatas();
        
        // the date of the event that we need
        String eventDate = localDate.toString(); //"2016-03-22";//  
        String beforeEventDate = localDate.minusDays(1).toString(); // "2016-03-21"; //
        
        Connection conn = null;
        Connection.Response res = null;
        Document doc = null;
        
        Map<String, String> cookies = Util.authentificationKi();
        
        conn = Util.getConnection(Constantes.URL_EVENT_KRALAND, cookies, null);
        // Connetion à la page
        res = conn.execute();
        doc = res.parse();
        
        // on récupère la page de tous les évènements.
        String lien = getUrlAllEvent(doc);
        
        conn = Util.getConnection(lien, cookies, lien);
        res = conn.execute();
        doc = res.parse();
        
        // On positionne le selecteur d'empire et nb element
        // et on raffraichit la page
        lien = getUrlAllEvent(doc);
        conn = Util.getConnection(Constantes.URL_KRALAND_MAIN, cookies, lien);
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

        
        Elements selectPage = doc.getElementsByClass(Constantes.CLASS_EVENT_PAGE_SELECT);
        int pageTotal = 0;
        // Lookup for page list
        if (!selectPage.isEmpty()) {
            Element pageList = selectPage.get(0);
            pageList = pageList.parent();
            // nombre de page
            pageTotal = pageList.childNodeSize()/2;
            
        }
        logger.debug("Nombre de page : "+ pageTotal);
        int pageCur = pageTotal;
        // Lookup for DATE
        Elements listJour = doc.getElementsByClass(Constantes.CLASS_EVENT_DATE);
        boolean stop = false;
        do {
            stop = parseCenter(listJour, eventDate, beforeEventDate);
            // TODO need plus eleguant
            pageCur = pageCur - 1;
            if (!stop) {
                conn = Util.getConnection(Constantes.URL_EVENT_KRALAND + "_" + pageCur, cookies, lien);
                res = conn.execute();
                doc = res.parse();
                listJour = doc.getElementsByClass(Constantes.CLASS_EVENT_DATE);
            }
        // stop if no page or event found for the day before
        } while (pageCur != 0 && !stop);
        logger.debug("Page lues : " + (pageTotal - pageCur));
        
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
                if (!sib.hasClass(Constantes.CLASS_EVENT_DATE) && sib.childNodeSize() == 3) {
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
    
    
    private String getHiddenT(Document doc) {
        return doc.getElementById("report-col3").getElementsByAttributeValue("name", "t").get(0).attr("value");
    }
    

    private String getUrlAllEvent(Document doc) {
        Elements el = doc.getElementsByAttributeValueMatching("title", "Rapport complet");
        String result = Constantes.URL_KRALAND +el.get(0).attr("href");
        return result;
    }
    
    
    /**
     * Only for test for now.
     * 
     * @param args
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        PropertiesHandler.load(AuthentificationParam.class);
        EventParser eventP = new EventParser();
        LocalDate date = null;
        if (args.length > 0) {
            String dateToParse = args[0];
            try {
            date = LocalDate.parse(dateToParse);
            } catch (DateTimeParseException dtpe) {
                date = null;
                logger.error("Date :" + dateToParse + " not a valid format, required YYYY-MM-DD");
            }
        }
        Events events = null;
        if (date != null) {
            events = eventP.proceed(date);
        } else {
            events = eventP.proceed();
        }
        
        logger.info("NB event: " + events.getEvents().size());
        //logger.info((Util.toPrettyJson(events)));
        
        
        String fichier = "ki-event-" + date.toString();
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fichier), CommonConst.UTF_8));
        writer.write((Util.toPrettyJson(events)));
        writer.close();
        
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

        // TODO hello-gitty à terme remplacer l'event par l'immutable
        //ImmutableKEvent.Builder builder = ImmutableKEvent.builder();
                
        
        Optional<String> empire = Optional.empty();
        Optional<String> province = Optional.empty();
        Optional<String> ville = Optional.empty();
        
        // Si au moins un noeud alors on récupére l'empire
        if (loca.childNodeSize() > 0) {
            Node empi = loca.childNode(0);
            if (empi.hasAttr(Constantes.ATTR_SRC)) {
                String im = empi.attr(Constantes.ATTR_SRC);
                im = im.substring(im.length() - 5, im.length() - 4);
                int pos = Util.parseInt(im) - 1;
                empire = Optional.of(Constantes.EMPIRE_LIST[pos]);
            }
        }
        // Si plus d'un noeud, le second c'est la province
        if (loca.childNodeSize() > 1) {
            province = Optional.of(Util.getText(loca.childNode(1)));
        }
        // Si plus d'un noeud, le troisieme c'est la ville
        if (loca.childNodeSize() > 2) {
            ville = Optional.of(Util.getText(loca.childNode(2)));
        }
        
        String data = node.childNode(2).toString();
        
        String type = null;
        if (data.startsWith(Constantes.TD_EVENT_ANIM)) {
            data = data.substring(Constantes.TD_EVENT_ANIM.length());
            type = "anim";
        } else if ( data.startsWith(Constantes.TD_EVENT_NORMAL)) {
            data = data.substring(Constantes.TD_EVENT_NORMAL.length());
            type = "normal";
        } else if ( data.startsWith(Constantes.TD_EVENT_MAJ)) {
            data = data.substring(Constantes.TD_EVENT_MAJ.length());
            type = "maj";
        }
        if (type != null) {
            data = data.substring(0, data.length() - 5);
        }

        result.setData(data);
        result.setType(type);
        
        
        String heure = Util.getText(node.childNode(1));
        heure = heure.replace("\u00A0", "").trim(); // Espace insecable pourri
        result.setDateHeure(LocalDateTime.parse(eventDate + "T" + heure));
        
        result.setVille(ville);
        result.setProvince(province);
        result.setEmpire(empire);
        
        if (ville.isPresent()) {
            this.datas.addVilles(ville.get());
        }
        if (province.isPresent()) {
            this.datas.addProvinces(province.get());
        }
        if (empire.isPresent()) {
            this.datas.addEmpire(empire.get());    
        }

        return result;
    }
    
    /**
     * get cybermonde data with empire, province and ville found
     * @return
     */
    public CybermondeDatas getDatas() {
        return datas;
    }


}
