package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Objects;
import java.io.Serializable;

/**
 * A specific book that was authored and published, represents the writing not a specific physical copy.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "publisher"}),
        @UniqueConstraint(columnNames = {"title", "authoringEntity"})
})
public class Books implements Serializable {

    // The ISBN code that identifies a specific book
    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    // The title of the book
    @Column(nullable = false, length = 80)
    private String title;

    // The year that the book was published
    @Column(nullable = false)
    private int yearPublished;

    // The author entity of the book
    @Column(nullable = false)
    private AuthoringEntity authoringEntity;

    // The group that published the book
    @Column(nullable = false)
    private Publisher publisher;

    /**
     * Null constructor
     */
    public Books(){

    }

    /**
     * Arguments constructor
     * @param ISBN
     * @param title
     * @param yearPublished
     * @param authoringEntity
     * @param publisher
     */
    public Books(String ISBN, String title, int yearPublished, AuthoringEntity authoringEntity, Publisher publisher){
        this.ISBN = ISBN;
        this.title = title;
        this.yearPublished = yearPublished;
        this.authoringEntity = authoringEntity;
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public AuthoringEntity getAuthoringEntity() {
        return authoringEntity;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public void setAuthoringEntity(AuthoringEntity authoringEntity){
        this.authoringEntity = authoringEntity;
    }

    public void setPublisher(Publisher publisher){
        this.publisher = publisher;
    }

    /**
     * Function to check if ISBN is equal to object passed
     * @param o Object which is being checked for equivalence
     * @return Whether or not the object is equal
     */
    @Override
    public boolean equals(Object o){
        boolean results = false;
        if (this == o){
            results = true;
        } else if (o == null || getClass() != o.getClass()){
            results = false;
        }
        else {
            Books book = (Books) o;
            results = this.getISBN().equals(((Books) o).getISBN());
        }
        return results;
    }

    /**
     * Function to map ISBN, the primary key
     * @return hash map
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.getISBN());
    }

    /**
     * String representation of a book
     * @return
     */
    @Override
    public String toString(){
        return ISBN;
    }
}
