package com.example.booking.infrastructure.service;

import com.example.booking.domain.service.LockService;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

@Service
public class RedisLockService implements LockService {
    private final LockRegistry lockRegistry;

    public RedisLockService(LockRegistry redisLockRegistry) {
        this.lockRegistry = redisLockRegistry;
    }

    @Override
    public boolean lock(String key) {
        Lock lock = lockRegistry.obtain(key);

        return lock.tryLock();
    }

    @Override
    public void unlock(String key) {
        Lock lock = lockRegistry.obtain(key);

        lock.unlock();
    }
}
