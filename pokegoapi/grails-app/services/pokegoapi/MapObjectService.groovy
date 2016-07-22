package pokegoapi

import POGOProtos.Map.Pokemon.NearbyPokemonOuterClass
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

		nearbyPokemons
	}

	MapObjects getMapObject(go, lat, lon) {
		go.latitude = lat as Double
		go.longitude = lon as Double
		Map map = new Map(go)
		map.getMapObjects()
	}
}
