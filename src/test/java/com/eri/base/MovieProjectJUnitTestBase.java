package com.eri.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb"
        })
public abstract class MovieProjectJUnitTestBase {
}
