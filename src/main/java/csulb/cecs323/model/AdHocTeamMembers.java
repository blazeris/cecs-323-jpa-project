package csulb.cecs323.model;
import javax.persistence.*;
import java.util.Objects;
public class AdHocTeamMembers {
    //name that uniquely identifies the member
    @Id
    @Column(nullable=false, length = 30)
    private String name;
    //email of the member
    @Column(nullable = false, length = 50)
    private String email;

    /**
     * Null constructor
     */
    public AdHocTeamMembers(){

    }
/*
 *
 *@param name
 *@param email
 */
    public AdHocTeamMembers(String name, String email){
    this.name = name;
    this.email = email;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    /**
     * toString function for members
     * @return the members name and email
     */
    @Override
    public String toString () {
        return "Member's name " + this.name + "\tEmail: " + this.email;
    }
    /**
     * Function to check if name equals the object passed
     * @param o Object which is passed in to check if it equals
     * @return the name which equals the object passed
     */
    @Override
    public boolean equals (Object o) {
        AdHocTeamMembers Members = (AdHocTeamMembers) o;
        return this.getName() == Members.getName();
    }
    /**
     * Hash function to map members name
     * @return hash map
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
