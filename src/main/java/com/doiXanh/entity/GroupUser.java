package com.doiXanh.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "group_user")
public class GroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TINYINT(4) DEFAULT 0")
    private boolean active;
    
//    @OneToMany(mappedBy = "groupUser")
//    @JsonBackReference        				1
//    private List<User> listUsers;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)			2
//    private List<User> listUsers;
}

