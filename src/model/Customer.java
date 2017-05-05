package model;

import java.util.*;

public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();
    public Customer (String name) {
        this.name = name;
    };
    public void addRental(Rental arg) {
        rentals.add(arg);
    }
    public String getName () {
        return name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;

        String result = "Учет аренды для " + getName() + "\n";
        for (Rental rental : rentals) {
            double thisAmount = 0;

            //определить сумму для каждой строки
            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2)
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3)
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
                    break;
            }
            // добавить очки для активного арендатора
            frequentRenterPoints ++;
            // бонус за аренду новинки на два дня
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
                    rental.getDaysRented() > 1) frequentRenterPoints ++;
            //показать результаты для этой аренды
            result += "\t" + rental.getMovie().getTitle()+ "\t" +
                    String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        //добавить нижний колонтитул
        result += "Сумма задолженности составляет " +
                String.valueOf(totalAmount) + "\n";
        result += "Вы заработали " + String.valueOf(frequentRenterPoints) +
                " очков за активность";
        return result;
    }
}
