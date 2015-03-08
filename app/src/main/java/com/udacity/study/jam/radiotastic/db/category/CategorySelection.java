package com.udacity.study.jam.radiotastic.db.category;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code category} table.
 */
public class CategorySelection extends AbstractSelection<CategorySelection> {
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
     * @return A {@code CategoryCursor} object, which is positioned before the first entry, or null.
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


    public CategorySelection id(long... value) {
        addEquals("category." + CategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public CategorySelection categoryId(long... value) {
        addEquals(CategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategorySelection categoryIdNot(long... value) {
        addNotEquals(CategoryColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategorySelection categoryIdGt(long value) {
        addGreaterThan(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategorySelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategorySelection categoryIdLt(long value) {
        addLessThan(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategorySelection categoryIdLtEq(long value) {
        addLessThanOrEquals(CategoryColumns.CATEGORY_ID, value);
        return this;
    }

    public CategorySelection name(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection description(String... value) {
        addEquals(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategorySelection descriptionNot(String... value) {
        addNotEquals(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategorySelection descriptionLike(String... value) {
        addLike(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategorySelection descriptionContains(String... value) {
        addContains(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategorySelection descriptionStartsWith(String... value) {
        addStartsWith(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategorySelection descriptionEndsWith(String... value) {
        addEndsWith(CategoryColumns.DESCRIPTION, value);
        return this;
    }
}
