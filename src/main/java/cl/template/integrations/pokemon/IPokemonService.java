package cl.template.integrations.pokemon;

import reactor.core.publisher.Mono;

public interface IPokemonService {
    Mono<Pokemon> getPokemonByNameOrId(String nameOrId);
}
