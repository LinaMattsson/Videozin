package starz.videozin.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rented {
    @Id
    String pid;
    String ssn;
    String date;
    //Boolean returned
}
