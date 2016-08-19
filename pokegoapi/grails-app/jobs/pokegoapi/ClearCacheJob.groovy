package pokegoapi

class ClearCacheJob {
    def pokeGoApiService

    static triggers = {
      simple name: "clearCacheTrigger", startDelay: 30*60000l, repeatInterval: 30*60000l // execute job every 30minutes
    }

    def execute() {
		println "Clearing pokeGoApi cache"
        pokeGoApiService.clearPokemonCache()
    }
}
