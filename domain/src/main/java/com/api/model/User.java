package com.api.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;

    @Column(nullable = false, length = 64)
    private String uFirstName;

    @Column(nullable = false, length = 64)
    private String uLastName;

    @Column(nullable = false, length = 64)
    private String uEmail;

    @Column(nullable = false, length = 1500)
    private String uImageUrl;

    @Column(nullable = false, length = 64)
    private String uCategory;

    @Column(nullable = false, length = 64)
    private String uDob;

    @Column(nullable = false, length = 12)
    private Long uAadhaar;

    @Column(nullable = false, length = 64)
    private String uPan;

    @Column(nullable = false, length = 10)
    private Long uPhone;

    @Column(nullable = false, length = 64)
    private String uPassword;

    @Column(length = 64)
    private String uCurrentOutstandingAmount;

    @Column(length = 64)
    private String uCurrentRequestedAmount;

    @Column(length = 64)
    private String uRequestReason;

    @Column(nullable = true, length = 64)
    private String uAddress;

    @Column(nullable = true, length = 70)
    private String uPrivateKey;

    @Column(nullable = true)
    private String uApproveStatus; //will get updated on particular user

    @Column(nullable = true)
    private String uDisableVerification;

    @Column(nullable = true)
    private Long uBankAccountNumber;

    @Column(nullable = true)
    private String uIFSCCode;

    @Column(nullable = true)
    private String uPasswordResetToken;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "uId"), inverseJoinColumns = @JoinColumn(name = "rId"))
    private Set<Roles> roles = new HashSet<>();

    public void addRole(Roles role) {
        this.roles.add(role);
    }

    public boolean hasRole(String roleName) {
        Iterator<Roles> iterator = roles.iterator();
        while(iterator.hasNext()) {
            Roles roles = iterator.next();
            if(roles.getRName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

}
