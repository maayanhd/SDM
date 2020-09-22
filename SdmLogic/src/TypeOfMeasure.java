public enum TypeOfMeasure {

    QUANTITY("Quantity"),
    WEIGHT("Weight");

    private String meaning;

    TypeOfMeasure(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public String toString() {
        return "TypeOfMeasure{" +
                "meaning='" + meaning + '\'' +
                '}';
    }
}
