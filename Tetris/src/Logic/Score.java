
package Logic;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {
    
    private Integer points = 0;
    private String user = "";

    public Score(String user, Integer points) {
        this.user = user;
        this.points = points;
    }
    
    @Override
    public int compareTo(Score u) {
        if (this.points == null || u.points == null) {
           return 0;
        }
        return this.points.compareTo(u.points);
    }

    public Integer getPoints() {
        return points;
    }

    public String getUser() {
        return user;
    }
    
    
    
}
