package pokegoapi

class PokemonController {
	def pokemonService

	static allowedMethods = [nearbyPokemon: "GET"]

	def update() {
		PokeradarUpdateJob.triggerNow()
	}

	def nearbyPokemon() {
		println "Getting neraby pokemon for lat: ${params.lat} and lon: ${params.lon}"
		def nearbyPokemon = (params.lat && params.lon) ? pokemonService.getPokemonForLocation(params.lat, params.lon) : []
		println "Returning nearby pokemon:\n ${nearbyPokemon}"
		[nearbyPokemons: nearbyPokemon.collect{it.pokemonId}]
	}

}
