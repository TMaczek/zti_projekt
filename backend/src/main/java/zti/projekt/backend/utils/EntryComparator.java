package zti.projekt.backend.utils;

import zti.projekt.backend.model.Entry;

import java.util.Comparator;

/**
 * Klasa pomocnicza tworząca komparator dla obiektów Entry
 */
public class EntryComparator implements Comparator<Entry> {

    /**
     * Metoda porownujaca dwa obiekty Entry
     * Porównywane są one po polu position
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return -1 gdy o1 wyzej niz o2, 1 gdy odwrotnie
     */
    @Override
    public int compare(Entry o1, Entry o2) {
        return Long.compare(o1.getPosition(), o2.getPosition());
    }
}

