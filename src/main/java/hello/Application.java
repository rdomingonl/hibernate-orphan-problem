package hello;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
// @EnableJpaRepositories
// @EnableAutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true)
@Import(RepositoryRestMvcConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Qualifier("pendingSessionFactory")
    @Bean
    LocalSessionFactoryBean pendingSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(pendingDataSource());
        sessionFactory.setPackagesToScan("hello");
        return sessionFactory;
    }

    @Qualifier("appliedSessionFactory")
    @Bean
    LocalSessionFactoryBean appliedSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(appliedDataSource());
        sessionFactory.setPackagesToScan("hello");
        return sessionFactory;
    }

    @Bean(name = "pendingDataSource")
    DataSource pendingDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://service-test1.test/t2_config_pending_rd");
        dataSource.setUsername("raymond");
        dataSource.setPassword("geheim");
        return dataSource;
    }

    @Bean(name = "appliedDataSource")
    public DataSource appliedDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://service-test1.test/t2_config_applied_rd");
        dataSource.setUsername("raymond");
        dataSource.setPassword("geheim");
        return dataSource;
    }

    // @Bean(name = "pendingDataSource")
    // DataSource pendingDataSource() {
    // return new SimpleDriverDataSource() {
    // {
    // setDriverClass(org.h2.Driver.class);
    // setUsername("sa");
    // setUrl("jdbc:h2:mem");
    // setPassword("");
    // }
    // };
    // }
    //
    // @Bean(name = "appliedDataSource")
    // public DataSource appliedDataSource() {
    // return new SimpleDriverDataSource() {
    // {
    // setDriverClass(org.h2.Driver.class);
    // setUsername("sa");
    // setUrl("jdbc:h2:mem");
    // setPassword("");
    // }
    // };
    // }

    @Bean
    JdbcTemplate jdbcTemplate1(DataSource appliedDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(appliedDataSource);
        // jdbcTemplate.execute("drop table Level0Item if exists");
        // jdbcTemplate.execute("drop table Level1Item if exists");
        // jdbcTemplate.execute("drop table Level1Item if exists");

        jdbcTemplate.execute("drop table Level0Item");
        jdbcTemplate.execute("drop table Level1Item");
        jdbcTemplate.execute("drop table Level2Item");

        jdbcTemplate.execute("create table Level0Item(" + "ID serial, VALUE varchar(255), level1_id bigint)");
        jdbcTemplate.execute("create table Level1Item(" + "ID serial, VALUE varchar(255))");
        jdbcTemplate.execute("create table Level2Item(" + "ID serial, VALUE varchar(255), level1_id bigint)");
        return jdbcTemplate;
    }

    @Bean
    JdbcTemplate jdbcTemplate2(DataSource pendingDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(pendingDataSource);
        // jdbcTemplate.execute("drop table Level0Item if exists");
        // jdbcTemplate.execute("drop table Level1Item if exists");
        // jdbcTemplate.execute("drop table Level2Item if exists");

        jdbcTemplate.execute("drop table Level0Item");
        jdbcTemplate.execute("drop table Level1Item");
        jdbcTemplate.execute("drop table Level2Item");

        jdbcTemplate.execute("create table Level0Item(" + "ID serial, VALUE varchar(255), level1_id bigint)");
        jdbcTemplate.execute("create table Level1Item(" + "ID serial, VALUE varchar(255))");
        jdbcTemplate.execute("create table Level2Item(" + "ID serial, VALUE varchar(255), level1_id bigint)");
        return jdbcTemplate;
    }
}
