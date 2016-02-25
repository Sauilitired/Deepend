package com.minecade.deepend.bits;

import com.minecade.deepend.object.ByteProvider;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wiki: <a>https://github.com/Minecade/Deepend/wiki/Bit-fields</a>
 *
 * @author Citymonstret
 */
public class BitField<E extends Enum<E> & ByteProvider> {

    private final Map<Byte, E> internalMap;

    /**
     * @param values Values to be used in this BitField
     */
    public BitField(@NonNull E[] values) {
        internalMap = new HashMap<>();

        for (E value : values) {
            internalMap.put(value.getByte(), value);
        }
    }

    /**
     * Extract the values from a constructed field
     *
     * @see #construct(Collection) To construct a field from a collection
     * @see #construct(Enum[]) To construct a field from an array (vararg)
     *
     * @param field BitField to extract from
     *
     * @return Collection containing the extracted objects
     */
    public final Collection<E> extract(int field) {
        if (field == 0) {
            return Collections.emptySet();
        }
        return internalMap.keySet().stream().filter(b -> (field & b) == b).map(internalMap::get).collect(Collectors.toCollection(HashSet::new));
    }


    /**
     * @see #construct(Collection) For super method
     */
    @SafeVarargs
    public final int construct(E... objects) {
        return construct(Arrays.asList(objects));
    }

    /**
     * Construct a BitField from the specified objects
     *
     * @param objects Objects to construct from
     *
     * @return Constructed BitField
     */
    public final int construct(@NonNull Collection<E> objects) {
        Iterator<E> iterator = objects.iterator();
        if (!iterator.hasNext()) {
            return 0;
        }
        byte i = iterator.next().getByte();
        while (iterator.hasNext()) {
            i |= iterator.next().getByte();
        }
        return i;
    }
}