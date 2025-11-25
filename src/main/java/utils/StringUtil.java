package utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * Normalizes a full name by trimming whitespace, collapsing multiple spaces,
     * and capitalizing the first letter of each word.
     * For example, "  hỒ  đỨC   vIỆT  " becomes "Hồ Đức Việt".
     *
     * @param name The full name to normalize.
     * @return The normalized full name, or null if the input is null or empty.
     */
    public static String normalizeFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        // 1. Trim leading/trailing whitespace and collapse multiple spaces into one
        String cleanedName = name.trim().replaceAll("\\s+", " ");

        // 2. Split the name into words and convert to lowercase
        String[] words = cleanedName.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        // 3. Capitalize the first letter of each word
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        // 4. Trim the trailing space and return
        return result.toString().trim();
    }

    public static String removeVietnameseDiacritics(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replace("đ", "d").replace("Đ", "D");
    }
}
