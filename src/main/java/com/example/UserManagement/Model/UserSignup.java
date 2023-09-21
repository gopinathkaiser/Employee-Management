package com.example.UserManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "UserSignup")
public class UserSignup {


    @Id
    private String email;

    private String name;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Timestamp createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Timestamp modifyDate;


    @Column(name = "verificationCode", updatable = false)
    private String verificationCode;

    @Column(nullable = false)
    private boolean verificationStatus = false;


}
