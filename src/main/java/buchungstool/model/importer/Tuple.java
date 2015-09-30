package buchungstool.model.importer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alexander Bischof on 24.07.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tuple {
  @XmlElement
  private Object value1;
  @XmlElement
  private Object value2;

  public Tuple() {
  }

  public Tuple(Object value1, Object value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  public Object getValue1() {
    return value1;
  }

  public void setValue1(Object value1) {
    this.value1 = value1;
  }

  public Object getValue2() {
    return value2;
  }

  public void setValue2(Object value2) {
    this.value2 = value2;
  }

  public static Tuple tuple(Object value1, Object value2) {
    return new Tuple(value1, value2);
  }

  @Override
  public String toString() {
    return "Tuple{" +
           "value1=" + value1 +
           ", value2=" + value2 +
           '}';
  }
}
