package pokegoapi

import grails.transaction.Transactional

@Transactional
class MapObjectService {

	def getNearbyPokemon(lat, lon) {
		def nearbyPokemons = []

//		def pokemons = mapObjects.getNearbyPokemons()
//		pokemons.each{ nearbyPokemon ->
//			nearbyPokemons << nearbyPokemon.pokemonId.name()
//		}
//
//
//		nearbyPokemons.unique()
//
//		Location location = Location.findOrCreateByLatAndLon(lat as Double, lon as Double)
//		def cachedPokemon = location.pokemon
//
//		def newPokemon = nearbyPokemons - cachedPokemon.collect{it.name}
//		newPokemon.each { name ->
//			location.addToPokemon(Pokemon.findOrCreateByName(name).save())
//			location.save()
//		}
//
//		location.orderedPokemon*.name
	}
}
