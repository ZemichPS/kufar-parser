package by.zemich.kufar.aspect.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PublishCounterAspect {
    private final MeterRegistry meterRegistry;

    public PublishCounterAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @AfterReturning(
            value = "by.zemich.kufar.aspect.pointcut.PublisherPointcut.publishMethodWithoutArgs()"
    )
    public void countAdvertisementPublishMethodInvocation(JoinPoint joinPoint) throws Throwable {

        String consumerClass = joinPoint.getTarget().getClass().getSimpleName();

        Counter counter = Counter.builder("publisher.advertisement.count")
                .tag("consumer", consumerClass)
                .register(meterRegistry);
        counter.increment();
    }
}
