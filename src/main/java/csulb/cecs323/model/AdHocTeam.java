package csulb.cecs323.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AD_HOC_TEAM")
public class AdHocTeam extends AuthoringEntities {
    /**
     * Null constructor
     */
    public AdHocTeam(){
        super();
    }

    /**
     * Arguments constructor
     * @param email The email address of the authoring entity
     * @param name The name of the authoring entity
     */
    public AdHocTeam (String email, String name) {
        super(email, name);
    }
}