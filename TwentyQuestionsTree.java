/**
 * Clase que modela un arbol binario para el juego 20 preguntas
 * @author Rodrigo Ortega 318036104
 * @version 1.0 Diciembre 2022
 * @since ED2023-1
 */

import java.util.*;
import java.io.*;

public class TwentyQuestionsTree {
   /**
    * Clase auxiliar que modela un nodo
    */
   private class Node {
      private String data;
      private Node leftNode;
      private Node rightNode;

      public Node(String data) {
         this.data = data;
      }

      public Node(String data, Node leftNode, Node rightNode) {
         this.data = data;
         this.leftNode = leftNode;
         this.rightNode = rightNode;
      }
   }

   private Node root;
   private Scanner scanner;

   public TwentyQuestionsTree() {
      scanner = new Scanner(System.in);
   }

   /**
    * Genera un arbol binario a partir de un documento de texto
    * @param input un documento de texto
    */
   public void generateTree(Scanner input) {
      while (input.hasNextLine()) {
         root = readHelper(input);
      }
   }

   /**
    * Metodo auxiliar de genenerateTree, lee lineas de texto para construir un
    * arbol binario
    * @param input lineas de texto
    * @return un arbol binario
    */
   private Node readHelper(Scanner input) {
      String type = input.nextLine();
      String data = input.nextLine();
      Node root = new Node(data);

      if (type.contains("Q:")) {
         root.leftNode = readHelper(input);
         root.rightNode = readHelper(input);
      }
      return root;
   }

   /**
    * Escribe en un documento de texto el arbol binario
    * @param output arbol a escribir
    */
   public void write(PrintStream output) {
      if (output == null) {
         throw new IllegalArgumentException();
      }
      writeTree(root, output);
   }

   /**
    * Metodo auxiliar de writeTree, guarda el contenido del arbol en un
    * documento de texto
    * @param root la raiz del arbol
    * @param output nodo del arbol
    */
   private void writeTree(Node root, PrintStream output) {
      if (isAnswerNode(root)) {
         output.println("A:");
         output.println(root.data);
      } else {
         output.println("Q:");
         output.println(root.data);
         writeTree(root.leftNode, output);
         writeTree(root.rightNode, output);
      }
   }

   /**
    * Se mueve por los nodos del arbol, haciendo preguntas hasta llegar al
    * resultado
    */
   public void askQuestions() {
      root = askQuestions(root);
   }


   /**
    * Metodo auxiliar de askQuestions, si no se encontro el elemento, se agrega
    * @param current nodo al que se le agregara la pregunta
    * @return el elemento agregado
    */
   private Node addAnswer(Node current) {
      System.out.print("En que estabas pensando? ");
      Node answer = new Node(scanner.nextLine());
      System.out.print("""
            Dame una pregunta de si o no para identificar lo que estabas pensando:
            """);
      String question = scanner.nextLine();
      System.out.print("""
            Cual es la respuesta para lo que estabas pensando? (si/no):
            """);
      String userInput = scanner.nextLine().trim().toLowerCase();
      switch (userInput) {
         case "yes", "y", "si":
            current = new Node(question, answer, current);
            break;
         case "no", "n":
            current = new Node(question, current, answer);
            break;
         default:
            System.out.println("Respuesta invalida");
            break;
      }
      return current;
   }

   /**
    * Se mueve por el arbol haciendo preguntas al usuario, si no se encuentra el
    * elemento, se agrega
    * @param current nodo actual
    * @return siguiente nodo
    */
   private Node askQuestions(Node current) {
      System.out.print("""
            Presiona:
            [1] Para ver todas las preguntas almacenadas.
            [2] Para ver todos los elementos almacenados.
            S/N A las preguntas de acuerdo a las propiedades de lo que 
            estes pensando.

            """);
      if (isAnswerNode(current)) {
         System.out.print("Estabas pensando en... " + current.data + "? ");
         String userInput = scanner.nextLine().trim().toLowerCase();
         switch (userInput) {
            case "yes", "y", "si":
               System.out.println("He adividado correctamente");
               break;
            case "no", "n":
               current = addAnswer(current);
               break;
            case "1":
               showQuestions();
               break;
            case "2":
               showAnswers();
               break;
            default:
               System.out.println("Respuesta invalida");
               break;
         }
      } else {
         System.out.print(current.data + ": ");
         String userInput = scanner.nextLine().trim().toLowerCase();
         switch (userInput) {
            case "yes", "y", "si", "s":
               current.leftNode = askQuestions(current.leftNode);
               break;
            case "no", "n":
               current.rightNode = askQuestions(current.rightNode);
               break;
            case "1":
               showQuestions();
               break;
            case "2":
               showAnswers();
               break;
            default:
               System.out.println("Respuesta invalida");
               break;
         }
      }
      return current;
   }

   
   /**
    * Le pregunta al usuario si quiere seguir jugando
    * @return true si el usuario quiere seguir jugando, no de otra manera
    */
   public boolean continuePlaying() {
      System.out.print("Quieres seguir jugando?: ");
      boolean continuePlaying = false;
      String userInput = scanner.nextLine().trim().toLowerCase();
      switch (userInput) {
         case "yes", "si", "s", "y":
            continuePlaying = true;
            break;
         case "no", "n":
            continuePlaying = false;
            break;
         default:
            System.out.println("Respuesta invalida");
            break;
      }
      return continuePlaying;
   }

   
   /**
    * Muestra todas los nodos con preguntas
    */
   private void showQuestions() {
      Stack<Node> stack = new Stack<>();
      Node current = root;
      stack.push(root);

      while (current != null && !stack.isEmpty()) {
         current = stack.pop();
         if (!isAnswerNode(current)) {
            show(current.data);
         }
         if (current.rightNode != null)
            stack.push(current.rightNode);

         if (current.leftNode != null)
            stack.push(current.leftNode);
      }
   }

   /**
    * Muestra todos los nodos con respuestas
    */
   private void showAnswers() {
      Stack<Node> stack = new Stack<>();
      Node current = root;
      stack.push(root);

      while (current != null && !stack.isEmpty()) {
         current = stack.pop();
         if (isAnswerNode(current)) {
            show(current.data);
         }

         if (current.rightNode != null)
            stack.push(current.rightNode);

         if (current.leftNode != null)
            stack.push(current.leftNode);
      }
   }

   /**
    * Muestra el valor de un nodo
    * @param value el valor de un nodo
    */
   private void show(String value) {
      System.out.print(value + "\n");
   }

   
   /**
    * Verifica si un nodo contiene una pregunta o una respuesta
    * @param node nodo a verificar
    * @return true si el nodo contiene una respuesta
    */
   private boolean isAnswerNode(Node node) {
      return (node.leftNode == null || node.rightNode == null);
   }
}
