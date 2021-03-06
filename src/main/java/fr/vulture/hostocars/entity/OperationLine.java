package fr.vulture.hostocars.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code operationLines} table.
 */
@Data
@Entity
@ToString
@Table(name = "operationLines")
class OperationLine implements Serializable {

    private static final long serialVersionUID = 7694570052168923210L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "done", nullable = false, columnDefinition = "INTEGER")
    private Boolean done;

    @Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operationId", referencedColumnName = "id")
    private Operation operation;

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 0;
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

        final OperationLine that = (OperationLine) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
