package enqueue.MiniProject;

import java.util.Objects;

public class Person {
    private String name;
    private String country;
    private String mobileNo;

    public Person() {
    }

    public Person(String name, String country, String mobileNo) {
        this.name = name;
        this.country = country;
        this.mobileNo = mobileNo;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(country, person.country) &&
                Objects.equals(mobileNo, person.mobileNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, mobileNo);
    }
}
