package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Objects;
/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2021 David Brown <david.brown@csulb.edu>
 *
 */

@Entity
@Table(name = "AUTHORING_ENTITIES",
        uniqueConstraints = {
                @UniqueConstraint(name = "authoring_entities_unique_01", columnNames = {"name", "email"})
        })
@NamedNativeQuery(
        name = "ReturnAuthoringEntities",
        query = "SELECT * " +
                "FROM AUTHORING_ENTITIES " +
                "WHERE AUTHORING_ENTITY_TYPE = ? ",
        resultClass = AuthoringEntities.class
)
@NamedNativeQuery(name = "ReturnAuthoringEntitiesPrimary",
        query = "SELECT email, authoring_entity_type FROM AuthoringEntities")
@Inheritance
@DiscriminatorColumn(name = "AUTHORING_ENTITY_TYPE", columnDefinition = "CHAR(31)")
public abstract class AuthoringEntities {
    // The email associated with the authoring entity
    @Id
    @Column(nullable = false, length = 30)
    private String email;

    // The name of the authoring entity
    @Column(nullable=false, length = 80)
    private String name;

    // The head writer of the authoring entity
    @Column(length = 80)
    private String headWriter;

    // The year that the authoring entity was formed
    @Column()
    private int yearFormed;


    /**
     * Null constructor
     */
    public AuthoringEntities() {

    }

    /**
     * Authoring Entity constructor
     * @param email The email address of the authoring entity
     * @param name The name of the authoring entity
     */
    public AuthoringEntities (String email, String name) {
        this.email = email;
        this.name = name;
        this.headWriter = null;
        this.yearFormed = 0;
    }

    /**
     * Writing group constructor
     * @param email The email address of the authoring entity
     * @param name The name of the authoring entity
     * @param headWriter The head writer of the authoring entity
     * @param yearFormed The year the entity was formed
     */
    public AuthoringEntities (String email, String name,
                              String headWriter, int yearFormed) {
        this.email = email;
        this.name = name;
        this.headWriter = headWriter;
        this.yearFormed = yearFormed;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadWriter() {
        return headWriter;
    }

    public void setHeadWriter(String headWriter) {
        this.headWriter = headWriter;
    }

    public int getYearFormed() {
        return yearFormed;
    }

    public void setYearFormed(int yearFormed) {
        this.yearFormed = yearFormed;
    }


    /**
     * toString function for email address
     * @return email address of authoring entity
     */
    @Override
    public String toString () {
        return email;
    }

    /**
     * Function to check if email is equal to object passed
     * @param o Object being checked for equivalence
     * @return Whether or not object is equal
     */
    @Override
    public boolean equals(Object o){
        boolean results = false;
        if (this == o){
            results = true;
        } else if (o == null || getClass() != o.getClass()){
            results = false;
        }
        else {
            AuthoringEntities authoringEntity = (AuthoringEntities) o;
            results = this.getEmail().equals(authoringEntity.getEmail());
        }
        return results;
    }

    /**
     * Function to map email, the primary key
     * @return hash map
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail());
    }
}