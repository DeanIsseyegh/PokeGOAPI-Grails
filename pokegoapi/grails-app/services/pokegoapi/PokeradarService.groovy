package pokegoapi

import grails.transaction.Transactional
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Value

@Transactional
class PokeradarService {

	@Value('${pokemon.go.data.url}')
	def dataUrl

	def data
	def lastUpdate

	def updateData() {
		def url = new URL(dataUrl)
		def responseCode

		try {
			HttpURLConnection connection = url.openConnection()
			connection.requestMethod = "GET"
			connection.ifModifiedSince = lastUpdate ?: 0
			connection.connect()

			if (connection.responseCode == 200) {
				data = (new JsonSlurper().parseText(url.text)).data
				lastUpdate = System.currentTimeMillis()
				parseData(data)
			}
		} catch (Exception ex) {
			log.error(ex)
		}
	}

	private parseData(data) {
		data.each {
			Location location = Location.findOrCreateByLatAndLon(Utils.round(it.latitude as double) as Double, Utils.round(it.longitude as double) as Double)
			def pokemon = Pokemon.findOrCreateByPokemonId(it.pokemonId)
			pokemon.save()
			if (!location.pokemon?.contains(pokemon)) {
				location.addToPokemon(pokemon)
				location.save()
			}
		}
	}
}
