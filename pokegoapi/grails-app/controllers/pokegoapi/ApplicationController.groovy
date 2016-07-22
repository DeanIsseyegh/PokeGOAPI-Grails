package pokegoapi

import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass
import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.auth.GoogleLogin
import grails.core.GrailsApplication
import grails.plugins.*
import okhttp3.OkHttpClient

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
		println "test3"
		OkHttpClient httpClient = new OkHttpClient()
		String token = ""
		RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(token)
		PokemonGo go = new PokemonGo(auth,httpClient)

		println "Player info:"
		println go.getPlayerProfile()

		go.latitude = 51.517959999999995 as Double
		go.longitude = -0.10827279999999999 as Double

		Map map = new Map(go)
		def mapObjects = map.getMapObjects()

		println "Map Objects:\n ${mapObjects}"


        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
