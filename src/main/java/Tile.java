import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

class Tile implements Serializable {

    private static BufferedReader in;

    private Integer x;
    private Integer y;
    private String symbol;

    Tile() {
        in = new BufferedReader(new InputStreamReader(System.in));

        this.x = null;
        this.y = null;
        this.symbol = null;
    }

    Integer getX() {
        return this.x;
    }

    void setX(Integer x) {
        this.x = x;
    }

    Integer getY() {
        return this.y;
    }

    void setY(Integer y) {
        this.y = y;
    }

    Integer readCoordinate() {
        try {
            return Integer.parseInt(in.readLine()) - 1;
        } catch(Exception e) {
            return null;
        }
    }

    String getSymbol() {
        return this.symbol;
    }

    void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
