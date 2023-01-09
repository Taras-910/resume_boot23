package ua.top.bootjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "rate")
public class Rate  extends AbstractBaseEntity implements Serializable {

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "value_rate")
    private Double value;

    @Column(name = "local_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate localDate;

    public Rate(@NotNull @Size(min = 6, max = 6) String name, @NotNull Double value, LocalDate localDate) {
        this.name = name;
        this.value = value;
        this.localDate = localDate;
    }

    public Rate(Integer id, @NotNull @Size(min = 6, max = 6) String name, @NotNull Double value, LocalDate localDate) {
        this(name, value, localDate);
        this.id = id;
    }

    public Rate(Rate r) {
        this(r.id, r.name, r.value, r.localDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(name, rate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", localDate=" + localDate +
                '}';
    }
}
