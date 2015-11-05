package twitter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TweetEntity {

    private TweetEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    @Column
    private String message;

    @Column
    private Date postedTime;

    public TweetEntity(Long id, UserEntity user, String message, Date postedTime) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Date getPostedTime() {
        return postedTime;
    }
}
