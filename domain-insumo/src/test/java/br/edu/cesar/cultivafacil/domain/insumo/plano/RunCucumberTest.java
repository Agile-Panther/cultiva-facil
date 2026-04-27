package br.edu.cesar.cultivafacil.domain.insumo.plano;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("br/edu/cesar/cultivafacil/domain/insumo/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
        value = "br.edu.cesar.cultivafacil.domain.insumo.plano")
class RunCucumberTest {
}