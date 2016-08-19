package pokegoapi

import com.pokegoapi.api.PokemonGo
import com.pokegoapi.auth.PtcCredentialProvider
import grails.transaction.Transactional
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value

import javax.annotation.PostConstruct

@Transactional
class PokeGoApiService {

    @Value('${pokemon.go.player.ptc.loginTimeout}')
    static LOGIN_TIMEOUT

    @Value('${pokemon.go.player.google.token}')
    def googleToken
    @Value('${pokemon.go.player.ptc.username}')
    def ptcUsername
    @Value('${pokemon.go.player.ptc.password}')
    def ptcPassword

    def lastLogin

	PokemonGo go

	@PostConstruct
	def init() {
		OkHttpClient httpClient = new OkHttpClient()
		//RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(googleToken)
		def auth = new PtcCredentialProvider(httpClient, ptcUsername, ptcPassword)
		go = new PokemonGo(auth, httpClient)
		lastLogin = new Date().time
	}

	def getPokestops(lat, lon) {
		def map = getMap(lat, lon)
	}

	private getMap(lat, lon) {
		autoRelogin()
		go.setLocation(lat, lon, 1)
		go.getMap()
	}

	private autoRelogin() {
		if ((new Date().time) <= lastLogin + LOGIN_TIMEOUT) {
			init()
			}
		}
}
