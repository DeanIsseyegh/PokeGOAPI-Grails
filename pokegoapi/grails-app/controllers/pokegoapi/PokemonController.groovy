package pokegoapi

class PokemonController {
	def pokemonService
	def pokeGoApiService

	static allowedMethods = [nearbyPokemon: "GET"]

	def update() {
		PokeradarUpdateJob.triggerNow()
	}

	def nearbyPokemon() {
		println "Getting neraby pokemon for lat: ${params.lat} and lon: ${params.lon}"

		if (!params.lat && !params.lon) {
			return [nearbyPokemons: []]
		}

		def nearbyPokemon = []
		try {
			nearbyPokemon += pokeGoApiService.getPokemon(params.lat, params.lon)
		} catch (Exception ex) {
			println "Exception getting pokemon from niantic API: $ex.stackTrace"
		}
		nearbyPokemon += pokemonService.getPokemonForLocation(params.lat, params.lon)

		nearbyPokemon = nearbyPokemon.collect{it.pokemonId}.unique().sort()

		println "Returning nearby pokemon:\n ${nearbyPokemon}"
		[nearbyPokemons: nearbyPokemon]
	}

	def pokestopsAndGyms() {
		println "Getting nearby pokestops and gyms for lat: ${params.lat} and lon: ${params.lon}"

		if (!params.lat && !params.lon) {
			return [pokestops: [], gyms: []]
		}

		def pokestopsAndGyms = pokeGoApiService.getPokestopsAndGyms(params.lat, params.lon)

		[pokestops: pokestopsAndGyms.pokestops.size(), gyms: pokestopsAndGyms.gyms.size()]
	}

}
