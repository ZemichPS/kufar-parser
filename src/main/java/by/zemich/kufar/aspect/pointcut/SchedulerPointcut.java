package by.zemich.kufar.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class SchedulerPointcut {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledMethods() {
    }
}
