package com.app.radiotastic.domain;

import com.google.common.base.Preconditions;

/**
 * @author Tom Koptel
 */
public final class Station {
    private final long id;
    private final String name;

    public Station(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != station.id) return false;
        return !(name != null ? !name.equals(station.name) : station.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long mId;
        private String mName;

        private Builder() {}

        public Builder setId(long id) {
            Preconditions.checkNotNull(id);
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            Preconditions.checkNotNull(name);
            mName = name;
            return this;
        }

        public Station create() {
            Preconditions.checkNotNull(mId);
            Preconditions.checkNotNull(mName);
            return new Station(mId, mName);
        }
    }
}
