package pl.sly.tools.springbootadmindocker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.sly.tools.springbootadmindocker.config.InsecureConfig;
import pl.sly.tools.springbootadmindocker.config.SecurityConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class SecurityConfigTest {

	private static final String USERNAME_PARAMETER = "username";
	private static final String USERNAME = "test";
	private static final String PASSWORD = "test";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void whenSecurityEnabledByDefault_shouldInjectSecurityConfig() {
		// when
		SecurityConfig securityConfig = applicationContext.getBean(SecurityConfig.class);

		// then
		Assert.assertNotNull(securityConfig);
	}

	@Test(expected = NoSuchBeanDefinitionException.class)
	public void whenSecurityEnabledByDefault_shouldNotInjectInsecureConfig() {
		// when
		applicationContext.getBean(InsecureConfig.class);

		// then
		// NoSuchBeanDefinitionException should be thrown
	}

	@Test
	public void whenSecurityEnabledByDefaultAndValidCredentials_shouldAuthenticateSuccess() throws Exception {
		// given
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user(USERNAME_PARAMETER, USERNAME)
				.password(PASSWORD);

		// when & then
		mockMvc.perform(login)
				.andExpect(authenticated().withUsername(USERNAME));
	}
}
