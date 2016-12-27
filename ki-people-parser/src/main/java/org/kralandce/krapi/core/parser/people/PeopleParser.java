package org.kralandce.krapi.core.parser.people;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kralandce.krapi.core.Util;
import org.kralandce.krapi.core.bean.People;
import org.kralandce.krapi.core.bean.PeopleList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Class to 
 * @author Hello-Gitty
 *
 */
public class PeopleParser {

    final static Logger logger = LoggerFactory.getLogger(PeopleParser.class);
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        PeopleParser pp = new PeopleParser();
        pp.proceed();
    }
    
    
    public List<PeopleList> proceed() throws MalformedURLException, IOException {
        List<PeopleList> result = new ArrayList<PeopleList>();

        // On construit les liens vers les listes pour les récupérer
        for (int i = 8; i <= Constantes.NB_EMPIRE_LIST; i++) {
            Document doc = Util.getDocument(Constantes.PATH + Constantes.CIT_PREFIX + i + Constantes.SUFFIX);
            result.add(parse(doc));
        }

        for (int i = 1; i <= Constantes.NB_OTHER_LIST; i++) {
            Document doc = Util.getDocument(Constantes.PATH + Constantes.OTHER_PREFIX + i + Constantes.SUFFIX);
            result.add(parse(doc));
        }

        return result;
    }
    
    /**
     * Get people list from xml page
     * @param doc
     * @return
     */
    private PeopleList parse(Document doc) {
        PeopleList peopleList = new PeopleList();
        Elements els = doc.getElementsByTag(Constantes.TAG_TITLE);
        String title = "";
        if (els.size() >= 1) {
            title = Util.getText(els.get(0));
        }
        logger.info(title);
        peopleList.setTitle(title);
        Elements peopls = doc.getElementsByTag(Constantes.TAG_PEOPLE);
        for (Element e : peopls) {
            if (e.getElementsByTag(Constantes.TAG_ID).size() > 0) {
                peopleList.add(parsePeople(e));
            }
        }

        return peopleList;
    }
    
    /**
     * Parse people from the page
     * @param el
     * @return
     */
    private People parsePeople(Element el) {
        People p = new People();
        String name = "";
        int id = 0;
        String avatar = "";
        
        name = get(el, Constantes.TAG_NAME).get();
        id = Util.parseInt(get(el, Constantes.TAG_ID).get(), 0);
        avatar = get(el, Constantes.TAG_AVATAR).get();
        
        p.setNom(name);
        p.setId(id);
        p.setAvatar(avatar);
        
        for (String tag : Constantes.PROFILE_TAG) {
            Optional<String> val = get(el, tag);
            if (val.isPresent()) {
                p.getProfil().put(tag, val.get());
            }
        }
        
        logger.info(Util.toPrettyJson(p));
        return p;
    }
    
    /**
     * Get profile element for people
     * @param el
     * @param tag
     * @return
     */
    private Optional<String> get(Element el, String tag) {
        Elements ells = el.getElementsByTag(tag);
        Optional<String> result = Optional.empty();
        if (ells.size() > 0) {
            result = Optional.of(Util.getText(ells.get(0)));
            // Si le noeud qu'on a ramené ne contient rien, c'est qu'on a récupéré le mauvais, donc suivant
            // pb sur les noeuds area et link
            if ("".equalsIgnoreCase(result.get())) {
                result = Optional.of(Util.getText(ells.get(0).nextSibling()));
            }
        }
        return result;
    }

}
