package pokegoapi

import POGOProtos.Enums.PokemonIdOuterClass
import com.pokegoapi.api.PokemonGo
import com.pokegoapi.api.map.Map
import com.pokegoapi.api.map.MapObjects
import com.pokegoapi.api.map.pokemon.NearbyPokemon
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges([Map, MapObjectService])
@TestFor(MapObjectService)
class MapObjectServiceSpec extends Specification {

    def setup() {
    }

	def "returns a list of nearby pokemans" () {
		given:
			PokemonGo go = Mock(PokemonGo)
		and:
			def (lat, lon) = ["123", "456"]
		and:
			MapObjects mockedMapObjects = Mock(MapObjects)
		and:
			NearbyPokemon pokemon = Mock(NearbyPokemon)
			pokemon.pokemonId >> PokemonIdOuterClass.PokemonId.ABRA
			mockedMapObjects.getNearbyPokemons() >> [pokemon]
		and:
			Map.metaClass.getMapObjects = { mockedMapObjects }

		when:
			def nearbyPokemon = service.getNearbyPokemon(go, lat, lon)
		then:
			nearbyPokemon == [PokemonIdOuterClass.PokemonId.ABRA.name()]
	}

    void "returns map objects"() {
		given:
			PokemonGo go = Mock(PokemonGo)
		and:
			def (lat, lon) = ["123", "456"]
		and:
			MapObjects mockedMapObjects = Mock(MapObjects)
			Map.metaClass.getMapObjects = { mockedMapObjects }

        when:
			def mapObjects = service.getMapObject(go, lat, lon)
		then:
			mapObjects == mockedMapObjects

    }
}
