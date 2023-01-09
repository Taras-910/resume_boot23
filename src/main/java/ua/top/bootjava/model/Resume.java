package ua.top.bootjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "resume", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "work_before"}, name = "resume_idx")})
public class Resume extends AbstractBaseEntity {

    @NotNull
    @Size(min = 2, max = 256)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(min = 2, max = 256)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private String age;

    @NotNull
    @Size(min = 2, max = 256)
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "salary")
    private Integer salary;

    @NotNull
    @Column(name = "work_before")
    private String workBefore;

    @Column(name = "url")
    private String url;

    @NotNull
    @Size(min = 1, max = 100000)
    @Column(name = "skills")
    private String skills;

    @Column(name = "release_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @Schema(hidden = true)
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "freshen_id", nullable = false)
    @JsonBackReference(value = "freshen-movement") //https://stackoverflow.com/questions/20119142/jackson-multiple-back-reference-properties-with-name-defaultreference
    private Freshen freshen;

    public Resume(Integer id, @NotNull String title, @NotNull String name, @Nullable String age, @NotNull String address,
                  @NotNull Integer salary, @NotNull String workBefore, String url,
                  @NotNull String skills, LocalDate releaseDate, Freshen freshen) {
        this(id, title, name, age, address, salary, workBefore, url, skills, releaseDate);
        this.freshen = freshen;
    }

    public Resume(Integer id, @NotNull String title, @NotNull String name, String age, @NotNull String address,
                  @NotNull Integer salary, @NotNull String workBefore, String url, @NotNull String skills, LocalDate releaseDate) {
        this(title, name, age, address, salary, workBefore, url, skills, releaseDate);
        this.id = id;
    }

    public Resume(@NotNull String title, @NotNull String name, String age, @NotNull String address, @NotNull Integer salary,
                  @NotNull String workBefore, String url, @NotNull String skills, LocalDate releaseDate) {
        this.title = title;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
        this.workBefore = workBefore;
        this.url = url;
        this.skills = skills;
        this.releaseDate = releaseDate;
    }

    public Resume(Resume r) {
        this(r.getId(), r.getTitle(), r.getName(), r.getAge(), r.getAddress(), r.getSalary(),
                r.getWorkBefore(), r.getUrl(), r.getSkills(), r.getReleaseDate());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(title, resume.title) &&
                Objects.equals(workBefore, resume.workBefore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, workBefore);
    }

    @Override
    public String toString() {
        return "\n\nResume{" +
                "\ntitle='" + title + '\'' +
                ", \nname=" + name +
                ", \nage=" + age +
                ", \naddress=" + address +
                ", \nsalary='" + salary + '\'' +
                ", \nworkBefore='" + workBefore + '\'' +
                ", \nurl='" + url + '\'' +
                ", \nskills=" + skills +
                ", \nreleaseDate=" + releaseDate +
                ", \nid=" + id +
                ", \nfreshen=" + freshen +
                '}';
    }
}
