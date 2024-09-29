package cl.template.integrations.pokemon;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokemonService implements IPokemonService {
    private final WebClient webClient;

    public PokemonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://pokeapi.co/api/v2/pokemon/").build();
    }

    @Override
    public Mono<Pokemon> getPokemonByNameOrId(String nameOrId){
        try {
            return webClient.get()
                    .uri("{nameOrId}", nameOrId)
                    .retrieve()
                    .bodyToMono(Pokemon.class);
        } catch (Exception e) {
            return null;
        }
    }
}
