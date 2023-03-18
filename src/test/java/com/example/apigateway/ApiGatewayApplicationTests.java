package com.example.apigateway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ApiGatewayApplicationTests {

  @Test
  void contextLoads(ApplicationContext context) {
    assertThat(context, notNullValue());
  }

}
