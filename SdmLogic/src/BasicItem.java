import jaxb.schema.generated.SDMItem;

import java.util.Objects;

public abstract class BasicItem {

    protected TypeOfMeasure typeToMeasureBy;
    protected String name;
    protected int serialNumber;
    protected static int counter =0;

    public BasicItem(TypeOfMeasure typeToMeasureBy, String name) {
        this.typeToMeasureBy = typeToMeasureBy;
        this.name = name;
        this.serialNumber = ++counter;
    }
    public BasicItem(SDMItem JAXBEItem) throws IllegalArgumentException{
        this.serialNumber = JAXBEItem.getId();
        this.name = JAXBEItem.getName();
        this.typeToMeasureBy = TypeOfMeasure.valueOf(JAXBEItem.getPurchaseCategory().toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicItem)) return false;
        BasicItem basicItem = (BasicItem) o;
        return serialNumber == basicItem.serialNumber &&
                typeToMeasureBy == basicItem.typeToMeasureBy &&
                Objects.equals(name, basicItem.name);
    }

    public TypeOfMeasure getTypeToMeasureBy() {
        return typeToMeasureBy;
    }

    public void setTypeToMeasureBy(TypeOfMeasure typeToMeasureBy) {
        this.typeToMeasureBy = typeToMeasureBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
}
