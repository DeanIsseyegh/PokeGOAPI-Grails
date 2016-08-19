package pokegoapi

import grails.transaction.Transactional

@Transactional
class PokemonService {
    def getPokemonForLocation(lat, lon) {
		def location = Location.findByLatAndLon(Utils.round(lat as double) as Double, Utils.round(lon as double) as Double)

		location?.pokemon ?: []
    }
}
