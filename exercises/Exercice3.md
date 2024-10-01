# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](./designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

Après avoir générer la règle avec javaFX, on lance le pmd dans un projet qui comprend des if imbriqués pour voir quels avertissements le pmd va signaler. 

En lançant l'analyse statique avec une règle spécifique (rule.xml) avec cette commande : pmd check -f text -R ../code/Exercise3/rule.xml -d commons-collections -r commons-collections/report
On remarque dans report qu'il détecte bien les if imbriqués 3 fois ou plus.
commons-collections/src/main/java/org/apache/commons/collections4/bidimap/TreeBidiMap.java:2138:	Rule_3if:	Détection de 3 if imbriqué

ligne 2138 dans TreeBidiMap.java
        if (y == xFormerParent) { // y was x's parent
            y.setParent(x, dataElement);

            if (xWasLeftChild) {
                x.setLeft(y, dataElement);
                x.setRight(yFormerRightChild, dataElement);
            } else {
                x.setRight(y, dataElement);
                x.setLeft(yFormerLeftChild, dataElement);
            }
        } else {
            y.setParent(xFormerParent, dataElement);

            if (xFormerParent != null) {
                if (xWasLeftChild) {
                    xFormerParent.setLeft(y, dataElement);
                } else {
                    xFormerParent.setRight(y, dataElement);
                }
            }

            x.setLeft(yFormerLeftChild, dataElement);
            x.setRight(yFormerRightChild, dataElement);
        }

On vérifie manuellement si l'avertissement est vrai et qu'il y a réellement 3 if imbriqué ou plus. 

