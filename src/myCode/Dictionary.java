package myCode;

import professorCode.AbstractDictionary;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary extends AbstractDictionary {

    public Dictionary(String path, FileManager fileManager) throws IOException {
        super(path, fileManager);
    }

    public int countWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {

        if ((prefix == null) || (prefix.isEmpty()) || (prefix.isBlank())) {
            throw new IllegalArgumentException("can not have an empty prefix string");
        }

        int count = 0;

        String resolvedPrefix = ignoreCase ? prefix.toLowerCase() : prefix;

        Set<String> allWords = this.getAllWords();

        // create an empty set
        Set<String> filteredSet = new HashSet<>();
        for (String s : allWords) {// remember this is really a set of sentences
            String[] words = s.split(" ");
            for (String w : words) {
                if (w.length() >= size) {
                    if (ignoreCase) {
                        filteredSet.add(w.toLowerCase());
                        if (w.toLowerCase().startsWith(resolvedPrefix))
                            count++;
                    } else {
                        filteredSet.add(w);
                        if (w.startsWith(resolvedPrefix))
                            count++;
                    }
                }
            }
        }

        return count;

    }

    public boolean containsWordsThatStartWith(String prefix, int size, boolean ignoreCase)
            throws IllegalArgumentException {
        int noWords = countWordsThatStartWith(prefix, size, ignoreCase);
        return noWords > 0;
    }

    public Set<String> getWordsThatStartWith(String prefix, int size, boolean ignoreCase)
            throws IllegalArgumentException {

        if ((prefix == null) || (prefix.isEmpty()) || (prefix.isBlank())) {
            throw new IllegalArgumentException("can not have an empty prefix string");
        }

        String resolvedPrefix = ignoreCase ? prefix.toLowerCase() : prefix;

        Set<String> allWords = this.getAllWords();

        // create an empty set
        Set<String> filteredSet = new HashSet<>();
        for (String s : allWords) {// remember this is really a set of sentences
            String[] words = s.split(" ");
            for (String w : words) {
                if (w.length() >= size) {
                    if (ignoreCase) {
                        if (w.toLowerCase().startsWith(resolvedPrefix))
                            filteredSet.add(w.toLowerCase());
                    } else {
                        if (w.startsWith(resolvedPrefix))
                            filteredSet.add(w);
                    }
                }
            }
        }

        return filteredSet;
    }

    public Set<String> getDictionary() {
        return this.getAllWords();
    }
}
