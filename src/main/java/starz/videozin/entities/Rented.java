package starz.videozin.entities;


import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rented {
    @Id
    String pid;
    String ssn;
    String date;
    @Value("${some.key:false}")
    boolean returned;

    public Rented() {
    }

    public Rented(String pid, String ssn, String date, boolean returned) {
        this.pid = pid;
        this.ssn = ssn;
        this.date = date;
        this.returned = returned;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
