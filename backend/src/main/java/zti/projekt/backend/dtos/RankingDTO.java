package zti.projekt.backend.dtos;

/**
 * DTO zawierajace dane rankingu, ktory chcemy dodac do bazy danych.
 */
public class RankingDTO {
    private String username;
    private String rankingName;

    public RankingDTO() {
        super();
    }

    public RankingDTO(String username, String rankingName) {
        this.username = username;
        this.rankingName = rankingName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }
}
