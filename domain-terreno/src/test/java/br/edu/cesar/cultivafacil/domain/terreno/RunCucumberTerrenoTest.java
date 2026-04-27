package br.edu.cesar.cultivafacil.domain.terreno;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Runner Cucumber para o subdomínio terreno (F-04).
 * Segue o padrão SGB — seção 2.10.2 do documento de modelagem.
 * <p>
 * Executa todos os cenários definidos em:
 *   src/test/resources/features/terreno.feature
 * <p>
 * Os steps serão implementados na Entrega 2, junto às camadas
 * de aplicação e infraestrutura (seção 2.10 do documento).
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("br.edu.cesar.cultivafacil.domain.terreno")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class RunCucumberTerrenoTest {
}