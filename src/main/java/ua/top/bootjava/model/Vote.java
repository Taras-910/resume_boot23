package ua.top.bootjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "resume_id"}, name = "votes_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "local_date", nullable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate localDate;

    @Column(name = "resume_id", nullable = false)
    @NotNull
    private Integer resumeId;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    public Vote(Integer id, @NotNull LocalDate localDate, @NotNull Integer resumeId, @NotNull Integer userId) {
        super(id);
        this.localDate = localDate;
        this.resumeId = resumeId;
        this.userId = userId;
    }

    public Vote(Vote v) {
        this(v.id, v.localDate, v.resumeId, v.userId);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", localDate=" + localDate +
                ", resumeId=" + resumeId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;

        return userId.equals(vote.getUserId()) && resumeId.equals(vote.getResumeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(resumeId, userId);
    }
}
