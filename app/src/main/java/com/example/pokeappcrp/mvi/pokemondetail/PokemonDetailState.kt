import com.example.pokeappcrp.domain.model.Pokemon
import com.example.pokeappcrp.data.remote.model.PokemonSpeciesResponse

sealed class PokemonDetailState {
    object Idle : PokemonDetailState()
    object Loading : PokemonDetailState()
    data class Success(
        val detail: Pokemon,
        val species: PokemonSpeciesResponse
    ) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}
