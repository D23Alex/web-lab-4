package com.d23alex.lab42.check;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

@Component
@RequiredArgsConstructor
public class Checking {

    private final CheckRepository checkRepository;

    @Table(name="checks")
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Check {
        Double x;
        Double y;
        Double r;
        boolean contains;
        Date performed;
        String creator;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
    }

    public static boolean belongsArea(Double x, Double y, Double r) {
        final var belongsInTopRight = x >= 0 && y >= 0 && x < r && y < r;
        final var belongsInTopLeft = x <= 0 && y >= 0 && abs(x) + abs(y) < r / 2;
        final var belongsInBottomLeft = x <= 0 && y <= 0 && pow(abs(x), 2) + pow(abs(y), 2) < pow(r, 2);
        final var belongsInBottomRight = false;
        return belongsInTopLeft || belongsInBottomLeft || belongsInBottomRight || belongsInTopRight;
    }

    public static Check check(Double x, Double y, Double r, String creator) {
        return new Check(x, y, r, belongsArea(x, y, r), new Date(1), creator, -1L);
    }

    public List<Check> checkForAllRadii(Double x, Double y, List<Double> radii, String creator) {
        final var checks = radii.stream().map((radius) -> check(x, y, radius, creator)).toList();
        checkRepository.saveAll(checks);
        return checks;
    }
}
