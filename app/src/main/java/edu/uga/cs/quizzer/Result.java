package edu.uga.cs.quizzer;

/**
 * Class for quiz result.
 */
public class Result {
    private String date;
    private int id;
    private int score;

    //basic constructor
    public Result() {

    }

    //constructor
    public Result(String date, int id, int score) {
        this.date = date;
        this.id = id;
        this.score = score;
    }

    //toString method
    @Override
    public String toString() {
        return "Result{" +
                "date='" + date + '\'' +
                ", Quiz id=" + id +
                ", score=" + score +
                '}';
    }

    //getter
    public String getDate() {
        return date;
    }

    //setter
    public void setDate(String date) {
        this.date = date;
    }

    //getter
    public int getId() {
        return id;
    }

    //setter
    public void setId(int id) {
        this.id = id;
    }

    //getter
    public int getScore() {
        return score;
    }

    //setter
    public void setScore(int score) {
        this.score = score;
    }
}
