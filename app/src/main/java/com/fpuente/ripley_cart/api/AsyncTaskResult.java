package com.fpuente.ripley_cart.api;

public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    AsyncTaskResult(Exception error) {
        super();
        this.error = error;

    }
}
