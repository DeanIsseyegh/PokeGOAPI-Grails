package pokegoapi

class Pokemon {
    int pokemonId

    static constraints = {
        pokemonId unique: true
    }
}
