package hello.data.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Level1Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String value;

    @JoinColumn(name = "level1_id")
    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<Level2Item> level2items = new HashSet<Level2Item>();

    public Level1Item() {
        super();
    }

    public Level1Item(String val) {
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

    public Set<Level2Item> getLevel2Items() {
        return level2items;
    }

    public void setLevel2Items(Set<Level2Item> level2items) {
        this.level2items = level2items;
    }

}
