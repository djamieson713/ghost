package myCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Strategy {

    private HashMap<String, WordIndex> index;
    private HashSet<String> words;
    private Set<String> dictWords;
    private int minWordSize;
    private String allLetters;
    private Boolean bOddFlag; // whether to seek odd or even words

    public Strategy(Dictionary d, int minWordSize) {
        this.dictWords = d.getAllWords();
        this.minWordSize = minWordSize;
        this.index = new HashMap<String, WordIndex>();
        this.words = new HashSet<String>();
        this.bOddFlag = true;
        // the word index is a helping data structures to get an idea of
        // how many odd and even words are available after giving a certain
        // prefix
        BuildWordIndex();

    }

    // for a given key index whether word is odd or even
    private void IndexWord(String word) {
        int beginIndex = 0;
        int endIndex = 2;
        for (int i = 0; i < word.length() - 2; i++) {
            String key = word.substring(beginIndex, endIndex + i);
            if (!this.index.containsKey(key)) {
                WordIndex wi = new WordIndex();
                wi.AddWord(word);
                this.index.put(key, wi);
            } else {
                WordIndex wi = this.index.get(key);
                wi.AddWord(word);
            }
        }
    }

    public void BuildWordIndex() {
        for (String s : this.dictWords) {
            if (s.length() >= this.minWordSize) {
                this.words.add(s);
                IndexWord(s);
            }
        }

        // System.out.println("Dictionary size =" + this.dictWords.size());
    }

    public String chooseNextLetter() {
        // if (isGameOver()) {
        // return "Z";
        // }
        String currentString = allLetters.toString().toLowerCase();
        int maxLeaning = 0;
        char maxChar = 'a';
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String proposedString = currentString + Character.toString(alphabet);
            // System.out.println("Here is the proposed string = " + proposedString);
            // we want strings that have an odd number of char, over even chars
            WordIndex wi = index.get(proposedString);

            if (wi == null) {
                // System.out.println("WI is null");
                // wi = this.index.get("test");
                // System.out.println("Word index for test, odd = " + wi.oddWords);
                // System.out.println("Word index for test, even = " + wi.evenWords);
                continue;
            }

            // System.out.println("For the proposed string here is even count = " +
            // wi.evenWords);
            // System.out.println("For the proposed string here is odd count = " +
            // wi.oddWords);

            if (this.bOddFlag) {
                if ((wi.oddWords > 0) && (wi.evenWords == 0)) {
                    maxChar = alphabet;
                    break;
                } else if ((wi.oddWords - wi.evenWords) > maxLeaning) {
                    maxLeaning = wi.oddWords - wi.evenWords;
                    maxChar = alphabet;
                }
            } else {
                if ((wi.evenWords > 0) && (wi.oddWords == 0)) {
                    maxChar = alphabet;
                    break;
                } else if ((wi.evenWords - wi.oddWords) > maxLeaning) {
                    maxLeaning = wi.evenWords - wi.oddWords;
                    maxChar = alphabet;
                }
            }
        }

        allLetters = allLetters + (Character.toString(maxChar));

        return Character.toString(maxChar);

    }

    public boolean isGameOver() {
        String currentWord = allLetters.toString();
        if (currentWord.length() >= this.minWordSize) {
            if (words.contains(currentWord)) {
                return true;
            } else {
                if (!index.containsKey(currentWord)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    // the current state of the game at this point
    public String getNextLetter(StringBuffer sb) {
        allLetters = sb.toString();
        if (sb.length() == 0) {
            // we are the first person to play, thus look for even
            // words
            bOddFlag = false; // seek even words
            return firstLetter();
        } else {
            return chooseNextLetter();
        }
    }

    public String firstLetter() {
        List<Character> givenList = Arrays.asList('q', 'x', 'z', 'y', 'a', 'b', 'c');
        Random rand = new Random();
        Character randomElement = givenList.get(rand.nextInt(givenList.size()));
        String nextLetter = Character.toString(randomElement);
        allLetters = allLetters + (nextLetter);
        return nextLetter;
    }

}