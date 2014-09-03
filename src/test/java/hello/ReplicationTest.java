package hello;

import hello.Application;
import hello.data.entities.Level0Item;
import hello.data.entities.Level1Item;
import hello.data.entities.Level2Item;

import java.util.List;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@TransactionConfiguration
public class ReplicationTest {
    public Logger logger = LoggerFactory.getLogger(ReplicationTest.class);

    @Qualifier("pendingSessionFactory")
    @Autowired
    LocalSessionFactoryBean pendingSessionFactory;

    @Qualifier("appliedSessionFactory")
    @Autowired
    LocalSessionFactoryBean appliedSessionFactory;

    @Test
    public void testReplicationRemovesOrphans1() {
        // test data
        Level2Item level2 = new Level2Item("Level2");

        Level1Item level1 = new Level1Item("Level1");
        level1.getLevel2Items().add(level2);

        hello.data.entities.Level0Item level0 = new Level0Item("Level0");
        level0.setLevel1(level1);

        // save data to pending database
        SessionFactory pendingFactory = pendingSessionFactory.getObject();
        Session pendingSession = pendingFactory.openSession();
        Transaction ptx = pendingSession.beginTransaction();
        pendingSession.save(level0);
        ptx.commit();
        pendingSession.close();

        // reload level0 before replication
        pendingSession = pendingFactory.openSession();
        level0 = (hello.data.entities.Level0Item) pendingSession.get(Level0Item.class,
                level0.getId());
        pendingSession.close();

        // replicate data to applied database
        SessionFactory appliedFactory = appliedSessionFactory.getObject();
        Session appliedSession = appliedFactory.openSession();
        Transaction atx = appliedSession.beginTransaction();
        appliedSession.replicate(level0, ReplicationMode.OVERWRITE);
        atx.commit();
        List result = appliedSession.createQuery("SELECT l FROM Level2Item l").list();
        Assert.assertEquals("Level2item added", 1, result.size());
        appliedSession.close();

        // perform changes on pending database (remove level2item)
        pendingSession = pendingFactory.openSession();
        ptx = pendingSession.beginTransaction();
        level0 = (Level0Item) pendingSession.get(Level0Item.class, level0.getId());
        Level2Item item = level0.getLevel1().getLevel2Items().iterator().next();
        level0.getLevel1().getLevel2Items().remove(item);
        pendingSession.save(level0);
        ptx.commit();
        pendingSession.close();

        // reload level0 before replication
        pendingSession = pendingFactory.openSession();
        level0 = (hello.data.entities.Level0Item) pendingSession.get(Level0Item.class,
                level0.getId());
        pendingSession.close();

        // replicate changed system to applied database
        appliedSession = appliedFactory.openSession();
        atx = appliedSession.beginTransaction();
        appliedSession.replicate(level0, ReplicationMode.OVERWRITE);
        atx.commit();
        result = appliedSession.createQuery("SELECT l FROM Level2Item l").list();
        Assert.assertEquals("Level2item removed", 0, result.size());
        appliedSession.close();
    }
}
