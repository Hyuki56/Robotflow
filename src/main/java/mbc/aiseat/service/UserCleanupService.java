package mbc.aiseat.service;

import mbc.aiseat.constant.MemberStatus;
import mbc.aiseat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserCleanupService {

    @Autowired
    private MemberRepository memberRepository; // 사용자 정보를 관리하는 repository

    // 일정 시간(예: 30초) 이상 지난 'DELETED' 상태의 사용자 삭제
    @Scheduled(fixedRate = 10000000)  // 10000초마다 (1000000ms)
    @Transactional
    public void deleteInactiveUsers() {

        System.out.println("Delete inactive users");

        LocalDateTime thresholdTime = LocalDateTime.now().minusSeconds(30);

        memberRepository.deleteByStatusAndUpdateTimeBefore(MemberStatus.DELETED, thresholdTime);
    }
}
