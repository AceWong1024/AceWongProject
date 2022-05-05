//package com.ace.Test;
//
//import com.ace.entity.Person;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//public class DBTest {
//    @Test
//    public void test1() throws Exception {
////        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
////        Person query = jdbcTemplate.queryForObject("select * from person_info",Person.class);
////        System.out.println(query);
//    }
//
//    @Test
//    public void test2() throws IOException {
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis/mybatis-config.xml"));
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<Person> list = sqlSession.selectList("com.ace.PersonMapper.getPersonById",1);
//        System.out.println(list);
//        sqlSession.close();
//    }
//}
