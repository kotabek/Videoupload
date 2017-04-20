package com.video.domains;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kotabek on 4/20/17.
 */
@MappedSuperclass
public abstract class SimpleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PreUpdate
    public void preUpdate() {
    }

    @PrePersist
    public void prePersist() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleEntity)) {
            return false;
        }
        if (getId() == null) {
            return false;
        }

        SimpleEntity unObject = (SimpleEntity) o;

        String table1 = this.getEntityKey();
        String table2 = unObject.getEntityKey();
        return !(table1 == null || !table1.equals(table2)) && getId().equals(unObject.getId());
    }

    /**
     * Use this method
     * Instead of checking #SimpleEntity.getId()==null everywhere
     *
     * @return true if Id has not been assigned yet.
     */
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + getEntityKey().hashCode();
        return result;
    }

    public String toString() {
        return "[objectID=" + getId() + "]";
    }


    public abstract String getEntityKey();
}
