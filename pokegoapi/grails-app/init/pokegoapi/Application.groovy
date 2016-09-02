package pokegoapi

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

class Application extends GrailsAutoConfiguration implements EnvironmentAware {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Override
    void setEnvironment(Environment environment) {
        //Set up Configuration directory
        def confFolder = "conf/"

        println ""
        println "Loading configuration from ${confFolder}"
        def appConfigured = new File('pokegoapi-config.groovy').exists()
        println "Loading configuration file ${new File('pokegoapi-config.groovy')}"
        println "Config file found : " + appConfigured

        if (appConfigured) {
            def config = new ConfigSlurper().parse(new File('pokegoapi-config.groovy').toURL())
            environment.propertySources.addFirst(new MapPropertySource("pokegoapi-config", config))
        }
    }
}