package org.cis1200.twentyfortyeight;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class Square implements Comparable<Square> {

    private final int value;
    private boolean separate = true;
    public static final TreeMap<String, Color> COLORMAP = new TreeMap<>(Map.ofEntries(
            Map.entry("empty", new Color(255, 255, 204)),
            Map.entry("2", new Color(255, 204, 153)),
            Map.entry("4", new Color(255, 204, 204)),
            Map.entry("8", new Color(255, 153, 204)),
            Map.entry("16", new Color(255, 204, 255)),
            Map.entry("32", new Color(204, 153, 255)),
            Map.entry("64", new Color(204, 204, 255)),
            Map.entry("128", new Color(153, 204, 255)),
            Map.entry("256", new Color(204, 255, 255)),
            Map.entry("512", new Color(153, 255, 204)),
            Map.entry("1024", new Color(204, 255, 204)),
            Map.entry("2048", new Color(204, 255, 153))
    ));

    public Square(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Color getColor(int value) {
        Color c = COLORMAP.get("" + value);

        if (c == null) {
            throw new IllegalArgumentException("invalid block value");
        } else {
            return c;
        }
    }

    public boolean getSeparate() {
        return separate;
    }

    public void setSeparate(boolean newStatus) {
        separate = newStatus;
    }

    public Square merge(Square square2) {
        int newValue = 0;
        if (this.getValue() == square2.getValue()) {
            newValue = this.getValue()
                    + square2.getValue();
        } else {
            throw new IllegalArgumentException("values not equal");
        }
        Square mergedSquare = new Square(newValue);
        mergedSquare.setSeparate(false);
        return mergedSquare;

    }

    @Override
    public int compareTo(Square square) {
        if (square == this) {
            return 0;
        }
        return square.getValue() - this.value;
    }


}
