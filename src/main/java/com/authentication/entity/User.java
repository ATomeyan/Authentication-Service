package com.authentication.entity;

import com.authentication.utils.constants.DBConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = DBConstants.tbUser)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.id, nullable = false)
    private Long id;

    @Column(name = DBConstants.firstName, nullable = false)
    private String firstName;

    @Column(name = DBConstants.lastname, nullable = false)
    private String lastName;

    @Column(name = DBConstants.username, nullable = false)
    private String username;

    @Column(name = DBConstants.email, unique = true, nullable = false)
    private String email;

    @Column(name = DBConstants.password, nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean emailVerified;

    @OneToMany(mappedBy = DBConstants.user, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRoles> userRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder().append(enabled, user.enabled).append(emailVerified, user.emailVerified)
                .append(id, user.id).append(firstName, user.firstName).append(lastName, user.lastName)
                .append(username, user.username).append(email, user.email).append(password, user.password)
                .append(userRoles, user.userRoles).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(firstName)
                .append(lastName).append(username).append(email).append(password).append(enabled)
                .append(emailVerified).append(userRoles).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("username", username)
                .append("email", email)
                .append("password", password)
                .append("isEnabled", enabled)
                .append("emailVerified", emailVerified)
                .append("userRoles", userRoles)
                .toString();
    }
}