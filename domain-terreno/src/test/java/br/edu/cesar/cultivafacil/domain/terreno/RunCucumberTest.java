package br.edu.cesar.cultivafacil.domain.terreno;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Disabled("Steps implementados na Entrega 2")
@Suite
@IncludeEngines("cucumber")
@SelectPackages("br.edu.cesar.cultivafacil.domain.terreno")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class RunCucumberTest {
}
