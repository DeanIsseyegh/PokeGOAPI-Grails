package pokegoapi

class PokemonController {
	def pokeradarService
	def pokemonService

	static allowedMethods = [nearbyPokemon: "GET"]

	def nearbyPokemon() {
		def nearbyPokemon = (params.lat && params.lon) ? pokemonService.getPokemonForLocation(params.lat, params.lon) : []
		[nearbyPokemons: nearbyPokemon.collect{it.pokemonId}]
	}

}
