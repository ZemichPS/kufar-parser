package by.zemich.kufar.model.criterias;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Parameter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class LocationCriteria implements Criteria {

    private final List<Location> locations = new ArrayList<>();

    public LocationCriteria(Location location) {
        locations.add(location);
    }

    public LocationCriteria() {
    }

    public void add(Location location) {
        locations.add(location);
    }

    public Location getByRegion(String region) {
        return locations.stream()
                .filter(l -> l.getRegion().equals(region))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        Location adLocation = new Location();
        List<Parameter> parameters = advertisement.getParameters();
        String area = getAreaFromParameters(parameters);
        String region = getRegionFromParameters(parameters);
        adLocation.setRegion(region);
        adLocation.setArea(area);
        return locations.contains(adLocation);
    }

    private String getRegionFromParameters(List<Parameter> parameters) {
        return parameters.stream()
                .filter(param -> "region".equalsIgnoreCase(param.getIdentity()))
                .map(Parameter::getValue)
                .findFirst().orElse("");

    }

    private String getAreaFromParameters(List<Parameter> parameters) {
        return parameters.stream()
                .filter(param -> "area".equalsIgnoreCase(param.getIdentity()))
                .map(Parameter::getValue)
                .findFirst().orElse("");
    }

    @Data
    public static class Location {
        private String region;
        private String area;
    }

}
