// src/main/java/org/example/skills/service/MemberService.java
package org.example.skills.service;

import org.example.skills.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private JdbcTemplate jdbc;

    public boolean login(String name, String passwd) {
        Boolean ok = jdbc.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM admin WHERE name=? AND passwd=? LIMIT 1)",
                Boolean.class,
                name, passwd
        );
        return Boolean.TRUE.equals(ok);
    }

    public boolean register(String code, String name, String birth, String tel, String address, String company) {

        String sql = "INSERT INTO customer (code, name, birth, tel, address, company) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbc.update(sql, code, name, java.sql.Date.valueOf(birth), tel, address, company);

        return rowsAffected == 1;
    }

    public List<Customer> getCustomers(String keyword) {

        System.out.println(keyword);
        String sql = "SELECT * FROM customer WHERE `name` LIKE CONCAT('%', ?, '%')";
        List<Customer> customers = jdbc.query(sql,
                new RowMapper<Customer>() {
                    @Override
                    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        Customer customer = new Customer(
                                resultSet.getString("code"),
                                resultSet.getString("name"),
                                resultSet.getDate("birth"),
                                resultSet.getString("tel"),
                                resultSet.getString("address"),
                                resultSet.getString("company")
                        );
                        return customer;
                    }
                }, keyword);
        System.out.println(customers.size());

        return customers;
    }
}
