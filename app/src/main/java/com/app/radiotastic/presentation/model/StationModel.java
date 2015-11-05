package com.app.radiotastic.presentation.model;

import com.google.common.base.Preconditions;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public final class StationModel {
    private final long id;
    private final String name;

    public StationModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationModel that = (StationModel) o;

        if (id != that.id) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
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

        public StationModel create() {
            Preconditions.checkNotNull(mId);
            Preconditions.checkNotNull(mName);
            return new StationModel(mId, mName);
        }
    }
}
