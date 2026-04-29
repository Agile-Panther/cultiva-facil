package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;

@IncludeEngines("cucumber")
@SelectClasspathResource("f-01-f-02-propriedade.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "br.edu.cesar.cultivafacil.domain.propriedade.propriedade.steps")
public class CucumberTestRunner {
}
