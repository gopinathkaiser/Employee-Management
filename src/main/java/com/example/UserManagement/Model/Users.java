package com.example.UserManagement.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Table(name = "Users")
public class Users {

    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column
   private String fname;

   @Column
   private String lname;

   @Column
   private Long mobile;

   @Column
   private LocalDate dob;

   @Column
   private String address;

   @Column
   private Timestamp createDate;

    @Column
    private Timestamp modifyDate;

    @ManyToOne(cascade =  CascadeType.REMOVE)
    @JoinColumn(name = "fk_role_id")
    private Role role;


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Users() {

    }

    public Users(String mail, String sankar, String m, long l, LocalDate of, String chennai) {
    }

    @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mobile=" + mobile +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }


    public Users(String email, String fname, String lname, Long mobile, LocalDate dob, String address, Timestamp createDate, Timestamp modifyDate) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.dob = dob;
        this.address = address;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public Users(String email) {
        this.email = email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
