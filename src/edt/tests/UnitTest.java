package edt.tests;

public class UnitTest {

	private static int totalTest = 0;
	private static int nbEchecTotal = 0;

	private static int numTest = 0;
	private static int nbEchec = 0;

	private static String testLabel = null;

	public static boolean isTrue(boolean test) {
		return processTest(test == true, "true");
	}

	public static boolean isFalse(boolean test) {
		return processTest(test == false, "false");
	}

	public static boolean isEquals(Object a, Object b) {
		return processTest(a.equals(b), "equals");
	}

	// Partie commune à tous les tests.
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

	public static void setTestLabel(String testLabel) {
		if(testLabel == null)
			return;

		showTestResult();

		UnitTest.testLabel = testLabel;
		System.out.println("["+testLabel+"]");
	}

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

	public static void reset() {
		totalTest = 0;
		nbEchecTotal = 0;

		numTest = 0;
		nbEchec = 0;

		testLabel = "";
	}

}
