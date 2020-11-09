package edu.uga.cs.quizzer;

/**
 * A representation of a State.
 */
public class State {

    private String name;
    private String capital;
    private String city1;
    private String city2;

    /**
     * Constructor for state
     * @param name state name
     */
    public State(String name) {
        this.name = name;
    }

    /**
     * Constructor for state
     * @param name state name
     * @param capital state capital
     * @param city1 a non-capital city in the state
     * @param city2 a non-capital city in the state
     */
    public State(String name, String capital, String city1, String city2) {
        this.name = name;
        this.capital = capital;
        this.city1 = city1;
        this.city2 = city2;
    }

    //getter
    public String getName() {
        return name;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    //getter
    public String getCapital() {
        return capital;
    }

    //setter
    public void setCapital(String capital) {
        this.capital = capital;
    }

    //getter
    public String getCity1() {
        return city1;
    }

    //setter
    public void setCity1(String city1) {
        this.city1 = city1;
    }

    //getter
    public String getCity2() {
        return city2;
    }

    //setter
    public void setCity2(String city2) {
        this.city2 = city2;
    }
}
