package com.authentication.entity;

import com.authentication.utils.constants.DBConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author Artur Tomeyan
 * @date 02.01.2023
 */
@Getter
@Setter
@Entity
@Table(name = DBConstants.tbUserToken)
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.id, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = DBConstants.userId, referencedColumnName = DBConstants.id)
    private User user;

    @OneToOne
    @JoinColumn(name = DBConstants.tokenId, referencedColumnName = DBConstants.id)
    private ConfirmationToken confirmationToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserToken userToken = (UserToken) o;

        return new EqualsBuilder().append(id, userToken.id).append(user, userToken.user)
                .append(confirmationToken, userToken.confirmationToken).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(user)
                .append(confirmationToken).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("user", user)
                .append("confirmationToken", confirmationToken)
                .toString();
    }
}