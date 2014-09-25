package com.qalight.javacourse.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NamingThreadFactory implements ThreadFactory {
    private static final AtomicInteger factoryCount = new AtomicInteger();
    private final AtomicInteger threadCount = new AtomicInteger();
    private final ThreadFactory factory;
    private final String prefix;

    @Autowired
    public NamingThreadFactory(@Value("${executor.thread.prefix}") String prefix) {
        this.factory = Executors.defaultThreadFactory();
        this.prefix = prefix;
        factoryCount.incrementAndGet();
    }

    @Override
    public Thread newThread(Runnable r) {
        threadCount.incrementAndGet();
        Thread thread = factory.newThread(r);
        thread.setName(threadName());
        return thread;
    }

    private String threadName() {
        return String.format("%s-%d-%d-thread", prefix, factoryCount.intValue(), threadCount.intValue());
    }
}
