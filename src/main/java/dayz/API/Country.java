package dayz.API;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Country {

    List<flagsName> flagsNames;

    public Country (List<flagsName> flagsNames) {
        this.flagsNames = flagsNames;
    }

    static class flagsName {
        Flags flags;
        Name name;
    }
    @Override
    public String toString() {
        return "" + flagsNames.get(1);
    }
}
