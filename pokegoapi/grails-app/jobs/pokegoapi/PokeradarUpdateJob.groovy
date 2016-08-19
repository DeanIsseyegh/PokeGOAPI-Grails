package pokegoapi

class PokeradarUpdateJob {
    def pokeradarService

    static triggers = {
      cron name: 'pokeradarTrigger', cronExpression: '0 0 * ? * *'
    }

    def execute() {
		println "Starting Pokeradar Update at ${new Date().time}"
        pokeradarService.updateData()
		println "Pokeradar Update finished at ${new Date().time}"
    }
}