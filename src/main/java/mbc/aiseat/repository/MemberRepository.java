package mbc.aiseat.repository;

import mbc.aiseat.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByname(String name);

}
