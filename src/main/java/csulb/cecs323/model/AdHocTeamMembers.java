package csulb.cecs323.model;
import javax.persistence.*;
import java.util.Objects;
public class AdHocTeamMembers {
    //individual authors email
    @Id
    @Column(nullable=false, length = 30)
    private String IndividualAuthorsEmail;
    //email of the member
    @Column(nullable = false, length = 50)
    private String AdHocTeamsEmail;

    /**
     * Null constructor
     */
    public AdHocTeamMembers(){

    }
/*
 *Arguments constructor
 *@param IndividualAuthorsEmail
 *@param AdHocTeamsEmail
 */
    public AdHocTeamMembers(String name, String email){
    this.IndividualAuthorsEmail = IndividualAuthorsEmail;
    this.AdHocTeamsEmail = AdHocTeamsEmail;
    }

    public String getIndividualAuthorsEmail(){
        return IndividualAuthorsEmail;
    }
    public String getAdHocTeamsEmail(){
        return AdHocTeamsEmail;
    }
    public void setIndividualAuthorsEmail(String IndividualAuthorsEmail){
        this.IndividualAuthorsEmail=IndividualAuthorsEmail;
    }
    public void setAdHocTeamsEmail(String email){
        this.AdHocTeamsEmail=AdHocTeamsEmail;
    }
    /**
     * toString function for members
     * @return the members name and email
     */
    @Override
    public String toString () {
        return "Member's name " + this.IndividualAuthorsEmail + "\tEmail: " + this.AdHocTeamsEmail;
    }
    /**
     * Function to check if name equals the object passed
     * @param o Object which is passed in to check if it equals
     * @return the IndividualAuthorsEmail which equals the object passed
     */
    @Override
    public boolean equals (Object o) {
        AdHocTeamMembers Members = (AdHocTeamMembers) o;
        return this.getIndividualAuthorsEmail() == Members.getIndividualAuthorsEmail();
    }
    /**
     * Hash function to map IndividualAuthorsEmail
     * @return hash map
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIndividualAuthorsEmail());
    }
}
