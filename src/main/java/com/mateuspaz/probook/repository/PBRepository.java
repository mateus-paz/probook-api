package com.mateuspaz.probook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mateuspaz.probook.entity.PBEntity;

@NoRepositoryBean
public interface PBRepository<ENTITY extends PBEntity> extends JpaRepository<ENTITY, Long> {

}
