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
    @Column(nullable=false)
    String format;
    String description;
    boolean rented;

    public Movie() {
    }

    public Movie(String pid, String name, String category, String format, String description, boolean rented) {
        this.pid = pid;
        this.name = name;
        this.category = category;
        this.format = format;
        this.description = description;
        this.rented = rented;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

}