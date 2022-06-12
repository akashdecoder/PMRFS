package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long uId;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uFirstName;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uLastName;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uEmail;

    @Column(nullable = false, length = 1500)
    private @Getter @Setter String uImageUrl;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uCategory;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uDob;

    @Column(nullable = false, length = 12)
    private @Getter @Setter Long uAadhaar;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uPan;

    @Column(nullable = false, length = 10)
    private @Getter @Setter Long uPhone;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uPassword;

    @Column(length = 64)
    private @Getter @Setter String uCurrentOutstandingAmount;

    @Column(length = 64)
    private @Getter @Setter String uCurrentRequestedAmount;

    @Column(length = 64)
    private @Getter @Setter String uRequestReason;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uAddress;

    @Column(nullable = true, length = 70)
    private @Getter @Setter String uPrivateKey;

    @Column(nullable = true)
    private @Getter @Setter String uApproveStatus; //will get updated on particular user

    @Column(nullable = true)
    private @Getter @Setter String uDisableVerification;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "uId"), inverseJoinColumns = @JoinColumn(name = "rId"))
    private @Getter @Setter Set<Roles> roles = new HashSet<>();

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
