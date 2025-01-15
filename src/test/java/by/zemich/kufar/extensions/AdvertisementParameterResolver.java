package by.zemich.kufar.extensions;

import by.zemich.kufar.domain.model.Advertisement;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.ArrayList;

public class AdvertisementParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Advertisement.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        return Advertisement.builder()
                .subject("Female clothes")
                .companyAd(false)
                .fullyFunctional(true)
                .parameters(new ArrayList<>())
                .build();
    }
}
