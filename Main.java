/**
 * Clase principal del juego 20 preguntas
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Diciembre 2022
 * @since ED2023-1
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException 
    {
        TwentyQuestionsTree game = new TwentyQuestionsTree();
        game.generateTree(new Scanner(new File("animals.txt")));
        do {
            System.out.println("Piensa en un objeto...");
            game.askQuestions();
        } while (game.continuePlaying());
        game.write(new PrintStream(new File("animals.txt")));
    }
}