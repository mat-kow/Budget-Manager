package budget;

public enum Category {
    FOOD, CLOTHES, ENTERTAINMENT, OTHER;

    public String asString() {
        return this.toString().substring(0,1) + this.toString().substring(1).toLowerCase();
    }
}
