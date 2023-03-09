package com.d23alex.lab42.check;

import com.d23alex.lab42.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckController {

    private final AuthService authService;
    private final CheckRepository checkRepository;

    private final Checking checking;

    @CrossOrigin
    @GetMapping("/check")
    public ResponseEntity<List<Checking.Check>> checksByCreator(HttpServletRequest req) {
        return ResponseEntity.ok(checkRepository.findAllByCreator(authService.username(req)));
    }

    @CrossOrigin
    @PostMapping("/check")
    public ResponseEntity<List<Checking.Check>> check(@RequestBody CheckRequest checkRequest, HttpServletRequest req) {
        return ResponseEntity.ok(
                checking.checkForAllRadii(
                        checkRequest.x, checkRequest.y, checkRequest.radii, authService.username(req)));
    }

}
