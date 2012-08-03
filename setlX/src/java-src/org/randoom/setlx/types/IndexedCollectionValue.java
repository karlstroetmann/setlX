package org.randoom.setlx.types;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.utilities.Environment;

public abstract class IndexedCollectionValue extends CollectionValue {

    public final    Value           rnd() {
        if (this.size() < 1) {
            return Om.OM;
        } else {
            try {
                return getMember(Environment.getRandomInt(this.size()) +1);
            } catch (final SetlException se) {
                return Om.OM;
            }
        }
    }

    public abstract Value           getMember(final int index) throws SetlException;

}
