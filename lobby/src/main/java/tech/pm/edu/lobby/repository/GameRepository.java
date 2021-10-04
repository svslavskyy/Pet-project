package tech.pm.edu.lobby.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.pm.edu.lobby.repository.entity.GameEntity;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

  @Query(value = "select g from game g join g.countries co join g.currencies cu " +
    "where co.countryCode = :countryCode and cu.currencyCode = :currencyCode")
  Page<GameEntity> getAvailableLobbyGameIds(
    @Param("currencyCode") String currencyCode,
    @Param("countryCode") String countryCode,
    Pageable pageable);

  @Query(value = "select g from game g join g.countries co join g.currencies cu " +
    "where co.countryCode = :countryCode and cu.currencyCode = :currencyCode and g.lobbyGameId = :lobbyGameId")
  Optional<GameEntity> isExistsForPlayer(
    @Param("lobbyGameId") String lobbyGameId,
    @Param("currencyCode") String currencyCode,
    @Param("countryCode") String countryCode);

  Optional<GameEntity> findByLobbyGameId(String lobbyGameId);


}
