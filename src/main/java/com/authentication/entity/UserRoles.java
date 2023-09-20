package com.authentication.entity;

import com.authentication.utils.constants.DBConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author Artur Tomeyan
 * @date 02.01.2023
 */
@Getter
@Setter
@Entity
@Table(name = DBConstants.tbUserRole)
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.id, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = DBConstants.userId, referencedColumnName = DBConstants.id)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = DBConstants.roleId, referencedColumnName = DBConstants.id)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRoles userRoles = (UserRoles) o;

        return new EqualsBuilder().append(id, userRoles.id).append(user, userRoles.user).append(role, userRoles.role).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(user).append(role).toHashCode();
    }
}