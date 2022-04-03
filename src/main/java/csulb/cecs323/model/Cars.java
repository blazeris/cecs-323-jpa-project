package csulb.cecs323.model;

/**
 * Individual, physical automobiles that someone can drive on land to transport one or more passengers
 * and a limited amount of cargo around.  Cars have four wheels and usually travel on paved roads.
 */
public class Cars {
    /** The unique ID of the vehicle.  Limited to 17 characters. */
    private String VIN;

    /** The name of the corporation which manufactured the vehicle.  Limited to 40 characters. */
    private String manufacturer;

    /** The popular name of the vehicle, like the Prius for Toyota.  Limited to 20 characters. */
    private String model;

    /** The year that the vehicle was manufactured.  For now, do not worry about validating this #. */
    private int year;

    @Override
    public String toString () {
        return "Cars - VIN: " + this.VIN + " Manufacturer: " + this.manufacturer +
                " Model: " + this.model + " year: " + this.year;
    }
}
