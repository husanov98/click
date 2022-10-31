package uz.mh.click.controller;

public abstract class ApiController<S> {
    protected static final String API = "/api";
    protected static final String VERSION = "/v1";

    public static final String PATH = API + VERSION;

    protected final S service;

    protected ApiController(S service) {
        this.service = service;
    }
}
