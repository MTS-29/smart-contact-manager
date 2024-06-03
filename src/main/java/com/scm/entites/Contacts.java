package com.scm.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contacts {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String picture;
    @Column(length = 5000)
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedinLink;

    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> socialLink = new ArrayList<>();
}
