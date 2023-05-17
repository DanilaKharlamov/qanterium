package com.dkharlamov.qanterium.common.entity;


import java.util.Objects;

public abstract class BaseEntity<T> {
    public abstract T getId();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity baseEntity = (BaseEntity) obj;
        return Objects.equals(baseEntity.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
