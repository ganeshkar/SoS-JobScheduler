//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.12 à 02:40:31 PM CEST 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Log_level.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Log_level">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="error"/>
 *     &lt;enumeration value="warn"/>
 *     &lt;enumeration value="info"/>
 *     &lt;enumeration value="debug"/>
 *     &lt;enumeration value="debug1"/>
 *     &lt;enumeration value="debug2"/>
 *     &lt;enumeration value="debug3"/>
 *     &lt;enumeration value="debug4"/>
 *     &lt;enumeration value="debug5"/>
 *     &lt;enumeration value="debug6"/>
 *     &lt;enumeration value="debug7"/>
 *     &lt;enumeration value="debug8"/>
 *     &lt;enumeration value="debug9"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Log_level")
@XmlEnum
public enum LogLevel {

    @XmlEnumValue("error")
    ERROR("error"),
    @XmlEnumValue("warn")
    WARN("warn"),
    @XmlEnumValue("info")
    INFO("info"),
    @XmlEnumValue("debug")
    DEBUG("debug"),
    @XmlEnumValue("debug1")
    DEBUG_1("debug1"),
    @XmlEnumValue("debug2")
    DEBUG_2("debug2"),
    @XmlEnumValue("debug3")
    DEBUG_3("debug3"),
    @XmlEnumValue("debug4")
    DEBUG_4("debug4"),
    @XmlEnumValue("debug5")
    DEBUG_5("debug5"),
    @XmlEnumValue("debug6")
    DEBUG_6("debug6"),
    @XmlEnumValue("debug7")
    DEBUG_7("debug7"),
    @XmlEnumValue("debug8")
    DEBUG_8("debug8"),
    @XmlEnumValue("debug9")
    DEBUG_9("debug9");
    private final String value;

    LogLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LogLevel fromValue(String v) {
        for (LogLevel c: LogLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
