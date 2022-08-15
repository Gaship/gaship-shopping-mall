package shop.gaship.gashipshoppingmall.repairschedule.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.repairschedule.service.RepairScheduleService;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({DailyRepairInstallSchedule.class})
@Slf4j
class DailyRepairInstallScheduleTest {
    @Autowired
    DailyRepairInstallSchedule dailyRepairInstallSchedule;

    @MockBean
    RepairScheduleService repairScheduleService;

    @Test
    void seatedCronTriggerTest() {
        CronTrigger trigger = new CronTrigger("0 0 0 1/1 * MON-FRI");
        Calendar today = Calendar.getInstance();

        today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE");
        Date yesterday = today.getTime();

        log.info("Yesterday was : " + df.format(yesterday));
        Date nextExecutionTime = trigger.nextExecutionTime(
            new TriggerContext() {
                @Override
                public Date lastScheduledExecutionTime() {
                    return yesterday;
                }

                @Override
                public Date lastActualExecutionTime() {
                    return yesterday;
                }

                @Override
                public Date lastCompletionTime() {
                    return yesterday;
                }
            });

        LocalDateTime nextScheduledPlan = new Timestamp(Objects.requireNonNull(nextExecutionTime).getTime())
            .toLocalDateTime();

        assertThat(nextScheduledPlan.getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY);
        assertThat(nextScheduledPlan.getHour()).isEqualTo(0);
        assertThat(nextScheduledPlan.getMinute()).isEqualTo(0);
        assertThat(nextScheduledPlan.getMinute()).isEqualTo(0);


        // Friday plan
        today.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date yesterday2 = today.getTime();
        log.info("Yesterday was : " + df.format(yesterday2));
        Date nextExecutionTime2 = trigger.nextExecutionTime(
            new TriggerContext() {
                @Override
                public Date lastScheduledExecutionTime() {
                    return yesterday2;
                }

                @Override
                public Date lastActualExecutionTime() {
                    return yesterday2;
                }

                @Override
                public Date lastCompletionTime() {
                    return yesterday2;
                }
            });

        LocalDateTime nextScheduledPlan2 = new Timestamp(Objects.requireNonNull(nextExecutionTime2).getTime())
            .toLocalDateTime();

        assertThat(nextScheduledPlan2.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(nextScheduledPlan2.getHour()).isEqualTo(0);
        assertThat(nextScheduledPlan2.getMinute()).isEqualTo(0);
        assertThat(nextScheduledPlan2.getMinute()).isEqualTo(0);
    }

    @Test
    void fillDailyRepairInstallScheduleTest() {
        willDoNothing().given(repairScheduleService).initializeDailyRepairInstallSchedule();

        dailyRepairInstallSchedule.fillDailyRepairInstallSchedule();

        then(repairScheduleService).should(times(1)).initializeDailyRepairInstallSchedule();
    }
}
