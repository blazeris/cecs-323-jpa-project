package csulb.cecs323.model;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "AD_HOC_TEAM_MEMBERS")
public class AdHocTeamMembers {
    //individual authors email
    @Id
    @OneToOne
    @JoinColumn(name= "INDIVIDUAL_AUTHORS_EMAIL")
    private AdHocTeamMembers IndividualAuthorsEmail;
    //email of the member
    @Id
    @OneToOne
    @JoinColumn(name ="AD_HOC_TEAMS_EMAIL")
    private AdHocTeamMembers AdHocTeamsEmail;

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

    public AdHocTeamMembers getIndividualAuthorsEmail(){
        return IndividualAuthorsEmail;
    }
    public AdHocTeamMembers getAdHocTeamsEmail(){
        return AdHocTeamsEmail;
    }
    public void setIndividualAuthorsEmail(AdHocTeamMembers IndividualAuthorsEmail){
        this.IndividualAuthorsEmail = IndividualAuthorsEmail;
    }
    public void setAdHocTeamsEmail(AdHocTeamMembers AdHocTeamsEmail){
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
