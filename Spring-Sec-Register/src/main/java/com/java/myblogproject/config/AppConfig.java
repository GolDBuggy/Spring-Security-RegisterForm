package com.java.myblogproject.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.java.myblogproject")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Bean
    public DataSource dataSource(){
        ComboPooledDataSource source=new ComboPooledDataSource();
        try {
            source.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        source.setJdbcUrl(environment.getProperty("jdbc.url"));
        source.setUser(environment.getProperty("jdbc.user"));
        source.setPassword(environment.getProperty("jdbc.password"));

        source.setMaxIdleTime(returnPool("connection.pool.maxIdleTime"));
        source.setMaxPoolSize(returnPool("connection.pool.maxPoolSize"));
        source.setMinPoolSize(returnPool("connection.pool.minPoolSize"));
        source.setInitialPoolSize(returnPool("connection.pool.initialPoolSize"));

        return source;
    }

    private int returnPool(String name){
        String str=environment.getProperty(name);
        int result=Integer.parseInt(str);

        return result;
    }


    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return props;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        // create session factorys
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hiberante.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }



    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

}
