package ca.vgorcinschi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(pattern="ca\\.vgorcinschi\\.ui.*", type = FilterType.REGEX),
        @ComponentScan.Filter(classes = App.class, type = FilterType.ASSIGNABLE_TYPE)
})
@SpringBootApplication(exclude = App.class)
public class IntegrationTestConfig {
}
