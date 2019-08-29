package com.perezm27.CodeFellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String username;
    public String password;

    public String firstname;
    public String lastname;
    public Date dateOfBirth;
    public String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    List<UserPost> posts;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = { @JoinColumn(name="primaryUser")},
            inverseJoinColumns = {@JoinColumn(name = "followedUser")}
    )
    Set<ApplicationUser> usersThatIFollow;

    @ManyToMany(mappedBy = "usersThatIFollow")
    Set<ApplicationUser> usersThatFollowMe;


    public ApplicationUser(){}

    public ApplicationUser(String username, String password, String firstname, String lastname, Date dateOfBirth, String bio){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public List<UserPost> getPosts() {
        return posts;
    }

    public void addFollowedUsers(ApplicationUser followedUser){
        usersThatIFollow.add(followedUser);
    }

    public Set<ApplicationUser> getUsersThatIFollow(){
        return this.usersThatIFollow;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String toString(){
        StringBuilder followedUsersString = new StringBuilder();
        if(this.usersThatIFollow.size() > 0){
            followedUsersString.append(" Follows ");
            for(ApplicationUser followedUser : this.usersThatIFollow){
                followedUsersString.append(followedUser.username);
                followedUsersString.append(", ");
            }
            followedUsersString.delete(followedUsersString.length() - 2, followedUsersString.length());
        }
        return String.format("%s", followedUsersString.toString());
    }
}
