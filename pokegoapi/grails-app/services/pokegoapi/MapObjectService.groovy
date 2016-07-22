package pokegoapi

import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.api.map.MapObjects
import grails.transaction.Transactional

@Transactional
class MapObjectService {

	def getNearbyPokemon(PokemonGo go, lat, lon) {
		def nearbyPokemons = []

		MapObjects mapObjects = getMapObject(go, lat, lon)
		def pokemons = mapObjects.getNearbyPokemons()
		pokemons.each{ nearbyPokemon ->
			nearbyPokemons << nearbyPokemon.pokemonId.name()
		}


		nearbyPokemons.unique()

		Location location = Location.findOrCreateByLatAndLon(lat as Double, lon as Double)
		def cachedPokemon = location.pokemon

		def newPokemon = nearbyPokemons - cachedPokemon.collect{it.name}
		newPokemon.each { name ->
			location.addToPokemon(Pokemon.findOrCreateByName(name).save())
			location.save()
		}

		location.orderedPokemon*.name
	}

	MapObjects getMapObject(go, lat, lon) {
		go.latitude = lat as Double
		go.longitude = lon as Double
		Map map = new Map(go)
		map.getMapObjects()
	}
}
