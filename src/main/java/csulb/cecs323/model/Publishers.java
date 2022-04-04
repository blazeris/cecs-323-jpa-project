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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =
        {"name", "email", "phone"})})
@NamedNativeQuery(
        name = "ReturnPublishers",
        query = "SELECT * " +
                "FROM PUBLISHERS ",
        resultClass = Publishers.class
)
@NamedNativeQuery(
        name = "ReturnPublisher",
        query = "SELECT * " +
                "FROM PUBLISHERS " +
                "WHERE NAME = ? ",
        resultClass = Publishers.class
)
public class Publishers {
    // The name of the publisher
    @Id
    @Column(nullable = false, length = 80)
    private String name;

    // The email of the publisher
    @Column(nullable=false, length = 80)  
    private String email;

    // The phone number of the publisher
    @Column(nullable=false, length = 24)
    private String phone;

   
    /**
    * Null constructor
    */
    public Publishers() {

    } 

  
    /**
     * Arguments constructor
     * @param name
     * @param email
     * @param phone
     */
    public Publishers (String name, String email, String phone) {
        this.name = name;        
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString () {
        return name;
    }
    @Override
    public boolean equals(Object o){
        boolean results = false;
        if (this == o){
            results = true;
        } else if (o == null || getClass() != o.getClass()){
            results = false;
        }
        else {
            Publishers publisher = (Publishers) o;
            results = this.getName().equals(publisher.getName());
        }
        return results;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}