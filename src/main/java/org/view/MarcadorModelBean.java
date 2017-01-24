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

import org.chronos.model.MarcadorModel;
import org.chronos.view.Pessoa;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import com.google.gson.Gson;

/**
 * Backing bean for MarcadorModel entities.
 * <p/>
 * This class provides CRUD functionality for all MarcadorModel entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class MarcadorModelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving MarcadorModel entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private MarcadorModel marcadorModel;

	public MarcadorModel getMarcadorModel() {
		return this.marcadorModel;
	}

	public void setMarcadorModel(MarcadorModel marcadorModel) {
		this.marcadorModel = marcadorModel;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "chronos-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);

		envMessage();

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
			this.marcadorModel = this.example;
		} else {
			this.marcadorModel = findById(getId());
		}
	}

	public MarcadorModel findById(Long id) {
		envMessage();

		return this.entityManager.find(MarcadorModel.class, id);
	}

	/*
	 * Support updating and deleting MarcadorModel entities
	 */

	public String update() {

		this.conversation.end();
		
		envMessage();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.marcadorModel);
				envMessage();
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.marcadorModel);
				envMessage();
				return "view?faces-redirect=true&id=" + this.marcadorModel.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}

	}

	public String delete() {
		this.conversation.end();

		try {
			MarcadorModel deletableEntity = findById(getId());

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			envMessage();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching MarcadorModel entities with pagination
	 */

	private int page;
	private long count;
	private List<MarcadorModel> pageItems;

	private MarcadorModel example = new MarcadorModel();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public MarcadorModel getExample() {
		return this.example;
	}

	public void setExample(MarcadorModel example) {
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
		Root<MarcadorModel> root = countCriteria.from(MarcadorModel.class);
		countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<MarcadorModel> criteria = builder.createQuery(MarcadorModel.class);
		root = criteria.from(MarcadorModel.class);
		TypedQuery<MarcadorModel> query = this.entityManager
				.createQuery(criteria.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<MarcadorModel> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String logradouro = this.example.getLogradouro();
		if (logradouro != null && !"".equals(logradouro)) {
			predicatesList.add(
					builder.like(builder.lower(root.<String>get("logradouro")), '%' + logradouro.toLowerCase() + '%'));
		}
		String numero = this.example.getNumero();
		if (numero != null && !"".equals(numero)) {
			predicatesList
					.add(builder.like(builder.lower(root.<String>get("numero")), '%' + numero.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<MarcadorModel> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back MarcadorModel entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<MarcadorModel> getAll() {

		CriteriaQuery<MarcadorModel> criteria = this.entityManager.getCriteriaBuilder()
				.createQuery(MarcadorModel.class);
		
		return this.entityManager.createQuery(criteria.select(criteria.from(MarcadorModel.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final MarcadorModelBean ejbProxy = this.sessionContext.getBusinessObject(MarcadorModelBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((MarcadorModel) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private MarcadorModel add = new MarcadorModel();

	public MarcadorModel getAdd() {
		return this.add;
	}

	public MarcadorModel getAdded() {
		MarcadorModel added = this.add;
		this.add = new MarcadorModel();
		return added;
	}

	private void envMessage() {
 
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Daniel ");
		pessoa.setIdade("3555");
		
		
		 
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		eventBus.publish("/mpsocket",new Gson().toJson(pessoa));
	}
}
