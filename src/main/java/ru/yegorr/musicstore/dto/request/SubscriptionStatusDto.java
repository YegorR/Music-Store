package ru.yegorr.musicstore.dto.request;

public class SubscriptionStatusDto {
    private boolean subscription;

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }
}
