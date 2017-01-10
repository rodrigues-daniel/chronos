package org.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.chronos.model.Geoposicao;

/**
 * Backing bean for Geoposicao entities.
 * <p/>
 * This class provides CRUD functionality for all Geoposicao entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class GeoposicaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Geoposicao entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Geoposicao geoposicao;

	public Geoposicao getGeoposicao() {
		return this.geoposicao;
	}

	public void setGeoposicao(Geoposicao geoposicao) {
		this.geoposicao = geoposicao;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "chronos-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		return "create?faces-redirect=true";
	}

	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}

		if (this.id == null) {
			this.geoposicao = this.example;
		} else {
			this.geoposicao = findById(getId());
		}
	}

	public Geoposicao findById(Long id) {

		return this.entityManager.find(Geoposicao.class, id);
	}

	/*
	 * Support updating and deleting Geoposicao entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.geoposicao);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.geoposicao);
				return "view?faces-redirect=true&id=" + this.geoposicao.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			Geoposicao deletableEntity = findById(getId());

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Geoposicao entities with pagination
	 */

	private int page;
	private long count;
	private List<Geoposicao> pageItems;

	private Geoposicao example = new Geoposicao();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Geoposicao getExample() {
		return this.example;
	}

	public void setExample(Geoposicao example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Geoposicao> root = countCriteria.from(Geoposicao.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Geoposicao> criteria = builder
				.createQuery(Geoposicao.class);
		root = criteria.from(Geoposicao.class);
		TypedQuery<Geoposicao> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Geoposicao> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String logradouro = this.example.getLogradouro();
		if (logradouro != null && !"".equals(logradouro)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("logradouro")),
					'%' + logradouro.toLowerCase() + '%'));
		}
		String numero = this.example.getNumero();
		if (numero != null && !"".equals(numero)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("numero")),
					'%' + numero.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Geoposicao> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Geoposicao entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Geoposicao> getAll() {

		CriteriaQuery<Geoposicao> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Geoposicao.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Geoposicao.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final GeoposicaoBean ejbProxy = this.sessionContext
				.getBusinessObject(GeoposicaoBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Geoposicao) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Geoposicao add = new Geoposicao();

	public Geoposicao getAdd() {
		return this.add;
	}

	public Geoposicao getAdded() {
		Geoposicao added = this.add;
		this.add = new Geoposicao();
		return added;
	}
}
