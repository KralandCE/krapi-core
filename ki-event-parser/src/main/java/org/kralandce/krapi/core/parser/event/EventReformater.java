package org.kralandce.krapi.core.parser.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.kralandce.krapi.core.CommonConst;
import org.kralandce.krapi.core.Util;
import org.kralandce.krapi.core.bean.Event;
import org.kralandce.krapi.core.bean.Events;

/**
 * Classe temporaire uniquement pour la migration des évènements du format sans
 * type vers le format avec
 * 
 * @author Hello-Gitty
 *
 */
public class EventReformater {

    public static void main(String[] args) throws Exception {

        File local = new File(".");
        // filtre pour recupérer tous les fichiers
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("ki-event-");
            }
        };

        File[] files = local.listFiles(fileFilter);
        // parcours 
        for (File fil : files) {
            handle(fil);
        }
    }

    /**
     * traitement d'un fichier
     * @param file
     * @throws IOException
     */
    public static void handle(File file) throws IOException {

        Events evt = Util.fromJson(file, Events.class);
        String fileName = file.getName();

        // On reformat les data pour récupérer le type
        for (Event e : evt.getEvents()) {
            String data = e.getData();
            String type = null;
            if (e.getType() == null || e.getType().isEmpty()) {
                if (data.startsWith(Constantes.TD_EVENT_ANIM)) {
                    data = data.substring(Constantes.TD_EVENT_ANIM.length());
                    type = "anim";
                } else if (data.startsWith(Constantes.TD_EVENT_NORMAL)) {
                    data = data.substring(Constantes.TD_EVENT_NORMAL.length());
                    type = "normal";
                } else if (data.startsWith(Constantes.TD_EVENT_MAJ)) {
                    data = data.substring(Constantes.TD_EVENT_MAJ.length());
                    type = "maj";
                }
                if (type != null) {
                    data = data.substring(0, data.length() - 5);
                }

                e.setData(data);
                e.setType(type);
            }
        }
        // On resauvegarde
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(new File(fileName)), CommonConst.UTF_8));
        writer.write((Util.toPrettyJson(evt)));
        writer.close();
    }

}
