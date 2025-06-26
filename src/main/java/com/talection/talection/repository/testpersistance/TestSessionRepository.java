package com.talection.talection.repository.testpersistance;

import com.talection.talection.model.testpersistance.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    public TestSession findTestSessionByUserId(Long userId);

    public Collection<TestSession> findAllByUserId(Long userId);
}
