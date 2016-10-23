/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gishoauth.spring.repositories;

import java.io.Serializable;
import org.gishoauth.model.entity.GishoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ZO-PC
 */
public interface GishoUserRepository extends JpaRepository<GishoUser,Serializable>{
    @Transactional
    GishoUser findByLogin(String login);
}
