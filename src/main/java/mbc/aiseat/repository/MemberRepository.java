package mbc.aiseat.repository;

import mbc.aiseat.constant.MemberStatus;
import mbc.aiseat.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByname(String name);

    Member findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByStatusAndUpdateTimeBefore(MemberStatus deleted, LocalDateTime thresholdTime);

    Member findByProviderId(String providerId);
}
