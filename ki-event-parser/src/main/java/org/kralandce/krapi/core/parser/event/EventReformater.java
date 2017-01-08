package org.kralandce.krapi.core.parser.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.kralandce.krapi.core.CommonConst;
import org.kralandce.krapi.core.Util;
import org.kralandce.krapi.core.bean.Event;
import org.kralandce.krapi.core.bean.Events;



/**
 * Classe temporaire uniquement pour la migration des évènements du format sans type vers le format avec
 * @author Hello-Gitty
 *
 */
public class EventReformater {

    public static void main(String[] args) throws Exception {
     
        if (args.length < 1) {
            return;
        }
        // recupération du fichier.
        String fileName = args[0];
        File file = new File(fileName);
        
        if (!file.exists()) {
            return;
        }
        // on récupère les évènements
        Events evt =  Util.fromJson(file, Events.class);
         
        // On reformat les data pour récupérer le type
        for (Event e : evt.getEvents()) {
            String data = e.getData();
            String type = "Undefined";
            if (data.startsWith(Constantes.TD_EVENT_ANIM)) {
                data = data.substring(Constantes.TD_EVENT_ANIM.length());
                type = "anim";
            } else if ( data.startsWith(Constantes.TD_EVENT_NORMAL)) {
                data = data.substring(Constantes.TD_EVENT_NORMAL.length());
                type = "normal";
            }
            data = data.substring(0, data.length() - 5);
            e.setData(data);
            e.setType(type);
        }
        // On resauvegarde
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileName)), CommonConst.UTF_8));
        writer.write((Util.toPrettyJson(evt)));
        writer.close();
        
    }
    
    
}
