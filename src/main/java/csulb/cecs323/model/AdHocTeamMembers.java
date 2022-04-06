package csulb.cecs323.model;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "AD_HOC_TEAM_MEMBERS")
/**
 * Association class between Individual Authors and Ad Hoc Team that represents the authors part of a team
 */
public class AdHocTeamMembers {
    //individual authors email
    @Id
    @ManyToOne
    @JoinColumn(name= "INDIVIDUAL_AUTHORS_EMAIL")
    private IndividualAuthor individualAuthorsEmail;
    //email of the member
    @Id
    @ManyToOne
    @JoinColumn(name ="AD_HOC_TEAMS_EMAIL")
    private AdHocTeam adHocTeamsEmail;

    /**
     * Null constructor
     */
    public AdHocTeamMembers(){

    }
    /**
     *Arguments constructor
     *@param individualAuthorsEmail The author that is being added
     *@param adHocTeamsEmail The team to add to
     **/
    public AdHocTeamMembers(IndividualAuthor individualAuthorsEmail, AdHocTeam adHocTeamsEmail){
    this.individualAuthorsEmail = individualAuthorsEmail;
    this.adHocTeamsEmail = adHocTeamsEmail;
    }

    public IndividualAuthor getIndividualAuthorsEmail(){
        return individualAuthorsEmail;
    }
    public AdHocTeam getAdHocTeamsEmail(){
        return adHocTeamsEmail;
    }
    public void setIndividualAuthorsEmail(IndividualAuthor individualAuthorsEmail){
        this.individualAuthorsEmail = individualAuthorsEmail;
    }
    public void setAdHocTeamsEmail(AdHocTeam adHocTeamsEmail){
        this.adHocTeamsEmail=adHocTeamsEmail;
    }
    /**
     * toString function for members
     * @return the members name and email
     */
    @Override
    public String toString () {
        return "Member's name " + this.individualAuthorsEmail + "\tEmail: " + this.adHocTeamsEmail;
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
