package com.assessment.usermanagement.config;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.assessment.usermanagement.entity.User;
@Configuration
public class AppDataRestConfig implements RepositoryRestConfigurer{
	
	
	private EntityManager entityManager;
	
	@Autowired
	AppDataRestConfig(EntityManager entityManager){
		
		this.entityManager = entityManager;
		
	}
	
	//to make spring data rest readonly
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		HttpMethod[] unSupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };
		
		config.getExposureConfiguration().forDomainType(User.class)
		.withItemExposure((metadata,httpMethods)->httpMethods.disable(unSupportedActions))
		.withCollectionExposure((metadata,httpMethods)->httpMethods.disable(unSupportedActions));
		
		config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
	}
}
