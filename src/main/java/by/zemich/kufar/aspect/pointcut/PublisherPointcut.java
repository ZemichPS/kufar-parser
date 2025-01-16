package by.zemich.kufar.aspect.pointcut;

import by.zemich.kufar.domain.model.Advertisement;
import org.aspectj.lang.annotation.Pointcut;

public class PublisherPointcut {

    @Pointcut("execution(boolean by.zemich.kufar.application.service.api.AdvertisementPublisher.publish(..)) && args(advertisement)")
    public void publishMethod(Advertisement advertisement) {}

    @Pointcut("execution(boolean by.zemich.kufar.application.service.api.AdvertisementPublisher.publish(..))")
    public void publishMethodWithoutArgs() {}
}
