package com.example.booking.domain.service;

public interface LockService {
    boolean lock(String key);

    void unlock(String key);
}
