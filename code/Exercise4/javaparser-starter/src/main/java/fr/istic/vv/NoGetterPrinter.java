package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import java.util.List;
import java.util.ArrayList;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class NoGetterPrinter extends VoidVisitorWithDefaults<Void> {

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        List<String> methodList = new ArrayList<>();
        for(MethodDeclaration method : declaration.getMethods()){
            if(!method.isPrivate() && method.getNameAsString().startsWith("get")){
                String s = method.getNameAsString().substring(3);
                s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
                methodList.add(s);
            }
        }
        for(FieldDeclaration field : declaration.getFields()) {
            if (field.isPrivate()){
                for (VariableDeclarator variable : field.getVariables()){
                    if(!methodList.contains(variable.getNameAsString())){
                        System.out.println(variable.getNameAsString());
                    }
                }
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

}
