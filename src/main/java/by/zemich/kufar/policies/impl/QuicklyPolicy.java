package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.regex.Pattern;

public class QuicklyPolicy implements Policy<Advertisement> {

    private final Pattern DETECT = Pattern.compile("(?i)срочн(о|а|ая|)");


    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String details = advertisement.getDetails();
        return DETECT.matcher(details).find();
    }
}
