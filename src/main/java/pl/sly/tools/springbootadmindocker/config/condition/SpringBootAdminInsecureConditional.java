package pl.sly.tools.springbootadmindocker.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Matches true if {spring.boot.admin.security.enabled} property is defined and value is false.
 */
public class SpringBootAdminInsecureConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();

        if (environment != null && Boolean.FALSE.toString().equalsIgnoreCase(
                environment.getProperty(Constants.SPRING_BOOT_ADMIN_SECURITY_ENABLED))) {
            return true;
        }

        return false;
    }
}
