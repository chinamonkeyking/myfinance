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

    public InstrumentMapping() {
    }

    public InstrumentMapping(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentMapping that = (InstrumentMapping) o;

        if (!instrumentMappingId.equals(that.instrumentMappingId)) return false;
        if (!code.equals(that.code)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = instrumentMappingId.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InstrumentMapping{" +
                "instrumentMappingId=" + instrumentMappingId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
