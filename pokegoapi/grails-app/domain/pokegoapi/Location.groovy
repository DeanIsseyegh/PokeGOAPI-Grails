package pokegoapi

class Location {
    Double lat
    Double lon

    static hasMany = [
        pokemon: Pokemon
    ]

    static constraints = {
		lat unique: 'lon'
    }
}
