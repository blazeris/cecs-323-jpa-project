package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Objects;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "publisher"}),
        @UniqueConstraint(columnNames = {"title", "authoringEntity"})
})
public class Books implements Serializable {
    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false)
    private int yearPublished;

    @Column(nullable = false)
    private AuthoringEntity authoringEntity;

    @Column(nullable = false)
    private Publisher publisher;

    public Books(){

    }

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

    @Override
    public int hashCode(){
        return Objects.hash(this.getISBN());
    }

    @Override
    public String toString(){
        return ISBN;
    }
}
