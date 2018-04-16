package starz.videozin.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Customer {
    @Id
    @NotNull
    String socialSecurity;

    @NotNull
    @Size(min=2, max=30)
    String fName;

    @NotNull
    @Size(min=2, max=30)
    String lName;

    @NotNull
    @Size(min=2, max=30)
    String adress;

    @NotNull
    @Size(min=2, max=30)
    String country;

    @NotNull
    @Size(min=2, max=30)
    String city;

    @NotNull
    @Column(nullable=false)
    int zip;

    @NotNull
    @Size(min=8, max=15)
    String phone;

    String mail;

    @OneToMany(mappedBy = "customer")
    List<Movie> rentedMovies;

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
