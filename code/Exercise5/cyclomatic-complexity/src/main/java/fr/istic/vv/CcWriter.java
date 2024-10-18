package fr.istic.vv;

import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class CcWriter extends VoidVisitorWithDefaults<Void> {
    // Attributs de classe
    private BufferedWriter writer ;
    private int[] repartitionCC = new int[6];
    // Constructeur
    CcWriter(String filePath){
        // On utilise le super constructeur puis on tente d'ouvrir notre fichier d'écriture
        super();
        try {
            FileWriter file = new FileWriter(filePath);
            this.writer = new BufferedWriter(file);
            writer.write("Package | Class | Method | Parameters | CC\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    // On parcours les fichiers sources Java
    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    // On parcourt toutes les méthodes d'une classe et on récupère les informations voulues
    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        String packageName = declaration.getFullyQualifiedName().orElse("Anonymous");
        String className = declaration.getNameAsString();
        for(MethodDeclaration method : declaration.getMethods()){
            String methodName = method.getNameAsString();
            String param = method.getParameters().toString();
            // Pour calculer la complexité cyclomatique
            int cc = methodCC(method);
            try {
                // On écrit les informations dans le fichier
                writer.write(packageName + " | " + className + " | " + methodName + " | " + param + " | " + cc + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    // On calcule la complexité cyclomatique de la méthode fournie en paramètre : nombre de points de décision + 1
    private int methodCC(MethodDeclaration method){
        int c = 1;
        List<IfStmt> listIf = method.findAll(IfStmt.class);
        List<SwitchEntry> listCase = method.findAll(SwitchEntry.class);
        List<ForStmt> listFor = method.findAll(ForStmt.class);
        List<WhileStmt> listWhile = method.findAll(WhileStmt.class);
        List<DoStmt> listDo = method.findAll(DoStmt.class);
        List<TryStmt> listTry = method.findAll(TryStmt.class);
        c+= listIf.size() + listCase.size() + listFor.size() + listWhile.size() + listDo.size() + listTry.size();
        sortCC(c);
        return c;
    }

    // On ferme le fichier d'écriture
    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // On place chaque valeur de complexité cyclomatique dans un tableau qui va nous permettre de générer un histogramme
    private void sortCC(int c){
        if (c<=2){
            repartitionCC[0]++;
        }
        else if(c<=4){
            repartitionCC[1]++;
        }
        else if(c<=6){
            repartitionCC[2]++;
        }
        else if(c<=8){
            repartitionCC[3]++;
        }
        else if(c<=10){
            repartitionCC[4]++;
        }
        else {
            repartitionCC[5]++;
        }
    }

    // Getter du tableau de répartition
    public int[] getRepartitionCC(){
        return repartitionCC;
    }
}
