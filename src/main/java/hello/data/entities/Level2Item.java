package hello.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Level2Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String value;

    public Level2Item() {
        super();
    }

    public Level2Item(String val) {
        this();
        value = val;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
