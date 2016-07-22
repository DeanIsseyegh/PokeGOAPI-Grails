package pokegoapi

class Pokemon {
    String name

    static constraints = {
        name unique
    }
}
