package csulb.cecs323.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INDIVIDUAL_AUTHOR")
public class IndividualAuthor extends AuthoringEntities {
    public IndividualAuthor(){
        super();
    }

    public IndividualAuthor (String email, String name) {
        super(email, name);
    }
}
