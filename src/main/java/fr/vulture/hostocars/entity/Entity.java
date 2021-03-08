package fr.vulture.hostocars.entity;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.ToString;

/**
 * Abstract representation of a DTO.
 */
@Data
@ToString
@MappedSuperclass
abstract class Entity implements Serializable {

    private static final long serialVersionUID = 6385609581190828938L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hash(this.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (isNull(obj) || this.getClass() != obj.getClass()) {
            return false;
        }

        final Entity that = (Entity) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}