import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Stream;

public class Wordle {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String filePath = "src\\words.txt"; // Replace with the actual file path
        Path path = Paths.get(filePath);

        // Input validation: check if the file exists and is readable
        if (!Files.exists(path)) {
            System.out.println("Error: File does not exist.");
            return;
        }

        if (!Files.isReadable(path)) {
            System.out.println("Error: File is not readable.");
            return;
        }

        // Limit the input file size to prevent memory issues
        try {
            long fileSize = Files.size(path);
            if (fileSize > 10 * 1024 * 1024) { // 10 MB limit
                System.out.println("Error: File size exceeds limit (10 MB).");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error checking file size: " + e.getMessage());
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                // Process the file lines to collect words into a list
                List<String> words = lines
                        .flatMap(line -> Arrays.stream(line.split("\\W+"))) // Split into words
                        .filter(word -> !word.isEmpty() && word.length() == 5) // Only take 5-letter words
                        .toList(); // Collect into a list

                // Check if any words were found
                if (words.isEmpty()) {
                    System.out.println("No valid 5-letter words found in the file.");
                    return;
                }

                // Use SecureRandom for cryptographically secure randomness
                SecureRandom secureRandom = new SecureRandom();
                String correct = words.get(secureRandom.nextInt(words.size())).toUpperCase(); // Select and uppercase the word

                // Wordle-like game starts here
                final String RIGHT_INDEX = "\u001b[42m";
                final String WRONG_INDEX = "\u001b[43m";
                final String RESET = "\u001b[0m";
            
                System.out.println("WORDLE!");

                for (int round = 0; round < 6; round++) {
                    System.out.print("Please guess a 5-letter word. > ");
                    String guess = sc.nextLine().toUpperCase();

                    // Check if the guess has 5 letters
                    if (guess.length() != 5) {
                        System.out.println("Invalid input. Please enter a 5-letter word.");
                        continue;
                    }

                    // Create a loop to iterate through each letter
                    for (int i = 0; i < 5; i++) {
                        if (guess.substring(i, i + 1).equals(correct.substring(i, i + 1))) {
                            // Letter matches and is in the correct position
                            System.out.print(RIGHT_INDEX + guess.substring(i, i + 1) + RESET);
                        } else if (correct.contains(guess.substring(i, i + 1))) {
                            // Letter is in the word but in a different position
                            System.out.print(WRONG_INDEX + guess.substring(i, i + 1) + RESET);
                        } else {
                            // Letter is not in the word
                            System.out.print(guess.substring(i, i + 1));
                        }
                    }

                    System.out.println("");

                    // If the guess is correct
                    if (guess.equals(correct)) {
                        System.out.println("Correct! You win!");
                        break;
                    }
                }

                // Print correct answer if the player loses
                if (!sc.nextLine().equals(correct)) {
                    System.out.println("Wrong! The correct word was " + correct + ".");
                }

            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());

            } finally {
                sc.close();
            }
        }
    }
}

