package csulb.cecs323.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WRITING_GROUP")
public class WritingGroup  extends AuthoringEntities {
    public WritingGroup(){

    }
    public WritingGroup (String email, String name,
                              String headWriter, int yearFormed) {
        super(email, name, headWriter, yearFormed);
    }
}
