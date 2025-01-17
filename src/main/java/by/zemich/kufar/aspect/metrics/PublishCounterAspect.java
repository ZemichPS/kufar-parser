package by.zemich.kufar.aspect.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Aspect
@Component
public class PublishCounterAspect {
    private final MeterRegistry meterRegistry;

    public PublishCounterAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @AfterReturning(
            value = "by.zemich.kufar.aspect.pointcut.PublisherPointcut.publishMethodWithoutArgs()",
            returning = "result"
    )
    public void countAdvertisementPublishMethodInvocation(JoinPoint joinPoint, boolean result) throws Throwable {
        if (result) {

            String consumerClass = joinPoint.getTarget().getClass().getSimpleName();
            Counter counter = Counter.builder("publisher.advertisement.count")
                    .tag("consumer", consumerClass)
                    .register(meterRegistry);
            counter.increment();
        }
    }
}
