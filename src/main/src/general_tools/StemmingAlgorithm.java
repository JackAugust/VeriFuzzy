package general_tools;



public class StemmingAlgorithm {
    public static String stem(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        // Convert word to lowercase
        word = word.toLowerCase();

        // Step 1a
        if (word.endsWith("sses")) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ies")) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("s") && !word.endsWith("ss")) {
            word = word.substring(0, word.length() - 1);
        }

        // Step 1b
        if (word.endsWith("eed")) {
            if (measure(word.substring(0, word.length() - 3)) > 0) {
                word = word.substring(0, word.length() - 1);
            }
        } else if (word.endsWith("ed")) {
            if (containsVowel(word.substring(0, word.length() - 2))) {
                word = word.substring(0, word.length() - 2);
                word = handleEdEnding(word);
            }
        } else if (word.endsWith("ing")) {
            if (containsVowel(word.substring(0, word.length() - 3))) {
                word = word.substring(0, word.length() - 3);
                word = handleEdEnding(word);
            }
        }

        // Step 1c
        if (word.endsWith("y") && containsVowel(word.substring(0, word.length() - 1))) {
            word = word.substring(0, word.length() - 1) + "i";
        }

        // Step 5
        if (word.endsWith("l") && measure(word.substring(0, word.length() - 1)) > 1) {
            word = word.substring(0, word.length() - 1);
        }

        return word;
    }

    private static int measure(String word) {
        int count = 0;
        boolean vowel = false;

        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) {
                vowel = true;
            } else if (vowel) {
                count++;
                vowel = false;
            }
        }

        return count;
    }

    private static boolean containsVowel(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    private static String handleEdEnding(String word) {
        if (word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {
            word += "e";
        } else if (endsWithDoubleConsonant(word) && !endsWithLorSorZ(word)) {
            word = word.substring(0, word.length() - 1);
        } else if (measure(word) == 1 && endsWithCVC(word)) {
            word += "e";
        }
        return word;
    }

    private static boolean endsWithDoubleConsonant(String word) {
        if (word.length() >= 2) {
            char c1 = word.charAt(word.length() - 1);
            char c2 = word.charAt(word.length() - 2);
            return !isVowel(c1) && c1 == c2;
        }
        return false;
    }

    private static boolean endsWithLorSorZ(String word) {
        if (word.length() >= 1) {
            char c = word.charAt(word.length() - 1);
            return c == 'l' || c == 's' || c == 'z';
        }
        return false;
    }

    private static boolean endsWithCVC(String word) {
        if (word.length() >= 3) {
            char c1 = word.charAt(word.length() - 3);
            char c2 = word.charAt(word.length() - 2);
            char c3 = word.charAt(word.length() - 1);
            return !isVowel(c1) && isVowel(c2) && !isVowel(c3) && c3 != 'w' && c3 != 'x' && c3 != 'y';
        }
        return false;
    }

    /*

    manager
walter
february
expected
start
estimated
resources

CONNECT CONNECTED CONNECTING  CONNECTION  CONNECTIONS
     */

    public static void main(String[] args) {
        String word = "CONNECTIONS";
        String stemmedWord = stem(word);

        System.out.println(stemmedWord);
    }
}

