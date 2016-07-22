package pokegoapi

import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass
import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.auth.PtcLogin
import grails.core.GrailsApplication
import grails.plugins.*
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.*

import javax.annotation.PostConstruct

class ApplicationController implements PluginManagerAware {

	GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

	static allowedMethods = [areaInfo: "GET"]

	@Value('${pokemon.go.player.google.token}')
	def googleToken
	@Value('${pokemon.go.player.ptc.username}')
	def ptcUsername
	@Value('${pokemon.go.player.ptc.password}')
	def ptcPassword

	PokemonGo go
	MapObjectService mapObjectService

	@PostConstruct
	def init() {
		OkHttpClient httpClient = new OkHttpClient()
		//RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(googleToken)
		RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new PtcLogin(httpClient).login(ptcUsername, ptcPassword)
		go = new PokemonGo(auth, httpClient)
	}

    def index() {}

	def player() {
		[profile: go.getPlayerProfile().username]
	}

	def nearbyPokemon() {
		[nearbyPokemons: mapObjectService.getNearbyPokemon(go, params.lat, params.lon)]
	}

	private getMapObject(go, lat, lon) {
		go.latitude = lat as Double
		go.longitude = lon as Double
		Map map = new Map(go)
		map.getMapObjects()
	}

}
