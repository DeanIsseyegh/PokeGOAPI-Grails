package pokegoapi

class PokemonController {
	def pokemonService

	static allowedMethods = [nearbyPokemon: "GET"]

	def update() {
		PokeradarUpdateJob.triggerNow()
	}

	def nearbyPokemon() {
		def nearbyPokemon = (params.lat && params.lon) ? pokemonService.getPokemonForLocation(params.lat, params.lon) : []
		[nearbyPokemons: nearbyPokemon.collect{it.pokemonId}]
	}

}
