package ru.yegorr.musicstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yegorr.musicstore.dto.request.SubscriptionStatusDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.service.SubscriptionService;

@RestController
public class SubscriptionController {
    private final UserChecker userChecker;

    private final SubscriptionService subscriptionService;

    public SubscriptionController(UserChecker userChecker, SubscriptionService subscriptionService) {
        this.userChecker = userChecker;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("musician/{musicianId}/subscription")
    public ResponseEntity<?> getSubscriptionStatus(@PathVariable Long musicianId,
                                                   @RequestHeader("Authorization") String token)
            throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        SubscriptionStatusDto result = subscriptionService.getSubscriptionStatus(userId, musicianId);

        return ResponseBuilder.getBuilder().body(result).code(200).getResponseEntity();
    }

    @PutMapping("musician/{musicianId}/subscription")
    public ResponseEntity<?> setSubscriptionStatus(@PathVariable Long musicianId,
                                                   @RequestBody SubscriptionStatusDto statusDto,
                                                   @RequestHeader("Authorization") String token)
            throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);

        SubscriptionStatusDto result = subscriptionService.changeSubscriptionStatus(userId, musicianId, statusDto);

        return ResponseBuilder.getBuilder().body(result).code(200).getResponseEntity();
    }

    @GetMapping("/subscription")
    public ResponseEntity<?> getSubscriptionStatus(@RequestParam boolean brief,
                                                   @RequestHeader("Authorization") String token)
            throws ApplicationException {
        Long userId = userChecker.getUserIdOrThrow(token);
        Object result;
        if (brief) {
            result = subscriptionService.getReleasesNumber(userId);
        } else {
            result = subscriptionService.getReleases(userId);
        }

        return ResponseBuilder.getBuilder().body(result).code(200).getResponseEntity();
    }
}
