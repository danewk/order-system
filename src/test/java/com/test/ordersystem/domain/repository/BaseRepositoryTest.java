package com.test.ordersystem.domain.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties =
    {"spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
    })
public class BaseRepositoryTest {

}