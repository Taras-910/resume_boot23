package ua.top.bootjava.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//@Value
//@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Schema(hidden = true)
public class ResumeTo extends BaseTo implements Serializable, Comparable<ResumeTo> {

    @NotNull
    @Size(min = 2, max = 250)
    String title;

    @NotNull
    @Size(min = 2, max = 512)
    String name;

    String age;

    @NotNull
    @Size(min = 2, max = 512)
    String address;

    @NotNull
    @Range(min = 1, max = 10000000)
    Integer salary;

    @NotNull
    @Size(min = 2, max = 512)
    String workBefore;

    @NotNull
    @Size(min = 4, max = 1000)
    String url;

    @NotNull
    @Size(min = 2, max = 512)
    String skills;

    LocalDate releaseDate;

    String language;

    String level;

    String workplace;

    boolean toVote = false;

    public ResumeTo(Integer id, @NotNull String title, @NotNull String name, String age, @NotNull String address,
                    @NotNull Integer salary, @NotNull String workBefore, @NotNull String url, @NotNull String skills,
                    @Nullable LocalDate releaseDate, @Nullable String language, @Nullable String level,
                    @Nullable String workplace, @Nullable boolean toVote) {
        super(id);
        this.title = title;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
        this.workBefore = workBefore;
        this.url = url;
        this.skills = skills;
        this.releaseDate = releaseDate;
        this.language = language;
        this.level = level;
        this.workplace = workplace;
        this.toVote = toVote;
    }

    public ResumeTo(@NotNull String title, @NotNull String name, String age, @NotNull String address,
                    @NotNull Integer salary, @NotNull String workBefore, @NotNull String url, @NotNull String skills,
                    @Nullable LocalDate releaseDate) {
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

    public ResumeTo(ResumeTo rTo){
        this(rTo.getId(), rTo.getTitle(), rTo.getName(), rTo.getAge(), rTo.getAddress(), rTo.getSalary(),
                rTo.getWorkBefore(), rTo.getUrl(), rTo.getSkills(), rTo.getReleaseDate(), rTo.getLanguage(),
                rTo.getLevel(), rTo.getWorkplace(), rTo.isToVote());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeTo resumeTo = (ResumeTo) o;
        return  Objects.equals(title, resumeTo.title) &&
                Objects.equals(workBefore, resumeTo.workBefore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, workBefore);
    }

    @Override
    public String toString() {
        return "\n        ResumeTo{" +
                "\nid=" + id +
                ", \ntitle='" + title + '\'' +
                ", \nname='" + name + '\'' +
                ", \nage='" + age + '\'' +
                ", \naddress='" + address + '\'' +
                ", \nsalary=" + salary +
                ", \nworkBefore='" + workBefore + '\'' +
                ", \nurl='" + url + '\'' +
                ", \nskills=" + skills +
                ", \nreleaseDate=" + releaseDate +
                ", \nlanguage=" + language +
                ", \nlevel=" + level +
                ", \nworkplace=" + workplace +
                ", \ntoVote=" + toVote +
                '}';
    }

    @Override
    public int compareTo(ResumeTo rTo) {
        String language = null;
        try {
            language = rTo.getLanguage().equals("all") ? "java" : rTo.getLanguage();
        } catch (NullPointerException e) {
            return -1;
        }
        return rTo.getTitle().toLowerCase().matches(".*\\b" + language + "\\b.*")
                && rTo.getTitle().toLowerCase().contains("middle") ? 1 : -1;
    }
}
