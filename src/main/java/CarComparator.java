import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

    @Override
    public int compare(Car car1, Car car2) {
       if(car1.getLaneNumber()=="Third" && car2.getLaneNumber()=="Second")
           return 1;
        if(car1.getLaneNumber()=="Second" && car2.getLaneNumber()=="Third" )
            return -1;
        if(car2.getLaneNumber()=="Second" && car1.getLaneNumber()=="Second" )
            return 0;
        if(car2.getLaneNumber()=="Third" && car1.getLaneNumber()=="Third" )
            return 0;

        return 0;
    }
}


