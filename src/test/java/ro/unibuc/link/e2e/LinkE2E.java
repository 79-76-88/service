package ro.unibuc.link.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.web.bind.annotation.CrossOrigin;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = "E2E")
@CrossOrigin
public class LinkE2E {
}
