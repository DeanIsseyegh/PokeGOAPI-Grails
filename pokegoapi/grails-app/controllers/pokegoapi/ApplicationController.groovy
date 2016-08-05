package pokegoapi

import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

class ApplicationController implements PluginManagerAware {

	GrailsApplication grailsApplication
    GrailsPluginManager pluginManager
	def mapObjectService

	static allowedMethods = [areaInfo: "GET"]

    def index() {}

	def nearbyPokemon() {
		[nearbyPokemons: mapObjectService.getNearbyPokemon(go, params.lat, params.lon)]
	}

}
