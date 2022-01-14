/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright (c)  2000-2022, TradeChannel AB. All rights reserved.
 * Any right to utilize the System under this Agreement shall be subject to the terms and condition of the
 * License Agreement between Customer "X" and TradeChannel AB.
 *
 * TradeseC contains third party software which includes software owned or licensed by a third party and
 * sub licensed to the Customer by TradeChannel AB in accordance with the License Agreement.
 *
 * TradeChannel AB owns the rights to the software product TradeseC.
 *
 * TradeChannel AB grants a right to the Customer and the Customer accepts a non-exclusive,
 * non-transferrable license to use TradeseC and Third Party Software, in accordance with the conditions
 * specified in this License Agreement.
 *
 * The Customer may not use TradeseC or the Third Party Software for time-sharing, rental,
 * service bureau use, or similar use. The Customer is responsible for that all use of TradeseC
 * and the Third Party Software is in accordance with the License Agreement.
 *
 * The Customer may not transfer, sell, sublicense, let, lend or in any other way permit any person or entity
 * other than the Customer, avail himself, herself or itself of or otherwise any rights to TradeseC or the
 * Third Party Software, either directly or indirectly.
 *
 * The Customer may not use, copy, modify or in any other way transfer or use TradeseC or the
 * Third Party Software wholly or partially, nor allow another person or entity to do so, in any way other than
 * what is expressly permitted according to the License Agreement. Nor, consequently, may the Customer,
 * independently or through an agent, reverse engineer, decompile or disassemble TradeseC, the Third Party Software
 * or any accessories that may be related to it.
 *
 * The Customer acknowledges TradeseC <i>(including but not limited to any copyrights, trademarks,
 * documentation, enhancements or other intellectual property or proprietary rights relating to it)</i>
 * and Third Party Software is the proprietary material of the Supplier and respectively Third Party.
 *
 * The Third Party Software are protected by copyright law.
 *
 * The Customer shall not remove, erase or hide from view any information about a patent, copyright,
 * trademark, confidentiality notice, mark or legend appearing on any of TradeseC or Third Party Software,
 * any medium by which they are made available or any form of output produced by them.
 *
 * The License Agreement will only grant the Customer the right to use TradeseC and Third Party Software
 * under the terms of the License Agreement.
 */

package pl.sly.tools.springbootadmindocker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sly.tools.springbootadmindocker.config.InsecureConfig;
import pl.sly.tools.springbootadmindocker.config.SecurityConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Insecure config test.
 */
@SpringBootTest(
        properties = "spring.boot.admin.security.enabled=false",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsecureConfigTest {

    private static final String DEFAULT_PATH = "/";
    private static final String HTML_APP_DIV = "<title>Spring Boot Admin Docker</title>";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * When security disabled should inject insecure config.
     */
    @Test
    public void whenSecurityDisabled_shouldInjectInsecureConfig() {
        // when
        final InsecureConfig insecureConfig = applicationContext.getBean(InsecureConfig.class);

        // then
        assertNotNull(insecureConfig);
    }

    /**
     * When security disabled should not inject security config.
     */
    @Test
    public void whenSecurityDisabled_shouldNotInjectSecurityConfig() {
        final Exception exception = assertThrows(NoSuchBeanDefinitionException.class, () -> {
            applicationContext.getBean(SecurityConfig.class);
        });

        // when

        // then
        // NoSuchBeanDefinitionException should be thrown
    }

    /**
     * When security disabled should return http 200 status code and valid body.
     */
    @Test
    public void whenSecurityDisabled_shouldReturnHttp200statusCodeAndValidBody() {
        // given & when
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(DEFAULT_PATH, String.class);

        // then
        assertNotNull(responseEntity, "Response Entity");
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody()
                                 .contains(HTML_APP_DIV));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
