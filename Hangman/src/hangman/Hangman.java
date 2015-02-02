/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author James Elder
 */
public class Hangman {

    private static final int MAX_WRONG_GUESS_COUNT = 10;
    private int hangs;
    private String[] words;
    private String currentWord;
    private String currentWordMask;
    private boolean status;
    private String responses;
    private int charCorrectCount;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Hangman game = new Hangman();
        
        Scanner in = new Scanner(System.in);
        String userResponse;
        
        while (game.isActive()) {
            game.draw();
            System.out.println("What is your next single character non-number guess? (Type quit to end)");
            userResponse = in.nextLine();

            if (game.isValid(userResponse)) {
                game.addResponse(userResponse);
            } else {
                System.out.println(userResponse + " is invalid. Just use single character (a-z). Please try again.");
            }

            if (game.isOver()) {
                if (game.isWinner()) {
                    System.out.println("Congrats, my friend, you guessed the word!");
                } else if (game.isLoser()) {
                    System.out.println("Sorry, my friend, but you have lost.");
                }

                System.out.println("Would you like to play again? (yes to play again)");
                userResponse = in.nextLine();
                if (userResponse.equalsIgnoreCase("yes")) {
                    game = new Hangman();
                } else {
                    game.setStatus(false);
                }
            }

        }
        System.out.println("Thanks for playing! Have a nice day.");
    }

    public Hangman() {
        this.responses = new String();
        this.words = this.createWordArray();
        this.currentWord = this.setCurrentWord();
        this.setCurrentWordMask();
        this.status = true;
        this.hangs = 0;
        this.charCorrectCount = 0;
    }

    private void draw() {
        System.out.println("      _________ ");

        if (this.hangs > 0) {
            if (this.hangs == 1) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("               |");
                System.out.println("               |");
                System.out.println("               |");
                System.out.println("               |");
                System.out.println("               |");
                System.out.println("               |");
            } else if (this.hangs == 2) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("               |");
                System.out.println("               |");
            } else if (this.hangs == 3) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("      \\        |");
                System.out.println("       \\       |");
            } else if (this.hangs == 4) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\       |");
            } else if (this.hangs == 5) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("     |\\       |");
                System.out.println("     | \\      |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\       |");
            } else if (this.hangs == 6) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("    /|\\       |");
                System.out.println("   / | \\      |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\       |");
            } else if (this.hangs == 7) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("    /|\\        |");
                System.out.println("   / | \\/      |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\       |");
            } else if (this.hangs == 8) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("    /|\\        |");
                System.out.println(" \\/ | \\/      |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\       |");
            } else if (this.hangs == 9) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("    /|\\        |");
                System.out.println(" \\/ | \\/      |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("   /   \\/       |");
            } else if (this.hangs == 10) {
                System.out.println("     _         |");
                System.out.println("    |_|        |");
                System.out.println("     |         |");
                System.out.println("    /|\\        |");
                System.out.println("   \\/ | \\ /     |");
                System.out.println("     |         |");
                System.out.println("    / \\        |");
                System.out.println("  ///   \\/       |");
            }
        } else {
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
            System.out.println("               |");
        }

        System.out.println("_________________|");
        System.out.println("");

        System.out.println("Hangman: " + this.getCurrentWordMask());
        System.out.println("Current Word: " + this.getCurrentWord());
        System.out.println("you have " + this.getCurrentCountLeft());
        System.out.println("Responses already submitted: " + this.getResponses());

    }

    private int getCurrentCountLeft() {
        return this.MAX_WRONG_GUESS_COUNT - this.hangs;
    }

    private boolean isActive() {
        return this.status;
    }

    private void addResponse(String response) {
        if (response.equalsIgnoreCase("quit")) {
            this.status = false;
        } else {
            this.responses = this.responses + response + " ";
            if (this.currentWord.contains(response)) {
                this.setCurrentWordMask();
            } else {
                this.hangs++;
            }
        }

    }

    /**
     * randomly choose an word from the array
     */
    private String setCurrentWord() {
        return this.words[new Random().nextInt(this.words.length)];
        
    }

    private String getCurrentWord() {
        return this.currentWord;
    }

    private String getCurrentWordMask() {
        return this.currentWordMask;
    }

    private String[] createWordArray() {
        
        String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
       
        
        return words;
    }

    private String getResponses() {
        return this.responses;
    }

    private boolean isValid(String userResponse) {
        if (userResponse.equalsIgnoreCase("quit")) {
            return true;
        }
        if (userResponse.length() == 0 || userResponse.length() > 1) {
            return false;
        } else {
            return true;
        }

    }

    private boolean isOver() {
        if (this.isWinner() || this.isLoser()) {
            return true;
        } else {
            return false;
        }
    }

    private void setStatus(boolean b) {
        this.status = b;
    }

    /**
     * compares the responses to the current word and creates a word mask.
     */
    private void setCurrentWordMask() {
        this.charCorrectCount = 0;
        char value;
        char letter;
        this.currentWordMask = "";
        for (int i = 0; i < this.currentWord.length(); i++) {
            letter = this.currentWord.charAt(i);
            if (this.responses.indexOf(letter) != -1) {
                value = letter;
                this.charCorrectCount++;
            } else {
                value = '*';
            }
            this.currentWordMask = this.currentWordMask + value;
        }
    }

    private boolean isWinner() {
        if (this.charCorrectCount == this.currentWord.length()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLoser() {
        if (this.hangs >= this.MAX_WRONG_GUESS_COUNT) {
            return true;
        } else {
            return false;
        }
    }

}
