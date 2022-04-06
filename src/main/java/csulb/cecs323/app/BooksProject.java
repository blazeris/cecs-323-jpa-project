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
import org.apache.derby.iapi.db.Database;
import org.eclipse.persistence.exceptions.DatabaseException;

import java.util.Scanner;
import javax.persistence.*;
import java.util.List;
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


      EntityTransaction tx = manager.getTransaction();

      // Insert example data
      tx.begin();
      AuthoringEntities ae = new IndividualAuthor("tree@gmail.com", "mr. washington");
      Publishers p = new Publishers("canada publishing", "canada@gmail.com", "123-456-7089");
      booksProject.createEntity(ae);
      booksProject.createEntity(p);
      booksProject.createEntity(new AdHocTeam("email", "pro team"));
      booksProject.createEntity(new Books("isbn", "good title", 2022, ae, p));
      tx.commit();


      while(true){
         booksProject.promptAction(manager);
      }


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

   public void promptAction(EntityManager em){
       Scanner in = new Scanner(System.in);
       boolean optionValid = false;
       while(!optionValid){
          System.out.println("What would you like to do? Select an option." +
                   "\n1. Add new item" +
                   "\n2. List information about an item" +
                   "\n3. Delete a book" +
                   "\n4. Update an existing book" +
                   "\n5. List primary keys");
          String userInput = in.nextLine();
          optionValid = true;
          switch(userInput){
              case "1":
                 promptAdd(em);
                 break;
              case "2":
                 promptList();
                 break;
              case "3":
                 promptDeleteBook(em);
                 break;
              case "4":
                 System.out.println(getBooks());
                 promptUpdateBook();
                 System.out.println(getBooks());
                 break;
              case "5":
                 promptKeys();
                 break;
              default:
                 System.out.println("None of the options were selected properly, try again!");
                 optionValid = false;
          }
       }
   }

   public void promptAdd(EntityManager em){
      EntityTransaction tx = em.getTransaction();
      Scanner in = new Scanner(System.in);
      boolean optionValid = false;
      while(!optionValid){
         System.out.println("Which type of item would you like to add? Select an option." +
                 "\n1. Authoring Entity" +
                 "\n2. Publisher" +
                 "\n3. Book");
         String userInput = in.nextLine();
         optionValid = true;
         switch(userInput){
            case "1":
               createAuthoringEntity(tx);
               break;
            case "2":
               createPublisher(tx);
               break;
            case "3":
               createBook(tx);
               break;
            default:
               System.out.println("None of the options were selected properly, try again!");
               optionValid = false;
         }
      }
   }

   public void promptList(){
      Scanner in = new Scanner(System.in);
      boolean optionValid = false;
      while(!optionValid){
         System.out.println("Which type of item would you like to list information about? Select an option." +
                 "\n1. Publisher" +
                 "\n2. Book" +
                 "\n3. Writing Group");
         String userInput = in.nextLine();
         optionValid = true;
         switch(userInput){
            case "1":
               System.out.println(selectPublisher());
               break;
            case "2":
               System.out.println(selectBooks());
               break;
            case "3":
               System.out.println(selectAuthoringEntity());
               break;
            default:
               System.out.println("None of the options were selected properly, try again!");
               optionValid = false;
         }
      }
   }

   public void promptDeleteBook(EntityManager em){
      System.out.println("Select a book to delete.");
      Books book = selectBooks();
      EntityTransaction tx = em.getTransaction();
      tx.begin();
      try{
         em.remove(book);
         tx.commit();
         System.out.println(book.getTitle() + " successfully removed.");
      }
      catch(DatabaseException e){
         tx.rollback();
         System.out.println("Book not found!");
      }

   }

   public void promptUpdateBook(){
      Scanner in = new Scanner(System.in);
      Books book = selectBooks();
      System.out.println("Enter the updated email: ");
      String email = in.nextLine();
      System.out.println("Enter the updated entity type: ");
      String entityType = in.nextLine();
      System.out.println("Enter the updated name: ");
      String name = in.nextLine();
      System.out.println("Enter the updated head writer: ");
      String headWriter = in.nextLine();
      System.out.println("Enter the updated year formed: ");
      int yearFormed = in.nextInt();
      //AuthoringEntities newAuthoringEntity = new AuthoringEntities(email, entityType, name, headWriter, yearFormed);
      //book.setAuthoringEntity(newAuthoringEntity);
      System.out.println("Book updated.");
   }

   public void promptKeys(){

   }

   public AuthoringEntities promptAuthoringEntity(){
      Scanner in = new Scanner(System.in);
      AuthoringEntities authoringEntity = null;
      System.out.println("Please enter the authoringEntity's email: ");
      String email = in.nextLine();
      System.out.println("Please enter the authoringEntity's name: ");
      String name = in.nextLine();
      System.out.println("Please enter the authoringEntity's headWriter: ");
      String headWriter = in.nextLine();
      System.out.println("Please enter the authoringEntity's year of formation: ");
      String userInput = in.nextLine();
      int yearFormed = Integer.parseInt(userInput);
      System.out.println("Please enter the authoringEntity's type: ");
      String authoringEntityType = in.nextLine();
      //authoringEntity = new AuthoringEntities(email, authoringEntityType, headWriter, name, yearFormed);
      return authoringEntity;
   }

   public Publishers promptPublisher(){
      Scanner in = new Scanner(System.in);
      Publishers publisher = null;
      System.out.println("Please enter the publisher's name: ");
      String name = in.nextLine();
      System.out.println("Please enter the publisher's email: ");
      String email = in.nextLine();
      System.out.println("Please enter the publisher's phone number: ");
      String phone = in.nextLine();
      publisher = new Publishers(name, email, phone);
      return publisher;
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



   public List<AuthoringEntities> getAuthoringEntities(){
      List<AuthoringEntities> authoringEntities = this.entityManager.createNamedQuery("ReturnAuthoringEntities",
              AuthoringEntities.class).getResultList();
      if(authoringEntities.size() == 0){
         authoringEntities = null;
      }
      return authoringEntities;
   }

   public AuthoringEntities getAuthoringEntity(String email){
        List<AuthoringEntities> authoringEntities = this.entityManager.createNamedQuery("ReturnAuthoringEntity",
                AuthoringEntities.class).setParameter(1, email).getResultList();
        if(authoringEntities.size() == 0){
            authoringEntities = null;
        }
        return authoringEntities.get(0);
    }


   public List<Publishers> getPublishers(){
      List<Publishers> publishers = this.entityManager.createNamedQuery("ReturnPublishers",
              Publishers.class).getResultList();
      if(publishers.size() == 0){
         publishers = null;
      }
      return publishers;
   }

   public Publishers getPublisher(String name){
        List<Publishers> publishers = this.entityManager.createNamedQuery("ReturnPublisher",
                Publishers.class).setParameter(1, name).getResultList();
        if(publishers.size() == 0){
            publishers = null;
        }
        return publishers.get(0);
    }


   public List<Books> getBooks(){
      List<Books> books = this.entityManager.createNamedQuery("ReturnBooks",
              Books.class).getResultList();
      if(books.size() == 0){
         books = null;
      }
      return books;
   }

   public Books getBook(String ISBN){
      List<Books> books = this.entityManager.createNamedQuery("ReturnBook",
              Books.class).setParameter(1, ISBN).getResultList();
      if(books.size() == 0){
         books = null;
      }
      return books.get(0);
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

   public Books selectBooks(){
      Scanner in = new Scanner(System.in);
      Books book = null;
      boolean bookValid = false;
      while(!bookValid){
         System.out.println("Select the number of the corresponding book");
         List<Books> books = getBooks();
         if(books != null){
            for(int i = 1; i <= books.size(); i++){
               System.out.printf("%s. %s\n", i, books.get(i - 1));
            }
            String userInput = in.nextLine();
            try{
               int bookSelection = Integer.parseInt(userInput);
               if(bookSelection > 0 && bookSelection <= books.size()){
                  book = books.get(bookSelection - 1);
                  bookValid = true;
               }
            }
            catch(Exception e){
               System.out.println("Invalid user input. Try again");
            }
         }
         else {
            System.out.println("No books exist.");
            bookValid = true; // Finish while loop, returning null since an authoring entity is impossible to select
         }
      }
      return book;
   }



   public void createAuthoringEntity(EntityTransaction tx){
      boolean authoringEntityValid = false;
      while(!authoringEntityValid){
         AuthoringEntities authoringEntity = this.promptAuthoringEntity();
         if(authoringEntity != null){
            tx.begin();
            try{
               this.createEntity(authoringEntity);
               tx.commit();
               authoringEntityValid = true;
            }
            catch(DatabaseException e){
               System.out.println("This already exists in the database! Try again!");
               tx.rollback();
            }
         }
      }
   }

   public void createPublisher(EntityTransaction tx){
      boolean publisherValid = false;
      while(!publisherValid){
         Publishers publisher = this.promptPublisher();
         if(publisher != null){
            tx.begin();
            try{
               this.createEntity(publisher);
               tx.commit();
               publisherValid = true;
            }
            catch(DatabaseException e){
               System.out.println("This already exists in the database! Try again!");
               tx.rollback();
            }
         }
      }
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

