package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

import java.util.regex.Pattern;

public class FastSalesPolicy implements Policy<Advertisement> {

    private final Pattern DETECT = Pattern.compile(
            "(?i)(срочн(о|а|ая|))[^\\w\\s]*\\s*(пр[ао]да(жа|м|[её]тся))?"
    );


    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String details = advertisement.getDetails();
        return DETECT.matcher(details).find();
    }
}
