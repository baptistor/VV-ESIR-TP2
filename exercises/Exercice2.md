
# Using PMD


Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset (see the [pmd install instruction](./pmd-help.md)). Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.


## Answer

tp1/src/rationnel/RationnelCouple.java:64:	UselessParentheses:	Useless parentheses.
RationnelCouple.java:64 		return (r.getNumerateur() == this.first && r.getDenominateur() == this.second);

Dans ce cas, PMD trouve une erreur qui est solvable donc c'est un vrai positif. En effet, les parrenthèses autour du return ne sont pas nécessaires et sont considérés comme un code smells. Elles ne créent pas de bug mais sont un motif qui rend le code plus difficile à comprendre.

On pourrait corriger la ligne 64 de cette manière pour ne plus avoir cette erreur : return r.getNumerateur() == this.first && r.getDenominateur() == this.second;

tp1/src/util/Client.java:11:	CloseResource:	Ensure that resources like this Scanner object are closed after use
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Rationnel r0 = makeRationnel(0, 1);
		Rationnel[] tab1= new Rationnel[10];
		Rationnel r2 = makeRationnel(0, 1);

		int i=0;
		while (true){
			Rationnel r1 = lireRationnel(scanner);
			System.out.print("courant =" + r1.toString() + " ; ");
			System.out.print(r0.toString() + " + " + r1.toString() + " = " + r0.somme(r1).toString() + " ; inverse = " + r1.inverse() + " ; valeur = " + r1.valeur()+ " ; ");
			System.out.print(r1.toString()+" "+lireComparaison(r0, r1)+" "+r0.toString()+" ; ");
			System.out.println(r1.toString()+lireEqual(r1, r0)+r0.toString());

			if (r2.equals(r1)){
				insererRationnel (r1, tab1, i);
				System.out.println("Tableau des valeurs :" );
				afficher(tab1, i);
				System.out.println("Somme des valeurs du tableau : " + sommeRationnels(tab1, i).valeur());
				break;

			}
			else {
				insererRationnel (r1, tab1, i);
				System.out.println("Tableau des valeurs :" );
				afficher(tab1, i);
				System.out.println("Somme des valeurs du tableau : [" +sommeRationnels(tab1, i).toString()+" = " + sommeRationnels(tab1, i).valeur()+"]");
				r0=r1;
			}
			i++;
		
		}
		scanner.close();

	}

   Dans ce cas, PMD signale que le flux du scanner pourrait ne pas être correctement fermé. Cependant, dans le contexte d'une méthode main, le flux d'entrée System.in reste ouvert pendant toute la durée de vie du programme et est automatiquement libéré à la fin. Ne pas fermer explicitement le scanner ne causera donc pas de problème ici, sauf si le programme plante avant la fermeture. Ainsi, on peut considérer cet avertissement de PMD comme un faux positif, car il n'y a pas de véritable erreur dans ce contexte particulier.