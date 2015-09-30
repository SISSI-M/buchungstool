package buchungstool.view;

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

import buchungstool.model.config.Konfiguration;

/**
 * Backing bean for Konfiguration entities.
 * <p/>
 * This class provides CRUD functionality for all Konfiguration entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class KonfigurationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Konfiguration entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Konfiguration konfiguration;

	public Konfiguration getKonfiguration() {
		return this.konfiguration;
	}

	public void setKonfiguration(Konfiguration konfiguration) {
		this.konfiguration = konfiguration;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "konfiguration-persistence-unit", type = PersistenceContextType.EXTENDED)
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
			this.konfiguration = this.example;
		} else {
			this.konfiguration = findById(getId());
		}
	}

	public Konfiguration findById(Long id) {

		return this.entityManager.find(Konfiguration.class, id);
	}

	/*
	 * Support updating and deleting Konfiguration entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.konfiguration);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.konfiguration);
				return "view?faces-redirect=true&id="
						+ this.konfiguration.getId();
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
			Konfiguration deletableEntity = findById(getId());

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
	 * Support searching Konfiguration entities with pagination
	 */

	private int page;
	private long count;
	private List<Konfiguration> pageItems;

	private Konfiguration example = new Konfiguration();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Konfiguration getExample() {
		return this.example;
	}

	public void setExample(Konfiguration example) {
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
		Root<Konfiguration> root = countCriteria.from(Konfiguration.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Konfiguration> criteria = builder
				.createQuery(Konfiguration.class);
		root = criteria.from(Konfiguration.class);
		TypedQuery<Konfiguration> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Konfiguration> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		int winterPausenGrenze = this.example.getWinterPausenGrenze();
		if (winterPausenGrenze != 0) {
			predicatesList.add(builder.equal(root.get("winterPausenGrenze"),
					winterPausenGrenze));
		}
		int sommerPausenGrenze = this.example.getSommerPausenGrenze();
		if (sommerPausenGrenze != 0) {
			predicatesList.add(builder.equal(root.get("sommerPausenGrenze"),
					sommerPausenGrenze));
		}
		int defaultMinPlaetze = this.example.getDefaultMinPlaetze();
		if (defaultMinPlaetze != 0) {
			predicatesList.add(builder.equal(root.get("defaultMinPlaetze"),
					defaultMinPlaetze));
		}
		int defaultMaxPlaetze = this.example.getDefaultMaxPlaetze();
		if (defaultMaxPlaetze != 0) {
			predicatesList.add(builder.equal(root.get("defaultMaxPlaetze"),
					defaultMaxPlaetze));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Konfiguration> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Konfiguration entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Konfiguration> getAll() {

		CriteriaQuery<Konfiguration> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Konfiguration.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Konfiguration.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final KonfigurationBean ejbProxy = this.sessionContext
				.getBusinessObject(KonfigurationBean.class);

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

				return String.valueOf(((Konfiguration) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Konfiguration add = new Konfiguration();

	public Konfiguration getAdd() {
		return this.add;
	}

	public Konfiguration getAdded() {
		Konfiguration added = this.add;
		this.add = new Konfiguration();
		return added;
	}
}
