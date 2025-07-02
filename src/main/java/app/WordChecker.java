package wordle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordChecker {

    public enum LetterResult {
        CORRECT,     // green
        PRESENT,     // yellow
        ABSENT       // gray
    }

    public static List<LetterResult> checkGuess(String guess, String answer) {
        guess = guess.toUpperCase();
        answer = answer.toUpperCase();
        List<LetterResult> result = new ArrayList<>(guess.length());

        // Count letters in answer
        HashMap<Character, Integer> answerLetterCounts = new HashMap<>();
        for (char c : answer.toCharArray()) {
            answerLetterCounts.put(c, answerLetterCounts.getOrDefault(c, 0) + 1);
        }

        // First pass: check correct letters
        for (int i = 0; i < guess.length(); i++) {
            char g = guess.charAt(i);
            if (g == answer.charAt(i)) {
                result.add(LetterResult.CORRECT);
                answerLetterCounts.put(g, answerLetterCounts.get(g) - 1);
            } else {
                result.add(null); // placeholder
            }
        }

        // Second pass: check present (yellow) and absent (gray)
        for (int i = 0; i < guess.length(); i++) {
            if (result.get(i) != null) continue;

            char g = guess.charAt(i);
            if (answerLetterCounts.getOrDefault(g, 0) > 0) {
                result.set(i, LetterResult.PRESENT);
                answerLetterCounts.put(g, answerLetterCounts.get(g) - 1);
            } else {
                result.set(i, LetterResult.ABSENT);
            }
        }

        return result;
    }
}
