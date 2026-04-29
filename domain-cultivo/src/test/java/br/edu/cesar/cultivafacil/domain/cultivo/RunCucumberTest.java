package br.edu.cesar.cultivafacil.domain.cultivo;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("br.edu.cesar.cultivafacil.domain.cultivo")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "classpath:br/edu/cesar/cultivafacil/domain/cultivo/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.edu.cesar.cultivafacil.domain.cultivo")
public class RunCucumberTest {
}
