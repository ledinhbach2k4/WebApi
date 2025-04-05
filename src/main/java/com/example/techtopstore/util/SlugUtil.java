package com.example.techtopstore.util;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {
    public static String toSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Normalize the string to remove accents (e.g., "á" → "a")
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalized).replaceAll("");

        // Convert to lowercase, replace spaces and special characters with hyphens
        slug = slug.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-");

        return slug;
    }
}
