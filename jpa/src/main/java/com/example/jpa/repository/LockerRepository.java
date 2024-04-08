package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

public interface LockerRepository extends JpaRepository<Locker, Long> {

}
