package pokegoapi

class PokemonController {
	def pokeradarService
	def pokemonService

	static allowedMethods = [nearbyPokemon: "GET"]

	def updateData() {
		println "Starting at ${new Date().time}"
		pokeradarService.updateData()
		println "Finished at ${new Date().time}"
	}

	def nearbyPokemon() {
		def nearbyPokemon = (params.lat && params.lon) ? pokemonService.getPokemonForLocation(params.lat, params.lon) : []
		[nearbyPokemons: nearbyPokemon.collect{it.pokemonId}]
	}

}
