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

import org.chronos.model.EntradaDeOrdem;
import org.chronos.model.Motivo;
import org.chronos.model.Servico;

/**
 * Backing bean for EntradaDeOrdem entities.
 * <p/>
 * This class provides CRUD functionality for all EntradaDeOrdem entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class EntradaDeOrdemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving EntradaDeOrdem entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private EntradaDeOrdem entradaDeOrdem;

	public EntradaDeOrdem getEntradaDeOrdem() {
		return this.entradaDeOrdem;
	}

	public void setEntradaDeOrdem(EntradaDeOrdem entradaDeOrdem) {
		this.entradaDeOrdem = entradaDeOrdem;
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
			this.entradaDeOrdem = this.example;
		} else {
			this.entradaDeOrdem = findById(getId());
		}
	}

	public EntradaDeOrdem findById(Long id) {

		return this.entityManager.find(EntradaDeOrdem.class, id);
	}

	/*
	 * Support updating and deleting EntradaDeOrdem entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.entradaDeOrdem);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.entradaDeOrdem);
				return "view?faces-redirect=true&id="
						+ this.entradaDeOrdem.getId();
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
			EntradaDeOrdem deletableEntity = findById(getId());

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
	 * Support searching EntradaDeOrdem entities with pagination
	 */

	private int page;
	private long count;
	private List<EntradaDeOrdem> pageItems;

	private EntradaDeOrdem example = new EntradaDeOrdem();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public EntradaDeOrdem getExample() {
		return this.example;
	}

	public void setExample(EntradaDeOrdem example) {
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
		Root<EntradaDeOrdem> root = countCriteria.from(EntradaDeOrdem.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<EntradaDeOrdem> criteria = builder
				.createQuery(EntradaDeOrdem.class);
		root = criteria.from(EntradaDeOrdem.class);
		TypedQuery<EntradaDeOrdem> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<EntradaDeOrdem> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String ordem = this.example.getOrdem();
		if (ordem != null && !"".equals(ordem)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("ordem")),
					'%' + ordem.toLowerCase() + '%'));
		}
		String endereco = this.example.getEndereco();
		if (endereco != null && !"".equals(endereco)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("endereco")),
					'%' + endereco.toLowerCase() + '%'));
		}
		String assinante = this.example.getAssinante();
		if (assinante != null && !"".equals(assinante)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("assinante")),
					'%' + assinante.toLowerCase() + '%'));
		}
		Servico servico = this.example.getServico();
		if (servico != null) {
			predicatesList.add(builder.equal(root.get("servico"), servico));
		}
		Motivo motivo = this.example.getMotivo();
		if (motivo != null) {
			predicatesList.add(builder.equal(root.get("motivo"), motivo));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<EntradaDeOrdem> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back EntradaDeOrdem entities (e.g. from
	 * inside an HtmlSelectOneMenu)
	 */

	public List<EntradaDeOrdem> getAll() {

		CriteriaQuery<EntradaDeOrdem> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(EntradaDeOrdem.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(EntradaDeOrdem.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final EntradaDeOrdemBean ejbProxy = this.sessionContext
				.getBusinessObject(EntradaDeOrdemBean.class);

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

				return String.valueOf(((EntradaDeOrdem) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private EntradaDeOrdem add = new EntradaDeOrdem();

	public EntradaDeOrdem getAdd() {
		return this.add;
	}

	public EntradaDeOrdem getAdded() {
		EntradaDeOrdem added = this.add;
		this.add = new EntradaDeOrdem();
		return added;
	}
}
