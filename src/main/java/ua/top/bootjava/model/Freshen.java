package ua.top.bootjava.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springframework.util.StringUtils.hasText;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "freshen")
public class Freshen extends AbstractBaseEntity implements Serializable {

    @Column(name = "recorded_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime recordedDate;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "language")
    private String language;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "level")
    private String level;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "workplace")
    private String workplace;

    @Column(name = "goal")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "freshen_goal", joinColumns = @JoinColumn(name = "freshen_id"))
//    @Fetch(FetchMode.SUBSELECT)
    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Goal> goals;

    @Column(name = "user_id")
    private Integer userId;

    @Schema(hidden = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freshen")
    @JsonManagedReference(value = "freshen-movement")  //https://stackoverflow.com/questions/20119142/jackson-multiple-back-reference-properties-with-name-defaultreference
    private List<Resume> resumes;

    public Freshen(Integer id, LocalDateTime recordedDate, String language, String level, String workplace, Collection<Goal> goals, Integer userId) {
        super(id);
        this.recordedDate = recordedDate;
        this.language = hasText(language) ? language.toLowerCase() : "all";
        this.level = hasText(level) ? level.toLowerCase() : "all";
        this.workplace = hasText(workplace) ? workplace.toLowerCase() : "all";
        setGoals((Set<Goal>) goals);
        this.userId = userId;
    }

    public Freshen(Freshen f) {
        this(f.getId(), f.recordedDate, f.language, f.level, f.workplace, f.getGoals(), f.userId);
    }

    @Override
    public String toString() {
        return "Freshen{" +
                "id=" + id +
                ", recordedDate=" + recordedDate +
                ", language='" + language + '\'' +
                ", level='" + level + '\'' +
                ", workplace='" + workplace + '\'' +
                ", goals=" + goals +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freshen freshen = (Freshen) o;

        return language.equalsIgnoreCase(freshen.language)
                && level.equalsIgnoreCase(freshen.level)
                && workplace.equalsIgnoreCase(freshen.workplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language.toLowerCase(), level.toLowerCase(), workplace.toLowerCase());
    }
}
