package com.udacity.study.jam.radiotastic.db.category;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code category_item} table.
 */
public class CategoryItemSelection extends AbstractSelection<CategoryItemSelection> {
    @Override
    protected Uri baseUri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CategoryItemCursor} object, which is positioned before the first entry, or null.
     */
    public CategoryCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CategoryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CategoryCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CategoryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CategoryItemSelection id(long... value) {
        addEquals("category_item." + CategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryId(long... value) {
        addEquals(CategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryIdNot(long... value) {
        addNotEquals(CategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryIdGt(long value) {
        addGreaterThan(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdLt(long value) {
        addLessThan(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdLtEq(long value) {
        addLessThanOrEquals(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection name(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection description(String... value) {
        addEquals(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionNot(String... value) {
        addNotEquals(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionLike(String... value) {
        addLike(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionContains(String... value) {
        addContains(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionStartsWith(String... value) {
        addStartsWith(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionEndsWith(String... value) {
        addEndsWith(CategoryColumns.DESCRIPTION, value);
        return this;
    }
}
