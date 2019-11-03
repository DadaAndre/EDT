package edt.tests;

/**
* Permet de réaliser différents tests et d'obtenir le résultat de chacun d'entre eux dans la sortie standard
*
* <p>La classe étant voulue pour être utilisée de manière purement statique, le constructeur y est donc privé afin d'en empêcher une quelconque instanciation.</p>
*
* <p>Les tests sont formalisés de la manière suivante :</p>
* <pre>
* 	{@code
* 	[PrecedenceConstraintWithGap]
* 	    1 - true expected : OK
* 	    2 - true expected : OK
* 	    3 - true expected : KO
* 	    4 - true expected : OK
* 	3/4 test(s) réussi(s)
*
* 	[PrecedenceConstraint]
* 	    1 - true expected : KO
* 	    2 - true expected : OK
* 	    3 - false expected : KO
* 	1/3 test(s) réussi(s)
*
* 	Bilan global : 4/7 test(s) réussi(s)
* 	}
* </pre>
*/
public class UnitTest {

	/**
	* Permet de connaître le nombre total de tests effectués
	*/
	private static int totalTest = 0;
	/**
	* Permet de connaître le nombre total de tests échoués
	*/
	private static int nbEchecTotal = 0;

	/**
	* Permet de connaître le nombre de tests effectués pour la série de test en cours
	*/
	private static int numTest = 0;
	/**
	* Permet de connaître le nombre de tests échoués pour la série de test en cours
	*/
	private static int nbEchec = 0;

	/**
	* Permet de connaître le libellé de la série de test en cours
	*/
	private static String testLabel = null;


	/**
	* Constructeur privé pour empêcher la création d'une nouvelle instance
	*/
	private UnitTest() {}


	/**
	* Vérifie que le test réalisé vaut bien true et affiche le résultat
	*
	* @param test Résultat du test effectué
	* @return True si le test vaut bien true, false sinon
	* @see #processTest
	*/
	public static boolean isTrue(boolean test) {
		return processTest(test == true, "true");
	}


	/**
	* Vérifie que le test réalisé vaut bien false et affiche le résultat
	*
	* @param test Résultat du test effectué
	* @return True si le test vaut bien false, false sinon
	* @see #processTest
	*/
	public static boolean isFalse(boolean test) {
		return processTest(test == false, "false");
	}


	/**
	* Vérifie que les 2 arguments sont bien égaux et affiche le résultat
	*
	* @param a Le premier objet
	* @param b Le deuxieme objet
	* @return True si les 2 objets sont égaux, false sinon
	* @see #processTest
	*/
	public static boolean isEquals(Object a, Object b) {
		return processTest(a.equals(b), "equals");
	}


	/**
	* Affiche le résultat du test et incrémente les compteurs pour le nombre d'échecs ainsi que le nombre de tests effectués
	*
	* @param testResult Vaut true si le test à réussi, false sinon
	* @param testType Type de test effectué (equals, false, true, ...)
	* @return Retourne testResult
	*/
	public static boolean processTest(boolean testResult, String testType) {
		UnitTest.numTest++;

		System.out.print("\t" + UnitTest.numTest + " - " + testType + " expected : ");
		if(testResult) {
			System.out.println("OK");
		} else {
			System.out.println("KO");
			UnitTest.nbEchec++;
		}

		return testResult;
	}


	/**
	* Permet de changer le libellé de la série de test en cours. Un changement du libellé entraine l'affiche des résultats de la série qui était en cours.
	* @param testLabel Le nouveau libellé de la série en cours
	* @see #showTestResult
	*/
	public static void setTestLabel(String testLabel) {
		if(testLabel == null)
			return;

		showTestResult();

		UnitTest.testLabel = testLabel;
		System.out.println("["+testLabel+"]");
	}


	/**
	* Affiche le résultat de la série de test en cours
	* <p><i>NB : Comme on commence une nouvelle série de test on ajoute le nombre d'échecs et de tests réalisés au total</i></p>
	*/
	public static void showTestResult() {
		// Si on demande les résultats alors que l'on fait AUCUN test depuis le DEBUT du programme
		if(UnitTest.testLabel == null)
			return;

		if(UnitTest.nbEchec > 0) {
			System.out.println((UnitTest.numTest-UnitTest.nbEchec) + "/" + UnitTest.numTest + " test(s) réussi(s)");
		} else {
			System.out.println(UnitTest.numTest  + " test(s) réussi(s)");
		}

		System.out.println();

		UnitTest.totalTest += UnitTest.numTest;
		UnitTest.nbEchecTotal += UnitTest.nbEchec;
		UnitTest.numTest = 0;
		UnitTest.nbEchec = 0;
	}


	/**
	* Affiche le bilan de tous les tests.
	*
	* <p>Permet d'obtenir le nombre total de tests qui ont échoués sur le nombre total de tests effectués</p>
	*/
	public static void summary() {
		// Avant d'afficher le bilan on montre le résultat du test précédent
		showTestResult();

		System.out.print("Bilan global : ");
		if(UnitTest.nbEchecTotal > 0) {
			System.out.println((UnitTest.totalTest - UnitTest.nbEchecTotal) + "/" + UnitTest.totalTest + " test(s) réussi(s)");
		} else {
			System.out.println("Les " + UnitTest.totalTest  + " test(s) réussi(s)");
		}
	}


	/**
	* Réinitialise tous les compteurs (échecs, nombre de tests faits, ...) à 0
	*/
	public static void reset() {
		totalTest = 0;
		nbEchecTotal = 0;

		numTest = 0;
		nbEchec = 0;

		testLabel = "";
	}

}
