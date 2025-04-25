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

    // 1년 이상 지난 'DELETED' 상태의 사용자 삭제
    @Scheduled(fixedRate = 8640000)  // 하루마다
    @Transactional
    public void deleteInactiveUsers() {

        System.out.println("Delete inactive users");

        LocalDateTime thresholdTime = LocalDateTime.now().minusYears(1);

        memberRepository.deleteByStatusAndUpdateTimeBefore(MemberStatus.DELETED, thresholdTime);
    }
}
