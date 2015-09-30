package buchungstool.model.config;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
@Entity
public class Konfiguration implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private int winterPausenGrenze;

	@Column
	private int sommerPausenGrenze;

	@Column
	private int defaultMinPlaetze;

	@Column
	private int defaultMaxPlaetze;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Konfiguration)) {
			return false;
		}
		Konfiguration other = (Konfiguration) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public int getWinterPausenGrenze() {
		return winterPausenGrenze;
	}

	public void setWinterPausenGrenze(int winterPausenGrenze) {
		this.winterPausenGrenze = winterPausenGrenze;
	}

	public int getSommerPausenGrenze() {
		return sommerPausenGrenze;
	}

	public void setSommerPausenGrenze(int sommerPausenGrenze) {
		this.sommerPausenGrenze = sommerPausenGrenze;
	}

	public int getDefaultMinPlaetze() {
		return defaultMinPlaetze;
	}

	public void setDefaultMinPlaetze(int defaultMinPlaetze) {
		this.defaultMinPlaetze = defaultMinPlaetze;
	}

	public int getDefaultMaxPlaetze() {
		return defaultMaxPlaetze;
	}

	public void setDefaultMaxPlaetze(int defaultMaxPlaetze) {
		this.defaultMaxPlaetze = defaultMaxPlaetze;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		result += ", winterPausenGrenze: " + winterPausenGrenze;
		result += ", sommerPausenGrenze: " + sommerPausenGrenze;
		result += ", defaultMinPlaetze: " + defaultMinPlaetze;
		result += ", defaultMaxPlaetze: " + defaultMaxPlaetze;
		return result;
	}
}