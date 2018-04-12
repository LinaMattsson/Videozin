package starz.videozin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {
    @Id
    String pid;
    @Column(nullable=false)
    String name;
    @Column(nullable=false)
    String category;

}
