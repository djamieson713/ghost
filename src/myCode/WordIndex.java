package myCode;

public class WordIndex {
    public int evenWords = 0;
    public int oddWords = 0;

    WordIndex() {

    }

    public void AddWord(String word) {
        if (word.length() % 2 == 0) {
            evenWords++;
        } else {
            oddWords++;
        }
    }

}