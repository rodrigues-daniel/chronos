package org.chronos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.chronos.model.Geoposicao;

/**
 * DAO for Geoposicao
 */
@Stateless
public class GeoposicaoDao {
	@PersistenceContext(unitName = "chronos-persistence-unit")
	private EntityManager em;

	public void create(Geoposicao entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Geoposicao entity = em.find(Geoposicao.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Geoposicao findById(Long id) {
		return em.find(Geoposicao.class, id);
		
	}

	public Geoposicao update(Geoposicao entity) {
		return em.merge(entity);
	}

	public List<Geoposicao> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Geoposicao> findAllQuery = em.createQuery(
				"SELECT DISTINCT g FROM Geoposicao g ORDER BY g.id",
				Geoposicao.class);  
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	
	public List<Geoposicao> listAll() {
		TypedQuery<Geoposicao> findAllQuery = em.createQuery(
				"SELECT DISTINCT g FROM Geoposicao g ORDER BY g.id",
				Geoposicao.class);  
		 
		return findAllQuery.getResultList();
	}
}
