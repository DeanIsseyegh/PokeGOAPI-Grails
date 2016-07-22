package pokegoapi

import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass
import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.auth.GoogleLogin
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
	def go

	@PostConstruct
	def init() {
		OkHttpClient httpClient = new OkHttpClient()
		RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(token)
		go = new PokemonGo(auth, httpClient)
	}

    def index() {}

	def player() {
		[profile: go.getPlayerProfile()]
	}

	def areaInfo() {
		if (go) {
			println "Player info:"
			println go.getPlayerProfile()

			def (lat,lon) = [params.latitude, params.longitude]

			go.latitude = lat as Double
			go.longitude = lon as Double

			Map map = new Map(go)
			def mapObjects = map.getMapObjects()

			println "Map Objects:\n ${mapObjects}"
		}
	}
}
