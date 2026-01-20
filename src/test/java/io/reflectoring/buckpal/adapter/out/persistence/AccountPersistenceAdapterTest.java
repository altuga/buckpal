package io.reflectoring.buckpal.adapter.out.persistence;

import java.time.LocalDateTime;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.ActivityWindow;
import io.reflectoring.buckpal.application.domain.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.AfterAll;

import static io.reflectoring.buckpal.common.AccountTestData.*;
import static io.reflectoring.buckpal.common.ActivityTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
@ActiveProfiles("test")
class AccountPersistenceAdapterTest {

	private static final String DB_SELECTOR_PROPERTY = "buckpal.db";
	private static final String DB_SELECTOR_ENV = "BUCKPAL_DB";
	private static Object mysql;
	private static Class<?> mysqlContainerClass;

	private static String selectedDb() {
		String fromProperty = System.getProperty(DB_SELECTOR_PROPERTY);
		if (fromProperty != null && !fromProperty.isBlank()) {
			return fromProperty.trim();
		}
		String fromEnv = System.getenv(DB_SELECTOR_ENV);
		if (fromEnv != null && !fromEnv.isBlank()) {
			return fromEnv.trim();
		}
		return "h2";
	}

	@AfterAll
	static void stopContainer() {
		if (mysql != null) {
			invokeVoid(mysql, "stop");
		}
	}

	private static void startMysqlContainer() {
		try {
			mysqlContainerClass = Class.forName("org.testcontainers.containers.MySQLContainer");
			Object container = mysqlContainerClass.getConstructor(String.class).newInstance("mysql:8.0");
			container = invoke(container, "withDatabaseName", new Class<?>[]{String.class}, new Object[]{"buckpal"});
			container = invoke(container, "withUsername", new Class<?>[]{String.class}, new Object[]{"buckpal"});
			container = invoke(container, "withPassword", new Class<?>[]{String.class}, new Object[]{"buckpal"});
			invokeVoid(container, "start");
			mysql = container;
		} catch (Exception e) {
			throw new IllegalStateException(
					"MySQL test mode requested but Testcontainers is not available or failed to start. "
							+ "Ensure the 'org.testcontainers:mysql' dependency is present and Docker is running.",
					e);
		}
	}

	private static Object invoke(Object target, String method, Class<?>[] parameterTypes, Object[] args) {
		try {
			return target.getClass().getMethod(method, parameterTypes).invoke(target, args);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to invoke method '" + method + "' via reflection", e);
		}
	}

	private static void invokeVoid(Object target, String method) {
		try {
			target.getClass().getMethod(method).invoke(target);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to invoke method '" + method + "' via reflection", e);
		}
	}

	private static String invokeString(Object target, String method) {
		try {
			Object value = target.getClass().getMethod(method).invoke(target);
			return (String) value;
		} catch (Exception e) {
			throw new IllegalStateException("Failed to invoke method '" + method + "' via reflection", e);
		}
	}

	@DynamicPropertySource
	static void overrideDatasourceProperties(DynamicPropertyRegistry registry) {
		String db = selectedDb();
		if ("mysql".equalsIgnoreCase(db)) {
			startMysqlContainer();

			registry.add("spring.datasource.url", () -> invokeString(mysql, "getJdbcUrl"));
			registry.add("spring.datasource.username", () -> invokeString(mysql, "getUsername"));
			registry.add("spring.datasource.password", () -> invokeString(mysql, "getPassword"));
			registry.add("spring.datasource.driver-class-name", () -> invokeString(mysql, "getDriverClassName"));
		} else {
			registry.add("spring.datasource.url",
					() -> "jdbc:h2:mem:buckpal;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
			registry.add("spring.datasource.username", () -> "sa");
			registry.add("spring.datasource.password", () -> "");
			registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
		}

		// Ensure schema is created for the test DB and removed afterwards.
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
	}

	@Autowired
	private AccountPersistenceAdapter adapterUnderTest;

	@Autowired
	private ActivityRepository activityRepository;

	@Test
	@Sql("AccountPersistenceAdapterTest.sql")
	void loadsAccount() {
		Account account = adapterUnderTest.loadAccount(new AccountId(1L), LocalDateTime.of(2018, 8, 10, 0, 0));

		assertThat(account.getActivityWindow().getActivities()).hasSize(2);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(500));
	}

	@Test
	void updatesActivities() {

		Account account = defaultAccount()
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withId(null)
								.withMoney(Money.of(1L)).build()))
				.build();

		adapterUnderTest.updateActivities(account);

		assertThat(activityRepository.count()).isEqualTo(1);

		ActivityJpaEntity savedActivity = activityRepository.findAll().get(0);
		assertThat(savedActivity.getAmount()).isEqualTo(1L);
	}

}