package csulb.cecs323.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WRITING_GROUP")
public class WritingGroup  extends AuthoringEntities {
    /**
     * Null constructor
     */
    public WritingGroup(){

    }

    /**
     * Arguments constructor
     * @param email The email address of the authoring entity
     * @param name The name of the authoring entity
     * @param headWriter The head writer of the authoring entity
     * @param yearFormed The year the entity was formed
     */
    public WritingGroup (String email, String name,
                              String headWriter, int yearFormed) {
        super(email, name, headWriter, yearFormed);
    }

    /**
     * Gives more detailed information about the writing group
     * @return detailed information about the writing group
     */
    @Override
    public String info(){
        return super.info() + "Head Writer: " + getHeadWriter() + "Year Formed: " + getYearFormed();
    }
}
