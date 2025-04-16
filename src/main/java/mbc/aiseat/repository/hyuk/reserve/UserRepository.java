package mbc.aiseat.repository.hyuk.reserve;

import mbc.aiseat.entity.hyuk.reserve.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
