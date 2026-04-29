package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue",
    value = "br.edu.cesar.cultivafacil.domain.maquinario.maquina.steps")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty")
public class RunCucumberTest {
}
