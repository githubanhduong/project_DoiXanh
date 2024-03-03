package com.doiXanh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.doiXanh.entity.GroupUser;
import com.doiXanh.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    @EntityGraph(attributePaths = {"groupUser"})
    List<User> findAllByIdIn(List<Integer> list);
    
    @Query("SELECT u FROM User u JOIN FETCH u.groupUser WHERE u.id IN :ids")
    List<User> findAllWithGroupUserByIdIn(@Param("ids") List<Integer> ids);
    
    @EntityGraph(attributePaths = {"groupUser"})
    @Query("SELECT u FROM User u JOIN FETCH u.groupUser WHERE u.id IN :ids AND u.groupUser.id = 3")
	List<User> findAllByIdInAndGroupId(@Param("ids") List<Integer> ids/* , @Param("groupId") Integer groupId */);
    
    @Modifying
    @Query("UPDATE User u SET u.groupUser = :group WHERE u.id in :userIds")
    void updateUsersGroup(@Param("userIds") List<Integer> userIds, @Param("group") GroupUser groupUser);
}
