package shop.gaship.gashipshoppingmall.repairschedule.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.repairschedule.service.RepairScheduleService;

/**
 * 정해진 주기로 각 지역에 최대 배송건수를 초기화 시키는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class DailyRepairInstallSchedule {
    private final RepairScheduleService repairScheduleService;

    /**
     * 매일 자정, 평일마다 각 지역의 최대 배송건수를 초기화 시킵니다.
     */
    @Scheduled(cron = "0 0 * * 1-5 ")
    public void fillDailyRepairInstallSchedule() {
        repairScheduleService.initializeDailyRepairInstallSchedule();
    }
}
