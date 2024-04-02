package com.authentication.entity;

import com.authentication.utils.constants.DBConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.util.Set;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
@Getter
@Setter
@Entity
@Table(name = DBConstants.tbRole)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.id, nullable = false)
    private Long id;

    @Column(name = DBConstants.name, nullable = false)
    private String name;

    @OneToMany(mappedBy = DBConstants.role, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRoles> userRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return new EqualsBuilder().append(id, role.id).append(name, role.name).append(userRoles, role.userRoles).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(userRoles).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("userRoles", userRoles)
                .toString();
    }
}