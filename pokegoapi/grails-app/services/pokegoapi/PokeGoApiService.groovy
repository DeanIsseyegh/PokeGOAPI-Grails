package pokegoapi

import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.pokemon.CatchablePokemon
import com.pokegoapi.api.map.pokemon.NearbyPokemon
import com.pokegoapi.auth.PtcCredentialProvider
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value

import javax.annotation.PostConstruct

@Transactional
class PokeGoApiService {

	@Value('${pokemon.go.player.ptc.loginTimeout}')
	long LOGIN_TIMEOUT

	@Value('${pokemon.go.player.google.token}')
	def googleToken
	@Value('${pokemon.go.player.ptc.username}')
	def ptcUsername
	@Value('${pokemon.go.player.ptc.password}')
	def ptcPassword

	def lastLogin = 0

	PokemonGo go

	@PostConstruct
	def init() {
		try {
			OkHttpClient httpClient = new OkHttpClient()
			//RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth = new GoogleLogin(httpClient).login(googleToken)
			def auth = new PtcCredentialProvider(httpClient, ptcUsername, ptcPassword)
			go = new PokemonGo(auth, httpClient)
			lastLogin = new Date().time
		} catch (Exception ex) {
			println "Exception logging into niantinc: $ex"
		}
	}

	@Cacheable(value = "pokestopsAndGymsCache")
	def getPokestopsAndGyms(lat, lon) {
		println "not from stops cache $lat, $lon"
		def map = getMap(lat as Double, lon as Double)
		[pokestops: map?.mapObjects?.pokestops ?: [], gyms: map?.mapObjects?.gyms ?: []]
	}

	@Cacheable(value = "pokemonCache")
	def getPokemon(lat, lon) {
		println "not from pokemon cache $lat, $lon"
		def map = getMap(lat, lon)
		def pokemon = map?.nearbyPokemon
		pokemon ? pokemon.collect { toPokemon(it) } : []
	}

	@CacheEvict(value = "pokemonCache", allEntries = true)
	def clearPokemonCache(){}

	@CacheEvict(value = "pokestopsAndGymsCache", allEntries = true)
	def clearPokestopsAndGymsCache(){}

	private getMap(lat, lon) {
		autoRelogin()
		if (go) {
			go.setLocation(lat as Double, lon as Double, 1d)
			go.getMap()
		}
	}

	private autoRelogin() {
		if (!go || ((new Date().time) >= lastLogin + LOGIN_TIMEOUT)) {
			init()
		}
	}

	private toPokemon(pokemon) {
		Pokemon.findOrCreateByPokemonId(pokemon.pokemonId.number)
	}
}
