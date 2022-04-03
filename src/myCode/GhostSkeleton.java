package myCode;

import professorCode.TurnParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class GhostSkeleton {

	private static String sharedFilePath;
	private static TurnParser turnParser;
	private static StringBuffer wordFragment;
	private static FileManager fileManager;
	private static Dictionary dictionary;
	// public static String dictionaryFilePath = getRunningDirectory() +
	// File.separator + "ARBITER_DICTIONARY.txt";
	private static Path path = FileSystems.getDefault().getPath("ARBITER_DICTIONARY.txt");
	public static String dictionaryFilePath = path.toAbsolutePath().toString();

	private static Strategy sg;

	public static void main(String[] args) throws Exception {

		if (args == null || args.length < 1) {
			throw new IllegalArgumentException(getTeamName()
					+ ".jar could not start because a shared file path was not specified as a runtime argument.");
		}

		if (!new File(args[0]).exists()) {
			throw new FileNotFoundException(getTeamName()
					+ ".jar could not start because a shared file path specified points to a file that does not exist.");
		}

		// System.out.println(dictionaryFilePath);

		if (!new File(dictionaryFilePath).exists()) {
			throw new FileNotFoundException(
					getTeamName() + ".jar could not start because the dictionary could not be found.");
		}

		// The location of the shared game file will be provided to you at runtime
		sharedFilePath = args[0];

		/*
		 * Create all of your necessary classes. Keep in mind that my constructors may
		 * look different than yours.
		 */
		turnParser = new TurnParser(getTeamName());

		wordFragment = new StringBuffer();

		fileManager = new FileManager(sharedFilePath);

		dictionary = new Dictionary(dictionaryFilePath, fileManager);
		// Stopwatch sw = new Stopwatch();
		sg = new Strategy(dictionary, 4);
		// double totalTime = sw.elapsedTime();
		// System.out.println("Strategy build elapsed time = " + totalTime);
		turnParser.setTeamName("AIAlgorithms");

		// Starts your game loop
		while (true) {

			// Updates the file monitor with the most recent information
			fileManager.update();

			// A change to the shared file as occurred!
			if (fileManager.hasChanged()) {
				// System.out.println("We are in the fileManager has changed");

				// We get the most recent line written to the shared file
				String lastLine = fileManager.getLastLine(sharedFilePath);
				// System.out.println("Here is the lastline = " + lastLine);
				// Check if its our turn
				if (turnParser.isMyTurn(lastLine)) {
					// System.out.println("turn Parser says it is our turn");
					String nextLetter = sg.getNextLetter(wordFragment);

					// System.out.println("Here is the letter we decided=" + nextLetter);
					// Adds the letter to our word fragment
					wordFragment.append(nextLetter);

					// Creates the next line to be written to the shared file
					// String nextLine = turnParser.getNextLine(nextLetter);

					String nextLine = turnParser.getNextLine(nextLetter);
					// System.out.println("Here is what turnParser will write =" + nextLine);
					// Writes the next line to the shared file
					fileManager.writeToFile(nextLine, sharedFilePath);
				}
				// Checks if the other player has finished their turn
				else if (turnParser.didOtherPlayerFinishTurn(lastLine)) {
					// Adds their letter to my word fragment
					wordFragment.append(turnParser.getOtherPlayersLetter(lastLine));

					// The game is over and its time for us to quit
				} else if (turnParser.isGameOver(lastLine)) {
					System.exit(0);
				}
			}
		}
	}

	/**
	 * Tells you if you want to select odd or even words. Only call this before
	 * adding your letter to the word fragment and it will tell you if your looking
	 * for even or odd length words.
	 * 
	 * @return True if you want Even length words. False otherwise.
	 */
	public static boolean doIWantEvenLengthWords() {
		return (wordFragment.length() & 1) == 0;
	}

	/**
	 * Returns a file object representing your jar file.
	 * 
	 * @return A file object
	 */
	private static File getJarFile() {
		return new java.io.File(GhostSkeleton.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	/**
	 * This will figure out your team name by using the name of your jar file.
	 * 
	 * @return A string representing your team name. The string is equal to the name
	 *         of your jar file.
	 */
	public static String getTeamName() {
		return getJarFile().getName().replaceAll(".jar", "");
	}

	/**
	 * This will return the directory that your jar file is located in. This is
	 * useful when you want to reference your dictionary text file when it is
	 * located in the same directory as your jar file.
	 * 
	 * @return The current directory of your jar file.
	 */
	public static String getRunningDirectory() {
		return getJarFile().getParent();
	}
}
