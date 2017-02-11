package com.zburzhynski.jsender.impl.util;

import com.zburzhynski.jsender.api.domain.ISortable;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Contains common methods for working with object implementing ISortable interface.
 * <p/>
 * Date: 10.10.13
 *
 * @author Vladimir Zburzhynski
 */
public final class SortableUtils {

    private SortableUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets next sort order number.
     *
     * @param elements elements
     * @param <T>      type
     * @return natural sort order
     */
    public static synchronized <T extends ISortable> Long nextSortOrder(SortedSet<T> elements) {
        if (CollectionUtils.isEmpty(elements)) {
            return 1L;
        }
        return elements.last().getSortOrder() + 1;
    }

    /**
     * Up element by sort order.
     *
     * @param elements elements
     * @param element  element to up
     * @param <T>      T type
     */
    public static <T extends ISortable> void upBySortOrder(Set<T> elements, T element) {
        List<T> list = new ArrayList<>(elements);
        int foundIndex = findIndex(list, element);
        if (foundIndex != 0) {
            T current = list.get(foundIndex);
            T previous = list.get(foundIndex - 1);
            swapElements(elements, current, previous);
        }
    }

    /**
     * Down element by sort order.
     *
     * @param elements elements
     * @param element  element to down
     * @param <T>      T type
     */
    public static <T extends ISortable> void downBySortOrder(Set<T> elements, T element) {
        List<T> list = new ArrayList<>(elements);
        int foundIndex = findIndex(list, element);
        if (foundIndex != elements.size() - 1) {
            T current = list.get(foundIndex);
            T next = list.get(foundIndex + 1);
            swapElements(elements, current, next);
        }
    }

    /**
     * Swap elements between them.
     *
     * @param elements elements
     * @param first    first element
     * @param second   second element
     * @param <T>      T type
     */
    private static <T extends ISortable> void swapElements(Set<T> elements, T first, T second) {
        Long firstSortOrder = first.getSortOrder();
        Long secondSortOrder = second.getSortOrder();
        second.setSortOrder(firstSortOrder);
        first.setSortOrder(secondSortOrder);
        elements.remove(first);
        elements.remove(second);
        elements.add(first);
        elements.add(second);
    }

    /**
     * Find index of element in collection.
     *
     * @param elements elements
     * @param element  element
     * @param <T>      T type
     * @return element index
     */
    private static <T extends ISortable> int findIndex(List<T> elements, T element) {
        int foundIndex = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getSortOrder() == element.getSortOrder()) {
                foundIndex = i;
            }
        }
        return foundIndex;
    }

}
