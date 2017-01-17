package org.chronos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.chronos.model.MarcadorModel;

/**
 * DAO for Geoposicao
 */
@Stateless
public class MarcadorDao {
	@PersistenceContext(unitName = "chronos-persistence-unit")
	private EntityManager em;

	public void create(MarcadorModel entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		MarcadorModel entity = em.find(MarcadorModel.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public MarcadorModel findById(Long id) {
		return em.find(MarcadorModel.class, id);
		
	}

	public MarcadorModel update(MarcadorModel entity) {
		return em.merge(entity);
	}

	public List<MarcadorModel> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<MarcadorModel> findAllQuery = em.createQuery(
				"SELECT DISTINCT g FROM MarcadorModel g ORDER BY g.id",
				MarcadorModel.class);  
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	
	public List<MarcadorModel> listAll() {
		TypedQuery<MarcadorModel> findAllQuery = em.createQuery(
				"SELECT DISTINCT g FROM MarcadorModel g ORDER BY g.id",
				MarcadorModel.class);  
		 
		return findAllQuery.getResultList();
	}
}
