package com.udacity.study.jam.radiotastic.db.categoryitem;

import java.util.Date;

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
        return CategoryItemColumns.CONTENT_URI;
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
    public CategoryItemCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CategoryItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CategoryItemCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CategoryItemCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CategoryItemSelection id(long... value) {
        addEquals("category_item." + CategoryItemColumns._ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryId(long... value) {
        addEquals(CategoryItemColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryIdNot(long... value) {
        addNotEquals(CategoryItemColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public CategoryItemSelection categoryIdGt(long value) {
        addGreaterThan(CategoryItemColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(CategoryItemColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdLt(long value) {
        addLessThan(CategoryItemColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection categoryIdLtEq(long value) {
        addLessThanOrEquals(CategoryItemColumns.CATEGORY_ID, value);
        return this;
    }

    public CategoryItemSelection name(String... value) {
        addEquals(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameNot(String... value) {
        addNotEquals(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameLike(String... value) {
        addLike(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameContains(String... value) {
        addContains(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameStartsWith(String... value) {
        addStartsWith(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection nameEndsWith(String... value) {
        addEndsWith(CategoryItemColumns.NAME, value);
        return this;
    }

    public CategoryItemSelection description(String... value) {
        addEquals(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionNot(String... value) {
        addNotEquals(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionLike(String... value) {
        addLike(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionContains(String... value) {
        addContains(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionStartsWith(String... value) {
        addStartsWith(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemSelection descriptionEndsWith(String... value) {
        addEndsWith(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }
}
