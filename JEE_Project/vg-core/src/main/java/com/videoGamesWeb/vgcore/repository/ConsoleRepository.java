package com.videoGamesWeb.vgcore.repository;


import com.videoGamesWeb.vgcore.entity.Console;
import com.videoGamesWeb.vgcore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsoleRepository extends JpaRepository<Console, Long> {

    @Query("SELECT c.name FROM Console c ORDER BY c.name")
    List<String> getNames();

    @Query("SELECT min(c.price) FROM Console c")
    float getPriceMin();

    @Query("SELECT max(c.price) FROM Console c")
    float getPriceMax();

    @Query("SELECT c FROM Console c " +
            "WHERE lower(c.name) LIKE %:input% " +
            "AND c.price >= :min_price AND c.price <= :max_price " +
            "AND (c.rating IS NULL OR c.rating >= :min_score)")
    List<Product> searchWithText(String input, float min_price, float max_price, float min_score);

    @Query("SELECT c FROM Console c " +
            "WHERE lower(c.name) LIKE %:input% " +
            "AND c.price >= :min_price AND c.price <= :max_price " +
            "AND (c.rating IS NULL OR c.rating >= :min_score) " +
            "AND c.name IN :console_names")
    List<Product> searchWithTextAndNames(String input, float min_price, float max_price, float min_score, List<String> console_names);

    @Query("SELECT c.id FROM Console c WHERE c.name IN :console_names")
    List<Long> searchConsoleIds(List<String> console_names);

    @Query("SELECT c.id FROM Console c WHERE c.name = :console_name")
    long findIdByName(String console_name);
}
