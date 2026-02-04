package utils;

public class TestDataOrder {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metro;
    public final String phone;
    public final String date;
    public final String rentPeriod;
    public final boolean colorBlack;
    public final String comment;

    public TestDataOrder(String firstName, String lastName, String address, String metro, String phone,
                         String date, String rentPeriod, boolean colorBlack, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentPeriod = rentPeriod;
        this.colorBlack = colorBlack;
        this.comment = comment;
    }
}

