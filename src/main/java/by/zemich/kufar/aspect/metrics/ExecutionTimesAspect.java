package by.zemich.kufar.aspect.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimesAspect {
    private final MeterRegistry meterRegistry;


    public ExecutionTimesAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("by.zemich.kufar.aspect.pointcut.SchedulerPointcut.scheduledMethods()")
    public void scheduledMethodTimeTaken(ProceedingJoinPoint proceedingJoinPoint) throws Exception {

        String methodName = proceedingJoinPoint.getSignature().getName();

        Timer timer = Timer.builder("scheduled.execution.duration")
                .description("Time taken for execute scheduled method")
                .tag("method", methodName)
                .register(meterRegistry);

        timer.record(() -> {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}
