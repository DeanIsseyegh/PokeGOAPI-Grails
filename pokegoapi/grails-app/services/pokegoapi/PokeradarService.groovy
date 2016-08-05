package pokegoapi

import grails.transaction.Transactional
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Value

@Transactional
class PokeradarService {

	@Value('${pokemon.go.data.url}')
	static dataUrl

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
			responseCode = connection.responseCode

			if (connection.responseCode == 200) {
				data = new JsonSlurper().parseText(url.text)
				lastUpdate = System.currentTimeMillis()
				parseData(data)
			}
		} catch (Exception ex) {
			log.error(ex)
		}
	}

	private parseData(data) {
		//TODO
	}
}
