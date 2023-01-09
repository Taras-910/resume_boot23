package ua.top.bootjava.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ua.top.bootjava.HasId;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Data
public abstract class BaseTo implements HasId {
    @Schema(hidden = true)
    protected Integer id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
