package com.ace.start;

import com.ace.entity.Person;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan("com.ace")
public class DBStart {
    @Bean(name = "person1")
    public static Person get(){
        return new Person();
    }


    public static void main(String[] args) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
//        List<Person> query = jdbcTemplate.query("select * from person_info", new RowMapper<Person>() {
//            @Override
//            public Person mapRow(ResultSet resultSet, int i) throws SQLException {
//                Person person = new Person();
//                person.setId(resultSet.getString("id"));
//                person.setName(resultSet.getString("name"));
//                person.setSex(resultSet.getString("sex"));
//                person.setTelephone(resultSet.getString("telephone"));
//                return person;
//            }
//        });
//        System.out.println(query);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

    }
}
