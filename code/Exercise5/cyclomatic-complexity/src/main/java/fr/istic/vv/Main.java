package fr.istic.vv;

import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // On vérifie que le nombre de paramètres fournit vaut bien 2
        if(args.length != 2) {
            System.err.println("Should provide the path to the source code and the path to the output file");
            System.exit(1);
        }

        // On vérifie et initialise le projet à analyser
        File file = new File(args[0]);
        if(!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);
        }
        
        // On initialise le chemin du fichier d'écriture des résultats
        String filePath = args[1];
        SourceRoot root = new SourceRoot(file.toPath());
        // On initialise notre writer qui va récupérer et écrire les informations voulues
        CcWriter writer = new CcWriter(filePath);
        root.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(writer, null));
            return SourceRoot.Callback.Result.DONT_SAVE;
        });
        // On ferme le fichier d'écriture
        writer.closeWriter();
        // On récupère le tableau de répartition qui va nous permetre de générer un histogramme
        int[] CC = writer.getRepartitionCC();
        Histogram.create(CC);
    }


}
