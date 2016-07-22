package pokegoapi

import POGOProtos.Map.Pokemon.NearbyPokemonOuterClass
import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass
import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.api.map.MapObjects
import com.pokegoapi.api.map.fort.FortDetails
import com.pokegoapi.api.map.fort.Pokestop
import com.pokegoapi.auth.GoogleLogin
import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugins.*
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.*

import javax.annotation.PostConstruct

class ApplicationController implements PluginManagerAware {

	GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

	static allowedMethods = [areaInfo: "GET"]

	@Value('${pokemon.go.player.token}')
	def token
	PokemonGo go

	@PostConstruct
	def init() {
		OkHttpClient httpClient = new OkHttpClient()
		RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(token)
		go = new PokemonGo(auth, httpClient)
	}

    def index() {}

	def player() {
		[profile: go.getPlayerProfile().username]
	}

	def nearbyPokemon() {
		def nearbyPokemons = []

		MapObjects mapObjects = getMapObject(go, params.lat, params.lon)
		mapObjects.getNearbyPokemons().each{ NearbyPokemonOuterClass.NearbyPokemon nearbyPokemon ->
			nearbyPokemons << nearbyPokemon.pokemonId.name()
		}

		[nearbyPokemons: nearbyPokemons]
	}

	private getMapObject(go, lat, lon) {
		go.latitude = lat as Double
		go.longitude = lon as Double
		Map map = new Map(go)
		map.getMapObjects()
	}
}
