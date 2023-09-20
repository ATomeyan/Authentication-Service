package com.authentication.entity;

import com.authentication.utils.constants.DBConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Artur Tomeyan
 * @date 02.01.2023
 */
@Getter
@Setter
@Entity
@Table(name = DBConstants.tbConfirmationToken)
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.id, nullable = false)
    private Long id;

    @Column(name = DBConstants.tokenName, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate;

    @OneToOne(mappedBy = DBConstants.mConfirmationToken)
    private UserToken userToken;

    // TODO check
    public ConfirmationToken(User user) {
        new UserToken().setUser(user);
        createDate = new Date();
        token = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ConfirmationToken that = (ConfirmationToken) o;

        return new EqualsBuilder().append(id, that.id).append(token, that.token)
                .append(createDate, that.createDate).append(userToken, that.userToken).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id)
                .append(token).append(createDate).append(userToken).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("token", token)
                .append("createDate", createDate)
                .append("userToken", userToken)
                .toString();
    }
}