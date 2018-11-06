import java.io.Serializable;

class Player implements Serializable {

    private String name;
    private String symbol;
    private boolean ia;

    Player() {
        this.name = null;
        this.symbol = null;
    }

    Player setName(String name) {
        this.name = name;

        return this;
    }

    String getName() {
        return this.name;
    }

    Player setSymbol(String symbol) {
        this.symbol = symbol;

        return this;
    }

    String getSymbol() {
        return this.symbol;
    }

    public boolean isIa() {
        return ia;
    }

    public void setIa(boolean ia) {
        this.ia = ia;
    }
}
