package hello.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Level0Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String value;

    @ManyToOne(optional = false, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    Level1Item level1;

    public Level0Item() {
        super();
    }

    public Level0Item(String val) {
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

    public Level1Item getLevel1() {
        return level1;
    }

    public void setLevel1(Level1Item level1) {
        this.level1 = level1;
    }

}
