package by.zemich.kufar.aspect.pointcut;


import org.aspectj.lang.annotation.Pointcut;

public class ControllerPointcut {

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController)")
    public void allRestControllers() {

    }
}
