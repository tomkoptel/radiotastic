package com.app.radiotastic.presentation;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public interface LoadDataView {
    void showLoading();
    void hideLoading();
    void showRetry();
    void showError(String message);
}
