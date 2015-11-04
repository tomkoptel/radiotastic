package com.app.radiotastic.presentation.model;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public final class StationModel {
    private final long id;
    private String name;

    public StationModel(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String label) {
        this.name = label;
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
}
