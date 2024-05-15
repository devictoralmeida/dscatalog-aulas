package com.devsuperior.dscatalog.projections;

// Para deixar a ordenação do Utils mais genérica, vamos criar essa projection, pois toda ordenação será feita pelo id.
// O generic <E>, significa que a ordenação será feita independente do tipo do id (Long, UUID, etc...).
// Agora as classes que precisarem de ordenação, devem implementar essa interface.
public interface IdProjection<E> {
    E getId();
}
