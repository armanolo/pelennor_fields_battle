package es.mmm.managerartillery.domain;

import es.mmm.managerartillery.domain.valueobject.ProtocolType;

import java.util.*;


class TargetComparatorBuilder {
    private final List<Comparator<Target>> comparators = new ArrayList<>();

    public Comparator<Target> build() {
        return comparators.stream().reduce(Comparator::thenComparing).orElse(null);
    }

    public void addDistanceComparator(boolean isClose) {
        Comparator<Target> distanceComparator = (obj1, obj2) ->
                isClose ? Double.compare(obj1.calculateDistance(), obj2.calculateDistance()) :
                        Double.compare(obj2.calculateDistance(), obj1.calculateDistance());
        comparators.add(distanceComparator);
    }

    public void addAlliesComparator(boolean isAllies) {
        Comparator<Target> assistComparator = (obj1, obj2) ->
            isAllies ? Boolean.compare(obj2.hasAllies(), obj1.hasAllies()) :
                    Boolean.compare(obj1.hasAllies(), obj2.hasAllies())
        ;
        comparators.add(assistComparator);
    }

    public void addMumakilComparator(boolean prioritizeMech) {
        Comparator<Target> mumakilComparator = (obj1, obj2) ->
            prioritizeMech ? Boolean.compare(obj2.hasMech(), obj1.hasMech()) :
                    Boolean.compare(obj1.hasMech(), obj2.hasMech());
        comparators.add(mumakilComparator);
    }
}

public class Attack {
    private static final int MAXIMUM_DISTANCE= 100;
    private final List<ProtocolType> protocolTypeList;
    private final LinkedList<Target> priorityTarget;

    public Attack(List<ProtocolType> protocolTypeList, List<Target> targetList){
        this.protocolTypeList = protocolTypeList;
        this.priorityTarget = new LinkedList<>(
                targetList.stream().filter( target -> target.calculateDistance() <= MAXIMUM_DISTANCE).toList()
        );
    }

    public Target getTargetToFire(){
        return targetCalculation();
    }

    private Target targetCalculation(){
        Optional<Boolean> isEnemi = Optional.empty();
        Optional<Boolean> isAlllies = Optional.empty();
        Optional<Boolean> isMech = Optional.empty();

        for (ProtocolType protocolType: protocolTypeList) {
            switch (protocolType){
                case CLOSEST_ENEMIES, FURTHEST_ENEMIES:
                    isEnemi = Optional.of(ProtocolType.CLOSEST_ENEMIES == protocolType);
                    break;

                case ASSIST_ALLIES, AVOID_CROSSFIRE:
                    isAlllies = Optional.of(ProtocolType.ASSIST_ALLIES == protocolType);
                    break;

                case PRIORITIZE_MUMAKIL, AVOID_MUMAKIL:
                    isMech = Optional.of(ProtocolType.PRIORITIZE_MUMAKIL == protocolType);
                    break;
                default:
            }
        }

        TargetComparatorBuilder dynamicComparator = new TargetComparatorBuilder();
        isAlllies.ifPresent(dynamicComparator::addAlliesComparator);
        isMech.ifPresent(dynamicComparator::addMumakilComparator);
        isEnemi.ifPresent(dynamicComparator::addDistanceComparator);
        Comparator<Target> comparator = dynamicComparator.build();
        if(comparator != null){
            priorityTarget.sort(comparator);
        }

        return priorityTarget.get(0);
    }
}
