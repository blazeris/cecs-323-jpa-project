/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;


// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;
import org.eclipse.persistence.exceptions.DatabaseException;

import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class BooksProject {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the CarClub
    * class, and create an instance of CarClub in the main.
    */
   private EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(BooksProject.class.getName());

   /**
    * The constructor for the CarClub class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    *
    * @param manager The EntityManager that we will use.
    */
   public BooksProject(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BooksProject");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of CarClub and store our new EntityManager as an instance variable.
      BooksProject booksProject = new BooksProject(manager);


      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();
      booksProject.createEntity(new AuthoringEntities("tree@gmail.com", "the good type", "mr. washington", "the american books", 1776));
      booksProject.createEntity(new Publishers("canada publishing", "canada@gmail.com", "123-456-7089"));
      tx.commit();

      booksProject.createBook(tx);
      booksProject.createBook(tx);

      Scanner in = new Scanner(System.in);
      String wait = in.nextLine();

      LOGGER.fine("End of Transaction");

   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    *
    * @param entities The list of entities to persist.  These can be any object that has been
    *                 properly annotated in JPA and marked as "persistable."  I specifically
    *                 used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List<E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method



   public <E> void createEntity(E entity){
      LOGGER.info("Persisting: " + entity);
      this.entityManager.persist(entity);
      LOGGER.info("Persisted object after flush (non-null id): " + entity);
   }

   public List<AuthoringEntities> getAuthoringEntities(){
      List<AuthoringEntities> authoringEntities = this.entityManager.createNamedQuery("ReturnAuthoringEntities",
              AuthoringEntities.class).getResultList();
      if(authoringEntities.size() == 0){
         authoringEntities = null;
      }
      return authoringEntities;
   }

   public List<Publishers> getPublishers(){
      List<Publishers> publishers = this.entityManager.createNamedQuery("ReturnPublishers",
              Publishers.class).getResultList();
      if(publishers.size() == 0){
         publishers = null;
      }
      return publishers;
   }

   public AuthoringEntities selectAuthoringEntity(){
      Scanner in = new Scanner(System.in);
      AuthoringEntities authoringEntity = null;
      boolean authoringEntityValid = false;
      while(!authoringEntityValid){
         System.out.println("Select the number of the corresponding authoring entity");
         List<AuthoringEntities> authoringEntities = getAuthoringEntities();
         if(authoringEntities != null){
            for(int i = 1; i <= authoringEntities.size(); i++){
               System.out.printf("%s. %s\n", i, authoringEntities.get(i - 1));
            }
            String userInput = in.nextLine();
            try{
               int authoringEntitySelection = Integer.parseInt(userInput);
               if(authoringEntitySelection > 0 && authoringEntitySelection <= authoringEntities.size()){
                  authoringEntity = authoringEntities.get(authoringEntitySelection - 1);
                  authoringEntityValid = true;
               }
            }
            catch(Exception e){
               System.out.println("Invalid user input. Try again");
            }
         }
         else {
            System.out.println("No authoring entities exist. Authoring entity required to select book.");
            authoringEntityValid = true; // Finish while loop, returning null since an authoring entity is impossible to select
         }
      }
      return authoringEntity;
   }

   public Publishers selectPublisher(){
      Scanner in = new Scanner(System.in);
      Publishers publisher = null;
      boolean publisherValid = false;
      while(!publisherValid){
         System.out.println("Select the number of the corresponding publisher");
         List<Publishers> publishers = getPublishers();
         if(publishers != null){
            for(int i = 1; i <= publishers.size(); i++){
               System.out.printf("%s. %s\n", i, publishers.get(i - 1));
            }
            String userInput = in.nextLine();
            try{
               int publisherSelection = Integer.parseInt(userInput);
               if(publisherSelection > 0 && publisherSelection <= publishers.size()){
                  publisher = publishers.get(publisherSelection - 1);
                  publisherValid = true;
               }
            }
            catch(Exception e){
               System.out.println("Invalid user input. Try again");
            }
         }
         else {
            System.out.println("No publisher exist. Publisher required to select book.");
            publisherValid = true; // Finish while loop, returning null since an authoring entity is impossible to select
         }
      }
      return publisher;
   }
   public AuthoringEntities promptAuthoringEntity(){
      Scanner in = new Scanner(System.in);
      AuthoringEntities authoringentity = null;
      System.out.println("Please enter the authoringEntity's email: ");
      String email = in.nextLine();
      System.out.println("Please enter the authoringEntity's name: ");
      String name = in.nextLine();
      System.out.println("Please enter the authoringEntity's headWriter: ");
      String headWriter = in.nextLine();
      System.out.println("Please enter the authoringEntity's year of formation: ");
      String yearFormed = in.nextLine();
      System.out.println("Please enter the authoringEntity's type: ");
      String AuthoringEntityType = in.nextLine();
      authoringentity = new AuthoringEntities (email,name,headWriter,yearFormed,AuthoringEntityType);
      return authoringentity;
   }



   public Books promptBook(){
      Scanner in = new Scanner(System.in);
      Books book = null;
      boolean inputValid = false;
      boolean inputPossible = true;
      while(!inputValid && inputPossible){
         System.out.println("What is the book's ISBN?");
         String ISBN = in.nextLine();
         System.out.println("What is the book's title?");
         String title = in.nextLine();

         boolean yearPublishedValid = false;
         int yearPublished = 0;
         while(!yearPublishedValid){
            System.out.println("What year was the book published?");
            String userInput = in.nextLine();
            try{
               yearPublished = Integer.parseInt(userInput);
               yearPublishedValid = true;
            }
            catch (Exception e){
               System.out.println("Invalid year published, try again.");
            }
         }

         AuthoringEntities authoringEntity = selectAuthoringEntity();
         if(authoringEntity == null){
            inputPossible = false;
         }
         else {
            Publishers publisher = selectPublisher();
            if(publisher == null){
               inputPossible = false;
            }
            else {
               book = new Books(ISBN, title, yearPublished, authoringEntity, publisher);
               inputValid = true;
            }
         }
      }
      return book;
   }

   public void createBook(EntityTransaction tx){
      boolean bookValid = false;
      while(!bookValid){
         Books book = this.promptBook();
         if(book != null){
            tx.begin();
            try{
               this.createEntity(book);
               tx.commit();
               bookValid = true;
            }
            catch(DatabaseException e){
               System.out.println("This already exists in the database! Try again!");
               tx.rollback();
            }
         }
         else {
            System.out.println("Either no publishers or authoring entities exist, impossible to make book.");
            bookValid = true;
         }
      }

   }
}

