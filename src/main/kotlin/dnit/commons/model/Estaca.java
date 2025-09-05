package dnit.commons.model;

import java.util.List;

public record Estaca<T>(
    Double inicio,
    Double fim,
    List<T> items
) {
}