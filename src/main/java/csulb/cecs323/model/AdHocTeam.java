package csulb.cecs323.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AD_HOC_TEAM")
public class AdHocTeam extends AuthoringEntities {
    public AdHocTeam(){
        super();
    }

    public AdHocTeam (String email, String name) {
        super(email, name);
    }
}