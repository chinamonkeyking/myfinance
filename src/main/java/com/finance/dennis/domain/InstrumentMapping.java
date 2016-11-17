package com.finance.dennis.domain;

import javax.persistence.*;

/**
 * Created by XiaoNiuniu on 4/18/2016.
 */
@Entity
@Table(name = "INSTRUMENT_MAPPING")
public class InstrumentMapping {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "instrument_mapping_seq_gen")
    @SequenceGenerator(name = "instrument_mapping_seq_gen", sequenceName = "INSTRUMENT_MAPPING_SEQ", allocationSize = 10)
    @Column(name="INSTRUMENT_MAPPING_ID")
    private Long instrumentMappingId;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String subtype;

    public InstrumentMapping() {}

    public InstrumentMapping(String code, String name, String type, String subtype) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.subtype = subtype;
    }

    public Long getInstrumentMappingId() {
        return instrumentMappingId;
    }

    public void setInstrumentMappingId(Long instrumentMappingId) {
        this.instrumentMappingId = instrumentMappingId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentMapping that = (InstrumentMapping) o;

        if (!instrumentMappingId.equals(that.instrumentMappingId)) return false;
        if (!code.equals(that.code)) return false;
        if (!name.equals(that.name)) return false;
        if (!type.equals(that.type)) return false;
        return !(subtype != null ? !subtype.equals(that.subtype) : that.subtype != null);

    }

    @Override
    public int hashCode() {
        int result = instrumentMappingId.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (subtype != null ? subtype.hashCode() : 0);
        return result;
    }
}
